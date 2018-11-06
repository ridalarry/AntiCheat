package me.rida.anticheat.checks.combat;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.utils.Color;

public class AimAssistB
extends Check {
    private int smoothAim = 0;

    public AimAssistB(AntiCheat AntiCheat) {
        super("AimAssistB", "AimAssist", AntiCheat);
		setEnabled(true);
		setMaxViolations(10);
		setBannable(false);
		setViolationsToNotify(1);
    }

    public static double getFrac(double d) {
        return d % 1.0;
    }

    public void setSmoothAim(int n) {
        this.smoothAim = n;
        if (this.smoothAim < 0) {
            this.smoothAim = 0;
        }
    }

    public int getSmoothAim() {
        return this.smoothAim;
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerMove(PlayerMoveEvent playerMoveEvent) {
        Location location = playerMoveEvent.getFrom().clone();
        Location location2 = playerMoveEvent.getTo().clone();
        Player player = playerMoveEvent.getPlayer();
        double d = Math.abs(location.getYaw() - location2.getYaw());
        if (d > 0.0 && d < 360.0) {
            if (AimAssistB.getFrac(d) == 0.0) {
                this.setSmoothAim(this.getSmoothAim() + 100);
                if (this.getSmoothAim() > 2000) {
                	getAntiCheat().logCheat(this, player, Color.Red + "Experemental", "(Type: B)");
                    this.setSmoothAim(0);
                }
            } else {
                this.setSmoothAim(this.getSmoothAim() - 21);
            }
        }
    }
}

