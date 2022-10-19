package wuason.storagemechanics.panels;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import wuason.storagemechanics.Storage;

public class PanelClick implements Listener {
    private Storage core;
    private PanelsManager manager;


    public PanelClick(Storage plugin){

        this.core = plugin;
        this.manager = plugin.getPanelsManager();

    }


    @EventHandler
    public void OnClick(InventoryClickEvent event){

        Inventory inventory = event.getInventory();

        if(manager.existPanel(inventory.getHolder())){

            event.setCancelled(true);

        }

    }
}
