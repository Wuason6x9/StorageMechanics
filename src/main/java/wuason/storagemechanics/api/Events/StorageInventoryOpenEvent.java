package wuason.storagemechanics.api.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import wuason.storagemechanics.Storages.StorageMemory;


public class StorageInventoryOpenEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();
    private Inventory inventory;
    private StorageMemory storageMemory;
    private int ActualPage;



    public StorageInventoryOpenEvent(Player who, Inventory inv, int actualPage, StorageMemory memory){
        super(who);

        this.inventory = inv;
        this.ActualPage = actualPage;
        this.storageMemory = memory;

    }

    public Inventory getInventory() {
        return inventory;
    }

    public StorageMemory getStorageMemory() {
        return storageMemory;
    }

    public int getActualPage() {
        return ActualPage;
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

}
