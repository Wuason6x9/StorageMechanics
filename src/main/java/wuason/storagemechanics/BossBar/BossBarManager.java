package wuason.storagemechanics.BossBar;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import wuason.storagemechanics.Storage;

import java.util.ArrayList;

public class BossBarManager {
    private Storage core;
    ArrayList<Bar> bossBars = new ArrayList<>();

    public BossBarManager(Storage core){
    }
    public BossBar createBosbbar(Player player, String text, BarColor color, BarStyle style, Double progress, String id){
        BossBar bossbar = Bukkit.createBossBar(text,color,style);
        bossbar.setProgress(progress / 100D);
        bossbar.setVisible(true);
        bossbar.addPlayer(player);
        bossBars.add(new Bar(bossbar, player, id));
        return bossbar;
    }
    public Boolean RemoveBossbar(String id, Player player){
        for(Bar bar:bossBars){
            if(bar.getPlayerBar().equals(player)){
                if(bar.getID().equals(id)){
                    bossBars.remove(bar);
                    bar.getBossBar().removeAll();
                    return true;
                }
            }
        }
        return false;
    }
}
