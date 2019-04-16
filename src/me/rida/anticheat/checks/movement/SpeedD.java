package me.rida.anticheat.checks.movement;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.data.DataPlayer;
import me.rida.anticheat.utils.PlayerUtil;

public class SpeedD extends Check {
	public SpeedD(AntiCheat AntiCheat) {
		super("SpeedD", "Speed", CheckType.Movement, true, false, false, true, false, 3, 1, 600000L, AntiCheat);
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		final DataPlayer data = AntiCheat.getInstance().getDataManager().getDataPlayer(event.getPlayer());

		//1.9 check for player.isGliding and in 1.13 check for player.isRiptiding().
		if(event.getPlayer().getAllowFlight()
				|| event.getPlayer().isInsideVehicle()
				|| data.isVelocityTaken()) {
			return;
		}
		float threshold = event.getPlayer().isOnGround() ? 0.31f : .341f;

		final float deltaXZ = (float) Math.sqrt(Math.pow(event.getTo().getX() - event.getFrom().getX(), 2) + Math.pow(event.getTo().getZ() - event.getFrom().getZ(), 2)), deltaY = (float) (event.getTo().getY() - event.getFrom().getY());
		final float speedLevel = PlayerUtil.getPotionEffectLevel(event.getPlayer(), PotionEffectType.SPEED);
		final Player p = event.getPlayer();
		threshold+= DataPlayer.slimeTicks > 0 ? 0.07f : 0;
		threshold+= data.groundTicks < 5 ? speedLevel * 0.06f : speedLevel * 0.04f;
		threshold*= data.onStairSlab ? 1.8f : 1.0;
		threshold*= data.iceTicks > 0 && data.groundTicks < 5 ? 1.7f : 1.0;
		threshold*= data.blockTicks > 0 && deltaY != 0 ? 2.0f : 1.0;
		threshold += p.getWalkSpeed() > 0.2 ? p.getWalkSpeed() * 1 : 0;
		threshold += p.getFlySpeed() > 0.2 ? p.getFlySpeed() * 1 : 0;

		if(deltaXZ > threshold) {
			if((data.speedThreshold+= 2) > 25) {
				getAntiCheat().logCheat(this, p, deltaXZ + ">-" + threshold, "(Type: D)");
				data.speedThreshold = 0;
			}
		} else {
			data.speedThreshold = Math.max(0, data.speedThreshold - 1);
		}
	}
}
