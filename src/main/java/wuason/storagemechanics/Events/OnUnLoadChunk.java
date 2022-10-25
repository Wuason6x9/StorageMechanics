package wuason.storagemechanics.Events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;
import wuason.storagemechanics.Storage;
import wuason.storagemechanics.Storages.chunk.ChunkStorage;

import java.io.IOException;
import java.util.List;

public class OnUnLoadChunk implements Listener {

    private Storage core;


    public OnUnLoadChunk(Storage plugin){

        this.core = plugin;

    }


    @EventHandler
    public void OnUnLoad(ChunkUnloadEvent event){

        if(core.getStorageManager().getChunkManager().existChunk(event.getChunk())){

            System.out.println("Chunk descargado");

            List<ChunkStorage> chunkStorages = core.getStorageManager().getChunkManager().getStorageChunks(event.getChunk());
            ChunkStorage chunkStorage;

            while (!chunkStorages.isEmpty()){

                chunkStorage = chunkStorages.get(0);

                System.out.println("Storage guardado");

                Bukkit.getScheduler().runTask(core, () -> {
                    try {
                        core.getStorageManager().saveStorage(core.getStorageManager().getStorageMemory(chunkStorages.get(0).getId()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                chunkStorages.remove(chunkStorage);


            }


        }

    }

}
