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
    private int aimAssist = 0;

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

    public void setAimAssest(int n) {
        this.aimAssist = n;
        if (this.aimAssist < 0) {
            this.aimAssist = 0;
        }
    }

    public int getAimAssist() {
        return this.aimAssist;
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerMove(PlayerMoveEvent playerMoveEvent) {
        Location location = playerMoveEvent.getFrom().clone();
        Location location2 = playerMoveEvent.getTo().clone();
        Player player = playerMoveEvent.getPlayer();
        double d = Math.abs(location.getYaw() - location2.getYaw());
        if (d > 0.0 && d < 360.0) {
            if (AimAssistB.getFrac(d) == 0.0) {
                this.setAimAssest(this.getAimAssist() + 100);
                if (this.getAimAssist() > 2000) {
                	getAntiCheat().logCheat(this, player, Color.Red + "Experemental", "(Type: B)");
                    this.setAimAssest(0);
                }
            } else {
                this.setAimAssest(this.getAimAssist() - 21);
            }
        }
    }
}

