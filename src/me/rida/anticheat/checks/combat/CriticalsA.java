package me.rida.anticheat.checks.combat;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.data.DataPlayer;
import me.rida.anticheat.utils.BlockUtil;
import me.rida.anticheat.utils.PlayerUtil;

public class CriticalsA extends Check {

	public CriticalsA(AntiCheat AntiCheat) {
		super("CriticalsA", "Criticals",  CheckType.Combat, true, false, false, false, true, 20, 1, 600000L, AntiCheat);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onAttack(EntityDamageByEntityEvent e) {
		if(!(e.getDamager() instanceof Player)) {
			return;
		}
		final Player p = (Player) e.getDamager();
		if(getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
			return;
		}

		@SuppressWarnings("unused")
		final
		Entity entity = e.getEntity();
		final DataPlayer data = AntiCheat.getInstance().getDataManager().getData(p);

		if(data.getAboveBlockTicks() > 0
				|| PlayerUtil.isInWeb(p)
				|| p.getAllowFlight()
				|| p.isFlying()
				|| BlockUtil.isNearPistion(p)
				|| data.getWaterTicks() > 0
				|| PlayerUtil.hasSlabsNear(p.getLocation())) {
			return;
		}
		if (BlockUtil.isNearLiquid(p) && BlockUtil.isNearFence(p)) {
			return;
		}
		int verbose = data.getCriticalsVerbose();

		if(p.getFallDistance() > 0 && data.getFallDistance() == 0) {
			if(++verbose > 3) {
				getAntiCheat().logCheat(this, p, "Packet", "(Type: A)");
				verbose = 0;
			}
		} else {
			verbose = 0;
		}
		data.setCriticalsVerbose(verbose);
	}
}
