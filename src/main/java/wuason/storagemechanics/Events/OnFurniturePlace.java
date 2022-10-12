package wuason.storagemechanics.Events;

import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTItem;
import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.Events.FurniturePlaceSuccessEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wuason.storagemechanics.Storage;

import java.io.IOException;
import java.util.ArrayList;

public class OnFurniturePlace implements Listener {

    private Storage core;
    ArrayList<ArrayList<String>> handItem = new ArrayList<>();

    public OnFurniturePlace(Storage plugin){
        this.core = plugin;
    }



    @EventHandler(priority = EventPriority.NORMAL)
    public void onInteract(PlayerInteractEvent event){

        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){

            ItemStack hand = event.getItem();
            CustomStack stack = CustomStack.byItemStack(hand);

            if(stack != null){

                ArrayList<String> k = new ArrayList<>();

                k.add(event.getPlayer().getName());

                k.add(NBTItem.convertItemtoNBT(hand).toString());

                handItem.add(k);

                Bukkit.getScheduler().runTaskLater(core,() -> handItem.remove(k),2L);

            }

        }

    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlace(FurniturePlaceSuccessEvent event) throws IOException {

        String NameSpaceID = event.getNamespacedID();

        if(core.getBlockManager().existNameSpaceID(NameSpaceID)){

            Player player = event.getPlayer();


            ItemStack itemInHand = getItem(player);
            ItemMeta itemMetaHand = itemInHand.getItemMeta();
            NamespacedKey key = new NamespacedKey(core, "storageid");
            String uuid = itemMetaHand.getPersistentDataContainer().get(key, PersistentDataType.STRING);

            if(uuid != null){

                Location loc = event.getBukkitEntity().getLocation();

                String id  = core.getStorageUtils().getLocationStorageID(loc);

                core.getStorageUtils().storageUUIDtoID(id, uuid);

            }

        }


    }

    public ItemStack getItem(Player player){
        ArrayList<ArrayList<String>> k = handItem;
        for(ArrayList<String> i : k){

            if(i.get(0).equals(player.getName())){

                ItemStack item = NBTItem.convertNBTtoItem(new NBTContainer(i.get(1)));

                handItem.remove(i);

                return item;

            }

        }
        return null;
    }

}
