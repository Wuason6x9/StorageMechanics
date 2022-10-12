package wuason.storagemechanics.BossBar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import wuason.storagemechanics.Storage;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BossBarManager {
    private Storage core;
    ArrayList<Bar> bossBars = new ArrayList<>();
    public BossBarManager(Storage core){
    }
    public Bar createBosbbar(Player player, String text, BarColor color, BarStyle style, Double progress, String id){
        if(!existBar(id)){
            BossBar bossbar = Bukkit.createBossBar(text,color,style);
            bossbar.setProgress(progress / 100D);
            bossbar.setVisible(true);
            bossbar.addPlayer(player);
            Bar bar = new Bar(bossbar, player, id);
            bossBars.add(bar);
            return bar;
        }
        else {
            for(Bar bar: bossBars){
                if(bar.getID().equals(id)){
                    return bar;
                }
            }
            return null;
        }
    }
    public boolean RemoveBossbar(String id, Player player){
        for(Bar bar:bossBars){
            if(bar.getPlayerBar().equals(player)){
                if(bar.getID().equals(id)){
                    bar.getBossBar().removeAll();
                    bossBars.remove(bar);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean existBar(String id){
        for(Bar bar:bossBars){
            if(bar.getID().equals(id)){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Bar> getBossBarsByPlayer(Player player){
        ArrayList<Bar> bars = new ArrayList<>();
        for(Bar bar:bossBars){
            if(bar.getPlayerBar().equals(player)){
                bars.add(bar);
            }
        }
        return bars;
    }
}
