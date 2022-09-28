package wuason.storagemechanics.Storages;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class StorageMemory {
    private String id;
    private String totalid;
    private String uuid;
    private String player;
    private String title;
    private ItemStack[] items = {};
    private byte slots;
    private Inventory inventory;


    public StorageMemory(String UUID, String PLAYER, String ID, String TOTALID, String TITLE){
        this.totalid = TOTALID;
        this.id = ID;
        this.uuid = UUID;
        this.player = PLAYER;
        this.title = TITLE;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public ItemStack[] getItems() {
        return items;
    }

    public void setItems(ItemStack[] items) {
        this.items = items;
    }

    public String getTotalid() {
        return totalid;
    }

    public void setTotalid(String totalid) {
        this.totalid = totalid;
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

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
