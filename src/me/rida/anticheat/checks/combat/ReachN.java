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

public class ReachN extends Check {
	public ReachN(AntiCheat AntiCheat) {
		super("ReachN", "Reach",  CheckType.Combat, true, false, false, false, false, 7, 1, 30000L, AntiCheat);
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDamage(final EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			if (event.getEntity().getType() == EntityType.PLAYER) {
				final Player player = (Player) event.getDamager();
				if (player == null) {
					return;
				}
				if (player.getGameMode() != GameMode.CREATIVE) {
					final double distance = MathUtil.getDistance3D(player.getLocation(), event.getEntity().getLocation());
					final int ping = getAntiCheat().getLag().getPing(player);
					final double tps = getAntiCheat().getLag().getTPS();
					double maxReach = 4.3;
					if (event.getEntity() instanceof Player) {
						final Player p = (Player) event.getEntity();
						if (p.getAllowFlight()) {
							maxReach += 0.7;
						}
						if (getAntiCheat().getLag().getPing(p) > 0) {
							maxReach += 0.00283906234 * getAntiCheat().getLag().getPing(p);
						}
					}
					final String dist = Double.toString(distance).substring(0, 3);
					if (event.getEntity().getLocation().distance(player.getLocation()) > maxReach && player.getLocation().getY() < event.getEntity().getLocation().getY() + 0.1) {
						if (player.hasPotionEffect(PotionEffectType.SPEED)) {
							if (event.getEntity().getLocation().distance(player.getLocation()) > maxReach + 1 && player.getLocation().getY() < event.getEntity().getLocation().getY() + 0.1) {
								if (player != null) {
									getAntiCheat().logCheat(this, player, "Interact too far away; distance: " + dist +"; MaxReach: " + maxReach + "; Ping: " + ping + "; TPS: " + tps, "(Type: N)");
								}
							}
						} else {
							if (player != null) {
								getAntiCheat().logCheat(this, player, "Interact too far away; distance: " + dist + "; MaxReach: " + maxReach +"; Ping: " + ping + "; TPS: " + tps, "(Type: N)");
							}
						}
					}
					if (event.getEntity().getLocation().distance(player.getLocation()) > maxReach && player.getLocation().getY() > event.getEntity().getLocation().getY()) {
						if (player != null) {
							getAntiCheat().logCheat(this, player, "Interact too far away; distance: " + dist +"; MaxReach: " + maxReach + "; Ping: " + ping + "; TPS: " + tps, "(Type: N)");
						}
					}
				}
			}
		}
	}
}