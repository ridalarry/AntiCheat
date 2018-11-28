package me.rida.anticheat.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class BlockUtil {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static HashSet<Byte> blockPassSet = new HashSet();
	public static Map<Material, BoundingBox[]> collisionBoundingBoxes;
	public static List<Material> allowed;
	public BlockUtil() {
		collisionBoundingBoxes = new HashMap<>();

		setupCollisionBB();

		allowed = new ArrayList<>();

		allowed.add(Material.AIR);
		allowed.add(Material.SIGN);
		allowed.add(Material.SIGN_POST);
		allowed.add(Material.WALL_SIGN);
		allowed.add(Material.SUGAR_CANE_BLOCK);
		allowed.add(Material.WHEAT);
		allowed.add(Material.POTATO);
		allowed.add(Material.CARROT);
		allowed.add(Material.STEP);
		allowed.add(Material.WOOD_STEP);
		allowed.add(Material.SOUL_SAND);
		allowed.add(Material.CARPET);
		allowed.add(Material.STONE_PLATE);
		allowed.add(Material.WOOD_PLATE);
		allowed.add(Material.LADDER);
		allowed.add(Material.CHEST);
		allowed.add(Material.WATER);
		allowed.add(Material.STATIONARY_WATER);
		allowed.add(Material.LAVA);
		allowed.add(Material.STATIONARY_LAVA);
		allowed.add(Material.REDSTONE_COMPARATOR);
		allowed.add(Material.REDSTONE_COMPARATOR_OFF);
		allowed.add(Material.REDSTONE_COMPARATOR_ON);
		allowed.add(Material.IRON_PLATE);
		allowed.add(Material.GOLD_PLATE);
		allowed.add(Material.DAYLIGHT_DETECTOR);
		allowed.add(Material.STONE_BUTTON);
		allowed.add(Material.WOOD_BUTTON);
		allowed.add(Material.HOPPER);
		allowed.add(Material.RAILS);
		allowed.add(Material.ACTIVATOR_RAIL);
		allowed.add(Material.DETECTOR_RAIL);
		allowed.add(Material.POWERED_RAIL);
		allowed.add(Material.TRIPWIRE_HOOK);
		allowed.add(Material.TRIPWIRE);
		allowed.add(Material.SNOW_BLOCK);
		allowed.add(Material.REDSTONE_TORCH_OFF);
		allowed.add(Material.REDSTONE_TORCH_ON);
		allowed.add(Material.DIODE_BLOCK_OFF);
		allowed.add(Material.DIODE_BLOCK_ON);
		allowed.add(Material.DIODE);
		allowed.add(Material.SEEDS);
		allowed.add(Material.MELON_SEEDS);
		allowed.add(Material.PUMPKIN_SEEDS);
		allowed.add(Material.DOUBLE_PLANT);
		allowed.add(Material.LONG_GRASS);
		allowed.add(Material.WEB);
		allowed.add(Material.CAKE_BLOCK);
		allowed.add(Material.SNOW);
		allowed.add(Material.FLOWER_POT);
		allowed.add(Material.BREWING_STAND);
		allowed.add(Material.CAULDRON);
		allowed.add(Material.CACTUS);
		allowed.add(Material.WATER_LILY);
		allowed.add(Material.RED_ROSE);
		allowed.add(Material.ENCHANTMENT_TABLE);
		allowed.add(Material.ENDER_PORTAL_FRAME);
		allowed.add(Material.PORTAL);
		allowed.add(Material.ENDER_PORTAL);
		allowed.add(Material.ENDER_CHEST);
		allowed.add(Material.NETHER_FENCE);
		allowed.add(Material.NETHER_WARTS);
		allowed.add(Material.REDSTONE_WIRE);
		allowed.add(Material.LEVER);
		allowed.add(Material.YELLOW_FLOWER);
		allowed.add(Material.CROPS);
		allowed.add(Material.WATER);
		allowed.add(Material.LAVA);
		allowed.add(Material.SKULL);
		allowed.add(Material.TRAPPED_CHEST);
		allowed.add(Material.FIRE);
		allowed.add(Material.BROWN_MUSHROOM);
		allowed.add(Material.RED_MUSHROOM);
		allowed.add(Material.DEAD_BUSH);
		allowed.add(Material.SAPLING);
		allowed.add(Material.TORCH);
		allowed.add(Material.MELON_STEM);
		allowed.add(Material.PUMPKIN_STEM);
		allowed.add(Material.COCOA);
		allowed.add(Material.BED);
		allowed.add(Material.BED_BLOCK);
		allowed.add(Material.PISTON_EXTENSION);
		allowed.add(Material.PISTON_MOVING_PIECE);
	}@SuppressWarnings("deprecation")
	public static boolean isSolid2(Block block) {
		int type = block.getType().getId();

		switch (type) {
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 7:
		case 12:
		case 13:
		case 14:
		case 15:
		case 16:
		case 17:
		case 18:
		case 19:
		case 20:
		case 21:
		case 22:
		case 23:
		case 24:
		case 25:
		case 26:
		case 29:
		case 34:
		case 33:
		case 35:
		case 36:
		case 41:
		case 42:
		case 43:
		case 44:
		case 45:
		case 46:
		case 47:
		case 48:
		case 49:
		case 52:
		case 53:
		case 54:
		case 56:
		case 57:
		case 58:
		case 60:
		case 61:
		case 62:
		case 64:
		case 65:
		case 67:
		case 71:
		case 73:
		case 74:
		case 78:
		case 79:
		case 80:
		case 81:
		case 82:
		case 84:
		case 85:
		case 86:
		case 87:
		case 88:
		case 89:
		case 91:
		case 92:
		case 93:
		case 94:
		case 95:
		case 96:
		case 97:
		case 98:
		case 99:
		case 100:
		case 101:
		case 102:
		case 103:
		case 106:
		case 107:
		case 108:
		case 109:
		case 110:
		case 111:
		case 112:
		case 113:
		case 114:
		case 116:
		case 117:
		case 118:
		case 120:
		case 121:
		case 122:
		case 123:
		case 124:
		case 125:
		case 126:
		case 127:
		case 128:
		case 129:
		case 130:
		case 133:
		case 134:
		case 135:
		case 136:
		case 137:
		case 138:
		case 139:
		case 140:
		case 144:
		case 145:
		case 146:
		case 149:
		case 150:
		case 151:
		case 152:
		case 153:
		case 154:
		case 155:
		case 156:
		case 158:
		case 159:
		case 160:
		case 161:
		case 162:
		case 163:
		case 164:
		case 165:
		case 166:
		case 167:
		case 168:
		case 169:
		case 170:
		case 171:
		case 172:
		case 173:
		case 174:
		case 178:
		case 179:
		case 180:
		case 181:
		case 182:
		case 183:
		case 184:
		case 185:
		case 186:
		case 187:
		case 188:
		case 189:
		case 190:
		case 191:
		case 192:
		case 193:
		case 194:
		case 195:
		case 196:
		case 197:
		case 198:
		case 199:
		case 200:
		case 201:
		case 202:
		case 203:
		case 204:
		case 205:
		case 206:
		case 207:
		case 208:
		case 210:
		case 211:
		case 212:
		case 213:
		case 214:
		case 215:
		case 216:
		case 218:
		case 219:
		case 220:
		case 221:
		case 222:
		case 223:
		case 224:
		case 225:
		case 226:
		case 227:
		case 228:
		case 229:
		case 230:
		case 231:
		case 232:
		case 233:
		case 234:
		case 235:
		case 236:
		case 237:
		case 238:
		case 239:
		case 240:
		case 241:
		case 242:
		case 243:
		case 244:
		case 245:
		case 246:
		case 247:
		case 248:
		case 249:
		case 250:
		case 251:
		case 252:
		case 255:
		case 397:
		case 355:
			return true;

		}
		return false;
	}
	public static boolean isSolid(byte block) {
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


	public static boolean isClimbableBlock(Block block) {
		return block.getType() == Material.LADDER || block.getType() == Material.VINE;
	}


	public static boolean isIce(Block block) {
		return block.getType().equals(Material.ICE)
				|| block.getType().equals(Material.PACKED_ICE)
				|| block.getType().equals(Material.getMaterial("FROSTED_ICE"));
	}


	public static boolean isLiquid(Block block) {
		if (block != null && (block.getType() == Material.WATER || block.getType() == Material.STATIONARY_WATER || block.getType() == Material.LAVA || block.getType() == Material.STATIONARY_LAVA)) {
			return true;
		}
		return false;
	}
	@SuppressWarnings("deprecation")
	public static boolean isSlab(Block block) {
		return block.getType().equals(Material.WOOD_STEP)
				|| block.getType().equals(Material.STEP)
				|| block.getTypeId() == 182
				|| block.getTypeId() == 44
				|| block.getTypeId() == 126
				|| block.getTypeId() == 205;
	}

	@SuppressWarnings("deprecation")
	public static boolean isStair(Block block) {
		if(block.getType().equals(Material.ACACIA_STAIRS)
				|| block.getType().equals(Material.BIRCH_WOOD_STAIRS)
				|| block.getType().equals(Material.BRICK_STAIRS)
				|| block.getType().equals(Material.COBBLESTONE_STAIRS)
				|| block.getType().equals(Material.DARK_OAK_STAIRS)
				|| block.getType().equals(Material.NETHER_BRICK_STAIRS)
				|| block.getType().equals(Material.JUNGLE_WOOD_STAIRS)
				|| block.getType().equals(Material.QUARTZ_STAIRS)
				|| block.getType().equals(Material.SMOOTH_STAIRS)
				|| block.getType().equals(Material.WOOD_STAIRS)
				|| block.getType().equals(Material.SANDSTONE_STAIRS)
				|| block.getType().equals(Material.SPRUCE_WOOD_STAIRS)
				|| block.getTypeId()==180
				|| block.getTypeId() == 203) {
			return true;
		}
		return false;
	}
	public static boolean isPiston(Block block) {
		return block.getType().equals(Material.PISTON_MOVING_PIECE)
				|| block.getType().equals(Material.PISTON_EXTENSION)
				|| block.getType().equals(Material.PISTON_BASE)
				|| block.getType().equals(Material.PISTON_STICKY_BASE);
	}
	public static boolean isChest(Block block) {
		return block.getType().equals(Material.CHEST)|| block.getType().equals(Material.ENDER_CHEST)|| block.getType().equals(Material.TRAPPED_CHEST);

	}
	@SuppressWarnings("deprecation")
	public static boolean isFence(Block block) {
		return block.getType().equals(Material.FENCE)
				|| block.getType().getId() == 85
				|| block.getType().getId() == 139
				|| block.getType().getId() == 113
				|| block.getTypeId() == 188
				|| block.getTypeId() == 189
				|| block.getTypeId() == 190
				|| block.getTypeId() == 191
				|| block.getTypeId() == 192
				|| block.getType().equals(Material.NETHER_FENCE);

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


	@SuppressWarnings("deprecation")
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
	@SuppressWarnings("deprecation")
	public static boolean isPressure(Block block) {
		return block.getTypeId() == 70
				|| block.getTypeId() == 72
				|| block.getTypeId() == 147
				|| block.getTypeId() == 148;
	}


	public static boolean isAir(Block block) {
		if(block.getType().equals(Material.AIR)){
			return true;
		}
		return false;
	}



	public static ArrayList<Block> getSurroundingXZ(Block block, boolean diagonals) {
		ArrayList<Block> blocks = new ArrayList<>();
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
		List<Block> blocks = new ArrayList<>();
		for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
			for(int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
				for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
					blocks.add(location.getWorld().getBlockAt(x, y, z));
				}
			}
		}
		return blocks;
	}




	@SuppressWarnings({ "unchecked", "rawtypes" })
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
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
	public static boolean isNearPiston(Player p) {
		boolean out = false;
		for (Block b : getNearbyBlocks(p.getLocation(), 1)) {
			if (isPiston(b)) {
				out = true;
			}
		}
		return out;
	}
	public static boolean isNearPressure(Player p) {
		boolean out = false;
		for (Block b : getNearbyBlocks(p.getLocation(), 1)) {
			if (isPressure(b)) {
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
	public static boolean isNearSlime(Location loc) {
		boolean out = false;
		for (Block b : getNearbyBlocks(loc, 1)) {
			if (isSlime(b)) {
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
			if (b.getType() == Material.PISTON_BASE || b.getType() == Material.PISTON_MOVING_PIECE || b.getType() == Material.PISTON_STICKY_BASE || b.getType() == Material.PISTON_EXTENSION) {
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
	static String[] HalfBlocksArray = { "pot", "flower", "step", "slab", "snow", "detector", "daylight",
			"comparator", "repeater", "diode", "water", "lava", "ladder", "vine", "carpet", "sign", "pressure", "plate",
			"button", "mushroom", "torch", "frame", "armor", "banner", "lever", "hook", "redstone", "rail", "brewing",
			"rose", "skull", "enchantment", "cake", "bed"};
	public static boolean isHalfBlock(Block block) {
		Material type = block.getType();
		for (String types : HalfBlocksArray) {
			if (type.toString().toLowerCase().contains(types)) {
				return true;
			}
		}
		return false;
	}
	@SuppressWarnings("unused")
	public static boolean isLessThanBlock(Block block) {
		Material type = block.getType();
		for (String types : HalfBlocksArray) {
			if (type.toString().toLowerCase().contains("chest")||type.toString().toLowerCase().contains("anvil")) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public static boolean allowedPhase(Block block) {
		return block.getType().equals(Material.SIGN)
				|| block.getTypeId() == 425
				|| block.getTypeId() == 167
				|| block.getTypeId() == 177
				|| block.getTypeId() == 176
				|| block.getTypeId() == 165
				|| block.getType().equals(Material.FENCE)
				|| block.getType().equals(Material.ANVIL)
				|| block.getType().equals(Material.TRAP_DOOR)
				|| block.getType().equals(Material.SIGN_POST)
				|| block.getType().equals(Material.WALL_SIGN)
				|| block.getType().equals(Material.SUGAR_CANE_BLOCK)
				|| block.getType().equals(Material.WHEAT)
				|| block.getType().equals(Material.POTATO)
				|| block.getType().equals(Material.CARROT)
				|| block.getType().equals(Material.STEP)
				|| block.getType().equals(Material.AIR)
				|| block.getType().equals(Material.WOOD_STEP)
				|| block.getType().equals(Material.SOUL_SAND)
				|| block.getType().equals(Material.CARPET)
				|| block.getType().equals(Material.STONE_PLATE)
				|| block.getType().equals(Material.WOOD_PLATE)
				|| block.getType().equals(Material.LADDER)
				|| block.getType().equals(Material.CHEST)
				|| block.getType().equals(Material.WATER)
				|| block.getType().equals(Material.STATIONARY_WATER)
				|| block.getType().equals(Material.LAVA)
				|| block.getType().equals(Material.STATIONARY_LAVA)
				|| block.getType().equals(Material.REDSTONE_COMPARATOR)
				|| block.getType().equals(Material.REDSTONE_COMPARATOR_OFF)
				|| block.getType().equals(Material.REDSTONE_COMPARATOR_ON)
				|| block.getType().equals(Material.IRON_PLATE)
				|| block.getType().equals(Material.GOLD_PLATE)
				|| block.getType().equals(Material.DAYLIGHT_DETECTOR)
				|| block.getType().equals(Material.STONE_BUTTON)
				|| block.getType().equals(Material.WOOD_BUTTON)
				|| block.getType().equals(Material.HOPPER)
				|| block.getType().equals(Material.RAILS)
				|| block.getType().equals(Material.ACTIVATOR_RAIL)
				|| block.getType().equals(Material.DETECTOR_RAIL)
				|| block.getType().equals(Material.POWERED_RAIL)
				|| block.getType().equals(Material.TRIPWIRE_HOOK)
				|| block.getType().equals(Material.TRIPWIRE)
				|| block.getType().equals(Material.SNOW_BLOCK)
				|| block.getType().equals(Material.REDSTONE_TORCH_OFF)
				|| block.getType().equals(Material.REDSTONE_TORCH_ON)
				|| block.getType().equals(Material.DIODE_BLOCK_OFF)
				|| block.getType().equals(Material.DIODE_BLOCK_ON)
				|| block.getType().equals(Material.DIODE)
				|| block.getType().equals(Material.SEEDS)
				|| block.getType().equals(Material.MELON_SEEDS)
				|| block.getType().equals(Material.PUMPKIN_SEEDS)
				|| block.getType().equals(Material.DOUBLE_PLANT)
				|| block.getType().equals(Material.LONG_GRASS)
				|| block.getType().equals(Material.WEB)
				|| block.getType().equals(Material.SNOW)
				|| block.getType().equals(Material.FLOWER_POT)
				|| block.getType().equals(Material.BREWING_STAND)
				|| block.getType().equals(Material.CAULDRON)
				|| block.getType().equals(Material.CACTUS)
				|| block.getType().equals(Material.WATER_LILY)
				|| block.getType().equals(Material.RED_ROSE)
				|| block.getType().equals(Material.ENCHANTMENT_TABLE)
				|| block.getType().equals(Material.ENDER_PORTAL_FRAME)
				|| block.getType().equals(Material.PORTAL)
				|| block.getType().equals(Material.ENDER_PORTAL)
				|| block.getType().equals(Material.ENDER_CHEST)
				|| block.getType().equals(Material.NETHER_FENCE)
				|| block.getType().equals(Material.NETHER_WARTS)
				|| block.getType().equals(Material.REDSTONE_WIRE)
				|| block.getType().equals(Material.LEVER)
				|| block.getType().equals(Material.YELLOW_FLOWER)
				|| block.getType().equals(Material.CROPS)
				|| block.getType().equals(Material.WATER)
				|| block.getType().equals(Material.LAVA)
				|| block.getType().equals(Material.SKULL)
				|| block.getType().equals(Material.TRAPPED_CHEST)
				|| block.getType().equals(Material.FIRE)
				|| block.getType().equals(Material.BROWN_MUSHROOM)
				|| block.getType().equals(Material.RED_MUSHROOM)
				|| block.getType().equals(Material.DEAD_BUSH)
				|| block.getType().equals(Material.SAPLING)
				|| block.getType().equals(Material.TORCH)
				|| block.getType().equals(Material.MELON_STEM)
				|| block.getType().equals(Material.PUMPKIN_STEM)
				|| block.getType().equals(Material.COCOA)
				|| block.getType().equals(Material.BED)
				|| block.getType().equals(Material.BED_BLOCK)
				|| block.getType().equals(Material.PISTON_EXTENSION)
				|| block.getType().equals(Material.PISTON_MOVING_PIECE)
				|| block.getType().equals(Material.IRON_FENCE)
				|| block.getType().equals(Material.THIN_GLASS)
				|| block.getType().equals(Material.STAINED_GLASS_PANE)
				|| block.getType().equals(Material.COBBLE_WALL);
	}

	@SuppressWarnings("deprecation")
	public static boolean isSlime(Block block) {
		return block.getTypeId() == 165;
	}
	public static BoundingBox[] getBlockBoundingBox(Block block) {
		if (collisionBoundingBoxes.containsKey(block.getType())) {
			BoundingBox[] newBox = collisionBoundingBoxes.get(block.getType());

			return new BoundingBox[]{newBox[0].add((float) (newBox[0].minX != -69 ? block.getLocation().getX() : 0), (float) (newBox[0].minY != -69 ? block.getLocation().getY() : 0), (float) (newBox[0].minZ != -69 ? block.getLocation().getZ() : 0), (float) (newBox[0].maxX != -69 ? block.getLocation().getX() : 0), (float) (newBox[0].maxY != -69 ? block.getLocation().getY() : 0), (float) (newBox[0].maxZ != -69 ? block.getLocation().getZ() : 0)), newBox[1].add((float) (newBox[1].minX != -69 ? block.getLocation().getX() : 0), (float) (newBox[1].minY != -69 ? block.getLocation().getY() : 0), (float) (newBox[1].minZ != -69 ? block.getLocation().getZ() : 0), (float) (newBox[1].maxX != -69 ? block.getLocation().getX() : 0), (float) (newBox[1].maxY != -69 ? block.getLocation().getY() : 0), (float) (newBox[1].maxZ != -69 ? block.getLocation().getZ() : 0))};
		}
		BoundingBox box = ReflectionUtil.getBlockBoundingBox(block);

		if (box != null) {
			return new BoundingBox[]{ReflectionUtil.getBlockBoundingBox(block), new BoundingBox(0, 0, 0, 0, 0, 0)};
		}
		return new BoundingBox[]{new BoundingBox(0, 0, 0, 0, 0, 0), new BoundingBox(0, 0, 0, 0, 0, 0)};
	}

	@SuppressWarnings("deprecation")
	public static boolean isDoor(Block block) {
		return block.getType().equals(Material.IRON_DOOR) || block.getType().equals(Material.IRON_DOOR_BLOCK) || block.getType().equals(Material.WOOD_DOOR) || block.getType().equals(Material.WOODEN_DOOR) || block.getTypeId() == 193 || block.getTypeId() == 194 || block.getTypeId() == 195 || block.getTypeId() == 196 || block.getTypeId() == 197 || block.getTypeId() == 324 || block.getTypeId() == 428 || block.getTypeId() == 429 || block.getTypeId() == 430 || block.getTypeId() == 431;
	}

	public static boolean isBed(Block block) {
		return block.getType().equals(Material.BED_BLOCK) || block.getType().equals(Material.BED);
	}

	@SuppressWarnings("deprecation")
	public static boolean isTrapDoor(Block block) {
		return block.getType().equals(Material.TRAP_DOOR) || block.getTypeId() == 167;
	}


	@SuppressWarnings("deprecation")
	public static boolean isFenceGate(Block block) {
		return block.getType().equals(Material.FENCE_GATE) || block.getTypeId() == 183 || block.getTypeId() == 184 || block.getTypeId() == 185 || block.getTypeId() == 186 || block.getTypeId() == 187;
	}


	public static boolean isEdible(Material material) {
		return material.equals(Material.COOKED_BEEF) || material.equals(Material.COOKED_CHICKEN) || material.equals(Material.COOKED_FISH) || material.equals(Material.getMaterial("COOKED_MUTTON")) || material.equals(Material.getMaterial("COOKED_RABBIT")) || material.equals(Material.ROTTEN_FLESH) || material.equals(Material.CARROT_ITEM) || material.equals(Material.CARROT) || material.equals(Material.GOLDEN_APPLE) || material.equals(Material.GOLDEN_CARROT) || material.equals(Material.GRILLED_PORK) || material.equals(Material.RAW_BEEF) || material.equals(Material.RAW_CHICKEN) || material.equals(Material.RAW_FISH) || material.equals(Material.SPIDER_EYE) || material.equals(Material.getMaterial("BEETROOT_SOUP")) || material.equals(Material.MUSHROOM_SOUP) || material.equals(Material.POTATO) || material.equals(Material.POTATO_ITEM) || material.equals(Material.BAKED_POTATO) || material.equals(Material.POISONOUS_POTATO) || material.equals(Material.PUMPKIN_PIE) || material.equals(Material.APPLE) || material.equals(Material.getMaterial("MUTTON")) || material.equals(Material.getMaterial("RABBIT")) || material.equals(Material.MELON) || material.equals(Material.getMaterial("CHORUS_FRUIT")) || material.equals(Material.COOKIE) || material.equals(Material.POTION);
	}

	private void setupCollisionBB() {
		collisionBoundingBoxes.put(Material.BREWING_STAND, new BoundingBox[]{new BoundingBox(0.4375f, 0, 0.4375f, 0.5625f, 0.875f, 0.5625f), new BoundingBox(0, 0, 0, 1f, 0.125f, 1f)});
		Arrays.stream(Material.values()).filter(material -> material.name().contains("FENCE") && !material.name().contains("GATE")).forEach(material -> collisionBoundingBoxes.put(material, new BoundingBox[]{new BoundingBox(-69, 0, -69, -69, 1.5f, -69), new BoundingBox(0, 0, 0, 0, 0, 0)}));
		//Arrays.stream(Material.values()).filter(material -> material.name().contains("STAIR")).forEach(material -> collisionBoundingBoxes.put(material, new BoundingBox[]{new BoundingBox(0, 0, 0, 1, 1f, 1), new BoundingBox(0, 0, 0, 0, 0, 0)}));
		collisionBoundingBoxes.put(Material.STATIONARY_LAVA, new BoundingBox[]{new BoundingBox(0, 0, 0, 1, 1, 1), new BoundingBox(0, 0, 0, 0, 0, 0)});
	}
}
