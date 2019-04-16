package me.rida.anticheat.checks.combat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerToggleSprintEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.events.SharedEvents;

public class AntiKBB extends Check {
	public AntiKBB(AntiCheat AntiCheat) {
		super("AntiKBB", "AntiKB",  CheckType.Combat, true, false, false, false, true, 10, 1, 600000L, AntiCheat);
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void Sprint(PlayerToggleSprintEvent e) {
		final Player p = e.getPlayer();
		if (p == null) {
			return;
		}
		if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
			return;
		}

		if (e.isSprinting() && SharedEvents.getLastSprintStop().containsKey(p)) {
			int n = 0;
			final int n2 = 1;
			final long lastSprintStop = System.currentTimeMillis() - SharedEvents.getLastSprintStop().get(p);
			n = lastSprintStop < 5 ? ++n : (lastSprintStop > 1000 ? --n : (n -= 2));
			if (n > n2) {
				getAntiCheat().logCheat(this, p, "Took less knockback without sprinting", "(Type: B)");
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