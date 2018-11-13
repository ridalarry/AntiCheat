package me.rida.anticheat.checks.combat;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;

public class KillAuraE extends Check {
	public static Map<Player, Map.Entry<Integer, Long>> lastAttack;

	public KillAuraE(AntiCheat AntiCheat) {
		super("KillAuraE", "KillAura",  CheckType.Combat, AntiCheat);
		
		lastAttack = new HashMap<>();

		setEnabled(true);
		setBannable(false);

		setViolationsToNotify(1);
		setMaxViolations(7);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onLog(PlayerQuitEvent e) {
		if (lastAttack.containsKey(e.getPlayer())) {
			lastAttack.remove(e.getPlayer());
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void Damage(EntityDamageByEntityEvent e) {
		if (e.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK
				|| !((e.getEntity()) instanceof Player)
				|| !(e.getDamager() instanceof Player)) {
			return;
		}

		Player p = (Player) e.getDamager();
		if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
                || getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
			return;
		}
		if (lastAttack.containsKey(p)) {
			Integer entityid = lastAttack.get(p).getKey();
			Long time = lastAttack.get(p).getValue();
			if (entityid != e.getEntity().getEntityId() && System.currentTimeMillis() - time < 6L) {
				this.getAntiCheat().logCheat(this, p, "MultiAura", "(Type: E)");
			}
			lastAttack.remove(p);
		} else {
			lastAttack.put(p, new AbstractMap.SimpleEntry<Integer, Long>(e.getEntity().getEntityId(),
					System.currentTimeMillis()));
		}
	}
}