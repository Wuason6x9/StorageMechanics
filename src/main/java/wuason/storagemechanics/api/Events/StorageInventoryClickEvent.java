package wuason.storagemechanics.api.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import wuason.storagemechanics.Storage;
import wuason.storagemechanics.Storages.StorageMemory;

public class StorageInventoryClickEvent extends PlayerEvent implements Cancellable {

    private final static HandlerList handlers = new HandlerList();
    private boolean cancelled = false;
    private String id;

    private InventoryClickEvent inventoryClickEvent;

    public StorageInventoryClickEvent(InventoryClickEvent event, String storageID) {
        super((Player)event.getWhoClicked());
        this.inventoryClickEvent = event;
        this.id = storageID;
    }

    public InventoryClickEvent getInventoryClickEvent() {
        return inventoryClickEvent;
    }

    @NotNull
    public static HandlerList getHandlerList(){

        return handlers;

    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return getHandlerList();
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    public String getStorageID() {
        return id;
    }
    public StorageMemory getStorageMemory(){
        return Storage.getInstance().getStorageManager().getStorageMemory(id);
    }
}
