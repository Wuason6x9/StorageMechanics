package wuason.storagemechanics.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import wuason.storagemechanics.Storage;
import wuason.storagemechanics.Storages.itemmodify.ItemModifyManager;
import wuason.storagemechanics.panels.PanelInfo;

public class StorageCommand implements CommandExecutor {

    private Storage core;

    public StorageCommand(Storage plugin){

        this.core = plugin;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        Player player = (Player) sender;
        //Si no pone argumentos
        if(args == null){
            sendCorrectUsage(player);
            return false;
        }
        //argumentos
        if(args.length>0){

            switch (args[0]){

                case "reload":

                    core.getConfigManager().config();
                    player.sendMessage("Reloaded!");

                    break;

                case "exit":
                    if(core.getEditorMode().isinEditorMode(player)){
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.EditorModeDesactivedMessage")));
                        core.getEditorMode().outEditorMode(player);

                    }
                    else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.CorrectUsage")));
                    }
                    break;

                case "create":

                    if(args[1] == null){
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.CorrectUsage")));
                        return false;

                    }
                    switch (args[1]){


                        case "multipage":

                            if(args[2] == null){

                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.CorrectUsage")));

                                return false;

                            }

                            int pages;
                            if(!core.getStorageUtils().isNumber(args[2])){

                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.CorrectUsage")));

                                return false;

                            }

                            pages = Integer.parseInt(args[2]);

                            if(pages < 2){

                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.CorrectUsage")));

                            }

                            boolean isShulker;

                            if(args[3] == null){

                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.CorrectUsage")));

                                return false;

                            }

                            if(!core.getStorageUtils().isBool(args[3])){

                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.CorrectUsage")));
                                return false;

                            }

                            isShulker = Boolean.parseBoolean(args[3]);


                            StringBuilder messageBuilders = new StringBuilder();
                            String titles = "";
                            String titleTs;

                            if(args.length >= 4){
                                if(args[4] != null){
                                    for(int i = 4; i < (args.length); i++){
                                        messageBuilders.append(args[i] + " ");
                                    }
                                    titleTs = messageBuilders.toString();
                                    titles = titleTs.substring(0, ((titleTs.length() - 1)));

                                }
                            }

                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.InEditorMode") + ChatColor.AQUA + "\n (" + titles + ")" + " (" + (pages)  + ")" + ChatColor.YELLOW + "(ShulkerMode: " + isShulker + ")"));
                            core.getEditorMode().inEditorMode(player, (6 * 9), titles, isShulker, pages);
                            return true;



                        case "normal":

                            player.sendMessage("" + args.length);

                            if(args.length < 5){
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.CorrectUsage")));
                                return false;
                            }

                            if(core.getEditorMode().isinEditorMode(player)){
                                core.getEditorMode().outEditorMode(player);
                            }

                            //ROWS
                            int rows = 1;

                            if(core.getStorageUtils().isNumber(args[2])){
                                rows = Integer.parseInt(args[2]);
                            }
                            else {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.CorrectUsage")));
                                return false;
                            }
                            if(rows==0){
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.CorrectUsage")));
                                return false;
                            }

                            //isShulkerMode
                            Boolean shulkerMode = false;

                            if(core.getStorageUtils().isBool(args[3])){
                                shulkerMode = Boolean.parseBoolean(args[3]);
                            }
                            else {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.CorrectUsage")));
                                return false;
                            }
                            if(args[3] == null){
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.CorrectUsage")));
                                return false;
                            }

                            //Titulo
                            StringBuilder messageBuilder = new StringBuilder();
                            String title = "";
                            String titleT;

                            if(args.length >= 5){
                                if(args[4] != null){
                                    for(int i = 4; i < (args.length); i++){
                                        messageBuilder.append(args[i] + " ");
                                    }
                                    titleT = messageBuilder.toString();
                                    title = titleT.substring(0, ((titleT.length() - 1)));

                                }
                            }

                            if(rows>6){
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("message_editor_maxRows")));
                                return false;
                            }

                            else{

                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.InEditorMode") + ChatColor.AQUA + "\n (" + title + ")" + " (" + (rows * 9)  + ")" + ChatColor.YELLOW + "(ShulkerMode: " + shulkerMode + ")"));
                                core.getEditorMode().inEditorMode(player, (rows * 9), title, shulkerMode,1);
                                return true;

                            }

                    }

                    break;

                case "editStorage":

                    if(args.length == 2) {

                        if (core.getBlockManager().existNameSpaceID(args[1])) {

                            core.getStorageManager().getInterfacesManger().openEditor(player, args[1]);
                            return true;

                        }
                    }
                    return false;
                case "loadALL":

                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',core.getConfig().getString("messages.loadAllCommand")));
                    core.getBlockManager().getManagerYaml().loadConfig();
                    break;

                case "modifyItem":

                    ItemStack itemStack = player.getInventory().getItemInMainHand();

                    if(itemStack == null){
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.NotItemInHand")));
                        return false;
                    }
                    if(args[1] == null){
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.CorrectUsage")));
                        return false;
                    }
                    if(args.length > 2){
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.CorrectUsage")));
                        return false;
                    }

                    ItemModifyManager.ItemsModification itemModification = ItemModifyManager.ItemsModification.valueOf(args[1].toUpperCase());

                    if(itemModification == null){

                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.CorrectUsage")));
                        return false;

                    }
                    else {

                        player.getInventory().setItemInMainHand(core.getStorageManager().getItemModifyManager().modifyItem(itemStack,itemModification));

                    }

                    break;

                case "info":

                    //si tiene 2 argumentos
                    if(args.length != 2){

                        sendCorrectUsage(player);
                        return false;

                    }

                    switch (args[1]){

                        case "panel":

                            PanelInfo panel = new PanelInfo(core, player);
                            panel.openPanelAdmin();
                            return true;

                    }

                    break;

            }

        }
        return false;
    }


    private void sendCorrectUsage(Player player){

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.CorrectUsage")));

    }
}
