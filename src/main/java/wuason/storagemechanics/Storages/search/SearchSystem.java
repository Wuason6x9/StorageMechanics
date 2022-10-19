package wuason.storagemechanics.Storages.search;

        import org.bukkit.Bukkit;
        import org.bukkit.ChatColor;
        import org.bukkit.entity.Player;
        import org.bukkit.event.EventHandler;
        import org.bukkit.event.Listener;
        import org.bukkit.event.player.AsyncPlayerChatEvent;
        import wuason.storagemechanics.Storage;
        import wuason.storagemechanics.Storages.StorageMemory;

        import java.io.FileNotFoundException;
        import java.util.ArrayList;

public class SearchSystem {

    private Storage core;
    ArrayList<SearchPlayer> players = new ArrayList<>();


    public SearchSystem(Storage plugin){

        this.core = plugin;

    }

    public void ChatInput(Player player, StorageMemory memory,SearchType type,String input){

       if(input != null){

           if(type.equals(SearchType.SEARCH)){



           }
           else if (type.equals(SearchType.GO_TO_PAGE)) {

               int page = Integer.parseInt(input);
               Bukkit.getScheduler().runTask(core,() ->{
                   try {
                       core.getStorageManager().OpenStorage(player,memory.getId(),(page - 1));
                   } catch (FileNotFoundException e) {
                       throw new RuntimeException(e);
                   }
               });
               players.remove(getSearchPlayer(player));

           }


       }
       else {

           if(existSearchPlayer(player)){
               players.remove(getSearchPlayer(player));
           }

           if(type.equals(SearchType.SEARCH)){



           }
           else if (type.equals(SearchType.GO_TO_PAGE)) {

                players.add(new SearchPlayer(player,memory,type));
               Bukkit.getScheduler().runTask(core,() ->{
                   player.closeInventory();
               });

                player.sendMessage(ChatColor.translateAlternateColorCodes('&', core.getConfig().getString("messages.SearchSystemChat")));

           }

       }

    }

    public SearchPlayer getSearchPlayer(Player player){

        for(SearchPlayer searchPlayer : players){

            if(searchPlayer.getPlayer().equals(player)){

                return searchPlayer;

            }

        }

        return null;

    }

    public boolean existSearchPlayer(Player player){

        for(SearchPlayer searchPlayer : players){

            if(searchPlayer.getPlayer().equals(player)){

                return true;

            }

        }

        return false;

    }

    public enum SearchType{

        GO_TO_PAGE,
        SEARCH

    }

}