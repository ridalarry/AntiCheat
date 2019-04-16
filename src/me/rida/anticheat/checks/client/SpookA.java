package me.rida.anticheat.checks.client;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.utils.MathUtil;

public class SpookA extends Check implements Listener{
	private float lastYaw;
	private int lastBad;

	public SpookA(AntiCheat AntiCheat) {
		super("SpookA", "Spook", CheckType.Client, true, false, false, false, true, 10, 1, 600000L, AntiCheat);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void playerSpookCheck(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player)) {
			return;
		}
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		final Player p = (Player) e.getDamager();
		if (p == null) {
			return;
		}
		final Player p2 = (Player) e.getEntity();
		final float yaw = MathUtil.getOffset(p, p2);
		onAim(p, yaw);
	}
	public float onAim(Player p, float yaw) {
		final float badYaw = Math.abs(yaw - this.lastYaw) % 180.0f;
		this.lastYaw = yaw;
		if (badYaw > 1.0f && Math.round(badYaw) == badYaw) {
			if (badYaw == this.lastBad) {
				getAntiCheat().logCheat(this, p, "Detection of spook hack client!", "(Type: A)");
				return badYaw;
			}
			this.lastBad = Math.round(badYaw);
		} else {
			this.lastBad = 0;
		}
		return 0.0f;
	}
}