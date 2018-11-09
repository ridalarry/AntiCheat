package me.rida.anticheat.checks.other;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.utils.Color;


public class BlockInteractB extends Check {
	public static Map<UUID, Map.Entry<Integer, Long>> speedTicks;

	public BlockInteractB(AntiCheat AntiCheat) {
		super("BlockInteractB", "BlockInteract", AntiCheat);

		setEnabled(true);
		setBannable(false);
		setMaxViolations(5);
		
		speedTicks = new HashMap<UUID, Map.Entry<Integer, Long>>();
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onInteract(PlayerInteractEvent e) {
		if ((e.getAction() == Action.RIGHT_CLICK_BLOCK)
				&& e.getItem() != null) {
			if (e.getItem().equals(Material.EXP_BOTTLE) || e.getItem().getType().equals(Material.GLASS_BOTTLE)
					|| e.getItem().getType().equals(Material.POTION)) {
				return;
			}
			Player p = e.getPlayer();
			UUID u = p.getUniqueId();
			
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
				getAntiCheat().logCheat(this, p, Color.Red + "Experimental! " + "Level: " + level + " Ping: " + getAntiCheat().lag.getPing(p), "(Type: B)");
				if (level > max) {
					level = max / 4;
				}
			} else if (level < 0) {
				level = 0;
			}
			speedTicks.put(u,
					new AbstractMap.SimpleEntry<Integer, Long>(level, System.currentTimeMillis()));
		}
	}
}
