package me.rida.anticheat.checks.movement;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.utils.CheatUtil;
import me.rida.anticheat.utils.MathUtil;
import me.rida.anticheat.utils.TimeUtil;
public class AscensionA extends Check {

	public static Map<UUID, Map.Entry<Long, Double>> AscensionTicks;
	public static Map<UUID, Double> velocity;

	public AscensionA(AntiCheat AntiCheat) {
		super("AscensionA", "Ascension",  CheckType.Combat, true, true, false, true, 4, 1, 600000L, AntiCheat);

		AscensionTicks = new HashMap<UUID, Map.Entry<Long, Double>>();
		velocity = new HashMap<UUID, Double>();
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void CheckAscension(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (e.getFrom().getY() >= e.getTo().getY()
				|| !getAntiCheat().isEnabled()
				|| p.getAllowFlight()
				|| p.getVehicle() != null
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()
				|| !TimeUtil.elapsed(getAntiCheat().LastVelocity.getOrDefault(p.getUniqueId(), 0L), 4200L)) {
			return;
		}

		long Time = System.currentTimeMillis();
		double TotalBlocks = 0.0D;
		if (AscensionTicks.containsKey(p.getUniqueId())) {
			Time = AscensionTicks.get(p.getUniqueId()).getKey().longValue();
			TotalBlocks = AscensionTicks.get(p.getUniqueId()).getValue().doubleValue();
		}
		long MS = System.currentTimeMillis() - Time;
		double OffsetY = MathUtil.offset(MathUtil.getVerticalVector(e.getFrom().toVector()),
				MathUtil.getVerticalVector(e.getTo().toVector()));
		if (OffsetY > 0.0D) {
			TotalBlocks += OffsetY;
		}
		Location a = p.getLocation().subtract(0.0D, 1.0D, 0.0D);
		if (CheatUtil.blocksNear(a)) {
			TotalBlocks = 0.0D;
		}
		double Limit = 1.05D;
		if (p.hasPotionEffect(PotionEffectType.JUMP)) {
			for (PotionEffect effect : p.getActivePotionEffects()) {
				if (effect.getType().equals(PotionEffectType.JUMP)) {
					int level = effect.getAmplifier() + 1;
					Limit += (Math.pow(level + 4.2D, 2.0D) / 16.0D) + 0.3;
					break;
				}
			}
		}
		if (TotalBlocks > Limit) {
			if (MS > 250L) {
				if (velocity.containsKey(p.getUniqueId())) {
					getAntiCheat().logCheat(this, p, "Flew up " + MathUtil.trim(1, TotalBlocks) + " blocks", "(Type: A)");
				}
				Time = System.currentTimeMillis();
			}
		} else {
			Time = System.currentTimeMillis();
		}
		AscensionTicks.put(p.getUniqueId(),
				new AbstractMap.SimpleEntry<Long, Double>(Time, TotalBlocks));
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onLog(PlayerQuitEvent e) {
		if (AscensionTicks.containsKey(e.getPlayer().getUniqueId())) {
			AscensionTicks.remove(e.getPlayer().getUniqueId());
		}
		if (velocity.containsKey(e.getPlayer().getUniqueId())) {
			velocity.remove(e.getPlayer().getUniqueId());
		}
	}
} 