package wuason.storagemechanics.api.Events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import wuason.storagemechanics.Storages.StorageMemory;

public class StorageClickEvent extends Event {
    private String id;
    private Player player;
    private Block block;
    private static HandlerList handlerList = new HandlerList();

    public StorageClickEvent(String id, Player player, Block block) {
        this.id = id;
        this.player = player;
        this.block = block;
    }

    public String getStorageId() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public Block getBlock() {
        return block;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return getHandlerList();
    }
}
