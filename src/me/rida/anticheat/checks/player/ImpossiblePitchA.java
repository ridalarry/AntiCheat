package me.rida.anticheat.checks.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;

public class ImpossiblePitchA extends Check {
      public ImpossiblePitchA(AntiCheat AntiCheat) {
        super("ImpossiblePitchA", "ImpossiblePitch", CheckType.Player, AntiCheat);
		setEnabled(true);
		setMaxViolations(10);
		setBannable(true);
    }
  	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
  	private void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        double x = p.getLocation().getPitch();
        if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				        || getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
        	return;
        }
        if (x > 90 || x < -90) {
            getAntiCheat().logCheat(this, p, "Head went back too far. Pitch: " + x, "(Type: A)");
        }
    }
}
