package wuason.storagemechanics.mythic.mechanics;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.*;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.skills.SkillTriggers;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import wuason.storagemechanics.Storage;

import java.io.FileNotFoundException;

public class StorageMMechanic implements ITargetedEntitySkill {

    private final int pages;
    private final String title;
    private final Storage core;

    public StorageMMechanic(MythicLineConfig config){
        this.core = Storage.getInstance();


        this.title = config.getPlaceholderString("title", "untitle").serialize();
        this.pages = config.getInteger("pages", 1);

    }


    @Override
    public SkillResult castAtEntity(SkillMetadata skillMetadata, AbstractEntity abstractEntity) {


        if(skillMetadata.getCause().equals(SkillTriggers.INTERACT)){

            return OnInteract(skillMetadata, abstractEntity);

        }


        return null;
    }


    private SkillResult OnInteract(SkillMetadata skillMetadata, AbstractEntity abstractEntity) {

        if(pages == 0){
            return SkillResult.INVALID_CONFIG;
        }

        Entity entity = abstractEntity.getBukkitEntity();

        Player player = (Player) skillMetadata.getTrigger().getBukkitEntity();

        PersistentDataContainer data = entity.getPersistentDataContainer();

        if(!data.has(new NamespacedKey(core,"Storage"), PersistentDataType.STRING)){

            data.set(new NamespacedKey(core,"Storage"), PersistentDataType.STRING, "a");

            String namespace = MythicBukkit.inst().getMobManager().getActiveMob(abstractEntity.getUniqueId()).orElse(null).getType().getInternalName();

            core.getStorageManager().createStorage(player, abstractEntity.getUniqueId().toString(), title,(byte)54,false, namespace, pages,null);
            core.getBlockManager().addNameSpaceID(namespace,(byte)54,title,false,pages);

        }
        try {
            core.getStorageManager().openStorage(player, abstractEntity.getUniqueId().toString(),0);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return SkillResult.SUCCESS;
    }




}
