package me.rida.anticheat.checks.other;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.rida.anticheat.utils.Color;
import me.rida.anticheat.utils.VelocityUtil;
import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.other.Ping;

public class BlockInteractD extends Check {
    public BlockInteractD(AntiCheat AntiCheat) {
        super("BlockInteractD", "BlockInteract", CheckType.Other, AntiCheat);
		setEnabled(true);
		setMaxViolations(20);
		setViolationResetTime(1000);
		setBannable(true);
		setViolationsToNotify(4);
    }
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlaceBlock(final BlockPlaceEvent e) {
        final Player p = e.getPlayer();
        if (p.getAllowFlight()
                || p.getVehicle() != null
                || Ping.getPing(e.getPlayer()) > 100
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
		        || getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()
				|| !getAntiCheat().isEnabled()
				|| p.getGameMode().equals(GameMode.CREATIVE)
                || VelocityUtil.didTakeVelocity(p)) return;
        if (e.getBlock().getWorld().getBlockAt(e.getBlock().getLocation().subtract(0.0, 1.0, 0.0)).getType() == Material.AIR) {
            
            if (!p.isSneaking() && !p.isFlying() && groundAround(p.getLocation()) && e.getBlock().getWorld().getBlockAt(e.getBlock().getLocation().subtract(0.0, 1.0, 0.0)).getType() == Material.AIR && p.getWorld().getBlockAt(p.getLocation().subtract(0.0, 1.0, 0.0)).equals(e.getBlock())) {
                	getAntiCheat().logCheat(this, p, Color.Red + "Experemental", "(Type: D)");
            }
        }
    }
    public static boolean groundAround(final Location loc) {
        for (int radius = 2, x = -radius; x < radius; ++x) {
            for (int y = -radius; y < radius; ++y) {
                for (int z = -radius; z < radius; ++z) {
                    final Material mat = loc.getWorld().getBlockAt(loc.add((double)x, (double)y, (double)z)).getType();
                    if (mat.isSolid() || mat == Material.WATER || mat == Material.STATIONARY_WATER || mat == Material.LAVA || mat == Material.STATIONARY_LAVA) {
                        loc.subtract((double)x, (double)y, (double)z);
                        return true;
                    }
                    loc.subtract((double)x, (double)y, (double)z);
                }
            }
        }
        return false;
    }
}