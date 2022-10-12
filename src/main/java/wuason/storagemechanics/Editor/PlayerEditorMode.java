package wuason.storagemechanics.Editor;

import org.bukkit.entity.Player;

public class PlayerEditorMode {
    private Player player;
    private byte slots;
    private String title;
    private boolean shulker;
    private int pages;

    public PlayerEditorMode(Player p, byte s, String t, boolean m, int pa){
        this.player = p;
        this.slots = s;
        this.title = t;
        this.shulker = m;
        this.pages = pa;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public byte getSlots() {
        return slots;
    }

    public void setSlots(byte slots) {
        this.slots = slots;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isShulker() {
        return shulker;
    }

    public void setShulker(boolean shulker) {
        this.shulker = shulker;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
