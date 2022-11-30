package wuason.storagemechanics.Storages.Mechanics.integrated.Hopper;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Hopper;
import org.bukkit.inventory.Inventory;
import org.bukkit.util.Vector;
import wuason.storagemechanics.Storage;
import wuason.storagemechanics.Storages.StorageManager;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class HopperUtils {

    public static boolean isHopper(Inventory inventory){

        try{

            Hopper hopper = (Hopper) inventory.getHolder();

            return true;

        } catch (Exception e) {

            return false;

        }

    }

    public static boolean existStorage(Location loc) {
        Storage core = Storage.getInstance();
        StorageManager manager = core.getStorageManager();
        String id = core.getStorageUtils().getLocationStorageID(loc);

        if(manager.existStorageByID(id)) return true;
        else if (manager.existStorageJson(id)){

            try {
                manager.loadStorage(id);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            return true;

        }
        return false;
    }

    public static boolean HopperHasSpace(Inventory inventory){
        for(int i=0;i<inventory.getSize();i++){
            if(inventory.getItem(i) == null || inventory.getItem(i).getType().equals(Material.AIR)){
                return true;
            }
        }
        return false;
    }

    public static Inventory getHopperInventory(Block block){

        Hopper hopper = (Hopper) block.getState();

        return hopper.getInventory();
    }
    public static ArrayList<Block> getHopperBlocksStorage(Block blockStorage){

        if(Storage.getInstance().getAdapter().isCustomBlock(blockStorage)) {

            if(HopperUtils.existStorage(blockStorage.getLocation())){

                Block[] blocks = {blockStorage.getLocation().getWorld().getBlockAt(blockStorage.getLocation().getBlockX() + 1,blockStorage.getLocation().getBlockY(),blockStorage.getLocation().getBlockZ()),blockStorage.getLocation().getWorld().getBlockAt(blockStorage.getLocation().getBlockX() - 1,blockStorage.getLocation().getBlockY(),blockStorage.getLocation().getBlockZ()),blockStorage.getLocation().getWorld().getBlockAt(blockStorage.getLocation().getBlockX(),blockStorage.getLocation().getBlockY() + 1,blockStorage.getLocation().getBlockZ()),blockStorage.getLocation().getWorld().getBlockAt(blockStorage.getLocation().getBlockX(),blockStorage.getLocation().getBlockY(),blockStorage.getLocation().getBlockZ() + 1),blockStorage.getLocation().getWorld().getBlockAt(blockStorage.getLocation().getBlockX(),blockStorage.getLocation().getBlockY(),blockStorage.getLocation().getBlockZ() - 1)};
                ArrayList<Block> hoppers = new ArrayList<>();
                for(Block block : blocks){

                    if(block.getType().equals(Material.HOPPER)) hoppers.add(block);

                }
                if(!hoppers.isEmpty()) return hoppers;
            }

        }

        return null;

    }
    public static Block getStorageBlockUpHopper(Block hopper){

        if(hopper.getType().equals(Material.HOPPER)){

            Location loc = hopper.getLocation();

            Block customBlock = loc.getWorld().getBlockAt(loc.getBlockX(),loc.getBlockY() + 1,loc.getBlockZ());

            if(Storage.getInstance().getAdapter().isCustomBlock(customBlock)){

                if(HopperUtils.existStorage(customBlock.getLocation())){

                    return customBlock;

                }

            }

        }
        return null;

    }
    public static Block getHopperBlockDownStorage(Block blockStorage){

        if(Storage.getInstance().getAdapter().isCustomBlock(blockStorage)) {

            if(HopperUtils.existStorage(blockStorage.getLocation())){
                Block hopper = blockStorage.getLocation().getWorld().getBlockAt(blockStorage.getLocation().getBlockX(),blockStorage.getLocation().getBlockY() - 1,blockStorage.getLocation().getBlockZ());
                if(hopper.getType().equals(Material.HOPPER)) return hopper;
            }

        }

        return null;

    }

    public static Block getHopperBlock(Inventory inventory){

        Hopper hopper = (Hopper) inventory.getHolder();

        Block block = hopper.getBlock();

        return block;

    }

    public static Block getBlockDirection(Inventory inventory){


        Hopper hopper = (Hopper) inventory.getHolder();

        if(hopper == null) return null;

        Block block = hopper.getBlock();

        org.bukkit.block.data.type.Hopper blockData = (org.bukkit.block.data.type.Hopper) hopper.getBlockData();

        Vector vector = blockData.getFacing().getDirection();

        Location loc = block.getLocation();

        return loc.getWorld().getBlockAt(loc.add(vector.getX(),vector.getY(),vector.getZ()));

    }

}
