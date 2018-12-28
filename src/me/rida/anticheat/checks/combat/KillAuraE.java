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
import me.rida.anticheat.utils.CheatUtil;

public class KillAuraE extends Check {
	public static double allowedDistance = 3.9;
	public static int hitCount = 0;

	public KillAuraE(AntiCheat AntiCheat) {
		super("KillAuraE", "KillAura",  CheckType.Combat, true, false, false, false, true, 20, 3, 600000L, AntiCheat);
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
		double dst = CheatUtil.getHorizontalDistance(p.getLocation(), p2.getLocation());
		double allowedDistance = KillAuraE.allowedDistance;
		int atckrPing = getAntiCheat().getLag().getPing(p);
		int dmgdPing = getAntiCheat().getLag().getPing(p2);
		int ping = atckrPing + dmgdPing / 2;
		int finalPing = (int)((double)ping * 0.0017);
		allowedDistance += (double)finalPing;
		if (!p2.isSprinting()) {
			allowedDistance += 0.2;
		}
		for (PotionEffect potionEffect : p2.getActivePotionEffects()) {
			if (potionEffect.getType().getId() != PotionEffectType.SPEED.getId()) continue;
			n = potionEffect.getAmplifier() + 1;
			allowedDistance += 0.15 * (double)n;
			break;
		}
		for (PotionEffect potionEffect : p.getActivePotionEffects()) {
			if (potionEffect.getType().getId() != PotionEffectType.SPEED.getId()) continue;
			n = potionEffect.getAmplifier() + 1;
			allowedDistance += 0.15 * (double)n;
			break;
		}
		if (dst > allowedDistance) {
			if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
					|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
				return;
			}
			getAntiCheat().logCheat(this, p, "Heuristic Flows", "(Type: E)");
		}
	}
}