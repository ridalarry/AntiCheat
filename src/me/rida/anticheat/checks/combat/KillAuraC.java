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
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.packets.events.PacketUseEntityEvent;
import me.rida.anticheat.utils.TimeUtil;

public class KillAuraC extends Check {
	public static Map<UUID, Map.Entry<Integer, Long>> AimbotTicks;
	public static Map<UUID, Double> Differences;
	public static Map<UUID, Location> LastLocation;

	public KillAuraC(final AntiCheat AntiCheat) {
		super("KillAuraC", "KillAura",  CheckType.Combat, AntiCheat);
		AimbotTicks = new HashMap<>();
		Differences = new HashMap<>();
		LastLocation = new HashMap<>();

		setEnabled(true);
		setBannable(true);

		setMaxViolations(14);
		setViolationsToNotify(1);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onLogout(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		UUID u = p.getUniqueId();
		if (AimbotTicks.containsKey(u)) {
			AimbotTicks.remove(u);
		}
		if (Differences.containsKey(u)) {
			Differences.remove(u);
		}
		if (LastLocation.containsKey(u)) {
			LastLocation.remove(u);
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void UseEntity(PacketUseEntityEvent e) {
		if (e.getAction() != EnumWrappers.EntityUseAction.ATTACK
				|| !((e.getAttacked()) instanceof Player)) {
			return;
		}
		Player damager = e.getAttacker();
		if (damager.getAllowFlight()
        		|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
                || getAntiCheat().getLag().getPing(damager) > getAntiCheat().getPingCancel()) {
			return;
		}

		Location from = null;
		Location to = damager.getLocation();
		if (LastLocation.containsKey(damager.getUniqueId())) {
			from = LastLocation.get(damager.getUniqueId());
		}
		LastLocation.put(damager.getUniqueId(), damager.getLocation());
		double Count = 0;
		long Time = System.currentTimeMillis();
		double LastDifference = -111111.0;
		if (Differences.containsKey(damager.getUniqueId())) {
			LastDifference = Differences.get(damager.getUniqueId());
		}
		if (AimbotTicks.containsKey(damager.getUniqueId())) {
			Count = AimbotTicks.get(damager.getUniqueId()).getKey();
			Time = AimbotTicks.get(damager.getUniqueId()).getValue();
		}
		if (from == null || (to.getX() == from.getX() && to.getZ() == from.getZ())) {
			return;
		}
		double Difference = Math.abs(to.getYaw() - from.getYaw());
		if (Difference == 0.0) {
			return;
		}

		if (Difference > 2.4) {
			this.dumplog(damager, "Logged for KillAura Type C; Difference: " + Difference);
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
		Differences.put(damager.getUniqueId(), Difference);
		if (AimbotTicks.containsKey(damager.getUniqueId()) && TimeUtil.elapsed(Time, 5000L)) {
			dumplog(damager, "Logged for KillAura Type C; Count Reset");
			Count = 0;
			Time = TimeUtil.nowlong();
		}
		if (Count > 5) {
			Count = 0;
			dumplog(damager,
					"Logged for KillAura Type C; Last Difference: " + Math.abs(to.getYaw() - from.getYaw()) + ", Count: " + Count);
			getAntiCheat().logCheat(this, damager, "Aimbot", "(Type: C)");
		}
		AimbotTicks.put(damager.getUniqueId(),
				new AbstractMap.SimpleEntry<Integer, Long>((int) Math.round(Count), Time));
	}
}