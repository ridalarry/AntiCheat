package me.rida.anticheat.checks.combat;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.utils.Color;

public class AimAssistA extends Check {
	private int aimAssist = 0;

	public AimAssistA(AntiCheat AntiCheat) {
		super("AimAssistA", "AimAssist",  CheckType.Combat, true, false, false, false, 10, 1, 600000L, AntiCheat);
	}

	private static double getFrac(double d) {
		return d % 1.0;
	}

	private void setAimAssest(int n) {
		this.aimAssist = n;
		if (this.aimAssist < 0) {
			this.aimAssist = 0;
		}
	}

	private int getAimAssist() {
		return this.aimAssist;
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onMove(PlayerMoveEvent e) {
		Location location = e.getFrom().clone();
		Location location2 = e.getTo().clone();
		Player p = e.getPlayer();
		double d = Math.abs(location.getYaw() - location2.getYaw());
		if (d > 0.0 && d < 360.0) {
			if (AimAssistA.getFrac(d) == 0.0) {
				this.setAimAssest(this.getAimAssist() + 100);
				if (this.getAimAssist() > 2000) {

					if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
							|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
						return;
					}
					getAntiCheat().logCheat(this, p, Color.Red + "Experemental", "(Type: A)");
					this.setAimAssest(0);
				}
			} else {
				this.setAimAssest(this.getAimAssist() - 21);
			}
		}
	}
}