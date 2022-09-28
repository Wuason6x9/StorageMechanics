package wuason.storagemechanics.Storages;

import com.google.gson.Gson;
import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import wuason.storagemechanics.Storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StorageManager {

    ArrayList<StorageMemory> storageMemories = new ArrayList<>();
    ArrayList<StorageFile> storageFiles = new ArrayList<>();

    private Gson gson;

    private Storage core;

    public StorageManager(Storage plugin){
        this.core = plugin;
        this.gson = new Gson();
    }


    public boolean RemoveStorage(Player player, String id, String uuid){
        if(existStorage(player,id,uuid)){
            StorageMemory storageMemory = getStorageMemory(player,uuid,id);
            if(storageMemory.getInventory() != null){
                for(HumanEntity human:storageMemory.getInventory().getViewers()){
                    human.closeInventory();
                }
            }
            storageMemories.remove(storageMemory);
        }
        return false;
    }

    public void CloseStorage(String uuid, Player player, String id, Player owner) throws IOException {
        if(existStorage(owner,id,uuid)){
            StorageMemory storageMemory = getStorageMemory(owner,uuid,id);
            Inventory inventory = storageMemory.getInventory();
            List<HumanEntity> players = inventory.getViewers();
            if(players.size()>1){
                System.out.println(players.toString());
                System.out.println("¡Mas de un jugador!");
            }
            else {
                storageMemory.setItems(inventory.getContents());
                saveByBlockStatic(storageMemory);
            }

        }
    }


    public void OpenStorage(String uuid, Player player, String id, OfflinePlayer owner){
        if(existStorage(owner,id,uuid)){
            StorageMemory storageMemory = getStorageMemory(owner,uuid,id);
            Inventory inventory = storageMemory.getInventory();
            List<HumanEntity> players = inventory.getViewers();
            if(players.size()>0){
                player.openInventory(inventory);
                player.setMetadata("storageinventory", new FixedMetadataValue(core, inventory));
                player.setMetadata("storageid", new FixedMetadataValue(core, id));
                player.setMetadata("storageuuid", new FixedMetadataValue(core,uuid));

                System.out.println(players.toString());
                System.out.println("¡Mas de un jugador!sssss");
            }
            else {
                inventory.setContents(storageMemory.getItems());
                player.openInventory(inventory);
                player.setMetadata("storageinventory", new FixedMetadataValue(core, inventory));
                player.setMetadata("storageid", new FixedMetadataValue(core, id));
                player.setMetadata("storageuuid", new FixedMetadataValue(core,uuid));
            }
        }
        else{
            System.out.println("El storage no existe!");
        }
    }

    public StorageMemory CreateStorage(OfflinePlayer player, String id, String uuid, String title, byte slots) throws IllegalStateException{
        if(existStorage(player,id,uuid)){
            new IllegalStateException("Inventario existente");
        }
        else{
            String totalid = player.getName() + "_" + uuid + "_" + id;
            StorageMemory storageMemory = new StorageMemory(uuid, player.getName(), id, totalid, title);
            storageMemories.add(storageMemory);
            storageMemory.setInventory(Bukkit.createInventory(player.getPlayer(),(int)slots,title));
            return storageMemory;
        }
        return null;
    }

    public Boolean existStorage(OfflinePlayer player, String id, String uuid){
        for(StorageMemory storageMemory:storageMemories){
            if(storageMemory.getPlayer().equals(player.getName()) && storageMemory.getId().equals(id) && storageMemory.getUuid().equals(uuid)){
                return true;
            }
        }
        return false;
    }

    public StorageMemory getStorageMemory(OfflinePlayer player, String uuid, String id) {
        for(StorageMemory storageMemory:storageMemories){
            if(storageMemory.getPlayer().equals(player.getName()) && storageMemory.getId().equals(id) && storageMemory.getUuid().equals(uuid)){
                return storageMemory;
            }
        }
        return null;
    }
    public StorageMemory getStorageMemoryByID(String id) {
        for(StorageMemory storageMemory:storageMemories){
            if(storageMemory.getId().equals(id)){
                return storageMemory;
            }
        }
        return null;
    }

    public ArrayList<StorageMemory> getStorageMemoriesOfPlayerActives(OfflinePlayer player) {
        ArrayList<StorageMemory> storages = new ArrayList<>();
        for(StorageMemory storageMemory:storageMemories){
            if(storageMemory.getPlayer().equals(player.getName())){
                storages.add(storageMemory);
            }
        }
        return storages;
    }



    public boolean saveByBlockStatic(StorageMemory storageMemory) throws IOException {
        File file = new File(core.getDataFolder().getPath() + "/storages/blockstatic/" + storageMemory.getId());
        if(!file.exists()){
            file.getParentFile().mkdir();
            file.createNewFile();
        }
        Writer writer = new FileWriter(file,false);
        Gson gson = new Gson();
        ArrayList<String> items = new ArrayList<>();
        ItemStack[] itemStacks = storageMemory.getItems();

        for(int i=0;i<itemStacks.length;i++){
            if(itemStacks[i] != null){
                NBTContainer container = NBTItem.convertItemtoNBT(itemStacks[i]);
                items.add(container.toString());

            }
            else{
                items.add(null);
            }
        }

        StorageFile storageFile = new StorageFile(storageMemory.getPlayer(),storageMemory.getId(), items,storageMemory.getUuid(),storageMemory.getTotalid(),storageMemory.getSlots(),storageMemory.getTitle());
        gson.toJson(storageFile, writer);
        writer.flush();
        writer.close();
        return true;
    }

    public StorageMemory loadByBlockStatic(String id){
        return null;
    }
}
