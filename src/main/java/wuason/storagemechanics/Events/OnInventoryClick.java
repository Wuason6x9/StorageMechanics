package wuason.storagemechanics.Events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import wuason.storagemechanics.Storage;
import wuason.storagemechanics.Storages.StorageMemory;
import wuason.storagemechanics.Storages.search.SearchSystem;
import wuason.storagemechanics.api.Events.StorageInventoryClickEvent;

import java.io.FileNotFoundException;

public class OnInventoryClick implements Listener {

    private Storage core;


    public OnInventoryClick(Storage plugin){

        this.core = plugin;

    }


    @EventHandler
    public void OnClick(InventoryClickEvent event){
        HumanEntity player = event.getWhoClicked();

        if(player.hasMetadata("storageinventorypag")){

            Inventory inventory = (Inventory) player.getMetadata("storageinventory").get(0).value();
            String id = player.getMetadata("storageid").get(0).asString();
            if(event.getInventory().equals(inventory)){
                if(core.getStorageManager().getStorageMemory(id).getChunkStorage() != null){
                    StorageInventoryClickEvent inventoryClickEvent = new StorageInventoryClickEvent(event, id);
                    Bukkit.getPluginManager().callEvent(inventoryClickEvent);
                    if(inventoryClickEvent.isCancelled()){
                        event.setCancelled(true);
                        return;
                    }
                }

            }

        }


        if(event.getCurrentItem() != null && !event.getCurrentItem().getType().equals(Material.AIR)) {
            ItemStack slotItem = event.getCurrentItem();

            if (core.getStorageUtils().isBlockedItem(slotItem)) {

                event.setCancelled(true);

                if (core.getStorageUtils().isNextItem(slotItem)) {

                    int actualpag = player.getMetadata("storageinventorypag").get(0).asInt();
                    String id = player.getMetadata("storageid").get(0).asString();
                    StorageMemory memory = core.getStorageManager().getStorageMemory(id);
                    core.getStorageManager().closeInv(memory,(Player)player,actualpag);

                    try {

                        core.getStorageManager().openStorage((Player) player, id, (actualpag + 1));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                } else if (core.getStorageUtils().isBackItem(slotItem)) {

                    int actualpag = player.getMetadata("storageinventorypag").get(0).asInt();
                    String id = player.getMetadata("storageid").get(0).asString();

                    StorageMemory memory = core.getStorageManager().getStorageMemory(id);
                    core.getStorageManager().closeInv(memory,(Player)player,actualpag);
                    try {
                        core.getStorageManager().openStorage((Player) player, id, (actualpag - 1));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }


                }
                else if (core.getStorageUtils().isSearchItem(slotItem)) {

                    String id = player.getMetadata("storageid").get(0).asString();

                    StorageMemory memory = core.getStorageManager().getStorageMemory(id);

                    Bukkit.getScheduler().runTask(core, () -> core.getStorageManager().getSearchSystemManager().ChatInput((Player) player,memory, SearchSystem.SearchType.GO_TO_PAGE,null));

                }
                else if (core.getStorageUtils().isSortItem(slotItem)){

                    StorageMemory storageMemory = core.getStorageManager().getStorageMemory(player.getMetadata("storageid").get(0).asString());

                    Inventory inventory = (Inventory) player.getMetadata("storageinventory").get(0).value();

                    if(storageMemory.getPages()>1){

                        de.jeff_media.chestsort.api.ChestSortAPI.sortInventory(inventory,0,(inventory.getSize() - 10));

                    }
                    else {

                        de.jeff_media.chestsort.api.ChestSortAPI.sortInventory(inventory,0,(inventory.getSize() - 2));

                    }

                }


            }
        }
    }

}
