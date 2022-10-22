package wuason.storagemechanics;

import com.sun.tools.javac.jvm.Items;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitTask;
import wuason.storagemechanics.Editor.PlayerEditorMode;
import wuason.storagemechanics.BossBar.Bar;
import wuason.storagemechanics.Storages.StorageManager;
import wuason.storagemechanics.Storages.itemmodify.ItemModifyManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class StorageUtils {
    private static Storage core;
    private static ArrayList<ArrayList<String>> tasks = new ArrayList<>();


    public static void addTask(String taskID, String ID){
        ArrayList<String> k = new ArrayList<>();
        k.add(taskID);
        k.add(ID);
        tasks.add(k);
    }
    public static boolean deleteTask(String ID){
        for(ArrayList<String> k : tasks){
            if(k.get(1).equals(ID)){
                Bukkit.getScheduler().cancelTask(Integer.parseInt(k.get(0)));
                tasks.remove(k);
                return true;
            }
        }
        return false;
    }
    public static boolean existTask(String ID){
        for(ArrayList<String> k : tasks){
            if(k.get(1).equals(ID)){
                return true;
            }
        }
        return false;
    }


    public StorageUtils(Storage plugin){
        this.core = plugin;
    }

    public static String getFilePath(){
        return core.getDataFolder().getPath();
    }

    public String Encode(String text){
        return Base64.getEncoder().encodeToString(text.getBytes());
    }
    public String Decode(String codified){
        return new String(Base64.getDecoder().decode(codified));
    }

    public Boolean isLong(String number){
        try {
            long n = Long.parseLong(number);
        }
        catch (NumberFormatException e){
            return false;
        }
        return true;
    }
    public Boolean isNumber(String number){
        try {
            int n = Integer.parseInt(number);
        }
        catch (NumberFormatException e){
            return false;
        }
        return true;
    }
    public Boolean isBool(String bool){
        try {
            boolean k = Boolean.parseBoolean(bool.toLowerCase());
        }
        catch (IllegalArgumentException e){
            return false;
        }
        return true;
    }

    public String getLocationStorageID(Location loc){
        return loc.getWorld().getName() + "_" + loc.getX() + "_" + loc.getY() + "_" + loc.getZ();
    }

    public void AddedBlock(PlayerEditorMode info, String id){

        String NameSpaceID = id + "_added";
        if(!existTask(NameSpaceID)){

            Bar bar = core.getBossBarManager().createBosbbar(info.getPlayer(), ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("config.BossbarAdded") + id), BarColor.valueOf(core.getConfig().getString("config.BarAddedColor")), BarStyle.valueOf(core.getConfig().getString("config.BarAddedStyle")),100D, NameSpaceID);


            final double[] num = {100};
            BukkitTask task = Bukkit.getScheduler().runTaskTimerAsynchronously(core, () ->{
                if(num[0]<1){

                    core.getBossBarManager().RemoveBossbar(NameSpaceID, info.getPlayer());
                    deleteTask(NameSpaceID);
                }
                else {

                    bar.getBossBar().setProgress(num[0] / 100D);
                    num[0]--;

                }
            },1L,1L);
            addTask(task.getTaskId() + "", NameSpaceID);
        }
    }
    public void removedBlock(PlayerEditorMode info, String id){
        String NameSpaceID = id + "_removed";
        if(!existTask(NameSpaceID)){

            Bar bar = core.getBossBarManager().createBosbbar(info.getPlayer(), ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("config.Bossbarremoved") + id), BarColor.valueOf(core.getConfig().getString("config.BarremovedColor")), BarStyle.valueOf(core.getConfig().getString("config.BarremovedStyle")),100D, NameSpaceID);


            final double[] num = {100};
            BukkitTask task = Bukkit.getScheduler().runTaskTimerAsynchronously(core, () ->{
                if(num[0]<1){

                    core.getBossBarManager().RemoveBossbar(NameSpaceID, info.getPlayer());
                    deleteTask(NameSpaceID);
                }
                else {

                    bar.getBossBar().setProgress(num[0] / 100D);
                    num[0]--;

                }
            },1L,1L);
            addTask(task.getTaskId() + "", NameSpaceID);
        }
    }

    public boolean existUUIDStorage(String uuid){
        File file = new File(core.getDataFolder().getPath() + "/storages/shulker/" + uuid + ".json");


        if(file.exists()){
            return true;
        }

        return false;

    }

    public void storageUUIDtoID(String id, String uuid) throws IOException {

        File JsonUUID = new File(core.getDataFolder().getPath() + "/storages/blockstatic/");

        if(!JsonUUID.exists()){

            JsonUUID.mkdirs();

        }

        Files.move(Paths.get(core.getDataFolder().getPath() + "/storages/shulker/" + uuid + ".json"), Paths.get(core.getDataFolder().getPath() + "/storages/blockstatic/" + id + ".json"));


        core.getStorageManager().loadStorage(id);


    }
    public UUID storageIDtoUUID(String id) throws IOException {

        StorageManager blockManager = core.getStorageManager();

        UUID uuid = UUID.randomUUID();
        if(existUUIDStorage(uuid.toString())){
            uuid = UUID.randomUUID();
        }


        if(blockManager.existStorageByID(id)){

            boolean k = false;
            while (!k){

                k = core.getStorageManager().saveStorage(core.getStorageManager().getStorageMemory(id));

            }

            File JsonUUID = new File(core.getDataFolder().getPath() + "/storages/shulker/");

            if(!JsonUUID.exists()){

                JsonUUID.mkdirs();

            }

            Files.move(Paths.get(core.getDataFolder().getPath() + "/storages/blockstatic/" + id + ".json"), Paths.get(core.getDataFolder().getPath() + "/storages/shulker/" + uuid.toString() + ".json"));


            return uuid;


        }

        else {

            if(blockManager.existStorageJson(id)){

                File JsonUUID = new File(core.getDataFolder().getPath() + "/storages/shulker/");

                if(!JsonUUID.exists()){

                    JsonUUID.mkdirs();

                }

                Files.move(Paths.get(core.getDataFolder().getPath() + "/storages/blockstatic/" + id + ".json"), Paths.get(core.getDataFolder().getPath() + "/storages/shulker/" + uuid.toString() + ".json"));

                return uuid;

            }

        }
        return null;


    }

    public ItemStack createItem(ItemStack item, String displayname, List<String> lore){

        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(displayname);
        if(lore != null){
            itemMeta.setLore(lore);
        }
        item.setItemMeta(itemMeta);
        return item;

    }

    public ItemStack getItemModified(ItemStack i, ItemModifyManager.ItemsModification itemModify){

        itemModify = core.getStorageManager().getItemModifyManager().getItemModification(i);
        ItemStack item = core.getStorageManager().getItemModifyManager().removeModifyItem(i);
        ItemMeta meta = item.getItemMeta();

        switch (itemModify){

            case BLOCKED_ITEM:
                meta.getPersistentDataContainer().set(new NamespacedKey(core, "itemBlocked"), PersistentDataType.STRING, "blocked");
                meta.getPersistentDataContainer().set(new NamespacedKey(core, "blockedItem"), PersistentDataType.STRING, "blocked");
                break;

            case SEARCH_PAGE:
                meta.getPersistentDataContainer().set(new NamespacedKey(core,"searchItem"), PersistentDataType.STRING, "searchItem");
                meta.getPersistentDataContainer().set(new NamespacedKey(core, "itemBlocked"), PersistentDataType.STRING, "blocked");
                break;

            case SORT_ITEMS:
                meta.getPersistentDataContainer().set(new NamespacedKey(core,"chestsortItem"), PersistentDataType.STRING, "chestsort");
                meta.getPersistentDataContainer().set(new NamespacedKey(core, "itemBlocked"), PersistentDataType.STRING, "blocked");
                break;

            case NEXT_PAGE:
                meta.getPersistentDataContainer().set(new NamespacedKey(core,"nextItem"), PersistentDataType.STRING, "next");
                meta.getPersistentDataContainer().set(new NamespacedKey(core, "itemBlocked"), PersistentDataType.STRING, "blocked");
                break;

            case BACK_PAGE:
                meta.getPersistentDataContainer().set(new NamespacedKey(core,"backItem"), PersistentDataType.STRING, "back");
                meta.getPersistentDataContainer().set(new NamespacedKey(core, "itemBlocked"), PersistentDataType.STRING, "blocked");
                break;

        }

        item.setItemMeta(meta);

        return item;
    }
    public ItemStack getNextItem(){
        ItemStack item = core.getAdapter().getItemStackByInstance(core.getConfig().getString("config.NextPageItem"));
        ItemMeta meta = item.getItemMeta();

        meta.getPersistentDataContainer().set(new NamespacedKey(core,"nextItem"), PersistentDataType.STRING, "next");
        meta.getPersistentDataContainer().set(new NamespacedKey(core, "itemBlocked"), PersistentDataType.STRING, "blocked");

        item.setItemMeta(meta);

        return item;
    }
    public ItemStack getBackItem(){
        ItemStack item = core.getAdapter().getItemStackByInstance(core.getConfig().getString("config.BackPageItem"));
        ItemMeta meta = item.getItemMeta();

        meta.getPersistentDataContainer().set(new NamespacedKey(core,"backItem"), PersistentDataType.STRING, "back");
        meta.getPersistentDataContainer().set(new NamespacedKey(core, "itemBlocked"), PersistentDataType.STRING, "blocked");

        item.setItemMeta(meta);

        return item;
    }
    public ItemStack getChestSortItem(){

        ItemStack item = core.getAdapter().getItemStackByInstance(core.getConfig().getString("config.ChestSortItem"));

        ItemMeta meta = item.getItemMeta();

        meta.getPersistentDataContainer().set(new NamespacedKey(core,"chestsortItem"), PersistentDataType.STRING, "chestsort");
        meta.getPersistentDataContainer().set(new NamespacedKey(core, "itemBlocked"), PersistentDataType.STRING, "blocked");

        item.setItemMeta(meta);

        return item;
    }
    public ItemStack getSearchItem(){
        ItemStack item = core.getAdapter().getItemStackByInstance(core.getConfig().getString("config.SearchItem"));

        ItemMeta meta = item.getItemMeta();

        meta.getPersistentDataContainer().set(new NamespacedKey(core,"searchItem"), PersistentDataType.STRING, "searchItem");
        meta.getPersistentDataContainer().set(new NamespacedKey(core, "itemBlocked"), PersistentDataType.STRING, "blocked");

        item.setItemMeta(meta);

        return item;
    }

    public ItemStack getBlockItem(){

        ItemStack itemBlackPanel = core.getAdapter().getItemStackByInstance(core.getConfig().getString("config.BlockItem"));
        ItemMeta itemBlackPanelMeta = itemBlackPanel.getItemMeta();


        itemBlackPanelMeta.getPersistentDataContainer().set(new NamespacedKey(core, "blockedItem"), PersistentDataType.STRING, "blocked");
        itemBlackPanelMeta.getPersistentDataContainer().set(new NamespacedKey(core, "itemBlocked"), PersistentDataType.STRING, "blocked");
        itemBlackPanel.setItemMeta(itemBlackPanelMeta);

        return itemBlackPanel;

    }

    public void setBlockItems(int[] slots, Inventory inv){

        for(int l : slots){

            inv.setItem(l, getBlockItem());

        }

    }

    public boolean isSearchItem(ItemStack item){

        ItemMeta meta = item.getItemMeta();

        if(meta.getPersistentDataContainer().has(new NamespacedKey(core, "searchItem"), PersistentDataType.STRING)){

            return true;

        }
        return false;

    }
    public boolean isNextItem(ItemStack item){

        ItemMeta meta = item.getItemMeta();

        if(meta.getPersistentDataContainer().has(new NamespacedKey(core, "nextItem"), PersistentDataType.STRING)){

            return true;

        }
        return false;

    }
    public boolean isSortItem(ItemStack item){

        ItemMeta meta = item.getItemMeta();

        if(meta.getPersistentDataContainer().has(new NamespacedKey(core, "chestsortItem"), PersistentDataType.STRING)){

            return true;

        }
        return false;

    }

    public boolean isBackItem(ItemStack item){

        ItemMeta meta = item.getItemMeta();

        if(meta.getPersistentDataContainer().has(new NamespacedKey(core, "backItem"), PersistentDataType.STRING)){

            return true;

        }
        return false;

    }


    public boolean isBlockedItem(ItemStack item){

        ItemMeta meta = item.getItemMeta();

        if(meta.getPersistentDataContainer().has(new NamespacedKey(core, "itemBlocked"), PersistentDataType.STRING)){

            return true;

        }
        return false;

    }

}
