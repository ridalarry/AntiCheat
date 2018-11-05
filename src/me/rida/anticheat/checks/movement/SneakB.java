package me.rida.anticheat.checks.movement;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    	 final Player player = e.getPlayer();
    	if (player.isSneaking()) {
    		if (player.isSprinting()) {
            getAntiCheat().logCheat(this, player, null, "(Type: B)");
    		}
    	}
    }
}
