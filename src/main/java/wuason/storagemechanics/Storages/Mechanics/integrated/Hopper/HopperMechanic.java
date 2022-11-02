package wuason.storagemechanics.Storages.Mechanics.integrated.Hopper;

import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Hopper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import wuason.storagemechanics.Storage;
import wuason.storagemechanics.Storages.Mechanics.Mechanic;
import wuason.storagemechanics.Storages.StorageMemory;
import wuason.storagemechanics.api.Events.StorageInventoryClickEvent;
import wuason.storagemechanics.api.Events.StorageInventoryMoveItemToHopperEvent;
import wuason.storagemechanics.api.Events.StorageInventoryMoveItemToStorageEvent;

import java.util.ArrayList;

public class HopperMechanic extends Mechanic implements Listener {

    private Storage core;
    public HopperMechanic() {

        super("HopperMechanic");

        this.core = Storage.getInstance();
    }

    @EventHandler
    public void event(BlockPlaceEvent event){

        if(core.getAdapter().isCustomItem(event.getPlayer().getInventory().getItemInMainHand())) {

            Block hopperBlock = event.getBlockPlaced().getLocation().getWorld().getBlockAt(event.getBlockPlaced().getLocation().getBlockX(), event.getBlockPlaced().getLocation().getBlockY() + 1, event.getBlockPlaced().getLocation().getBlockZ());
            if(hopperBlock.getType().equals(Material.HOPPER)){

                transferItemsToStorage(HopperUtils.getHopperInventory(hopperBlock));

            }

        }

        if(event.getItemInHand().getType().equals(Material.HOPPER)){

            Location location = event.getBlock().getLocation();

            Block blockUp = location.getWorld().getBlockAt(location.getBlockX(), location.getBlockY() + 1, location.getBlockZ());

            if(core.getAdapter().isCustomBlock(blockUp)){

                if(core.getBlockManager().existNameSpaceID(core.getAdapter().getIDBlock(blockUp))){

                    if(HopperUtils.existStorage(blockUp.getLocation())){

                        Bukkit.getScheduler().runTaskLater(core, () -> {

                            transferItemsToHopper(blockUp, location.getBlock());

                        }, 15L);

                    }

                }

            }

        }

    }

    @EventHandler
    public void event(StorageInventoryClickEvent event){

        Block storageBlock = core.getAdapter().getCustomBlockAtLocation(event.getStorageMemory().getChunkStorage().getLocation());

        if(storageBlock != null) {

            ArrayList<Block> hoppers = HopperUtils.getHopperBlocksStorage(storageBlock);

            if (hoppers != null) {

                for (Block hopper : hoppers) {

                    org.bukkit.block.Hopper hopperState = (org.bukkit.block.Hopper) hopper.getState();

                    Inventory inventory = hopperState.getInventory();

                    Block block = HopperUtils.getBlockDirection(inventory);

                    transferItemsToStorage(inventory);

                }

            }

            Block blockDown = HopperUtils.getHopperBlockDownStorage(storageBlock);

            if(blockDown != null){

                transferItemsToHopper(storageBlock, blockDown);

            }
        }
    }


    @EventHandler
    public void event(InventoryClickEvent event){

        Inventory inventory = event.getInventory();

        if(HopperUtils.isHopper(inventory)) {

            Block block = HopperUtils.getBlockDirection(inventory);

            Block storageUp = block.getLocation().getWorld().getBlockAt(block.getLocation().getBlockX(),block.getLocation().getBlockY() + 1,block.getLocation().getBlockZ());

            if(storageUp != null && !storageUp.getType().equals(Material.AIR)){

                if(core.getAdapter().isCustomBlock(storageUp)){

                    if(core.getBlockManager().existNameSpaceID(core.getAdapter().getIDBlock(block))){

                        if(HopperUtils.existStorage(storageUp.getLocation())){

                            transferItemsToHopper(storageUp,block);

                        }

                    }

                }

            }

            if (core.getAdapter().isCustomBlock(block)) {

                transferItemsToStorage(inventory);

            }

        }


    }
    @EventHandler
    public void event(StorageInventoryMoveItemToHopperEvent event){

        Inventory inventory = event.getDestination();

        if(HopperUtils.isHopper(inventory)) {

            if(core.getStorageManager().IsInMemory(event.getSource()) && event.getSource() != null){

                transferItemsToHopper(event.getSource().getChunkStorage().getLocation().getBlock(), HopperUtils.getHopperBlock(inventory));

            }

            Block block = HopperUtils.getBlockDirection(inventory);

            if (core.getAdapter().isCustomBlock(block)) {

                transferItemsToStorage(inventory);

            }

        }

    }

    @EventHandler
    public void event(StorageInventoryMoveItemToStorageEvent event){

        Block hopperBlock = HopperUtils.getHopperBlock(event.getSource());

        Block storageBlock = HopperUtils.getBlockDirection(event.getSource());

        Block hopperDownStorageBlock = HopperUtils.getHopperBlockDownStorage(storageBlock);

        Block storageBlockUpHopper = HopperUtils.getStorageBlockUpHopper(hopperBlock);
        if(storageBlockUpHopper != null) {
            if (core.getAdapter().isCustomBlock(storageBlockUpHopper)) {

                transferItemsToHopper(storageBlockUpHopper, hopperBlock);

            }
        }
        if(hopperDownStorageBlock != null && hopperDownStorageBlock.getType().equals(Material.HOPPER)){

            transferItemsToHopper(storageBlock,hopperDownStorageBlock);

        }



    }

    @EventHandler
    public void event(InventoryMoveItemEvent event){

        Inventory inventoryD = event.getDestination();
        Inventory inventoryS = event.getSource();

        if(HopperUtils.isHopper(inventoryS)){
            Block hopperBlock = HopperUtils.getHopperBlock(inventoryS);
            Block upBlock = HopperUtils.getStorageBlockUpHopper(hopperBlock);

            if(upBlock != null && core.getAdapter().isCustomBlock(upBlock)){

                if(core.getBlockManager().existNameSpaceID(core.getAdapter().getIDBlock(upBlock))){

                    if(HopperUtils.existStorage(upBlock.getLocation())){

                        transferItemsToHopper(upBlock,hopperBlock);

                    }

                }

            }

        }

        if(HopperUtils.isHopper(inventoryD)) {

            Block block = HopperUtils.getBlockDirection(inventoryD);

            if (core.getAdapter().isCustomBlock(block)) {

                transferItemsToStorage(inventoryD);

            }

        }


    }

    @EventHandler
    public void event(InventoryPickupItemEvent event){

        Inventory inventory = event.getInventory();

        if(HopperUtils.isHopper(inventory)) {

            Block block = HopperUtils.getBlockDirection(inventory);

            if (core.getAdapter().isCustomBlock(block)) {

                transferItemsToStorage(inventory);

            }
        }
    }

    private void transferItemsToHopper(Block block, Block hopper){

        Bukkit.getScheduler().runTaskLater(core, () -> {
            if(hopper != null && !hopper.getType().equals(Material.AIR)) {

                if (core.getAdapter().isCustomBlock(block)) {

                    if (HopperUtils.existStorage(block.getLocation())) {

                        Location location = block.getLocation();

                        StorageMemory storageMemory = core.getStorageManager().getStorageMemory(core.getStorageUtils().getLocationStorageID(block.getLocation()));


                        if (core.getStorageManager().IsInMemory(storageMemory)) {

                            if (!storageMemory.isEmpty()) {

                                if (hopper != null) {
                                    Inventory inventory = HopperUtils.getHopperInventory(hopper);

                                    for (int i = 0; i < storageMemory.getPages(); i++) {

                                        for (int k = 0; k < storageMemory.getSlots(); k++) {

                                            ItemStack itemStack = storageMemory.getItemAll(i, k);

                                            if (itemStack != null && !itemStack.getType().equals(Material.AIR)) {

                                                if (HopperUtils.HopperHasSpace(inventory)) {

                                                    StorageInventoryMoveItemToHopperEvent event = new StorageInventoryMoveItemToHopperEvent(inventory, storageMemory, itemStack);
                                                    Bukkit.getPluginManager().callEvent(event);

                                                    if (event.isCancelled()) {
                                                        return;
                                                    }

                                                    storageMemory.removeItemAll(i,k);
                                                    inventory.addItem(itemStack);
                                                    return;

                                                } else {
                                                    return;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }, 15L);
    }

    private void transferItemsToStorage(Inventory inventory){

        Bukkit.getScheduler().runTaskLater(core, () -> {

            if(HopperUtils.isHopper(inventory)) {

                if (inventory != null) {

                    Block block = HopperUtils.getBlockDirection(inventory);

                    if(block != null) {

                        if (core.getAdapter().isCustomBlock(block)) {

                            Location location = block.getLocation();

                            if (HopperUtils.existStorage(location)) {

                                StorageMemory storageMemory = core.getStorageManager().getStorageMemory(core.getStorageUtils().getLocationStorageID(location));

                                if (core.getStorageManager().IsInMemory(storageMemory)) {

                                    if (!inventory.isEmpty()) {

                                        for (int i = 0; i < inventory.getSize(); i++) {

                                            ItemStack itemStack = inventory.getItem(i);

                                            if (itemStack != null && !itemStack.equals(Material.AIR)) {

                                                boolean succes = storageMemory.addItemAll(itemStack);

                                                if (succes) {

                                                    StorageInventoryMoveItemToStorageEvent event = new StorageInventoryMoveItemToStorageEvent(itemStack, storageMemory, inventory);

                                                    Bukkit.getPluginManager().callEvent(event);

                                                    inventory.clear(i);
                                                    return;

                                                }

                                            }

                                        }

                                    }

                                }
                            }
                        }
                    }
                }
            }
        }, 15L);
    }





}
