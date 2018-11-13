package me.rida.anticheat.checks.combat;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.utils.Color;
import me.rida.anticheat.AntiCheat;

public class KillAuraI extends Check {
    Map<UUID, Integer> hits = new HashMap<UUID, Integer>();

    public KillAuraI(AntiCheat AntiCheat) {
        super("KillAuraI", "KillAura",  CheckType.Combat, AntiCheat);
		setEnabled(true);
		setMaxViolations(10);
		setBannable(false);
    }
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)||
        		!(e.getEntity() instanceof Player)) {
            return;
        }
        if (!((Player)e.getDamager()).hasLineOfSight(e.getEntity()) && !this.isPlayerInCorner((Player)e.getDamager())) {
            int n = 0;
            Player p = (Player)e.getDamager();
            this.hits.putIfAbsent(e.getDamager().getUniqueId(), 1);
            if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
                || getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
            	return;
            }
            if (this.hits.get(e.getDamager().getUniqueId()) >= 5) {
                n = 1;
            	getAntiCheat().logCheat(this, p, Color.Red + "Experemental" + "[1]", "(Type: I)");
            }
            if (this.hits.get(e.getDamager().getUniqueId()) >= 10) {
                n = 2;
            	getAntiCheat().logCheat(this, p, Color.Red + "Experemental" + "[2]", "(Type: I)");
            }
            if (this.hits.get(e.getDamager().getUniqueId()) > 19) {
                n = 3;
            	getAntiCheat().logCheat(this, p, Color.Red + "Experemental" + "[3]", "(Type: I)");
                this.hits.remove(e.getDamager().getUniqueId());
                n = 0;
            }
        }
    }

    public boolean isPlayerInCorner(Player p) {
        int n;
        float f = p.getLocation().getYaw();
        if (f < 0.0f) {
            f += 360.0f;
        }
        if ((n = (int)((double)((f %= 360.0f) + 8.0f) / 22.5)) != 0 && n != 4 && n != 8 && n != 12) {
            return true;
        }
        return false;
    }
}

