package wuason.storagemechanics;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import wuason.storagemechanics.BossBar.BossBarManager;
import wuason.storagemechanics.Commands.CommandManager;
import wuason.storagemechanics.Events.EventsManager;
import wuason.storagemechanics.Storages.StorageManager;

import java.io.File;

public final class Storage extends JavaPlugin {

    private CommandManager commandManager;
    private EventsManager eventsManager;
    private StorageManager storageManager;
    private BossBarManager bossBarManager;
    private static Storage instance = null;

    public Storage(){
        if(instance==null) {
            instance = this;
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Instancia Cargada!");
        }
    }

    @Override
    public void onEnable() {
        commandManager = new CommandManager(this);
        storageManager = new StorageManager(this);
        bossBarManager = new BossBarManager(this);
        eventsManager = new EventsManager(this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "" + this.getDataFolder().getPath());
        config();
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


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

    public void config(){
        FileConfiguration config = this.getConfig();

        File file = new File(this.getDataFolder() + "/data/cache.json");



        if(!file.exists()){
            file.getParentFile().mkdir();
        }

        if(!config.contains("mode_cpu")){
            config.addDefault("guild", true);
            Bukkit.getScheduler().runTaskLater(this,() -> this.saveConfig(), 10);
        }
        config.options().copyDefaults(true);
    }
}
