package wuason.storagemechanics.Storages.Mechanics.integrated.hologram;

import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import org.bukkit.event.Listener;
import wuason.storagemechanics.Storage;
import wuason.storagemechanics.Storages.Mechanics.Mechanic;

import java.util.ArrayList;

public class HologramMechanic extends Mechanic implements Listener {

    private ArrayList<HologramSM> hologramsSM = new ArrayList<>();
    private HolographicDisplaysAPI holoAPI;


    public HologramMechanic() {
        super("HologramMechanic");

        this.holoAPI = HolographicDisplaysAPI.get(Storage.getInstance());
    }

}
