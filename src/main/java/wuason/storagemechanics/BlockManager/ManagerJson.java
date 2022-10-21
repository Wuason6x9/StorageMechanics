package wuason.storagemechanics.BlockManager;

import java.util.ArrayList;

public class ManagerJson {

    private ArrayList<ArrayList<ArrayList<String>>> namespaces;


    public ManagerJson(ArrayList<ArrayList<ArrayList<String>>> list){
        namespaces = list;
    }

    public ArrayList<String> getNamespaces() {
        ArrayList<String> list = new ArrayList<>();

        for(ArrayList<ArrayList<String>> k : namespaces){
            list.add(k.get(0).get(0));
        }
        return list;
    }
    public ArrayList<ArrayList<ArrayList<String>>> getArrayList(){
        return namespaces;
    }
}
