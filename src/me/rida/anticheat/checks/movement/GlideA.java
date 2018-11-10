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
import me.rida.anticheat.utils.CheatUtil;

public class GlideA extends Check {
	public static Map<UUID, Long> flyTicks;

	public GlideA(AntiCheat AntiCheat) {
		super("GlideA", "Glide", AntiCheat);
		flyTicks = new HashMap<UUID, Long>();
		setEnabled(true);
		setBannable(false);
		setMaxViolations(5);
	}

	@EventHandler
	public void onLog(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();

		if (flyTicks.containsKey(uuid)) {
			flyTicks.remove(uuid);
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void CheckGlide(PlayerMoveEvent e) {
		if (!this.getAntiCheat().isEnabled()) {
			return;
		}
		Player p = e.getPlayer();
		
		if (e.isCancelled()
				|| !(e.getTo().getX() == e.getFrom().getX() && e.getTo().getZ() == e.getFrom().getZ())
				|| p.getVehicle() != null
				|| p.getAllowFlight()
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

		double OffsetY = e.getFrom().getY() - e.getTo().getY();
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
		long MS = System.currentTimeMillis() - Time;
		if (MS > 1000L) {
			this.dumplog(p, "Logged. MS: " + MS);
			flyTicks.remove(p.getUniqueId());
			if (getAntiCheat().getLag().getPing(p) < 275) {
				this.getAntiCheat().logCheat(this, p, null, "(Type: A)");
			}
			return;
		}
		flyTicks.put(p.getUniqueId(), Time);
	}
}