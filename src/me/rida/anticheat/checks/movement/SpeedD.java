package me.rida.anticheat.checks.movement;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;

public class SpeedD extends Check {

    public SpeedD(AntiCheat AntiCheat) {
		super("SpeedD", "Speed", CheckType.Movement, true, false, false, 15, 1, 600000L, AntiCheat);
    }

    /*@EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        DataPlayer data = AntiCheat.getInstance().getDataManager().getDataPlayer(event.getPlayer());
        Player p = event.getPlayer();
        if (data.player.getAllowFlight()
                || data.player.isInsideVehicle()
                || data.onClimbable
                || (System.currentTimeMillis() - data.lastVelocityTaken) < 4000L) {
            return;
        }
        double horizontal = MathUtil.getHorizontalDistance(event.getFrom(), event.getTo()),
                threshold = data.airTicks > 0 ? 0.36 - (0.004 * Math.min(20, data.airTicks)) : 0.32 - (0.008 * Math.min(4, data.groundTicks));

        threshold = BlockUtil.isNearLiquid(p) ? 0.34 + (PlayerUtil.hasDepthStrider(data.player) * 0.02) : threshold;
        threshold += data.iceTicks > 0 ? !data.onGround ? 0.22 : 0.06 : 0;
        threshold += data.blockTicks > 0 ? 0.25 : 0;
        threshold += BlockUtil.isNearSlab(p) || BlockUtil.isNearStair(p) ? 0.12 : 0;
        threshold += PlayerUtil.getPotionEffectLevel(data.player, PotionEffectType.SPEED) * 0.035;
        threshold += (data.player.getWalkSpeed() - 0.2) * 0.45;

        if (horizontal > threshold) {
            if ((data.speedThreshold += (horizontal - threshold > 0.145 ? 5 : 2)) > 30) {
    			getAntiCheat().logCheat(this, p, horizontal + ">-" + threshold, "(Type: D)");
                data.speedThreshold = 10;
            }
       } else data.speedThreshold = Math.max(0, data.speedThreshold - 1);
    }

*/}
