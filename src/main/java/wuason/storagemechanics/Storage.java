package wuason.storagemechanics;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import wuason.storagemechanics.BlockManager.Manager;
import wuason.storagemechanics.BossBar.BossBarManager;
import wuason.storagemechanics.Commands.CommandManager;
import wuason.storagemechanics.Editor.EditorMode;
import wuason.storagemechanics.Events.EventsManager;
import wuason.storagemechanics.Helper.Helper;
import wuason.storagemechanics.Storages.StorageManager;
import wuason.storagemechanics.adapters.Adapter;
import wuason.storagemechanics.adapters.PluginSelectorManager;
import wuason.storagemechanics.info.InfoManager;
import wuason.storagemechanics.mythic.MythicMobsManager;
import wuason.storagemechanics.panels.PanelsManager;
import wuason.storagemechanics.worldguard.RegionManagerStorage;

import java.io.FileNotFoundException;
import java.io.IOException;

public final class Storage extends JavaPlugin {

    private CommandManager commandManager;
    private MythicMobsManager mythicMobsManager;
    private EventsManager eventsManager;
    private StorageManager storageManager;
    private BossBarManager bossBarManager;
    private StorageUtils storageUtils;
    private EditorMode editorMode;
    private Manager blockManager;
    private InfoManager infoManager;
    private Helper helperManager;
    private RegionManagerStorage regionManagerStorage;
    private PanelsManager panelsManager;
    private PluginSelectorManager pluginSelectorManager;
    private PluginSelectorManager.PluginSelected pluginSelected;
    private boolean placeHolderApi = false;
    private Adapter adapter;
    private ConfigManager configManager;
    private static Storage instance = null;
    private int pluginId = 17896;

    public Storage(){
        if(instance == null) {
            instance = this;
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Instancia Cargada!");
        }
    }

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIConfig().silentLogs(true));
    }

    @Override
    public void onEnable() {

        Metrics metrics = new Metrics(this, pluginId);

        //GESTORES o MANAGERS
        CommandAPI.onEnable(this);
        //Selector Plugin
        pluginSelectorManager = new PluginSelectorManager(this);
        pluginSelected = pluginSelectorManager.SelectPlugin();
        adapter = new Adapter(this, pluginSelected);

        ConfigManager configManager = new ConfigManager(this);

        try {
            this.editorMode = new EditorMode(this);
            blockManager = new Manager(this);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        storageUtils = new StorageUtils(this);
        mythicMobsManager = new MythicMobsManager(this);
        commandManager = new CommandManager(this);
        storageManager = new StorageManager(this);
        panelsManager = new PanelsManager(this);
        bossBarManager = new BossBarManager(this);
        eventsManager = new EventsManager(this);
        try {
            infoManager = new InfoManager(this);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //GESTION WORLD EDIT

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6StorageMechanics&8] -> &e(------------------------------)"));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6StorageMechanics&8] -> &bPlugin has been loaded!"));

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&8[&6StorageMechanics&8] -> &bPlugin selected: &3" + pluginSelected));
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            this.placeHolderApi = true;
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6StorageMechanics&8] -> &bPlaceholderAPI [ready]"));
        }
        if(Bukkit.getPluginManager().getPlugin("WorldGuard") != null){
            this.helperManager = new Helper(this);
            this.regionManagerStorage = new RegionManagerStorage(this);
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6StorageMechanics&8] -> &bWorldGuard [ready]"));
        }
        if(Bukkit.getPluginManager().getPlugin("ChestSort") != null) {

            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6StorageMechanics&8] -> &bChestSort [ready]"));

        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6StorageMechanics&8] -> &6Mechanics loaded: " + ChatColor.AQUA + storageManager.getMechanicsManager().getMechanicActiveList().size()));
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6StorageMechanics&8] -> &e(------------------------------)"));
        // Plugin startup logic

    }

    @Override
    public void onDisable() {

        if(pluginSelected != null) {

            //apagar block manager
            try {
                blockManager.stop();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //GUARDAR TODOS LOS INVETARIOS
            while (!storageManager.getStorageMemories().isEmpty()) {

                try {
                    storageManager.saveStorage(storageManager.getStorageMemories().get(0));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "¡All storages saved!");

            // Plugin shutdown logic
        }
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

    public Manager getBlockManager() {
        return blockManager;
    }

    public boolean isLoadedPlaceHolderApi() {
        return placeHolderApi;
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

    public PanelsManager getPanelsManager() {
        return panelsManager;
    }

    public RegionManagerStorage getRegionManagerStorage() {
        return regionManagerStorage;
    }

    public PluginSelectorManager getPluginSelectorManager() {
        return pluginSelectorManager;
    }

    public PluginSelectorManager.PluginSelected getPluginSelected() {
        return pluginSelected;
    }

    public Adapter getAdapter() {
        return adapter;
    }

    public MythicMobsManager getMythicMobsManager() {
        return mythicMobsManager;
    }
}
