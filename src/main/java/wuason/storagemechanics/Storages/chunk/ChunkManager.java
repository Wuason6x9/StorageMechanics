package wuason.storagemechanics.Storages.chunk;

import org.bukkit.Chunk;
import org.bukkit.Location;
import wuason.storagemechanics.Storage;
import wuason.storagemechanics.Storages.StorageMemory;

import java.util.ArrayList;
import java.util.List;

public class ChunkManager {

    private Storage core;
    ArrayList<ChunkStorage> chunks = new ArrayList<>();

    public ChunkManager(Storage plugin){

        this.core = plugin;

    }


    public List<ChunkStorage> getStorageChunks(Chunk chunk){

        List<ChunkStorage> list = new ArrayList<>();

        for(ChunkStorage c : chunks){

            if(c.getChunk().equals(chunk)){

                list.add(c);

            }

        }

        return list;

    }

    public ChunkStorage addChunk(Location loc, String id){


        ChunkStorage chunkStorage = new ChunkStorage(id,loc);

        chunks.add(chunkStorage);

        return chunkStorage;

    }

    public boolean existChunk(String id){

        for(ChunkStorage chunk : chunks){

            if(chunk.getId().equals(id)){

                return true;

            }

        }

        return false;

    }
    public boolean existChunk(Chunk c){

        for(ChunkStorage chunk : chunks){

            if(chunk.getChunk().equals(c)){

                return true;

            }

        }

        return false;

    }

    public boolean removeChunk(String id){

        for(ChunkStorage chunk : chunks){

            if(chunk.getId().equals(id)){

                chunks.remove(chunk);

                return true;

            }

        }

        return false;

    }
    public boolean removeChunk(Chunk c){

        for(ChunkStorage chunk : chunks){

            if(chunk.getChunk().equals(c)){

                chunks.remove(chunk);

                return true;

            }

        }

        return false;

    }

}
