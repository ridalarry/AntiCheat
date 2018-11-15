package me.rida.anticheat.utils;

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

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CheatUtil {
    public static final String SPY_METADATA = "ac-spydata";
    private static final List<Material> INSTANT_BREAK = new ArrayList();
    private static final List<Material> FOOD = new ArrayList();
    private static final List<Material> INTERACTABLE = new ArrayList();
    private static final Map<Material, Material> COMBO = new HashMap();
    private static Set<UUID> teleported = new HashSet<>();

    static {
        INSTANT_BREAK.add(Material.getMaterial("RED_MUSHROOM"));
        INSTANT_BREAK.add(Material.getMaterial("RED_ROSE"));
        INSTANT_BREAK.add(Material.getMaterial("BROWN_MUSHROOM"));
        INSTANT_BREAK.add(Material.getMaterial("YELLOW_FLOWER"));
        INSTANT_BREAK.add(Material.getMaterial("REDSTONE"));
        INSTANT_BREAK.add(Material.getMaterial("REDSTONE_TORCH_OFF"));
        INSTANT_BREAK.add(Material.getMaterial("REDSTONE_TORCH_ON"));
        INSTANT_BREAK.add(Material.getMaterial("REDSTONE_WIRE"));
        INSTANT_BREAK.add(Material.getMaterial("LONG_GRASS"));
        INSTANT_BREAK.add(Material.getMaterial("PAINTING"));
        INSTANT_BREAK.add(Material.getMaterial("WHEAT"));
        INSTANT_BREAK.add(Material.getMaterial("SUGAR_CANE"));
        INSTANT_BREAK.add(Material.getMaterial("SUGAR_CANE_BLOCK"));
        INSTANT_BREAK.add(Material.getMaterial("DIODE"));
        INSTANT_BREAK.add(Material.getMaterial("DIODE_BLOCK_OFF"));
        INSTANT_BREAK.add(Material.getMaterial("DIODE_BLOCK_ON"));
        INSTANT_BREAK.add(Material.getMaterial("SAPLING"));
        INSTANT_BREAK.add(Material.getMaterial("TORCH"));
        INSTANT_BREAK.add(Material.getMaterial("CROPS"));
        INSTANT_BREAK.add(Material.getMaterial("SNOW"));
        INSTANT_BREAK.add(Material.getMaterial("TNT"));
        INSTANT_BREAK.add(Material.getMaterial("POTATO"));
        INSTANT_BREAK.add(Material.getMaterial("CARROT"));

        INTERACTABLE.add(Material.getMaterial("STONE_BUTTON"));
        INTERACTABLE.add(Material.getMaterial("LEVER"));
        INTERACTABLE.add(Material.getMaterial("CHEST"));

        FOOD.add(Material.getMaterial("COOKED_BEEF"));
        FOOD.add(Material.getMaterial("COOKED_CHICKEN"));
        FOOD.add(Material.getMaterial("COOKED_FISH"));
        FOOD.add(Material.getMaterial("GRILLED_PORK"));
        FOOD.add(Material.getMaterial("PORK"));
        FOOD.add(Material.getMaterial("MUSHROOM_SOUP"));
        FOOD.add(Material.getMaterial("RAW_BEEF"));
        FOOD.add(Material.getMaterial("RAW_CHICKEN"));
        FOOD.add(Material.getMaterial("RAW_FISH"));
        FOOD.add(Material.getMaterial("APPLE"));
        FOOD.add(Material.getMaterial("GOLDEN_APPLE"));
        FOOD.add(Material.getMaterial("MELON"));
        FOOD.add(Material.getMaterial("COOKIE"));
        FOOD.add(Material.getMaterial("BREAD"));
        FOOD.add(Material.getMaterial("SPIDER_EYE"));
        FOOD.add(Material.getMaterial("ROTTEN_FLESH"));
        FOOD.add(Material.getMaterial("POTATO_ITEM"));

        COMBO.put(Material.getMaterial("SHEARS"), Material.getMaterial("WOOL"));
        COMBO.put(Material.getMaterial("IRON_SWORD"), Material.getMaterial("WEB"));
        COMBO.put(Material.getMaterial("DIAMOND_SWORD"), Material.getMaterial("WEB"));
        COMBO.put(Material.getMaterial("STONE_SWORD"), Material.getMaterial("WEB"));
        COMBO.put(Material.getMaterial("WOOD_SWORD"), Material.getMaterial("WEB"));
    }

    public static double getXDelta(Location one, Location two) {
        return Math.abs(one.getX() - two.getX());
    }

    public static boolean isDoor(Block block) {
        return block.getType().equals(Material.IRON_DOOR) || block.getType().equals(Material.getMaterial("IRON_DOOR_BLOCK"))
                || block.getType().equals(Material.getMaterial("WOOD_DOOR")) || block.getType().equals(Material.getMaterial("WOODEN_DOOR"));
    }

    public static boolean isFenceGate(Block block) {
        return block.getType().equals(Material.getMaterial("FENCE_GATE"));
    }

    public static boolean isTrapDoor(Block block) {
        return block.getType().equals(Material.getMaterial("TRAP_DOOR"));
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
                || (otherBlock.getRelative(BlockFace.NORTH).getType() == Material.getMaterial("STATIONARY_WATER"));
        boolean s = (otherBlock.getRelative(BlockFace.SOUTH).getType() == Material.WATER)
                || (otherBlock.getRelative(BlockFace.SOUTH).getType() == Material.getMaterial("STATIONARY_WATER"));
        boolean e = (otherBlock.getRelative(BlockFace.EAST).getType() == Material.WATER)
                || (otherBlock.getRelative(BlockFace.EAST).getType() == Material.getMaterial("STATIONARY_WATER"));
        boolean w = (otherBlock.getRelative(BlockFace.WEST).getType() == Material.WATER)
                || (otherBlock.getRelative(BlockFace.WEST).getType() == Material.getMaterial("STATIONARY_WATER"));
        boolean ne = (otherBlock.getRelative(BlockFace.NORTH_EAST).getType() == Material.WATER)
                || (otherBlock.getRelative(BlockFace.NORTH_EAST).getType() == Material.getMaterial("STATIONARY_WATER"));
        boolean nw = (otherBlock.getRelative(BlockFace.NORTH_WEST).getType() == Material.WATER)
                || (otherBlock.getRelative(BlockFace.NORTH_WEST).getType() == Material.getMaterial("STATIONARY_WATER"));
        boolean se = (otherBlock.getRelative(BlockFace.SOUTH_EAST).getType() == Material.WATER)
                || (otherBlock.getRelative(BlockFace.NORTH).getType() == Material.getMaterial("STATIONARY_WATER"));
        boolean sw = (otherBlock.getRelative(BlockFace.SOUTH_WEST).getType() == Material.WATER)
                || (otherBlock.getRelative(BlockFace.SOUTH_WEST).getType() == Material.getMaterial("STATIONARY_WATER"));

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
        if (BlockUtil.isSolid(world.getBlockAt(blockX, blockY, blockZ))) {
            return true;
        }
        if (fracX < 0.3) {
            if (BlockUtil.isSolid(world.getBlockAt(blockX - 1, blockY, blockZ))) {
                return true;
            }
            if (fracZ < 0.3) {
                if (BlockUtil.isSolid(world.getBlockAt(blockX - 1, blockY, blockZ - 1))) {
                    return true;
                }
                if (BlockUtil.isSolid(world.getBlockAt(blockX, blockY, blockZ - 1))) {
                    return true;
                }
                return BlockUtil.isSolid(world.getBlockAt(blockX + 1, blockY, blockZ - 1));
            } else if (fracZ > 0.7) {
                if (BlockUtil.isSolid(world.getBlockAt(blockX - 1, blockY, blockZ + 1))) {
                    return true;
                }
                if (BlockUtil.isSolid(world.getBlockAt(blockX, blockY, blockZ + 1))) {
                    return true;
                }
                return BlockUtil.isSolid(world.getBlockAt(blockX + 1, blockY, blockZ + 1));
            }
        } else if (fracX > 0.7) {
            if (BlockUtil.isSolid(world.getBlockAt(blockX + 1, blockY, blockZ))) {
                return true;
            }
            if (fracZ < 0.3) {
                if (BlockUtil.isSolid(world.getBlockAt(blockX - 1, blockY, blockZ - 1))) {
                    return true;
                }
                if (BlockUtil.isSolid(world.getBlockAt(blockX, blockY, blockZ - 1))) {
                    return true;
                }
                return BlockUtil.isSolid(world.getBlockAt(blockX + 1, blockY, blockZ - 1));
            } else if (fracZ > 0.7) {
                if (BlockUtil.isSolid(world.getBlockAt(blockX - 1, blockY, blockZ + 1))) {
                    return true;
                }
                if (BlockUtil.isSolid(world.getBlockAt(blockX, blockY, blockZ + 1))) {
                    return true;
                }
                return BlockUtil.isSolid(world.getBlockAt(blockX + 1, blockY, blockZ + 1));
            }
        } else if (fracZ < 0.3) {
            return BlockUtil.isSolid(world.getBlockAt(blockX, blockY, blockZ - 1));
        } else return fracZ > 0.7 && BlockUtil.isSolid(world.getBlockAt(blockX, blockY, blockZ + 1));
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



    public static boolean isInteractable(Material m) {
        return INTERACTABLE.contains(m);
    }

    public static boolean sprintFly(Player player) {
        return (player.isSprinting()) || (player.isFlying());
    }

    public static boolean isOnLilyPad(Player player) {
        Block block = player.getLocation().getBlock();
        Material lily = Material.getMaterial("WATER_LILY");

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

    public static boolean isInWeb(Player player) {
        if (BlockUtil.getBlocksAroundCenter(player.getLocation(), 1).contains(Material.getMaterial("WEB"))) {
            return true;
        }
        return (player.getLocation().getBlock().getType() == Material.getMaterial("WEB"))
                || (player.getLocation().clone().add(0.0D, 1.0D, 0.0D).getBlock().getType() == Material.getMaterial("WEB"))
                || (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.getMaterial("WEB"))
                || (player.getLocation().getBlock().getRelative(BlockFace.UP).getType() == Material.getMaterial("WEB"));
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

    public static boolean blocksNear(Player player) {
        return blocksNear(player.getLocation());
    }

    public static boolean blocksNear(final Location loc) {
        boolean nearBlocks = false;
        for (Block block : BlockUtil.getSurrounding(loc.getBlock(), true)) {
            if (block.getType() != Material.AIR) {
                nearBlocks = true;
                break;
            }
        }
        for (final Block block : BlockUtil.getSurrounding(loc.getBlock(), false)) {
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
                new Material[]{Material.getMaterial("FENCE"), Material.getMaterial("FENCE_GATE"), Material.getMaterial("COBBLE_WALL"), Material.LADDER})) {
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
                new Material[]{Material.getMaterial("FENCE"), Material.getMaterial("FENCE_GATE"), Material.getMaterial("COBBLE_WALL"), Material.LADDER})) {
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
