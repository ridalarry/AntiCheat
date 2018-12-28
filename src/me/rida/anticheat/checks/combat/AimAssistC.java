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
        float yaw = player.getLocation().getYaw();
        float pitch = player.getLocation().getPitch();
        this.onAim(player, yaw);
        this.onAim3(player, yaw);
    }
     public boolean onAim(Player p, float yaw) {
    	if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
                || getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
    		return true;
    	}
        float pitch = Math.abs(yaw - this.lastYaw) % 180.0f;
        this.lastYaw = yaw;
        this.lastBad = (float)Math.round(pitch * 10.0f) * 0.1f;
        if ((double)yaw < 0.1) {
            return true;
        }
        if (pitch > 1.0f && (float)Math.round(pitch * 10.0f) * 0.1f == pitch && (float)Math.round(pitch) != pitch) {
            if (pitch == this.lastBad) {
            	getAntiCheat().logCheat(this, p, "[1] Yaw: " + yaw + "; pitch: " + pitch, "(Type: C)");
                return true;
            }
        } else {
            return true;
        }
        return false;
    }
     public int onAim2(Player p, float yaw, float pitch) {
        float lastYaw = yaw - this.lastYaw2;
        float lastPitch = pitch - this.lastPitch;
        if (Math.abs(lastPitch) >= 2.0f && lastYaw == 0.0f) {
            ++this.streak;
        } else {
            return 0;
        }
        this.lastYaw2 = yaw;
        this.lastPitch = pitch;
        if (this.streak >= this.min) {
        	getAntiCheat().logCheat(this, p, "[2] Yaw: " + yaw + "; pitch: " + pitch, "(Type: C)");
            return this.streak;
        }
        return 0;
    }
     public float onAim3(Player p, float yaw) {
        float pitch = Math.abs(yaw - this.lastYaw) % 180.0f;
        this.lastYaw = yaw;
        if (pitch > 0.1f && (float)Math.round(pitch) == pitch) {
            if (pitch == this.lastBad) {
            	getAntiCheat().logCheat(this, p, "[3] Yaw: " + yaw + "; pitch: " + pitch, "(Type: C)");
                return pitch;
            }
            this.lastBad = Math.round(pitch);
        } else {
            this.lastBad = 0.0f;
        }
        return 0.0f;
    }
}