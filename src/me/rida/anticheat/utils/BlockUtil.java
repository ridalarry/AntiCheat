package me.rida.anticheat.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class BlockUtil {
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
        if (block != null && (block.getType() == Material.WATER 
        		|| block.getType() == Material.getMaterial("STATIONARY_WATER") 
        		|| block.getType() == Material.LAVA 
        		|| block.getType() == Material.getMaterial("STATIONARY_LAVA"))) {
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
		if(isSlab(block)||isStair(block)) {
			return 0.5;
		}
		if(isFence(block)) {
			return 0.5;
		}
		if(isChest(block)) {
			return 0.125;
		}
		return 0;
	}
    static String[] Shulker = { "shulker"};
	public static boolean isShulker(Block block) {
        Material type = block.getType();
        for (String types : Shulker) {
            if (type.toString().toLowerCase().contains(types)) {
                return true;
            }
        }
        return false;
    }
	public static boolean isChest(Block block) {
		return block.getType().equals(Material.getMaterial("CHEST"))
				|| block.getType().equals(Material.getMaterial("ENDER_CHEST"))
				|| block.getType().equals(Material.getMaterial("TRAPPED_CHEST"))
				|| isShulker(block);
			
	}

    static String[] Fence = { "fence"};
	public static boolean isFence(Block block) {
        Material type = block.getType();
        for (String types : Fence) {
            if (type.toString().toLowerCase().contains(types)) {
                return true;
            }
        }
        return false;
    }
		
	public static boolean isPiston(Block block) {
		return block.getType().equals(Material.getMaterial("PISTON_MOVING_PIECE")) 
				|| block.getType().equals(Material.getMaterial("PISTON_EXTENSION"))
				|| block.getType().equals(Material.getMaterial("PISTON_BASE")) 
				|| block.getType().equals(Material.getMaterial("PISTON_STICKY_BASE"));
	}

	public static boolean isAir(Block block) {
	    if(block.getType().equals(Material.getMaterial("AIR"))){
	    		return true;
	    }
	    return false;
	}

    static String[] Slab = { "slab", "step"};
    static String[] Stair = { "stair"};
	public static boolean isStair(Block block) {
        Material type = block.getType();
        for (String types : Stair) {
            if (type.toString().toLowerCase().contains(types)) {
                return true;
            }
        }
        return false;
    }
	
	public static boolean isSlab(Block block) {
        Material type = block.getType();
        for (String types : Slab) {
            if (type.toString().toLowerCase().contains(types)) {
                return true;
            }
        }
        return false;
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
            return block.getType().equals(Material.getMaterial("LADDER"))
                    || block.getType().equals(Material.getMaterial("VINE"));
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

        public static boolean isNearAllowedPhase(Player p) {
            boolean out = false;
             for (Block b : getNearbyBlocks(p.getLocation(), 1)) {
                 if (allowedPhase(b)) {
                     out = true;
                 }
             }
            return out;
        }
        public static boolean isNearLessThanABlock(Player p) {
            boolean out = false;
             for (Block b : getNearbyBlocks(p.getLocation(), 1)) {
                 if (isLessThanBlock(b)) {
                     out = true;
                 }
             }
            return out;
        }
        public static boolean isNearSlab(Player p) {
            boolean out = false;
             for (Block b : getNearbyBlocks(p.getLocation(), 1)) {
                 if (isSlab(b)) {
                     out = true;
                 }
             }
            return out;
        }
        public static boolean isNearAir(Player p) {
            boolean out = false;
             for (Block b : getNearbyBlocks(p.getLocation(), 1)) {
                 if (isAir(b)) {
                     out = true;
                 }
             }
            return out;
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
        public static boolean isNearClimable(Player p) {
        	boolean out = false;
    		for (Block b : getNearbyBlocks(p.getLocation(), 1)) {
    			if (isClimbableBlock(b)) {
    				out = true;
    			}
    		}
        	return out;
    	}
        public static boolean isNearPistion(Player p) {
            boolean out = false;
            for (Block b : getNearbyBlocks(p.getLocation(), 1)) {
               if (b.getType() == Material.getMaterial("PISTON_BASE")
            		   || b.getType() == Material.getMaterial("PISTON_MOVING_PIECE")
            		   || b.getType() == Material.getMaterial("PISTON_STICKY_BASE") 
            		   || b.getType() == Material.getMaterial("PISTON_EXTENSION")) {
                   out = true;
               }
            }
            return out;
        }
        public static boolean isNearFence(Player p) {
            boolean out = false;
            for (Block b : getNearbyBlocks(p.getLocation(), 1)) {
               if (isFence(b)) {
                   out = true;
               }
            }
            return out;
        }
    	public static boolean isNearStair(Player p) {
    		boolean out = false;
    		for (Block b : getNearbyBlocks(p.getLocation(), 1)) {
    			if (BlockUtil.isStair(b)) {
    				out = true;
    			}
    		}
    		return out;
    	}
    	public static boolean isNearLiquid(Player p) {
    		boolean out = false;
    		for (Block b : getNearbyBlocks(p.getLocation(), 1)) {
    			if (BlockUtil.isLiquid(b)) {
    				out = true;
    			}
    		}
    		return out;
    	}
        static String[] HalfBlocksArray = { "pot", "flower", "step", "slab", "snow", "detector", "daylight",
                "comparator", "repeater", "diode", "water", "lava", "ladder", "vine", "carpet", "sign", "pressure", "plate",
                "button", "mushroom", "torch", "frame", "armor", "banner", "lever", "hook", "redstone", "rail", "brewing",
                "rose", "skull", "enchantment", "cake", "bed", "shulker"};
        public static boolean isHalfBlock(Block block) {
            Material type = block.getType();
            for (String types : HalfBlocksArray) {
                if (type.toString().toLowerCase().contains(types)) {
                    return true;
                }
            }
            return false;
        }
        public static boolean isLessThanBlock(Block block) {
            Material type = block.getType();
            for (String types : HalfBlocksArray) {
                if (type.toString().toLowerCase().contains("chest")||type.toString().toLowerCase().contains("anvil") ||type.toString().toLowerCase().contains("shulker") ) {
                    return true;
                }
            }
            return false;
        }

    	public static boolean allowedPhase(Block block) {
    		return block.getType().equals(Material.getMaterial("SIGN"))
    				|| block.getType().equals(Material.getMaterial("BANNER"))
    				|| block.getType().equals(Material.getMaterial("WALL_BANNER"))
    				|| block.getType().equals(Material.getMaterial("STANDING_BANNER"))
    				|| block.getType().equals(Material.getMaterial("IRON_TRAPDOOR"))
    				|| block.getType().equals(Material.getMaterial("FENCE"))
    				|| block.getType().equals(Material.getMaterial("ANVIL"))
    				|| block.getType().equals(Material.getMaterial("TRAP_DOOR"))
    				|| block.getType().equals(Material.getMaterial("SIGN_POST"))
    				|| block.getType().equals(Material.getMaterial("WALL_SIGN"))
    				|| block.getType().equals(Material.getMaterial("SUGAR_CANE_BLOCK"))
    				|| block.getType().equals(Material.getMaterial("WHEAT"))
    				|| block.getType().equals(Material.getMaterial("POTATO"))
    				|| block.getType().equals(Material.getMaterial("CARROT"))
    				|| block.getType().equals(Material.getMaterial("STEP"))
    				|| block.getType().equals(Material.getMaterial("AIR"))
    				|| block.getType().equals(Material.getMaterial("WOOD_STEP"))
    				|| block.getType().equals(Material.getMaterial("SOUL_SAND"))
    				|| block.getType().equals(Material.getMaterial("CARPET"))
    				|| block.getType().equals(Material.getMaterial("STONE_PLATE"))
    				|| block.getType().equals(Material.getMaterial("WOOD_PLATE"))
    				|| block.getType().equals(Material.getMaterial("LADDER"))
    				|| block.getType().equals(Material.getMaterial("CHEST"))
    				|| block.getType().equals(Material.getMaterial("WATER"))
    				|| block.getType().equals(Material.getMaterial("STATIONARY_WATER"))
    				|| block.getType().equals(Material.getMaterial("LAVA"))
    				|| block.getType().equals(Material.getMaterial("STATIONARY_LAVA"))
    				|| block.getType().equals(Material.getMaterial("REDSTONE_COMPARATOR"))
    				|| block.getType().equals(Material.getMaterial("REDSTONE_COMPARATOR_OFF"))
    				|| block.getType().equals(Material.getMaterial("REDSTONE_COMPARATOR_ON"))
    				|| block.getType().equals(Material.getMaterial("IRON_PLATE"))
    				|| block.getType().equals(Material.getMaterial("GOLD_PLATE"))
    				|| block.getType().equals(Material.getMaterial("DAYLIGHT_DETECTOR"))
    				|| block.getType().equals(Material.getMaterial("STONE_BUTTON"))
    				|| block.getType().equals(Material.getMaterial("WOOD_BUTTON"))
    				|| block.getType().equals(Material.getMaterial("HOPPER"))
    				|| block.getType().equals(Material.getMaterial("RAILS"))
    				|| block.getType().equals(Material.getMaterial("ACTIVATOR_RAIL"))
    				|| block.getType().equals(Material.getMaterial("DETECTOR_RAIL"))
    				|| block.getType().equals(Material.getMaterial("POWERED_RAIL"))
    				|| block.getType().equals(Material.getMaterial("TRIPWIRE_HOOK"))
    				|| block.getType().equals(Material.getMaterial("TRIPWIRE"))
    				|| block.getType().equals(Material.getMaterial("SNOW_BLOCK"))
    				|| block.getType().equals(Material.getMaterial("REDSTONE_TORCH_OFF"))
    				|| block.getType().equals(Material.getMaterial("REDSTONE_TORCH_ON"))
    				|| block.getType().equals(Material.getMaterial("DIODE_BLOCK_OFF"))
    				|| block.getType().equals(Material.getMaterial("DIODE_BLOCK_ON"))
    				|| block.getType().equals(Material.getMaterial("DIODE"))
    				|| block.getType().equals(Material.getMaterial("SEEDS"))
    				|| block.getType().equals(Material.getMaterial("MELON_SEEDS"))
    				|| block.getType().equals(Material.getMaterial("PUMPKIN_SEEDS"))
    				|| block.getType().equals(Material.getMaterial("DOUBLE_PLANT"))
    				|| block.getType().equals(Material.getMaterial("LONG_GRASS"))
    				|| block.getType().equals(Material.getMaterial("WEB"))
    				|| block.getType().equals(Material.getMaterial("SNOW"))
    				|| block.getType().equals(Material.getMaterial("FLOWER_POT"))
    				|| block.getType().equals(Material.getMaterial("BREWING_STAND"))
    				|| block.getType().equals(Material.getMaterial("CAULDRON"))
    				|| block.getType().equals(Material.getMaterial("CACTUS"))
    				|| block.getType().equals(Material.getMaterial("WATER_LILY"))
    				|| block.getType().equals(Material.getMaterial("RED_ROSE"))
    				|| block.getType().equals(Material.getMaterial("ENCHANTMENT_TABLE"))
    				|| block.getType().equals(Material.getMaterial("ENDER_PORTAL_FRAME"))
    				|| block.getType().equals(Material.getMaterial("PORTAL"))
    				|| block.getType().equals(Material.getMaterial("ENDER_PORTAL"))
    				|| block.getType().equals(Material.getMaterial("ENDER_CHEST"))
    				|| block.getType().equals(Material.getMaterial("NETHER_FENCE"))
    				|| block.getType().equals(Material.getMaterial("NETHER_WARTS"))
    				|| block.getType().equals(Material.getMaterial("REDSTONE_WIRE"))
    				|| block.getType().equals(Material.getMaterial("LEVER"))
    				|| block.getType().equals(Material.getMaterial("YELLOW_FLOWER"))
    				|| block.getType().equals(Material.getMaterial("CROPS"))
    				|| block.getType().equals(Material.getMaterial("WATER"))
    				|| block.getType().equals(Material.getMaterial("LAVA"))
    				|| block.getType().equals(Material.getMaterial("SKULL"))
    				|| block.getType().equals(Material.getMaterial("TRAPPED_CHEST"))
    				|| block.getType().equals(Material.getMaterial("FIRE"))
    				|| block.getType().equals(Material.getMaterial("BROWN_MUSHROOM"))
    				|| block.getType().equals(Material.getMaterial("RED_MUSHROOM"))
    				|| block.getType().equals(Material.getMaterial("DEAD_BUSH"))
    				|| block.getType().equals(Material.getMaterial("SAPLING"))
    				|| block.getType().equals(Material.getMaterial("TORCH"))
    				|| block.getType().equals(Material.getMaterial("MELON_STEM"))
    				|| block.getType().equals(Material.getMaterial("PUMPKIN_STEM"))
    				|| block.getType().equals(Material.getMaterial("COCOA"))
    				|| block.getType().equals(Material.getMaterial("BED"))
    				|| block.getType().equals(Material.getMaterial("BED_BLOCK"))
    				|| block.getType().equals(Material.getMaterial("PISTON_EXTENSION"))
    				|| block.getType().equals(Material.getMaterial("PISTON_MOVING_PIECE"))
    				|| block.getType().equals(Material.getMaterial("IRON_FENCE"))
    				|| block.getType().equals(Material.getMaterial("THIN_GLASS"))
    				|| block.getType().equals(Material.getMaterial("STAINED_GLASS_PANE"))
    				|| block.getType().equals(Material.getMaterial("COBBLE_WALL"));
    	}

    	public static boolean isIce(Block block) {
    		return block.getType().equals(Material.getMaterial("ICE"))
    				|| block.getType().equals(Material.getMaterial("PACKED_ICE"))
    				|| block.getType().equals(Material.getMaterial("FROSTED_ICE"));
    	}
    	public static boolean isSlime(Block block) {
    		return block.getType().equals(Material.getMaterial("SLIME_BLOCK"));
    	}
}
