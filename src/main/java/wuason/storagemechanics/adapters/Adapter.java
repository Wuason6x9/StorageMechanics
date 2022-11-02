package wuason.storagemechanics.adapters;

import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.CustomStack;
import io.th0rgal.oraxen.items.ItemBuilder;
import io.th0rgal.oraxen.items.OraxenItems;
import io.th0rgal.oraxen.mechanics.provided.gameplay.noteblock.NoteBlockMechanicListener;
import io.th0rgal.oraxen.mechanics.provided.gameplay.stringblock.StringBlockMechanicListener;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import wuason.storagemechanics.Storage;

public class Adapter {
    private final Storage core;
    private final PluginSelectorManager.PluginSelected pluginSelected;

    public Adapter(Storage plugin, PluginSelectorManager.PluginSelected ps){

        this.core = plugin;
        this.pluginSelected = ps;

    }
    public Block getCustomBlockAtLocation(Location location){

        Block block = location.getBlock();

        if(pluginSelected.equals(PluginSelectorManager.PluginSelected.ITEMSADDER)){

            if(CustomBlock.byAlreadyPlaced(block) != null) return block;

        }
        else if (pluginSelected.equals(PluginSelectorManager.PluginSelected.ORAXEN)){


            if(NoteBlockMechanicListener.getNoteBlockMechanic(block) != null || StringBlockMechanicListener.getStringMechanic(block) != null) return block;

        }

        return null;

    }
    public boolean isCustomBlock(Block block){

        if(pluginSelected.equals(PluginSelectorManager.PluginSelected.ITEMSADDER)){

            if(CustomBlock.byAlreadyPlaced(block) != null) return true;

        }
        else if (pluginSelected.equals(PluginSelectorManager.PluginSelected.ORAXEN)){


            if(NoteBlockMechanicListener.getNoteBlockMechanic(block) != null || StringBlockMechanicListener.getStringMechanic(block) != null) return true;

        }

        return false;

    }
    public boolean isCustomItem(ItemStack itemStack){

        if(pluginSelected.equals(PluginSelectorManager.PluginSelected.ITEMSADDER)){

            if(CustomStack.byItemStack(itemStack) != null) return true;

        }
        else if (pluginSelected.equals(PluginSelectorManager.PluginSelected.ORAXEN)){

            if(OraxenItems.getIdByItem(itemStack) != null) return true;

        }

        return false;

    }
    public String getIDBlock(Block block){

        if(pluginSelected.equals(PluginSelectorManager.PluginSelected.ITEMSADDER)){

            if(CustomBlock.byAlreadyPlaced(block) != null) return CustomBlock.byAlreadyPlaced(block).getNamespacedID();

        }
        else if (pluginSelected.equals(PluginSelectorManager.PluginSelected.ORAXEN)){

            if(NoteBlockMechanicListener.getNoteBlockMechanic(block) != null) return NoteBlockMechanicListener.getNoteBlockMechanic(block).getItemID();
            if(StringBlockMechanicListener.getStringMechanic(block) != null) return StringBlockMechanicListener.getStringMechanic(block).getItemID();

        }

        return null;

    }

    public ItemStack getItemStackByInstance(String NameSpaceID){

        if(pluginSelected.equals(PluginSelectorManager.PluginSelected.ITEMSADDER)){

            CustomStack customStack = CustomStack.getInstance(NameSpaceID);

            return customStack.getItemStack();

        }
        else if (pluginSelected.equals(PluginSelectorManager.PluginSelected.ORAXEN)){

            ItemBuilder itemBuilder = OraxenItems.getItemById(NameSpaceID);

            return itemBuilder.build();

        }

        return null;

    }
}
