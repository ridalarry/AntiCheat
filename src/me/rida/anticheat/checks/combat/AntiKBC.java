package me.rida.anticheat.checks.combat;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.utils.VelocityUtil;
public class AntiKBC extends Check {
	public AntiKBC(AntiCheat AntiCheat) {
		super("AntiKBC", "AntiKB",  CheckType.Combat, true, false, false, false, true, 10, 1, 600000L, AntiCheat);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onPlayerDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof  Player) {
			Player p = (Player) e.getEntity();
			if(p.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
				Entity damager = (Player) p.getLastDamageCause().getEntity();
				if (VelocityUtil.didTakeVelocity(p)
						|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
						|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
					return;
				}
				else {
					if (!(VelocityUtil.didTakeVelocity(p))) {
						if(p.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
							if (damager != p){
								getAntiCheat().logCheat(this, p, "Did not take velocity", "(Type: C)");
							}
							else {
								return;
							}	
						}

					}
				}
			}

		}
	}
} 