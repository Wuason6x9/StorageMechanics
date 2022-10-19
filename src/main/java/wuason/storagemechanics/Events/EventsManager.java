package wuason.storagemechanics.Events;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import wuason.storagemechanics.Storage;
import wuason.storagemechanics.panels.PanelClick;
import wuason.storagemechanics.panels.PanelClose;
import wuason.storagemechanics.panels.PanelInfo;

public class EventsManager {
    private Storage core;

    public EventsManager(Storage plugin){
        this.core = plugin;
        register();
    }

    public void register(){
        
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new OnClick(core), core);
        pm.registerEvents(new OnInventoryClose(core), core);
        pm.registerEvents(new OnBlockBreak(core), core);
        pm.registerEvents(new OnPlaceBlock(core), core);
        pm.registerEvents(new OnFurnitureInteract(core), core);
        pm.registerEvents(new OnFurniturePlace(core), core);
        pm.registerEvents(new OnFurnitureBreak(core), core);
        pm.registerEvents(new OnItemsAdderLoad(core), core);
        pm.registerEvents(new PanelInfo(core, null), core);
        pm.registerEvents(new OnInventoryClick(core), core);
        pm.registerEvents(new OnUnLoadChunk(core), core);
        pm.registerEvents(new PlayerSendMessageEvent(core), core);
        pm.registerEvents(new PanelClick(core),core);
        pm.registerEvents(new PanelClose(core), core);

    }
}
