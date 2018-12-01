package me.rida.anticheat.checks.movement;

import java.util.Map;
import java.util.UUID;

import org.bukkit.GameMode;
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
		super("NoSlowdownB", "NoSlowdown", CheckType.Movement, false, false, false, false, 5, 1, 600000L, AntiCheat);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onMove(PlayerMoveEvent e) {
		if (e.getTo().getX() == e.getFrom().getX() && e.getFrom().getY() == e.getTo().getY()
				&& e.getTo().getZ() == e.getFrom().getZ()) {
			return;
		}
		Player p = e.getPlayer();
		double OffsetY = MathUtil.offset(MathUtil.getVerticalVector(e.getFrom().toVector()),
				MathUtil.getVerticalVector(e.getTo().toVector()));
		//double Offset = e.getFrom().getY() - e.getFrom().getY();
		//double Offset2 = e.getFrom().getY() - e.getFrom().getY();
		//double OffsetXZ = MathUtil.offset(MathUtil.getHorizontalVector(e.getFrom().toVector()),
				//MathUtil.getHorizontalVector(e.getTo().toVector()));

		if (!BlockUtil.isNearLiquid(p)
				|| p.getAllowFlight() 
				|| p.getGameMode().equals(GameMode.CREATIVE)
				|| PlayerUtil.isNearSlab(p)
				|| BlockUtil.isNearFence(p)
				|| BlockUtil.isNearStair(p)
				|| PlayerUtil.isNearSign(p)
				//|| e.getFrom().getY() > e.getTo().getY()
				//|| e.getFrom().getY() < e.getTo().getY()
				|| OffsetY < 0.13 ) {
			return;
		}
		if (PlayerUtil.isInLiquid(p))
			getAntiCheat().logCheat(this, p, "Offset: " + OffsetY + " From Y: " + e.getFrom().getY() + " To Y:  " + e.getTo().getY(), "(Type: A)");
	}
}