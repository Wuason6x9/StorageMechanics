package wuason.storagemechanics.Events.oraxen;

import io.th0rgal.oraxen.events.OraxenNoteBlockBreakEvent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wuason.storagemechanics.Storage;
import wuason.storagemechanics.Storages.StorageManager;
import wuason.storagemechanics.Storages.StorageMemory;

import java.io.IOException;
import java.util.UUID;

public class OnBlockBreakOraxen implements Listener {

    private final Storage core;

    public OnBlockBreakOraxen(Storage plugin){

        this.core = plugin;

    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void BlockBreak(OraxenNoteBlockBreakEvent event) throws IOException {

        String NameSpaceID = event.getNoteBlockMechanic().getItemID();

        if(core.getBlockManager().existNameSpaceID(NameSpaceID)) {

            boolean succes = false;


            if(core.getHelperManager() == null){

                succes = true;

            }
            if(core.getHelperManager() != null){

                succes = core.getRegionManagerStorage().hasPermission(event.getBlock().getLocation(),event.getPlayer());

            }
            if(event.getPlayer().hasPermission("storage.all")){

                succes = true;

            }

            if (succes) {

                Block block = event.getBlock();
                StorageManager manager = core.getStorageManager();
                Location loc = block.getLocation();
                String id = core.getStorageUtils().getLocationStorageID(loc);
                ItemStack storageItem = core.getAdapter().getItemStackByInstance(NameSpaceID);


                NamespacedKey key = new NamespacedKey(core, "storageid");


                if (manager.existStorageByID(id)) {

                    StorageMemory storageMemory = manager.getStorageMemory(id);

                    if(!core.getBlockManager().isBreakable(NameSpaceID)){

                        if(!storageMemory.isEmpty()){

                            event.setCancelled(true);
                            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',core.getConfig().getString("messages.ItemsInStorage")));
                            return;
                        }

                    }

                    manager.closePlayersInvetory(storageMemory);


                    if (storageMemory.isShulker()) {


                        ItemMeta itemMeta = storageItem.getItemMeta();

                        UUID uuid = core.getStorageUtils().storageIDtoUUID(id);
                        itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, uuid.toString());

                        storageItem.setItemMeta(itemMeta);


                        if (storageItem != null) {

                            loc.getWorld().dropItem(loc, storageItem);

                        }
                        //drop item with

                    } else {

                        for(ItemStack[] items : storageMemory.getAllItems()) {

                            for(ItemStack l : items) {

                                if(l != null && !l.getType().equals(Material.AIR)){

                                    ItemMeta meta = l.getItemMeta();

                                    if (!meta.getPersistentDataContainer().has(new NamespacedKey(core, "itemBlocked"), PersistentDataType.STRING)){
                                        if (l != null) {

                                            loc.getWorld().dropItem(loc, l);

                                        }
                                    }

                                }

                            }
                        }

                        core.getStorageManager().RemoveStorage(id);

                    }

                } else {
                    if (manager.existStorageJson(id)) {

                        StorageMemory storageMemory = manager.loadStorage(id);

                        if(!core.getBlockManager().isBreakable(NameSpaceID)){

                            if(!storageMemory.isEmpty()){

                                event.setCancelled(true);
                                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',core.getConfig().getString("messages.ItemsInStorage")));
                                return;

                            }

                        }

                        if (storageMemory.isShulker()) {

                            UUID uuid = core.getStorageUtils().storageIDtoUUID(id);

                            ItemMeta itemMeta = storageItem.getItemMeta();

                            itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, uuid.toString());

                            storageItem.setItemMeta(itemMeta);


                            if (storageItem != null) {

                                loc.getWorld().dropItem(loc, storageItem);

                            }


                        } else {

                            for(ItemStack[] items : storageMemory.getAllItems()) {

                                for(ItemStack l : items) {

                                    if(l != null && !l.getType().equals(Material.AIR)){

                                        ItemMeta meta = l.getItemMeta();

                                        if (!meta.getPersistentDataContainer().has(new NamespacedKey(core, "itemBlocked"), PersistentDataType.STRING)){
                                            if (l != null) {

                                                loc.getWorld().dropItem(loc, l);

                                            }
                                        }

                                    }

                                }
                            }

                            core.getStorageManager().RemoveStorage(id);

                        }
                        //CODIGO

                    } else {

                        if (core.getBlockManager().isShulker(NameSpaceID)) {

                            loc.getWorld().dropItem(loc, storageItem);

                        }

                    }


                }

            }
            else {

                event.setCancelled(true);

            }

        }
    }

}
