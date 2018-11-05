package me.rida.anticheat.checks.movement;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.other.Latency;
import me.rida.anticheat.utils.Color;
import me.rida.anticheat.utils.UtilTime;

public class TimerB extends Check {

	public static Map<UUID, Map.Entry<Integer, Long>> timerTicks;

	public TimerB(AntiCheat AntiCheat) {
		super("TimerB", "Timer", AntiCheat);

		setViolationsToNotify(1);
		setMaxViolations(9);
		setEnabled(true);
		setBannable(false);

		timerTicks = new HashMap<UUID, Map.Entry<Integer, Long>>();
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onMove(PlayerMoveEvent e) {
		if (!getAntiCheat().isEnabled()) {
			return;
		}
		Player player = e.getPlayer();
		
		if (e.getFrom().getX() == e.getTo().getX() && e.getFrom().getZ() == e.getTo().getZ()
				&& e.getFrom().getY() == e.getTo().getY()) {
			return;
		}
		if (getAntiCheat().isSotwMode() || Latency.getLag(player) > 500) {
			return;
		}
		int Count = 0;
		long Time = System.currentTimeMillis();
		if (timerTicks.containsKey(player.getUniqueId())) {
			Count = timerTicks.get(player.getUniqueId()).getKey().intValue();
			Time = timerTicks.get(player.getUniqueId()).getValue().longValue();
		}

		Count++;

		if ((timerTicks.containsKey(player.getUniqueId())) && (UtilTime.elapsed(Time, 1000L))) {
			if (Count > 35) {
				this.getAntiCheat().logCheat(this, player, Color.Red + "Experimental", "(Type: B)");
			}
			Count = 0;
			Time = UtilTime.nowlong();
		}
		timerTicks.put(player.getUniqueId(), new AbstractMap.SimpleEntry<Integer, Long>(Count, Time));
	}

}
