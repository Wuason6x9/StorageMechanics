package wuason.storagemechanics.Events;

import dev.lone.itemsadder.api.Events.CustomBlockPlaceEvent;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wuason.storagemechanics.Storage;

import java.io.IOException;

public class OnPlaceBlock implements Listener {

    private Storage core;

    public OnPlaceBlock(Storage plugin){
        this.core = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void OnplaceCustomBlock(CustomBlockPlaceEvent event) throws IOException {

        ItemStack itemInHand = event.getItemInHand();
        ItemMeta itemMetaHand = itemInHand.getItemMeta();
        NamespacedKey key = new NamespacedKey(core, "storageid");
        String uuid = itemMetaHand.getPersistentDataContainer().get(key,PersistentDataType.STRING);

        if(uuid != null){

            Location loc = event.getBlock().getLocation();

            String id  = core.getStorageUtils().getLocationStorageID(loc);

            core.getStorageUtils().storageUUIDtoID(id, uuid);

        }

    }


}
