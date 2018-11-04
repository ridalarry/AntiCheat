package me.rida.anticheat.checks.other;

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
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.packets.events.PacketPlayerEventB;
import me.rida.anticheat.utils.Color;
import me.rida.anticheat.utils.SetBackSystem;
import me.rida.anticheat.utils.UtilTime;
import me.rida.anticheat.utils.needscleanup.UtilsB;

public class MorePacketsB extends Check {
	public static Map<UUID, Map.Entry<Integer, Long>> packetTicks;
	public static Map<UUID, Long> lastPacket;
	public List<UUID> blacklist;

	public MorePacketsB(AntiCheat AntiCheat) {
		super("MorePackets", "MorePackets (Type: B)", AntiCheat);

		setEnabled(true);
		setBannable(false);
		setMaxViolations(10);

		blacklist = new ArrayList<UUID>();
		lastPacket = new HashMap<UUID, Long>();
		packetTicks = new HashMap<UUID, Map.Entry<Integer, Long>>();
	}

	@EventHandler
	public void PlayerJoin(PlayerJoinEvent event) {
		this.blacklist.add(event.getPlayer().getUniqueId());
	}

	@EventHandler
	public void onLogout(PlayerQuitEvent e) {
		if (packetTicks.containsKey(e.getPlayer().getUniqueId())) {
			packetTicks.remove(e.getPlayer().getUniqueId());
		}
		if (lastPacket.containsKey(e.getPlayer().getUniqueId())) {
			lastPacket.remove(e.getPlayer().getUniqueId());
		}
		if (blacklist.contains(e.getPlayer().getUniqueId())) {
			blacklist.remove(e.getPlayer().getUniqueId());
		}
	}

	@EventHandler
	public void PlayerChangedWorld(PlayerChangedWorldEvent event) {
		blacklist.add(event.getPlayer().getUniqueId());
	}

	@EventHandler
	public void PlayerRespawn(PlayerRespawnEvent event) {
		blacklist.add(event.getPlayer().getUniqueId());
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void PacketPlayer(PacketPlayerEventB event) {
		Player player = event.getPlayer();
		if (!getAntiCheat().isEnabled()) {
			return;
		}
		if (getAntiCheat().isSotwMode()) {
			return;
		}
		if (player.getGameMode().equals(GameMode.CREATIVE)) {
			return;
		}
		if (getAntiCheat().lag.getTPS() > 21.0D || getAntiCheat().lag.getTPS() < getAntiCheat().getTPSCancel()) {
			return;
		}

		if (getAntiCheat().lag.getPing(player) > 200) {
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
			} else if ((MS > 1L) && (this.blacklist.contains(player.getUniqueId()))) {
				blacklist.remove(player.getUniqueId());
			}
		}
		if (!blacklist.contains(player.getUniqueId())) {
			Count++;
			if ((packetTicks.containsKey(player.getUniqueId())) && (UtilTime.elapsed(Time, 1000L))) {
				int maxPackets = 50;
				if (Count > maxPackets) {
					if (!UtilsB.isFullyStuck(player) && !UtilsB.isPartiallyStuck(player)) {
						getAntiCheat().logCheat(this, player, "sent over " + Count  + " packets! ", "(Type: B)");
						SetBackSystem.setBack(player);
					}
				}
				if (Count > 400) {
					getAntiCheat().logCheat(this, player, Color.Red + "Kicked, " + Color.White + "sent over " + Count  + " packets! " , null);
					player.kickPlayer("Too many packets.");
				}
				Count = 0;
				Time = UtilTime.nowlong();
			}
		}
		packetTicks.put(player.getUniqueId(), new AbstractMap.SimpleEntry<Integer, Long>(Count, Time));
		lastPacket.put(player.getUniqueId(), System.currentTimeMillis());
	}
}
