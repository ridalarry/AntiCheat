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

public class ReachM extends Check {
	public ReachM(AntiCheat AntiCheat) {
		super("ReachM", "Reach",  CheckType.Combat, true, false, false, false, false, 20, 1, 600000L, AntiCheat);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onAttack(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			if (event.getEntity().getType() == EntityType.PLAYER) {
				final Player player = (Player) event.getDamager();
				if (player == null) {
					return;
				}
				if (player.getGameMode() != GameMode.CREATIVE) {
					final double distance = MathUtil.getHorizontalDistance(player.getLocation(), event.getEntity().getLocation()) - 0.3;
					double Reach = 3.4;
					final double yawDifference = 0.01 - Math.abs(Math.abs(player.getEyeLocation().getYaw()) - Math.abs(event.getEntity().getLocation().getYaw()));
					Reach += Math.abs(player.getVelocity().length() + event.getEntity().getVelocity().length()) * 2.5;
					Reach += yawDifference * 0.01;
					if (Reach < 3.4) {
						Reach = 3.4;
					}
					double maxReach = 4.25;
					if (event.getEntity() instanceof Player) {
						final Player p = (Player) event.getEntity();
						if (p.getAllowFlight()) {
							maxReach += 0.35;
						}
						if (getAntiCheat().getLag().getPing(p) > 0) {
							maxReach += 0.000862069 * getAntiCheat().getLag().getPing(p);
						}
					}
					if (distance > Reach) {
						final int ping = getAntiCheat().getLag().getPing(player);
						final double tps = getAntiCheat().getLag().getTPS();
						final String dist = Double.toString(distance).substring(0, 3);
						if (distance > maxReach) {
							if (player.hasPotionEffect(PotionEffectType.SPEED)) {
								if (distance > maxReach + 1.0) {
									if (player != null) {
										getAntiCheat().logCheat(this, player, "Over max reach; distance: " + dist + "; MaxReach: " + maxReach + "; Ping: " + ping + "; TPS: " + tps, "(Type: M)");
									}
								}
							} else {
								if (player != null) {
									getAntiCheat().logCheat(this, player, "Over max reach; distance: " + dist +  "; MaxReach: " + maxReach +"; Ping: " + ping + "; TPS: " + tps, "(Type: M)");
								}
							}
						}
					}
				}
			}
		}
	}
}