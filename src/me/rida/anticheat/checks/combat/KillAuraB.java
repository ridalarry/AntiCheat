package me.rida.anticheat.checks.combat;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

import com.comphenix.protocol.wrappers.EnumWrappers;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.packets.events.PacketUseEntityEvent;
import me.rida.anticheat.utils.CheatUtil;
import me.rida.anticheat.utils.TimeUtil;

public class KillAuraB extends Check {
	public static Map<UUID, Map.Entry<Integer, Long>> AuraTicks;

	public KillAuraB(AntiCheat AntiCheat) {
		super("KillAuraB", "KillAura", AntiCheat);
		
		AuraTicks = new HashMap<UUID, Map.Entry<Integer, Long>>();

		setEnabled(true);
		setBannable(false);
		setMaxViolations(150);
		setViolationsToNotify(140);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onLog(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		UUID u = p.getUniqueId();

		if (AuraTicks.containsKey(u)) {
			AuraTicks.remove(u);
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void UseEntity(PacketUseEntityEvent e) {
		if (e.getAction() != EnumWrappers.EntityUseAction.ATTACK
				|| !((e.getAttacked()) instanceof Player)) {
			return;
		}

		Player p = e.getAttacker();
		Player p2 = (Player) e.getAttacked();
		
		if (p.getAllowFlight()
				|| p2.getAllowFlight()) {
			return;
		}
		
		int Count = 0;
		long Time = System.currentTimeMillis();
		if (AuraTicks.containsKey(p.getUniqueId())) {
			Count = AuraTicks.get(p.getUniqueId()).getKey();
			Time = AuraTicks.get(p.getUniqueId()).getValue();
		}
		double OffsetXZ = CheatUtil.getAimbotoffset(p.getLocation(), p.getEyeHeight(),
				p2);
		double LimitOffset = 200.0;
		if (p.getVelocity().length() > 0.08
				|| this.getAntiCheat().LastVelocity.containsKey(p.getUniqueId())) {
			LimitOffset += 200.0;
		}
		int Ping = this.getAntiCheat().getLag().getPing(p);
		if (Ping >= 100 && Ping < 200) {
			LimitOffset += 50.0;
		} else if (Ping >= 200 && Ping < 250) {
			LimitOffset += 75.0;
		} else if (Ping >= 250 && Ping < 300) {
			LimitOffset += 150.0;
		} else if (Ping >= 300 && Ping < 350) {
			LimitOffset += 300.0;
		} else if (Ping >= 350 && Ping < 400) {
			LimitOffset += 400.0;
		} else if (Ping > 400) {
			return;
		}
		if (OffsetXZ > LimitOffset * 4.0) {
			Count += 12;
		} else if (OffsetXZ > LimitOffset * 3.0) {
			Count += 10;
		} else if (OffsetXZ > LimitOffset * 2.0) {
			Count += 8;
		} else if (OffsetXZ > LimitOffset) {
			Count += 4;
		}
		if (AuraTicks.containsKey(p.getUniqueId()) && TimeUtil.elapsed(Time, 60000L)) {
			Count = 0;
			Time = TimeUtil.nowlong();
		}
		if (Count >= 16) {
			this.dumplog(p, "Offset: " + OffsetXZ + ", Ping: " + Ping + ", Max Offset: " + LimitOffset);
			this.dumplog(p, "Logged. Count: " + Count + ", Ping: " + Ping);
			Count = 0;
			this.getAntiCheat().logCheat(this, p, "Hit Miss Ratio", "(Type: B)");
		}
		AuraTicks.put(p.getUniqueId(), new AbstractMap.SimpleEntry<Integer, Long>(Count, Time));
	}
}