package wuason.storagemechanics;

public class StorageUtils {
    private static Storage core;

    public StorageUtils(Storage plugin){
        this.core = plugin;
    }

    public static String getFilePath(){
        return core.getDataFolder().getPath();
    }
}
