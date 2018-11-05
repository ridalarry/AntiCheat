package me.rida.anticheat.checks.experemintal;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.rida.anticheat.checks.Check;
import me.rida.anticheat.utils.Color;
import me.rida.anticheat.AntiCheat;

public class KillAuraI
extends Check {
    Map<UUID, Integer> hits = new HashMap<UUID, Integer>();

    public KillAuraI(AntiCheat AntiCheat) {
        super("KillAuraI", "KillAura", AntiCheat);
		setEnabled(true);
		setMaxViolations(10);
		setBannable(false);
		setViolationsToNotify(1);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (!(entityDamageByEntityEvent.getDamager() instanceof Player)) {
            return;
        }
        if (!(entityDamageByEntityEvent.getEntity() instanceof Player)) {
            return;
        }
        if (!((Player)entityDamageByEntityEvent.getDamager()).hasLineOfSight(entityDamageByEntityEvent.getEntity()) && !this.isPlayerInCorner((Player)entityDamageByEntityEvent.getDamager())) {
            int n = 0;
            Player player = (Player)entityDamageByEntityEvent.getDamager();
            this.hits.putIfAbsent(entityDamageByEntityEvent.getDamager().getUniqueId(), 1);
            if (this.hits.get(entityDamageByEntityEvent.getDamager().getUniqueId()) >= 5) {
                n = 1;
            	getAntiCheat().logCheat(this, player, Color.Red + "Experemental" + "1 [0x01]", "(Type: I)");
            }
            if (this.hits.get(entityDamageByEntityEvent.getDamager().getUniqueId()) >= 10) {
                n = 2;
            	getAntiCheat().logCheat(this, player, Color.Red + "Experemental" + "2 [0x01]", "(Type: I)");
            }
            if (this.hits.get(entityDamageByEntityEvent.getDamager().getUniqueId()) > 19) {
                n = 3;
            	getAntiCheat().logCheat(this, player, Color.Red + "Experemental" + "3 [0x01]", "(Type: I)");
                this.hits.remove(entityDamageByEntityEvent.getDamager().getUniqueId());
                n = 0;
            }
        }
    }

    public boolean isPlayerInCorner(Player player) {
        int n;
        float f = player.getLocation().getYaw();
        if (f < 0.0f) {
            f += 360.0f;
        }
        if ((n = (int)((double)((f %= 360.0f) + 8.0f) / 22.5)) != 0 && n != 4 && n != 8 && n != 12) {
            return true;
        }
        return false;
    }
}

