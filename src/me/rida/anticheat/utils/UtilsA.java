package me.rida.anticheat.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.data.DataPlayer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.text.DecimalFormat;
public class UtilsA {

	private static final String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
	public static final Class<?> EntityPlayer = UtilsA.getNMSClass("EntityPlayer");
	public static final Class<?> Entity = UtilsA.getNMSClass("Entity");
	private static final Class<?> CraftPlayer = UtilsA.getCBClass("entity.CraftPlayer");
	private static final Class<?> CraftWorld = UtilsA.getCBClass("CraftWorld");
	private static final Class<?> World = UtilsA.getNMSClass("World");
	private static final Method getCubes = UtilsA.getMethod(World, "a", UtilsA.getNMSClass("AxisAlignedBB"));

	public static Object getEntityPlayer(Player player) {
		return UtilsA.getMethodValue(UtilsA.getMethod(CraftPlayer, "getHandle"), player);
	}

	public static Object getBoundingBox(Player player) {
		return UtilsA.isBukkitVerison("1_7") ? UtilsA.getFieldValue(UtilsA.getFieldByName(Entity, "boundingBox"), getEntityPlayer(player)) : UtilsA.getMethodValue(UtilsA.getMethod(EntityPlayer, "getBoundingBox"), getEntityPlayer(player));
	}

	public static boolean isBukkitVerison(String version) {
		String bukkit = Bukkit.getServer().getClass().getPackage().getName().substring(23);
		
		return bukkit.contains(version);
	}

	public static Class<?> getCBClass(String string) {
		return getClass("org.bukkit.craftbukkit." + version + "." + string);
	}

	public static Class<?> getClass(String string) {
		try {
			return Class.forName(string);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}


	public static Field getFieldByName(Class<?> clazz, String fieldName) {
		try {
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			return field;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean inBlock(Player player, Object axisAlignedBB) {
		Object world = UtilsA.getMethodValue(UtilsA.getMethod(CraftWorld, "getHandle"), player.getWorld());
		return ((Collection<?>)UtilsA.getMethodValue(getCubes, world, axisAlignedBB)).size() > 0;
	}

	public static Collection<?> getCollidingBlocks(Player player, Object axisAlignedBB) {
		Object world = UtilsA.getMethodValue(UtilsA.getMethod(CraftWorld, "getHandle"), player.getWorld());
		return ((Collection<?>)UtilsA.getMethodValue(getCubes, world, axisAlignedBB));
	}


	public static Method getMethod(Class<?> clazz, String methodName, Class<?>... args) {
		try {
			Method method = clazz.getMethod(methodName, args);
			method.setAccessible(true);
			return method;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Object getMethodValue(Method method, Object object, Object... args) {
		try {
			return method.invoke(object, args);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Object getFieldValue(Field field, Object object) {
		try {
			field.setAccessible(true);
			return field.get(object);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Class<?> getNMSClass(String string) {
		return getClass("net.minecraft.server." + version + "." + string);
	}
	
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
        Location loc = p.getLocation().clone();
        double y = loc.getBlockY();
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
		double diff = .3;

		return !isGround(loc.clone().add(0, -.1, 0).getBlock().getType())
				|| !isGround(loc.clone().add(diff, -.1, 0).getBlock().getType())
				|| !isGround(loc.clone().add(-diff, -.1, 0).getBlock().getType())
				|| !isGround(loc.clone().add(0, -.1, diff).getBlock().getType())
				|| !isGround(loc.clone().add(0, -.1, -diff).getBlock().getType())
				|| !isGround(loc.clone().add(diff, -.1, diff).getBlock().getType())
				|| !isGround(loc.clone().add(diff, -.1, -diff).getBlock().getType())
				|| !isGround(loc.clone().add(-diff, -.1, diff).getBlock().getType())
				|| !isGround(loc.clone().add(-diff, -.1, -diff).getBlock().getType())
				|| (UtilsA.getBlockHeight(loc.clone().subtract(0.0D, 0.5D, 0.0D).getBlock()) != 0.0D &&
				(!isGround(loc.clone().add(diff, UtilsA.getBlockHeight(loc.getBlock()) - 0.1, 0).getBlock().getType())
						|| !isGround(loc.clone().add(-diff, UtilsA.getBlockHeight(loc.getBlock()) - 0.1, 0).getBlock().getType())
						|| !isGround(loc.clone().add(0, UtilsA.getBlockHeight(loc.getBlock()) - 0.1, diff).getBlock().getType())
						|| !isGround(loc.clone().add(0, UtilsA.getBlockHeight(loc.getBlock()) - 0.1, -diff).getBlock().getType())
						|| !isGround(loc.clone().add(diff, UtilsA.getBlockHeight(loc.getBlock()) - 0.1, diff).getBlock().getType())
						|| !isGround(loc.clone().add(diff, UtilsA.getBlockHeight(loc.getBlock()) - 0.1, -diff).getBlock().getType())
						|| !isGround(loc.clone().add(-diff, UtilsA.getBlockHeight(loc.getBlock()) - 0.1, diff).getBlock().getType())
						|| !isGround(loc.clone().add(-diff, UtilsA.getBlockHeight(loc.getBlock()) - 0.1, -diff).getBlock().getType())));
	}


	public static boolean isOnGround(Player player) {
			Object box = UtilsA.getBoundingBox(player);
			Object outcome = UtilsA.getMethodValue(UtilsA.getMethod(box.getClass(), "grow", double.class, double.class, double.class), box, 0D, 0.1D, 0D);
			return UtilsA.inBlock(player, outcome);
	}
	
	public static boolean hasPistonNear(Player player) {
		Object box = UtilsA.getMethodValue(UtilsA.getMethod(UtilsA.getBoundingBox(player).getClass(), "grow", double.class, double.class, double.class), UtilsA.getBoundingBox(player), 2D, 3D, 2D);

		Collection<?> collidingBlocks = UtilsA.getCollidingBlocks(player, box);

		for(Object object : collidingBlocks) {
			double x = (double) UtilsA.getFieldValue(UtilsA.getFieldByName(object.getClass(), "a"), object);
			double y = (double) UtilsA.getFieldValue(UtilsA.getFieldByName(object.getClass(), "b"), object);
			double z = (double) UtilsA.getFieldValue(UtilsA.getFieldByName(object.getClass(), "c"), object);

			Block block = new Location(player.getWorld(), x, y, z).getBlock();
			if(UtilsA.isPiston(block)) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasIceNear(Player player) {
		Object box = UtilsA.getMethodValue(UtilsA.getMethod(UtilsA.getBoundingBox(player).getClass(), "grow", double.class, double.class, double.class), UtilsA.getBoundingBox(player), 0D, 1.5D, 0D);

		Collection<?> collidingBlocks = UtilsA.getCollidingBlocks(player, box);

		for(Object object : collidingBlocks) {
			double x = (double) UtilsA.getFieldValue(UtilsA.getFieldByName(object.getClass(), "a"), object);
			double y = (double) UtilsA.getFieldValue(UtilsA.getFieldByName(object.getClass(), "b"), object);
			double z = (double) UtilsA.getFieldValue(UtilsA.getFieldByName(object.getClass(), "c"), object);

			Block block = new Location(player.getWorld(), x, y, z).getBlock();

			if(UtilsA.isIce(block)) {
				return true;
			}
		}
		return false;
	}

	public static boolean wasOnSlime(Player player) {
		DataPlayer user = AntiCheat.getInstance().getDataManager().getData(player);

		if(user != null
				&&user.getSetbackLocation() != null) {
			Location location = user.getSetbackLocation().clone().subtract(0.0D, 1.0D, 0.0D);

			if(location.getBlock().getTypeId() == 165){
				return true;
			}
		}
		return false;
	}

	public static boolean isOnGround3(Player player) {
		Object box = UtilsA.getBoundingBox(player);
		Object outcome = UtilsA.getMethodValue(UtilsA.getMethod(box.getClass(), "grow", double.class, double.class, double.class), box, 0D, 0.3D, 0D);
		return UtilsA.inBlock(player, outcome);
	}

	public static boolean isInWater(Location loc) {
		double diff = .3;
		return UtilsA.isLiquid(loc.clone().add(0, 0D, 0).getBlock())
				|| UtilsA.isLiquid(loc.clone().add(diff, 0D, 0).getBlock())
				|| UtilsA.isLiquid(loc.clone().add(-diff, 0D, 0).getBlock())
				|| UtilsA.isLiquid(loc.clone().add(0, 0D, diff).getBlock())
				|| UtilsA.isLiquid(loc.clone().add(0, 0D, -diff).getBlock())
				|| UtilsA.isLiquid(loc.clone().add(diff, 0D, diff).getBlock())
				|| UtilsA.isLiquid(loc.clone().add(diff, 0D, -diff).getBlock())
				|| UtilsA.isLiquid(loc.clone().add(-diff, 0D, diff).getBlock())
				|| UtilsA.isLiquid(loc.clone().add(-diff, 0D, -diff).getBlock())
				|| (UtilsA.getBlockHeight(loc.clone().subtract(0.0D, 0.5D, 0.0D).getBlock()) != 0.0D &&
				(UtilsA.isLiquid(loc.clone().add(diff,  0D, 0).getBlock())
						|| UtilsA.isLiquid(loc.clone().add(-diff,  0D, 0).getBlock())
						|| UtilsA.isLiquid(loc.clone().add(0,  0D, diff).getBlock())
						|| UtilsA.isLiquid(loc.clone().add(0,  0D, -diff).getBlock())
						|| UtilsA.isLiquid(loc.clone().add(diff,  0D, diff).getBlock())
						|| UtilsA.isLiquid(loc.clone().add(diff,  0D, -diff).getBlock())
						|| UtilsA.isLiquid(loc.clone().add(-diff,  0D, diff).getBlock())
						|| UtilsA.isLiquid(loc.clone().add(-diff,  0D, -diff).getBlock())));
	}

	public static boolean isOnSlab(Location loc) {
		double diff = .3;
		return UtilsA.isSlab(loc.clone().add(0, 0D, 0).getBlock())
				|| UtilsA.isSlab(loc.clone().add(diff, 0D, 0).getBlock())
				|| UtilsA.isSlab(loc.clone().add(-diff, 0D, 0).getBlock())
				|| UtilsA.isSlab(loc.clone().add(0, 0D, diff).getBlock())
				|| UtilsA.isSlab(loc.clone().add(0, 0D, -diff).getBlock())
				|| UtilsA.isSlab(loc.clone().add(diff, 0D, diff).getBlock())
				|| UtilsA.isSlab(loc.clone().add(diff, 0D, -diff).getBlock())
				|| UtilsA.isSlab(loc.clone().add(-diff, 0D, diff).getBlock())
				|| UtilsA.isSlab(loc.clone().add(-diff, 0D, -diff).getBlock());
	}

	public static boolean isOnStair(Location loc) {
		double diff = 0.3;
		return 	UtilsA.isStair(loc.clone().add(0, 0D, 0).getBlock())
				|| UtilsA.isStair(loc.clone().add(diff, 0D, 0).getBlock())
				|| UtilsA.isStair(loc.clone().add(-diff, 0D, 0).getBlock())
				|| UtilsA.isStair(loc.clone().add(0, 0D, diff).getBlock())
				|| UtilsA.isStair(loc.clone().add(0, 0D, -diff).getBlock())
				|| UtilsA.isStair(loc.clone().add(diff, 0D, diff).getBlock())
				|| UtilsA.isStair(loc.clone().add(diff, 0D, -diff).getBlock())
				|| UtilsA.isStair(loc.clone().add(-diff, 0D, diff).getBlock())
				|| UtilsA.isStair(loc.clone().add(-diff, 0D, -diff).getBlock());
	}

	public static boolean hasSlabsNear(Location location) {
		for(Block block : UtilsA.getSurroundingXZ(location.getBlock(), true)) {
			if(UtilsA.isSlab(block)) {
				return true;
			}
		}
		return false;
	}


	public static boolean isOnClimbable(Player player, int blocks) {
		if (blocks == 0) {
			for (Block block : getSurrounding(player.getLocation().getBlock(), false)) {
				if (block.getType() == Material.LADDER || block.getType() == Material.VINE) {
					return true;
				}
			}
		} else {
			for (Block block : getSurrounding(player.getLocation().clone().add(0.0D, 1.0D, 0.0D).getBlock(),
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
		ArrayList<Block> blocks = new ArrayList<Block>();
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
		Material type = block.getType();
		Material[] arrayOfMaterial;
		int j = (arrayOfMaterial = materials).length;
		for (int i = 0; i < j; i++) {
			Material m = arrayOfMaterial[i];
			if (m == type) {
				return true;
			}
		}
		return false;
	}

	public static boolean isAir(final Player player) {
		final Block b = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
		return b.getType().equals((Object) Material.AIR)
				&& b.getRelative(BlockFace.WEST).getType().equals((Object) Material.AIR)
				&& b.getRelative(BlockFace.NORTH).getType().equals((Object) Material.AIR)
				&& b.getRelative(BlockFace.EAST).getType().equals((Object) Material.AIR)
				&& b.getRelative(BlockFace.SOUTH).getType().equals((Object) Material.AIR);
	}


	public static int getPotionEffectLevel(Player player, PotionEffectType pet) {
		for (PotionEffect pe : player.getActivePotionEffects()) {
			if (pe.getType().getName().equals(pet.getName())) {
				return pe.getAmplifier() + 1;
			}
		}
		return 0;
	}
	public static boolean isLiquid(Block block) {
		if((block.getType() == Material.WATER || block.getType() == Material.STATIONARY_WATER
				|| block.getType() == Material.LAVA || block.getType() == Material.STATIONARY_LAVA)) {
			return true;
		}
		return false;
	}
	

	public static boolean isIce(Block block) {
		return block.getType().equals(Material.ICE)
				|| block.getType().equals(Material.PACKED_ICE)
				|| block.getType().equals(Material.getMaterial("FROSTED_ICE"));
	}
	public static double getBlockHeight(Block block) {	
		if(block.getTypeId() == 44) {
			return 0.5;
		}
		if(block.getTypeId() == 53) {
			return 0.5;
		}
		if(block.getTypeId()  == 85) {
			return 0.2;
		}
		if(block.getTypeId() == 54 || block.getTypeId() == 130) {
			return 0.125;
		}
		return 0;
	}

	public static boolean isPiston(Block block) {
		return block.getType().equals(Material.PISTON_MOVING_PIECE) || block.getType().equals(Material.PISTON_EXTENSION)
				|| block.getType().equals(Material.PISTON_BASE) || block.getType().equals(Material.PISTON_STICKY_BASE);
	}
	
	
	public static boolean isStair(Block block) {
	    if(block.getType().equals(Material.ACACIA_STAIRS) || block.getType().equals(Material.BIRCH_WOOD_STAIRS)
	    		|| block.getType().equals(Material.BRICK_STAIRS) || block.getType().equals(Material.COBBLESTONE_STAIRS)
	    		|| block.getType().equals(Material.DARK_OAK_STAIRS) || block.getType().equals(Material.NETHER_BRICK_STAIRS)
	    		|| block.getType().equals(Material.JUNGLE_WOOD_STAIRS) || block.getType().equals(Material.QUARTZ_STAIRS)
	    		|| block.getType().equals(Material.SMOOTH_STAIRS) || block.getType().equals(Material.WOOD_STAIRS)
	    		|| block.getType().equals(Material.SANDSTONE_STAIRS) || block.getType().equals(Material.SPRUCE_WOOD_STAIRS)
				|| block.getTypeId() == 203 || block.getTypeId() == 180) {
	    	return true;
	    }
	    return false;
	}
	
	public static boolean isSlab(Block block) {
		return block.getTypeId() == 44 || block.getTypeId() ==126 || block.getTypeId() == 205
				|| block.getTypeId() == 182;
	}
	

	public static ArrayList<Block> getSurroundingXZ(Block block, boolean diagonals) {
		ArrayList<Block> blocks = new ArrayList<Block>();
		if (diagonals) {
			blocks.add(block.getRelative(BlockFace.NORTH));
			blocks.add(block.getRelative(BlockFace.NORTH_EAST));
			blocks.add(block.getRelative(BlockFace.NORTH_WEST));
			blocks.add(block.getRelative(BlockFace.SOUTH));
			blocks.add(block.getRelative(BlockFace.SOUTH_EAST));
			blocks.add(block.getRelative(BlockFace.SOUTH_WEST));
			blocks.add(block.getRelative(BlockFace.EAST));
			blocks.add(block.getRelative(BlockFace.WEST));
		} else {
			blocks.add(block.getRelative(BlockFace.NORTH));
			blocks.add(block.getRelative(BlockFace.SOUTH));
			blocks.add(block.getRelative(BlockFace.EAST));
			blocks.add(block.getRelative(BlockFace.WEST));
		}

		return blocks;
	}
        public static List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<Block>();
        for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for(int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }
    public static boolean isNearIce(Player p) {
    	boolean out = false;
		for (Block b : getNearbyBlocks(p.getLocation(), 1)) {
			if (isIce(b)) {
				out = true;
			}
		}
    	return out;
	}
	public static boolean isNearStiar(Player p) {
		boolean out = false;
		for (Block b : getNearbyBlocks(p.getLocation(), 1)) {
			if (isStair(b)) {
				out = true;
			}
		}
		return out;
	}
	public static boolean isNearLiquid(Player p) {
		boolean out = false;
		for (Block b : getNearbyBlocks(p.getLocation(), 1)) {
			if (isLiquid(b)) {
				out = true;
			}
		}
		return out;
	}
    public static boolean isNearPistion(Player p) {
        boolean out = false;
        for (Block b : getNearbyBlocks(p.getLocation(), 1)) {
           if (b.getType() == Material.PISTON_BASE || b.getType() == Material.PISTON_MOVING_PIECE || b.getType() == Material.PISTON_STICKY_BASE || b.getType() == Material.PISTON_EXTENSION) {
               out = true;
           }
        }
        return out;
    }

    public static boolean elapsed(long from, long required) {
		return System.currentTimeMillis() - from > required;
	}
	

	public static double getHorizontalDistance(Location to, Location from) {
		double x = Math.abs(Math.abs(to.getX()) - Math.abs(from.getX()));
		double z = Math.abs(Math.abs(to.getZ()) - Math.abs(from.getZ()));

		return Math.sqrt(x * x + z * z);
	}

	public static double getVerticalDistance(Location to, Location from) {
		double y = Math.abs(Math.abs(to.getY()) - Math.abs(from.getY()));
		
		return Math.sqrt(y * y);
	}
	
}