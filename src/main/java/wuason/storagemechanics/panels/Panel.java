package wuason.storagemechanics.panels;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class Panel implements InventoryHolder {

    private Inventory inventory;
    private Player player;
    private String id;

    public Panel(int rows, String title, Player p, String i){

        inventory = Bukkit.createInventory(this,rows * 9,title);

        this.id = i;
        this.player = p;

    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public Player getPlayer() {
        return player;
    }

    public String getId() {
        return id;
    }
    public InventoryHolder getHolder(){

        return inventory.getHolder();

    }
}
