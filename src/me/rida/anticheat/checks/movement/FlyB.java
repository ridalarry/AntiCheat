package me.rida.anticheat.checks.movement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.other.Latency;
import me.rida.anticheat.utils.CheatUtil;
import me.rida.anticheat.utils.MathUtil;
import me.rida.anticheat.utils.PlayerUtil;

public class FlyB extends Check {

	public static Map<UUID, Long> flyTicks;

	public FlyB(AntiCheat AntiCheat) {
		super("FlyB", "Fly", CheckType.Movement, true, true, false, true, 5, 1, 600000L, AntiCheat);
		flyTicks = new HashMap<UUID, Long>();
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onLog(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();

		if (flyTicks.containsKey(uuid)) {
			flyTicks.remove(uuid);
		}
	}

	public static void resetCBPE(Player p) {
		blockPlacedFB.remove(p.getName().toString());
	}
	public static List<String> blockPlacedFB = new ArrayList<>();
	@EventHandler(priority = EventPriority.MONITOR)
	public static void blockPlaceCancelled (BlockPlaceEvent e) {
		if (e.isCancelled()) {
			blockPlacedFB.add(e.getPlayer().getName().toString());
		}
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void CheckFlyA(PlayerMoveEvent e) {
		if (!getAntiCheat().isEnabled()) {
			return;
		}
		Player p = e.getPlayer();

		if (e.isCancelled()
				|| (e.getTo().getX() == e.getFrom().getX()) && (e.getTo().getZ() == e.getFrom().getZ())
				|| blockPlacedFB.contains(p.getName().toString())
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
			if (flyTicks.containsKey(p.getUniqueId())) {
				flyTicks.remove(p.getUniqueId());
			}
			return;
		} 
		if (Math.abs(e.getTo().getY() - e.getFrom().getY()) > 0.06) {
			if (flyTicks.containsKey(p.getUniqueId())) {
				flyTicks.remove(p.getUniqueId());
			}
			return;
		}

		long Time = System.currentTimeMillis();
		if (flyTicks.containsKey(p.getUniqueId())) {
			Time = flyTicks.get(p.getUniqueId()).longValue();
		}
		long MS = System.currentTimeMillis() - Time;
		if (MS > 200L) {
			dumplog(p, "Logged for Fly Type B;  MS: " + MS);
			getAntiCheat().logCheat(this, p, "Hovering for " + MathUtil.trim(1, Double.valueOf((MS / 1000))) + " second(s)", "(Type: B)"
					);
			flyTicks.remove(p.getUniqueId());
			return;
		}
		flyTicks.put(p.getUniqueId(), Time);
	}
}