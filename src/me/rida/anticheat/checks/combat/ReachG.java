package me.rida.anticheat.checks.combat;

import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.utils.MathUtil;

public class ReachG extends Check {
	public ReachG(AntiCheat AntiCheat) {
		super("ReachG", "Reach",  CheckType.Combat, true, false, false, false, false, 20, 1, 600000L, AntiCheat);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onAttack(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			if (event.getEntity().getType() == EntityType.PLAYER) {
				Player player = (Player) event.getDamager();
				if (player.getGameMode() != GameMode.CREATIVE) {
					double distance = MathUtil.getDistance3D(player.getLocation(), event.getEntity().getLocation());
					final int ping = getAntiCheat().getLag().getPing(player);
					double tps = getAntiCheat().getLag().getTPS();
					String dist = Double.toString(distance).substring(0, 3);
					double maxReach = 3.9;
					MathUtil.Distance(player.getLocation(), event.getEntity().getLocation());

					if (player.hasPotionEffect(PotionEffectType.SPEED)) {
						if (MathUtil.getxDiff() > maxReach + 1 || MathUtil.getzDiff() > maxReach + 1) {
							if (player != null) {
								getAntiCheat().logCheat(this, player, "Interact too far away; distance: " + dist + "; Ping: " + ping + "; TPS: " + tps, "(Type: G)");
							} 
						}
					} else {
						if (MathUtil.getxDiff() > maxReach || MathUtil.getzDiff() > maxReach) {
							if (player != null) {
								event.setCancelled(true);
								getAntiCheat().logCheat(this, player, "Interact too far away; distance: " + dist + "; Ping: " + ping + "; TPS: " + tps, "(Type: G)");
							} 
						}
					}
				}

			}
		}
	}
}