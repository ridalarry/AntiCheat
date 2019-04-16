package me.rida.anticheat.checks.combat;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.util.Vector;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.utils.BlockUtil;
import me.rida.anticheat.utils.Color;
import me.rida.anticheat.utils.PlayerUtil;
import me.rida.anticheat.utils.ServerUtil;

public class AntiKBA extends Check {
	public static Map<Player, Long> lastVelocity = new HashMap<>();
	public static Map<Player, Integer> awaitingVelocity = new HashMap<>();
	public static Map<Player, Double> totalMoved = new HashMap<>();

	public AntiKBA(AntiCheat AntiCheat) {
		super("AntiKBA", "AntiKB",  CheckType.Combat, true, false, false, false, true, 30, 3, 250000L, AntiCheat);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onMove(PlayerMoveEvent e) {
		double yLoc;
		final Player p = e.getPlayer();
		if (p == null) {
			return;
		}
		if (ServerUtil.isOnBlock(p, 0, new Material[]{Material.WEB})
				|| ServerUtil.isOnBlock(p, 1, new Material[]{Material.WEB})
				|| ServerUtil.isHoveringOverWater(p, 1)
				|| ServerUtil.isHoveringOverWater(p, 0)
				|| p.getAllowFlight()
				|| p.isDead()
				|| PlayerUtil.isNearSlime(p)
				|| BlockUtil.isSolid(BlockUtil.getBlockBehindPlayer(p))
				|| p.getGameMode().equals(GameMode.CREATIVE)
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
			return;
		}
		int awaitingVelocity = 0;
		if (AntiKBA.awaitingVelocity.containsKey(p)) {
			awaitingVelocity = AntiKBA.awaitingVelocity.get(p);
		}
		long lastVelocity = 0;
		if (AntiKBA.lastVelocity.containsKey(p)) {
			lastVelocity = AntiKBA.lastVelocity.get(p);
		}
		if (p.getLastDamageCause() == null || p.getLastDamageCause().getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK && p.getLastDamageCause().getCause() != EntityDamageEvent.DamageCause.PROJECTILE) {
			awaitingVelocity = 0;
		}
		if (System.currentTimeMillis() - lastVelocity > 2000 && awaitingVelocity > 0) {
			--awaitingVelocity;
		}
		double totalMoved = 0.0;
		if (AntiKBA.totalMoved.containsKey(p)) {
			totalMoved = AntiKBA.totalMoved.get(p);
		}
		if ((yLoc = e.getTo().getY() - e.getFrom().getY()) > 0.0) {
			totalMoved += yLoc;
		}
		int awaitingVelocity2 = 0;
		final int awaitingVelocity3 = 1;
		if (awaitingVelocity > 0) {
			if (totalMoved < 0.3) {
				awaitingVelocity2 += 9;
			} else {
				awaitingVelocity2 = 0;
				totalMoved = 0.0;
				--awaitingVelocity;
			}
			if (ServerUtil.isOnGround(p, -1) || ServerUtil.isOnGround(p, -2) || ServerUtil.isOnGround(p, -3)) {
				awaitingVelocity2 -= 9;
			}
		}
		if (awaitingVelocity2 > awaitingVelocity3) {
			if (totalMoved == 0.0) {
				getAntiCheat().logCheat(this, p, Color.Red + "[1] vertical", "(Type: A)");
			} else {
				if (getAntiCheat().getLag().getPing(p) > 220) {
					return;
				}
				getAntiCheat().logCheat(this, p, Color.Red + "[2] vertical", "(Type: A)");
			}
			awaitingVelocity2 = 0;
			totalMoved = 0.0;
			--awaitingVelocity;
		}
		AntiKBA.awaitingVelocity.put(p, awaitingVelocity);
		AntiKBA.totalMoved.put(p, totalMoved);
	}

	@SuppressWarnings( "unused" )
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void Velocity(PlayerVelocityEvent e) {
		double yVio;
		long lastVelocity;
		final Player p = e.getPlayer();
		if (ServerUtil.isOnBlock(p, 0, new Material[]{Material.WEB}) || ServerUtil.isOnBlock(p, 1, new Material[]{Material.WEB})) {
			return;
		}
		if (ServerUtil.isHoveringOverWater(p, 1) || ServerUtil.isHoveringOverWater(p, 0)) {
			return;
		}
		if (ServerUtil.isOnGround(p, -1) || ServerUtil.isOnGround(p, -2) || ServerUtil.isOnGround(p, -3)) {
			return;
		}
		if (p.getAllowFlight()) {
			return;
		}
		if (AntiKBA.lastVelocity.containsKey(p) && (lastVelocity = System.currentTimeMillis() - AntiKBA.lastVelocity.get(p)) < 500) {
			return;
		}
		final Vector vector = e.getVelocity();
		final double yLoc = Math.abs(vector.getY());
		if (yLoc > 0.0 && (yVio = ((int)(Math.pow(yLoc + 2.0, 2.0) * 5.0))) > 20.0) {
			int awaitingVelocity = 0;
			if (AntiKBA.awaitingVelocity.containsKey(p)) {
				awaitingVelocity = AntiKBA.awaitingVelocity.get(p);
			}
			AntiKBA.awaitingVelocity.put(p, ++awaitingVelocity);
			AntiKBA.lastVelocity.put(p, System.currentTimeMillis());
		}
	}
}