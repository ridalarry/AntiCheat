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
import me.rida.anticheat.other.Latency;
import me.rida.anticheat.utils.Color;
import me.rida.anticheat.utils.TimeUtil;

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
		Player p = e.getPlayer();
		UUID u = p.getUniqueId();
		if (!getAntiCheat().isEnabled() 
				|| (Latency.getLag(p) > 500)
				|| (e.getFrom().getX() == e.getTo().getX() && e.getFrom().getZ() == e.getTo().getZ()
						&& e.getFrom().getY() == e.getTo().getY())) {
			return;
		}
		
		int Count = 0;
		long Time = System.currentTimeMillis();
		if (timerTicks.containsKey(u)) {
			Count = timerTicks.get(u).getKey().intValue();
			Time = timerTicks.get(u).getValue().longValue();
		}

		Count++;

		if ((timerTicks.containsKey(p.getUniqueId())) && (TimeUtil.elapsed(Time, 1000L))) {
			if (Count > 35) {
				this.getAntiCheat().logCheat(this, p, Color.Red + "Experimental", "(Type: B)");
			}
			Count = 0;
			Time = TimeUtil.nowlong();
		}
		timerTicks.put(u, new AbstractMap.SimpleEntry<Integer, Long>(Count, Time));
	}

}
