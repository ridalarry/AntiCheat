package me.rida.anticheat.checks.movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.utils.CheatUtil;

public class JesusA extends Check {
	public static Map<Player, Integer> onWater;
	public static List<Player> placedBlockOnWater;
	public static Map<Player, Integer> count;
	public static Map<Player, Long> velocity;

	public JesusA(AntiCheat AntiCheat) {
		super("JesusA", "Jesus", CheckType.Movement, true, true, false, true, 5, 1, 600000L, AntiCheat);
		count = new WeakHashMap<Player, Integer>();
		placedBlockOnWater = new ArrayList<Player>();
		onWater = new WeakHashMap<Player, Integer>();
		velocity = new WeakHashMap<Player, Long>();
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onMove(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		if (event.isCancelled()
				|| (event.getFrom().getX() == event.getTo().getX()) && (event.getFrom().getZ() == event.getTo().getZ())
				|| p.getAllowFlight()
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()
				|| CheatUtil.isOnLilyPad(p)
				|| p.getLocation().clone().add(0.0D, 0.4D, 0.0D).getBlock().getType().isSolid()
				|| placedBlockOnWater.remove(p)) {
			return;
		}

		int Count = count.getOrDefault(p, 0);

		if ((CheatUtil.cantStandAtWater(p.getWorld().getBlockAt(p.getLocation())))
				&& (CheatUtil.isHoveringOverWater(p.getLocation())) && (!CheatUtil.isFullyInWater(p.getLocation()))) {
			Count+= 2;
		} else {
			Count = Count > 0 ? Count - 1 : Count;
		}

		if (Count > 19) {
			Count = 0;
			getAntiCheat().logCheat(this, p, null, "(Type: A)");
		}

		count.put(p, Count);
	}
}