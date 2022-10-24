package wuason.storagemechanics.mythic;

import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import wuason.storagemechanics.Storage;

public class MythicMobsManager {

    private Storage core;
    private MythicBukkit mythicBukkit = null;

    public MythicMobsManager(Storage plugin){

        this.core = plugin;

        if(Bukkit.getPluginManager().getPlugin("MythicMobs") != null){

            mythicBukkit = MythicBukkit.inst();
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6StorageMechanics&8] -> &bMythicMobs [LOADED]"));
            loadEvents();

        }


    }

    public boolean existMythicMobs(){

        if(mythicBukkit != null) return true;

        return false;

    }

    public boolean loadEvents(){

        if(existMythicMobs()){

            PluginManager pm = Bukkit.getPluginManager();
            return true;

        }
        return false;
    }
}
