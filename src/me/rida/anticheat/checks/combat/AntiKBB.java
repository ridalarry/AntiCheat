package me.rida.anticheat.checks.combat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerToggleSprintEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.events.SharedEvents;
import me.rida.anticheat.utils.Color;

public class AntiKBB extends Check {
	public AntiKBB(AntiCheat AntiCheat) {
		super("AntiKBB", "AntiKB",  CheckType.Combat, true, false, false, false, 10, 1, 600000L, AntiCheat);
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void Sprint(PlayerToggleSprintEvent e) {
		Player p = e.getPlayer();
		if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
			return;
		}

		if (e.isSprinting() && SharedEvents.getLastSprintStop().containsKey((Object)p)) {
			int n = 0;
			int n2 = 1;
			long l = System.currentTimeMillis() - SharedEvents.getLastSprintStop().get((Object)p);
			n = l < 5 ? ++n : (l > 1000 ? --n : (n -= 2));
			if (n > n2) {
				getAntiCheat().logCheat(this, p, Color.Red + "Experemental", "(Type: B)");
				n = 0;
			}
		}
		if (!e.isSprinting()) {
			SharedEvents.getLastSprintStop().put(p, System.currentTimeMillis());
		} else if (e.isSprinting()) {
			SharedEvents.getLastSprintStart().put(p, System.currentTimeMillis());
		}
	}
}