package me.rida.anticheat.checks.movement;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.data.DataPlayer;
import me.rida.anticheat.utils.BlockUtil;
import me.rida.anticheat.utils.NewVelocityUtil;
import me.rida.anticheat.utils.PlayerUtil;
import me.rida.anticheat.utils.VelocityUtil;

public class FlyC extends Check {
	public FlyC(AntiCheat AntiCheat) {
		super("FlyC", "Fly", CheckType.Movement, true, false, false, false, 4, 1, 600000L, AntiCheat);
	}

	public static void resetCBPE(Player p) {
		blockPlacedFC.remove(p.getName().toString());
	}
	public static List<String> blockPlacedFC = new ArrayList<>();
	@EventHandler(priority = EventPriority.MONITOR)
	public static void blockPlaceCancelled (BlockPlaceEvent e) {
		if (e.isCancelled()) {
			blockPlacedFC.add(e.getPlayer().getName().toString());
		}
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onMove(PlayerMoveEvent e) {

		Location from = e.getFrom();
		Location to = e.getTo();
		Player p = e.getPlayer();
		DataPlayer data = AntiCheat.getInstance().getDataManager().getData(p);
		if (p.getGameMode().equals(GameMode.CREATIVE)
				|| p.getAllowFlight()
				|| e.getPlayer().getVehicle() != null
				|| p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SPONGE
				|| PlayerUtil.isOnClimbable(p, 0)
				|| PlayerUtil.wasOnSlime(p)
				|| PlayerUtil.isNearSlime(p)
				|| BlockUtil.isNearLiquid(p)
				|| PlayerUtil.isInLiquid(p)
				|| data == null
				|| blockPlacedFC.contains(p.getName().toString())
				|| PlayerUtil.isOnSlime(p.getLocation())
				|| PlayerUtil.isOnClimbable(p, 1) || VelocityUtil.didTakeVelocity(p)
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()
				|| PlayerUtil.isNearSlime(e.getFrom())
				|| PlayerUtil.isNearSlime(e.getTo())) {
			return;
		}

		Vector vec = new Vector(to.getX(), to.getY(), to.getZ());
		double Distance = vec.distance(new Vector(from.getX(), from.getY(), from.getZ()));
		if (!NewVelocityUtil.didTakeVel(p) && !PlayerUtil.wasOnSlime(p)) {
			if (p.getFallDistance() == 0.0f && p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR && p.getLocation().getBlock().getRelative(BlockFace.UP).getType() == Material.AIR) {
				if (PlayerUtil.isNearSlime(from)
						|| PlayerUtil.isNearSlime(to)) {
					return;
				}
				if (Distance > 0.50 && !PlayerUtil.isOnGround(p) && e.getTo().getY() > e.getFrom().getY() && e.getTo().getX() == e.getFrom().getX() && e.getTo().getZ() == e.getFrom().getZ() && !VelocityUtil.didTakeVelocity(p)) {
					getAntiCheat().logCheat(this, p, "[1] Distance: " + Distance + " To: " + e.getTo().getY() + " From: " + e.getFrom().getY(),  "(Type: C)");
				} else if (Distance > 0.90 && !PlayerUtil.isOnGround(p) && e.getTo().getY() > e.getFrom().getY() && e.getTo().getX() == e.getFrom().getX() && e.getTo().getZ() == e.getFrom().getZ()) {
					getAntiCheat().logCheat(this, p, "[2] Distance: " + Distance + " To: " + e.getTo().getY() + " From: " + e.getFrom().getY(),  "(Type: C)");
				} else if (Distance > 1.0 && !PlayerUtil.isOnGround(p) && e.getTo().getY() > e.getFrom().getY() && e.getTo().getX() == e.getFrom().getX() && e.getTo().getZ() == e.getFrom().getZ()) {
					getAntiCheat().logCheat(this, p, "[3] Distance: " + Distance + " To: " + e.getTo().getY() + " From: " + e.getFrom().getY(),  "(Type: C)");
				}
			}
		}
		if (!NewVelocityUtil.didTakeVel(p) && !PlayerUtil.wasOnSlime(p)) {
			if (e.getTo().getY() > e.getFrom().getY() && data.getAirTicks() > 2 && !VelocityUtil.didTakeVelocity(p)) {
				if (!PlayerUtil.isOnGround4(p) && !PlayerUtil.onGround2(p) && !PlayerUtil.isOnGround(p)) {
					if (PlayerUtil.getDistanceToGround(p) > 2) {
						if (data.getGoingUp_Blocks() >= 3 && data.getAirTicks() >= 10) {
							if (p.getFallDistance() == 0.0f && p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR && p.getLocation().getBlock().getRelative(BlockFace.UP).getType() == Material.AIR) {
								if (PlayerUtil.isNearSlime(from)
										|| PlayerUtil.isNearSlime(to)
										|| Distance <= 3.5) {
									return;
								}
								getAntiCheat().logCheat(this, p, "[4] Distance: " + Distance + " To: " + e.getTo().getY() + " From: " + e.getFrom().getY(), "(Type: C)");
							} else {
								data.setGoingUp_Blocks(data.getGoingUp_Blocks() + 1);
							}
						} else {
							data.setGoingUp_Blocks(0);
						}
					} else {
						data.setGoingUp_Blocks(0);
					}
				} else if (e.getTo().getY() < e.getFrom().getY()) {
					data.setGoingUp_Blocks(0);
				} else {
					data.setGoingUp_Blocks(0);
				}
			} else {
				data.setGoingUp_Blocks(0);
			}
		}
	}
}