package wuason.storagemechanics.panels;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import wuason.storagemechanics.Storage;

import java.util.ArrayList;

public class PanelsManager {

    private Storage core;
    private ArrayList<Panel> panels;

    public PanelsManager(Storage plugin){
        this.core = plugin;
        this.panels = new ArrayList<>();
    }

    public Panel createPanel(int rows, String title, Player player, String id){

        if(existPanel(id)){

           return getPanel(id);

        }
        else {

            Panel panel = new Panel(rows,title,player,id);
            panels.add(panel);
            return panel;

        }
    }

    public boolean removePanel(String id){

        if(existPanel(id)){

            panels.remove(getPanel(id));
            return true;

        }
        return false;

    }
    public boolean removePanel(Inventory inventory){

        if(existPanel(inventory)){

            panels.remove(getPanel(inventory));
            return true;

        }
        return false;

    }
    public boolean removePanels(Player player){

        //Posible error

        ArrayList<Panel> p = getPanels(player);

        for(Panel panel : p){

            panels.remove(p);

        }

        return true;

    }

    public boolean existPanel(String id){

        for(Panel panel : panels){
            if(panel.getId().equals(id)){

                return true;

            }

        }
        return false;
    }
    public boolean existPanel(Inventory inventory){

        for(Panel panel : panels){

            if(panel.getInventory().equals(inventory)){

                return true;

            }

        }
        return false;
    }
    public boolean existPanel(InventoryHolder inventoryHolder){

        for(Panel panel : panels){

            if(panel.getHolder().equals(inventoryHolder)){

                return true;

            }

        }
        return false;
    }
    public boolean existPanel(Panel p){

        for(Panel panel : panels){

            if(panel.equals(p)){

                return true;

            }

        }
        return false;
    }

    public Panel getPanel(String id){

        for(Panel panel : panels){

            if(panel.getId().equals(id)){

                return panel;

            }

        }
        return null;
    }
    public Panel getPanel(Inventory inventory){

        for(Panel panel : panels){

            if(panel.getInventory().equals(inventory)){

                return panel;

            }

        }
        return null;
    }
    public Panel getPanel(InventoryHolder inventoryHolder){

        for(Panel panel : panels){

            if(panel.getHolder().equals(inventoryHolder)){

                return panel;

            }

        }
        return null;
    }
    public ArrayList<Panel> getPanels(Player p){

        ArrayList<Panel> panelArrayList = new ArrayList<>();

        for(Panel panel : panels){

            if(panel.getPlayer().equals(p)){

                panelArrayList.add(panel);

            }

        }

        return panelArrayList;
    }


}
