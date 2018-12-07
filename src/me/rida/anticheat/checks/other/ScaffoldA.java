package me.rida.anticheat.checks.other;
import java.util.Map;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.utils.PlayerUtil;

public class ScaffoldA extends Check {
	public static Map<UUID, Map.Entry<Integer, Long>> speedTicks;
	public ScaffoldA(AntiCheat AntiCheat) {
		super("ScaffoldA", "Scaffold", CheckType.Other, true, true, false, true, 10, 1, 600000L, AntiCheat);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onInteract(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		double x = p.getEyeLocation().getX() - e.getBlockPlaced().getX();
		double y = p.getEyeLocation().getY() - e.getBlockPlaced().getY();
		double z = p.getEyeLocation().getZ() - e.getBlockPlaced().getZ();
		if (getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()
				|| p.getAllowFlight()
				|| p.getGameMode().equals(GameMode.CREATIVE)
				|| y < 2.6
				|| PlayerUtil.isOnGround(e, p)) {
			return;
		}
		if (PlayerUtil.isFlying2(e,p)) {
			return;
		}
		if (!PlayerUtil.isFlying(e,p)) {
			return;

		}
		if (PlayerUtil.isOnGround(e,p)) {
			return;

		}
		if (x > 0.8) {
			getAntiCheat().logCheat(this, p, "[1]", "(Type: A)");
		}
		if (z > 0.8) {
			getAntiCheat().logCheat(this, p, "[2]", "(Type: A)");
		}
	}
}
