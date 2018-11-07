package me.rida.anticheat.checks.movement;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.packets.events.PacketEntityActionEvent;
import me.rida.anticheat.utils.UtilTime;

public class SneakA extends Check {
	public static Map<UUID, Map.Entry<Integer, Long>> sneakTicks;

	public SneakA(AntiCheat AntiCheat) {
		super("SneakA", "Sneak", AntiCheat);

		setEnabled(true);
		setBannable(true);
		setMaxViolations(5);

		sneakTicks = new HashMap<UUID, Map.Entry<Integer, Long>>();
	}

	@EventHandler
	public void onLog(PlayerQuitEvent e) {
		if (sneakTicks.containsKey(e.getPlayer().getUniqueId())) {
			sneakTicks.remove(e.getPlayer().getUniqueId());
		}
	}

	@EventHandler
	public void EntityAction(PacketEntityActionEvent event) {
		if (event.getAction() != 1) {
			return;
		}
		Player player = event.getPlayer();

		if (getAntiCheat().isSotwMode()) {
			return;
		}

		int Count = 0;
		long Time = -1L;
		if (sneakTicks.containsKey(player.getUniqueId())) {
			Count = sneakTicks.get(player.getUniqueId()).getKey().intValue();
			Time = sneakTicks.get(player.getUniqueId()).getValue().longValue();
		}
		Count++;
		if (sneakTicks.containsKey(player.getUniqueId())) {
			if (UtilTime.elapsed(Time, 100L)) {
				Count = 0;
				Time = System.currentTimeMillis();
			} else {
				Time = System.currentTimeMillis();
			}
		}
		if (Count > 50) {
			Count = 0;

			getAntiCheat().logCheat(this, player, null, "(Type: A)");
		}
		sneakTicks.put(player.getUniqueId(), new AbstractMap.SimpleEntry<Integer, Long>(Count, Time));
	}
}
