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
import me.rida.anticheat.other.Latency;
import me.rida.anticheat.utils.MathUtil;
import me.rida.anticheat.utils.PlayerUtil;
import me.rida.anticheat.utils.CheatUtil;

public class FlyB extends Check {
	
	public static Map<UUID, Long> flyTicksA;

	public FlyB(AntiCheat AntiCheat) {
		super("FlyB", "Fly", AntiCheat);

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
	public void CheckFlyA(PlayerMoveEvent event) {
		if (!getAntiCheat().isEnabled()) {
			return;
		}
		Player player = event.getPlayer();
		
		if (event.isCancelled()
				|| (event.getTo().getX() == event.getFrom().getX()) && (event.getTo().getZ() == event.getFrom().getZ())
				|| player.getAllowFlight()
				|| player.getVehicle() != null
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| PlayerUtil.isInWater(player)
				|| CheatUtil.isInWeb(player)
				|| Latency.getLag(player) > 92) {
			return;
		}
		
		if (CheatUtil.blocksNear(player.getLocation())) {
			if (flyTicksA.containsKey(player.getUniqueId())) {
				flyTicksA.remove(player.getUniqueId());
			}
			return;
		} 
		if (Math.abs(event.getTo().getY() - event.getFrom().getY()) > 0.06) {
			if (flyTicksA.containsKey(player.getUniqueId())) {
				flyTicksA.remove(player.getUniqueId());
			}
			return;
		}
		
		long Time = System.currentTimeMillis();
		if (flyTicksA.containsKey(player.getUniqueId())) {
			Time = flyTicksA.get(player.getUniqueId()).longValue();
		}
		long MS = System.currentTimeMillis() - Time;
		if (MS > 200L) {
			dumplog(player, "Logged Fly. MS: " + MS);
			getAntiCheat().logCheat(this, player, "Hovering for " + MathUtil.trim(1, Double.valueOf((MS / 1000))) + " second(s)", "(Type: B)"
					);
			flyTicksA.remove(player.getUniqueId());
			return;
		}
		flyTicksA.put(player.getUniqueId(), Time);
	}
}