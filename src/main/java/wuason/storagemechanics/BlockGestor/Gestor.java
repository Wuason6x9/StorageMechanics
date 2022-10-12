package wuason.storagemechanics.BlockGestor;

import com.google.gson.Gson;
import wuason.storagemechanics.Storage;

import java.io.*;
import java.util.ArrayList;

public class Gestor {

    private Storage core;
    private Gson gson;
    private ArrayList<ArrayList<String>> list = new ArrayList<>();
    private GestorYaml gestorYaml;

    public Gestor(Storage plugin) throws FileNotFoundException {
        this.core = plugin;
        this.gson = new Gson();
        this.gestorYaml = new GestorYaml(plugin);
        load();

    }

    public ArrayList<ArrayList<String>> getList(){
        return list;
    }

    public void addNameSpaceID(String id, byte slots, String title, boolean shulker, int pag){
        if(!existNameSpaceID(id)){
            ArrayList<String> k = new ArrayList<>();
            k.add(id);
            k.add(title);
            k.add(slots + "");
            k.add(shulker + "");
            k.add(pag + "");
            list.add(k);
        }
    }
    public boolean removeNameSpaceID(String id){
        for(ArrayList<String> k : list){
            if(k.get(0).equals(id)){
                list.remove(k);
                return true;
            }
        }
        return false;
    }
    public boolean existNameSpaceID(String id){
        for(ArrayList<String> k:list){
            if(k.get(0).equals(id)){
                return true;
            }
        }
        return false;
    }
    public String getTitle(String id){
        for(ArrayList<String> k:list){
            if(k.get(0).equals(id)){
                return k.get(1);
            }
        }
        return null;
    }
    public int getPags(String id){
        for(ArrayList<String> k:list){
            if(k.get(0).equals(id)){
                return Integer.parseInt(k.get(4));
            }
        }
        return 1;
    }
    public byte getSlots(String id){
        for(ArrayList<String> k:list){
            if(k.get(0).equals(id)){
                return Byte.parseByte(k.get(2));
            }
        }
        return 9;
    }
    public boolean isShulker(String id){
        for(ArrayList<String> k:list){
            if(k.get(0).equals(id)){
                return Boolean.parseBoolean(k.get(3));
            }
        }
        return false;
    }
    public void stop() throws IOException {
        save();
    }


    public void save() throws IOException {
        if(list.size()>0){
            File file = new File(core.getDataFolder() + "/storages/blockstatic/data.json");
            if(!file.exists()){
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            Writer writer = new FileWriter(file,false);
            gson.toJson(new GestorJson(list), writer);
            writer.flush();
            writer.close();
        }
        else {
            File file = new File(core.getDataFolder() + "/storages/blockstatic/data.json");
            if(file.exists()){
                file.delete();
            }
        }
    }

    public void load() throws FileNotFoundException {
        File file = new File(core.getDataFolder() + "/storages/blockstatic/data.json");
        if(file.exists()){
            Reader reader = new FileReader(file);
            GestorJson json = gson.fromJson(reader, GestorJson.class);
            if(json != null){
                list = json.getArrayList();
            }
        }
    }

    public GestorYaml getGestorYaml() {
        return gestorYaml;
    }
}
