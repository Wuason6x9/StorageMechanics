package wuason.storagemechanics.Storages.itemmodify;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import wuason.storagemechanics.Storage;

public class ItemModifyManager {

    private Storage core;

    public ItemModifyManager(Storage plugin){
        this.core = plugin;
    }


    public ItemStack modifyItem(ItemStack itemStack, ItemsModification modify){

        ItemMeta meta = itemStack.getItemMeta();

        meta.getPersistentDataContainer().set(new NamespacedKey(core, "ItemModify"), PersistentDataType.STRING, modify.toString());

        itemStack.setItemMeta(meta);

        return itemStack;
    }
    public ItemStack removeModifyItem(ItemStack itemStack){

        ItemMeta meta = itemStack.getItemMeta();
        NamespacedKey namespacedKey = new NamespacedKey(core, "ItemModify");

        if(meta.getPersistentDataContainer().has(namespacedKey,PersistentDataType.STRING)){

            meta.getPersistentDataContainer().remove(namespacedKey);

        }

        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public boolean hasItemModification(ItemStack itemStack){

        ItemMeta meta = itemStack.getItemMeta();

        if(meta.getPersistentDataContainer().has(new NamespacedKey(core, "ItemModify"),PersistentDataType.STRING)){

            return true;

        }

        return false;

    }

    public ItemsModification getItemModification(ItemStack itemStack){

        ItemMeta meta = itemStack.getItemMeta();

        String itemModify = meta.getPersistentDataContainer().get(new NamespacedKey(core, "ItemModify"),PersistentDataType.STRING);

        return ItemsModification.valueOf(itemModify);

    }


    public enum ItemsModification{

        NEXT_PAGE,
        BACK_PAGE,
        SEARCH_PAGE,
        SORT_ITEMS,
        BLOCKED_ITEM

    }

}
