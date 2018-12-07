package me.rida.anticheat.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class ExtraUtil {

	public static boolean isOnGround(Player player) {
		return isOnGround(player, 0.25);
	}
	public static boolean isOnGround(Player player, double yExpanded) {
		Object box = ReflectionUtil.modifyBoundingBox(ReflectionUtil.getBoundingBox(player), 0, -yExpanded, 0,0,0,0);

		return ReflectionUtil.getCollidingBlocks(player, box).size() > 0;
	}

	public static boolean isNotSpider(Player player) {
		return isOnGround(player, 1.25);
	} 

	public static boolean isInLiquid(Player player) {
		Object box = ReflectionUtil.getBoundingBox(player);

		double minX = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "a"), box);
		double minY = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "b"), box);
		double minZ = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "c"), box);
		double maxX = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "d"), box);
		double maxY = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "e"), box);
		double maxZ = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "f"), box);

		for(double x = minX ; x < maxX ; x++) {
			for(double y = minY ; y < maxY ; y++) {
				for(double z = minZ ; z < maxZ ; z++) {
					Block block = new Location(player.getWorld(), x, y, z).getBlock();

					if(isLiquid(block)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isInStairs(Player player) {
		Object box = ReflectionUtil.modifyBoundingBox(ReflectionUtil.getBoundingBox(player), 0, -0.5,0,0,0,0);

		double minX = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "a"), box);
		double minY = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "b"), box);
		double minZ = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "c"), box);
		double maxX = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "d"), box);
		double maxY = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "e"), box);
		double maxZ = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "f"), box);

		for(double x = minX ; x < maxX ; x++) {
			for(double y = minY ; y < maxY ; y++) {
				for(double z = minZ ; z < maxZ ; z++) {
					Block block = new Location(player.getWorld(), x, y, z).getBlock();

					if(isStair(block)
							|| isSlab(block)
							|| block.getType().equals(Material.SKULL)
							|| block.getType().equals(Material.CAKE_BLOCK)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isOnClimbable(Player player) {
		return isClimbableBlock(player.getLocation().getBlock())
				|| isClimbableBlock(player.getLocation().add(0, 1,0).getBlock());
	}



	public static boolean inUnderBlock(Player player) {
		Object box = ReflectionUtil.modifyBoundingBox(ReflectionUtil.getBoundingBox(player), 0, 0,0,0,1,0);

		double minX = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "a"), box);
		double minY = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "b"), box);
		double minZ = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "c"), box);
		double maxX = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "d"), box);
		double maxY = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "e"), box);
		double maxZ = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "f"), box);

		for(double x = minX ; x < maxX ; x++) {
			for(double y = minY ; y < maxY ; y++) {
				for(double z = minZ ; z < maxZ ; z++) {
					Block block = new Location(player.getWorld(), x, y, z).getBlock();

					if(block.getType().isSolid()) {
						return true;
					}
				}
			}
		}
		return false;
	}


	public static boolean isOnIce(Player player) {
		Object box = ReflectionUtil.modifyBoundingBox(ReflectionUtil.getBoundingBox(player), 0, -0.5,0,0,0,0);

		double minX = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "a"), box);
		double minY = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "b"), box);
		double minZ = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "c"), box);
		double maxX = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "d"), box);
		double maxY = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "e"), box);
		double maxZ = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "f"), box);

		for(double x = minX ; x < maxX ; x++) {
			for(double y = minY ; y < maxY ; y++) {
				for(double z = minZ ; z < maxZ ; z++) {
					Block block = new Location(player.getWorld(), x, y, z).getBlock();

					if(block.getType().equals(Material.ICE)
							|| block.getType().equals(Material.PACKED_ICE)) {
						return true;
					}
				}
			}
		}
		return false;
	}


	public static float yawTo180F(float flub) {
		if ((flub %= 360.0f) >= 180.0f) {
			flub -= 360.0f;
		}
		if (flub < -180.0f) {
			flub += 360.0f;
		}
		return flub;
	}

	public static float[] getRotations(Location one, Location two) {
		double diffX = two.getX() - one.getX();
		double diffZ = two.getZ() - one.getZ();
		double diffY = two.getY() + 2.0 - 0.4 - (one.getY() + 2.0);
		double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
		float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
		float pitch = (float) (-Math.atan2(diffY, dist) * 180.0 / 3.141592653589793);
		return new float[]{yaw, pitch};
	}

	public static double[] getOffsetFromEntity(Player player, LivingEntity entity) {
		double yawOffset = Math.abs(yawTo180F(player.getEyeLocation().getYaw()) - yawTo180F(getRotations(player.getLocation(), entity.getLocation())[0]));
		double pitchOffset = Math.abs(Math.abs(player.getEyeLocation().getPitch()) - Math.abs(getRotations(player.getLocation(), entity.getLocation())[1]));
		return new double[]{yawOffset, pitchOffset};
	}
	public static boolean isLiquid(Block block) {
		Material type = block.getType();

		return type.equals(Material.WATER) || type.equals(Material.STATIONARY_LAVA)
				|| type.equals(Material.LAVA) || type.equals(Material.STATIONARY_LAVA);
	}

	public static boolean isClimbableBlock(Block block) {
		return block.getType().equals(Material.LADDER)
				|| block.getType().equals(Material.VINE);
	}

	@SuppressWarnings("deprecation")
	public static boolean isSlab(Block block) {
		return block.getType().getId() == 44 || block.getType().getId() == 126 || block.getType().getId() == 205 || block.getType().getId() == 182;
	}

	@SuppressWarnings("deprecation")
	public static boolean isStair(Block block) {
		return block.getType().equals(Material.ACACIA_STAIRS) || block.getType().equals(Material.BIRCH_WOOD_STAIRS) || block.getType().equals(Material.BRICK_STAIRS) || block.getType().equals(Material.COBBLESTONE_STAIRS) || block.getType().equals(Material.DARK_OAK_STAIRS) || block.getType().equals(Material.NETHER_BRICK_STAIRS) || block.getType().equals(Material.JUNGLE_WOOD_STAIRS) || block.getType().equals(Material.QUARTZ_STAIRS) || block.getType().equals(Material.SMOOTH_STAIRS) || block.getType().equals(Material.WOOD_STAIRS) || block.getType().equals(Material.SANDSTONE_STAIRS) || block.getType().equals(Material.SPRUCE_WOOD_STAIRS) || block.getType().getId() == 203 || block.getType().getId() == 180;
	}

	public static boolean groundAround(Location loc) {
		for (int radius = 2, x = -radius; x < radius; ++x) {
			for (int y = -radius; y < radius; ++y) {
				for (int z = -radius; z < radius; ++z) {
					Material mat = loc.getWorld().getBlockAt(loc.add((double)x, (double)y, (double)z)).getType();
					if (mat.isSolid() || mat == Material.WATER || mat == Material.STATIONARY_WATER || mat == Material.LAVA || mat == Material.STATIONARY_LAVA) {
						loc.subtract((double)x, (double)y, (double)z);
						return true;
					}
					loc.subtract((double)x, (double)y, (double)z);
				}
			}
		}
		return false;
	}


}
