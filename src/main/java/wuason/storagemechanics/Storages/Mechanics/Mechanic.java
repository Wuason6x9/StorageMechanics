package wuason.storagemechanics.Storages.Mechanics;

import org.bukkit.event.Listener;

public abstract class Mechanic {

    private String name;

    public Mechanic(String n){

        this.name = n;

    }

    public String getName() {
        return name;
    }
}
