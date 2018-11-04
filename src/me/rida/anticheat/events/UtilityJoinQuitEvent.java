package me.rida.anticheat.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.rida.anticheat.AntiCheat;

public class UtilityJoinQuitEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
    	AntiCheat.getInstance().getDataManager().addPlayerData(e.getPlayer());
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerQuitEvent e) {
    	AntiCheat.getInstance().getDataManager().removePlayerData(e.getPlayer());
    }
}
