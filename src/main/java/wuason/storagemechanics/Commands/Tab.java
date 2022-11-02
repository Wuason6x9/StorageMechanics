package wuason.storagemechanics.Commands;

import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.CustomFurniture;
import io.th0rgal.oraxen.compatibilities.provided.itembridge.OraxenItemBridge;
import io.th0rgal.oraxen.mechanics.provided.gameplay.block.BlockMechanic;
import io.th0rgal.oraxen.mechanics.provided.gameplay.block.BlockMechanicFactory;
import io.th0rgal.oraxen.mechanics.provided.gameplay.noteblock.NoteBlockMechanic;
import io.th0rgal.oraxen.mechanics.provided.gameplay.noteblock.NoteBlockMechanicFactory;
import io.th0rgal.oraxen.mechanics.provided.gameplay.noteblock.NoteBlockMechanicListener;
import io.th0rgal.oraxen.mechanics.provided.gameplay.stringblock.StringBlockMechanic;
import io.th0rgal.oraxen.mechanics.provided.gameplay.stringblock.StringBlockMechanicFactory;
import io.th0rgal.oraxen.mechanics.provided.gameplay.stringblock.StringBlockMechanicListener;
import io.th0rgal.oraxen.shaded.customblockdata.CustomBlockData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import wuason.storagemechanics.Storage;
import wuason.storagemechanics.Storages.itemmodify.ItemModify;
import wuason.storagemechanics.Storages.itemmodify.ItemModifyManager;
import wuason.storagemechanics.adapters.PluginSelectorManager;

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

                        return Arrays.asList("create", "info", "loadALL","exit", "modifyItem","editStorage");

                    case 2:
                        switch(args[0]){

                            case "modifyItem":

                                List<String> list = new ArrayList<>();
                                for(ItemModifyManager.ItemsModification item : ItemModifyManager.ItemsModification.values()){

                                    list.add(item.toString());

                                }

                                return list;

                            case "editStorage":

                                if(core.getPluginSelected().equals(PluginSelectorManager.PluginSelected.ITEMSADDER)) {
                                    CustomBlock block = CustomBlock.byAlreadyPlaced(player.getTargetBlockExact(100));

                                    if (block != null) {

                                        String NameSpaceID = block.getNamespacedID();

                                        return Collections.singletonList(NameSpaceID);

                                    }
                                }
                                else if(core.getPluginSelected().equals(PluginSelectorManager.PluginSelected.ORAXEN)){
                                    Block target = player.getTargetBlockExact(100);
                                    NoteBlockMechanic blockNote = null;
                                    StringBlockMechanic blockString = null;
                                    if(target.getType().equals(Material.NOTE_BLOCK)) {blockNote = NoteBlockMechanicListener.getNoteBlockMechanic(target);}
                                    if(target.getType().equals(Material.TRIPWIRE)) {blockString = StringBlockMechanicListener.getStringMechanic(player.getTargetBlockExact(100));}


                                    if (blockString != null) {

                                        String NameSpaceID = blockString.getItemID();

                                        return Collections.singletonList(NameSpaceID);

                                    }
                                    else if (blockNote != null) {

                                        String NameSpaceID = blockNote.getItemID();

                                        return Collections.singletonList(NameSpaceID);

                                    }

                                }
                                break;

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
