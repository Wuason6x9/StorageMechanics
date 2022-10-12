package wuason.storagemechanics.Editor;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import wuason.storagemechanics.Storage;

import java.util.ArrayList;

public class EditorMode {

    final private byte MaxArguments = 2;


    private Storage core;

    private ArrayList<PlayerEditorMode> ActivePlayersEditorMode = new ArrayList<>();

    public EditorMode(Storage plugin){
        this.core = plugin;
    }

    public void inEditorMode(Player player, int slots, String title, boolean isShulker, int pages){
        if(title == ""){
            title = "Untitled";
        }
        core.getBossBarManager().createBosbbar(player,core.getConfig().getString("BossbarText") + " " + core.getConfig().getString("Bossbarslots") + " " + slots, BarColor.valueOf(core.getConfig().getString("BarColor").toUpperCase()), BarStyle.valueOf(core.getConfig().getString("BarStyle").toUpperCase()),100D, "editormode");
        System.out.println(pages + "EDITORMODE ");
        ActivePlayersEditorMode.add(new PlayerEditorMode(player,(byte)slots, title,isShulker,pages));
    }
    public boolean isinEditorMode(Player player){
        for(PlayerEditorMode k:ActivePlayersEditorMode){
            if(k.getPlayer().equals(player)){
                return true;
            }
        }
        return false;
    }

    public boolean outEditorMode(Player player){
        if(isinEditorMode(player)){
            core.getBossBarManager().RemoveBossbar("editormode", player);
            for(PlayerEditorMode k: ActivePlayersEditorMode){
                if(k.getPlayer().equals(player)){
                    ActivePlayersEditorMode.remove(k);
                    return true;
                }
            }
        }
        return false;
    }



    public ArrayList<PlayerEditorMode> getActivePlayersEditorMode() {
        return ActivePlayersEditorMode;
    }
    public PlayerEditorMode getPlayerEditorMode(Player player){
        if(isinEditorMode(player)){
            for(PlayerEditorMode instance: ActivePlayersEditorMode){
                if(instance.getPlayer().equals(player)){
                    return instance;
                }
            }
        }
        return null;
    }

    public byte getMaxArguments() {
        return MaxArguments;
    }
}
