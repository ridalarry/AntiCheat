package me.rida.anticheat.checks.combat;

import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.utils.MathUtil;

public class ReachI extends Check {
	public ReachI(AntiCheat AntiCheat) {
		super("ReachI", "Reach",  CheckType.Combat, true, false, false, false, false, 20, 1, 600000L, AntiCheat);
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
					final int ping = getAntiCheat().getLag().getPing(player);
					final double tps = getAntiCheat().getLag().getTPS();
					double distance = MathUtil.getDistance3D(player.getLocation(), event.getEntity().getLocation());
					double maxReach = 4.0;
					if (player.isSprinting()) {
						distance += 0.2;
					}
					if (event.getEntity() instanceof Player) {
						final Player p = (Player) event.getEntity();
						if (p.getAllowFlight()) {
							maxReach += 1.0;
						}
						if (getAntiCheat().getLag().getPing(p) > 0) {
							maxReach += 0.00354082535 * getAntiCheat().getLag().getPing(p);
						}
					}
					for (final PotionEffect effect : player.getActivePotionEffects()) {
						if (effect.getType() == PotionEffectType.SPEED) {
							distance += 0.2 * (effect.getAmplifier() + 1);
						}
					}
					final String dist = Double.toString(distance).substring(0, 3);
					if (maxReach < distance) {
						getAntiCheat().logCheat(this, player, "Interact too far away; distance: " + dist + "; MaxReach: " + maxReach + "; Ping: " + ping + "; TPS: " + tps, "(Type: I)");
					}
				}
			}
		}
	}
}