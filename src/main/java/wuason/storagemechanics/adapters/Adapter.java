package wuason.storagemechanics.adapters;

import dev.lone.itemsadder.api.CustomStack;
import io.th0rgal.oraxen.items.ItemBuilder;
import io.th0rgal.oraxen.items.OraxenItems;
import org.bukkit.inventory.ItemStack;
import wuason.storagemechanics.Storage;

public class Adapter {
    private final Storage core;
    private final PluginSelectorManager.PluginSelected pluginSelected;

    public Adapter(Storage plugin, PluginSelectorManager.PluginSelected ps){

        this.core = plugin;
        this.pluginSelected = ps;

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
