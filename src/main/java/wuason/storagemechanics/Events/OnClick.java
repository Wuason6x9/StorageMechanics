package wuason.storagemechanics.Events;

import dev.lone.itemsadder.api.CustomBlock;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import wuason.storagemechanics.Storage;
import wuason.storagemechanics.Storages.StorageManager;

import java.util.List;
import java.util.UUID;

public class OnClick implements Listener {

    private Storage core;
    private StorageManager SM;

    public OnClick(Storage plugin){
        this.core = plugin;
        this.SM = plugin.getStorageManager();
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "" + SM);
    }


    @EventHandler(priority = EventPriority.NORMAL)
    public void OnClickEvent(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Block block = event.getClickedBlock();
            CustomBlock customblock = CustomBlock.byAlreadyPlaced(block);
            if (customblock != null) {
                if(!block.hasMetadata("storageblock_id")){
                    event.getPlayer().sendMessage(customblock.getNamespacedID());
                    String uuid = "" + UUID.randomUUID();
                    SM.CreateStorage(event.getPlayer(),customblock.getNamespacedID(),uuid,"Titulo de prueba", (byte)54);
                    block.setMetadata("storageblock_id", new FixedMetadataValue(core, customblock.getNamespacedID()));
                    block.setMetadata("storageblock_uuid", new FixedMetadataValue(core, uuid));
                    block.setMetadata("storageblock_owner", new FixedMetadataValue(core, player));
                    SM.OpenStorage(uuid,player,customblock.getNamespacedID(),player);
                }
                else{
                    String uuid = block.getMetadata("storageblock_uuid").get(0).asString();
                    String id = block.getMetadata("storageblock_id").get(0).asString();
                    Player owner = (Player) block.getMetadata("storageblock_owner").get(0).value();
                    SM.OpenStorage(uuid,player,id,owner);
                }
            }
        }
    }
}
