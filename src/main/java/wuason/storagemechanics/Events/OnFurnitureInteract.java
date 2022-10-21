package wuason.storagemechanics.Events;

import dev.lone.itemsadder.api.Events.FurnitureInteractEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import wuason.storagemechanics.BlockManager.Manager;
import wuason.storagemechanics.Editor.PlayerEditorMode;
import wuason.storagemechanics.Storage;

import java.io.FileNotFoundException;

public class OnFurnitureInteract implements Listener {

    private Storage core;

    public OnFurnitureInteract(Storage plugin){

        this.core = plugin;

    }



    @EventHandler(priority = EventPriority.NORMAL)
    public void onInteract(FurnitureInteractEvent event) throws FileNotFoundException {

        Player player = event.getPlayer();
        String NamespacedID = event.getNamespacedID();

        if(core.getEditorMode().isinEditorMode(player)){

            event.setCancelled(true);
            Location loc = event.getBukkitEntity().getLocation();

            if(player.isSneaking()){

                if(core.getBlockManager().existNameSpaceID(NamespacedID)){
                    //DELETE
                    PlayerEditorMode editor = core.getEditorMode().getPlayerEditorMode(player);

                    core.getBlockManager().removeNameSpaceID(NamespacedID);
                    core.getStorageUtils().removedBlock(editor, NamespacedID);

                    String id = core.getStorageUtils().getLocationStorageID(loc);

                    core.getStorageManager().RemoveStorage(id);


                }
                else {
                    //Si el bloque no esta en la lista

                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.NotInList") + NamespacedID));

                }

            }
            else {

                if(!core.getBlockManager().existNameSpaceID(NamespacedID)){


                    PlayerEditorMode editor = core.getEditorMode().getPlayerEditorMode(player);

                    //ADD
                    byte slots = editor.getSlots();
                    String title = editor.getTitle();
                    boolean isShulker = editor.isShulker();
                    int pages = editor.getPages();

                    core.getBlockManager().addNameSpaceID(NamespacedID,slots,title,isShulker,pages);
                    core.getStorageUtils().AddedBlock(editor, NamespacedID);


                }
                else{
                    //Añadir mensaje de que ya existe este bloque en la lista y que primero debe eliminarlo para poder añadir otro

                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.AlreadyInList")));

                }

            }


        }
        else {
            if(core.getBlockManager().existNameSpaceID(NamespacedID)) {

                boolean succes = false;

                if (core.getHelperManager() == null) {

                    succes = true;

                }
                if (core.getHelperManager() != null) {

                    succes = core.getRegionManagerStorage().hasPermission(event.getBukkitEntity().getLocation(), event.getPlayer());

                }
                if(event.getPlayer().hasPermission("storage.all")){

                    succes = true;

                }

                if (succes) {

                    Location loc = event.getBukkitEntity().getLocation();
                    if (!player.isSneaking()) {
                        event.setCancelled(true);
                        Manager blockManager = core.getBlockManager();

                        byte slots = blockManager.getSlots(NamespacedID);
                        String title = blockManager.getTitle(NamespacedID);
                        int pag = blockManager.getPags(NamespacedID);
                        boolean isShulker = blockManager.isShulker(NamespacedID);
                        String id = core.getStorageUtils().getLocationStorageID(loc);

                        if (core.getStorageManager().existStorageByID(id)) {  //SI existe el inventario

                            core.getStorageManager().OpenStorage(player, id,0);
                            //abrir inventario

                        } else {

                            if (core.getStorageManager().existStorageJson(id)) {

                                core.getStorageManager().OpenStorage(player, id,0);

                            } else {


                                core.getStorageManager().CreateStorage(player, id, title, slots, isShulker, NamespacedID, pag, loc);
                                //Crear el inventario
                                Bukkit.getScheduler().runTaskLater(core, () -> {
                                    try {
                                        core.getStorageManager().OpenStorage(player, id,0);
                                    } catch (FileNotFoundException e) {
                                        throw new RuntimeException(e);
                                    }
                                }, 1L);

                            }

                        }
                    }
                }
            }
        }
    }

}
