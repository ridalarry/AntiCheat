package me.rida.anticheat.checks.movement;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.packets.events.PacketPlayerEventB;
import me.rida.anticheat.utils.UtilTime;

public class TimerA extends Check {
	private Map<UUID, Map.Entry<Integer, Long>> packets;
	private Map<UUID, Integer> verbose;
	private Map<UUID, Long> lastPacket;
	private List<Player> toCancel;

	public TimerA(AntiCheat AntiCheat) {
		super("TimerA", "Timer (Type: A)", AntiCheat);
		
		packets = new HashMap<>();
		verbose = new HashMap<>();
		toCancel = new ArrayList<>();
		lastPacket = new HashMap<>();

		setEnabled(true);
		setBannable(false);
		setMaxViolations(5);
	}

	@EventHandler
	public void onLogout(PlayerQuitEvent e) {
		if(packets.containsKey(e.getPlayer().getUniqueId())) {
			packets.remove(e.getPlayer().getUniqueId());
		}
		if(verbose.containsKey(e.getPlayer().getUniqueId())) {
			verbose.remove(e.getPlayer().getUniqueId());
		}
		if(lastPacket.containsKey(e.getPlayer().getUniqueId())) {
			lastPacket.remove(e.getPlayer().getUniqueId());
		}
		if(toCancel.contains(e.getPlayer())) {
			toCancel.remove(e.getPlayer());
		}
	}

	@EventHandler
	public void PacketPlayer(PacketPlayerEventB event) {
		Player player = event.getPlayer();
		if (!this.getAntiCheat().isEnabled()) {
			return;
		}

		if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()) {
			return;
		}
		
		long lastPacket = this.lastPacket.getOrDefault(player.getUniqueId(), 0L);
		int packets = 0;
		long Time = System.currentTimeMillis();
		int verbose = this.verbose.getOrDefault(player.getUniqueId(), 0);
		
		if (this.packets.containsKey(player.getUniqueId())) {
			packets = this.packets.get(player.getUniqueId()).getKey();
			Time = this.packets.get(player.getUniqueId()).getValue();
		}

		if(System.currentTimeMillis() - lastPacket < 5) {
			this.lastPacket.put(player.getUniqueId(), System.currentTimeMillis());
			return;
		}
		double threshold = 21;
		if(UtilTime.elapsed(Time, 1000L)) {
			if(toCancel.remove(player) && packets <= 13) {
				return;
			}
			if(packets > threshold + getAntiCheat().packet.movePackets.getOrDefault(player.getUniqueId(), 0) && getAntiCheat().packet.movePackets.getOrDefault(player.getUniqueId(), 0) < 5) {
				verbose = (packets - threshold) > 10 ? verbose + 2 : verbose + 1;
			} else {
				verbose = 0;
			}
			
			if(verbose > 2) {
				getAntiCheat().logCheat(this, player, "Packets: " + packets, null);
			}
			packets = 0;
			Time = UtilTime.nowlong();
			getAntiCheat().packet.movePackets.remove(player.getUniqueId());
		}
		packets++;

		this.lastPacket.put(player.getUniqueId(), System.currentTimeMillis());
		this.packets.put(player.getUniqueId(), new SimpleEntry<Integer, Long>(packets, Time));
		this.verbose.put(player.getUniqueId(), verbose);
	}
}