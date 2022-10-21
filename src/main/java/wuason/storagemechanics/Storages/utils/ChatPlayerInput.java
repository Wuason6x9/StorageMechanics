package wuason.storagemechanics.Storages.utils;

import org.bukkit.entity.Player;

public class ChatPlayerInput {

    private Player player;

    private ChatInputManager.ChatInputType ChatInputType;

    private String NameSpaceID;


    public ChatPlayerInput(Player p, ChatInputManager.ChatInputType type, String n){

        this.player = p;
        this.ChatInputType = type;
        this.NameSpaceID = n;

    }

    public Player getPlayer() {
        return player;
    }

    public ChatInputManager.ChatInputType getChatInputType() {
        return ChatInputType;
    }

    public String getNameSpaceID() {
        return NameSpaceID;
    }
}
