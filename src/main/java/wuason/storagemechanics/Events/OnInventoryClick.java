package wuason.storagemechanics.Events;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import wuason.storagemechanics.Storage;

import java.io.FileNotFoundException;

public class OnInventoryClick implements Listener {

    private Storage core;


    public OnInventoryClick(Storage plugin){

        this.core = plugin;

    }


    @EventHandler
    public void OnClick(InventoryClickEvent event){

        if(event.getCurrentItem() != null) {
            HumanEntity player = event.getWhoClicked();
            ItemStack slotItem = event.getCurrentItem();

            if (core.getStorageUtils().isBlockedItem(slotItem)) {

                event.setCancelled(true);

                if (core.getStorageUtils().isNextItem(slotItem)) {

                    int actualpag = player.getMetadata("storageinventorypag").get(0).asInt();
                    String id = player.getMetadata("storageid").get(0).asString();

                    try {
                        core.getStorageManager().OpenStorage((Player) player, id, (actualpag + 1));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                } else if (core.getStorageUtils().isBackItem(slotItem)) {

                    int actualpag = player.getMetadata("storageinventorypag").get(0).asInt();
                    String id = player.getMetadata("storageid").get(0).asString();

                    try {
                        core.getStorageManager().OpenStorage((Player) player, id, (actualpag - 1));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }


                }


            }
        }
    }

}