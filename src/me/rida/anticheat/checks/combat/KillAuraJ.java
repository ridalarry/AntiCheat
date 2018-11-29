package me.rida.anticheat.checks.combat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.utils.Color;
public class KillAuraJ extends Check {
	private float lastYaw;
	private float lastBad;
	public KillAuraJ(AntiCheat AntiCheat) {
		super("KillAuraI", "KillAura",  CheckType.Combat, true, false, false, 10, 1, 600000, AntiCheat);
		setEnabled(true);
		setMaxViolations(10);
		setBannable(false);
	}
	@SuppressWarnings("unused")
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onHit(EntityDamageByEntityEvent e) {
		float f;
		if (!(e.getDamager() instanceof Player)
				|| !(e.getEntity() instanceof Player)) {
			return;
		}
		Player p = (Player)e.getDamager();
		this.lastYaw = f = p.getLocation().getYaw();
		float f2 = Math.abs(f - this.lastYaw) % 180.0f;
	}
	public boolean onAim(Player p, float f) {
		float f2 = Math.abs(f - this.lastYaw) % 180.0f;
		this.lastYaw = f;
		this.lastBad = (float)Math.round(f2 * 10.0f) * 0.1f;
		if ((double)f < 0.1) {
			return true;
		}
		if (f2 > 1.0f && (float)Math.round(f2 * 10.0f) * 0.1f == f2 && (float)Math.round(f2) != f2) {
			if (f2 == this.lastBad) {
				if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
						|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
					return true;
				}
				getAntiCheat().logCheat(this, p, Color.Red + "Experemental" + " Huzuni [V5X01]", "(Type: J)");
				return true;
			}
		} else {
			return true;
		}
		return false;
	}
}