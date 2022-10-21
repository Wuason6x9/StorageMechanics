package wuason.storagemechanics.Storages;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import wuason.storagemechanics.Storage;
import wuason.storagemechanics.Storages.chunk.ChunkStorage;

import java.util.ArrayList;
import java.util.UUID;

public class StorageMemory {
    private String id;
    private String title;
    private ArrayList<ItemStack[]> allItems = new ArrayList<>();
    private byte slots;
    private ArrayList<Inventory> inventories = new ArrayList<>();
    private String uuidOwner;
    private boolean shulker;
    private String NameSpaceID;
    private ChunkStorage chunkStorage;
    private Storage core;

    public StorageMemory(String ID, String TITLE, byte SLOTS, String uuidplayer, boolean isShulker, String namespaceid, int pag){
        this.id = ID;
        this.title = TITLE;
        this.slots = SLOTS;
        this.uuidOwner = uuidplayer;
        this.shulker = isShulker;
        this.NameSpaceID = namespaceid;
        this.core = Storage.getInstance();
        for(int i=0;i<pag;i++){

            allItems.add(new ItemStack[SLOTS]);

        }

        for(int i=0;i<pag;i++){

            inventories.add(null);

        }


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ChunkStorage getChunkStorage() {
        return chunkStorage;
    }

    public void setChunkStorage(ChunkStorage chunkStorage) {
        this.chunkStorage = chunkStorage;
    }

    public ItemStack[] getItems(int page) {
        return allItems.get(page);
    }

    public void setItems(ItemStack[] items, int page) {

        allItems.set(page,items);

    }

    public ArrayList<ItemStack[]> getAllItems() {
        return allItems;
    }

    public void setAllItems(ArrayList<ItemStack[]> allItems) {
        this.allItems = allItems;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public OfflinePlayer getOfflinePlayerOwner(){

        return Bukkit.getOfflinePlayer(UUID.fromString(uuidOwner));

    }

    public byte getSlots() {
        return slots;
    }

    public void setSlots(byte slots) {
        this.slots = slots;
    }

    public String getUuidOwner() {
        return uuidOwner;
    }

    public void setUuidOwner(String uuidOwner) {
        this.uuidOwner = uuidOwner;
    }

    public boolean isShulker() {
        return shulker;
    }

    public void setShulker(boolean shulker) {
        this.shulker = shulker;
    }

    public String getNameSpaceID() {
        return NameSpaceID;
    }

    public void setNameSpaceID(String nameSpaceID) {
        NameSpaceID = nameSpaceID;
    }

    public Inventory setInventory(Inventory inv, int page){

        return inventories.set(page, inv);

    }
    public boolean isEmpty(){

        boolean succes = true;

        for(ItemStack[] items : allItems){

            for(ItemStack item : items){

                if(item != null && !item.equals(Material.AIR) && !item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(core, "itemBlocked"), PersistentDataType.STRING)){

                    succes = false;

                }

            }

        }

        if(succes){

            return true;

        }

        else {

            return false;

        }

    }
    public boolean existInventory(int page){

        if(inventories.get(page) != null){

            return true;

        }

        return false;

    }

    public void removeInventory(int page){

        inventories.set(page, null);

    }

    public Inventory getInventory(int page){

        return inventories.get(page);

    }

    public ArrayList<Inventory> getInventories() {
        return inventories;
    }

    public void setInventories(ArrayList<Inventory> inventories) {
        this.inventories = inventories;
    }

    public int getPages(){

        return inventories.size();

    }
}
