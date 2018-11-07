package me.rida.anticheat.checks.combat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.rida.anticheat.checks.Check;
import me.rida.anticheat.other.Ping;
import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.utils.Color;
import me.rida.anticheat.utils.UtilCheat;
import me.rida.anticheat.utils.needscleanup.UtilsC;;

public class KillAuraK
extends Check {
    private double allowedDistance = 3.9;
    private int hitCount = 0;

    public KillAuraK(AntiCheat AntiCheat) {
        super("KillAuraK", "KillAura", AntiCheat);
		setEnabled(true);
		setMaxViolations(10);
		setBannable(false);
		setViolationsToNotify(1);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        int n;
        if (!(entityDamageByEntityEvent.getEntity() instanceof Player)) {
            return;
        }
        if (!(entityDamageByEntityEvent.getDamager() instanceof Player)) {
            return;
        }
        ++this.hitCount;
        Bukkit.getScheduler().runTaskLater((Plugin)AntiCheat.Instance, () -> {
            int hitCount = 0;
        }
        , 300);
        Player player = (Player)entityDamageByEntityEvent.getDamager();
        Player player2 = (Player)entityDamageByEntityEvent.getEntity();
        double d = UtilCheat.getHorizontalDistance(player.getLocation(), player2.getLocation());
        double d2 = this.allowedDistance;
        int n2 = Ping.getPing(player);
        int n3 = Ping.getPing(player2);
        int n4 = n2 + n3 / 2;
        int n5 = (int)((double)n4 * 0.0017);
        d2 += (double)n5;
        if (!player2.isSprinting()) {
            d2 += 0.2;
        }
        if (!player2.isOnGround()) {
            return;
        }
        for (PotionEffect potionEffect : player2.getActivePotionEffects()) {
            if (potionEffect.getType().getId() != PotionEffectType.SPEED.getId()) continue;
            n = potionEffect.getAmplifier() + 1;
            d2 += 0.15 * (double)n;
            break;
        }
        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            if (potionEffect.getType().getId() != PotionEffectType.SPEED.getId()) continue;
            n = potionEffect.getAmplifier() + 1;
            d2 += 0.15 * (double)n;
            break;
        }
        if (d > d2) {
        	getAntiCheat().logCheat(this, player, Color.Red + "Experemental" + " Heuristic (Flows)", "(Type: K)");
        }
    }
}

