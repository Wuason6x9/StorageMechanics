package wuason.storagemechanics.Commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import wuason.storagemechanics.Storage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Tab implements TabCompleter {

    private Storage core;

    public Tab(Storage plugin){
        this.core = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        Player player = (Player) sender;
        switch (command.getName()){

            case "storagemechanics":

                switch (args.length){

                    case 1:

                        return Arrays.asList("create", "info", "loadALL","exit");

                    case 2:
                        switch(args[0]){

                            case "create":

                                return Arrays.asList("normal", "multipage");

                            case "info":

                                return Collections.singletonList("panel");

                        }
                        break;

                    case 3:
                        switch (args[0]){

                            case "create":

                                switch (args[1]){

                                    case "multipage":
                                        return Arrays.asList("2", "3", "4", "4", "5", "6" , "2-99999999");


                                    case "normal":
                                        return Arrays.asList("1", "2", "3", "4", "4", "5", "6");

                                }

                                break;

                        }

                    case 4:
                        switch (args[0]){

                            case "create":

                                switch (args[1]){

                                    case "multipage":
                                        return Arrays.asList("true", "false");

                                    case "normal":
                                        return Arrays.asList("true", "false");

                                }

                                break;

                        }

                }
                break;

        }

        return null;
    }
}
