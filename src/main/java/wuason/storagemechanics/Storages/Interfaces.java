package wuason.storagemechanics.Storages;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import wuason.storagemechanics.BlockManager.Manager;
import wuason.storagemechanics.Storage;
import wuason.storagemechanics.panels.Panel;

import java.util.Arrays;
import java.util.Collections;

public class Interfaces {

    private Storage core;

    public Interfaces(Storage plugin){

        this.core = plugin;

    }

    public Panel openEditor(Player player, String NameSpaceID){

        Manager blockManager = core.getBlockManager();

        Panel panel = core.getPanelsManager().createPanel(3, NameSpaceID, player, "editor_" + player.getName() + "_" + NameSpaceID);

        int[] slots = {0,1,2,3,4,5,6,7,8,9,11,13,15,17,18,19,20,21,22,23,24,25,26};

        for(int slot : slots){

            panel.getInventory().setItem(slot, core.getStorageUtils().createItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)," ",null));

        }


        if(blockManager.getOpenSound(NameSpaceID) == null){

            panel.getInventory().setItem(10, core.getStorageUtils().createItem(new ItemStack(Material.REDSTONE_BLOCK),ChatColor.translateAlternateColorCodes('&',"&c[DISABLED] Open Sound"), Collections.singletonList(ChatColor.translateAlternateColorCodes('&', "&anone"))));

        }
        else {

            panel.getInventory().setItem(10, core.getStorageUtils().createItem(new ItemStack(Material.EMERALD_BLOCK),ChatColor.translateAlternateColorCodes('&',"&a[ENABLED] Open Sound"), Collections.singletonList(ChatColor.translateAlternateColorCodes('&', "&a" + blockManager.getOpenSound(NameSpaceID)))));

        }
        if(blockManager.getCloseSound(NameSpaceID) == null){

            panel.getInventory().setItem(12, core.getStorageUtils().createItem(new ItemStack(Material.REDSTONE_BLOCK),ChatColor.translateAlternateColorCodes('&',"&c[DISABLED] Close Sound"), Collections.singletonList(ChatColor.translateAlternateColorCodes('&', "&anone"))));

        }
        else {

            panel.getInventory().setItem(12, core.getStorageUtils().createItem(new ItemStack(Material.EMERALD_BLOCK),ChatColor.translateAlternateColorCodes('&',"&a[ENABLED] Close Sound"), Collections.singletonList(ChatColor.translateAlternateColorCodes('&', "&a" + blockManager.getCloseSound(NameSpaceID)))));

        }

        if(!blockManager.isBreakable(NameSpaceID)){

            panel.getInventory().setItem(14, core.getStorageUtils().createItem(new ItemStack(Material.REDSTONE_BLOCK),ChatColor.translateAlternateColorCodes('&',"&c[DISABLED] Breakable"), null));

        }
        else {

            panel.getInventory().setItem(14, core.getStorageUtils().createItem(new ItemStack(Material.EMERALD_BLOCK),ChatColor.translateAlternateColorCodes('&',"&a[ENABLED] Breakable"), null));

        }

        panel.getInventory().setItem(16, core.getStorageUtils().createItem(new ItemStack(Material.CHEST), ChatColor.translateAlternateColorCodes('&',core.getConfig().getString("items.InterfaceEditItem")), Arrays.asList(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("items.InterfaceEditLore")), ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("items.InterfaceEditLore2")))));

        Bukkit.getScheduler().runTask(core, () -> player.openInventory(panel.getInventory()));

        return panel;

    }

    public Inventory OpenInterfaceEditorFirst(Player player, String NameSpaceID){

        Inventory inv = Bukkit.createInventory(player,core.getBlockManager().getSlots(NameSpaceID),NameSpaceID + "_" + player.getName() + "_" + "editor_interface_first");

        Bukkit.getScheduler().runTask(core,() -> player.openInventory(inv));

        return inv;

    }
    public Inventory OpenInterfaceEditorBetween(Player player, String NameSpaceID){

        Inventory inv = Bukkit.createInventory(player,core.getBlockManager().getSlots(NameSpaceID),NameSpaceID + "_" + player.getName() + "_" + "editor_interface_between");

        Bukkit.getScheduler().runTask(core,() -> player.openInventory(inv));

        return inv;

    }
    public Inventory OpenInterfaceEditorLast(Player player, String NameSpaceID){

        Inventory inv = Bukkit.createInventory(player,core.getBlockManager().getSlots(NameSpaceID),NameSpaceID + "_" + player.getName() + "_" + "editor_interface_last");

        Bukkit.getScheduler().runTask(core,() -> player.openInventory(inv));

        return inv;

    }

}
