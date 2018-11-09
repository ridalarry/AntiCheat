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
import me.rida.anticheat.packets.events.PacketPlayerEvent;
import me.rida.anticheat.utils.TimeUtil;

public class TimerA extends Check {
	private Map<UUID, Map.Entry<Integer, Long>> packets;
	private Map<UUID, Integer> verbose;
	private Map<UUID, Long> lastPacket;
	private List<Player> toCancel;

	public TimerA(AntiCheat AntiCheat) {
		super("TimerA", "Timer", AntiCheat);
		
		packets = new HashMap<>();
		verbose = new HashMap<>();
		toCancel = new ArrayList<>();
		lastPacket = new HashMap<>();

		setEnabled(true);
		setBannable(false);
		setMaxViolations(5);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onLogout(PlayerQuitEvent e) {
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
	public void PacketPlayer(PacketPlayerEvent e) {
		Player p = e.getPlayer();
		UUID u = p.getUniqueId();
		if (!this.getAntiCheat().isEnabled()) {
			return;
		}

		if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()) {
			return;
		}
		
		long lastPacket = this.lastPacket.getOrDefault(p.getUniqueId(), 0L);
		int packets = 0;
		long Time = System.currentTimeMillis();
		int verbose = this.verbose.getOrDefault(p.getUniqueId(), 0);
		
		if (this.packets.containsKey(p.getUniqueId())) {
			packets = this.packets.get(p.getUniqueId()).getKey();
			Time = this.packets.get(p.getUniqueId()).getValue();
		}

		if(System.currentTimeMillis() - lastPacket < 5) {
			this.lastPacket.put(u, System.currentTimeMillis());
			return;
		}
		double threshold = 21;
		if(TimeUtil.elapsed(Time, 1000L)) {
			if(toCancel.remove(p) && packets <= 13) {
				return;
			}
			if(packets > threshold + getAntiCheat().packet.movePackets.getOrDefault(u, 0) && getAntiCheat().packet.movePackets.getOrDefault(u, 0) < 5) {
				verbose = (packets - threshold) > 10 ? verbose + 2 : verbose + 1;
			} else {
				verbose = 0;
			}
			
			if(verbose > 2) {
				getAntiCheat().logCheat(this, p, "Packets: " + packets, "(Type: A)");
			}
			packets = 0;
			Time = TimeUtil.nowlong();
			getAntiCheat().packet.movePackets.remove(u);
		}
		packets++;

		this.lastPacket.put(u, System.currentTimeMillis());
		this.packets.put(u, new SimpleEntry<Integer, Long>(packets, Time));
		this.verbose.put(u, verbose);
	}
}