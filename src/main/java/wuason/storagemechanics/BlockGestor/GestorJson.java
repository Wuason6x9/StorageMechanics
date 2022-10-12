package wuason.storagemechanics.BlockGestor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GestorJson {

    private ArrayList<ArrayList<String>> namespaces = new ArrayList<>();


    public GestorJson(ArrayList<ArrayList<String>> list){
        namespaces = list;
    }

    public ArrayList<String> getNamespaces() {
        ArrayList<String> list = new ArrayList<>();
        for(ArrayList<String> k:namespaces){
            list.add(k.get(0));
        }
        return list;
    }
    public ArrayList<ArrayList<String>> getArrayList(){
        return namespaces;
    }
}
