package me.rida.anticheat.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.rida.anticheat.AntiCheat;

public class JoinQuitEvent implements Listener {

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent e) {
    	AntiCheat.getInstance().getDataManager().addPlayerData(e.getPlayer());
    }
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onJoin(PlayerQuitEvent e) {
    	AntiCheat.getInstance().getDataManager().removePlayerData(e.getPlayer());
    }
}
