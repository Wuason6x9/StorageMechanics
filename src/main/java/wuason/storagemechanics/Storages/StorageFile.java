package wuason.storagemechanics.Storages;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import wuason.storagemechanics.Storage;

import java.util.ArrayList;

public class StorageFile {

    private String id;
    private String totalid;
    private String uuid;
    private String player;
    private String title;
    private byte slots;

    private ArrayList<String> items;

    public StorageFile(String p, String ID, ArrayList<String> itemStack, String UUID, String tid, byte Slots, String TITLE){
        this.player = p;
        this.id = ID;
        this.uuid = UUID;
        this.totalid = tid;
        this.slots = Slots;
        this.title = TITLE;
        this.items = itemStack;
    }
}
