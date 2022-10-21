package wuason.storagemechanics.panels;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import wuason.storagemechanics.Storage;

public class PanelClose implements Listener {

    private Storage core;
    private PanelsManager manager;


    public PanelClose(Storage plugin){

        this.core = plugin;
        this.manager = plugin.getPanelsManager();

    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){



    }
}
