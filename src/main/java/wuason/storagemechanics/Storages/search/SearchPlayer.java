package wuason.storagemechanics.Storages.search;

import org.bukkit.entity.Player;
import wuason.storagemechanics.Storages.StorageMemory;

public class SearchPlayer {

    private Player player;
    private StorageMemory storageMemory;
    private SearchSystem.SearchType type;

    public SearchPlayer(Player p, StorageMemory memory, SearchSystem.SearchType t){

        this.player = p;
        this.storageMemory = memory;
        this.type = t;

    }

    public Player getPlayer() {
        return player;
    }

    public StorageMemory getStorageMemory() {
        return storageMemory;
    }

    public SearchSystem.SearchType getType() {
        return type;
    }
}
