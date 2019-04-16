package me.rida.anticheat.checks.combat;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.utils.CheatUtil;
import me.rida.anticheat.utils.TimeUtil;

public class CriticalsB extends Check {
	public static Map<UUID, Map.Entry<Integer, Long>> CritTicks = new HashMap<>();
	public static Map<UUID, Double> FallDistance = new HashMap<>();

	public CriticalsB(AntiCheat AntiCheat) {
		super("CriticalsB", "Criticals",  CheckType.Combat, true, true, false, true, false, 4, 1, 600000L, AntiCheat);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onDamage(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player)
				|| !e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
			return;
		}

		final Player p = (Player) e.getDamager();
		if (p == null) {
			return;
		}
		if (p.getAllowFlight()
				|| getAntiCheat().LastVelocity.containsKey(p.getUniqueId())
				|| CheatUtil.slabsNear(p.getLocation())
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
			return;
		}

		final Location pL = p.getLocation().clone();
		pL.add(0.0, p.getEyeHeight() + 1.0, 0.0);
		if (CheatUtil.blocksNear(pL)) {
			return;
		}
		int Count = 0;
		long Time = System.currentTimeMillis();
		if (CritTicks.containsKey(p.getUniqueId())) {
			Count = CritTicks.get(p.getUniqueId()).getKey();
			Time = CritTicks.get(p.getUniqueId()).getValue();
		}
		if (!FallDistance.containsKey(p.getUniqueId())) {
			return;
		}
		final double realFallDistance = FallDistance.get(p.getUniqueId());
		Count = p.getFallDistance() > 0.0 && !p.isOnGround() && realFallDistance == 0.0 ? ++Count : 0;
		if (CritTicks.containsKey(p.getUniqueId()) && TimeUtil.elapsed(Time, 10000)) {
			Count = 0;
			Time = TimeUtil.nowlong();
		}
		if (Count >= 2) {
			Count = 0;
			this.getAntiCheat().logCheat(this, p, "Count: " + Count, "(Type: B)");
		}
		CritTicks.put(p.getUniqueId(), new AbstractMap.SimpleEntry<>(Count, Time));
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onMove(PlayerMoveEvent e) {
		final Player p2 = e.getPlayer();
		double Falling = 0.0;
		if (!p2.isOnGround() && e.getFrom().getY() > e.getTo().getY()) {
			if (FallDistance.containsKey(p2.getUniqueId())) {
				Falling = FallDistance.get(p2.getUniqueId());
			}
			Falling += e.getFrom().getY() - e.getTo().getY();
		}
		FallDistance.put(p2.getUniqueId(), Falling);
	}
}