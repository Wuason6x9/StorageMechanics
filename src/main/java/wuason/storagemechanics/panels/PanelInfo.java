package wuason.storagemechanics.panels;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import wuason.storagemechanics.Storage;
import wuason.storagemechanics.info.InfoGeneral;

import java.io.FileNotFoundException;

public class PanelInfo implements Listener {

    private Storage core;
    private Inventory inv;
    private Player player;

    private final String openPerm = "storage.admin.panelinfo";

    public PanelInfo(Storage plugin, Player p){

        this.core = plugin;
        this.player = p;
        load();

    }

    public void load(){

        inv = Bukkit.createInventory(player, InventoryType.DROPPER, ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("config.InventoryPanelInfo")));
        loadItems();

    }



    //OPEN INVENTORY

    public void openPanelAdmin(){

        if(player.hasPermission(openPerm)){

            String sound = core.getConfig().getString("config.InventoryPanelInfoSound");

            if(!sound.equals("none")){

                player.playSound(player.getLocation(),sound,100f,1f);

            }

            player.openInventory(inv);

        }

    }

    public void action(InventoryClickEvent event){

        switch (event.getSlot()){
            case 1:

                try {
                    core.getInfoManager().updateInfo();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

                break;

            case 4:
                event.getWhoClicked().sendMessage("");

                event.getWhoClicked().sendMessage(ChatColor.DARK_GRAY + "(----------Info General----------)");

                for(InfoGeneral info : core.getInfoManager().getList()){

                    event.getWhoClicked().sendMessage(ChatColor.GOLD + "(NameSpaceID: " + info.getNameSpaceID() + ")");
                    event.getWhoClicked().sendMessage(ChatColor.GOLD + "(Count: " + info.getCount() + ")");
                    event.getWhoClicked().sendMessage("");

                }
                event.getWhoClicked().sendMessage(ChatColor.DARK_GRAY + "(-------------------------------)");
                event.getWhoClicked().sendMessage("");
                break;

        }

    }

    public void loadItems(){
        //book item info general
        ItemStack itemBook = new ItemStack(Material.BOOK);
        ItemMeta itemBookMeta = itemBook.getItemMeta();
        itemBookMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("config.InventoryPanelInfoItem")));
        itemBook.setItemMeta(itemBookMeta);
        //Paneles

        int[] slotsNull = {0,2,3,5,6,7,8};

        ItemStack itemBlackPanel = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta itemBlackPanelMeta = itemBlackPanel.getItemMeta();
        itemBlackPanelMeta.setDisplayName(" ");
        itemBlackPanel.setItemMeta(itemBlackPanelMeta);

        //set items
        for(int i : slotsNull){

            inv.setItem(i, itemBlackPanel);

        }
        inv.setItem(4, itemBook);
        inv.setItem(1, core.getStorageUtils().createItem(new ItemStack(Material.BLUE_CONCRETE),ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("config.InventoryPanelInfoItemUpdate")),null));

    }

    @EventHandler
    public void onClick(final InventoryClickEvent event){

        if(event.getWhoClicked().getOpenInventory().getTitle().equals(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("config.InventoryPanelInfo")))){
            if(event.getInventory().getType().equals(InventoryType.DROPPER)){

                event.setCancelled(true);
                action(event);

            }
        }
    }

}