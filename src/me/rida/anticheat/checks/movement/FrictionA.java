package me.rida.anticheat.checks.movement;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.data.DataPlayer;
import me.rida.anticheat.utils.ReflectionUtil;

public class FrictionA extends Check {
    public FrictionA(AntiCheat AntiCheat) {
		super("FrictionA", "Friction", CheckType.Movement, AntiCheat);

		this.setEnabled(true);
		this.setBannable(true);
		setViolationsToNotify(1);
		setMaxViolations(5);
    }

    @SuppressWarnings("deprecation")
	@EventHandler
    public void onMove(PlayerMoveEvent event) {
        DataPlayer player = AntiCheat.getInstance().getDataManager().getDataPlayer(event.getPlayer());
        Player p = event.getPlayer();
        float deltaXZ = (float) Math.hypot(event.getTo().getX() - event.getFrom().getX(), event.getTo().getZ() - event.getFrom().getZ()),
                friction = event.getPlayer().isOnGround() ? ReflectionUtil.getFriction(event.getPlayer().getLocation().subtract(0, 1, 0).getBlock()) * 0.91f : 0.91f,
                predicted = deltaXZ * friction;

        Bukkit.broadcastMessage(Math.abs(deltaXZ - predicted) + ", " + event.getPlayer().isOnGround());

        if(Math.abs(deltaXZ - predicted) > (event.getPlayer().isOnGround() ? 0.3 : 0.033)) {
			getAntiCheat().logCheat(this, p, Math.abs(deltaXZ - predicted) + ">-" + (event.getPlayer().isOnGround() ? 0.3 : 0.033), "(Type: A)");
        }
        player.lastDeltaXZ = deltaXZ;
    }
}