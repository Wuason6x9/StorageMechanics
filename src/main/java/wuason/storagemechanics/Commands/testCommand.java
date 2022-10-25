package wuason.storagemechanics.Commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import wuason.storagemechanics.Storage;
import wuason.storagemechanics.Storages.StorageMemory;

public class testCommand implements CommandExecutor {

    private Storage core;

    public testCommand(Storage plugin){
        this.core = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;

        StorageMemory storageMemory = core.getStorageManager().getStorageMemory(core.getStorageUtils().getLocationStorageID(player.getTargetBlockExact(100).getLocation()));


        for(int i=0;i<Integer.parseInt(args[2]);i++) {
            storageMemory.addItemAll(new ItemStack(Material.getMaterial(args[0]), Integer.parseInt(args[1])));
        }

        return false;
    }
}
