package me.rida.anticheat.checks.combat;
 import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
 public class KillAuraI extends Check {
    Map<UUID, Integer> hits = new HashMap<UUID, Integer>();
     public KillAuraI(AntiCheat AntiCheat) {
 		super("KillAuraI", "KillAura",  CheckType.Combat, true, false, false, false, true, 7, 1, 600000L, AntiCheat);
    }
	@SuppressWarnings("unused")
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)||
        		!(e.getEntity() instanceof Player)) {
            return;
        }
        if (!((Player)e.getDamager()).hasLineOfSight(e.getEntity()) && !this.isPlayerInCorner((Player)e.getDamager())) {
            int violation = 0;
            Player p = (Player)e.getDamager();
            this.hits.putIfAbsent(e.getDamager().getUniqueId(), 1);
            if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
                || getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
            	return;
            }
            if (this.hits.get(e.getDamager().getUniqueId()) >= 5) {
            	violation = 1;
            	getAntiCheat().logCheat(this, p, "5 or more illegal hits", "(Type: I)");
            }
            if (this.hits.get(e.getDamager().getUniqueId()) >= 10) {
            	violation = 2;
            	getAntiCheat().logCheat(this, p, "10 or more illegal hits", "(Type: I)");
            }
            if (this.hits.get(e.getDamager().getUniqueId()) >= 20) {
            	violation = 3;
            	getAntiCheat().logCheat(this, p, "20 or more illegal hits", "(Type: I)");
                this.hits.remove(e.getDamager().getUniqueId());
                violation = 0;
            }
        }
    }
     public boolean isPlayerInCorner(Player p) {
        int violation;
        float yaw = p.getLocation().getYaw();
        if (yaw < 0.0f) {
        	yaw += 360.0f;
        }
        if ((violation = (int)((double)((yaw %= 360.0f) + 8.0f) / 22.5)) != 0 && violation != 4 && violation != 8 && violation != 12) {
            return true;
        }
        return false;
    }
}