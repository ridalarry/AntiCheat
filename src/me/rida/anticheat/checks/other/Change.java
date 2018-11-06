package me.rida.anticheat.checks.other;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import me.rida.anticheat.checks.Check;
import me.rida.anticheat.utils.Color;
import me.rida.anticheat.utils.needscleanup.UtilsB;
import me.rida.anticheat.utils.needscleanup.UtilsC;
import me.rida.anticheat.AntiCheat;

public class Change
extends Check {
    private List<UUID> built = new ArrayList<UUID>();
    private List<UUID> falling = new ArrayList<UUID>();

    public Change(AntiCheat AntiCheat) {
        super("Change", "Change", AntiCheat);
		setEnabled(true);
		setMaxViolations(10);
		setBannable(false);
		setViolationsToNotify(1);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent playerMoveEvent) {
        if (!this.isEnabled()) {
            return;
        }
        Player player = playerMoveEvent.getPlayer();
        if (player.getAllowFlight()) {
            return;
        }
        if (player.isInsideVehicle()) {
            return;
        }
        if (!player.getNearbyEntities(1.0, 1.0, 1.0).isEmpty()) {
            return;
        }
        if (this.built.contains(player.getUniqueId())) {
            return;
        }
        int n = 0;
        int n2 = 5;
        if (!(UtilsB.isOnTheGround(player) || UtilsC.isOnBlock(player, 0, new Material[]{Material.CARPET}) || UtilsC.isHoveringOverWater(player, 0) || player.getLocation().getBlock().getType() != Material.AIR)) {
            if (playerMoveEvent.getFrom().getY() > playerMoveEvent.getTo().getY()) {
                if (!this.falling.contains(player.getUniqueId())) {
                    this.falling.add(player.getUniqueId());
                }
            } else {
                n = playerMoveEvent.getTo().getY() > playerMoveEvent.getFrom().getY() ? (this.falling.contains(player.getUniqueId()) ? ++n : --n) : --n;
            }
        } else {
            this.falling.remove(player.getUniqueId());
        }
        if (n > n2) {
        	getAntiCheat().logCheat(this, player, Color.Red + "Experemental", null);
            n = 0;
            this.falling.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent playerQuitEvent) {
        Player player = playerQuitEvent.getPlayer();
        if (this.falling.contains(player.getUniqueId())) {
            this.falling.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onAttack(BlockPlaceEvent blockPlaceEvent) {
        if (blockPlaceEvent.getPlayer() instanceof Player) {
            Player player = blockPlaceEvent.getPlayer();
            this.built.add(player.getUniqueId());
            Bukkit.getScheduler().runTaskLater((Plugin)AntiCheat.Instance, () -> {
                boolean bl = this.built.remove(player.getUniqueId());
            }
            , 60);
        }
    }
}

