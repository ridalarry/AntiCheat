package me.rida.anticheat.checks.player;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.utils.*;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.packets.events.PacketPlayerEvent;

public class PacketsA extends Check {
	public static Map<UUID, Map.Entry<Integer, Long>> packetTicks;
	public static Map<UUID, Long> lastPacket;
	public List<UUID> blacklist;

	public PacketsA(AntiCheat AntiCheat) {
		super("PacketsA", "Packets", AntiCheat);
		setEnabled(true);
		setBannable(false);
		setMaxViolations(10);

		blacklist = new ArrayList<UUID>();
		lastPacket = new HashMap<UUID, Long>();
		packetTicks = new HashMap<UUID, Map.Entry<Integer, Long>>();
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void PlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		UUID u = p.getUniqueId();
		this.blacklist.add(u);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onLogout(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		UUID u = p.getUniqueId();
		if (packetTicks.containsKey(u)) {
			packetTicks.remove(u);
		}
		if (lastPacket.containsKey(u)) {
			lastPacket.remove(u);
		}
		if (blacklist.contains(u)) {
			blacklist.remove(u);
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void PlayerChangedWorld(PlayerChangedWorldEvent e) {
		Player p = e.getPlayer();
		UUID u = p.getUniqueId();
		blacklist.add(u);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void PlayerRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		UUID u = p.getUniqueId();
		blacklist.add(u);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public final void PacketPlayer(PacketPlayerEvent e) {
		Player p = e.getPlayer();
		UUID u = p.getUniqueId();
		if (!getAntiCheat().isEnabled()
				|| (p.getGameMode().equals(GameMode.CREATIVE)
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| getAntiCheat().getLag().getPing(p) < getAntiCheat().getPingCancel()
				|| getAntiCheat().lag.getPing(p) > 200)
				|| getAntiCheat().lag.getTPS() > 21.0D){
			return;
		}

		int Count = 0;
		long Time = System.currentTimeMillis();
		if (packetTicks.containsKey(u)) {
			Count = packetTicks.get(u).getKey().intValue();
			Time = packetTicks.get(u).getValue().longValue();
		}
		if (lastPacket.containsKey(u)) {
			long MS = System.currentTimeMillis() - lastPacket.get(u).longValue();
			if (MS >= 100L) {
				blacklist.add(u);
			} else if ((MS > 1L) && (this.blacklist.contains(u))) {
				blacklist.remove(u);
			}
		}
		if (!blacklist.contains(u)) {
			Count++;
			if ((packetTicks.containsKey(u)) && (TimeUtil.elapsed(Time, 1000L))) {
				int maxPackets = 50;
				if (Count > maxPackets) {
					if (!PlayerUtil.isFullyStuck(p) && !PlayerUtil.isPartiallyStuck(p)) {
						getAntiCheat().logCheat(this, p, "sent over " + Count  + " packets! ", "(Type: A)");
					}
				}
				if (Count > 400) {
					getAntiCheat().logCheat(this, p, "sent over " + Count  + " packets! ", "(Type: A)");
				}
				if (Count > 800) {
						getAntiCheat().logCheat(this, p, "sent over " + Count  + " packets! ", "(Type: A)");
				}
				if (Count > 1000) {
					getAntiCheat().logCheat(this, p, Color.Red + "Kicked, " + Color.White + "sent over " + Count  + " packets! " , "(Type: A)");				        	
					AntiCheat.Instance.getServer().getScheduler().runTask((Plugin)AntiCheat.Instance, new Runnable(){
			        	final Player p = e.getPlayer();
			            @Override
			            public void run() {
			                p.kickPlayer("Too many packets");
			            }
			        });
			    }
					Count = 0;
				Time = TimeUtil.nowlong();
			}
		}
		packetTicks.put(u, new AbstractMap.SimpleEntry<Integer, Long>(Count, Time));
		lastPacket.put(u, System.currentTimeMillis());
	}
}
