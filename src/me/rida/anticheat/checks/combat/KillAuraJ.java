package me.rida.anticheat.checks.combat;

import java.text.DecimalFormat;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.rida.anticheat.checks.Check;
import me.rida.anticheat.utils.Color;
import me.rida.anticheat.AntiCheat;

public class KillAuraJ
extends Check {
    private float lastYaw;
    private float lastBad;

    public KillAuraJ(AntiCheat AntiCheat) {
        super("KillAuraJ", "KillAura", AntiCheat);
		setEnabled(true);
		setMaxViolations(10);
		setBannable(false);
		setViolationsToNotify(1);
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        float f;
        if (!(entityDamageByEntityEvent.getDamager() instanceof Player)) {
            return;
        }
        if (!(entityDamageByEntityEvent.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player)entityDamageByEntityEvent.getDamager();
        this.lastYaw = f = player.getLocation().getYaw();
        float f2 = Math.abs(f - this.lastYaw) % 180.0f;
    }

    public boolean onAim(Player player, float f) {
        float f2 = Math.abs(f - this.lastYaw) % 180.0f;
        this.lastYaw = f;
        this.lastBad = (float)Math.round(f2 * 10.0f) * 0.1f;
        if ((double)f < 0.1) {
            return true;
        }
        if (f2 > 1.0f && (float)Math.round(f2 * 10.0f) * 0.1f == f2 && (float)Math.round(f2) != f2) {
            if (f2 == this.lastBad) {
            	getAntiCheat().logCheat(this, player, Color.Red + "Experemental" + " Huzuni [V5X01]", "(Type: J)");
                return true;
            }
        } else {
            return true;
        }
        return false;
    }
}

