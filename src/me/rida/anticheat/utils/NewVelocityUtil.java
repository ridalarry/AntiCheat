package me.rida.anticheat.utils;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.data.DataPlayer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerVelocityEvent;

public class NewVelocityUtil implements Listener {
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        DataPlayer data = AntiCheat.getInstance().getDataManager().getData(p);
        if (data != null) {
            if (data.isLastVelUpdateBoolean()) {
                if (TimerUtils.elapsed(data.getLastVelUpdate(),Values.VelTimeReset_1_FORCE_RESET)) {
                    data.setLastVelUpdateBoolean(false);
                }
                if (TimerUtils.elapsed(data.getLastVelUpdate(),Values.VelTimeReset_1)) {
                    if (!p.isOnGround()) {
                        data.setLastVelUpdate(TimerUtils.nowlong());
                    } else {
                        data.setLastVelUpdateBoolean(false);
                    }
                }
            }
        }
    }
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onVelChange(PlayerVelocityEvent e) {
        Player p = e.getPlayer();
        DataPlayer data = AntiCheat.getInstance().getDataManager().getData(p);
        if (data != null) {
            if (p.getNoDamageTicks() > 0 == false) {
                if (!data.isLastVelUpdateBoolean()) {
                    data.setLastVelUpdateBoolean(true);
                    data.setLastVelUpdate(TimerUtils.nowlong());
                }
            }
        }
    }
    public static boolean didTakeVel(Player p) {
        DataPlayer data = AntiCheat.getInstance().getDataManager().getData(p);
        if (data != null) {
            return data.isLastVelUpdateBoolean();
        } else {
            return false;
        }
    }
}
