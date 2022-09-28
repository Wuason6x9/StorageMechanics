package wuason.storagemechanics.Events;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class test {

    private ArrayList<String> test = new ArrayList<>();

    public test(String nbt){
        test.add(nbt);
    }

    public ArrayList<String> getTest() {
        return test;
    }
}
