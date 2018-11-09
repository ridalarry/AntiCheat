package me.rida.anticheat.checks.combat;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;

public class FastBowA extends Check {
	public static Map<Player, Long> bowPull;
	public static Map<Player, Integer> count;

	public FastBowA(AntiCheat AntiCheat) {
		super("FastBowA", "FastBow", AntiCheat);
		
		bowPull = new HashMap<Player, Long>();
		count = new HashMap<Player, Integer>();
		
		setViolationsToNotify(1);
		setMaxViolations(7);

		setEnabled(true);
		setBannable(true);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void Interact(final PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.getItemInHand() != null && p.getItemInHand().getType().equals(Material.BOW)) {
			bowPull.put(p, System.currentTimeMillis());
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onLogout(PlayerQuitEvent e) {
		if (bowPull.containsKey(e.getPlayer())) {
			bowPull.remove(e.getPlayer());
		}

		if (count.containsKey(e.getPlayer())) {
			count.remove(e.getPlayer());
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onShoot(final ProjectileLaunchEvent e) {
		if (!this.isEnabled()) {
			return;
		}
		if (e.getEntity() instanceof Arrow) {
			Arrow arrow = (Arrow) e.getEntity();
			if (arrow.getShooter() != null && arrow.getShooter() instanceof Player) {
				Player p = (Player) arrow.getShooter();
				if (bowPull.containsKey(p)) {
					Long time = System.currentTimeMillis() - this.bowPull.get(p);
					double power = arrow.getVelocity().length();
					Long timeLimit = 300L;
					int Count = 0;
					if (count.containsKey(p)) {
						Count = count.get(p);
					}
					if (power > 2.5 && time < timeLimit) {
						count.put(p, Count + 1);
					} else {
						count.put(p, Count > 0 ? Count - 1 : Count);
					}
					if (Count > 8) {
						if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
		                || getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
							return;
						}
						getAntiCheat().logCheat(this, p, time + " ms", "(Type: A)");
						count.remove(p);
					}
				}
			}
		}
	}
}