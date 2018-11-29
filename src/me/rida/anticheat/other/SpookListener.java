package me.rida.anticheat.other;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.rida.anticheat.checks.client.SpookA;
import me.rida.anticheat.utils.MathUtil;

public class SpookListener implements Listener {
	public void playerSpookCheck(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player)) {
			return;
		}
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getDamager();
		Player p2 = (Player) e.getEntity();
		float f = MathUtil.getOffset(p, p2);
		SpookA.SpookAInstance().onAim(p, f);
	}
}