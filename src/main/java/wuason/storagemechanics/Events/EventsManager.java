package wuason.storagemechanics.Events;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import wuason.storagemechanics.Storage;

public class EventsManager {
    private Storage core;

    public EventsManager(Storage plugin){
        this.core = plugin;
        register();
    }

    public void register(){
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new OnClick(core), core);
        pm.registerEvents(new OnClickInventory(), core);
        pm.registerEvents(new OnInventoryClose(core), core);
        pm.registerEvents(new OnInventoryOpen(core), core);
    }
}
