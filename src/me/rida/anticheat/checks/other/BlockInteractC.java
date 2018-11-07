package me.rida.anticheat.checks.other;

import org.bukkit.event.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.event.block.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;

import me.rida.anticheat.utils.Color;
import me.rida.anticheat.utils.UtilVelocity;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;

public class BlockInteractC extends Check {
    public BlockInteractC(AntiCheat AntiCheat) {
        super("BlockInteractC", "BlockInteract", AntiCheat);
		setEnabled(true);
		setMaxViolations(20);
		setViolationResetTime(1000);
		setBannable(false);
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
            if (!event.getBlock().getLocation().equals((Object)target.getLocation()) && !event.isCancelled() && target.getType().isSolid() && !target.getType().name().toLowerCase().contains("sign") && !target.getType().toString().toLowerCase().contains("fence") && player.getLocation().getY() > event.getBlock().getLocation().getY()) {
            	getAntiCheat().logCheat(this, player, Color.Red + "Experemental" + " [1]", "(Type: C)");
            	
            }

            if (event.getBlockAgainst().isLiquid() && event.getBlock().getType() != Material.WATER_LILY) {
            	getAntiCheat().logCheat(this, player, Color.Red + "Experemental" + " [2]", "(Type: C)");
            }
        }
    }
}