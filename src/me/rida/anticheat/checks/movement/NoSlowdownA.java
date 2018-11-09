package me.rida.anticheat.checks.movement;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.utils.Color;
import me.rida.anticheat.utils.MathUtil;

public class NoSlowdownA extends Check {

	public static Map<UUID, Map.Entry<Integer, Long>> speedTicks;

	public NoSlowdownA(AntiCheat AntiCheat) {
		super("NoSlowdownA", "NoSlowdown", AntiCheat);

		setEnabled(true);
		setBannable(false);

		setMaxViolations(5);
		
		speedTicks = new HashMap<UUID, Map.Entry<Integer, Long>>();
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent e) {
		if (speedTicks.containsKey(e.getPlayer().getUniqueId())) {
			speedTicks.remove(e.getPlayer().getUniqueId());
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void BowShoot(final EntityShootBowEvent e) {
		if (!this.isEnabled()) {
			return;
		}
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		final Player p = (Player) e.getEntity();
		if (p.isInsideVehicle()) {
			return;
		}
		if (p.isSprinting()) {
			getAntiCheat().logCheat(this, p, "Sprinting while bowing.", "(Type: A)");
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onMove(PlayerMoveEvent e) {
		if (e.getTo().getX() == e.getFrom().getX() && e.getFrom().getY() == e.getTo().getY()
				&& e.getTo().getZ() == e.getFrom().getZ()) {
			return;
		}
		Player p = e.getPlayer();
		double OffsetXZ = MathUtil.offset(MathUtil.getHorizontalVector(e.getFrom().toVector()),
				MathUtil.getHorizontalVector(e.getTo().toVector()));

		if (!p.getLocation().getBlock().getType().equals(Material.WEB) || (OffsetXZ < 0.2)) {
			return;
		}

		getAntiCheat().logCheat(this, p, "Offset: " + OffsetXZ, "(Type: A)");
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onInteract(PlayerInteractEvent e) {
		if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
				&& e.getItem() != null) {
			if (e.getItem().equals(Material.EXP_BOTTLE) || e.getItem().getType().equals(Material.GLASS_BOTTLE)
					|| e.getItem().getType().equals(Material.POTION)) {
				return;
			}
			Player p = e.getPlayer();
			
			long Time = System.currentTimeMillis();
			int level = 0;
			if (speedTicks.containsKey(p.getUniqueId())) {
				level = speedTicks.get(p.getUniqueId()).getKey().intValue();
				Time = speedTicks.get(p.getUniqueId()).getValue().longValue();
			}
			double diff = System.currentTimeMillis() - Time;
			level = diff >= 2.0
					? (diff <= 51.0 ? (level += 2)
							: (diff <= 100.0 ? (level += 0) : (diff <= 500.0 ? (level -= 6) : (level -= 12))))
					: ++level;
			int max = 50;
			if (level > max * 0.9D && diff <= 100.0D) {
				if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					return;
				}
				getAntiCheat().logCheat(this, p, Color.Red + "Expermintal! " + "Level: " + level + " Ping: " + getAntiCheat().lag.getPing(p), "(Type: A)");
				if (level > max) {
					level = max / 4;
				}
			} else if (level < 0) {
				level = 0;
			}
			speedTicks.put(p.getUniqueId(),
					new AbstractMap.SimpleEntry<Integer, Long>(level, System.currentTimeMillis()));
		}
	}
}
