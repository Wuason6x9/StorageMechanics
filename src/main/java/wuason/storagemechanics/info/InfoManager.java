package wuason.storagemechanics.info;

import com.google.gson.Gson;
import wuason.storagemechanics.Storage;
import wuason.storagemechanics.Storages.StorageFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;

public class InfoManager {


    private Storage core;
    private ArrayList<InfoGeneral> list = new ArrayList<>();


    public InfoManager(Storage plugin) throws FileNotFoundException {

        this.core = plugin;
        load();

    }

    public ArrayList<InfoGeneral> getList(){

        return list;

    }
    public void load() throws FileNotFoundException {

        updateInfo();

    }

    public void updateInfo() throws FileNotFoundException {
        while (!list.isEmpty()){

            list.remove(0);

        }

        File dirBlock = new File(core.getDataFolder().getPath() + "/storages/blockstatic/");
        File dirShulker = new File(core.getDataFolder().getPath() + "/storages/shulker/");

        File[] filesBlocks = dirBlock.listFiles();
        File[] filesShulker = dirShulker.listFiles();
        ArrayList<File> files = new ArrayList<>();
        Gson gson = new Gson();
        Reader reader;
        StorageFile storageFile;
        InfoGeneral info;

        if(filesBlocks != null){

            for(File f : filesBlocks){

                if(f != null){

                    files.add(f);

                }

            }

        }
        if(filesShulker != null){

            for(File k : filesShulker){

                if(k != null){

                    files.add(k);

                }

            }

        }

        String filename;

        for(File i : files){


            filename = i.getName();

            if(!filename.equals("data.json")){

                reader = new FileReader(i);
                storageFile = gson.fromJson(reader, StorageFile.class);

                if(!existNamespace(storageFile.getNameSpaceID())){
                    info = new InfoGeneral(storageFile.getNameSpaceID());
                    info.addCount(1);
                    list.add(info);

                }
                else {

                    info = getInfoGeneralByID(storageFile.getNameSpaceID());
                    info.addCount(1);

                }
            }

        }


    }


    public boolean existNamespace(String id){
        for(InfoGeneral k : list){

            if(k.getNameSpaceID().equals(id)){

                return true;

            }

        }

        return false;

    }

    public InfoGeneral getInfoGeneralByID(String id){

        for(InfoGeneral k : list){

            if(k.getNameSpaceID().equals(id)){

                return k;

            }

        }
        return null;
    }

}
