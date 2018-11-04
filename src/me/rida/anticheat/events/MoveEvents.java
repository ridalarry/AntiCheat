package me.rida.anticheat.events;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.data.DataPlayer;
import me.rida.anticheat.utils.needscleanup.ExtraUtils;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerVelocityEvent;

public class MoveEvents implements Listener {

    @EventHandler()
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (event.getFrom().getX() != event.getTo().getX()
                || event.getFrom().getY() != event.getTo().getY()
                || event.getFrom().getZ() != event.getTo().getZ()) {
            DataPlayer data = AntiCheat.getInstance().getDataManager().getDataPlayer(player);

            if (data != null) {
                data.onGround = ExtraUtils.isOnGround(player);
                data.onStairSlab = ExtraUtils.isInStairs(player);
                data.inLiquid = ExtraUtils.isInLiquid(player);
                data.onIce = ExtraUtils.isOnIce(player);
                data.onClimbable = ExtraUtils.isOnClimbable(player);
                data.underBlock = ExtraUtils.inUnderBlock(player);
                
                if(data.onGround) {
                    data.groundTicks++;
                    data.airTicks = 0;
                } else {
                    data.airTicks++;
                    data.groundTicks = 0;
                }
                
                data.iceTicks = Math.max(0, data.onIce ? data.iceTicks + 1  : data.iceTicks - 1);
                data.liquidTicks = Math.max(0, data.inLiquid ? data.liquidTicks + 1  : data.liquidTicks - 1);
                data.blockTicks = Math.max(0, data.underBlock ? data.blockTicks + 1  : data.blockTicks - 1);
            }
        }
    }

    @EventHandler
    public void onVelocity(PlayerVelocityEvent event) {
        DataPlayer data = AntiCheat.getInstance().getDataManager().getDataPlayer(event.getPlayer());

        if(data == null) {
            return;
        }
        if(event.getVelocity().getY() > -0.078 || event.getVelocity().getY() < -0.08) {
            data.lastVelocityTaken = System.currentTimeMillis();
        }
    }
}
