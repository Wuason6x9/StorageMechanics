package wuason.storagemechanics.api.Events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import wuason.storagemechanics.Storages.StorageMemory;

public class StorageInventoryMoveItemToStorageEvent extends Event {

    private final static HandlerList handlers = new HandlerList();

    private Inventory source;
    private StorageMemory destination;
    private ItemStack itemStack;

    public StorageInventoryMoveItemToStorageEvent(ItemStack i, StorageMemory d, Inventory s){

        itemStack = i;
        destination = d;
        source = s;


    }


    public static HandlerList getHandlerList(){

        return handlers;

    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return getHandlerList();
    }

    public Inventory getSource() {
        return source;
    }

    public StorageMemory getDestination() {
        return destination;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
