package wuason.storagemechanics.Events;

import com.sk89q.worldguard.WorldGuard;
import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import wuason.storagemechanics.Storage;

public class OnItemsAdderLoad implements Listener {

    private Storage core;

    public OnItemsAdderLoad(Storage plugin){

        this.core = plugin;

    }


    @EventHandler(priority = EventPriority.NORMAL)
    public void OnLoad(ItemsAdderLoadDataEvent event){

        if(event.getCause().equals(ItemsAdderLoadDataEvent.Cause.FIRST_LOAD)){

            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6StorageMechanics&8] -> &aITEMSADDER LOADED!"));

        }
    }

}
