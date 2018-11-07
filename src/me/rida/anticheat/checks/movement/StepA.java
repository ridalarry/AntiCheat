package me.rida.anticheat.checks.movement;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.utils.UtilCheat;
import me.rida.anticheat.utils.UtilsB;

public class StepA extends Check {
	double stepHeight;

	public StepA(AntiCheat AntiCheat) {
		super("StepA", "Step", AntiCheat);

		setEnabled(true);
		setBannable(false);

		setMaxViolations(7);
		setViolationsToNotify(1);
		setViolationResetTime(90000);
	}

	public boolean isOnGround(Player player) {
		if (UtilsB.isOnClimbable(player, 0)) {
			return false;
		}
		if (player.getVehicle() != null) {
			return false;
		}
		Material type = player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType();
		if ((type != Material.AIR) && (type.isBlock()) && (type.isSolid()) && (type != Material.LADDER)
				&& (type != Material.VINE)) {
			return true;
		}
		Location a = player.getLocation().clone();
		a.setY(a.getY() - 0.5D);
		type = a.getBlock().getType();
		if ((type != Material.AIR) && (type.isBlock()) && (type.isSolid()) && (type != Material.LADDER)
				&& (type != Material.VINE)) {
			return true;
		}
		a = player.getLocation().clone();
		a.setY(a.getY() + 0.5D);
		type = a.getBlock().getRelative(BlockFace.DOWN).getType();
		if ((type != Material.AIR) && (type.isBlock()) && (type.isSolid()) && (type != Material.LADDER)
				&& (type != Material.VINE)) {
			return true;
		}
		if (UtilCheat.isBlock(player.getLocation().getBlock().getRelative(BlockFace.DOWN),
				new Material[] { Material.FENCE, Material.FENCE_GATE, Material.COBBLE_WALL, Material.LADDER })) {
			return true;
		}
		return false;
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();

		if (!getAntiCheat().isEnabled() 
				|| !isOnGround(player)
				|| player.getAllowFlight()
				|| getAntiCheat().isSotwMode()
				|| player.hasPotionEffect(PotionEffectType.JUMP)
				|| getAntiCheat().LastVelocity.containsKey(player.getUniqueId())
				|| UtilsB.isOnClimbable(player, 0)
				|| UtilCheat.slabsNear(player.getLocation())
				|| player.getLocation().getBlock().getType().equals(Material.WATER)
				|| player.getLocation().getBlock().getType().equals(Material.STATIONARY_WATER)) {
			return;
		}

		double yDist = event.getTo().getY() - event.getFrom().getY();
		if (yDist < 0) {
			return;
		}
		double YSpeed = UtilsB.offset(UtilsB.getVerticalVector(event.getFrom().toVector()),
				UtilsB.getVerticalVector(event.getTo().toVector()));
		if (yDist > 0.95) {
			this.dumplog(player, "Height (Logged): " + yDist);
			this.getAntiCheat().logCheat(this, player, "[1] " + Math.round(yDist) + " blocks", "(Type: A)");
			return;
		}
		if (((YSpeed == 0.25D || (YSpeed >= 0.58D && YSpeed < 0.581D)) && yDist > 0.0D
				|| (YSpeed > 0.2457D && YSpeed < 0.24582D) || (YSpeed > 0.329 && YSpeed < 0.33))
				&& !player.getLocation().clone().subtract(0.0D, 0.1, 0.0D).getBlock().getType().equals(Material.SNOW)) {
			this.getAntiCheat().logCheat(this, player, "[2] Speed: " + YSpeed + " Block: " + player.getLocation().clone().subtract(0.0D, 0.1D, 0.0D).getBlock().getType().toString(), "(Type: A)");
			return;
		}
		ArrayList<Block> blocks = UtilsB.getBlocksAroundCenter(player.getLocation(), 1);
		for (Block block : blocks) {
			if (block.getType().isSolid()) {
				if ((YSpeed >= 0.321 && YSpeed < 0.322)) {
					this.getAntiCheat().logCheat(this, player, "[3] Speed: " + YSpeed, "(Type: A)");
					return;
				}
			}
		}
	}
}