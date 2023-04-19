package wuason.storagemechanics.Commands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.*;
import dev.lone.itemsadder.api.CustomBlock;
import io.th0rgal.oraxen.mechanics.provided.gameplay.noteblock.NoteBlockMechanic;
import io.th0rgal.oraxen.mechanics.provided.gameplay.noteblock.NoteBlockMechanicListener;
import io.th0rgal.oraxen.mechanics.provided.gameplay.stringblock.StringBlockMechanic;
import io.th0rgal.oraxen.mechanics.provided.gameplay.stringblock.StringBlockMechanicListener;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import wuason.storagemechanics.Storage;
import wuason.storagemechanics.Storages.itemmodify.ItemModifyManager;
import wuason.storagemechanics.adapters.PluginSelectorManager;
import wuason.storagemechanics.panels.PanelInfo;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Objects;

public class CommandManager {

    private Storage core;

    public CommandManager(Storage plugin){
        this.core = plugin;
        register();

    }
    public void register(){

        new CommandAPI

        new CommandAPICommand("storagemechanics")
                .withAliases("sm","smechanics","storagem")
                .withPermission("storage.admin")
                .withSubcommand(new CommandAPICommand("create")
                        .withSubcommand(new CommandAPICommand("multipage")

                                .withArguments(new IntegerArgument("pages"))
                                .withArguments(new BooleanArgument("isShulker"))
                                .withArguments(new GreedyStringArgument("gui title"))
                                .executes((sender, args) -> {

                                    Player player = (Player) sender;
                                    String guiTitle = (String)args[2];
                                    int pages = (int)args[0];
                                    boolean isShulker = (boolean) args[1];

                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.InEditorMode") + ChatColor.AQUA + "\n (" + guiTitle + ")" + " (" + (pages)  + ")" + ChatColor.YELLOW + "(ShulkerMode: " + isShulker + ")"));
                                    core.getEditorMode().inEditorMode(player, (6 * 9), guiTitle, isShulker, pages);

                                })

                        )
                        .withSubcommand(new CommandAPICommand("normal")
                                .withArguments(new IntegerArgument("rows").replaceSuggestions(ArgumentSuggestions.strings("1","2","3","4","5","6")))
                                .withArguments(new BooleanArgument("isShulker"))
                                .withArguments(new GreedyStringArgument("gui title"))
                                .executes((sender, args) -> {

                                    Player player = (Player) sender;
                                    String guiTitle = (String)args[2];
                                    int rows = (int)args[0];
                                    boolean isShulker = (boolean) args[1];

                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.InEditorMode") + ChatColor.AQUA + "\n (" + guiTitle + ")" + " (" + (rows * 9)  + ")" + ChatColor.YELLOW + "(ShulkerMode: " + isShulker + ")"));
                                    core.getEditorMode().inEditorMode(player, (rows * 9), guiTitle, isShulker,1);

                                })
                        )
                )

                .withSubcommand(new CommandAPICommand("editStorage")

                        .withArguments(new TextArgument("CustomBlock ID").replaceSuggestions(ArgumentSuggestions.strings(i -> {

                            String[] id = new String[1];

                            id[0] = "none";

                            Player player = (Player) i.sender();

                            if(core.getPluginSelected().equals(PluginSelectorManager.PluginSelected.ITEMSADDER)) {
                                CustomBlock block = CustomBlock.byAlreadyPlaced(player.getTargetBlockExact(100));

                                if (block != null) {

                                    id[0] = "\"" + block.getNamespacedID() + "\"";
                                    return id;

                                }
                            }
                            else if(core.getPluginSelected().equals(PluginSelectorManager.PluginSelected.ORAXEN)) {
                                Block target = player.getTargetBlockExact(100);
                                NoteBlockMechanic blockNote = null;
                                StringBlockMechanic blockString = null;
                                if (target.getType().equals(Material.NOTE_BLOCK)) {
                                    blockNote = NoteBlockMechanicListener.getNoteBlockMechanic(target);
                                }
                                if (target.getType().equals(Material.TRIPWIRE)) {
                                    blockString = StringBlockMechanicListener.getStringMechanic(player.getTargetBlockExact(100));
                                }


                                if (blockString != null) {

                                    id[0] = "\"" + blockString.getItemID() + "\"";

                                    return id;

                                } else if (blockNote != null) {

                                    id[0] = "\"" +  blockNote.getItemID() + "\"";

                                    return id;

                                }
                            }
                            return id;

                        })))

                        .executes((sender, args) -> {

                            Player player = (Player) sender;

                            if (core.getBlockManager().existNameSpaceID((String)args[0])) {

                                core.getStorageManager().getInterfacesManger().openEditor(player, (String)args[0]);

                            }

                        })

                )
                .withSubcommand(new CommandAPICommand("loadALL")
                        .executes((sender,args) ->{

                            Player player = (Player) sender;
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',core.getConfig().getString("messages.loadAllCommand")));
                            core.getBlockManager().getManagerYaml().loadConfig();

                        })
                )
                .withSubcommand(new CommandAPICommand("modifyItem")
                        .withArguments(new StringArgument("Type").replaceSuggestions(suggestionInfo -> Arrays.stream(ItemModifyManager.ItemsModification.values()).map(Objects::toString).toArray(String[]::new)))
                        .executes((sender, args) ->{
                            Player player = (Player) sender;

                            if(player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType().equals(Material.AIR)){
                                return;
                            }

                            ItemModifyManager.ItemsModification itemModification = ItemModifyManager.ItemsModification.valueOf(((String)args[0]).toUpperCase());

                            player.getInventory().setItemInMainHand(core.getStorageManager().getItemModifyManager().modifyItem(player.getInventory().getItemInMainHand(), itemModification));

                        })
                )
                .withSubcommand(new CommandAPICommand("exit")

                        .executes((sender, args) ->{
                            Player player = (Player) sender;
                            if(core.getEditorMode().isinEditorMode(player)){
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.EditorModeDesactivedMessage")));
                                core.getEditorMode().outEditorMode(player);

                            }
                            else {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.CorrectUsage")));
                            }

                        })
                )
                .withSubcommand(new CommandAPICommand("info")
                        .withSubcommand(new CommandAPICommand("panel")
                                .executes((sender,args) -> {

                                    PanelInfo panel = new PanelInfo(core, (Player)sender);
                                    panel.openPanelAdmin();

                                })
                        )
                )
                .withSubcommand(new CommandAPICommand("api")
                        .withSubcommand(new CommandAPICommand("groups")
                                .withSubcommand(new CommandAPICommand("create")

                                        .withArguments(new TextArgument("GROUP"))
                                        .withArguments(new IntegerArgument("rows").replaceSuggestions(ArgumentSuggestions.strings("1","2","3","4","5","6")))

                                        .executes((sender, args) -> {

                                            String group = (String) args[0];
                                            int slots = (int)args[1]*9;

                                            core.getBlockManager().addNameSpaceID(group,(byte)slots,"none",false,3);


                                        })

                                )
                                .withSubcommand(new CommandAPICommand("remove")

                                        .withArguments(new TextArgument("GROUP"))

                                        .executes((sender, args) -> {

                                            String group = (String) args[0];

                                            core.getBlockManager().removeNameSpaceID(group);


                                        })

                                )
                        )
                        .withSubcommand(new CommandAPICommand("create")

                                .withSubcommand(new CommandAPICommand("multipage")
                                        .withArguments(new TextArgument("GROUP"))
                                        .withArguments(new TextArgument("ID"))
                                        .withArguments(new IntegerArgument("pages"))
                                        .withArguments(new GreedyStringArgument("gui title"))
                                        .executes((sender, args) -> {

                                            String group = (String)args[0];
                                            Player player = (Player) sender;
                                            String guiTitle = (String)args[3];
                                            int pages = (int)args[2];

                                            String id = (String)args[1];

                                            core.getStorageManager().createStorage(player,id,guiTitle,(byte)(6 * 9),false,group,pages, null);

                                        })

                                )
                                .withSubcommand(new CommandAPICommand("normal")
                                        .withArguments(new TextArgument("GROUP"))
                                        .withArguments(new TextArgument("ID"))
                                        .withArguments(new IntegerArgument("rows").replaceSuggestions(ArgumentSuggestions.strings("1","2","3","4","5","6")))
                                        .withArguments(new GreedyStringArgument("gui title"))
                                        .executes((sender, args) -> {

                                            String group = (String)args[0];
                                            String id = (String)args[1];
                                            Player player = (Player) sender;
                                            String guiTitle = (String)args[3];
                                            int rows = (int)args[2];

                                            core.getStorageManager().createStorage(player,id,guiTitle,(byte)(rows * 9),false,group,1, null);

                                        })
                                )
                        )
                        .withSubcommand(new CommandAPICommand("open")
                                .withSubcommand(new CommandAPICommand("open_me")

                                        .withSubcommand(new CommandAPICommand("normal")

                                                .withArguments(new TextArgument("ID").replaceSuggestions(ArgumentSuggestions.strings(suggestionInfo -> core.getStorageManager().getCustomStoragesID().toArray(new String[0]))))
                                                .executes((sender,args) ->{

                                                    String id = (String)args[0];
                                                    Player player = (Player) sender;

                                                    try {
                                                        core.getStorageManager().openStorage(player,id,0);
                                                    } catch (FileNotFoundException e) {
                                                        throw new RuntimeException(e);
                                                    }

                                                })

                                        )
                                        .withSubcommand(new CommandAPICommand("multipage")

                                                .withArguments(new TextArgument("ID").replaceSuggestions(ArgumentSuggestions.strings(suggestionInfo -> core.getStorageManager().getCustomStoragesID().toArray(new String[0]))))
                                                .withArguments(new IntegerArgument("page"))
                                                .executes((sender,args) ->{

                                                    String id = (String)args[0];
                                                    Player player = (Player) sender;

                                                    try {
                                                        core.getStorageManager().openStorage(player,id,(int)args[1]);
                                                    } catch (FileNotFoundException e) {
                                                        throw new RuntimeException(e);
                                                    }

                                                })

                                        )

                                )
                                .withSubcommand(new CommandAPICommand("open_other")

                                        .withSubcommand(new CommandAPICommand("normal")

                                                .withArguments(new TextArgument("ID").replaceSuggestions(ArgumentSuggestions.strings(suggestionInfo -> core.getStorageManager().getCustomStoragesID().toArray(new String[0]))))
                                                .withArguments(new PlayerArgument("player"))
                                                .executes((sender,args) ->{

                                                    String id = (String)args[0];
                                                    Player player = (Player) args[1];

                                                    try {
                                                        core.getStorageManager().openStorage(player,id,0);
                                                    } catch (FileNotFoundException e) {
                                                        throw new RuntimeException(e);
                                                    }

                                                })

                                        )
                                        .withSubcommand(new CommandAPICommand("multipage")

                                                .withArguments(new TextArgument("ID").replaceSuggestions(ArgumentSuggestions.strings(suggestionInfo -> core.getStorageManager().getCustomStoragesID().toArray(new String[0]))))
                                                .withArguments(new IntegerArgument("page"))
                                                .withArguments(new PlayerArgument("player"))
                                                .executes((sender,args) ->{

                                                    String id = (String)args[0];
                                                    Player player = (Player) args[2];

                                                    try {
                                                        core.getStorageManager().openStorage(player,id,(int)args[1]);
                                                    } catch (FileNotFoundException e) {
                                                        throw new RuntimeException(e);
                                                    }

                                                })

                                        )

                                )



                        )

                        .withSubcommand(new CommandAPICommand("remove")

                                .withArguments(new TextArgument("ID").replaceSuggestions(ArgumentSuggestions.strings(suggestionInfo -> core.getStorageManager().getCustomStoragesID().toArray(new String[0]))))
                                .executes((sender,args) ->{

                                    String id = (String)args[0];

                                    core.getStorageManager().removeStorage(id);

                                })

                        )



                )

                .register();

    }
}
