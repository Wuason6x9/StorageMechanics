package wuason.storagemechanics.Storages;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class StorageMemory {
    private String id;
    private String title;
    private ArrayList<ItemStack[]> allItems = new ArrayList<>();
    private byte slots;
    private ArrayList<Inventory> inventories;
    private String uuidOwner;
    private boolean shulker;
    private String NameSpaceID;

    public StorageMemory(String ID, String TITLE, byte SLOTS, String uuidplayer, boolean isShulker, String namespaceid, int pag){
        this.id = ID;
        this.title = TITLE;
        this.slots = SLOTS;
        this.uuidOwner = uuidplayer;
        this.shulker = isShulker;
        this.NameSpaceID = namespaceid;
        for(int i=0;i<pag;i++){

            allItems.add(new ItemStack[SLOTS]);

        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
