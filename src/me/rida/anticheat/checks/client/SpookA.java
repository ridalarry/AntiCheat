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
		super("SpookA", "Spook", CheckType.Client, true, false, false, false, 10, 1, 600000L, AntiCheat);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void playerSpookCheck(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player)) {
			return;
		}
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
			Player p = (Player) e.getDamager();
			Player p2 = (Player) e.getEntity();
			float f = MathUtil.getOffset(p, p2);
			onAim(p, f);
	}
	public float onAim(Player p, float f) {
		float f2 = Math.abs(f - this.lastYaw) % 180.0f;
		this.lastYaw = f;
		if (f2 > 1.0f && (float)Math.round(f2) == f2) {
			if (f2 == (float)this.lastBad) {
				getAntiCheat().logCheat(this, p, "Detection of a hack client!", "(Type: A)");
				return f2;
			}
			this.lastBad = Math.round(f2);
		} else {
			this.lastBad = 0;
		}
		return 0.0f;
	}
}