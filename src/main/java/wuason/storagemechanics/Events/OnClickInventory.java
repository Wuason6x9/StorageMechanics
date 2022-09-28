package wuason.storagemechanics.Events;

import com.google.gson.Gson;
import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import wuason.storagemechanics.Storage;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

public class OnClickInventory implements Listener {


    @EventHandler(priority = EventPriority.NORMAL)
    public void click(InventoryClickEvent event) throws IOException {
        File file = new File(Storage.getInstance().getDataFolder() + "/data/test.json");
        Writer writer = new FileWriter(file,false);
        Gson gson = new Gson();
        NBTContainer nbtItem = NBTItem.convertItemtoNBT(event.getCurrentItem());
        test tst = new test(nbtItem.toString());
        event.getWhoClicked().sendMessage(ChatColor.BLUE + nbtItem.toString());
        gson.toJson(tst, writer);
        writer.flush();
        writer.close();

        event.getWhoClicked().sendMessage(ChatColor.YELLOW + nbtItem.toString());
        int slot = event.getSlot();
        Reader reader = new FileReader(file);
        Bukkit.getScheduler().runTaskLater(Storage.getInstance(),() -> {
            test tss = gson.fromJson(reader, test.class);
            ArrayList<String> dataItem = tss.getTest();
            System.out.println(ChatColor.YELLOW + dataItem.toString());
            event.getInventory().setItem(slot, NBTItem.convertNBTtoItem(new NBTContainer(dataItem.get(0))));

        }, 20L * 3L);
    }
}
