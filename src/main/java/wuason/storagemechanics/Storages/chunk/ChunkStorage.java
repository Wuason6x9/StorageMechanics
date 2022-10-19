package wuason.storagemechanics.Storages.chunk;

import org.bukkit.Chunk;
import org.bukkit.Location;

public class ChunkStorage {

    private Location location;

    private Chunk chunk;

    private String id;

    public ChunkStorage(String id_storage, Location loc){

        this.id = id_storage;
        this.location = loc;
        this.chunk = loc.getChunk();

    }


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Chunk getChunk() {
        return chunk;
    }

    public void setChunk(Chunk chunk) {
        this.chunk = chunk;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
