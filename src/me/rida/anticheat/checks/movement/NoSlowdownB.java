package me.rida.anticheat.checks.movement;

import java.util.Map;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.utils.BlockUtil;
import me.rida.anticheat.utils.MathUtil;
import me.rida.anticheat.utils.PlayerUtil;

public class NoSlowdownB extends Check {

	public static Map<UUID, Map.Entry<Integer, Long>> speedTicks;

	public NoSlowdownB(AntiCheat AntiCheat) {
		super("NoSlowdownB", "NoSlowdown", CheckType.Movement, true, false, false, false, 5, 1, 600000L, AntiCheat);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onMove(PlayerMoveEvent e) {
		Location from = e.getFrom();
		Location to = e.getTo();
		if (to.getX() == e.getFrom().getX() && from.getY() == to.getY()
				&& from.getZ() == from.getZ()) {
			return;
		}
		Player p = e.getPlayer();
		double OffsetY = MathUtil.offset(MathUtil.getVerticalVector(from.toVector()), MathUtil.getVerticalVector(to.toVector()));
		double OffsetXZ = MathUtil.offset(MathUtil.getHorizontalVector(from.toVector()), MathUtil.getHorizontalVector(to.toVector()));
		if (!BlockUtil.isNearLiquid(p)
				|| p.getAllowFlight() 
				|| p.getGameMode().equals(GameMode.CREATIVE)
				|| PlayerUtil.isNearSlab(p)
				|| BlockUtil.isNearFence(p)
				|| BlockUtil.isNearStair(p)
				|| PlayerUtil.isNearSign(p)
				|| OffsetY > 0.55 
				|| OffsetXZ > 0.3
				|| to.getY() < from.getY()
				|| PlayerUtil.isNearAir(p)
				|| OffsetY < 0.131 ) {
			return;
		}
		if (PlayerUtil.isInLiquid(p)) {
			getAntiCheat().logCheat(this, p, "OffsetY: " + OffsetY + " OffsetXZ: " + OffsetXZ, "(Type: B)");
		}
	}
}