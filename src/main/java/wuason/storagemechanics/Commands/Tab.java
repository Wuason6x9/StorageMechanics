package wuason.storagemechanics.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import wuason.storagemechanics.Storage;

import java.util.List;

public class Tab implements TabCompleter {

    private Storage core;

    public Tab(Storage plugin){
        this.core = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
