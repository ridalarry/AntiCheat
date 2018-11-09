package me.rida.anticheat.checks.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;

public class ImpossiblePitchA extends Check {
      public ImpossiblePitchA(AntiCheat AntiCheat) {
        super("ImpossiblePitchA", "ImpossiblePitch", AntiCheat);
		setEnabled(true);
		setMaxViolations(10);
		setBannable(true);
    }
  	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        double x = p.getLocation().getPitch();
        if (x > 90 || x < -90) {
            getAntiCheat().logCheat(this, p, "Head went back too far. Pitch: " + x, "(Type: A)");
        }
    }
}
