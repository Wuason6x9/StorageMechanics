package wuason.storagemechanics.panels;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import wuason.storagemechanics.Storage;
import wuason.storagemechanics.Storages.utils.ChatInputManager;

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
        InventoryView inventoryView = event.getWhoClicked().getOpenInventory();

        String title = inventoryView.getTitle();
        Player player = (Player) event.getWhoClicked();

        if(manager.existPanel(inventory.getHolder())){

            event.setCancelled(true);

            if(core.getBlockManager().existNameSpaceID(title)){

                if(event.getSlot() == 14){

                    core.getBlockManager().setBreakable(title, !core.getBlockManager().isBreakable(title));
                    core.getStorageManager().getInterfacesManger().openEditor(player,title);

                }

                if(event.isLeftClick()){

                    if(event.getSlot() == 16){

                            core.getStorageManager().getInterfacesManger().OpenInterfaceEditorFirst(player,title);

                    }

                    if(event.getSlot() == 10){

                        core.getStorageManager().getChatInputManager().ChatInput(player,title, ChatInputManager.ChatInputType.OPEN_SOUND,null);

                    }
                    if(event.getSlot() == 12){

                        core.getStorageManager().getChatInputManager().ChatInput(player,title, ChatInputManager.ChatInputType.CLOSE_SOUND,null);

                    }

                }
                else if(event.isRightClick()){

                    if(event.getSlot() == 10){

                        core.getBlockManager().setOpenSound(title,null);
                        core.getStorageManager().getInterfacesManger().openEditor(player,title);

                    }
                    if(event.getSlot() == 12){

                        core.getBlockManager().setCloseSound(title,null);
                        core.getStorageManager().getInterfacesManger().openEditor(player,title);

                    }

                }
            }
        }

    }

}
