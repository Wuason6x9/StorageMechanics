package wuason.storagemechanics.Storages.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import wuason.storagemechanics.Storage;
import wuason.storagemechanics.Storages.StorageMemory;
import wuason.storagemechanics.Storages.search.SearchPlayer;
import wuason.storagemechanics.Storages.search.SearchSystem;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ChatInputManager {

    private Storage core;
    private ArrayList<ChatPlayerInput> players = new ArrayList<>();

    public ChatInputManager(Storage plugin){

        this.core = plugin;

    }



    public void ChatInput(Player player,String NameSpaceID, ChatInputManager.ChatInputType type, String input){

        if(input != null){

            if(type.equals(ChatInputType.CLOSE_SOUND)){

                player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.SoundEstablished")) + input);
                core.getBlockManager().setCloseSound(NameSpaceID,input);
                players.remove(getChatPlayerInput(player));
                core.getStorageManager().getInterfacesManger().openEditor(player,NameSpaceID);

            }
            if(type.equals(ChatInputType.OPEN_SOUND)){

                player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.SoundEstablished")) + input);
                core.getBlockManager().setOpenSound(NameSpaceID,input);
                players.remove(getChatPlayerInput(player));
                core.getStorageManager().getInterfacesManger().openEditor(player,NameSpaceID);

            }


        }
        else {

            if(existChatPlayerInput(player)){
                players.remove(getChatPlayerInput(player));
            }

            Bukkit.getScheduler().runTask(core,() -> player.closeInventory());

            if(type.equals(ChatInputType.CLOSE_SOUND)){


                players.add(new ChatPlayerInput(player,type,NameSpaceID));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.EnterAsound")));


            }
            if(type.equals(ChatInputType.OPEN_SOUND)){

                players.add(new ChatPlayerInput(player,type,NameSpaceID));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.EnterAsound")));

            }

        }

    }
    public ChatPlayerInput getChatPlayerInput(Player player){

        for(ChatPlayerInput chatPlayerInput : players){

            if(chatPlayerInput.getPlayer().equals(player)){

                return chatPlayerInput;

            }

        }

        return null;

    }

    public boolean existChatPlayerInput(Player player){

        for(ChatPlayerInput chatPlayerInput : players){

            if(chatPlayerInput.getPlayer().equals(player)){

                return true;

            }

        }

        return false;

    }

    public enum ChatInputType{

        OPEN_SOUND,
        CLOSE_SOUND

    }

}
