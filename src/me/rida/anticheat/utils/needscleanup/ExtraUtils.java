package me.rida.anticheat.utils.needscleanup;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.rida.anticheat.utils.UtilReflection;

public class ExtraUtils {

    public static boolean isOnGround(Player player) {
        return isOnGround(player, 0.25);
    }
    public static boolean isOnGround(Player player, double yExpanded) {
        Object box = UtilReflection.modifyBoundingBox(UtilReflection.getBoundingBox(player), 0, -yExpanded, 0,0,0,0);

        return UtilReflection.getCollidingBlocks(player, box).size() > 0;
    }

    public static boolean isNotSpider(Player player) {
        return isOnGround(player, 1.25);
    } 

    public static boolean isInLiquid(Player player) {
        Object box = UtilReflection.getBoundingBox(player);

        double minX = (double) UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "a"), box);
        double minY = (double) UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "b"), box);
        double minZ = (double) UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "c"), box);
        double maxX = (double) UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "d"), box);
        double maxY = (double) UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "e"), box);
        double maxZ = (double) UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "f"), box);

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
        Object box = UtilReflection.modifyBoundingBox(UtilReflection.getBoundingBox(player), 0, -0.5,0,0,0,0);

        double minX = (double) UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "a"), box);
        double minY = (double) UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "b"), box);
        double minZ = (double) UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "c"), box);
        double maxX = (double) UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "d"), box);
        double maxY = (double) UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "e"), box);
        double maxZ = (double) UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "f"), box);

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
        Object box = UtilReflection.modifyBoundingBox(UtilReflection.getBoundingBox(player), 0, 0,0,0,1,0);

        double minX = (double) UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "a"), box);
        double minY = (double) UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "b"), box);
        double minZ = (double) UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "c"), box);
        double maxX = (double) UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "d"), box);
        double maxY = (double) UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "e"), box);
        double maxZ = (double) UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "f"), box);

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
        Object box = UtilReflection.modifyBoundingBox(UtilReflection.getBoundingBox(player), 0, -0.5,0,0,0,0);

        double minX = (double) UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "a"), box);
        double minY = (double) UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "b"), box);
        double minZ = (double) UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "c"), box);
        double maxX = (double) UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "d"), box);
        double maxY = (double) UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "e"), box);
        double maxZ = (double) UtilReflection.getInvokedField(UtilReflection.getField(box.getClass(), "f"), box);

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

    public static boolean isSlab(Block block) {
        return block.getTypeId() == 44 || block.getTypeId() == 126 || block.getTypeId() == 205 || block.getTypeId() == 182;
    }

    public static boolean isStair(Block block) {
        return block.getType().equals(Material.ACACIA_STAIRS) || block.getType().equals(Material.BIRCH_WOOD_STAIRS) || block.getType().equals(Material.BRICK_STAIRS) || block.getType().equals(Material.COBBLESTONE_STAIRS) || block.getType().equals(Material.DARK_OAK_STAIRS) || block.getType().equals(Material.NETHER_BRICK_STAIRS) || block.getType().equals(Material.JUNGLE_WOOD_STAIRS) || block.getType().equals(Material.QUARTZ_STAIRS) || block.getType().equals(Material.SMOOTH_STAIRS) || block.getType().equals(Material.WOOD_STAIRS) || block.getType().equals(Material.SANDSTONE_STAIRS) || block.getType().equals(Material.SPRUCE_WOOD_STAIRS) || block.getTypeId() == 203 || block.getTypeId() == 180;
    }
}