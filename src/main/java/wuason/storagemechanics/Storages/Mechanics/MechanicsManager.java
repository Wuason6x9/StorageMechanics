package wuason.storagemechanics.Storages.Mechanics;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import wuason.storagemechanics.Storage;
import wuason.storagemechanics.Storages.Mechanics.integrated.Hopper.HopperMechanic;

import java.util.ArrayList;
import java.util.List;

public class MechanicsManager {

    private Storage core;

    List<Mechanic> mechanicActiveList = new ArrayList<>();


    public MechanicsManager(Storage plugin){
        this.core = plugin;
        registerDefaultMechanics();
    }

    public void registerMechanic(Mechanic mechanic){

        mechanicActiveList.add(mechanic);
        Bukkit.getPluginManager().registerEvents((Listener) mechanic , core);

    }

    public void registerDefaultMechanics(){

        registerMechanic(new HopperMechanic());

    }


    public List<Mechanic> getMechanicActiveList() {

        return mechanicActiveList;

    }
}
