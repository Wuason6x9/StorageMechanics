package wuason.storagemechanics.Events;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;
import wuason.storagemechanics.Storage;

public class OnInventoryOpen implements Listener {

    private Storage core;

    public OnInventoryOpen(Storage plugin){
        this.core = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void OnOpen(InventoryOpenEvent event){
        HumanEntity player = event.getPlayer();
    }
}
