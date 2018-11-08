package me.rida.anticheat.utils.a;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class BlockUtils {

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

        public static boolean isClimbableBlock(Block block) {
            return block.getType().equals(Material.LADDER)
                    || block.getType().equals(Material.VINE);
        }

    }

