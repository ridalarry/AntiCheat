package me.rida.anticheat.checks.movement;

import org.bukkit.event.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.event.block.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;

import me.rida.anticheat.utils.Color;
import me.rida.anticheat.utils.UtilVelocity;
import me.rida.anticheat.utils.needscleanup.UtilsB;
import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;

public class ScaffoldB extends Check {
    public ScaffoldB(AntiCheat AntiCheat) {
        super("ScaffoldB", "Scaffold", AntiCheat);
		setEnabled(true);
		setMaxViolations(20);
		setViolationResetTime(1000);
		setBannable(true);
		setViolationsToNotify(2);
    }
    @EventHandler
    public void onPlaceBlock(final BlockPlaceEvent event) {
        final Player player = event.getPlayer();
        final Block target = player.getTargetBlock((Set)null, 5);
        if (player.getGameMode().equals(GameMode.CREATIVE)
                || player.getAllowFlight()
                || event.getPlayer().getVehicle() != null
				|| !getAntiCheat().isEnabled()
				|| getAntiCheat().isSotwMode()
                || UtilVelocity.didTakeVelocity(player)) return;
        if (event.getBlock().getWorld().getBlockAt(event.getBlock().getLocation().subtract(0.0, 1.0, 0.0)).getType() == Material.AIR) {
            
            if (!player.isSneaking() && !player.isFlying() && UtilsB.groundAround(player.getLocation()) && event.getBlock().getWorld().getBlockAt(event.getBlock().getLocation().subtract(0.0, 1.0, 0.0)).getType() == Material.AIR && player.getWorld().getBlockAt(player.getLocation().subtract(0.0, 1.0, 0.0)).equals(event.getBlock())) {
                	getAntiCheat().logCheat(this, player, Color.Red + "Experemental", "(Type: B)");
            }
        }
    }
}