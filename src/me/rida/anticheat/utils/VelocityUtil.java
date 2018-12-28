package me.rida.anticheat.utils;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerVelocityEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.data.DataPlayer;

public class VelocityUtil implements Listener {
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onMove(PlayerMoveEvent e) {
		final Player p = e.getPlayer();
		final DataPlayer data = AntiCheat.getInstance().getDataManager().getData(p);
		if (data != null) {
			if (data.isDidTakeVelocity()) {
				if (TimerUtil.elapsed(data.getLastVelMS(),2000L)) {
					data.setDidTakeVelocity(false);
				}
			}
		}
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onVelEvent(PlayerVelocityEvent e) {
		final Player p = e.getPlayer();
		final DataPlayer data = AntiCheat.getInstance().getDataManager().getData(p);
		if (data != null) {
			data.setDidTakeVelocity(true);
			data.setLastVelMS(TimerUtil.nowlong());
		}
	}
	public static boolean didTakeVelocity(Player p) {
		boolean out = false;
		final DataPlayer data = AntiCheat.getInstance().getDataManager().getData(p);
		if (data != null && data.isDidTakeVelocity()) {
			out = true;
		}
		return out;
	}
}