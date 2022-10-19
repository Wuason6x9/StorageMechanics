package wuason.storagemechanics.Events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import wuason.storagemechanics.Storage;
import wuason.storagemechanics.Storages.search.SearchPlayer;
import wuason.storagemechanics.Storages.search.SearchSystem;

public class PlayerSendMessageEvent implements Listener {

    private Storage core;

    public PlayerSendMessageEvent(Storage plugin){

        this.core = plugin;


    }

    @EventHandler
    public void onSendMessage(AsyncPlayerChatEvent event){

        Player player = event.getPlayer();
        String message = event.getMessage();
        SearchSystem searchManager = core.getStorageManager().getSearchSystemManager();

        if(searchManager.existSearchPlayer(player)){

            event.setCancelled(true);

            SearchPlayer searchPlayer = searchManager.getSearchPlayer(player);

            SearchSystem.SearchType searchType = searchPlayer.getType();

            if(searchType.equals(SearchSystem.SearchType.GO_TO_PAGE)){

                if(core.getStorageUtils().isNumber(message)){


                    int distance = (int) Math.round(player.getLocation().distance(searchPlayer.getStorageMemory().getChunkStorage().getLocation()));

                    int permitted = core.getConfig().getInt("config.MaxDistanceOpenStorage");

                    if(distance > permitted){

                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',core.getConfig().getString("messages.MaxDistanceOpenStorage")));
                        searchManager.ChatInput(player,searchPlayer.getStorageMemory(),searchType, null);
                        return;

                    }

                    int page = Integer.parseInt(message);
                    if(page == 0){
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.PageNotExist")));
                        searchManager.ChatInput(player,searchPlayer.getStorageMemory(),searchType, null);
                        return;
                    }

                    if(page > searchPlayer.getStorageMemory().getPages()){

                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.PageNotExist")));
                        searchManager.ChatInput(player,searchPlayer.getStorageMemory(),searchType, null);
                        return;
                    }
                    else {

                        searchManager.ChatInput(player,searchPlayer.getStorageMemory(),searchType, "" + page);

                    }

                }
                else {

                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.PageNotExist")));
                    searchManager.ChatInput(player,searchPlayer.getStorageMemory(),searchType, null);

                }

            }
            else if (searchType.equals(SearchSystem.SearchType.SEARCH)){




            }

        }

    }

}
