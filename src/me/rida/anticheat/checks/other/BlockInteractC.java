package me.rida.anticheat.checks.other;

import org.bukkit.event.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.event.block.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;

import me.rida.anticheat.utils.Color;
import me.rida.anticheat.utils.VelocityUtil;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;

public class BlockInteractC extends Check {
    public BlockInteractC(AntiCheat AntiCheat) {
        super("BlockInteractC", "BlockInteract", AntiCheat);
		setEnabled(true);
		setMaxViolations(20);
		setViolationResetTime(1000);
		setBannable(false);
		setViolationsToNotify(4);
    }
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlaceBlock(final BlockPlaceEvent e) {
        final Player p = e.getPlayer();
        final Block t = p.getTargetBlock((Set)null, 5);
        if (p.getGameMode().equals(GameMode.CREATIVE)
                || p.getAllowFlight()
                || e.getPlayer().getVehicle() != null
				|| !getAntiCheat().isEnabled()
                || VelocityUtil.didTakeVelocity(p)) return;
        if (e.getBlock().getWorld().getBlockAt(e.getBlock().getLocation().subtract(0.0, 1.0, 0.0)).getType() == Material.AIR) {
            if (!e.getBlock().getLocation().equals((Object)t.getLocation()) && !e.isCancelled() && t.getType().isSolid() && !t.getType().name().toLowerCase().contains("sign") && !t.getType().toString().toLowerCase().contains("fence") && p.getLocation().getY() > e.getBlock().getLocation().getY()) {
            	getAntiCheat().logCheat(this, p, Color.Red + "Experemental" + " [1]", "(Type: C)");
            	
            }

            if (e.getBlockAgainst().isLiquid() && e.getBlock().getType() != Material.WATER_LILY) {
            	getAntiCheat().logCheat(this, p, Color.Red + "Experemental" + " [2]", "(Type: C)");
            }
        }
    }
}