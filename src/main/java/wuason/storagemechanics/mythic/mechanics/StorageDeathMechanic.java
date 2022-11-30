package wuason.storagemechanics.mythic.mechanics;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.core.skills.SkillTriggers;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import wuason.storagemechanics.Storage;
import wuason.storagemechanics.Storages.StorageMemory;

import java.io.FileNotFoundException;

public class StorageDeathMechanic implements ITargetedEntitySkill {

    private final boolean hasDrops;
    private final Storage core;

    public StorageDeathMechanic(MythicLineConfig config) {

        this.hasDrops = config.getBoolean("drops",true);
        this.core = Storage.getInstance();
    }

    @Override
    public SkillResult castAtEntity(SkillMetadata skillMetadata, AbstractEntity abstractEntity) {


        if(skillMetadata.getCause().equals(SkillTriggers.DEATH)){

            return onDeath(skillMetadata,abstractEntity);

        }

        return null;
    }

    private SkillResult onDeath(SkillMetadata skillMetadata, AbstractEntity abstractEntity) {

        Entity entity = abstractEntity.getBukkitEntity();

        Player player = (Player) skillMetadata.getTrigger().getBukkitEntity();

        PersistentDataContainer data = entity.getPersistentDataContainer();

        if(data.has(new NamespacedKey(core, "Storage"), PersistentDataType.STRING)){

            if(hasDrops){

                StorageMemory sm = null;

                if(core.getStorageManager().existStorageByID(entity.getUniqueId().toString())){

                    sm = core.getStorageManager().getStorageMemory(abstractEntity.getUniqueId().toString());

                }
                else if (core.getStorageManager().existStorageJson(abstractEntity.getUniqueId().toString())){

                    try {
                        sm = core.getStorageManager().loadStorage(abstractEntity.getUniqueId().toString());
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                }

                if(sm != null){

                    StorageMemory finalSm = sm;
                    Bukkit.getScheduler().runTask(core,() ->{

                        for(ItemStack[] items : finalSm.getAllItems()) {

                            for(ItemStack l : items) {

                                if(l != null && !l.getType().equals(Material.AIR)){

                                    ItemMeta meta = l.getItemMeta();

                                    if (!meta.getPersistentDataContainer().has(new NamespacedKey(core, "itemBlocked"), PersistentDataType.STRING)){
                                        if (l != null) {

                                            entity.getLocation().getWorld().dropItem(entity.getLocation(), l);

                                        }
                                    }
                                }
                            }
                        }

                    });
                }
                else {
                    return SkillResult.ERROR;
                }
            }

        }

        core.getStorageManager().removeStorage(entity.getUniqueId().toString());

        return SkillResult.SUCCESS;

    }


}
