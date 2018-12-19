package me.rida.anticheat.checks.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;

public class PingSpoofA extends Check {
	public PingSpoofA(AntiCheat AntiCheat) {
		super("PingSpoofA", "PingSpoof", CheckType.Player, true, false, false, false, true, 50, 1, 600000L, AntiCheat);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (getAntiCheat().getLag().getPing(p) > 500) {
			if (e.getFrom() != e.getTo()) {
				getAntiCheat().logCheat(this, p, "Ping: " + getAntiCheat().getLag().getPing(p), "(Type: A)");
			}
		}
	}
}
