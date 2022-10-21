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
        if(!config.contains("messages.EditorModeDesactivedMessage")){
            config.addDefault("messages.EditorModeDesactivedMessage", "&8[&6StorageMechanics&8] -> &cEditor mode deactivated!");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("messages.message_editor_maxRows")){
            config.addDefault("messages.message_editor_maxRows", "&8[&6StorageMechanics&8] -> &cError, max 6 rows!");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("config.BossbarText")){
            config.addDefault("config.BossbarText", "Editor mode");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("config.Bossbarpages")){
            config.addDefault("config.Bossbarpages", "Pages: ");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("config.Bossbarslots")){
            config.addDefault("config.Bossbarslots", "Slots:");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("config.BarColor")){
            config.addDefault("config.BarColor", "BLUE");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("config.BarStyle")){
            config.addDefault("config.BarStyle", "SOLID");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("config.BarAddedColor")){
            config.addDefault("config.BarAddedColor", "GREEN");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("config.BarAddedStyle")){
            config.addDefault("config.BarAddedStyle", "SOLID");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("messages.CorrectUsage")){
            config.addDefault("messages.CorrectUsage", "&8[&6StorageMechanics&8] -> &cError command");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("messages.InEditorMode")){
            config.addDefault("messages.InEditorMode","&8[&6StorageMechanics&8] -> &aEditor mode activated");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("messages.added")){
            config.addDefault("messages.added","&8[&6StorageMechanics&8] -> &9New block added!");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("config.BossbarAdded")){
            config.addDefault("config.BossbarAdded","New block added: ");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("config.Bossbarremoved")){
            config.addDefault("config.Bossbarremoved","New block removed: ");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("config.BarremovedColor")){
            config.addDefault("config.BarremovedColor", "RED");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("config.BarremovedStyle")){
            config.addDefault("config.BarremovedStyle", "SOLID");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("messages.NotInList")){
            config.addDefault("messages.NotInList", "&8[&6StorageMechanics&8] -> &2This block not in list! &a");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("messages.AlreadyInList")){
            config.addDefault("messages.AlreadyInList", "&8[&6StorageMechanics&8] -> &2This block already in list! &a");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("config.timetosavestorageinMINUTES")){
            config.addDefault("config.timetosavestorageinMINUTES", 60L);
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("config.InventoryPanelInfo")){
            config.addDefault("config.InventoryPanelInfo", "&aPanel Info");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("config.InventoryPanelInfoSound")){
            config.addDefault("config.InventoryPanelInfoSound", "none");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("config.InventoryPanelInfoItem")){
            config.addDefault("config.InventoryPanelInfoItem", "&6General info");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("config.InventoryPanelInfoItemUpdate")){
            config.addDefault("config.InventoryPanelInfoItemUpdate", "&6Refresh");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("messages.loadAllCommand")){
            config.addDefault("messages.loadAllCommand", "&8[&6StorageMechanics&8] -> &cConfig has been loaded!");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("config.CustomModelDataBlackGlass")){
            config.addDefault("config.CustomModelDataBlackGlass", 100000);
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("config.NextPageItem")){
            config.addDefault("config.NextPageItem", "mcicons:icon_next_orange");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("config.BackPageItem")){
            config.addDefault("config.BackPageItem", "mcicons:icon_back_orange");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("config.ChestSortItem")){
            config.addDefault("config.ChestSortItem", "mcicons:icon_arrow_chest");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("config.SearchItem")){
            config.addDefault("config.SearchItem", "mcicons:icon_search");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("messages.SearchSystemChat")){
            config.addDefault("messages.SearchSystemChat", "&8[&6StorageMechanics&8] -> &2Enter a valid page!");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("messages.storageNotExist")){
            config.addDefault("messages.storageNotExist", "&8[&6StorageMechanics&8] -> &cThe storage you are trying to open does not exist!");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("messages.PageNotExist")){
            config.addDefault("messages.PageNotExist", "&8[&6StorageMechanics&8] -> &cThat page does not exist!");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("messages.NotItemInHand")){
            config.addDefault("messages.NotItemInHand", "&8[&6StorageMechanics&8] -> &cYou don't have any items in hand!");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("config.MaxDistanceOpenStorage")){
            config.addDefault("config.MaxDistanceOpenStorage", 6);
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("messages.MaxDistanceOpenStorage")){
            config.addDefault("messages.MaxDistanceOpenStorage", "&8[&6StorageMechanics&8] -> &cYou can't open the storage at that distance, get a little closer and try again");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("items.InterfaceEditItem")){
            config.addDefault("items.InterfaceEditItem", "&6Edit interface");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("items.InterfaceEditLore")){
            config.addDefault("items.InterfaceEditLore", "&cIf you want to delete the interface just");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("items.InterfaceEditLore2")){
            config.addDefault("items.InterfaceEditLore2", "&cdon't add anything and it will be deleted");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("messages.EnterAsound")){
            config.addDefault("messages.EnterAsound", "&8[&6StorageMechanics&8] -> &cEnter a sound, example: techmc:music.dakiti or minecraft:music.end");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("messages.SoundEstablished")){
            config.addDefault("messages.SoundEstablished", "&8[&6StorageMechanics&8] -> &aThe sound has been established: &b");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }
        if(!config.contains("messages.ItemsInStorage")){
            config.addDefault("messages.ItemsInStorage", "&8[&6StorageMechanics&8] -> &cThere are still items in storage");
            Bukkit.getScheduler().runTaskLater(core,() -> core.saveConfig(), 10);
        }

        config.options().copyDefaults(true);
    }

}
