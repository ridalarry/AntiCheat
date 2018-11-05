package me.rida.anticheat.checks.movement;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.utils.UtilCheat;
import me.rida.anticheat.utils.UtilTime;
import me.rida.anticheat.utils.needscleanup.UtilsB;

public class AscensionA extends Check {
	
	public static Map<UUID, Map.Entry<Long, Double>> AscensionTicks;
	public static Map<UUID, Double> velocity;
	
	public AscensionA(AntiCheat AntiCheat) {
		super("AscensionA", "Ascension", AntiCheat);

		this.setBannable(true);
		this.setEnabled(true);
		setMaxViolations(4);
		
		AscensionTicks = new HashMap<UUID, Map.Entry<Long, Double>>();
		velocity = new HashMap<UUID, Double>();
	}

	@EventHandler
	public void CheckAscension(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (event.getFrom().getY() >= event.getTo().getY()
				|| !getAntiCheat().isEnabled()
				|| player.getAllowFlight()
				|| player.getVehicle() != null
				|| !UtilTime.elapsed(getAntiCheat().LastVelocity.getOrDefault(player.getUniqueId(), 0L), 4200L)) {
			return;
		}
		
		long Time = System.currentTimeMillis();
		double TotalBlocks = 0.0D;
		if (AscensionTicks.containsKey(player.getUniqueId())) {
			Time = AscensionTicks.get(player.getUniqueId()).getKey().longValue();
			TotalBlocks = AscensionTicks.get(player.getUniqueId()).getValue().doubleValue();
		}
		long MS = System.currentTimeMillis() - Time;
		double OffsetY = UtilsB.offset(UtilsB.getVerticalVector(event.getFrom().toVector()),
				UtilsB.getVerticalVector(event.getTo().toVector()));
		if (OffsetY > 0.0D) {
			TotalBlocks += OffsetY;
		}
		Location a = player.getLocation().subtract(0.0D, 1.0D, 0.0D);
		if (UtilCheat.blocksNear(a)) {
			TotalBlocks = 0.0D;
		}
		double Limit = 1.05D;
		if (player.hasPotionEffect(PotionEffectType.JUMP)) {
			for (PotionEffect effect : player.getActivePotionEffects()) {
				if (effect.getType().equals(PotionEffectType.JUMP)) {
					int level = effect.getAmplifier() + 1;
					Limit += (Math.pow(level + 4.2D, 2.0D) / 16.0D) + 0.3;
					break;
				}
			}
		}
		if (TotalBlocks > Limit) {
			if (MS > 250L) {
				if (velocity.containsKey(player.getUniqueId())) {
					getAntiCheat().logCheat(this, player, "Flew up " + UtilsB.trim(1, TotalBlocks) + " blocks", "(Type: A)");
				}
				Time = System.currentTimeMillis();
			}
		} else {
			Time = System.currentTimeMillis();
		}
		AscensionTicks.put(player.getUniqueId(),
				new AbstractMap.SimpleEntry<Long, Double>(Time, TotalBlocks));
	}

	@EventHandler
	public void onLog(PlayerQuitEvent e) {
		if (AscensionTicks.containsKey(e.getPlayer().getUniqueId())) {
			AscensionTicks.remove(e.getPlayer().getUniqueId());
		}
		if (velocity.containsKey(e.getPlayer().getUniqueId())) {
			velocity.remove(e.getPlayer().getUniqueId());
		}
	}
}