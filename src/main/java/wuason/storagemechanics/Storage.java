package wuason.storagemechanics;

import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.enginehub.piston.config.Config;
import wuason.storagemechanics.BlockGestor.Gestor;
import wuason.storagemechanics.BossBar.BossBarManager;
import wuason.storagemechanics.Commands.CommandManager;
import wuason.storagemechanics.Editor.EditorMode;
import wuason.storagemechanics.Events.EventsManager;
import wuason.storagemechanics.Helper.Helper;
import wuason.storagemechanics.Storages.StorageManager;
import wuason.storagemechanics.info.InfoManager;
import wuason.storagemechanics.worldguard.RegionManagerStorage;

import java.io.FileNotFoundException;
import java.io.IOException;

public final class Storage extends JavaPlugin {

    private CommandManager commandManager;
    private EventsManager eventsManager;
    private StorageManager storageManager;
    private BossBarManager bossBarManager;
    private StorageUtils storageUtils;
    private EditorMode editorMode;
    private Gestor blockManager;
    private InfoManager infoManager;
    private Helper helperManager;
    private RegionManagerStorage regionManagerStorage;
    private ConfigManager configManager;
    private static Storage instance = null;

    public Storage(){
        if(instance==null) {
            instance = this;
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Instancia Cargada!");
        }
    }
    @Override
    public void onEnable() {

        //GESTORES o MANAGERS

        ConfigManager configManager = new ConfigManager(this);

        try {
            this.editorMode = new EditorMode(this);
            blockManager = new Gestor(this);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        storageUtils = new StorageUtils(this);
        commandManager = new CommandManager(this);
        storageManager = new StorageManager(this);
        bossBarManager = new BossBarManager(this);
        eventsManager = new EventsManager(this);
        try {
            infoManager = new InfoManager(this);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //GESTION WORLD EDIT
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6StorageMechanics&8] -> &bPlugin has been loaded!"));

        Bukkit.getScheduler().runTaskLater(this,() -> {

            if(Bukkit.getPluginManager().getPlugin("ChestSort") != null) {

                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6StorageMechanics&8] -> &eChestSort [ready]"));

            }
            if(Bukkit.getPluginManager().getPlugin("WorldGuard") != null){

                this.helperManager = new Helper(this);
                this.regionManagerStorage = new RegionManagerStorage(this);
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6StorageMechanics&8] -> &eWorldGuard [ready]"));
            }

        }, 1L);

        // Plugin startup logic

    }

    @Override
    public void onDisable() {

        //apagar block manager
        try {
            blockManager.stop();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //GUARDAR TODOS LOS INVETARIOS
        while (!storageManager.getStorageMemories().isEmpty()){
            
            try {
                storageManager.saveStorage(storageManager.getStorageMemories().get(0));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "¡All storages saved!");

        // Plugin shutdown logic
    }

    //OBTENER INSTANCIAS DE MANAGERS Y CORE
    public static Storage getInstance(){
        return instance;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public EventsManager getEventsManager() {
        return eventsManager;
    }

    public StorageManager getStorageManager() {
        return storageManager;
    }

    public BossBarManager getBossBarManager() {
        return bossBarManager;
    }

    public Gestor getBlockManager() {
        return blockManager;
    }

    public StorageUtils getStorageUtils() {
        return storageUtils;
    }

    public EditorMode getEditorMode() {
        return editorMode;
    }

    public Helper getHelperManager() {
        return helperManager;
    }

    public InfoManager getInfoManager() {
        return infoManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public RegionManagerStorage getRegionManagerStorage() {
        return regionManagerStorage;
    }
}
