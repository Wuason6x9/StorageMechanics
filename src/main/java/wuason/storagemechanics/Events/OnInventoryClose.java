package wuason.storagemechanics.Events;

import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.MetadataValue;
import wuason.storagemechanics.Storage;

import java.io.IOException;

public class OnInventoryClose implements Listener {

    private Storage core;

    public OnInventoryClose(Storage plugin){
        this.core = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void Onclose(InventoryCloseEvent event) throws IOException {

        Player player = (Player) event.getPlayer();


        if(player.hasMetadata("storageinventory")){

            Inventory inventory = (Inventory) player.getMetadata("storageinventory").get(0).value();

            if(event.getInventory().equals(inventory)){

                String id = player.getMetadata("storageid").get(0).asString();

                Player owner = (Player) inventory.getHolder();

                core.getStorageManager().CloseStorage(player, id);

            }
        }
    }
}
