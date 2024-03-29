package wuason.storagemechanics.Events.oraxen;

import io.th0rgal.oraxen.api.events.OraxenFurniturePlaceEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wuason.storagemechanics.Storage;

import java.io.IOException;

public class OnFurniturePlaceOraxen implements Listener {

    private final Storage core;

    public OnFurniturePlaceOraxen(Storage plugin){
        this.core = plugin;
    }


    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlace(OraxenFurniturePlaceEvent event) throws IOException {

        String NameSpaceID = event.getMechanic().getItemID();
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if(core.getBlockManager().existNameSpaceID(NameSpaceID)){

            if(itemInHand != null && !itemInHand.getType().equals(Material.AIR)){

                ItemMeta itemMetaHand = itemInHand.getItemMeta();
                NamespacedKey key = new NamespacedKey(core, "storageid");
                String uuid = itemMetaHand.getPersistentDataContainer().get(key, PersistentDataType.STRING);

                if(uuid != null){

                    Location loc = event.getItemFrame().getLocation();

                    String id  = core.getStorageUtils().getLocationStorageID(loc);

                    core.getStorageUtils().storageUUIDtoID(id, uuid);

                }

            }

        }


    }
}
