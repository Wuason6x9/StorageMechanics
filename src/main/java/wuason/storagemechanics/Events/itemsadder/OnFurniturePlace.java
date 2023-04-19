package wuason.storagemechanics.Events.itemsadder;

import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTItem;
import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.Events.FurniturePlaceSuccessEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wuason.storagemechanics.Storage;

import java.io.IOException;
import java.util.ArrayList;

public class OnFurniturePlace implements Listener {

    private Storage core;

    public OnFurniturePlace(Storage plugin){
        this.core = plugin;
    }


    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlace(FurniturePlaceSuccessEvent event) throws IOException {

        String NameSpaceID = event.getNamespacedID();
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if(core.getBlockManager().existNameSpaceID(NameSpaceID)){

            if(itemInHand != null && !itemInHand.getType().equals(Material.AIR)) {

                ItemMeta itemMetaHand = itemInHand.getItemMeta();
                NamespacedKey key = new NamespacedKey(core, "storageid");
                String uuid = itemMetaHand.getPersistentDataContainer().get(key, PersistentDataType.STRING);

                if (uuid != null) {

                    Location loc = event.getBukkitEntity().getLocation();

                    String id = core.getStorageUtils().getLocationStorageID(loc);

                    core.getStorageUtils().storageUUIDtoID(id, uuid);

                }

            }

        }


    }

}
