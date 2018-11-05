package me.rida.anticheat.checks.experemintal;

import java.text.DecimalFormat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.rida.anticheat.checks.Check;
import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.utils.AngleUtil;
import me.rida.anticheat.utils.Color;
import me.rida.anticheat.utils.needscleanup.UtilsC;

public class KillAuraH
extends Check {
    public KillAuraH(AntiCheat AntiCheat) {
        super("KillAuraH", "KillAura", AntiCheat);
		setEnabled(true);
		setMaxViolations(10);
		setBannable(false);
		setViolationsToNotify(1);
    }

    @EventHandler
    public void onAngleHit(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (!(entityDamageByEntityEvent.getEntity() instanceof Player)) {
            return;
        }
        if (!(entityDamageByEntityEvent.getDamager() instanceof Player)) {
            return;
        }
        Player player = (Player)entityDamageByEntityEvent.getDamager();
        Player player2 = (Player)entityDamageByEntityEvent.getEntity();
        double d = UtilsC.getPing(player);
        double d2 = UtilsC.getPing(player2);
        double d3 = AngleUtil.getOffsets(player, (LivingEntity)player2)[0];
        if (d2 > 450.0) {
            return;
        }
        if (d >= 100.0 && d < 200.0) {
            d3 -= 50.0;
        } else if (d >= 200.0 && d < 250.0) {
            d3 -= 75.0;
        } else if (d >= 250.0 && d < 300.0) {
            d3 -= 150.0;
        } else if (d >= 300.0 && d < 350.0) {
            d3 -= 300.0;
        } else if (d >= 350.0 && d < 400.0) {
            d3 -= 350.0;
        } else if (d > 400.0) {
            return;
        }
        if (d3 >= 300.0) {
        	getAntiCheat().logCheat(this, player, Color.Red + "Experemental", "(Type: H)");
        }
    }
}

