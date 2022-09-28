package wuason.storagemechanics.BossBar;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class Bar {
    private Player playerBar;
    private String ID;
    private BossBar bossBar;
    public Bar(BossBar bossbar, Player player, String id){
        this.bossBar = bossbar;
        this.ID = id;
        this.playerBar = player;
    }

    public Player getPlayerBar() {
        return playerBar;
    }

    public String getID() {
        return ID;
    }

    public BossBar getBossBar() {
        return bossBar;
    }
}
