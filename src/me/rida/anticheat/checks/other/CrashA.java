package me.rida.anticheat.checks.other;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.packets.events.PacketBlockPlacementEvent;
import me.rida.anticheat.packets.events.PacketHeldItemChangeEvent;
import me.rida.anticheat.packets.events.PacketSwingArmEvent;
import me.rida.anticheat.utils.UtilTime;

public class CrashA extends Check {
	public static Map<UUID, Map.Entry<Integer, Long>> crashTicks;
	public static Map<UUID, Map.Entry<Integer, Long>> crash2Ticks;
	public static Map<UUID, Map.Entry<Integer, Long>> crash3Ticks;
	public List<UUID> crashs;

	public CrashA(AntiCheat AntiCheat) {
		super("Crash", "Crash", AntiCheat);
		crashTicks = new HashMap<UUID, Map.Entry<Integer, Long>>();
		crash2Ticks = new HashMap<UUID, Map.Entry<Integer, Long>>();
		crash3Ticks = new HashMap<UUID, Map.Entry<Integer, Long>>();
		crashs = new ArrayList<UUID>();
		setMaxViolations(0);

		this.setEnabled(true);
		this.setBannable(true);
	}

	@EventHandler
	public void Swing(final PacketSwingArmEvent e) {
		final Player crash = e.getPlayer();
		if (this.crashs.contains(crash.getUniqueId())) {
			e.getPacketEvent().setCancelled(true);
			return;
		}
		int Count = 0;
		long Time = System.currentTimeMillis();
		if (crashTicks.containsKey(crash.getUniqueId())) {
			Count = crashTicks.get(crash.getUniqueId()).getKey();
			Time = crashTicks.get(crash.getUniqueId()).getValue();
		}
		++Count;
		if (crashTicks.containsKey(crash.getUniqueId()) && UtilTime.elapsed(Time, 100L)) {
			Count = 0;
			Time = UtilTime.nowlong();
		}
		if (Count > 2000) {
			this.getAntiCheat().logCheat(this, crash, null, "(Type: A)");
			this.crashs.add(crash.getUniqueId());
		}
		crashTicks.put(crash.getUniqueId(), new AbstractMap.SimpleEntry<Integer, Long>(Count, Time));
	}

	@EventHandler
	public void Switch(final PacketHeldItemChangeEvent e) {
		final Player crash = e.getPlayer();
		if (this.crashs.contains(crash.getUniqueId())) {
			e.getPacketEvent().setCancelled(true);
			return;
		}
		int Count = 0;
		long Time = System.currentTimeMillis();
		if (crash2Ticks.containsKey(crash.getUniqueId())) {
			Count = crash2Ticks.get(crash.getUniqueId()).getKey();
			Time = crash2Ticks.get(crash.getUniqueId()).getValue();
		}
		++Count;
		if (crash2Ticks.containsKey(crash.getUniqueId()) && UtilTime.elapsed(Time, 100L)) {
			Count = 0;
			Time = UtilTime.nowlong();
		}
		if (Count > 2000) {
			this.getAntiCheat().logCheat(this, crash, null, "(Type: B)");
			this.crashs.add(crash.getUniqueId());
		}
		crash2Ticks.put(crash.getUniqueId(), new AbstractMap.SimpleEntry<Integer, Long>(Count, Time));
	}

	@EventHandler
	public void BlockPlace(final PacketBlockPlacementEvent e) {
		final Player crash = e.getPlayer();
		if (this.crashs.contains(crash.getUniqueId())) {
			e.getPacketEvent().setCancelled(true);
			return;
		}
		int Count = 0;
		long Time = System.currentTimeMillis();
		if (crash3Ticks.containsKey(crash.getUniqueId())) {
			Count = crash3Ticks.get(crash.getUniqueId()).getKey();
			Time = crash3Ticks.get(crash.getUniqueId()).getValue();
		}
		++Count;
		if (crash3Ticks.containsKey(crash.getUniqueId()) && UtilTime.elapsed(Time, 100L)) {
			Count = 0;
			Time = UtilTime.nowlong();
		}
		if (Count > 2000) {
			this.getAntiCheat().logCheat(this, crash, null, "(Type: C)");
			this.crashs.add(crash.getUniqueId());
		}
		crash3Ticks.put(crash.getUniqueId(), new AbstractMap.SimpleEntry<Integer, Long>(Count, Time));
	}
}