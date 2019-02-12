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
import me.rida.anticheat.utils.AimUtil;
import me.rida.anticheat.utils.PlayerUtil;

public class ReachJ extends Check {
	public ReachJ(AntiCheat AntiCheat) {
		super("ReachJ", "Reach",  CheckType.Combat, true, false, false, false, false, 20, 1, 600000L, AntiCheat);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onAttack(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			if (event.getEntity().getType() == EntityType.PLAYER) {
				final Player player = (Player) event.getDamager();
				final Player damaged = (Player) event.getEntity();
				if (player.getGameMode() != GameMode.CREATIVE) {
					final double YawDifference = Math.abs(180.0f - Math.abs(damaged.getLocation().getYaw() - player.getLocation().getYaw()));
					double Difference = PlayerUtil.getEyeLocation(player).distance(damaged.getEyeLocation()) - 0.35;
					final int ping = getAntiCheat().getLag().getPing(player);
					double maxReach = 4.1 + damaged.getVelocity().length();
					if (player.isSprinting()) {
						maxReach += 0.2;
					}
					if (player.getLocation().getY() > damaged.getLocation().getY()) {
						Difference = player.getLocation().getY() - player.getLocation().getY();
						maxReach += Difference / 2.5;
					} else if (player.getLocation().getY() > player.getLocation().getY()) {
						Difference = player.getLocation().getY() - player.getLocation().getY();
						maxReach += Difference / 2.5;
					}
					for (final PotionEffect effect : player.getActivePotionEffects()) {
						if (effect.getType() == PotionEffectType.SPEED) {
							maxReach += 0.2 * (effect.getAmplifier() + 1);
						}
					}
					if (event.getEntity() instanceof Player) {
						final Player p = (Player) event.getEntity();
						if (p.getAllowFlight()) {
							maxReach += 1.1;
						}
					}
					final double velocity = player.getVelocity().length() + damaged.getVelocity().length();
					maxReach += velocity * 1.5;
					maxReach += ((ping < 250) ? (ping * 0.00212) : (ping * 0.031));
					maxReach += YawDifference * 0.008;
					if (maxReach < Difference) {
						if (AimUtil.instance.lookingTo(player, damaged)) {
							final String dist = Double.toString(Difference).substring(0, 3);
							final double tps = getAntiCheat().getLag().getTPS();
							getAntiCheat().logCheat(this, player, "Wrong heuristics; distance: " + dist + "; Ping: " + ping + "; TPS: " + tps, "(Type: J)");
						}
					}
				}
			}
		}
	}
}