package wuason.storagemechanics.Storages;

import com.google.gson.Gson;
import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTItem;
import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.*;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitTask;
import sun.jvm.hotspot.tools.HeapSummary;
import wuason.storagemechanics.Storage;
import wuason.storagemechanics.StorageUtils;
import wuason.storagemechanics.Storages.multipage.MultiPage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StorageManager {

    ArrayList<StorageMemory> storageMemories = new ArrayList<>();
    ArrayList<StorageFile> storageFiles = new ArrayList<>();
    private Gson gson;
    private MultiPage multiPageManager;
    private Storage core;
    private StorageUtils storageUtils;

    public StorageManager(Storage plugin){
        this.core = plugin;
        this.gson = new Gson();
        this.storageUtils = plugin.getStorageUtils();
        this.multiPageManager = new MultiPage(plugin);
    }

    public boolean RemoveStorage(String id){

        if(existStorageByID(id)){

            StorageMemory storageMemory = getStorageMemory(id);

            if(storageMemory.getInventories().get(0) != null){

                for(Inventory inv : storageMemory.getInventories()){

                    while (!inv.getViewers().isEmpty()){

                        inv.getViewers().get(0).closeInventory();

                    }

                }

            }

            if(!storageMemory.isShulker()){
                File file = new File(core.getDataFolder().getPath() + "/storages/blockstatic/" + storageMemory.getId() + ".json");
                if(file.exists()){
                    file.delete();
                }
            }
            else {
                File file = new File(core.getDataFolder().getPath() + "/storages/shulker/" + storageMemory.getId() + ".json");
                if(file.exists()){
                    file.delete();
                }
            }

            storageMemories.remove(storageMemory);

        }
        else {

            if(existStorageJson(id)){

                if(!shulkermodeJson(id)){

                    File file = new File(core.getDataFolder().getPath() + "/storages/blockstatic/" + id + ".json");

                    if(file.exists()){

                        file.delete();

                    }
                }
                else {

                    File file = new File(core.getDataFolder().getPath() + "/storages/shulker/" + id + ".json");

                    if(file.exists()){

                        file.delete();

                    }
                }

            }

        }
        return false;
    }

    public void closePlayersInvetory(StorageMemory storageMemory){

        ArrayList<ItemStack[]> items;
        Inventory inv;
        HumanEntity human;

        if(storageMemory.getInventories().get(0) != null){

            for(int i=0;i<storageMemory.getInventories().size();i++){

                inv = storageMemory.getInventories().get(i);
                if(inv.getViewers().size()>0){

                    while (!inv.getViewers().isEmpty()){

                        human = inv.getViewers().get(0);

                        human.removeMetadata("storageinventory",core);
                        human.removeMetadata("storageid",core);
                        human.removeMetadata("storageinventorypag", core);

                        human.closeInventory();

                    }

                    storageMemory.setItems(inv.getContents(), i);

                }

            }

        }

    }

    public void CloseStorage(Player player, String id) throws IOException {
        if(existStorageByID(id)){

            int pag = player.getMetadata("storageinventorypag").get(0).asInt();
            StorageMemory storageMemory = getStorageMemory(id);
            Inventory inventory = storageMemory.getInventories().get(pag);
            List<HumanEntity> players = inventory.getViewers();

            player.removeMetadata("storageid",core);
            player.removeMetadata("storageinventory",core);
            player.removeMetadata("storageinventorypag", core);
            if(players.size()>1){

            }
            else {

                storageMemory.setItems(inventory.getContents(),pag);

            }

        }
    }


    public void OpenStorage(Player player, String id, int pag) throws FileNotFoundException {

        if(existStorageByID(id)){
            StorageMemory storageMemory = getStorageMemory(id);
            Inventory inventory = storageMemory.getInventories().get(pag);
            List<HumanEntity> players = inventory.getViewers();

            if(players.size()>0){

                player.openInventory(inventory);
                player.setMetadata("storageinventorypag", new FixedMetadataValue(core, pag));
                player.setMetadata("storageinventory", new FixedMetadataValue(core, inventory));
                player.setMetadata("storageid", new FixedMetadataValue(core, id));

            }
            else {

                inventory.setContents(storageMemory.getItems(pag));


                player.openInventory(inventory);

                player.setMetadata("storageinventory", new FixedMetadataValue(core, inventory));

                player.setMetadata("storageinventorypag", new FixedMetadataValue(core, pag));

                player.setMetadata("storageid", new FixedMetadataValue(core, id));

            }
        }
        else{
            if(existStorageJson(id)){

                if(!existStorageByID(id)){

                    StorageMemory storageMemory = loadStorage(id);

                    Inventory inventory = storageMemory.getInventories().get(pag);

                    inventory.setContents(storageMemory.getItems(pag));

                    player.setMetadata("storageinventory", new FixedMetadataValue(core, inventory));

                    player.setMetadata("storageid", new FixedMetadataValue(core, id));

                    player.setMetadata("storageinventorypag", new FixedMetadataValue(core, pag));

                    Bukkit.getScheduler().runTaskLater(core,() -> player.openInventory(storageMemory.getInventories().get(pag)), 1L);

                }
            }
            else {

                System.out.println("Storage doesn't exist");

            }
        }
    }




    public StorageMemory CreateStorage(Player player, String id, String title, byte slots, boolean isShulker, String namespaceid, int pag) throws IllegalStateException{

        if(!existStorageJson(id)){
            if(existStorageByID(id)){

                return getStorageMemory(id);

            }
            else{

                ItemStack itemBlackPanel = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                ItemMeta itemBlackPanelMeta = itemBlackPanel.getItemMeta();
                itemBlackPanelMeta.setDisplayName(" ");
                itemBlackPanelMeta.setCustomModelData(core.getConfig().getInt("CustomModelDataBlackGlass"));
                itemBlackPanelMeta.getPersistentDataContainer().set(new NamespacedKey(core, "itemBlocked"), PersistentDataType.STRING, "blocked");
                itemBlackPanel.setItemMeta(itemBlackPanelMeta);

                StorageMemory storageMemory = new StorageMemory(id,title,slots,player.getUniqueId().toString(),isShulker,namespaceid, pag);



                ArrayList<Inventory> inventories = new ArrayList<>();

                title.replaceAll("%pag_count%", "" + pag);
                for(int i=0;i<(pag);i++){

                    title.replaceAll("%actual_pag%", "" + (i + 1));

                    Inventory inv = Bukkit.createInventory(player,(int)slots,title);

                    if(pag>1){

                        int[] slotsNull = {45,46,47,48,49,50,51,52,53};

                        //set items
                        for(int l : slotsNull){

                            inv.setItem(l, itemBlackPanel);

                        }

                        inv.setItem(52, core.getStorageUtils().getNextItem(CustomStack.getInstance(core.getConfig().getString("NextPageItem")).getItemStack()));


                        if(i>0){

                            inv.setItem(46, core.getStorageUtils().getBackItem(CustomStack.getInstance(core.getConfig().getString("BackPageItem")).getItemStack()));

                        }
                        if(i == (pag - 1)){

                            inv.setItem(46, core.getStorageUtils().getBackItem(CustomStack.getInstance(core.getConfig().getString("BackPageItem")).getItemStack()));
                            inv.setItem(52, itemBlackPanel);
                        }
                    }

                    storageMemory.setItems(inv.getContents(), i);
                    inventories.add(inv);


                }

                storageMemory.setInventories(inventories);
                storageMemories.add(storageMemory);
                TaskBlockMode(storageMemory);

                return storageMemory;

            }
        }
        return null;

    }


    public boolean IsInMemory(StorageMemory storageMemory){
        for(StorageMemory k:storageMemories){
            if(k.equals(storageMemory)){
                return true;
            }
        }
        return false;
    }

    public Boolean existStorageByID(String id){
        for(StorageMemory storageMemory:storageMemories){
            if(storageMemory.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    public Boolean existStorageJson(String id){

        File file = new File(core.getDataFolder().getPath() + "/storages/blockstatic/" + id + ".json");
        File file1 = new File(core.getDataFolder().getPath() + "/storages/shulker/" + id + ".json");

        if(file.exists()){

            return true;

        }
        if(file1.exists()){

            return true;

        }

        return false;

    }
    public Boolean shulkermodeJson(String id){

        File file = new File(core.getDataFolder().getPath() + "/storages/shulker/" + id + ".json");

        if(file.exists()){

            return true;

        }

        return false;

    }

    public Boolean existStorage(String uuid, String id){
        for(StorageMemory storageMemory:storageMemories){
            if(storageMemory.getUuidOwner().equals(uuid) && storageMemory.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    public StorageMemory getStorageMemory(String id) {
        for(StorageMemory storageMemory:storageMemories){
            if(storageMemory.getId().equals(id)){
                return storageMemory;
            }
        }
        return null;
    }
    public ArrayList<StorageMemory> getStorageMemories(Player player) {

        ArrayList<StorageMemory> list = new ArrayList<>();

        for(StorageMemory storageMemory:storageMemories){

            if(storageMemory.getUuidOwner().equals(player.getUniqueId())){

                list.add(storageMemory);

            }

        }
        return list;
    }

    public ArrayList<StorageMemory> getStorageMemoriesOfPlayerActives(OfflinePlayer player) {
        ArrayList<StorageMemory> storages = new ArrayList<>();
        for(StorageMemory storageMemory:storageMemories){
            if(storageMemory.getUuidOwner().equals(player.getUniqueId())){
                storages.add(storageMemory);
            }
        }
        return storages;
    }


    public void TaskBlockMode(StorageMemory storageMemory){
        if(IsInMemory(storageMemory)){

            BukkitTask task = Bukkit.getScheduler().runTaskLater(core, new Runnable() {

                @Override
                public void run() {


                    if(IsInMemory(storageMemory)){
                        Inventory inventory;

                        for(int i=0;i<storageMemory.getInventories().size();i++){

                            inventory = storageMemory.getInventories().get(i);

                            if(inventory.getViewers().size() >= 1){
                                TaskBlockMode(storageMemory);
                                return;

                            }


                            else {

                                while (!inventory.getViewers().isEmpty()){
                                    inventory.getViewers().get(0).closeInventory();
                                }
                                try {

                                    saveStorage(storageMemory);

                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }


                            }


                        }

                    }


                }

            }, 20L * (60L * core.getConfig().getLong("timetosavestorageinMINUTES")));
        }
    }



    public boolean saveStorage(StorageMemory storageMemory) throws IOException {

        closePlayersInvetory(storageMemory);

        File file = new File(core.getDataFolder().getPath() + "/storages/blockstatic/" + storageMemory.getId() + ".json");


        if(!file.exists()){
            file.getParentFile().mkdirs();
            file.createNewFile();
        }


        Writer writer = new FileWriter(file,false);

        ArrayList<ArrayList<String>> items = new ArrayList<>();
        ArrayList<String> itemsLocal;
        ItemStack[] itemStacks;

        for(int i=0;i<storageMemory.getAllItems().size();i++){

            itemsLocal = new ArrayList<>();
            itemStacks = storageMemory.getItems(i);

            for(int k=0;k<itemStacks.length;k++){

                if(itemStacks[k] != null){

                    NBTContainer container = NBTItem.convertItemtoNBT(itemStacks[k]);
                    itemsLocal.add(container.toString());

                }
                else{

                    itemsLocal.add(null);

                }
            }

            items.add(itemsLocal);

        }


        StorageFile storageFile = new StorageFile(storageMemory.getId(),items,storageMemory.getSlots(),storageMemory.getTitle(),storageMemory.getUuidOwner(),storageMemory.isShulker(),storageMemory.getNameSpaceID());
        gson.toJson(storageFile, writer);
        writer.flush();
        writer.close();

        storageMemories.remove(storageMemory);

        return true;
    }

    public StorageMemory loadStorage(String id) throws FileNotFoundException {

        if (!existStorageByID(id)) {

            File file = new File(core.getDataFolder().getPath() + "/storages/blockstatic/" + id + ".json");


            if (file.exists()) {


                Reader reader = new FileReader(file);

                StorageFile storageFile = gson.fromJson(reader, StorageFile.class);
                StorageMemory storageMemory = new StorageMemory(id,storageFile.getTitle(),storageFile.getSlots(),storageFile.getUuidOwner(),storageFile.isShulker(),storageFile.getNameSpaceID(), storageFile.getPages());

                UUID uuidOwner = UUID.fromString(storageFile.getUuidOwner());
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuidOwner);
                file.delete();

                String title = storageFile.getTitle();
                int pag = storageFile.getPages();
                ArrayList<Inventory> inventories = new ArrayList<>();

                title.replaceAll("%pag_count%", "" + pag);

                for(int i=0;i<pag;i++){

                    title.replaceAll("%actual_pag%", "" + (i + 1));
                    inventories.add(Bukkit.createInventory(offlinePlayer.getPlayer(),storageFile.getSlots(),title));

                }

                storageMemory.setInventories(inventories);

                storageMemories.add(storageMemory);

                ArrayList<ItemStack[]> itemsTotals = new ArrayList<>();
                ItemStack[] itemsLocal;

                for(int i=0;i<storageFile.getItems().size();i++){

                    itemsLocal = new ItemStack[storageFile.getItems().get(i).size()];

                    for (int k = 0; k < storageFile.getItems().get(i).size(); k++) {

                        if (storageFile.getItems().get(i).get(k) != null) {

                            itemsLocal[k] = NBTItem.convertNBTtoItem(new NBTContainer(storageFile.getItems().get(i).get(k)));

                        } else {

                            itemsLocal[k] = null;

                        }

                    }

                    itemsTotals.add(itemsLocal);

                }

                storageMemory.setAllItems(itemsTotals);

                return storageMemory;

            }

            return null;

        }

        else {

            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "This storage already loaded! " + id);
            return null;

        }
    }

    public ArrayList<StorageMemory> getStorageMemories() {
        return storageMemories;
    }
}
