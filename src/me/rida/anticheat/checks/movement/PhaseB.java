package me.rida.anticheat.checks.movement;

import java.util.Map;
import java.util.WeakHashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.utils.BlockUtil;
import me.rida.anticheat.utils.BoundingBox;
import me.rida.anticheat.utils.MathUtil;
import me.rida.anticheat.utils.PlayerUtil;
import me.rida.anticheat.utils.ReflectionUtil;


public class PhaseB extends Check implements Listener {
	private Map<Player, Long> lastDoorSwing;

	public PhaseB(AntiCheat AntiCheat) {
		super("PhaseB", "Phase", CheckType.Movement, true, false, false, false, true, 40, 10, 600000L, AntiCheat);
		lastDoorSwing = new WeakHashMap<>();
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onMove(PlayerMoveEvent e) {


		Player player = e.getPlayer();

		if(AntiCheat.isInPhaseTimer(player)) {
			return;
		}

		if (player.getAllowFlight()
				|| player.getVehicle() != null
				|| MathUtil.elapsed(lastDoorSwing.getOrDefault(player, 0L)) < 500
				|| BlockUtil.isNearPistion(player)
				|| PlayerUtil.isNearAllowed(player)) {
			return;
		}
		if (BlockUtil.isNearLava(player) && BlockUtil.isNearWater(player)) {
			return;
		}
		if (PlayerUtil.isNearLog(player) && PlayerUtil.isNearGrass(player)) {
			return;
		}

		float minX = (float) Math.min(e.getFrom().getX(), e.getTo().getX()), minY = (float) Math.min(e.getFrom().getY(), e.getTo().getY()), minZ = (float) Math.min(e.getFrom().getZ(), e.getTo().getZ()),
				maxX = (float) Math.max(e.getFrom().getX(), e.getTo().getX()), maxY = (float) Math.max(e.getFrom().getY(), e.getTo().getY()), maxZ = (float) Math.max(e.getFrom().getZ(), e.getTo().getZ());

		Object box = new BoundingBox(minX, minY, minZ, maxX, maxY, maxZ).add(0f, 0f, 0f, 0f, 1.8f, 0f).toAxisAlignedBB();
			if(ReflectionUtil.getCollidingBlocks(e.getPlayer(), box)) {
				getAntiCheat().logCheat(this, player, "[1]", "(Type: B)");
			}
		}
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {

		if(AntiCheat.isInPhaseTimer(event.getPlayer())) {
			return;
		}

		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if ((BlockUtil.isDoor(event.getClickedBlock())
					|| BlockUtil.isFenceGate(event.getClickedBlock())
					|| BlockUtil.isTrapDoor(event.getClickedBlock()))
					&& !event.isCancelled()) {
				lastDoorSwing.put(event.getPlayer(), System.currentTimeMillis());
			}
		}
	}
}