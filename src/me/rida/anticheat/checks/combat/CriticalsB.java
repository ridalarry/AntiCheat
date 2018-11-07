package me.rida.anticheat.checks.combat;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.utils.UtilCheat;
import me.rida.anticheat.utils.UtilTime;

public class CriticalsB extends Check {
	public static Map<UUID, Map.Entry<Integer, Long>> CritTicks = new HashMap<UUID, Map.Entry<Integer, Long>>();
	public static Map<UUID, Double> FallDistance = new HashMap<UUID, Double>();

	public CriticalsB(AntiCheat AntiCheat) {
		super("CriticalsB", "Criticals", AntiCheat);

		setEnabled(true);
		setBannable(true);
		setMaxViolations(4);
	}

	@EventHandler
	public void onLog(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();

		if (CritTicks.containsKey(uuid)) {
			CritTicks.remove(uuid);
		}
		if (FallDistance.containsKey(uuid)) {
			CritTicks.remove(uuid);
		}
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player)
				|| !e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
			return;
		}

		Player player = (Player) e.getDamager();
		if (player.getAllowFlight()
				|| getAntiCheat().LastVelocity.containsKey(player.getUniqueId())
				|| UtilCheat.slabsNear(player.getLocation())) {
			return;
		}

		Location pL = player.getLocation().clone();
		pL.add(0.0, player.getEyeHeight() + 1.0, 0.0);
		if (UtilCheat.blocksNear(pL)) {
			return;
		}
		int Count = 0;
		long Time = System.currentTimeMillis();
		if (CritTicks.containsKey(player.getUniqueId())) {
			Count = CritTicks.get(player.getUniqueId()).getKey();
			Time = CritTicks.get(player.getUniqueId()).getValue();
		}
		if (!FallDistance.containsKey(player.getUniqueId())) {
			return;
		}
		double realFallDistance = FallDistance.get(player.getUniqueId());
		Count = player.getFallDistance() > 0.0 && !player.isOnGround() && realFallDistance == 0.0 ? ++Count : 0;
		if (CritTicks.containsKey(player.getUniqueId()) && UtilTime.elapsed(Time, 10000)) {
			Count = 0;
			Time = UtilTime.nowlong();
		}
		if (Count >= 2) {
			Count = 0;
			this.getAntiCheat().logCheat(this, player, null, "(Type: B)");
		}
		CritTicks.put(player.getUniqueId(), new AbstractMap.SimpleEntry<Integer, Long>(Count, Time));
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void Move(PlayerMoveEvent e) {
		Player Player2 = e.getPlayer();
		double Falling = 0.0;
		if (!Player2.isOnGround() && e.getFrom().getY() > e.getTo().getY()) {
			if (FallDistance.containsKey(Player2.getUniqueId())) {
				Falling = FallDistance.get(Player2.getUniqueId());
			}
			Falling += e.getFrom().getY() - e.getTo().getY();
		}
		FallDistance.put(Player2.getUniqueId(), Falling);
	}
}
