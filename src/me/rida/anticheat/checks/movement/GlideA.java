package me.rida.anticheat.checks.movement;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.data.DataPlayer;
import me.rida.anticheat.utils.CheatUtil;

public class GlideA extends Check {
	public static Map<UUID, Long> flyTicks;

	public GlideA(AntiCheat AntiCheat) {
		super("GlideA", "Glide", CheckType.Movement, true, false, false, false, true, 12, 1, 600000L, AntiCheat);
		flyTicks = new HashMap<>();
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onMove(PlayerMoveEvent e) {
		if (!this.getAntiCheat().isEnabled()) {
			return;
		}
		final Player p = e.getPlayer();

		if (e.isCancelled()
				|| !(e.getTo().getX() == e.getFrom().getX() && e.getTo().getZ() == e.getFrom().getZ())
				|| p.getVehicle() != null
				|| p.getAllowFlight()
				|| DataPlayer.getWasFlying() > 0
				|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| CheatUtil.isInWeb(p)) {
			return;
		}

		if (CheatUtil.blocksNear(p)) {
			if (flyTicks.containsKey(p.getUniqueId())) {
				flyTicks.remove(p.getUniqueId());
			}
			return;
		}

		final double OffsetY = e.getFrom().getY() - e.getTo().getY();
		if (OffsetY <= 0.0 || OffsetY > 0.16) {
			if (flyTicks.containsKey(p.getUniqueId())) {
				flyTicks.remove(p.getUniqueId());
			}
			return;
		}
		long Time = System.currentTimeMillis();
		if (flyTicks.containsKey(p.getUniqueId())) {
			Time = flyTicks.get(p.getUniqueId());
		}
		final long MS = System.currentTimeMillis() - Time;
		if (MS > 1000L) {
			this.dumplog(p, "Logged for Glide Type A;. MS: " + MS);
			flyTicks.remove(p.getUniqueId());
			this.getAntiCheat().logCheat(this, p, null, "(Type: A)");
			return;
		}
		flyTicks.put(p.getUniqueId(), Time);
	}
}