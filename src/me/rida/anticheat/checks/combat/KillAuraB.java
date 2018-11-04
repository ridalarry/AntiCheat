package me.rida.anticheat.checks.combat;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import com.comphenix.protocol.wrappers.EnumWrappers;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.packets.events.PacketUseEntityEvent;
import me.rida.anticheat.utils.UtilCheat;
import me.rida.anticheat.utils.UtilTime;

public class KillAuraB extends Check {
	public static Map<UUID, Map.Entry<Integer, Long>> AuraTicks;

	public KillAuraB(AntiCheat AntiCheat) {
		super("KillAuraB", "KillAura (Type: B)", AntiCheat);
		
		AuraTicks = new HashMap<UUID, Map.Entry<Integer, Long>>();

		setEnabled(false);
		setBannable(true);
		setMaxViolations(150);
		setViolationsToNotify(140);
	}

	@EventHandler
	public void onLog(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();

		if (AuraTicks.containsKey(uuid)) {
			AuraTicks.remove(uuid);
		}
	}

	@EventHandler
	public void UseEntity(PacketUseEntityEvent e) {
		if (e.getAction() != EnumWrappers.EntityUseAction.ATTACK
				|| !((e.getAttacked()) instanceof Player)
				|| getAntiCheat().isSotwMode()) {
			return;
		}

		Player damager = e.getAttacker();
		Player player = (Player) e.getAttacked();
		
		if (damager.getAllowFlight()
				|| player.getAllowFlight()) {
			return;
		}
		
		int Count = 0;
		long Time = System.currentTimeMillis();
		if (AuraTicks.containsKey(damager.getUniqueId())) {
			Count = AuraTicks.get(damager.getUniqueId()).getKey();
			Time = AuraTicks.get(damager.getUniqueId()).getValue();
		}
		double OffsetXZ = UtilCheat.getAimbotoffset(damager.getLocation(), damager.getEyeHeight(),
				player);
		double LimitOffset = 200.0;
		if (damager.getVelocity().length() > 0.08
				|| this.getAntiCheat().LastVelocity.containsKey(damager.getUniqueId())) {
			LimitOffset += 200.0;
		}
		int Ping = this.getAntiCheat().getLag().getPing(damager);
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
		if (AuraTicks.containsKey(damager.getUniqueId()) && UtilTime.elapsed(Time, 60000L)) {
			Count = 0;
			Time = UtilTime.nowlong();
		}
		if (Count >= 16) {
			this.dumplog(damager, "Offset: " + OffsetXZ + ", Ping: " + Ping + ", Max Offset: " + LimitOffset);
			this.dumplog(damager, "Logged. Count: " + Count + ", Ping: " + Ping);
			Count = 0;
			this.getAntiCheat().logCheat(this, damager, "Hit Miss Ratio", null);
		}
		AuraTicks.put(damager.getUniqueId(), new AbstractMap.SimpleEntry<Integer, Long>(Count, Time));
	}
}