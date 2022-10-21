package wuason.storagemechanics.BlockManager;

import com.google.gson.Gson;
import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import wuason.storagemechanics.Storage;

import java.io.*;
import java.util.ArrayList;

public class Manager {

    private Storage core;
    private Gson gson;
    private ArrayList<ArrayList<ArrayList<String>>> list = new ArrayList<>();
    private ManagerYaml managerYaml;

    public Manager(Storage plugin) throws FileNotFoundException {
        this.core = plugin;
        this.gson = new Gson();
        this.managerYaml = new ManagerYaml(plugin);
        load();

    }

    public ArrayList<ArrayList<ArrayList<String>>> getList(){
        return list;
    }

    public void addNameSpaceID(String id, byte slots, String title, boolean shulker, int pag){
        if(!existNameSpaceID(id)){
            ArrayList<ArrayList<String>> u = new ArrayList<>();
            ArrayList<String> k = new ArrayList<>(); // Basic config
            ArrayList<String> i = new ArrayList<>(); // Interface
            ArrayList<String> b = new ArrayList<>(); // Advanced config
            ArrayList<String> w = new ArrayList<>(); // Interface between
            ArrayList<String> a = new ArrayList<>(); // Interface Last
            ArrayList<String> l = new ArrayList<>(); // WhiteList items
            ArrayList<String> m = new ArrayList<>(); // BlackList items


            //basic config
            k.add(id); // 0
            k.add(title); // 1
            k.add(slots + ""); // 2
            k.add(shulker + ""); // 3
            k.add(pag + ""); // 4


            //advanced config
            for(int o=0;o<10;o++){

                b.add(null);

                //0 OPEN SOUND
                //1 CLOSE SOUND
                //2 IS BREAKABLE

            }

            //interface

            for(int o=0;o<slots;o++){

                i.add(null);
                w.add(null);
                a.add(null);


            }




            u.add(k); // basic config 0

            u.add(b); // advanced config 1

            u.add(i); // Interface First 2

            u.add(w); //Interface between 3

            u.add(a); //Interface Last 4

            u.add(l); // WhiteList items 5

            u.add(m); // BlackList items 6


            list.add(u);

            setBreakable(id,true);
        }
    }
    public boolean removeNameSpaceID(String id){
        for(ArrayList<ArrayList<String>> k : list){
            if(k.get(0).get(0).equals(id)){
                list.remove(k);
                return true;
            }
        }
        return false;
    }
    public boolean existNameSpaceID(String id){
        for(ArrayList<ArrayList<String>> k:list){
            if(k.get(0).get(0).equals(id)){
                return true;
            }
        }
        return false;
    }

    public boolean existInterfaces(String id){

        if(existInterfaceFirst(id) || existInterfaceBetween(id) || existInterfaceLast(id)){

            return true;

        }

        return false;

    }

    public boolean removeInterfaceFirst(String id){

        for(ArrayList<ArrayList<String>> k:list){
            if(k.get(0).get(0).equals(id)){

                int slots = Integer.parseInt(k.get(0).get(2));

                for(int i=0;i<slots;i++){

                    k.get(2).set(i,null);

                }
                return true;

            }
        }
        return false;
    }

    public boolean existInterfaceFirst(String id){

        for(ArrayList<ArrayList<String>> k:list){
            if(k.get(0).get(0).equals(id)){

                int slots = Integer.parseInt(k.get(0).get(2));

                for(int i=0;i<slots;i++){

                    if(k.get(2).get(i) != null) {

                        return true;

                    }

                }

            }
        }
        return false;
    }

    public ItemStack[] getInterfaceFirst(String id){

        for(ArrayList<ArrayList<String>> k:list){
            if(k.get(0).get(0).equals(id)){

                int slots = Integer.parseInt(k.get(0).get(2));

                ItemStack[] itemStacks = new ItemStack[slots];

                for(int i=0;i<slots;i++){

                    if(k.get(2).get(i) != null) {

                        itemStacks[i] = NBTItem.convertNBTtoItem(new NBTContainer(k.get(2).get(i)));

                    }
                    else {

                        itemStacks[i] = null;

                    }

                }

                return itemStacks;

            }
        }
        return null;
    }

    public ItemStack[] setInterfaceFirst(String id, Inventory inventory){

        for(ArrayList<ArrayList<String>> k : list){
            if(k.get(0).get(0).equals(id)){

                for(int i=0;i<inventory.getSize();i++){

                    if(inventory.getItem(i) != null && !inventory.getItem(i).getType().equals(Material.AIR)) {
                        k.get(2).set(i, NBTItem.convertItemtoNBT(inventory.getItem(i)).toString());
                    }
                    else {

                        k.get(2).set(i, null);

                    }

                }

            }
        }

        return null;
    }
    public boolean removeInterfaceBetween(String id){

        for(ArrayList<ArrayList<String>> k:list){
            if(k.get(0).get(0).equals(id)){

                int slots = Integer.parseInt(k.get(0).get(2));

                for(int i=0;i<slots;i++){

                    k.get(3).set(i,null);

                }
                return true;

            }
        }
        return false;
    }

    public boolean existInterfaceBetween(String id){

        for(ArrayList<ArrayList<String>> k:list){
            if(k.get(0).get(0).equals(id)){

                int slots = Integer.parseInt(k.get(0).get(2));

                for(int i=0;i<slots;i++){

                    if(k.get(3).get(i) != null) {

                        return true;

                    }

                }

            }
        }
        return false;
    }

    public ItemStack[] getInterfaceBetween(String id){

        for(ArrayList<ArrayList<String>> k:list){
            if(k.get(0).get(0).equals(id)){

                int slots = Integer.parseInt(k.get(0).get(2));

                ItemStack[] itemStacks = new ItemStack[slots];

                for(int i=0;i<slots;i++){

                    if(k.get(3).get(i) != null) {

                        itemStacks[i] = NBTItem.convertNBTtoItem(new NBTContainer(k.get(3).get(i)));

                    }
                    else {

                        itemStacks[i] = null;

                    }

                }

                return itemStacks;

            }
        }
        return null;
    }

    public ItemStack[] setInterfaceBetween(String id, Inventory inventory){

        for(ArrayList<ArrayList<String>> k : list){
            if(k.get(0).get(0).equals(id)){

                for(int i=0;i<inventory.getSize();i++){

                    if(inventory.getItem(i) != null && !inventory.getItem(i).getType().equals(Material.AIR)) {
                        k.get(3).set(i, NBTItem.convertItemtoNBT(inventory.getItem(i)).toString());
                    }
                    else {

                        k.get(3).set(i, null);

                    }

                }

            }
        }

        return null;
    }
    public boolean removeInterfaceLast(String id){

        for(ArrayList<ArrayList<String>> k:list){
            if(k.get(0).get(0).equals(id)){

                int slots = Integer.parseInt(k.get(0).get(2));

                for(int i=0;i<slots;i++){

                    k.get(4).set(i,null);

                }
                return true;

            }
        }
        return false;
    }

    public boolean existInterfaceLast(String id){

        for(ArrayList<ArrayList<String>> k:list){
            if(k.get(0).get(0).equals(id)){

                int slots = Integer.parseInt(k.get(0).get(2));

                for(int i=0;i<slots;i++){

                    if(k.get(4).get(i) != null) {

                        return true;

                    }

                }

            }
        }
        return false;
    }

    public ItemStack[] getInterfaceLast(String id){

        for(ArrayList<ArrayList<String>> k:list){
            if(k.get(0).get(0).equals(id)){

                int slots = Integer.parseInt(k.get(0).get(2));

                ItemStack[] itemStacks = new ItemStack[slots];

                for(int i=0;i<slots;i++){

                    if(k.get(4).get(i) != null) {

                        itemStacks[i] = NBTItem.convertNBTtoItem(new NBTContainer(k.get(4).get(i)));

                    }
                    else {

                        itemStacks[i] = null;

                    }

                }

                return itemStacks;

            }
        }
        return null;
    }

    public ItemStack[] setInterfaceLast(String id, Inventory inventory){

        for(ArrayList<ArrayList<String>> k : list){
            if(k.get(0).get(0).equals(id)){

                for(int i=0;i<inventory.getSize();i++){

                    if(inventory.getItem(i) != null && !inventory.getItem(i).getType().equals(Material.AIR)) {
                        k.get(4).set(i, NBTItem.convertItemtoNBT(inventory.getItem(i)).toString());
                    }
                    else {

                        k.get(4).set(i, null);

                    }

                }

            }
        }

        return null;
    }

    public boolean setBreakable(String id, boolean breakable){

        for(ArrayList<ArrayList<String>> k:list){
            if(k.get(0).get(0).equals(id)){

                k.get(1).set(2,"" + breakable);

            }
        }
        return breakable;
    }

    public boolean isBreakable(String id){

        for(ArrayList<ArrayList<String>> k:list){
            if(k.get(0).get(0).equals(id)){
                if(k.get(1).get(2) != null){

                    return Boolean.parseBoolean(k.get(1).get(2));

                }
                else {
                    return false;
                }
            }
        }
        return false;
    }
    public boolean isMultiPage(String NamespaceID){

        for(ArrayList<ArrayList<String>> k:list){
            if(k.get(0).get(0).equals(NamespaceID)){

                if(Integer.parseInt(k.get(0).get(4)) > 1){

                    return true;

                }
                else {

                    return false;

                }

            }
        }

        return false;
    }
    public String getTitle(String id){
        for(ArrayList<ArrayList<String>> k:list){
            if(k.get(0).get(0).equals(id)){
                return k.get(0).get(1);
            }
        }
        return null;
    }
    public String setCloseSound(String id,String sound){
        for(ArrayList<ArrayList<String>> k:list){
            if(k.get(0).get(0).equals(id)){

                k.get(1).set(1,sound);

                return sound;

            }
        }
        return null;
    }
    public String getCloseSound(String id){
        for(ArrayList<ArrayList<String>> k:list){
            if(k.get(0).get(0).equals(id)){
                if(k.get(1).get(1) != null) {
                    return k.get(1).get(1);
                }
                else {
                    return null;
                }
            }
        }
        return null;
    }
    public String setOpenSound(String id,String sound){
        for(ArrayList<ArrayList<String>> k:list){
            if(k.get(0).get(0).equals(id)){

                k.get(1).set(0,sound);

                return sound;

            }
        }
        return null;
    }
    public String getOpenSound(String id){
        for(ArrayList<ArrayList<String>> k:list){
            if(k.get(0).get(0).equals(id)){
                if(k.get(1).get(0) != null) {
                    return k.get(1).get(0);
                }
                else {
                    return null;
                }
            }
        }
        return null;
    }
    public int getPags(String id){
        for(ArrayList<ArrayList<String>> k:list){
            if(k.get(0).get(0).equals(id)){
                return Integer.parseInt(k.get(0).get(4));
            }
        }
        return 1;
    }
    public byte getSlots(String id){
        for(ArrayList<ArrayList<String>> k:list){
            if(k.get(0).get(0).equals(id)){
                return Byte.parseByte(k.get(0).get(2));
            }
        }
        return 9;
    }
    public boolean isShulker(String id){
        for(ArrayList<ArrayList<String>> k:list){
            if(k.get(0).get(0).equals(id)){
                return Boolean.parseBoolean(k.get(0).get(3));
            }
        }
        return false;
    }
    public void stop() throws IOException {
        save();
    }


    public void save() throws IOException {
        if(list.size()>0){
            File file = new File(core.getDataFolder() + "/storages/data.json");
            if(!file.exists()){
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            Writer writer = new FileWriter(file,false);
            gson.toJson(new ManagerJson(list), writer);
            writer.flush();
            writer.close();
        }
        else {
            File file = new File(core.getDataFolder() + "/storages/data.json");
            if(file.exists()){
                file.delete();
            }
        }
    }

    public void load() throws FileNotFoundException {
        File file = new File(core.getDataFolder() + "/storages/data.json");
        if(file.exists()){
            Reader reader = new FileReader(file);
            ManagerJson json = gson.fromJson(reader, ManagerJson.class);
            if(json != null){
                list = json.getArrayList();
            }
        }
    }

    public ManagerYaml getManagerYaml() {
        return managerYaml;
    }
}
