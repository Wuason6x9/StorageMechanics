package wuason.storagemechanics.api;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import wuason.storagemechanics.Storage;
import wuason.storagemechanics.Storages.StorageManager;
import wuason.storagemechanics.Storages.StorageMemory;

import javax.annotation.Nullable;

public class StoragesApi {

    private static Storage core;
    private static StorageManager storageManager;

    public StoragesApi(Storage plugin, StorageManager sManager){

        core = plugin;
        storageManager = sManager;

    }

    public static StorageMemory getStorageMemory(String id){

        return storageManager.getStorageMemory(id);

    }

    public static boolean existStorage(String id){

        if(storageManager.existStorageByID(id)) return true;

        else if (storageManager.existStorageJson(id)) return true;

        return false;

    }

}
