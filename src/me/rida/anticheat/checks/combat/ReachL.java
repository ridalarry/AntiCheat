package me.rida.anticheat.checks.combat;

import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.utils.MathUtil;

public class ReachL extends Check {
	public ReachL(AntiCheat AntiCheat) {
		super("ReachL", "Reach",  CheckType.Combat, true, false, false, false, false, 20, 1, 600000L, AntiCheat);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onHit(EntityDamageByEntityEvent event) {

		if (event.getDamager() instanceof Player) {
			if (event.getEntity().getType() == EntityType.PLAYER) {
				Player player = (Player) event.getDamager();
				if (player.getGameMode() != GameMode.CREATIVE) {
					double distance = MathUtil.getDistance3D(player.getLocation(), event.getEntity().getLocation());
					final int ping = getAntiCheat().getLag().getPing(player);
					double tps = getAntiCheat().getLag().getTPS();
					String dst = Double.toString(distance).substring(0, 3);
					double maxReach = 4.25;
					double dist = event.getEntity().getLocation().distance(player.getLocation());
					if (dist > maxReach && event.getEntity().getLocation().getBlockY() == player.getLocation().getBlockY()) {
						if (player != null) {
							getAntiCheat().logCheat(this, player, "Interact too far away; distance: " + dst + "; Ping: " + ping + "; TPS: " + tps, "(Type: L)");
						} 
					} else if ((dist > maxReach + 0.3
							|| dist > maxReach + 0.4
							|| dist > maxReach + 0.5
							|| dist > maxReach + 0.6
							|| dist > maxReach + 0.7
							|| dist > maxReach + 0.8
							|| dist > maxReach + 0.9
							|| dist > maxReach + 1.0
							|| dist > maxReach + 1.1
							|| dist > maxReach + 1.2
							|| dist > maxReach + 1.3)
							&& event.getEntity().getLocation().getBlockY() > player.getLocation().getBlockY()) {
						if (player.hasPotionEffect(PotionEffectType.SPEED)) {
							if ((dist > maxReach + 1 + 0.3
									|| dist > maxReach + 1 + 0.4
									|| dist > maxReach + 1 + 0.5
									|| dist > maxReach + 1 + 0.6
									|| dist > maxReach + 1 + 0.7
									|| dist > maxReach + 1 + 0.8
									|| dist > maxReach + 1 + 0.9
									|| dist > maxReach + 1 + 1.0
									|| dist > maxReach + 1 + 1.1
									|| dist > maxReach + 1 + 1.2
									|| dist > maxReach + 1 + 1.3)
									&& event.getEntity().getLocation().getBlockY() > player.getLocation().getBlockY()) {
								if (player != null) {
									getAntiCheat().logCheat(this, player, "Interact too far away; distance: " + dst + "; Ping: " + ping + "; TPS: " + tps, "(Type: L)");
								} 
							}
						} else {
							if (player != null) {
								getAntiCheat().logCheat(this, player, "Interact too far away; distance: " + dst + "; Ping: " + ping + "; TPS: " + tps, "(Type: L)");
							}
						}

					} else if (dist > maxReach + 0.2 && event.getEntity().getLocation().getBlockY() < player.getLocation().getBlockY()) {
						if (player.hasPotionEffect(PotionEffectType.SPEED)) {
							if (dist > maxReach + 1 + 0.2 && event.getEntity().getLocation().getBlockY() < player.getLocation().getBlockY()) {
								if (player != null) {
									getAntiCheat().logCheat(this, player, "Interact too far away; distance: " + dst + "; Ping: " + ping + "; TPS: " + tps, "(Type: L)");
								}
							}
						} else {
							if (player != null) {
								getAntiCheat().logCheat(this, player, "Interact too far away; distance: " + dst + "; Ping: " + ping + "; TPS: " + tps, "(Type: L)");
							}
						}
					}
				}
			}
		}
	}
}