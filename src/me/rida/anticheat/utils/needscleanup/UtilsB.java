package me.rida.anticheat.utils.needscleanup;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.util.Vector;

import me.rida.anticheat.utils.UtilCheat;
import me.rida.anticheat.utils.UtilReflection;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.Base64;

public class UtilsB {
    
    public static boolean isNearHalfBlock(Player p) {
        boolean out = false;
         for (Block b : getNearbyBlocks(p.getLocation(), 1)) {
             if (isHalfBlock(b)) {
                 out = true;
             }
         }
        return out;
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
    public static boolean isNearSlime(Player p) {
    	boolean out = false;
		for (Block b : getNearbyBlocks(p.getLocation(), 1)) {
			if (isSlime(b)) {
				out = true;
			}
		}
    	return out;
	}
    static String[] HalfBlocksArray = { "pot", "flower", "step", "slab", "snow", "detector", "daylight",
            "comparator", "repeater", "diode", "water", "lava", "ladder", "vine", "carpet", "sign", "pressure", "plate",
            "button", "mushroom", "torch", "frame", "armor", "banner", "lever", "hook", "redstone", "rail", "brewing",
            "rose", "skull", "enchantment", "cake","bed", "chest", "anvil" };
    public static boolean isHalfBlock(Block block) {
        Material type = block.getType();
        for (String types : HalfBlocksArray) {
            if (type.toString().toLowerCase().contains(types)) {
                return true;
            }
        }
        return false;
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

	public static boolean isIce(Block block) {
		return block.getType().equals(Material.ICE)
				|| block.getType().equals(Material.PACKED_ICE)
				|| block.getType().equals(Material.getMaterial("FROSTED_ICE"));
	}
	public static boolean isSlime(Block block) {
		return block.getType().equals(Material.SLIME_BLOCK);
	}

    public static boolean isLiquid(Block block) {
        return block != null && (block.getType() == Material.WATER || block.getType() == Material.STATIONARY_WATER
                || block.getType() == Material.LAVA || block.getType() == Material.STATIONARY_LAVA);
    }

    public static boolean isSolid(Block material) {
        return material != null && isSolid(material.getTypeId());
    }

    public static boolean isSolid(int block) {
        return isSolid((byte) block);
    }

    public static ArrayList<Block> getBlocksAroundCenter(Location loc, int radius) {
        ArrayList<Block> blocks = new ArrayList();
        for (int x = loc.getBlockX() - radius; x <= loc.getBlockX() + radius; x++) {
            for (int y = loc.getBlockY() - radius; y <= loc.getBlockY() + radius; y++) {
                for (int z = loc.getBlockZ() - radius; z <= loc.getBlockZ() + radius; z++) {
                    Location l = new Location(loc.getWorld(), x, y, z);
                    if (l.distance(loc) <= radius) {
                        blocks.add(l.getBlock());
                    }
                }
            }
        }
        return blocks;
    }
    
    public static ArrayList<Block> getSurrounding(Block block, boolean diagonals) {
        ArrayList<Block> blocks = new ArrayList();
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

    public static ArrayList<Block> getSurroundingB(Block block) {
        ArrayList<Block> blocks = new ArrayList();
        for (double x = -0.5; x <= 0.5; x += 0.5) {
            for (double y = -0.5; y <= 0.5; y += 0.5) {
                for (double z = -0.5; z <= 0.5; z += 0.5) {
                    if ((x != 0) || (y != 0) || (z != 0)) {
                        blocks.add(block.getLocation().add(x, y, z).getBlock());
                    }
                }
            }
        }
        return blocks;
    }
    
    public static ArrayList<Player> getOnlinePlayers() {
        ArrayList<Player> list = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            list.add(player);
        }
        return list;
    }

    public static Location getEyeLocation(Player player) {
        final Location eye = player.getLocation();
        eye.setY(eye.getY() + player.getEyeHeight());
        return eye;
    }

    public static boolean isInWater(Player player) {
        final Material m = player.getLocation().getBlock().getType();
        return m == Material.STATIONARY_WATER || m == Material.WATER;
    }

    public static boolean isOnClimbable(Player player, int blocks) {
        if (blocks == 0) {
            for (Block block : UtilsB.getSurrounding(player.getLocation().getBlock(), false)) {
                if (block.getType() == Material.LADDER || block.getType() == Material.VINE) {
                    return true;
                }
            }
        } else {
            for (Block block : UtilsB.getSurrounding(player.getLocation().clone().add(0.0D, 1.0D, 0.0D).getBlock(),
                    false)) {
                if (block.getType() == Material.LADDER || block.getType() == Material.VINE) {
                    return true;
                }
            }
        }
        return player.getLocation().getBlock().getType() == Material.LADDER
                || player.getLocation().getBlock().getType() == Material.VINE;
    }

    public static boolean isPartiallyStuck(Player player) {
        if (player.getLocation().clone().getBlock() == null) {
            return false;
        }
        Block block = player.getLocation().clone().getBlock();
        if (UtilCheat.isSlab(block) || UtilCheat.isStair(block)) {
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
        Block block1 = player.getLocation().clone().getBlock();
        Block block2 = player.getLocation().clone().add(0.0D, 1.0D, 0.0D).getBlock();
        if (block1.getType().isSolid() && block2.getType().isSolid()) {
            return true;
        }
        return block1.getRelative(BlockFace.DOWN).getType().isSolid()
                || block1.getLocation().getBlock().getRelative(BlockFace.UP).getType().isSolid()
                && block2.getRelative(BlockFace.DOWN).getType().isSolid()
                || block2.getLocation().getBlock().getRelative(BlockFace.UP).getType().isSolid();
    }

    public static boolean isOnGround(Player player) {
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
                || UtilCheat.isBlock(player.getLocation().getBlock().getRelative(BlockFace.DOWN),
                new Material[]{Material.FENCE, Material.FENCE_GATE, Material.COBBLE_WALL, Material.LADDER});
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double getFraction(final double value) {
        return value % 1.0;
    }

    public static double trim(int degree, double d) {
        String format = "#.#";
        for (int i = 1; i < degree; ++i) {
            format = String.valueOf(format) + "#";
        }
        DecimalFormat twoDForm = new DecimalFormat(format);
        return Double.valueOf(twoDForm.format(d).replaceAll(",", "."));
    }

    public static String decrypt(String strEncrypted) {
        String strData = "";

        try {
            byte[] decoded = Base64.getDecoder().decode(strEncrypted);
            strData = (new String(decoded, StandardCharsets.UTF_8) + "\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return strData;
    }

    public static double offset(final Vector a, final Vector b) {
        return a.subtract(b).length();
    }

    public static Vector getHorizontalVector(final Vector v) {
        v.setY(0);
        return v;
    }

    public static Vector getVerticalVector(final Vector v) {
        v.setX(0);
        v.setZ(0);
        return v;
    }
    public static boolean isOnTheGround(Player player) {
        return isOnTheGround(player, 0.25);
    }
    public static boolean isOnTheGround(Player player, double yExpanded) {
        Object box = UtilReflection.modifyBoundingBox(UtilReflection.getBoundingBox(player), 0, -yExpanded, 0,0,0,0);

        return UtilReflection.getCollidingBlocks(player, box).size() > 0;
    }

    public static boolean isNotSpider(Player player) {
        return isOnTheGround(player, 1.25);
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

    public static boolean groundAround(final Location loc) {
        for (int radius = 2, x = -radius; x < radius; ++x) {
            for (int y = -radius; y < radius; ++y) {
                for (int z = -radius; z < radius; ++z) {
                    final Material mat = loc.getWorld().getBlockAt(loc.add((double)x, (double)y, (double)z)).getType();
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