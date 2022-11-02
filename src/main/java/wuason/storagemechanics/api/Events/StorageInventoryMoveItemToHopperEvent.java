package wuason.storagemechanics.api.Events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import wuason.storagemechanics.Storages.StorageMemory;

public class StorageInventoryMoveItemToHopperEvent extends Event implements Cancellable {

    private final static HandlerList handlers = new HandlerList();
    private StorageMemory source;
    private boolean isCancelled;
    private Inventory destination;
    private ItemStack item;

    public StorageInventoryMoveItemToHopperEvent(Inventory destinationInventory, StorageMemory sourceMemory, ItemStack itemStack){

        this.destination = destinationInventory;
        this.source = sourceMemory;
        this.item = itemStack;

    }

    public static HandlerList getHandlerList(){
        return handlers;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return getHandlerList();
    }

    public StorageMemory getSource() {
        return source;
    }

    public Inventory getDestination() {
        return destination;
    }

    public ItemStack getItem() {
        return item;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {

        isCancelled = cancel;

    }
}
