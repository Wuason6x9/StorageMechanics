package wuason.storagemechanics.BlockGestor;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import wuason.storagemechanics.Storage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class GestorYaml {

    private Storage core;

    public GestorYaml(Storage plugin){

        this.core = plugin;

        File file = new File(core.getDataFolder().getPath() + "/config/");

        if(!file.exists()){
            file.mkdirs();
        }


    }

    public void loadConfig(){

        File[] files = new File(core.getDataFolder().getPath() + "/config/").listFiles();
        if(files.length > 0){

            ArrayList<File> filesToDelete = new ArrayList<>();
            YamlConfiguration yaml;


            for(File f : files){

                yaml = YamlConfiguration.loadConfiguration(f);
                core.getBlockManager().addNameSpaceID(yaml.getString("NameSpaceID"),(byte)(yaml.getInt("rows") * 9),yaml.getString("title"),yaml.getBoolean("isShulker"), yaml.getInt("pags"));
                filesToDelete.add(f);

            }

            while (!filesToDelete.isEmpty()){

                filesToDelete.get(0).delete();
                filesToDelete.remove(0);

            }

        }
    }

}
