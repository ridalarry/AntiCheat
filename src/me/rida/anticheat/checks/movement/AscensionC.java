package me.rida.anticheat.checks.movement;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.data.DataPlayer;
import me.rida.anticheat.other.Latency;
import me.rida.anticheat.utils.CheatUtil;
import me.rida.anticheat.utils.PlayerUtil;
import me.rida.anticheat.utils.ServerUtil;
import me.rida.anticheat.utils.TimeUtil;

public class AscensionC extends Check {
	public AscensionC(AntiCheat AntiCheat) {
		super("AscensionC", "Ascension",  CheckType.Movement, true, true, false, true, false, 5, 1, 600000L, AntiCheat);
	}	
	public static Map<UUID, Map.Entry<Integer, Long>> flyTicks = new HashMap<UUID, Map.Entry<Integer, Long>>();
	public static Map<UUID, Double> velocity = new HashMap<UUID, Double>();

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (p.getVehicle() != null
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| e.getFrom().getY() >= e.getTo().getY()
				|| p.getAllowFlight()
				|| p.getGameMode().equals(GameMode.CREATIVE)
				|| PlayerUtil.isNearSlime(e.getFrom())
				|| PlayerUtil.isNearSlime(e.getTo())
				|| PlayerUtil.wasOnSlime(p)
				|| PlayerUtil.isNearSlime(p)
				|| PlayerUtil.isOnSlime(p.getLocation())
				|| !TimeUtil.elapsed(getAntiCheat().LastVelocity.getOrDefault(p.getUniqueId(), 0L), 4200L)
				|| Latency.getLag(p) > 75
				|| getAntiCheat().getLastVelocity().containsKey(p.getUniqueId())) {
			return;
		}
		if (DataPlayer.lastNearSlime !=null) {
			if (DataPlayer.lastNearSlime.contains(p.getPlayer().getName().toString())) {
				return;
			}
		}
		if (!ServerUtil.isBukkitVerison("1_8")
				&&!ServerUtil.isBukkitVerison("1_7")) {
			if (p.hasPotionEffect(PotionEffectType.LEVITATION)) {
				return;
			}
		}

		Location to = e.getTo();
		Location from = e.getFrom();
		int Count = 0;
		long Time = TimeUtil.nowlong();
		if (flyTicks.containsKey(p.getUniqueId())) {
			Count = flyTicks.get(p.getUniqueId()).getKey().intValue();
			Time = flyTicks.get(p.getUniqueId()).getValue().longValue();
		}
		if (flyTicks.containsKey(p.getUniqueId())) {
			double Offset = to.getY() - from.getY();
			double Limit = 0.5D;
			double TotalBlocks = Offset;

			if (CheatUtil.blocksNear(p)) {
				TotalBlocks = 0.0D;
			}
			Location a = p.getLocation().subtract(0.0D, 1.0D, 0.0D);
			if (CheatUtil.blocksNear(a)) {
				TotalBlocks = 0.0D;
			}
			if (p.hasPotionEffect(PotionEffectType.JUMP)) {
				for (PotionEffect effect : p.getActivePotionEffects()) {
					if (effect.getType().equals(PotionEffectType.JUMP)) {
						int level = effect.getAmplifier() + 1;
						Limit += Math.pow(level + 4.1D, 2.0D) / 16.0D;
						break;
					}
				}
			}

			if (TotalBlocks >= Limit) {
				Count += 2;
			} else {
				if (Count > 0) {
					Count--;
				}
			}
		}
		if ((flyTicks.containsKey(p.getUniqueId())) && (TimeUtil.elapsed(Time, 30000L))) {
			Count = 0;
			Time = TimeUtil.nowlong();
		}
		if (Count >= 4) {
			Count = 0;
			dumplog(p, "Logged for Ascension Type C");
			if (PlayerUtil.isNearSlime(p)
					|| PlayerUtil.isNearSlime(e.getFrom())
					|| PlayerUtil.isNearSlime(e.getTo())
					||PlayerUtil.isNearSlime(p.getLocation())) {
				return;
			}
			AntiCheat.Instance.logCheat(this, p, null, "(Type C)");

		}
		flyTicks.put(p.getUniqueId(), new AbstractMap.SimpleEntry<Integer, Long>(Count, Time));
	}
}