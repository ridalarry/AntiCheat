package me.rida.anticheat.checks.player;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.packets.events.PacketPlayerEvent;
import me.rida.anticheat.utils.Color;
import me.rida.anticheat.utils.PlayerUtil;
import me.rida.anticheat.utils.TimeUtil;

public class PacketsA extends Check {
	public static Map<UUID, Map.Entry<Integer, Long>> packetTicks;
	public static Map<UUID, Long> lastPacket;
	public static List<UUID> blacklist;

	public PacketsA(AntiCheat AntiCheat) {
		super("PacketsA", "Packets", CheckType.Player, true, false, false, false, true, 10, 1, 600000L, AntiCheat);
		blacklist = new ArrayList<UUID>();
		lastPacket = new HashMap<UUID, Long>();
		packetTicks = new HashMap<UUID, Map.Entry<Integer, Long>>();
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	private void PacketPlayer(PacketPlayerEvent event) {
		Player player = event.getPlayer();
		if (!getAntiCheat().isEnabled()
				|| player.getGameMode().equals(GameMode.CREATIVE)
				|| getAntiCheat().lag.getTPS() > 21.0D || getAntiCheat().lag.getTPS() < getAntiCheat().getTPSCancel()
				|| getAntiCheat().lag.getPing(player) > 200) {
			return;
		}
		int Count = 0;
		long Time = System.currentTimeMillis();
		if (packetTicks.containsKey(player.getUniqueId())) {
			Count = packetTicks.get(player.getUniqueId()).getKey().intValue();
			Time = packetTicks.get(player.getUniqueId()).getValue().longValue();
		}
		if (lastPacket.containsKey(player.getUniqueId())) {
			long MS = System.currentTimeMillis() - lastPacket.get(player.getUniqueId()).longValue();
			if (MS >= 100L) {
				blacklist.add(player.getUniqueId());
			} else if ((MS > 1L) && (PacketsA.blacklist.contains(player.getUniqueId()))) {
				blacklist.remove(player.getUniqueId());
			}
		}
		if (!blacklist.contains(player.getUniqueId())) {
			Count++;
			if ((packetTicks.containsKey(player.getUniqueId())) && (TimeUtil.elapsed(Time, 1000L))) {
				int maxPackets = 85;
				if (Count > maxPackets) {
					if (!PlayerUtil.isFullyStuck(player) && !PlayerUtil.isPartiallyStuck(player)) {
						if (player.getAllowFlight() || player.isFlying()) {
							return;
						}

						getAntiCheat().logCheat(this, player, "sent over " + Count  + " packets! ", "(Type: A)");
					}
				}
				if (Count > 400) {
					getAntiCheat().logCheat(this, player, Color.White + "Sent over " + Count  + " packets! " , "(Type: A)");
				}

				if (Count > 800) {
					getAntiCheat().logCheat(this, player, Color.White + "Kicked! sent over " + Count  + " packets! " , "(Type: A)");
					AntiCheat.Instance.getServer().getScheduler().runTask((Plugin)AntiCheat.Instance, new Runnable(){
						@SuppressWarnings("unused")
						Player p = event.getPlayer();
						@Override
						public void run() {
							player.kickPlayer("Too many packets");
						}
					});
				}
				Count = 0;
				Time = TimeUtil.nowlong();
			}
		}
		packetTicks.put(player.getUniqueId(), new AbstractMap.SimpleEntry<Integer, Long>(Count, Time));
		lastPacket.put(player.getUniqueId(), System.currentTimeMillis());
	}
}