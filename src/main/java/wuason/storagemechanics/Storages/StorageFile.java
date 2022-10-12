package wuason.storagemechanics.Storages;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import wuason.storagemechanics.Storage;

import java.util.ArrayList;

public class StorageFile {

    private String id;
    private String title;
    private byte slots;
    private String uuidOwner;
    private boolean shulker;
    private String NameSpaceID;

    private ArrayList<ArrayList<String>> items;

    public StorageFile(String ID, ArrayList<ArrayList<String>> itemStack, byte Slots, String TITLE,String uuidowner, boolean isShulker,String namespaceid){
        this.id = ID;
        this.slots = Slots;
        this.title = TITLE;
        this.items = itemStack;
        this.uuidOwner = uuidowner;
        this.shulker = isShulker;
        this.NameSpaceID = namespaceid;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public byte getSlots() {
        return slots;
    }

    public ArrayList<ArrayList<String>> getItems() {
        return items;
    }

    public String getUuidOwner() {
        return uuidOwner;
    }

    public boolean isShulker() {
        return shulker;
    }

    public String getNameSpaceID() {
        return NameSpaceID;
    }

    public int getPages(){

        return items.size();

    }
}
