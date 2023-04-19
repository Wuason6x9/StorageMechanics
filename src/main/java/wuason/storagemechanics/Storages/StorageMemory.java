package wuason.storagemechanics.Storages;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import wuason.storagemechanics.Storage;
import wuason.storagemechanics.Storages.chunk.ChunkStorage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class StorageMemory implements Serializable {
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
    public void removeItemAll(int page, int slot){

        if(existInventory(page)){
            Inventory inventory = getInventory(page);

            inventory.clear(slot);
        }
        else {

            removeItem(page,slot);

        }

    }
    public boolean removeItemAll(ItemStack itemStack){

        boolean succes = false;

        for(int i=0;i<getPages();i++){

            if(succes){
                return true;
            }

            if(existInventory(i)){


                Inventory inventory = getInventory(i);

                succes = core.getStorageUtils().removeItemInventory(inventory,itemStack);


            }
            else {

                succes = removeItem(i,itemStack);

            }


        }

        return succes;
    }

    public boolean addItemAll(ItemStack itemStack){

        boolean succes = false;

        for(int i=0;i<getPages();i++){

            if(succes){
                return true;
            }

            if(existInventory(i)){


                Inventory inventory = getInventory(i);

                succes = core.getStorageUtils().addItemInventory(inventory,itemStack);




            }
            else {

                succes = addItem(i,itemStack);

            }


        }

        return succes;
    }
    public boolean removeItem(int page,int slot){

        setItem(page,slot,null);

        return false;

    }
    public boolean removeItem(int page,ItemStack itemStack){

        ItemStack[] items = getItems(page);

        for(int i=0;i<items.length;i++){

            if(items[i].equals(itemStack)){

                setItem(page,i,null);
                return true;

            }

        }

        return false;

    }

    public boolean addItem(int page,ItemStack itemStack){

        ItemStack[] items = getItems(page);
        ItemStack item;
        int amount;
        int dispo;
        int amountStack = itemStack.getAmount();

        for(int i=0;i<items.length;i++){

            item = items[i];

            if(item != null && !item.getType().equals(Material.AIR)) {
                dispo = 64 - item.getAmount();
                amount = item.getAmount();
                if (item.getType().equals(itemStack.getType()) && item.getItemMeta().equals(itemStack.getItemMeta())) {
                    if (amount < 64) {
                        if (amountStack > dispo) {
                            item.setAmount(amount + dispo);
                            amountStack -= dispo;
                            dispo = 0;
                            itemStack.setAmount(amountStack);
                        } else if (amountStack <= dispo) {
                            dispo = dispo - amountStack;
                            itemStack.setAmount(0);
                            item.setAmount(amountStack + amount);
                            return true;
                        }
                    }
                }
            }
            else if(item == null || item.equals(Material.AIR)){

                setItem(page,i,itemStack);
                return true;

            }

        }

        return false;

    }

    public ItemStack getItemAll(int page, int slot){

        if(existInventory(page)){

            if(!core.getStorageUtils().inStorageMechanicsItem(getInventory(page).getItem(slot))) {
                return getInventory(page).getItem(slot);
            }
        }
        else {

            if(!core.getStorageUtils().inStorageMechanicsItem(getItem(page,slot))) {
                return getItem(page,slot);
            }

        }

        return null;
    }


    public ItemStack getItem(int page, int slot){

        ItemStack[] items = getItems(page);

        return items[slot];

    }
    public boolean setItem(int page, int slot, ItemStack itemStack){

        ItemStack[] items =  allItems.get(page) ;

        items[slot] = itemStack;

        return true;

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

        for(int i=0;i<getPages();i++){

            if(existInventory(i)){

                ItemStack[] itemStackList = getInventory(i).getContents();

                for(ItemStack item : itemStackList){

                    if(item != null && !item.equals(Material.AIR) && !item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(core, "itemBlocked"), PersistentDataType.STRING)){

                        return false;

                    }
                }

            }
            else {

                ItemStack[] itemStackList = allItems.get(i);

                for(ItemStack item : itemStackList){

                    if(item != null && !item.equals(Material.AIR) && !item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(core, "itemBlocked"), PersistentDataType.STRING)){

                        return false;

                    }
                }
            }
        }

        return true;

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
