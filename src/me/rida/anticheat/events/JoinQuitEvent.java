package me.rida.anticheat.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.utils.ServerUtil;

public class JoinQuitEvent implements Listener {

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onJoin(PlayerJoinEvent e) {
		if (ServerUtil.isBukkitVerison("1_7")) {
			if (e.getPlayer().hasPermission("anticheat.staff")) {
				AntiCheat.AlertsOn.add(e.getPlayer());
			}
		}
		if(AntiCheat.isInPhaseTimer(e.getPlayer())) {
			AntiCheat.timerLeft.put(e.getPlayer().getName().toString(), 3);
		}else {
			AntiCheat.getInstance().startTimerPhaseCheck(e.getPlayer());
		}


		AntiCheat.getInstance().getDataManager().addPlayerData(e.getPlayer());
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onJoin(PlayerQuitEvent e) {
		AntiCheat.getInstance().getDataManager().removePlayerData(e.getPlayer());
	}
}