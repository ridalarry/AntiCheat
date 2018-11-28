package me.rida.anticheat.checks.combat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.other.Ping;
import me.rida.anticheat.utils.CheatUtil;
import me.rida.anticheat.utils.Color;

public class KillAuraE extends Check {
    public static double allowedDistance = 3.9;
    public static int hitCount = 0;

    public KillAuraE(AntiCheat AntiCheat) {
        super("KillAuraE", "KillAura",  CheckType.Combat, AntiCheat);
		setEnabled(true);
		setMaxViolations(20);
		setBannable(false);
		setViolationsToNotify(3);
    }

	@SuppressWarnings({ "unused", "deprecation" })
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	private void onDamage(EntityDamageByEntityEvent e) {
        int n;
        if (!(e.getEntity() instanceof Player) 
        		|| !(e.getDamager() instanceof Player)
        		|| !e.getEntity().isOnGround()) {
            return;
        }

        Player p = (Player)e.getDamager();
        Player p2 = (Player)e.getEntity();
        ++KillAuraE.hitCount;
        Bukkit.getScheduler().runTaskLater((Plugin)AntiCheat.Instance, () -> {
            int hitCount = 0;
        }
        , 300);
        double d = CheatUtil.getHorizontalDistance(p.getLocation(), p2.getLocation());
        double d2 = KillAuraE.allowedDistance;
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
        	getAntiCheat().logCheat(this, p, Color.Red + "Experemental" + " Heuristic Flows", "(Type: E)");
        }
    }
}

