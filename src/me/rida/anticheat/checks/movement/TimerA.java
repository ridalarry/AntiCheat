package me.rida.anticheat.checks.movement;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.packets.PacketCore;
import me.rida.anticheat.packets.events.PacketPlayerEvent;
import me.rida.anticheat.utils.TimeUtil;

public class TimerA extends Check {
	public static Map<UUID, Map.Entry<Integer, Long>> packets;
	public static Map<UUID, Integer> verbose;
	public static Map<UUID, Long> lastPacket;
	public static List<Player> toCancel;

	public TimerA(AntiCheat AntiCheat) {
		super("TimerA", "Timer", CheckType.Movement, AntiCheat);
		
		packets = new HashMap<>();
		verbose = new HashMap<>();
		toCancel = new ArrayList<>();
		lastPacket = new HashMap<>();

		setEnabled(true);
		setBannable(false);
		setMaxViolations(5);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	private void onLogout(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		UUID u = p.getUniqueId();
		if(packets.containsKey(u)) {
			packets.remove(u);
		}
		if(verbose.containsKey(u)) {
			verbose.remove(u);
		}
		if(lastPacket.containsKey(u)) {
			lastPacket.remove(u);
		}
		if(toCancel.contains(p)) {
			toCancel.remove(p);
		}
	}

	@EventHandler
	private void PacketPlayer(PacketPlayerEvent e) {
		Player p = e.getPlayer();
		UUID u = p.getUniqueId();
		if (!this.getAntiCheat().isEnabled()
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
		        || getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
			return;
		}

		if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()) {
			return;
		}
		
		long lastPacket = TimerA.lastPacket.getOrDefault(p.getUniqueId(), 0L);
		int packets = 0;
		long Time = System.currentTimeMillis();
		int verbose = TimerA.verbose.getOrDefault(p.getUniqueId(), 0);
		
		if (TimerA.packets.containsKey(p.getUniqueId())) {
			packets = TimerA.packets.get(p.getUniqueId()).getKey();
			Time = TimerA.packets.get(p.getUniqueId()).getValue();
		}

		if(System.currentTimeMillis() - lastPacket < 5) {
			TimerA.lastPacket.put(u, System.currentTimeMillis());
			return;
		}
		double threshold = 21;
		if(TimeUtil.elapsed(Time, 1000L)) {
			if(toCancel.remove(p) && packets <= 13) {
				return;
			}
			if(packets > threshold + PacketCore.movePackets.getOrDefault(u, 0) && PacketCore.movePackets.getOrDefault(u, 0) < 5) {
				verbose = (packets - threshold) > 10 ? verbose + 2 : verbose + 1;
			} else {
				verbose = 0;
			}
			
			if(verbose > 2) {
				getAntiCheat().logCheat(this, p, "Packets: " + packets, "(Type: A)");
			}
			packets = 0;
			Time = TimeUtil.nowlong();
			PacketCore.movePackets.remove(u);
		}
		packets++;

		TimerA.lastPacket.put(u, System.currentTimeMillis());
		TimerA.packets.put(u, new SimpleEntry<Integer, Long>(packets, Time));
		TimerA.verbose.put(u, verbose);
	}
}