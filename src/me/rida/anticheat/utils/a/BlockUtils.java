package me.rida.anticheat.utils.a;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class BlockUtils {
    public static HashSet<Byte> blockPassSet = new HashSet();

    public static boolean isSolid(final byte block) {
        if (blockPassSet.isEmpty()) {
            blockPassSet.add((byte) 0);
            blockPassSet.add((byte) 6);
            blockPassSet.add((byte) 8);
            blockPassSet.add((byte) 9);
            blockPassSet.add((byte) 10);
            blockPassSet.add((byte) 11);
            blockPassSet.add((byte) 27);
            blockPassSet.add((byte) 28);
            blockPassSet.add((byte) 30);
            blockPassSet.add((byte) 31);
            blockPassSet.add((byte) 32);
            blockPassSet.add((byte) 37);
            blockPassSet.add((byte) 38);
            blockPassSet.add((byte) 39);
            blockPassSet.add((byte) 40);
            blockPassSet.add((byte) 50);
            blockPassSet.add((byte) 51);
            blockPassSet.add((byte) 55);
            blockPassSet.add((byte) 59);
            blockPassSet.add((byte) 63);
            blockPassSet.add((byte) 66);
            blockPassSet.add((byte) 68);
            blockPassSet.add((byte) 69);
            blockPassSet.add((byte) 70);
            blockPassSet.add((byte) 72);
            blockPassSet.add((byte) 75);
            blockPassSet.add((byte) 76);
            blockPassSet.add((byte) 77);
            blockPassSet.add((byte) 78);
            blockPassSet.add((byte) 83);
            blockPassSet.add((byte) 90);
            blockPassSet.add((byte) 104);
            blockPassSet.add((byte) 105);
            blockPassSet.add((byte) 115);
            blockPassSet.add((byte) 119);
            blockPassSet.add((byte) (-124));
            blockPassSet.add((byte) (-113));
            blockPassSet.add((byte) (-81));
            blockPassSet.add((byte) (-85));
        }
        return !blockPassSet.contains(block);
    }


    public static boolean containsBlockType(Material[] arrmaterial, Block block) {
        Material[] arrmaterial2 = arrmaterial;
        int n = arrmaterial2.length;
        int n2 = 0;
        while (n2 < n) {
            Material material = arrmaterial2[n2];
            if (material == block.getType()) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public static boolean isLiquid(Block block) {
        if (block != null && (block.getType() == Material.WATER || block.getType() == Material.STATIONARY_WATER || block.getType() == Material.LAVA || block.getType() == Material.STATIONARY_LAVA)) {
            return true;
        }
        return false;
    }

    public static boolean isSolid(Block block) {
        if (block != null && isSolid(block.getTypeId())) {
            return true;
        }
        return false;
    }

    public static boolean isSolid(int n) {
        return isSolid((byte)n);
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

        
        public static boolean isNearHalfBlock(Player p) {
            boolean out = false;
             for (Block b : getNearbyBlocks(p.getLocation(), 1)) {
                 if (isHalfBlock(b)) {
                     out = true;
                 }
             }
            return out;
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
        public static boolean isNearPistion(Player p) {
            boolean out = false;
            for (Block b : getNearbyBlocks(p.getLocation(), 1)) {
               if (b.getType() == Material.PISTON_BASE || b.getType() == Material.PISTON_MOVING_PIECE || b.getType() == Material.PISTON_STICKY_BASE || b.getType() == Material.PISTON_EXTENSION) {
                   out = true;
               }
            }
            return out;
        }
    	public static boolean isNearStiar(Player p) {
    		boolean out = false;
    		for (Block b : getNearbyBlocks(p.getLocation(), 1)) {
    			if (BlockUtils.isStair(b)) {
    				out = true;
    			}
    		}
    		return out;
    	}
    	public static boolean isNearLiquid(Player p) {
    		boolean out = false;
    		for (Block b : getNearbyBlocks(p.getLocation(), 1)) {
    			if (BlockUtils.isLiquid(b)) {
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
    	

    	public static boolean isIce(Block block) {
    		return block.getType().equals(Material.ICE)
    				|| block.getType().equals(Material.PACKED_ICE)
    				|| block.getType().equals(Material.getMaterial("FROSTED_ICE"));
    	}
    	public static boolean isSlime(Block block) {
    		return block.getType().equals(Material.SLIME_BLOCK);
    	}
}