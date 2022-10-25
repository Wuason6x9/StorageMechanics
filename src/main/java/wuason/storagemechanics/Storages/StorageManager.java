package wuason.storagemechanics.Storages;

import com.google.gson.Gson;
import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.*;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitTask;
import wuason.storagemechanics.Storage;
import wuason.storagemechanics.StorageUtils;
import wuason.storagemechanics.Storages.chunk.ChunkManager;
import wuason.storagemechanics.Storages.chunk.ChunkStorage;
import wuason.storagemechanics.Storages.itemmodify.ItemModifyManager;
import wuason.storagemechanics.Storages.search.SearchSystem;
import wuason.storagemechanics.Storages.utils.ChatInputManager;
import wuason.storagemechanics.api.Events.InventoryOpenEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StorageManager {

    ArrayList<StorageMemory> storageMemories = new ArrayList<>();
    ArrayList<StorageFile> storageFiles = new ArrayList<>();
    private Gson gson;
    private Storage core;
    private StorageUtils storageUtils;

    private ChunkManager chunkManager;
    private SearchSystem searchSystemManager;
    private ItemModifyManager itemModifyManager;
    private Interfaces interfacesManger;
    private ChatInputManager chatInputManager;

    public StorageManager(Storage plugin){
        this.core = plugin;
        this.gson = new Gson();
        this.storageUtils = plugin.getStorageUtils();
        this.chunkManager = new ChunkManager(plugin);
        this.searchSystemManager = new SearchSystem(plugin);
        this.itemModifyManager = new ItemModifyManager(plugin);
        this.interfacesManger = new Interfaces(plugin);
        this.chatInputManager = new ChatInputManager(plugin);
    }

    public boolean RemoveStorage(String id){

        if(existStorageByID(id)){

            StorageMemory storageMemory = getStorageMemory(id);

            if(storageMemory.getInventories().get(0) != null){

                for(Inventory inv : storageMemory.getInventories()){

                    if(inv != null) {

                        while (!inv.getViewers().isEmpty()) {

                            inv.getViewers().get(0).closeInventory();

                        }
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

            getChunkManager().removeChunk(id);

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
                if(inv != null) {
                    inv = storageMemory.getInventories().get(i);
                    if (inv.getViewers().size() > 0) {

                        while (!inv.getViewers().isEmpty()) {

                            human = inv.getViewers().get(0);

                            human.removeMetadata("storageinventory", core);
                            human.removeMetadata("storageid", core);
                            human.removeMetadata("storageinventorypag", core);

                            human.closeInventory();

                        }

                        storageMemory.setItems(inv.getContents(), i);

                    }
                }
            }

        }

    }

    public void CloseStorage(Player player, String id) throws IOException {
        if(existStorageByID(id)){

            int pag = player.getMetadata("storageinventorypag").get(0).asInt();
            StorageMemory storageMemory = getStorageMemory(id);

            if(core.getBlockManager().getCloseSound(storageMemory.getNameSpaceID()) != null){

                String sound = core.getBlockManager().getCloseSound(storageMemory.getNameSpaceID());
                player.playSound(player.getLocation(),sound,100f,1f);

            }

            player.removeMetadata("storageid",core);
            player.removeMetadata("storageinventory",core);
            player.removeMetadata("storageinventorypag", core);

            closeInv(storageMemory,player,pag);


        }
    }


    public void openInv(StorageMemory memory,Player player ,int pag){

        if(core.getBlockManager().getOpenSound(memory.getNameSpaceID()) != null){

            String sound = core.getBlockManager().getOpenSound(memory.getNameSpaceID());
            player.playSound(player.getLocation(),sound,100f,1f);

        }

        player.setMetadata("storageinventorypag", new FixedMetadataValue(core, pag));
        player.setMetadata("storageid", new FixedMetadataValue(core, memory.getId()));

        if(memory.existInventory(pag)){

            Inventory inv = memory.getInventory(pag);

            player.setMetadata("storageinventory", new FixedMetadataValue(core, inv));
            player.openInventory(inv);

        }
        else {

            Inventory inv = Bukkit.createInventory(player,(int)memory.getSlots(),ChatColor.translateAlternateColorCodes('&',memory.getTitle().replaceAll("%actual_pag%", "" + (pag + 1))).replaceAll("%pag_count%", "" + memory.getPages()).replaceAll("%owner%","" + Bukkit.getOfflinePlayer(UUID.fromString(memory.getUuidOwner())).getName()));
            InventoryOpenEvent inventoryOpenEvent = new InventoryOpenEvent(player,inv,pag,memory);
            Bukkit.getPluginManager().callEvent(inventoryOpenEvent);


            player.setMetadata("storageinventory", new FixedMetadataValue(core, inv));
            inv.setContents(memory.getItems(pag));

            if(!core.getBlockManager().existInterfaces(memory.getNameSpaceID())) {

                for(int i = 0; i<inv.getSize();i++){
                    if(inv.getItem(i) != null && !inv.getItem(i).getType().equals(Material.AIR)) {

                        if (core.getStorageUtils().isBlockedItem(inv.getItem(i))) {

                            inv.clear(i);

                        }
                    }
                }

                if (memory.getPages() > 1) {

                    //set items

                    int[] slots = {45, 46, 47, 48, 49, 50, 51, 52, 53};
                    core.getStorageUtils().setBlockItems(slots, inv);


                    inv.setItem(52, core.getStorageUtils().getNextItem());
                    inv.setItem((memory.getSlots() - 4), core.getStorageUtils().getSearchItem());

                    if (pag > 0) {

                        inv.setItem(46, core.getStorageUtils().getBackItem());

                    }
                    if (pag == (memory.getPages() - 1)) {

                        inv.setItem(46, core.getStorageUtils().getBackItem());
                        inv.setItem(52, core.getStorageUtils().getBlockItem());
                    }
                }

                if (Bukkit.getPluginManager().getPlugin("ChestSort") != null) {
                    if (memory.getPages() > 1) {

                        inv.setItem((memory.getSlots() - 5), core.getStorageUtils().getChestSortItem());

                    } else {

                        inv.setItem(memory.getSlots() - 1, core.getStorageUtils().getChestSortItem());

                    }
                    //de.jeff_media.chestsort.api.ChestSortAPI.sortInventory(inv,0,45);
                }
            }
            else {


                if (memory.getPages() > 1){

                    if(pag == 0){

                        ItemStack[] itemStack = core.getBlockManager().getInterfaceFirst(memory.getNameSpaceID());

                        for (int i = 0; i < inv.getSize(); i++) {

                            if (inv.getItem(i) != null && !inv.getItem(i).getType().equals(Material.AIR)) {

                                if (core.getStorageUtils().isBlockedItem(inv.getItem(i))) {

                                    inv.clear(i);

                                }
                            }

                            if (itemStack[i] != null && !itemStack[i].getType().equals(Material.AIR)) {

                                inv.setItem(i, itemStack[i]);

                            }

                        }

                    }

                    else if (pag == (memory.getPages() - 1)) {

                        ItemStack[] itemStack = core.getBlockManager().getInterfaceLast(memory.getNameSpaceID());

                        for (int i = 0; i < inv.getSize(); i++) {

                            if (inv.getItem(i) != null && !inv.getItem(i).getType().equals(Material.AIR)) {

                                if (core.getStorageUtils().isBlockedItem(inv.getItem(i))) {

                                    inv.clear(i);

                                }
                            }

                            if (itemStack[i] != null && !itemStack[i].getType().equals(Material.AIR)) {

                                inv.setItem(i, itemStack[i]);

                            }

                        }

                    }

                    else if (pag > 0) {
                        ItemStack[] itemStack = core.getBlockManager().getInterfaceBetween(memory.getNameSpaceID());

                        for (int i = 0; i < inv.getSize(); i++) {

                            if (inv.getItem(i) != null && !inv.getItem(i).getType().equals(Material.AIR)) {

                                if (core.getStorageUtils().isBlockedItem(inv.getItem(i))) {

                                    inv.clear(i);

                                }
                            }

                            if (itemStack[i] != null && !itemStack[i].getType().equals(Material.AIR)) {

                                inv.setItem(i, itemStack[i]);

                            }

                        }

                        //BETWEEN INTERFACE

                    }

                }
                else {

                    ItemStack[] itemStack = core.getBlockManager().getInterfaceFirst(memory.getNameSpaceID());

                    for (int i = 0; i < inv.getSize(); i++) {

                        if (inv.getItem(i) != null && !inv.getItem(i).getType().equals(Material.AIR)) {

                            if (core.getStorageUtils().isBlockedItem(inv.getItem(i))) {

                                inv.clear(i);

                            }
                        }

                        if (itemStack[i] != null && !itemStack[i].getType().equals(Material.AIR)) {

                            inv.setItem(i, itemStack[i]);

                        }

                    }
                }

            }

            memory.setItems(inv.getContents(), pag);

            memory.setInventory(inv, pag);
            player.openInventory(inv);

        }

    }

    public void closeInv(StorageMemory memory, Player player, int pag){

        Inventory inventory = memory.getInventory(pag);

        List<HumanEntity> players = inventory.getViewers();

        if(!(players.size()>1)){

            memory.setItems(inventory.getContents(),pag);
            memory.removeInventory(pag);

        }


    }


    public void OpenStorage(Player player, String id, int pag) throws FileNotFoundException {


        if(existStorageByID(id)){
            StorageMemory storageMemory = getStorageMemory(id);


            openInv(storageMemory,player,pag);

        }
        else{
            if(existStorageJson(id)){

                if(!existStorageByID(id)){

                    StorageMemory storageMemory = loadStorage(id);


                    openInv(storageMemory,player,pag);

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

                }
            }
            else {

                player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.storageNotExist")));

            }
        }
    }




    public StorageMemory CreateStorage(Player player, String id, String title, byte slots, boolean isShulker, String namespaceid, int pag,Location loc) throws IllegalStateException{

        if(!existStorageJson(id)){
            if(existStorageByID(id)){

                return getStorageMemory(id);

            }
            else{

                StorageMemory storageMemory = new StorageMemory(id,title,slots,player.getUniqueId().toString(),isShulker,namespaceid, pag);

                ChunkStorage chunkStorage = getChunkManager().addChunk(loc, id);

                storageMemory.setChunkStorage(chunkStorage);

                storageMemories.add(storageMemory);

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


        StorageFile storageFile = new StorageFile(storageMemory.getId(),items,storageMemory.getSlots(),storageMemory.getTitle(),storageMemory.getUuidOwner(),storageMemory.isShulker(),storageMemory.getNameSpaceID(),storageMemory.getChunkStorage().getLocation());
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

                Location location = new Location(Bukkit.getWorld(storageFile.getWorld()),storageFile.getX(),storageFile.getY(),storageFile.getZ());

                UUID uuidOwner = UUID.fromString(storageFile.getUuidOwner());
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuidOwner);
                file.delete();


                ChunkStorage chunkStorage = getChunkManager().addChunk(location, id);
                storageMemory.setChunkStorage(chunkStorage);

                String title = storageFile.getTitle();
                int pag = storageFile.getPages();

                //inv add

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

    public ChunkManager getChunkManager() {
        return chunkManager;
    }

    public SearchSystem getSearchSystemManager() {
        return searchSystemManager;
    }

    public ItemModifyManager getItemModifyManager() {
        return itemModifyManager;
    }

    public Interfaces getInterfacesManger() {
        return interfacesManger;
    }

    public ChatInputManager getChatInputManager() {
        return chatInputManager;
    }
}
