package me.rida.anticheat.checks.movement;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.data.DataPlayer;
import me.rida.anticheat.utils.BlockUtil;
import me.rida.anticheat.utils.MathUtil;
import me.rida.anticheat.utils.NewVelocityUtil;
import me.rida.anticheat.utils.PlayerUtil;
import me.rida.anticheat.utils.VelocityUtil;

public class FlyA extends Check {
	public FlyA(AntiCheat AntiCheat) {
		super("FlyA", "Fly", CheckType.Movement, AntiCheat);
		this.setBannable(true);
		this.setEnabled(true);
		setMaxViolations(4);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	private void onMove(PlayerMoveEvent e) {
		final Location from = e.getFrom();
		final Location to = e.getTo();
		final Player p = e.getPlayer();
		if (p.getGameMode().equals(GameMode.CREATIVE)
				|| p.getAllowFlight()
				|| e.getPlayer().getVehicle() != null
				|| p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SPONGE
				|| PlayerUtil.isOnClimbable(p, 0)
				|| PlayerUtil.wasOnSlime(p)
				|| BlockUtil.isNearSlime(p)
				|| BlockUtil.isNearLiquid(p)
				|| PlayerUtil.isInLiquid(p)
				|| PlayerUtil.isOnSlime(p.getLocation())
				|| PlayerUtil.isOnClimbable(p, 1) || VelocityUtil.didTakeVelocity(p)
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
			return;
		}

		final DataPlayer data = AntiCheat.getInstance().getDataManager().getData(p);

		if (data == null) {
			return;
		}
		if (!NewVelocityUtil.didTakeVel(p) && !PlayerUtil.wasOnSlime(p)) {
			final Vector vec = new Vector(to.getX(), to.getY(), to.getZ());
			final double Distance = vec.distance(new Vector(from.getX(), from.getY(), from.getZ()));
			if (p.getFallDistance() == 0.0f && p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR && p.getLocation().getBlock().getRelative(BlockFace.UP).getType() == Material.AIR) {
				if (Distance > 0.50 && !PlayerUtil.isOnGround(p) && e.getTo().getY() > e.getFrom().getY() && e.getTo().getX() == e.getFrom().getX() && e.getTo().getZ() == e.getFrom().getZ() && !VelocityUtil.didTakeVelocity(p)) {
					getAntiCheat().logCheat(this, p, "[3] Distance: " + Distance,  "(Type: A)");
				} else if (Distance > 0.90 && !PlayerUtil.isOnGround(p) && e.getTo().getY() > e.getFrom().getY() && e.getTo().getX() == e.getFrom().getX() && e.getTo().getZ() == e.getFrom().getZ()) {
					getAntiCheat().logCheat(this, p, "[2] Distance: " + Distance, "(Type: A)");
				} else if (Distance > 1.0 && !PlayerUtil.isOnGround(p) && e.getTo().getY() > e.getFrom().getY() && e.getTo().getX() == e.getFrom().getX() && e.getTo().getZ() == e.getFrom().getZ()) {
					getAntiCheat().logCheat(this, p, "[3] Distance: " + Distance, "(Type: A)");
				} else if (Distance > 3.24 && !PlayerUtil.isOnGround(p) && e.getTo().getY() > e.getFrom().getY() && e.getTo().getX() == e.getFrom().getX() && e.getTo().getZ() == e.getFrom().getZ()) {
					getAntiCheat().logCheat(this, p, "[4] Distance: " + Distance, "(Type: A)");
				}
			}
		}
		if (!NewVelocityUtil.didTakeVel(p) && !PlayerUtil.wasOnSlime(p)) {
			if (e.getTo().getY() > e.getFrom().getY() && data.getAirTicks() > 2 && !VelocityUtil.didTakeVelocity(p)) {
				if (!PlayerUtil.isOnGround4(p) && !PlayerUtil.onGround2(p) && !PlayerUtil.isOnGround(p)) {
					if (PlayerUtil.getDistanceToGround(p) > 2) {
						if (data.getGoingUp_Blocks() >= 3 && data.getAirTicks() >= 10) {
							// getAntiCheat().logCheat(this, p, "[5] Distance: 10 blocks or more", "(Type: A)");
							//   setBackPlayer(p);
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
		if(!PlayerUtil.isOnGround(p)) {
			final double distanceToGround = getDistanceToGround(p);
			final double yDiff = MathUtil.getVerticalDistance(e.getFrom(), e.getTo());
			int verbose = data.getFlyHoverVerbose();

			if(distanceToGround > 2) {
				verbose = yDiff == 0 ? verbose + 6 : yDiff < 0.06 ? verbose + 4 : 0;
			} else if(data.getAirTicks() > 7
					&& yDiff < 0.001) {
				verbose+= 2;
			} else {
				verbose = 0;
			}

			if(verbose > 20) {
				getAntiCheat().logCheat(this, p, "[6]", "(Type: A)");
				verbose = 0;
			}
			data.setFlyHoverVerbose(verbose);
		}
		if (PlayerUtil.getDistanceToGround(p) >  3) {
			final double OffSet = e.getFrom().getY() - e.getTo().getY();
			long Time = System.currentTimeMillis();
			if (OffSet <= 0.0 || OffSet > 0.16) {
				data.setGlideTicks(0);
				return;
			}
			if (data.getGlideTicks() != 0) {
				Time = data.getGlideTicks();
			}
			final long Millis = System.currentTimeMillis() - Time;
			if (Millis > 200L) {
				if (PlayerUtil.isInLiquid(p)) {
					return;
				}
				if (!BlockUtil.isNearLiquid(p)) {
					getAntiCheat().logCheat(this, p, "[7]", "(Type: A)");

					data.setGlideTicks(0);
				}
			}
			data.setGlideTicks(Time);
		} else {
			data.setGlideTicks(0);
		}
		final double diffY = Math.abs(from.getY() - to.getY());
		final double lastDiffY = data.getLastVelocityFlyY();
		int verboseC = data.getFlyVelocityVerbose();
		final double finalDifference = Math.abs(diffY - lastDiffY);
		if(finalDifference < 0.08
				&& e.getFrom().getY() < e.getTo().getY()
				&& !PlayerUtil.isOnGround(p) && !p.getLocation().getBlock().isLiquid() && !BlockUtil.isNearLiquid(p)
				&& !NewVelocityUtil.didTakeVel(p) && !VelocityUtil.didTakeVelocity(p)) {
			if(++verboseC > 8) {
				if (!PlayerUtil.wasOnSlime(p)) {
					verboseC = 0;
				}
				else {
					getAntiCheat().logCheat(this, p, "[8]", "(Type: A)");
					verboseC = 0;
				}
			}
		} else {
			verboseC = verboseC > 0 ? verboseC - 1 : 0;
		}
		data.setLastVelocityFlyY(diffY);
		data.setFlyVelocityVerbose(verboseC);
	}

	private int getDistanceToGround(Player p){
		final Location loc = p.getLocation().clone();
		final double y = loc.getBlockY();
		int distance = 0;
		for (double i = y; i >= 0; i--){
			loc.setY(i);
			if(loc.getBlock().getType().isSolid() || loc.getBlock().isLiquid())break;
			distance++;
		}
		return distance;
	}
}