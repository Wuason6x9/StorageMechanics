package wuason.storagemechanics.Commands;

import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import wuason.storagemechanics.Storage;

import java.util.List;

public class EditorCommand implements CommandExecutor {

    private Storage core;

    public EditorCommand(Storage plugin){
        this.core = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        List<MetadataValue> metadata = player.getMetadata("editor");
        player.sendMessage("Editor modeEEEEE" + metadata);

        if(metadata.get(0) != null){

        }
        else {
            player.sendMessage("Editor mode");
            int slots = Integer.parseInt(args[0]);
            String validate = slots + "";
            if(validate.length() >= 1){
                player.sendMessage("Editor mode2");
                switch (slots){
                    case 54:
                        player.setMetadata("editor", new FixedMetadataValue(core, slots));
                        core.getBossBarManager().createBosbbar(player, ChatColor.GREEN + "Editor: " + 54 + " slots.", BarColor.RED, BarStyle.SOLID,100D,"editor");
                        break;
                    case 45:
                        player.setMetadata("editor", new FixedMetadataValue(core, slots));
                        core.getBossBarManager().createBosbbar(player, ChatColor.GREEN + "Editor: " + 45 + " slots.", BarColor.RED, BarStyle.SOLID,100D,"editor");
                        break;
                    case 36:
                        player.setMetadata("editor", new FixedMetadataValue(core, slots));
                        core.getBossBarManager().createBosbbar(player, ChatColor.GREEN + "Editor: " + 36 + " slots.", BarColor.RED, BarStyle.SOLID,100D,"editor");
                        break;
                    case 27:
                        player.setMetadata("editor", new FixedMetadataValue(core, slots));
                        core.getBossBarManager().createBosbbar(player, ChatColor.GREEN + "Editor: " + 27 + " slots.", BarColor.RED, BarStyle.SOLID,100D,"editor");
                        break;
                    case 18:
                        player.setMetadata("editor", new FixedMetadataValue(core, slots));
                        core.getBossBarManager().createBosbbar(player, ChatColor.GREEN + "Editor: " + 18 + " slots.", BarColor.RED, BarStyle.SOLID,100D,"editor");
                        break;
                    case 9:
                        player.setMetadata("editor", new FixedMetadataValue(core, slots));
                        core.getBossBarManager().createBosbbar(player, ChatColor.GREEN + "Editor: " + 9 + " slots.", BarColor.RED, BarStyle.SOLID,100D,"editor");
                        break;
                    default:
                        player.sendMessage(ChatColor.RED + "¡Maximo 54 slots! Elije 9, 18, 27, 36, 45, or 54");
                        player.sendMessage("Editor mode3");

                }
            }
            else {
                player.sendMessage(ChatColor.RED + "¡Error introduce un numero!");
            }
        }

        return false;
    }
}
