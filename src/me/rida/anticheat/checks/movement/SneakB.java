package me.rida.anticheat.checks.movement;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;

public class SneakB extends Check {

	public SneakB(AntiCheat AntiCheat) {
		super("SneakB", "Sneak", CheckType.Movement, AntiCheat);

		setEnabled(true);
		setBannable(true);
		setMaxViolations(5);

	}

	@SuppressWarnings("unused")
	private void onMove(PlayerMoveEvent e) {
    	 final Player p = e.getPlayer();
    	if (p.isSneaking()) {
    		if (p.isSprinting()) {
    			if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
		        || getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
    				return;
    			}
            getAntiCheat().logCheat(this, p, null, "(Type: B)");
    		}
    	}
    }
}
