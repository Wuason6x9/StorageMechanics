package wuason.storagemechanics;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import wuason.storagemechanics.adapters.PluginSelectorManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConfigManager {

    private Storage core;

    public ConfigManager(Storage plugin){
        this.core = plugin;

        config();
    }


    public void config(){
        FileConfiguration config = core.getConfig();

        if(core.getPluginSelected().equals(PluginSelectorManager.PluginSelected.ORAXEN)){

            InputStream inputStream = core.getResource("config_oraxen.yml");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            try {
                config.load(inputStreamReader);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }

            core.saveConfig();


        } else if (core.getPluginSelected().equals(PluginSelectorManager.PluginSelected.ITEMSADDER)) {

            InputStream inputStream = core.getResource("config_ia.yml");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            try {
                config.load(inputStreamReader);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }

            core.saveConfig();

        }
        config.options().copyDefaults(true);
    }

}
