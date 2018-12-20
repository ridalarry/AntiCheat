package me.rida.anticheat.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

@SuppressWarnings("deprecation")
public final class CheatUtil {
    public static final String SPY_METADATA = "ac-spydata";
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private static final List<Material> INSTANT_BREAK = new ArrayList();
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static final List<Material> FOOD = new ArrayList();
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static final List<Material> INTERACTABLE = new ArrayList();
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static final Map<Material, Material> COMBO = new HashMap();
    @SuppressWarnings("unused")
	private static Set<UUID> teleported = new HashSet<>();

    static {
        INSTANT_BREAK.add(Material.RED_MUSHROOM);
        INSTANT_BREAK.add(Material.LEGACY_RED_ROSE);
        INSTANT_BREAK.add(Material.BROWN_MUSHROOM);
        INSTANT_BREAK.add(Material.LEGACY_YELLOW_FLOWER);
        INSTANT_BREAK.add(Material.POTTED_POPPY);
        INSTANT_BREAK.add(Material.POTTED_DANDELION);
        INSTANT_BREAK.add(Material.POTTED_BLUE_ORCHID);
        INSTANT_BREAK.add(Material.POTTED_ALLIUM);
        INSTANT_BREAK.add(Material.POTTED_AZURE_BLUET);
        INSTANT_BREAK.add(Material.POTTED_RED_TULIP);
        INSTANT_BREAK.add(Material.POTTED_ORANGE_TULIP);
        INSTANT_BREAK.add(Material.POTTED_WHITE_TULIP);
        INSTANT_BREAK.add(Material.POTTED_PINK_TULIP);
        INSTANT_BREAK.add(Material.POTTED_OXEYE_DAISY);
        INSTANT_BREAK.add(Material.FLOWER_POT);
        INSTANT_BREAK.add(Material.POPPY);
        INSTANT_BREAK.add(Material.SUNFLOWER);
        INSTANT_BREAK.add(Material.DANDELION);
        INSTANT_BREAK.add(Material.BLUE_ORCHID);
        INSTANT_BREAK.add(Material.ALLIUM);
        INSTANT_BREAK.add(Material.AZURE_BLUET);
        INSTANT_BREAK.add(Material.RED_TULIP);
        INSTANT_BREAK.add(Material.ORANGE_TULIP);
        INSTANT_BREAK.add(Material.WHITE_TULIP);
        INSTANT_BREAK.add(Material.PINK_TULIP);
        INSTANT_BREAK.add(Material.OXEYE_DAISY);
        INSTANT_BREAK.add(Material.REDSTONE);
        INSTANT_BREAK.add(Material.REDSTONE_TORCH);
        INSTANT_BREAK.add(Material.REDSTONE_WALL_TORCH);
        INSTANT_BREAK.add(Material.LEGACY_REDSTONE_TORCH_OFF);
        INSTANT_BREAK.add(Material.LEGACY_REDSTONE_TORCH_ON);
        INSTANT_BREAK.add(Material.REDSTONE_WIRE);
        INSTANT_BREAK.add(Material.LEGACY_LONG_GRASS);
        INSTANT_BREAK.add(Material.PAINTING);
        INSTANT_BREAK.add(Material.WHEAT);
        INSTANT_BREAK.add(Material.SUGAR_CANE);
        INSTANT_BREAK.add(Material.LEGACY_SUGAR_CANE_BLOCK);
        INSTANT_BREAK.add(Material.LEGACY_DIODE);
        INSTANT_BREAK.add(Material.LEGACY_DIODE_BLOCK_OFF);
        INSTANT_BREAK.add(Material.LEGACY_DIODE_BLOCK_ON);
        INSTANT_BREAK.add(Material.ACACIA_SAPLING);
        INSTANT_BREAK.add(Material.BIRCH_SAPLING);
        INSTANT_BREAK.add(Material.DARK_OAK_SAPLING);
        INSTANT_BREAK.add(Material.JUNGLE_SAPLING);
        INSTANT_BREAK.add(Material.SPRUCE_SAPLING);
		INSTANT_BREAK.add(Material.LEGACY_SAPLING);
		INSTANT_BREAK.add(Material.POTTED_ACACIA_SAPLING);
		INSTANT_BREAK.add(Material.POTTED_BIRCH_SAPLING);
		INSTANT_BREAK.add(Material.POTTED_DARK_OAK_SAPLING);
		INSTANT_BREAK.add(Material.POTTED_JUNGLE_SAPLING);
		INSTANT_BREAK.add(Material.POTTED_SPRUCE_SAPLING);
        INSTANT_BREAK.add(Material.TORCH);
        INSTANT_BREAK.add(Material.LEGACY_CROPS);
        INSTANT_BREAK.add(Material.SNOW);
        INSTANT_BREAK.add(Material.TNT);
        INSTANT_BREAK.add(Material.POTATO);
        INSTANT_BREAK.add(Material.CARROT);

        INTERACTABLE.add(Material.STONE_BUTTON);
        INTERACTABLE.add(Material.LEVER);
        INTERACTABLE.add(Material.CHEST);

        FOOD.add(Material.COOKED_BEEF);
        FOOD.add(Material.COOKED_CHICKEN);
        FOOD.add(Material.COOKED_COD);
        FOOD.add(Material.COOKED_SALMON);
        FOOD.add(Material.LEGACY_COOKED_FISH);
        FOOD.add(Material.COOKED_PORKCHOP);
        FOOD.add(Material.PORKCHOP);
        FOOD.add(Material.MUSHROOM_STEW);
        FOOD.add(Material.BEEF);
        FOOD.add(Material.CHICKEN);
        FOOD.add(Material.LEGACY_RAW_FISH);
        FOOD.add(Material.COD);
        FOOD.add(Material.SALMON);
        FOOD.add(Material.PUFFERFISH);
        FOOD.add(Material.APPLE);
        FOOD.add(Material.GOLDEN_APPLE);
        FOOD.add(Material.MELON);
        FOOD.add(Material.COOKIE);
        FOOD.add(Material.BREAD);
        FOOD.add(Material.SPIDER_EYE);
        FOOD.add(Material.ROTTEN_FLESH);
        FOOD.add(Material.LEGACY_POTATO_ITEM);
        FOOD.add(Material.POTATO);
        FOOD.add(Material.POTATOES);

        COMBO.put(Material.SHEARS, Material.LEGACY_WOOL);
        COMBO.put(Material.SHEARS, Material.WHITE_WOOL);
        COMBO.put(Material.SHEARS, Material.ORANGE_WOOL);
        COMBO.put(Material.SHEARS, Material.MAGENTA_WOOL);
        COMBO.put(Material.SHEARS, Material.LIGHT_BLUE_WOOL);
        COMBO.put(Material.SHEARS, Material.YELLOW_WOOL);
        COMBO.put(Material.SHEARS, Material.LIME_WOOL);
        COMBO.put(Material.SHEARS, Material.PINK_WOOL);
        COMBO.put(Material.SHEARS, Material.GRAY_WOOL);
        COMBO.put(Material.SHEARS, Material.LIGHT_GRAY_WOOL);
        COMBO.put(Material.SHEARS, Material.CYAN_WOOL);
        COMBO.put(Material.SHEARS, Material.PURPLE_WOOL);
        COMBO.put(Material.SHEARS, Material.BLUE_WOOL);
        COMBO.put(Material.SHEARS, Material.GREEN_WOOL);
        COMBO.put(Material.SHEARS, Material.BROWN_WOOL);
        COMBO.put(Material.SHEARS, Material.RED_WOOL);
        COMBO.put(Material.SHEARS, Material.BLACK_WOOL);
        COMBO.put(Material.IRON_SWORD, Material.COBWEB);
        COMBO.put(Material.DIAMOND_SWORD, Material.COBWEB);
        COMBO.put(Material.STONE_SWORD, Material.COBWEB);
        COMBO.put(Material.WOODEN_SWORD, Material.COBWEB);
    }

    public static double getXDelta(Location one, Location two) {
        return Math.abs(one.getX() - two.getX());
    }

    public static boolean isDoor(Block block) {
		return block.getType().equals(Material.IRON_DOOR) 
				|| block.getType().equals(Material.LEGACY_IRON_DOOR_BLOCK) 
				|| block.getType().equals(Material.LEGACY_WOOD_DOOR) 
				|| block.getType().equals(Material.OAK_DOOR)  
				|| block.getType().equals(Material.DARK_OAK_DOOR)  
				|| block.getType().equals(Material.SPRUCE_DOOR)  
				|| block.getType().equals(Material.BIRCH_DOOR)  
				|| block.getType().equals(Material.JUNGLE_DOOR)  
				|| block.getType().equals(Material.ACACIA_DOOR) 
				|| block.getType().equals(Material.LEGACY_ACACIA_DOOR_ITEM)  
				|| block.getType().equals(Material.LEGACY_DARK_OAK_DOOR_ITEM)  
				|| block.getType().equals(Material.LEGACY_SPRUCE_DOOR_ITEM)  
				|| block.getType().equals(Material.LEGACY_BIRCH_DOOR_ITEM)  
				|| block.getType().equals(Material.LEGACY_JUNGLE_DOOR_ITEM);
    }



    public static double getZDelta(Location one, Location two) {
        return Math.abs(one.getZ() - two.getZ());
    }

    public static double getDistance3D(Location one, Location two) {
        double toReturn = 0.0D;
        double xSqr = (two.getX() - one.getX()) * (two.getX() - one.getX());
        double ySqr = (two.getY() - one.getY()) * (two.getY() - one.getY());
        double zSqr = (two.getZ() - one.getZ()) * (two.getZ() - one.getZ());
        double sqrt = Math.sqrt(xSqr + ySqr + zSqr);
        toReturn = Math.abs(sqrt);
        return toReturn;
    }

    public static double getVerticalDistance(Location one, Location two) {
        double toReturn = 0.0D;
        double ySqr = (two.getY() - one.getY()) * (two.getY() - one.getY());
        double sqrt = Math.sqrt(ySqr);
        toReturn = Math.abs(sqrt);
        return toReturn;
    }

    public static double getHorizontalDistance(Location one, Location two) {
        double toReturn = 0.0D;
        double xSqr = (two.getX() - one.getX()) * (two.getX() - one.getX());
        double zSqr = (two.getZ() - one.getZ()) * (two.getZ() - one.getZ());
        double sqrt = Math.sqrt(xSqr + zSqr);
        toReturn = Math.abs(sqrt);
        return toReturn;
    }

    public static boolean cantStandAtBetter(Block block) {
        Block otherBlock = block.getRelative(BlockFace.DOWN);

        boolean center1 = otherBlock.getType() == Material.AIR;
        boolean north1 = otherBlock.getRelative(BlockFace.NORTH).getType() == Material.AIR;
        boolean east1 = otherBlock.getRelative(BlockFace.EAST).getType() == Material.AIR;
        boolean south1 = otherBlock.getRelative(BlockFace.SOUTH).getType() == Material.AIR;
        boolean west1 = otherBlock.getRelative(BlockFace.WEST).getType() == Material.AIR;
        boolean northeast1 = otherBlock.getRelative(BlockFace.NORTH_EAST).getType() == Material.AIR;
        boolean northwest1 = otherBlock.getRelative(BlockFace.NORTH_WEST).getType() == Material.AIR;
        boolean southeast1 = otherBlock.getRelative(BlockFace.SOUTH_EAST).getType() == Material.AIR;
        boolean southwest1 = otherBlock.getRelative(BlockFace.SOUTH_WEST).getType() == Material.AIR;
        boolean overAir1 = (otherBlock.getRelative(BlockFace.DOWN).getType() == Material.AIR)
                || (otherBlock.getRelative(BlockFace.DOWN).getType() == Material.WATER)
                || (otherBlock.getRelative(BlockFace.DOWN).getType() == Material.LAVA);

        return (center1) && (north1) && (east1) && (south1) && (west1) && (northeast1) && (southeast1) && (northwest1)
                && (southwest1) && (overAir1);
    }

    public static boolean cantStandAtSingle(Block block) {
        Block otherBlock = block.getRelative(BlockFace.DOWN);
        boolean center = otherBlock.getType() == Material.AIR;
        return center;
    }

    public static boolean cantStandAtWater(Block block) {
        Block otherBlock = block.getRelative(BlockFace.DOWN);

        boolean isHover = block.getType() == Material.AIR;
        boolean n = (otherBlock.getRelative(BlockFace.NORTH).getType() == Material.WATER)
                || (otherBlock.getRelative(BlockFace.NORTH).getType() == Material.LEGACY_STATIONARY_WATER);
        boolean s = (otherBlock.getRelative(BlockFace.SOUTH).getType() == Material.WATER)
                || (otherBlock.getRelative(BlockFace.SOUTH).getType() == Material.LEGACY_STATIONARY_WATER);
        boolean e = (otherBlock.getRelative(BlockFace.EAST).getType() == Material.WATER)
                || (otherBlock.getRelative(BlockFace.EAST).getType() == Material.LEGACY_STATIONARY_WATER);
        boolean w = (otherBlock.getRelative(BlockFace.WEST).getType() == Material.WATER)
                || (otherBlock.getRelative(BlockFace.WEST).getType() == Material.LEGACY_STATIONARY_WATER);
        boolean ne = (otherBlock.getRelative(BlockFace.NORTH_EAST).getType() == Material.WATER)
                || (otherBlock.getRelative(BlockFace.NORTH_EAST).getType() == Material.LEGACY_STATIONARY_WATER);
        boolean nw = (otherBlock.getRelative(BlockFace.NORTH_WEST).getType() == Material.WATER)
                || (otherBlock.getRelative(BlockFace.NORTH_WEST).getType() == Material.LEGACY_STATIONARY_WATER);
        boolean se = (otherBlock.getRelative(BlockFace.SOUTH_EAST).getType() == Material.WATER)
                || (otherBlock.getRelative(BlockFace.NORTH).getType() == Material.LEGACY_STATIONARY_WATER);
        boolean sw = (otherBlock.getRelative(BlockFace.SOUTH_WEST).getType() == Material.WATER)
                || (otherBlock.getRelative(BlockFace.SOUTH_WEST).getType() == Material.LEGACY_STATIONARY_WATER);

        return (n) && (s) && (e) && (w) && (ne) && (nw) && (se) && (sw) && (isHover);
    }

    public static boolean canStandWithin(Block block) {
        boolean isSand = block.getType() == Material.SAND;
        boolean isGravel = block.getType() == Material.GRAVEL;
        boolean solid = (block.getType().isSolid()) && (!block.getType().name().toLowerCase().contains("door"))
                && (!block.getType().name().toLowerCase().contains("fence"))
                && (!block.getType().name().toLowerCase().contains("bars"))
                && (!block.getType().name().toLowerCase().contains("sign"));

        return (!isSand) && (!isGravel) && (!solid);
    }

    public static Vector getRotation(Location one, Location two) {
        double dx = two.getX() - one.getX();
        double dy = two.getY() - one.getY();
        double dz = two.getZ() - one.getZ();
        double distanceXZ = Math.sqrt(dx * dx + dz * dz);
        float yaw = (float) (Math.atan2(dz, dx) * 180.0D / 3.141592653589793D) - 90.0F;
        float pitch = (float) -(Math.atan2(dy, distanceXZ) * 180.0D / 3.141592653589793D);
        return new Vector(yaw, pitch, 0.0F);
    }

    public static double clamp180(double theta) {
        theta %= 360.0D;
        if (theta >= 180.0D) {
            theta -= 360.0D;
        }
        if (theta < -180.0D) {
            theta += 360.0D;
        }
        return theta;
    }

    public static int getLevelForEnchantment(Player player, String enchantment) {
        try {
            Enchantment theEnchantment = Enchantment.getByName(enchantment);
            ItemStack[] arrayOfItemStack;
            int j = (arrayOfItemStack = player.getInventory().getArmorContents()).length;
            for (int i = 0; i < j; i++) {
                ItemStack item = arrayOfItemStack[i];
                if (item.containsEnchantment(theEnchantment)) {
                    return item.getEnchantmentLevel(theEnchantment);
                }
            }
        } catch (Exception e) {
            return -1;
        }
        return -1;
    }

    public static boolean cantStandAt(Block block) {
        return (!canStand(block)) && (cantStandClose(block)) && (cantStandFar(block));
    }

    public static boolean cantStandAtExp(Location location) {
        return cantStandAt(new Location(location.getWorld(), fixXAxis(location.getX()), location.getY() - 0.01D,
                location.getBlockZ()).getBlock());
    }

    public static boolean cantStandClose(Block block) {
        return (!canStand(block.getRelative(BlockFace.NORTH))) && (!canStand(block.getRelative(BlockFace.EAST)))
                && (!canStand(block.getRelative(BlockFace.SOUTH))) && (!canStand(block.getRelative(BlockFace.WEST)));
    }

    public static boolean cantStandFar(Block block) {
        return (!canStand(block.getRelative(BlockFace.NORTH_WEST)))
                && (!canStand(block.getRelative(BlockFace.NORTH_EAST)))
                && (!canStand(block.getRelative(BlockFace.SOUTH_WEST)))
                && (!canStand(block.getRelative(BlockFace.SOUTH_EAST)));
    }

    public static boolean canStand(Block block) {
        return (!block.isLiquid()) && (block.getType() != Material.AIR);
    }

    public static boolean isFullyInWater(Location player) {
        double touchedX = fixXAxis(player.getX());

        return (new Location(player.getWorld(), touchedX, player.getY(), player.getBlockZ()).getBlock().isLiquid())
                && (new Location(player.getWorld(), touchedX, Math.round(player.getY()), player.getBlockZ()).getBlock()
                .isLiquid());
    }

    public static double fixXAxis(double x) {
        double touchedX = x;
        double rem = touchedX - Math.round(touchedX) + 0.01D;
        if (rem < 0.3D) {
            touchedX = NumberConversions.floor(x) - 1;
        }
        return touchedX;
    }

    public static boolean isOnGround(final Location location, final int down) {
    	final double posX = location.getX();
    	final double posZ = location.getZ();
    	final double fracX = (MathUtil.getFraction(posX) > 0.0) ? Math.abs(MathUtil.getFraction(posX))
                : (1.0 - Math.abs(MathUtil.getFraction(posX)));
    	final double fracZ = (MathUtil.getFraction(posZ) > 0.0) ? Math.abs(MathUtil.getFraction(posZ))
                : (1.0 - Math.abs(MathUtil.getFraction(posZ)));
    	final int blockX = location.getBlockX();
    	final int blockY = location.getBlockY() - down;
    	final int blockZ = location.getBlockZ();
    	final World world = location.getWorld();
        if (BlockUtil.isSolid2(world.getBlockAt(blockX, blockY, blockZ))) {
            return true;
        }
        if (fracX < 0.3) {
            if (BlockUtil.isSolid2(world.getBlockAt(blockX - 1, blockY, blockZ))) {
                return true;
            }
            if (fracZ < 0.3) {
                if (BlockUtil.isSolid2(world.getBlockAt(blockX - 1, blockY, blockZ - 1))) {
                    return true;
                }
                if (BlockUtil.isSolid2(world.getBlockAt(blockX, blockY, blockZ - 1))) {
                    return true;
                }
                return BlockUtil.isSolid2(world.getBlockAt(blockX + 1, blockY, blockZ - 1));
            } else if (fracZ > 0.7) {
                if (BlockUtil.isSolid2(world.getBlockAt(blockX - 1, blockY, blockZ + 1))) {
                    return true;
                }
                if (BlockUtil.isSolid2(world.getBlockAt(blockX, blockY, blockZ + 1))) {
                    return true;
                }
                return BlockUtil.isSolid2(world.getBlockAt(blockX + 1, blockY, blockZ + 1));
            }
        } else if (fracX > 0.7) {
            if (BlockUtil.isSolid2(world.getBlockAt(blockX + 1, blockY, blockZ))) {
                return true;
            }
            if (fracZ < 0.3) {
                if (BlockUtil.isSolid2(world.getBlockAt(blockX - 1, blockY, blockZ - 1))) {
                    return true;
                }
                if (BlockUtil.isSolid2(world.getBlockAt(blockX, blockY, blockZ - 1))) {
                    return true;
                }
                return BlockUtil.isSolid2(world.getBlockAt(blockX + 1, blockY, blockZ - 1));
            } else if (fracZ > 0.7) {
                if (BlockUtil.isSolid2(world.getBlockAt(blockX - 1, blockY, blockZ + 1))) {
                    return true;
                }
                if (BlockUtil.isSolid2(world.getBlockAt(blockX, blockY, blockZ + 1))) {
                    return true;
                }
                return BlockUtil.isSolid2(world.getBlockAt(blockX + 1, blockY, blockZ + 1));
            }
        } else if (fracZ < 0.3) {
            return BlockUtil.isSolid2(world.getBlockAt(blockX, blockY, blockZ - 1));
        } else return fracZ > 0.7 && BlockUtil.isSolid2(world.getBlockAt(blockX, blockY, blockZ + 1));
        return false;
    }

    public static boolean isHoveringOverWater(Location player, int blocks) {
        for (int i = player.getBlockY(); i > player.getBlockY() - blocks; i--) {
            Block newloc = new Location(player.getWorld(), player.getBlockX(), i, player.getBlockZ()).getBlock();
            if (newloc.getType() != Material.AIR) {
                return newloc.isLiquid();
            }
        }
        return false;
    }

    public static boolean isHoveringOverWater(Location player) {
        return isHoveringOverWater(player, 25);
    }

    public static boolean isInstantBreak(Material m) {
        return INSTANT_BREAK.contains(m);
    }

    public static boolean isFood(Material m) {
        return FOOD.contains(m);
    }

    public static String getCardinalDirection(Player player) {
        double rotation = (player.getLocation().getYaw() - 90) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }
        if (0 <= rotation && rotation < 22.5) {
            return "N";
        } else if (22.5 <= rotation && rotation < 67.5) {
            return "NE";
        } else if (67.5 <= rotation && rotation < 112.5) {
            return "E";
        } else if (112.5 <= rotation && rotation < 157.5) {
            return "SE";
        } else if (157.5 <= rotation && rotation < 202.5) {
            return "S";
        } else if (202.5 <= rotation && rotation < 247.5) {
            return "SW";
        } else if (247.5 <= rotation && rotation < 292.5) {
            return "W";
        } else if (292.5 <= rotation && rotation < 337.5) {
            return "NW";
        } else if (337.5 <= rotation && rotation < 360.0) {
            return "N";
        } else {
            return null;
        }
    }

    @SuppressWarnings("incomplete-switch")
	public static boolean isSlab(Block block) {
        Material type = block.getType();
        switch (type) {
            case LEGACY_STEP:
            case LEGACY_WOOD_STEP:
			case BIRCH_SLAB:
			case ACACIA_SLAB:
			case BRICK_SLAB:
			case COBBLESTONE_SLAB:
			case DARK_OAK_SLAB:
			case DARK_PRISMARINE_SLAB:
			case JUNGLE_SLAB:
			case NETHER_BRICK_SLAB:
			case OAK_SLAB:
			case PETRIFIED_OAK_SLAB:
			case PRISMARINE_BRICK_SLAB:
			case PRISMARINE_SLAB:
			case PURPUR_SLAB:
			case QUARTZ_SLAB:
			case RED_SANDSTONE_SLAB:
			case SANDSTONE_SLAB:
			case SPRUCE_SLAB:
			case STONE_BRICK_SLAB:
			case STONE_SLAB:
			case LEGACY_DOUBLE_STEP: 
			case LEGACY_DOUBLE_STONE_SLAB2: 
			case LEGACY_PURPUR_DOUBLE_SLAB: 
			case LEGACY_WOOD_DOUBLE_STEP:
                return true;
        }
        return false;
    }

    @SuppressWarnings("incomplete-switch")
	public static boolean isStair(Block block) {
        Material type = block.getType();
        switch (type) {
            case COMMAND_BLOCK:
            case COBBLESTONE_STAIRS:
            case BRICK_STAIRS:
            case ACACIA_STAIRS:
            case BIRCH_STAIRS:
            case DARK_OAK_STAIRS:
            case JUNGLE_STAIRS:
            case NETHER_BRICK_STAIRS:
            case QUARTZ_STAIRS:
            case SANDSTONE_STAIRS:
            case LEGACY_SMOOTH_STAIRS:
            case SPRUCE_STAIRS:
            case OAK_STAIRS:
                return true;
        }
        return false;
    }

    public static boolean isInteractable(Material m) {
        return INTERACTABLE.contains(m);
    }

    public static boolean sprintFly(Player player) {
        return (player.isSprinting()) || (player.isFlying());
    }

    public static boolean isOnLilyPad(Player player) {
        Block block = player.getLocation().getBlock();
        Material lily = Material.LILY_PAD;

        return (block.getType() == lily) || (block.getRelative(BlockFace.NORTH).getType() == lily)
                || (block.getRelative(BlockFace.SOUTH).getType() == lily)
                || (block.getRelative(BlockFace.EAST).getType() == lily)
                || (block.getRelative(BlockFace.WEST).getType() == lily);
    }

    public static boolean isSubmersed(Player player) {
        return (player.getLocation().getBlock().isLiquid())
                && (player.getLocation().getBlock().getRelative(BlockFace.UP).isLiquid());
    }

    public static boolean isInWater(Player player) {
        return (player.getLocation().getBlock().isLiquid())
                || (player.getLocation().getBlock().getRelative(BlockFace.DOWN).isLiquid())
                || (player.getLocation().getBlock().getRelative(BlockFace.UP).isLiquid());
    }

    @SuppressWarnings("unlikely-arg-type")
	public static boolean isInWeb(Player player) {
        if (BlockUtil.getBlocksAroundCenter(player.getLocation(), 1).contains(Material.COBWEB)) {
            return true;
        }
        return (player.getLocation().getBlock().getType() == Material.COBWEB)
                || (player.getLocation().clone().add(0.0D, 1.0D, 0.0D).getBlock().getType() == Material.COBWEB)
                || (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.COBWEB)
                || (player.getLocation().getBlock().getRelative(BlockFace.UP).getType() == Material.COBWEB);
    }

    public static boolean isClimbableBlock(Block block) {
        return (block.getType() == Material.VINE) || (block.getType() == Material.LADDER)
                || (block.getType() == Material.WATER) || (block.getType() == Material.LEGACY_STATIONARY_WATER);
    }

    public static boolean isOnVine(Player player) {
        return player.getLocation().getBlock().getType() == Material.VINE;
    }

    public static boolean isInt(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (Exception localException) {
        }
        return false;
    }

    public static boolean isDouble(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (Exception localException) {
        }
        return false;
    }

    public static boolean blocksNear(final Player player) {
        return blocksNear(player.getLocation());
    }

    public static boolean blocksNear(Location loc) {
        boolean nearBlocks = false;
        for (final Block block : BlockUtil.getSurrounding(loc.getBlock(), true)) {
            if (block.getType() != Material.AIR) {
                nearBlocks = true;
                break;
            }
        }
        for (Block block : BlockUtil.getSurrounding(loc.getBlock(), false)) {
            if (block.getType() != Material.AIR) {
                nearBlocks = true;
                break;
            }
        }
        loc.setY(loc.getY() - 0.5);
        if (loc.getBlock().getType() != Material.AIR) {
            nearBlocks = true;
        }
        if (isBlock(loc.getBlock().getRelative(BlockFace.DOWN),
                new Material[]{Material.ACACIA_FENCE, Material.BIRCH_FENCE, Material.DARK_OAK_FENCE, Material.JUNGLE_FENCE, 
            			Material.NETHER_BRICK_FENCE, Material.SPRUCE_FENCE, 

            			Material.ACACIA_FENCE_GATE, Material.BIRCH_FENCE_GATE, Material.DARK_OAK_FENCE_GATE, Material.JUNGLE_FENCE_GATE, 
            			Material.SPRUCE_FENCE_GATE, Material.COBBLESTONE_WALL, Material.LADDER})) {
            nearBlocks = true;
        }
        return nearBlocks;
    }

    public static boolean blocksNearB(final Location loc) {
        boolean nearBlocks = false;
        for (Block block : BlockUtil.getSurroundingB(loc.getBlock())) {
            if (block.getType() != Material.AIR) {
                nearBlocks = true;
                break;
            }
        }
        loc.setY(loc.getY() - 0.5);
        if (loc.getBlock().getType() != Material.AIR) {
            nearBlocks = true;
        }
        if (isBlock(loc.getBlock().getRelative(BlockFace.DOWN),
                new Material[]{Material.ACACIA_FENCE, Material.BIRCH_FENCE, Material.DARK_OAK_FENCE, Material.JUNGLE_FENCE, 
            			Material.NETHER_BRICK_FENCE, Material.SPRUCE_FENCE, 

            			Material.ACACIA_FENCE_GATE, Material.BIRCH_FENCE_GATE, Material.DARK_OAK_FENCE_GATE, Material.JUNGLE_FENCE_GATE, 
            			Material.SPRUCE_FENCE_GATE, Material.COBBLESTONE_WALL, Material.LADDER})) {
            nearBlocks = true;
        }
        return nearBlocks;
    }

    public static boolean slabsNear(Location loc) {
        boolean nearBlocks = false;
        for (Block bl : BlockUtil.getSurrounding(loc.getBlock(), true)) {
            if ((
					bl.getType().equals(Material.BIRCH_SLAB)
					|| bl.getType().equals(Material.ACACIA_SLAB)
					|| bl.getType().equals(Material.BRICK_SLAB)
					|| bl.getType().equals(Material.COBBLESTONE_SLAB)
					|| bl.getType().equals(Material.DARK_OAK_SLAB)
					|| bl.getType().equals(Material.DARK_PRISMARINE_SLAB)
					|| bl.getType().equals(Material.JUNGLE_SLAB)
					|| bl.getType().equals(Material.NETHER_BRICK_SLAB)
					|| bl.getType().equals(Material.OAK_SLAB)
					|| bl.getType().equals(Material.PETRIFIED_OAK_SLAB)
					|| bl.getType().equals(Material.PRISMARINE_BRICK_SLAB)
					|| bl.getType().equals(Material.PRISMARINE_SLAB)
					|| bl.getType().equals(Material.PURPUR_SLAB)
					|| bl.getType().equals(Material.QUARTZ_SLAB)
					|| bl.getType().equals(Material.RED_SANDSTONE_SLAB)
					|| bl.getType().equals(Material.SANDSTONE_SLAB)
					|| bl.getType().equals(Material.SPRUCE_SLAB)
					|| bl.getType().equals(Material.STONE_BRICK_SLAB)
					|| bl.getType().equals(Material.STONE_SLAB)) 
            		|| (bl.getType().equals(Material.LEGACY_DOUBLE_STEP))
                    || (bl.getType().equals(Material.LEGACY_WOOD_DOUBLE_STEP)) 
                    || (bl.getType().equals(Material.LEGACY_WOOD_STEP))) {
                nearBlocks = true;
                break;
            }
        }
        for (Block bl : BlockUtil.getSurrounding(loc.getBlock(), false)) {
            if (bl.getType().equals(Material.LEGACY_STEP)
					|| bl.getType().equals(Material.BIRCH_SLAB)
					|| bl.getType().equals(Material.ACACIA_SLAB)
					|| bl.getType().equals(Material.BRICK_SLAB)
					|| bl.getType().equals(Material.COBBLESTONE_SLAB)
					|| bl.getType().equals(Material.DARK_OAK_SLAB)
					|| bl.getType().equals(Material.DARK_PRISMARINE_SLAB)
					|| bl.getType().equals(Material.JUNGLE_SLAB)
					|| bl.getType().equals(Material.NETHER_BRICK_SLAB)
					|| bl.getType().equals(Material.OAK_SLAB)
					|| bl.getType().equals(Material.PETRIFIED_OAK_SLAB)
					|| bl.getType().equals(Material.PRISMARINE_BRICK_SLAB)
					|| bl.getType().equals(Material.PRISMARINE_SLAB)
					|| bl.getType().equals(Material.PURPUR_SLAB)
					|| bl.getType().equals(Material.QUARTZ_SLAB)
					|| bl.getType().equals(Material.RED_SANDSTONE_SLAB)
					|| bl.getType().equals(Material.SANDSTONE_SLAB)
					|| bl.getType().equals(Material.SPRUCE_SLAB)
					|| bl.getType().equals(Material.STONE_BRICK_SLAB)
					|| bl.getType().equals(Material.STONE_SLAB) 
					|| bl.getType().equals(Material.LEGACY_DOUBLE_STEP)
                    || bl.getType().equals(Material.LEGACY_WOOD_DOUBLE_STEP)) {
                nearBlocks = true;
                break;
            }
        }
        if (isBlock(loc.getBlock().getRelative(BlockFace.DOWN), new Material[]{Material.BIRCH_SLAB,
				Material.ACACIA_SLAB,
				Material.BRICK_SLAB,
				Material.COBBLESTONE_SLAB,
				Material.DARK_OAK_SLAB,
				Material.DARK_PRISMARINE_SLAB,
				Material.JUNGLE_SLAB,
				Material.NETHER_BRICK_SLAB,
				Material.OAK_SLAB,
				Material.PETRIFIED_OAK_SLAB,
				Material.PRISMARINE_BRICK_SLAB,
				Material.PRISMARINE_SLAB,
				Material.PURPUR_SLAB,
				Material.QUARTZ_SLAB,
				Material.RED_SANDSTONE_SLAB,
				Material.SANDSTONE_SLAB,
				Material.SPRUCE_SLAB,
				Material.STONE_BRICK_SLAB,
				Material.STONE_SLAB,
				Material.LEGACY_DOUBLE_STEP, Material.LEGACY_DOUBLE_STONE_SLAB2, Material.LEGACY_PURPUR_DOUBLE_SLAB, Material.LEGACY_WOOD_DOUBLE_STEP})) {
            nearBlocks = true;
        }
        return nearBlocks;
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

    public static String[] getCommands(String command) {
        return command.replaceAll("COMMAND\\[", "").replaceAll("]", "").split(";");
    }

    public static String removeWhitespace(String string) {
        return string.replaceAll(" ", "");
    }

    public static boolean hasArmorEnchantment(Player player, Enchantment e) {
        ItemStack[] arrayOfItemStack;
        int j = (arrayOfItemStack = player.getInventory().getArmorContents()).length;
        for (int i = 0; i < j; i++) {
            ItemStack is = arrayOfItemStack[i];
            if ((is != null) && (is.containsEnchantment(e))) {
                return true;
            }
        }
        return false;
    }

    public static String listToCommaString(List<String> list) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            b.append(list.get(i));
            if (i < list.size() - 1) {
                b.append(",");
            }
        }
        return b.toString();
    }

    public static long lifeToSeconds(String string) {
        if ((string.equals("0")) || (string.equals(""))) {
            return 0L;
        }
        String[] lifeMatch = {"d", "h", "m", "s"};
        int[] lifeInterval = {86400, 3600, 60, 1};
        long seconds = 0L;
        for (int i = 0; i < lifeMatch.length; i++) {
            Matcher matcher = Pattern.compile("([0-9]*)" + lifeMatch[i]).matcher(string);
            while (matcher.find()) {
                seconds += Integer.parseInt(matcher.group(1)) * lifeInterval[i];
            }
        }
        return seconds;
    }

    public static double[] cursor(Player player, LivingEntity entity) {
        Location entityLoc = entity.getLocation().add(0.0D, entity.getEyeHeight(), 0.0D);
        Location playerLoc = player.getLocation().add(0.0D, player.getEyeHeight(), 0.0D);

        Vector playerRotation = new Vector(playerLoc.getYaw(), playerLoc.getPitch(), 0.0F);
        Vector expectedRotation = getRotation(playerLoc, entityLoc);

        double deltaYaw = clamp180(playerRotation.getX() - expectedRotation.getX());
        double deltaPitch = clamp180(playerRotation.getY() - expectedRotation.getY());

        double horizontalDistance = getHorizontalDistance(playerLoc, entityLoc);
        double distance = getDistance3D(playerLoc, entityLoc);

        double offsetX = deltaYaw * horizontalDistance * distance;
        double offsetY = deltaPitch * Math.abs(Math.sqrt(entityLoc.getY() - playerLoc.getY())) * distance;

        return new double[]{Math.abs(offsetX), Math.abs(offsetY)};
    }

    public static double getAimbotoffset(Location playerLocLoc, double playerEyeHeight, LivingEntity entity) {
        Location entityLoc = entity.getLocation().add(0.0D, entity.getEyeHeight(), 0.0D);
        Location playerLoc = playerLocLoc.add(0.0D, playerEyeHeight, 0.0D);

        Vector playerRotation = new Vector(playerLoc.getYaw(), playerLoc.getPitch(), 0.0F);
        Vector expectedRotation = getRotation(playerLoc, entityLoc);

        double deltaYaw = clamp180(playerRotation.getX() - expectedRotation.getX());

        double horizontalDistance = getHorizontalDistance(playerLoc, entityLoc);
        double distance = getDistance3D(playerLoc, entityLoc);

        double offsetX = deltaYaw * horizontalDistance * distance;

        return offsetX;
    }

    public static double getAimbotoffset2(Location playerLocLoc, double playerEyeHeight, LivingEntity entity) {
        Location entityLoc = entity.getLocation().add(0.0D, entity.getEyeHeight(), 0.0D);
        Location playerLoc = playerLocLoc.add(0.0D, playerEyeHeight, 0.0D);

        Vector playerRotation = new Vector(playerLoc.getYaw(), playerLoc.getPitch(), 0.0F);
        Vector expectedRotation = getRotation(playerLoc, entityLoc);

        double deltaPitch = clamp180(playerRotation.getY() - expectedRotation.getY());

        double distance = getDistance3D(playerLoc, entityLoc);

        double offsetY = deltaPitch * Math.abs(Math.sqrt(entityLoc.getY() - playerLoc.getY())) * distance;

        return offsetY;
    }

    public static double[] getOffsetsOffCursor(Player player, LivingEntity entity) {
        Location entityLoc = entity.getLocation().add(0.0D, entity.getEyeHeight(), 0.0D);
        Location playerLoc = player.getLocation().add(0.0D, player.getEyeHeight(), 0.0D);

        Vector playerRotation = new Vector(playerLoc.getYaw(), playerLoc.getPitch(), 0.0F);
        Vector expectedRotation = getRotation(playerLoc, entityLoc);

        double deltaYaw = clamp180(playerRotation.getX() - expectedRotation.getX());
        double deltaPitch = clamp180(playerRotation.getY() - expectedRotation.getY());

        double horizontalDistance = getHorizontalDistance(playerLoc, entityLoc);
        double distance = getDistance3D(playerLoc, entityLoc);

        double offsetX = deltaYaw * horizontalDistance * distance;
        double offsetY = deltaPitch * Math.abs(Math.sqrt(entityLoc.getY() - playerLoc.getY())) * distance;

        return new double[]{Math.abs(offsetX), Math.abs(offsetY)};
    }

    public static double getOffsetOffCursor(Player player, LivingEntity entity) {
        double offset = 0.0D;
        double[] offsets = getOffsetsOffCursor(player, entity);

        offset += offsets[0];
        offset += offsets[1];

        return offset;
    }
}
