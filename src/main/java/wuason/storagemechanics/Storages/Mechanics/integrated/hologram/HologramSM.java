package wuason.storagemechanics.Storages.Mechanics.integrated.hologram;

import me.filoghost.holographicdisplays.api.hologram.Hologram;

public class HologramSM {
    private Hologram hologram;
    private String storageID;

    public HologramSM(Hologram hologram, String storageID) {
        this.hologram = hologram;
        this.storageID = storageID;
    }

    public Hologram getHologram() {
        return hologram;
    }

    public String getStorageID() {
        return storageID;
    }
}
