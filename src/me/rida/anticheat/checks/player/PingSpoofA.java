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
		final Player p = e.getPlayer();
		final int ping1 = getAntiCheat().getLag().getPing(p);
		final int ping2 = getAntiCheat().getLag().getPing2(p);
		final int ping3 = getAntiCheat().getLag().getPing3(p);
		if (ping1 > 500 && ping2 > 500 && ping3 > 500) {
			if (e.getFrom() != e.getTo()) {
				getAntiCheat().logCheat(this, p, "Ping1: " + ping1 + "; Ping2: " + ping2 + "; Ping3: " + ping3, "(Type: A)");
			}
		}
	}
}
