package wuason.storagemechanics.Helper;

import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import wuason.storagemechanics.Storage;

public class Helper {

    private Storage core;
    private RegionContainer containerManager;
    private WorldGuard worldGuard;

    public Helper(Storage plugin){

        this.core = plugin;
        this.worldGuard = WorldGuard.getInstance();
        this.containerManager = worldGuard.getPlatform().getRegionContainer();


    }

    public boolean isLoaded(){

        return true;

    }

    public RegionContainer getContainerManager() {

        return containerManager;

    }

    public WorldGuard getWorldGuard() {

        return worldGuard;

    }
}
