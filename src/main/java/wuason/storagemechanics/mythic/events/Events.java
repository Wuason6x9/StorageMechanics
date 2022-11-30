package wuason.storagemechanics.mythic.events;

import io.lumine.mythic.bukkit.events.MythicMechanicLoadEvent;
import io.lumine.mythic.core.skills.SkillTriggers;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import wuason.storagemechanics.mythic.mechanics.StorageDeathMechanic;
import wuason.storagemechanics.mythic.mechanics.StorageMMechanic;
import wuason.storagemechanics.mythic.mechanics.StorageNMechanic;

public class Events implements Listener {


    @EventHandler
    public void onEvent(MythicMechanicLoadEvent event){

        if(event.getMechanicName().equalsIgnoreCase("storagem")){

            if(event.getContainer().getTrigger().equals(SkillTriggers.INTERACT)){
                event.register(new StorageMMechanic(event.getConfig()));
            }
            else if(event.getContainer().getTrigger().equals(SkillTriggers.DEATH)){
                event.register(new StorageDeathMechanic(event.getConfig()));
            }
        }
        if(event.getMechanicName().equalsIgnoreCase("storagen")){

            if(event.getContainer().getTrigger().equals(SkillTriggers.INTERACT)){
                event.register(new StorageNMechanic(event.getConfig()));
            }
            else if(event.getContainer().getTrigger().equals(SkillTriggers.DEATH)){
                event.register(new StorageDeathMechanic(event.getConfig()));
            }
        }

    }

}
