package me.rida.anticheat.checks.combat;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.data.DataPlayer;
import me.rida.anticheat.utils.PlayerUtil;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CriticalsA extends Check {

    public CriticalsA(AntiCheat AntiCheat) {
        super("CriticalsA", "Criticals",  CheckType.Combat, AntiCheat);
		setEnabled(true);
		setMaxViolations(10);
		setBannable(false);
    }

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	private void onAttack(EntityDamageByEntityEvent e) {
        if(!(e.getDamager() instanceof Player)) {
            return;
        }

        Player p = (Player) e.getDamager();

        if(getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
                || getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
            return;
        }

        @SuppressWarnings("unused")
		Entity entity = e.getEntity();
        DataPlayer data = AntiCheat.getInstance().getDataManager().getData(p);

        if(data.getAboveBlockTicks() > 0
                || PlayerUtil.isInWeb(p)
                || p.getAllowFlight()
                || p.isFlying()
                || data.getWaterTicks() > 0
                || PlayerUtil.hasSlabsNear(p.getLocation())) {
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
