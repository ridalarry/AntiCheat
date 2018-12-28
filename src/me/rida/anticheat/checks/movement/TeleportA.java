package me.rida.anticheat.checks.movement;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.events.SharedEvents;

public class TeleportA extends Check{
	public TeleportA (AntiCheat AntiCheat) {
		super("TeleportA", "Teleport", CheckType.Movement, true, false, false, false, false, 10, 1, 600000L, AntiCheat);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	private void onTeleport(PlayerTeleportEvent e) {
		final Player p = e.getPlayer();
		if (SharedEvents.getLastJoin().containsKey(p)) {
			if (System.currentTimeMillis() - SharedEvents.getLastJoin().get(p) < 1000) {
				return;
			}
		}
		if (e.getCause() == TeleportCause.UNKNOWN) {
			this.getAntiCheat().logCheat(this, p, "Unknown teleport cause", "(Type: A)");
			e.setCancelled(true);

			if (VClipA.lastLocation.containsKey(p)) {
				p.teleport(VClipA.lastLocation.get(p));
			}
		}
	}
}
