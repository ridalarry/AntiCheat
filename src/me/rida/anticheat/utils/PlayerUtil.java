package me.rida.anticheat.utils;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.data.DataPlayer;

public class PlayerUtil {
	private static ImmutableSet<Material> ground = Sets.immutableEnumSet(Material.SUGAR_CANE, Material.SUGAR_CANE_BLOCK,
			Material.TORCH, Material.ACTIVATOR_RAIL, Material.AIR, Material.CARROT, Material.CROPS, Material.DEAD_BUSH,
			Material.DETECTOR_RAIL, Material.DIODE_BLOCK_OFF, Material.DIODE_BLOCK_ON, Material.DOUBLE_PLANT,
			Material.FIRE, Material.GOLD_PLATE, Material.IRON_PLATE, Material.LAVA, Material.LEVER, Material.LONG_GRASS,
			Material.MELON_STEM, Material.NETHER_WARTS, Material.PORTAL, Material.POTATO, Material.POWERED_RAIL,
			Material.PUMPKIN_STEM, Material.RAILS, Material.RED_ROSE, Material.REDSTONE_COMPARATOR_OFF,
			Material.REDSTONE_COMPARATOR_ON, Material.REDSTONE_TORCH_OFF, Material.REDSTONE_TORCH_ON,
			Material.REDSTONE_WIRE, Material.SAPLING, Material.SEEDS, Material.SIGN, Material.SIGN_POST,
			Material.STATIONARY_LAVA, Material.STATIONARY_WATER, Material.STONE_BUTTON, Material.STONE_PLATE,
			Material.SUGAR_CANE_BLOCK, Material.TORCH, Material.TRIPWIRE, Material.TRIPWIRE_HOOK, Material.WALL_SIGN,
			Material.WATER, Material.WEB, Material.WOOD_BUTTON, Material.WOOD_PLATE, Material.YELLOW_FLOWER);



	public static boolean onGround2(Player p) {
		if (p.getLocation().getBlock().getType() == Material.AIR) {
			return false;
		} else {
			return true;
		}
	}
	public static int hasDepthStrider(Player player) {
		if(player.getInventory().getBoots() != null && player.getInventory().getBoots().containsEnchantment(Enchantment.getByName("DEPTH_STRIDER"))) {
			return player.getInventory().getBoots().getEnchantments().get(Enchantment.getByName("DEPTH_STRIDER"));
		}
		return 0;
	}

	public static boolean isOnGround4(Player player) {
		if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR) {
			return true;
		}
		Location a = player.getLocation().clone();
		a.setY(a.getY() - 0.5);
		if (a.getBlock().getType() != Material.AIR) {
			return true;
		}
		a = player.getLocation().clone();
		a.setY(a.getY() + 0.5);
		return a.getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR || isBlock(player.getLocation().getBlock().getRelative(BlockFace.DOWN),
				new Material[]{Material.FENCE, Material.FENCE_GATE, Material.COBBLE_WALL, Material.LADDER});
	}

	public static int getDistanceToGround(Player p){
		final Location loc = p.getLocation().clone();
		final double y = loc.getBlockY();
		int distance = 0;
		for (double i = y; i >= 0; i--){
			loc.setY(i);
			if(loc.getBlock().getType().isSolid())break;
			distance++;
		}
		return distance;
	}

	private static boolean isGround(Material material) {
		return ground.contains(material);
	}

	public static boolean isOnGround(Location loc) {
		final double diff = .3;

		return !isGround(loc.clone().add(0, -.1, 0).getBlock().getType())
				|| !isGround(loc.clone().add(diff, -.1, 0).getBlock().getType())
				|| !isGround(loc.clone().add(-diff, -.1, 0).getBlock().getType())
				|| !isGround(loc.clone().add(0, -.1, diff).getBlock().getType())
				|| !isGround(loc.clone().add(0, -.1, -diff).getBlock().getType())
				|| !isGround(loc.clone().add(diff, -.1, diff).getBlock().getType())
				|| !isGround(loc.clone().add(diff, -.1, -diff).getBlock().getType())
				|| !isGround(loc.clone().add(-diff, -.1, diff).getBlock().getType())
				|| !isGround(loc.clone().add(-diff, -.1, -diff).getBlock().getType())
				|| (BlockUtil.getBlockHeight(loc.clone().subtract(0.0D, 0.5D, 0.0D).getBlock()) != 0.0D &&
				(!isGround(loc.clone().add(diff, BlockUtil.getBlockHeight(loc.getBlock()) - 0.1, 0).getBlock().getType())
						|| !isGround(loc.clone().add(-diff, BlockUtil.getBlockHeight(loc.getBlock()) - 0.1, 0).getBlock().getType())
						|| !isGround(loc.clone().add(0, BlockUtil.getBlockHeight(loc.getBlock()) - 0.1, diff).getBlock().getType())
						|| !isGround(loc.clone().add(0, BlockUtil.getBlockHeight(loc.getBlock()) - 0.1, -diff).getBlock().getType())
						|| !isGround(loc.clone().add(diff, BlockUtil.getBlockHeight(loc.getBlock()) - 0.1, diff).getBlock().getType())
						|| !isGround(loc.clone().add(diff, BlockUtil.getBlockHeight(loc.getBlock()) - 0.1, -diff).getBlock().getType())
						|| !isGround(loc.clone().add(-diff, BlockUtil.getBlockHeight(loc.getBlock()) - 0.1, diff).getBlock().getType())
						|| !isGround(loc.clone().add(-diff, BlockUtil.getBlockHeight(loc.getBlock()) - 0.1, -diff).getBlock().getType())));
	}


	public static boolean isOnGround(Player player) {
		final Object box = ServerUtil.getBoundingBox(player);
		final Object outcome = ServerUtil.getMethodValue(ServerUtil.getMethod(box.getClass(), "grow", double.class, double.class, double.class), box, 0D, 0.1D, 0D);
		return ServerUtil.inBlock(player, outcome);
	}

	public static boolean hasPistonNear(Player player) {
		final Object box = ServerUtil.getMethodValue(ServerUtil.getMethod(ServerUtil.getBoundingBox(player).getClass(), "grow", double.class, double.class, double.class), ServerUtil.getBoundingBox(player), 2D, 3D, 2D);

		final Collection<?> collidingBlocks = ServerUtil.getCollidingBlocks(player, box);

		for(final Object object : collidingBlocks) {
			final double x = (double) ServerUtil.getFieldValue(ServerUtil.getFieldByName(object.getClass(), "a"), object);
			final double y = (double) ServerUtil.getFieldValue(ServerUtil.getFieldByName(object.getClass(), "b"), object);
			final double z = (double) ServerUtil.getFieldValue(ServerUtil.getFieldByName(object.getClass(), "c"), object);

			final Block block = new Location(player.getWorld(), x, y, z).getBlock();
			if(BlockUtil.isPiston(block)) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasIceNear(Player player) {
		final Object box = ServerUtil.getMethodValue(ServerUtil.getMethod(ServerUtil.getBoundingBox(player).getClass(), "grow", double.class, double.class, double.class), ServerUtil.getBoundingBox(player), 0D, 1.5D, 0D);

		final Collection<?> collidingBlocks = ServerUtil.getCollidingBlocks(player, box);

		for(final Object object : collidingBlocks) {
			final double x = (double) ServerUtil.getFieldValue(ServerUtil.getFieldByName(object.getClass(), "a"), object);
			final double y = (double) ServerUtil.getFieldValue(ServerUtil.getFieldByName(object.getClass(), "b"), object);
			final double z = (double) ServerUtil.getFieldValue(ServerUtil.getFieldByName(object.getClass(), "c"), object);

			final Block block = new Location(player.getWorld(), x, y, z).getBlock();

			if(BlockUtil.isIce(block)) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public static boolean wasOnSlime(Player player) {
		final DataPlayer user = AntiCheat.getInstance().getDataManager().getData(player);

		if(user != null
				&&user.getSetbackLocation() != null) {
			final Location location = user.getSetbackLocation().clone().subtract(0.0D, 1.0D, 0.0D);

			if(location.getBlock().getTypeId() == 165){
				return true;
			}
		}
		return false;
	}

	public static boolean isOnGround3(Player player) {
		final Object box = ServerUtil.getBoundingBox(player);
		final Object outcome = ServerUtil.getMethodValue(ServerUtil.getMethod(box.getClass(), "grow", double.class, double.class, double.class), box, 0D, 0.3D, 0D);
		return ServerUtil.inBlock(player, outcome);
	}

	public static boolean isInWater(Location loc) {
		final double diff = .3;
		return BlockUtil.isLiquid(loc.clone().add(0, 0D, 0).getBlock())
				|| BlockUtil.isLiquid(loc.clone().add(diff, 0D, 0).getBlock())
				|| BlockUtil.isLiquid(loc.clone().add(-diff, 0D, 0).getBlock())
				|| BlockUtil.isLiquid(loc.clone().add(0, 0D, diff).getBlock())
				|| BlockUtil.isLiquid(loc.clone().add(0, 0D, -diff).getBlock())
				|| BlockUtil.isLiquid(loc.clone().add(diff, 0D, diff).getBlock())
				|| BlockUtil.isLiquid(loc.clone().add(diff, 0D, -diff).getBlock())
				|| BlockUtil.isLiquid(loc.clone().add(-diff, 0D, diff).getBlock())
				|| BlockUtil.isLiquid(loc.clone().add(-diff, 0D, -diff).getBlock())
				|| (BlockUtil.getBlockHeight(loc.clone().subtract(0.0D, 0.5D, 0.0D).getBlock()) != 0.0D &&
				(BlockUtil.isLiquid(loc.clone().add(diff,  0D, 0).getBlock())
						|| BlockUtil.isLiquid(loc.clone().add(-diff,  0D, 0).getBlock())
						|| BlockUtil.isLiquid(loc.clone().add(0,  0D, diff).getBlock())
						|| BlockUtil.isLiquid(loc.clone().add(0,  0D, -diff).getBlock())
						|| BlockUtil.isLiquid(loc.clone().add(diff,  0D, diff).getBlock())
						|| BlockUtil.isLiquid(loc.clone().add(diff,  0D, -diff).getBlock())
						|| BlockUtil.isLiquid(loc.clone().add(-diff,  0D, diff).getBlock())
						|| BlockUtil.isLiquid(loc.clone().add(-diff,  0D, -diff).getBlock())));
	}
	public static boolean isOnSlime(Location loc) {
		final double diff = .3;
		return BlockUtil.isSlime(loc.clone().add(0, 0D, 0).getBlock())
				|| BlockUtil.isSlime(loc.clone().add(diff, 0D, 0).getBlock())
				|| BlockUtil.isSlime(loc.clone().add(-diff, 0D, 0).getBlock())
				|| BlockUtil.isSlime(loc.clone().add(0, 0D, diff).getBlock())
				|| BlockUtil.isSlime(loc.clone().add(0, 0D, -diff).getBlock())
				|| BlockUtil.isSlime(loc.clone().add(diff, 0D, diff).getBlock())
				|| BlockUtil.isSlime(loc.clone().add(diff, 0D, -diff).getBlock())
				|| BlockUtil.isSlime(loc.clone().add(-diff, 0D, diff).getBlock())
				|| BlockUtil.isSlime(loc.clone().add(-diff, 0D, -diff).getBlock());
	}


	public static boolean isOnSlab(Location loc) {
		final double diff = .3;
		return BlockUtil.isSlab(loc.clone().add(0, 0D, 0).getBlock())
				|| BlockUtil.isSlab(loc.clone().add(diff, 0D, 0).getBlock())
				|| BlockUtil.isSlab(loc.clone().add(-diff, 0D, 0).getBlock())
				|| BlockUtil.isSlab(loc.clone().add(0, 0D, diff).getBlock())
				|| BlockUtil.isSlab(loc.clone().add(0, 0D, -diff).getBlock())
				|| BlockUtil.isSlab(loc.clone().add(diff, 0D, diff).getBlock())
				|| BlockUtil.isSlab(loc.clone().add(diff, 0D, -diff).getBlock())
				|| BlockUtil.isSlab(loc.clone().add(-diff, 0D, diff).getBlock())
				|| BlockUtil.isSlab(loc.clone().add(-diff, 0D, -diff).getBlock());
	}

	public static boolean isOnStair(Location loc) {
		final double diff = 0.3;
		return 	BlockUtil.isStair(loc.clone().add(0, 0D, 0).getBlock())
				|| BlockUtil.isStair(loc.clone().add(diff, 0D, 0).getBlock())
				|| BlockUtil.isStair(loc.clone().add(-diff, 0D, 0).getBlock())
				|| BlockUtil.isStair(loc.clone().add(0, 0D, diff).getBlock())
				|| BlockUtil.isStair(loc.clone().add(0, 0D, -diff).getBlock())
				|| BlockUtil.isStair(loc.clone().add(diff, 0D, diff).getBlock())
				|| BlockUtil.isStair(loc.clone().add(diff, 0D, -diff).getBlock())
				|| BlockUtil.isStair(loc.clone().add(-diff, 0D, diff).getBlock())
				|| BlockUtil.isStair(loc.clone().add(-diff, 0D, -diff).getBlock());
	}
	public static boolean isOnFence(Location loc) {
		final double diff = 0.3;
		return 	BlockUtil.isStair(loc.clone().add(0, 0D, 0).getBlock())
				|| BlockUtil.isFence(loc.clone().add(diff, 0D, 0).getBlock())
				|| BlockUtil.isFence(loc.clone().add(-diff, 0D, 0).getBlock())
				|| BlockUtil.isFence(loc.clone().add(0, 0D, diff).getBlock())
				|| BlockUtil.isFence(loc.clone().add(0, 0D, -diff).getBlock())
				|| BlockUtil.isFence(loc.clone().add(diff, 0D, diff).getBlock())
				|| BlockUtil.isFence(loc.clone().add(diff, 0D, -diff).getBlock())
				|| BlockUtil.isFence(loc.clone().add(-diff, 0D, diff).getBlock())
				|| BlockUtil.isFence(loc.clone().add(-diff, 0D, -diff).getBlock());
	}
	public static boolean isOnPressure(Location loc) {
		final double diff = 0.3;
		return 	BlockUtil.isStair(loc.clone().add(0, 0D, 0).getBlock())
				|| BlockUtil.isStair(loc.clone().add(diff, 0D, 0).getBlock())
				|| BlockUtil.isStair(loc.clone().add(-diff, 0D, 0).getBlock())
				|| BlockUtil.isStair(loc.clone().add(0, 0D, diff).getBlock())
				|| BlockUtil.isStair(loc.clone().add(0, 0D, -diff).getBlock())
				|| BlockUtil.isStair(loc.clone().add(diff, 0D, diff).getBlock())
				|| BlockUtil.isStair(loc.clone().add(diff, 0D, -diff).getBlock())
				|| BlockUtil.isStair(loc.clone().add(-diff, 0D, diff).getBlock())
				|| BlockUtil.isStair(loc.clone().add(-diff, 0D, -diff).getBlock());
	}

	public static boolean hasSlabsNear(Location location) {
		for(final Block block : BlockUtil.getSurroundingXZ(location.getBlock(), true)) {
			if(BlockUtil.isSlab(block)) {
				return true;
			}
		}
		return false;
	}


	public static boolean isOnClimbable(Player player, int blocks) {
		if (blocks == 0) {
			for (final Block block : getSurrounding(player.getLocation().getBlock(), false)) {
				if (block.getType() == Material.LADDER || block.getType() == Material.VINE) {
					return true;
				}
			}
		} else {
			for (final Block block : getSurrounding(player.getLocation().clone().add(0.0D, 1.0D, 0.0D).getBlock(),
					false)) {
				if (block.getType() == Material.LADDER || block.getType() == Material.VINE) {
					return true;
				}
			}
		}
		return player.getLocation().getBlock().getType() == Material.LADDER
				|| player.getLocation().getBlock().getType() == Material.VINE;
	}

	public static boolean isInWeb(Player player) {
		if (player.getLocation().getBlock().getType() != Material.WEB && player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.WEB && player.getLocation().getBlock().getRelative(BlockFace.UP).getType() != Material.WEB) {
			return false;
		}
		return true;
	}

	public static ArrayList<Block> getSurrounding(Block block, boolean diagonals) {
		final ArrayList<Block> blocks = new ArrayList<>();
		if (diagonals) {
			for (int x = -1; x <= 1; x++) {
				for (int y = -1; y <= 1; y++) {
					for (int z = -1; z <= 1; z++) {
						if ((x != 0) || (y != 0) || (z != 0)) {
							blocks.add(block.getRelative(x, y, z));
						}
					}
				}
			}
		} else {
			blocks.add(block.getRelative(BlockFace.UP));
			blocks.add(block.getRelative(BlockFace.DOWN));
			blocks.add(block.getRelative(BlockFace.NORTH));
			blocks.add(block.getRelative(BlockFace.SOUTH));
			blocks.add(block.getRelative(BlockFace.EAST));
			blocks.add(block.getRelative(BlockFace.WEST));
		}
		return blocks;
	}


	public static Location getEyeLocation(final Player player) {
		final Location eye = player.getLocation();
		eye.setY(eye.getY() + player.getEyeHeight());
		return eye;
	}

	public static boolean isBlock(Block block, Material[] materials) {
		final Material type = block.getType();
		Material[] arrayOfMaterial;
		final int j = (arrayOfMaterial = materials).length;
		for (int i = 0; i < j; i++) {
			final Material m = arrayOfMaterial[i];
			if (m == type) {
				return true;
			}
		}
		return false;
	}

	public static boolean isAir(final Player player) {
		final Block b = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
		return b.getType().equals(Material.AIR)
				&& b.getRelative(BlockFace.WEST).getType().equals(Material.AIR)
				&& b.getRelative(BlockFace.NORTH).getType().equals(Material.AIR)
				&& b.getRelative(BlockFace.EAST).getType().equals(Material.AIR)
				&& b.getRelative(BlockFace.SOUTH).getType().equals(Material.AIR);
	}


	public static int getPotionEffectLevel(Player player, PotionEffectType pet) {
		for (final PotionEffect pe : player.getActivePotionEffects()) {
			if (pe.getType().getName().equals(pet.getName())) {
				return pe.getAmplifier() + 1;
			}
		}
		return 0;
	}


	public static boolean isOnTheGround(Player player) {
		return isOnTheGround(player, 0.25);
	}
	public static boolean isOnTheGround(Player player, double yExpanded) {
		final Object box = ReflectionUtil.modifyBoundingBox(ReflectionUtil.getBoundingBox(player), 0, -yExpanded, 0,0,0,0);

		return ReflectionUtil.getCollidingBlocks(player, box).size() > 0;
	}

	public static boolean isNotSpider(Player player) {
		return isOnTheGround(player, 1.25);
	}
	public static boolean isInSlime(Player player) {
		final Object box = ReflectionUtil.getBoundingBox(player);

		final double minX = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "a"), box);
		final double minY = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "b"), box);
		final double minZ = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "c"), box);
		final double maxX = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "d"), box);
		final double maxY = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "e"), box);
		final double maxZ = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "f"), box);

		for(double x = minX ; x < maxX ; x++) {
			for(double y = minY ; y < maxY ; y++) {
				for(double z = minZ ; z < maxZ ; z++) {
					final Block block = new Location(player.getWorld(), x, y, z).getBlock();

					if(BlockUtil.isSlime(block)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isInLiquid(Player player) {
		final Object box = ReflectionUtil.getBoundingBox(player);

		final double minX = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "a"), box);
		final double minY = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "b"), box);
		final double minZ = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "c"), box);
		final double maxX = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "d"), box);
		final double maxY = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "e"), box);
		final double maxZ = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "f"), box);

		for(double x = minX ; x < maxX ; x++) {
			for(double y = minY ; y < maxY ; y++) {
				for(double z = minZ ; z < maxZ ; z++) {
					final Block block = new Location(player.getWorld(), x, y, z).getBlock();

					if(BlockUtil.isLiquid(block)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isInStairs(Player player) {
		final Object box = ReflectionUtil.modifyBoundingBox(ReflectionUtil.getBoundingBox(player), 0, -0.5,0,0,0,0);

		final double minX = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "a"), box);
		final double minY = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "b"), box);
		final double minZ = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "c"), box);
		final double maxX = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "d"), box);
		final double maxY = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "e"), box);
		final double maxZ = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "f"), box);

		for(double x = minX ; x < maxX ; x++) {
			for(double y = minY ; y < maxY ; y++) {
				for(double z = minZ ; z < maxZ ; z++) {
					final Block block = new Location(player.getWorld(), x, y, z).getBlock();

					if(BlockUtil.isStair(block)
							|| BlockUtil.isSlab(block)
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
		return BlockUtil.isClimbableBlock(player.getLocation().getBlock())
				|| BlockUtil.isClimbableBlock(player.getLocation().add(0, 1,0).getBlock());
	}



	public static boolean inUnderBlock(Player player) {
		final Object box = ReflectionUtil.modifyBoundingBox(ReflectionUtil.getBoundingBox(player), 0, 0,0,0,1,0);

		final double minX = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "a"), box);
		final double minY = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "b"), box);
		final double minZ = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "c"), box);
		final double maxX = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "d"), box);
		final double maxY = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "e"), box);
		final double maxZ = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "f"), box);

		for(double x = minX ; x < maxX ; x++) {
			for(double y = minY ; y < maxY ; y++) {
				for(double z = minZ ; z < maxZ ; z++) {
					final Block block = new Location(player.getWorld(), x, y, z).getBlock();

					if(block.getType().isSolid()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	public static boolean isOnAllowedPhase(Player player) {
		final Object box = ReflectionUtil.modifyBoundingBox(ReflectionUtil.getBoundingBox(player), 0, -0.5,0,0,0,0);

		final double minX = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "a"), box);
		final double minY = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "b"), box);
		final double minZ = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "c"), box);
		final double maxX = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "d"), box);
		final double maxY = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "e"), box);
		final double maxZ = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "f"), box);

		for(double x = minX ; x < maxX ; x++) {
			for(double y = minY ; y < maxY ; y++) {
				for(double z = minZ ; z < maxZ ; z++) {
					final Block block = new Location(player.getWorld(), x, y, z).getBlock();

					if(BlockUtil.allowedPhase(block)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isOnIce(Player player) {
		final Object box = ReflectionUtil.modifyBoundingBox(ReflectionUtil.getBoundingBox(player), 0, -0.5,0,0,0,0);

		final double minX = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "a"), box);
		final double minY = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "b"), box);
		final double minZ = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "c"), box);
		final double maxX = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "d"), box);
		final double maxY = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "e"), box);
		final double maxZ = (double) ReflectionUtil.getInvokedField(ReflectionUtil.getField(box.getClass(), "f"), box);

		for(double x = minX ; x < maxX ; x++) {
			for(double y = minY ; y < maxY ; y++) {
				for(double z = minZ ; z < maxZ ; z++) {
					final Block block = new Location(player.getWorld(), x, y, z).getBlock();

					if(block.getType().equals(Material.ICE)
							|| block.getType().equals(Material.PACKED_ICE)) {
						return true;
					}
				}
			}
		}
		return false;
	}


	public static boolean isInWater(Player player) {
		final Material m = player.getLocation().getBlock().getType();
		return m == Material.STATIONARY_WATER || m == Material.WATER;
	}


	public static boolean isPartiallyStuck(Player player) {
		if (player.getLocation().clone().getBlock() == null) {
			return false;
		}
		final Block block = player.getLocation().clone().getBlock();
		if (CheatUtil.isSlab(block) || CheatUtil.isStair(block)) {
			return false;
		}
		if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().isSolid()
				|| player.getLocation().getBlock().getRelative(BlockFace.UP).getType().isSolid()) {
			return true;
		}
		if (player.getLocation().clone().add(0.0D, 1.0D, 0.0D).getBlock().getRelative(BlockFace.DOWN).getType()
				.isSolid()
				|| player.getLocation().clone().add(0.0D, 1.0D, 0.0D).getBlock().getRelative(BlockFace.UP).getType()
				.isSolid()) {
			return true;
		}
		return block.getType().isSolid();
	}

	public static boolean isFullyStuck(Player player) {
		final Block block1 = player.getLocation().clone().getBlock();
		final Block block2 = player.getLocation().clone().add(0.0D, 1.0D, 0.0D).getBlock();
		if (block1.getType().isSolid() && block2.getType().isSolid()) {
			return true;
		}
		return block1.getRelative(BlockFace.DOWN).getType().isSolid()
				|| block1.getLocation().getBlock().getRelative(BlockFace.UP).getType().isSolid()
				&& block2.getRelative(BlockFace.DOWN).getType().isSolid()
				|| block2.getLocation().getBlock().getRelative(BlockFace.UP).getType().isSolid();
	}

	public static boolean isInGround(Player player) {
		if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR) {
			return true;
		}
		Location a = player.getLocation().clone();
		a.setY(a.getY() - 0.5);
		if (a.getBlock().getType() != Material.AIR) {
			return true;
		}
		a = player.getLocation().clone();
		a.setY(a.getY() + 0.5);
		return a.getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR
				|| CheatUtil.isBlock(player.getLocation().getBlock().getRelative(BlockFace.DOWN),
						new Material[]{Material.FENCE, Material.FENCE_GATE, Material.COBBLE_WALL, Material.LADDER});
	}
}