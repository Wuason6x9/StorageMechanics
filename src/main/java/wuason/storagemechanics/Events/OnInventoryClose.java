package wuason.storagemechanics.Events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import wuason.storagemechanics.Storage;
import wuason.storagemechanics.Storages.itemmodify.ItemModifyManager;

import java.io.IOException;

public class OnInventoryClose implements Listener {

    private Storage core;

    public OnInventoryClose(Storage plugin){
        this.core = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void Onclose(InventoryCloseEvent event) throws IOException {

        Player player = (Player) event.getPlayer();

        String title = event.getView().getTitle();

        Inventory inventory = event.getInventory();


        if(player.hasMetadata("storageinventory")){

            Inventory inv = (Inventory) player.getMetadata("storageinventory").get(0).value();

            if(event.getInventory().equals(inv)){

                String id = player.getMetadata("storageid").get(0).asString();

                Player owner = (Player) inv.getHolder();

                core.getStorageManager().CloseStorage(player, id);

            }
        }

        if(title.contains(event.getPlayer().getName() + "_" + "editor_interface")){

            String NamespaceId = title.replaceAll("_" + event.getPlayer().getName() + "_" + "editor_interface","").replaceAll("_first", "").replaceAll("_between", "").replaceAll("_last", "");

            if(title.contains("first")) {


                if (core.getBlockManager().existNameSpaceID(NamespaceId)) {

                    for (int i = 0; i < inventory.getSize(); i++) {

                        if (inventory.getItem(i) != null && !inventory.getItem(i).equals(Material.AIR)) {

                            if (core.getStorageManager().getItemModifyManager().hasItemModification(inventory.getItem(i))) {

                                ItemStack item = core.getStorageUtils().getItemModified(inventory.getItem(i), core.getStorageManager().getItemModifyManager().getItemModification(inventory.getItem(i)));

                                inventory.setItem(i, item);

                            }

                        }

                    }

                    core.getBlockManager().setInterfaceFirst(NamespaceId, inventory);


                    if(core.getBlockManager().isMultiPage(NamespaceId)){

                        core.getStorageManager().getInterfacesManger().OpenInterfaceEditorBetween(player, NamespaceId);

                    }

                }
            }

            else if(title.contains("between")){


                if (core.getBlockManager().existNameSpaceID(NamespaceId)) {

                    for (int i = 0; i < inventory.getSize(); i++) {

                        if (inventory.getItem(i) != null && !inventory.getItem(i).equals(Material.AIR)) {

                            if (core.getStorageManager().getItemModifyManager().hasItemModification(inventory.getItem(i))) {

                                ItemStack item = core.getStorageUtils().getItemModified(inventory.getItem(i), core.getStorageManager().getItemModifyManager().getItemModification(inventory.getItem(i)));

                                inventory.setItem(i, item);

                            }

                        }

                    }

                    core.getBlockManager().setInterfaceBetween(NamespaceId, inventory);

                    if(core.getBlockManager().isMultiPage(NamespaceId)){

                        core.getStorageManager().getInterfacesManger().OpenInterfaceEditorLast(player, NamespaceId);

                    }

                }
            }
            else if(title.contains("last")){


                if (core.getBlockManager().existNameSpaceID(NamespaceId)) {

                    for (int i = 0; i < inventory.getSize(); i++) {

                        if (inventory.getItem(i) != null && !inventory.getItem(i).equals(Material.AIR)) {

                            if (core.getStorageManager().getItemModifyManager().hasItemModification(inventory.getItem(i))) {

                                ItemStack item = core.getStorageUtils().getItemModified(inventory.getItem(i), core.getStorageManager().getItemModifyManager().getItemModification(inventory.getItem(i)));

                                inventory.setItem(i, item);

                            }

                        }

                    }

                    core.getBlockManager().setInterfaceLast(NamespaceId, inventory);


                }
            }
        }
    }
}
