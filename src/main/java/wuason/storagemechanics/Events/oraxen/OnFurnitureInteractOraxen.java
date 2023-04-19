package wuason.storagemechanics.Events.oraxen;

import io.th0rgal.oraxen.api.events.OraxenFurnitureInteractEvent;
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
import wuason.storagemechanics.api.Events.StorageClickEvent;

import java.io.FileNotFoundException;

public class OnFurnitureInteractOraxen implements Listener {
    private final Storage core;

    public OnFurnitureInteractOraxen(Storage plugin){
        this.core = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInteract(OraxenFurnitureInteractEvent event) throws FileNotFoundException {

        Player player = event.getPlayer();
        String NamespacedID = event.getMechanic().getItemID();

        if(core.getEditorMode().isinEditorMode(player)){

            event.setCancelled(true);
            Location loc = event.getItemFrame().getLocation();

            if(player.isSneaking()){

                if(core.getBlockManager().existNameSpaceID(NamespacedID)){
                    //DELETE
                    PlayerEditorMode editor = core.getEditorMode().getPlayerEditorMode(player);

                    core.getBlockManager().removeNameSpaceID(NamespacedID);
                    core.getStorageUtils().removedBlock(editor, NamespacedID);

                    String id = core.getStorageUtils().getLocationStorageID(loc);

                    core.getStorageManager().removeStorage(id);


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

                    PlayerEditorMode editor = core.getEditorMode().getPlayerEditorMode(player);

                    core.getBlockManager().removeNameSpaceID(NamespacedID);
                    core.getStorageUtils().removedBlock(editor, NamespacedID);

                    String id = core.getStorageUtils().getLocationStorageID(loc);

                    core.getStorageManager().removeStorage(id);

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

                    succes = core.getRegionManagerStorage().hasPermission(event.getItemFrame().getLocation(), event.getPlayer());

                }
                if(event.getPlayer().hasPermission("storage.all")){

                    succes = true;

                }

                if (succes) {

                    Location loc = event.getItemFrame().getLocation();
                    if (!player.isSneaking()) {
                        event.setCancelled(true);
                        Manager blockManager = core.getBlockManager();

                        byte slots = blockManager.getSlots(NamespacedID);
                        String title = blockManager.getTitle(NamespacedID);
                        int pag = blockManager.getPags(NamespacedID);
                        boolean isShulker = blockManager.isShulker(NamespacedID);
                        String id = core.getStorageUtils().getLocationStorageID(loc);

                        if (core.getStorageManager().existStorageByID(id)) {  //SI existe el inventario

                            core.getStorageManager().openStorage(player, id,0);
                            StorageClickEvent storageClickEvent = new StorageClickEvent(id, player, loc.getBlock());
                            Bukkit.getPluginManager().callEvent(storageClickEvent);
                            //abrir inventario

                        } else {

                            if (core.getStorageManager().existStorageJson(id)) {

                                core.getStorageManager().openStorage(player, id,0);
                                StorageClickEvent storageClickEvent = new StorageClickEvent(id, player, loc.getBlock());
                                Bukkit.getPluginManager().callEvent(storageClickEvent);

                            } else {


                                core.getStorageManager().createStorage(player, id, title, slots, isShulker, NamespacedID, pag, loc);
                                //Crear el inventario
                                Bukkit.getScheduler().runTaskLater(core, () -> {
                                    try {
                                        core.getStorageManager().openStorage(player, id,0);
                                        StorageClickEvent storageClickEvent = new StorageClickEvent(id, player, loc.getBlock());
                                        Bukkit.getPluginManager().callEvent(storageClickEvent);
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
