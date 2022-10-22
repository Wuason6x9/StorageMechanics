package wuason.storagemechanics.adapters;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import wuason.storagemechanics.Storage;

public class PluginSelectorManager {
    private final Storage core;
    private PluginSelected pluginSelected;

    public PluginSelectorManager(Storage plugin){

        this.core = plugin;

    }

    public PluginSelected SelectPlugin(){

        Plugin ItemsAdder = Bukkit.getPluginManager().getPlugin("ItemsAdder");
        Plugin Oraxen = Bukkit.getPluginManager().getPlugin("Oraxen");
        if(Oraxen != null && ItemsAdder != null){

            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&8[&6StorageMechanics&8] -> &cThe plugin cannot work with ItemsAdder and Oraxen at the same time!"));
            core.getPluginLoader().disablePlugin(core);

        }

        if(ItemsAdder != null){

            pluginSelected = PluginSelected.ITEMSADDER;
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&8[&6StorageMechanics&8] -> &bPlugin selected ItemsAdder"));
            return pluginSelected;

        } else if (Oraxen != null) {

            pluginSelected = PluginSelected.ORAXEN;
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&8[&6StorageMechanics&8] -> &bPlugin selected Oraxen"));
            return pluginSelected;

        }

        else {

            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&8[&6StorageMechanics&8] -> &cNo Oraxen or Itemsadder detected!"));
            core.getPluginLoader().disablePlugin(core);

        }
        return null;

    }

    public enum PluginSelected {

        ITEMSADDER,
        ORAXEN

    }
}
