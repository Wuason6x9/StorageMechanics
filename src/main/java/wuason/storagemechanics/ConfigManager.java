package wuason.storagemechanics;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private Storage core;

    public ConfigManager(Storage plugin){
        this.core = plugin;

        config();
    }


    public void config(){
        FileConfiguration config = core.getConfig();
        if(!config.contains("EditorModeDesactivedMessage")){
            config.addDefault("EditorModeDesactivedMessage", "&8[&6StorageMechanics&8] -> &cEditor mode deactivated!");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("message_editor_maxRows")){
            config.addDefault("message_editor_maxRows", "&8[&6StorageMechanics&8] -> &cError, max 6 rows!");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("BossbarText")){
            config.addDefault("BossbarText", "Editor mode");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("Bossbarpages")){
            config.addDefault("Bossbarpages", "Pages: ");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("Bossbarslots")){
            config.addDefault("Bossbarslots", "Slots:");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("BarColor")){
            config.addDefault("BarColor", "BLUE");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("BarStyle")){
            config.addDefault("BarStyle", "SOLID");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("BarAddedColor")){
            config.addDefault("BarAddedColor", "GREEN");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("BarAddedStyle")){
            config.addDefault("BarAddedStyle", "SOLID");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("CorrectUsage")){
            config.addDefault("CorrectUsage", "&8[&6StorageMechanics&8] -> &cError command");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("InEditorMode")){
            config.addDefault("InEditorMode","&8[&6StorageMechanics&8] -> &aEditor mode activated");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("added")){
            config.addDefault("added","&8[&6StorageMechanics&8] -> &9¡New block added!");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("BossbarAdded")){
            config.addDefault("BossbarAdded","New block added: ");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("Bossbarremoved")){
            config.addDefault("Bossbarremoved","New block removed: ");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("BarremovedColor")){
            config.addDefault("BarremovedColor", "RED");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("BarremovedStyle")){
            config.addDefault("BarremovedStyle", "SOLID");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("NotInList")){
            config.addDefault("NotInList", "&8[&6StorageMechanics&8] -> &2¡This block not in list! &a");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("AlreadyInList")){
            config.addDefault("AlreadyInList", "&8[&6StorageMechanics&8] -> &2¡This block already in list! &a");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("timetosavestorageinMINUTES")){
            config.addDefault("timetosavestorageinMINUTES", 60L);
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("InventoryPanelInfo")){
            config.addDefault("InventoryPanelInfo", "&aPanel Info");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("InventoryPanelInfoSound")){
            config.addDefault("InventoryPanelInfoSound", "none");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("InventoryPanelInfoItem")){
            config.addDefault("InventoryPanelInfoItem", "&6General info");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("InventoryPanelInfoItemUpdate")){
            config.addDefault("InventoryPanelInfoItemUpdate", "&6Refresh");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("loadAllCommand")){
            config.addDefault("loadAllCommand", "&8[&6StorageMechanics&8] -> &c¡Config has been loaded!");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("CustomModelDataBlackGlass")){
            config.addDefault("CustomModelDataBlackGlass", 100000);
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("NextPageItem")){
            config.addDefault("NextPageItem", "mcicons:icon_next_orange");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("BackPageItem")){
            config.addDefault("BackPageItem", "mcicons:icon_back_orange");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("ChestSortItem")){
            config.addDefault("ChestSortItem", "mcicons:icon_arrow_chest");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        config.options().copyDefaults(true);
    }

}
