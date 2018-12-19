package me.rida.anticheat.checks.combat;
 import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
 public class AimAssistC
extends Check {
    private float lastYaw;
    private float lastBad;
    private float lastYaw2;
    private float lastPitch;
    private int streak;
    private int min;
     public AimAssistC(AntiCheat AntiCheat) {
        super("AimAssistC", "AimAssist",  CheckType.Combat, true, false, false, false, true, 20, 1, 600000L, AntiCheat);
    }
     
	@SuppressWarnings("unused")
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onHit(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        if (!(e.getDamager() instanceof Player)) {
            return;
        }
        Player player = (Player)e.getDamager();
        Player player2 = (Player)e.getEntity();
        float f = player.getLocation().getYaw();
        float f2 = player.getLocation().getPitch();
        this.onAim(player, f);
        this.onAim3(player, f);
    }
     public boolean onAim(Player p, float f) {
    	if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
                || getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
    		return true;
    	}
        float f2 = Math.abs(f - this.lastYaw) % 180.0f;
        this.lastYaw = f;
        this.lastBad = (float)Math.round(f2 * 10.0f) * 0.1f;
        if ((double)f < 0.1) {
            return true;
        }
        if (f2 > 1.0f && (float)Math.round(f2 * 10.0f) * 0.1f == f2 && (float)Math.round(f2) != f2) {
            if (f2 == this.lastBad) {
            	getAntiCheat().logCheat(this, p, "[YAW 1]", "(Type: C)");
                return true;
            }
        } else {
            return true;
        }
        return false;
    }
     public int onAim2(Player p, float f, float f2) {
        float f3 = f - this.lastYaw2;
        float f4 = f2 - this.lastPitch;
        if (Math.abs(f4) >= 2.0f && f3 == 0.0f) {
            ++this.streak;
        } else {
            return 0;
        }
        this.lastYaw2 = f;
        this.lastPitch = f2;
        if (this.streak >= this.min) {
        	getAntiCheat().logCheat(this, p, "[YAW 2]", "(Type: C)");
            return this.streak;
        }
        return 0;
    }
     public float onAim3(Player p, float f) {
        float f2 = Math.abs(f - this.lastYaw) % 180.0f;
        this.lastYaw = f;
        if (f2 > 0.1f && (float)Math.round(f2) == f2) {
            if (f2 == this.lastBad) {
            	getAntiCheat().logCheat(this, p, "[YAW 3]", "(Type: C)");
                return f2;
            }
            this.lastBad = Math.round(f2);
        } else {
            this.lastBad = 0.0f;
        }
        return 0.0f;
    }
}