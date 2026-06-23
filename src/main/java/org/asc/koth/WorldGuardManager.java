package org.asc.koth;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import java.util.logging.Level;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class WorldGuardManager {
    private WorldGuard worldGuard;

    public WorldGuardManager(KOTHPlugin plugin) {
        if (plugin.getServer().getPluginManager().getPlugin("WorldGuard") != null) {
            this.worldGuard = WorldGuard.getInstance();
        } else {
            plugin.getLogger().log(Level.SEVERE, "Disabled due to no WorldGuard dependency found!");
            plugin.getServer().getPluginManager().disablePlugin((Plugin)plugin);
        }
    }

    public WorldGuard getWorldGuard() {
        return this.worldGuard;
    }

    public boolean isInRegion(Player player, String regionName) {
        RegionContainer container = this.worldGuard.getPlatform().getRegionContainer();
        World world = BukkitAdapter.adapt(player.getWorld());
        RegionManager regionManager = container.get(world);
        if (regionManager == null)
            return false;
        ApplicableRegionSet regions = regionManager.getApplicableRegions(BukkitAdapter.asBlockVector(player.getLocation()));
        for (ProtectedRegion region : regions) {
            if (region.getId().equalsIgnoreCase(regionName))
                return true;
        }
        return false;
    }
}
