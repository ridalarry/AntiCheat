package me.rida.anticheat.checks.movement;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.other.Latency;
import me.rida.anticheat.utils.MathUtil;
import me.rida.anticheat.utils.PlayerUtil;
import me.rida.anticheat.utils.CheatUtil;

public class FlyB extends Check {
	
	public static Map<UUID, Long> flyTicksA;

	public FlyB(AntiCheat AntiCheat) {
		super("FlyB", "Fly", CheckType.Movement, AntiCheat);

		setEnabled(true);
		setBannable(true);
		setMaxViolations(5);
		
		flyTicksA = new HashMap<UUID, Long>();
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onLog(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();

		if (flyTicksA.containsKey(uuid)) {
			flyTicksA.remove(uuid);
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void CheckFlyA(PlayerMoveEvent e) {
		if (!getAntiCheat().isEnabled()) {
			return;
		}
		Player p = e.getPlayer();
		
		if (e.isCancelled()
				|| (e.getTo().getX() == e.getFrom().getX()) && (e.getTo().getZ() == e.getFrom().getZ())
				|| p.getAllowFlight()
				|| p.getVehicle() != null
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| PlayerUtil.isInWater(p)
				|| CheatUtil.isInWeb(p)
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
		        || getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()
				|| Latency.getLag(p) > 92) {
			return;
		}
		
		if (CheatUtil.blocksNear(p.getLocation())) {
			if (flyTicksA.containsKey(p.getUniqueId())) {
				flyTicksA.remove(p.getUniqueId());
			}
			return;
		} 
		if (Math.abs(e.getTo().getY() - e.getFrom().getY()) > 0.06) {
			if (flyTicksA.containsKey(p.getUniqueId())) {
				flyTicksA.remove(p.getUniqueId());
			}
			return;
		}
		
		long Time = System.currentTimeMillis();
		if (flyTicksA.containsKey(p.getUniqueId())) {
			Time = flyTicksA.get(p.getUniqueId()).longValue();
		}
		long MS = System.currentTimeMillis() - Time;
		if (MS > 200L) {
			dumplog(p, "Logged for Fly Type B;  MS: " + MS);
			getAntiCheat().logCheat(this, p, "Hovering for " + MathUtil.trim(1, Double.valueOf((MS / 1000))) + " second(s)", "(Type: B)"
					);
			flyTicksA.remove(p.getUniqueId());
			return;
		}
		flyTicksA.put(p.getUniqueId(), Time);
	}
}