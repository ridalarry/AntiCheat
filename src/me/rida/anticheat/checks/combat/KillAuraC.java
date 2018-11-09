package me.rida.anticheat.checks.combat;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

import com.comphenix.protocol.wrappers.EnumWrappers;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.packets.events.PacketUseEntityEvent;
import me.rida.anticheat.utils.UtilTime;

public class KillAuraC extends Check {
	public static Map<UUID, Map.Entry<Integer, Long>> AimbotTicks;
	public static Map<UUID, Double> Differences;
	public static Map<UUID, Location> LastLocation;

	public KillAuraC(final AntiCheat AntiCheat) {
		super("KillAuraC", "KillAura", AntiCheat);
		AimbotTicks = new HashMap<>();
		Differences = new HashMap<>();
		LastLocation = new HashMap<>();

		setEnabled(true);
		setBannable(true);

		setMaxViolations(14);
		setViolationResetTime(120000);
		setViolationsToNotify(4);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onLogout(PlayerQuitEvent e) {
		if (AimbotTicks.containsKey(e.getPlayer().getUniqueId())) {
			AimbotTicks.remove(e.getPlayer().getUniqueId());
		}
		if (Differences.containsKey(e.getPlayer().getUniqueId())) {
			Differences.remove(e.getPlayer().getUniqueId());
		}
		if (LastLocation.containsKey(e.getPlayer().getUniqueId())) {
			LastLocation.remove(e.getPlayer().getUniqueId());
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void UseEntity(PacketUseEntityEvent e) {
		if (e.getAction() != EnumWrappers.EntityUseAction.ATTACK
				|| !((e.getAttacked()) instanceof Player)) {
			return;
		}
		Player p = e.getAttacker();
		if (p.getAllowFlight()) {
			return;
		}

		Location from = null;
		Location to = p.getLocation();
		if (LastLocation.containsKey(p.getUniqueId())) {
			from = LastLocation.get(p.getUniqueId());
		}
		LastLocation.put(p.getUniqueId(), p.getLocation());
		double Count = 0;
		long Time = System.currentTimeMillis();
		double LastDifference = -111111.0;
		double Difference = Math.abs(to.getYaw() - from.getYaw());
		if (Differences.containsKey(p.getUniqueId())) {
			LastDifference = Differences.get(p.getUniqueId());
		}
		if (AimbotTicks.containsKey(p.getUniqueId())) {
			Count = AimbotTicks.get(p.getUniqueId()).getKey();
			Time = AimbotTicks.get(p.getUniqueId()).getValue();
		}
		if (from == null 
				|| (to.getX() == from.getX() && to.getZ() == from.getZ()) 
				|| (Difference == 0.0)) {
			return;
		}

		if (Difference > 2.4) {
			this.dumplog(p, "Difference: " + Difference);
			double diff = Math.abs(LastDifference - Difference);
			if (e.getAttacked().getVelocity().length() < 0.1) {
				if (diff < 1.4) {
					Count += 1;
				} else {
					Count = 0;
				}
			} else {
				if (diff < 1.8) {
					Count += 1;
				} else {
					Count = 0;
				}
			}
		}
		Differences.put(p.getUniqueId(), Difference);
		if (AimbotTicks.containsKey(p.getUniqueId()) && UtilTime.elapsed(Time, 5000L)) {
			dumplog(p, "Count Reset");
			Count = 0;
			Time = UtilTime.nowlong();
		}
		if (Count > 5) {
			Count = 0;
			dumplog(p,
					"Logged. Last Difference: " + Math.abs(to.getYaw() - from.getYaw()) + ", Count: " + Count);
			getAntiCheat().logCheat(this, p, "Aimbot", "(Type: C)");
		}
		AimbotTicks.put(p.getUniqueId(),
				new AbstractMap.SimpleEntry<Integer, Long>((int) Math.round(Count), Time));
	}
}