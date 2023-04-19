package wuason.storagemechanics.Helper;

import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import wuason.storagemechanics.Storage;

public class Helper {

    private Storage core;

    public Helper(Storage plugin){

        this.core = plugin;


    }

    public boolean isLoaded(){

        return true;

    }

    public RegionContainer getContainerManager() {

        return WorldGuard.getInstance().getPlatform().getRegionContainer();

    }

    public WorldGuard getWorldGuard() {

        return WorldGuard.getInstance();

    }
}
