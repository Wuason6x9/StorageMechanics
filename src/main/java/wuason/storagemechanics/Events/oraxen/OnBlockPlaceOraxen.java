package wuason.storagemechanics.Events.oraxen;

import io.th0rgal.oraxen.api.events.OraxenNoteBlockPlaceEvent;
import io.th0rgal.oraxen.api.events.OraxenStringBlockPlaceEvent;
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

public class OnBlockPlaceOraxen implements Listener {
    private Storage core;

    public OnBlockPlaceOraxen(Storage plugin){
        this.core = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void OnplaceCustomBlock(OraxenNoteBlockPlaceEvent event) throws IOException {

        ItemStack itemInHand = event.getPlayer().getInventory().getItemInMainHand();
        ItemMeta itemMetaHand = itemInHand.getItemMeta();
        NamespacedKey key = new NamespacedKey(core, "storageid");
        String uuid = itemMetaHand.getPersistentDataContainer().get(key, PersistentDataType.STRING);

        if(uuid != null){

            Location loc = event.getBlock().getLocation();

            String id  = core.getStorageUtils().getLocationStorageID(loc);

            core.getStorageUtils().storageUUIDtoID(id, uuid);

        }

    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void OnplaceCustomBlock(OraxenStringBlockPlaceEvent event) throws IOException {

        ItemStack itemInHand = event.getPlayer().getInventory().getItemInMainHand();
        ItemMeta itemMetaHand = itemInHand.getItemMeta();
        NamespacedKey key = new NamespacedKey(core, "storageid");
        String uuid = itemMetaHand.getPersistentDataContainer().get(key, PersistentDataType.STRING);

        if(uuid != null){

            Location loc = event.getBlock().getLocation();

            String id  = core.getStorageUtils().getLocationStorageID(loc);

            core.getStorageUtils().storageUUIDtoID(id, uuid);

        }

    }

}
