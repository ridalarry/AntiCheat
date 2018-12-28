package me.rida.anticheat.checks.combat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.utils.MathUtil;

public class KillAuraC extends Check {
	public KillAuraC(AntiCheat AntiCheat) {
		super("KillAuraC", "KillAura",  CheckType.Combat, true, false, false, false, true, 20, 3, 600000L, AntiCheat);
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	private void onAngleHit(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player)
				||!(e.getDamager() instanceof Player)) {
			return;
		}
		final Player p = (Player) e.getDamager();
		final Player damaged = (Player) e.getEntity();
		final double ping = getAntiCheat().getLag().getPing(p);
		final double dmgdPing = getAntiCheat().getLag().getPing(damaged);
		double offset = MathUtil.getOffsets2(p, damaged)[0];
		if (dmgdPing > 450.0) {
			return;
		}
		if (ping >= 100.0 && ping < 200.0) {
			offset -= 50.0;
		} else if (ping >= 200.0 && ping < 250.0) {
			offset -= 75.0;
		} else if (ping >= 250.0 && ping < 300.0) {
			offset -= 150.0;
		} else if (ping >= 300.0 && ping < 350.0) {
			offset -= 300.0;
		} else if (ping >= 350.0 && ping < 400.0) {
			offset -= 350.0;
		} else if (ping > 400.0) {
			return;
		}
		if (offset >= 300.0) {
			if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
					|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
				return;
			}
			getAntiCheat().logCheat(this, p, "Offset: " + offset + "; ping: " + ping + "; Attacked Ping: " + dmgdPing, "(Type: C)");
		}
	}
}