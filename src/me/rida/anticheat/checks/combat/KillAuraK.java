package me.rida.anticheat.checks.combat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.rida.anticheat.checks.Check;
import me.rida.anticheat.other.Ping;
import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.utils.Color;
import me.rida.anticheat.utils.CheatUtil;

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

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent e) {
        int n;
        if (!(e.getEntity() instanceof Player) 
        		|| !(e.getDamager() instanceof Player)
        		|| !e.getEntity().isOnGround()) {
            return;
        }

        Player p = (Player)e.getDamager();
        Player p2 = (Player)e.getEntity();
        ++this.hitCount;
        Bukkit.getScheduler().runTaskLater((Plugin)AntiCheat.Instance, () -> {
            int hitCount = 0;
        }
        , 300);
        double d = CheatUtil.getHorizontalDistance(p.getLocation(), p2.getLocation());
        double d2 = this.allowedDistance;
        int n2 = Ping.getPing(p);
        int n3 = Ping.getPing(p2);
        int n4 = n2 + n3 / 2;
        int n5 = (int)((double)n4 * 0.0017);
        d2 += (double)n5;
        if (!p2.isSprinting()) {
            d2 += 0.2;
        }
        for (PotionEffect potionEffect : p2.getActivePotionEffects()) {
            if (potionEffect.getType().getId() != PotionEffectType.SPEED.getId()) continue;
            n = potionEffect.getAmplifier() + 1;
            d2 += 0.15 * (double)n;
            break;
        }
        for (PotionEffect potionEffect : p.getActivePotionEffects()) {
            if (potionEffect.getType().getId() != PotionEffectType.SPEED.getId()) continue;
            n = potionEffect.getAmplifier() + 1;
            d2 += 0.15 * (double)n;
            break;
        }
        if (d > d2) {
        	if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
                    || getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
        		return;
        	}
        	getAntiCheat().logCheat(this, p, Color.Red + "Experemental" + " Heuristic (Flows)", "(Type: K)");
        }
    }
}

