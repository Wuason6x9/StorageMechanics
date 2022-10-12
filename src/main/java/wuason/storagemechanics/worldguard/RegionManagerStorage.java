package wuason.storagemechanics.worldguard;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import wuason.storagemechanics.Storage;

public class RegionManagerStorage {

    private Storage core;


    public RegionManagerStorage(Storage plugin){

        this.core = plugin;

    }

    public RegionManager getRegionManager(World world){

        RegionManager regionManager = core.getHelperManager().getContainerManager().get(BukkitAdapter.adapt(world));
        return regionManager;
    }

    public boolean hasPermission(Location loc, Player player){

        boolean perm;

        ApplicableRegionSet regions = getRegionManager(loc.getWorld()).getApplicableRegions(BukkitAdapter.asBlockVector(loc));

        if(regions.isMemberOfAll(WorldGuardPlugin.inst().wrapPlayer(player))){

            return true;

        }
        else if(regions.isOwnerOfAll(WorldGuardPlugin.inst().wrapPlayer(player))){

            return true;

        }

        return false;
    }

}
