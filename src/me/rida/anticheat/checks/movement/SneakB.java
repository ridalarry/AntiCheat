package me.rida.anticheat.checks.movement;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;

public class SneakB extends Check {

	public SneakB(AntiCheat AntiCheat) {
		super("SneakB", "Sneak", AntiCheat);

		setEnabled(true);
		setBannable(true);
		setMaxViolations(5);

	}

    public void onMove(PlayerMoveEvent e) {
    	 final Player p = e.getPlayer();
    	if (p.isSneaking()) {
    		if (p.isSprinting()) {
            getAntiCheat().logCheat(this, p, null, "(Type: B)");
    		}
    	}
    }
}
