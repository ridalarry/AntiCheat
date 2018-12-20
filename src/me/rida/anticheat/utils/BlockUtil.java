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
import org.bukkit.util.Vector;

public class BlockUtil {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static HashSet<Byte> blockPassSet = new HashSet();
	public static Map<Material, BoundingBox[]> collisionBoundingBoxes;
	public static List<Material> allowed = new ArrayList<>();
	public static List<Material> semi = new ArrayList<>();
	public static List<Material> blockedPearlTypes = new ArrayList<>();
	public BlockUtil() {
		collisionBoundingBoxes = new HashMap<>();
		setupCollisionBB();
		allowed = new ArrayList<>();allowed.add(Material.AIR);
		allowed.add(Material.SIGN);
		allowed.add(Material.WALL_SIGN);
		allowed.add(Material.WHEAT);
		allowed.add(Material.POTATO);
		allowed.add(Material.CARROT);
		allowed.add(Material.SOUL_SAND);
		allowed.add(Material.LADDER);
		allowed.add(Material.CHEST);
		allowed.add(Material.WATER);
		allowed.add(Material.LAVA);
		allowed.add(Material.DAYLIGHT_DETECTOR);
		allowed.add(Material.STONE_BUTTON);
		allowed.add(Material.HOPPER);
		allowed.add(Material.ACTIVATOR_RAIL);
		allowed.add(Material.DETECTOR_RAIL);
		allowed.add(Material.POWERED_RAIL);
		allowed.add(Material.TRIPWIRE_HOOK);
		allowed.add(Material.TRIPWIRE);
		allowed.add(Material.SNOW_BLOCK);
		allowed.add(Material.MELON_SEEDS);
		allowed.add(Material.PUMPKIN_SEEDS);
		allowed.add(Material.SNOW);
		allowed.add(Material.FLOWER_POT);
		allowed.add(Material.BREWING_STAND);
		allowed.add(Material.CAULDRON);
		allowed.add(Material.CACTUS);
		allowed.add(Material.ENDER_CHEST);
		allowed.add(Material.REDSTONE_WIRE);
		allowed.add(Material.LEVER);
		allowed.add(Material.TRAPPED_CHEST);
		allowed.add(Material.FIRE);
		allowed.add(Material.BROWN_MUSHROOM);
		allowed.add(Material.RED_MUSHROOM);
		allowed.add(Material.DEAD_BUSH);
		allowed.add(Material.TORCH);
		allowed.add(Material.MELON_STEM);
		allowed.add(Material.PUMPKIN_STEM);
		allowed.add(Material.COCOA);
		allowed.add(Material.ANVIL);
		allowed.add(Material.ACACIA_TRAPDOOR);
		allowed.add(Material.BIRCH_TRAPDOOR);
		allowed.add(Material.DARK_OAK_TRAPDOOR);
		allowed.add(Material.IRON_TRAPDOOR);
		allowed.add(Material.JUNGLE_TRAPDOOR);
		allowed.add(Material.OAK_TRAPDOOR);
		allowed.add(Material.SPRUCE_TRAPDOOR);
		allowed.add(Material.ACACIA_FENCE);
		allowed.add(Material.BIRCH_FENCE);
		allowed.add(Material.PLAYER_HEAD);
		allowed.add(Material.PLAYER_WALL_HEAD);
		allowed.add(Material.CREEPER_HEAD);
		allowed.add(Material.CREEPER_WALL_HEAD);
		allowed.add(Material.ZOMBIE_HEAD);
		allowed.add(Material.ZOMBIE_WALL_HEAD);
		allowed.add(Material.DRAGON_HEAD);
		allowed.add(Material.DRAGON_WALL_HEAD);
		allowed.add(Material.DARK_OAK_FENCE);
		allowed.add(Material.JUNGLE_FENCE);
		allowed.add(Material.NETHER_BRICK_FENCE);
		allowed.add(Material.SPRUCE_FENCE);
		allowed.add(Material.LEGACY_BANNER);
		allowed.add(Material.LEGACY_WALL_BANNER);
		allowed.add(Material.LEGACY_STANDING_BANNER);
		allowed.add(Material.LEGACY_SIGN_POST);
		allowed.add(Material.LEGACY_SUGAR_CANE_BLOCK);
		allowed.add(Material.SUGAR_CANE);
		allowed.add(Material.LEGACY_STEP);
		allowed.add(Material.BIRCH_SLAB);
		allowed.add(Material.ACACIA_SLAB);
		allowed.add(Material.BRICK_SLAB);
		allowed.add(Material.COBBLESTONE_SLAB);
		allowed.add(Material.DARK_OAK_SLAB);
		allowed.add(Material.DARK_PRISMARINE_SLAB);
		allowed.add(Material.JUNGLE_SLAB);
		allowed.add(Material.NETHER_BRICK_SLAB);
		allowed.add(Material.OAK_SLAB);
		allowed.add(Material.PETRIFIED_OAK_SLAB);
		allowed.add(Material.PRISMARINE_BRICK_SLAB);
		allowed.add(Material.PRISMARINE_SLAB);
		allowed.add(Material.PURPUR_SLAB);
		allowed.add(Material.QUARTZ_SLAB);
		allowed.add(Material.RED_SANDSTONE_SLAB);
		allowed.add(Material.SANDSTONE_SLAB);
		allowed.add(Material.SPRUCE_SLAB);
		allowed.add(Material.STONE_BRICK_SLAB);
		allowed.add(Material.STONE_SLAB);
		allowed.add(Material.WHITE_CARPET);
		allowed.add(Material.ORANGE_CARPET);
		allowed.add(Material.MAGENTA_CARPET);
		allowed.add(Material.LIGHT_BLUE_CARPET);
		allowed.add(Material.YELLOW_CARPET);
		allowed.add(Material.LIME_CARPET);
		allowed.add(Material.PINK_CARPET);
		allowed.add(Material.GRAY_CARPET);
		allowed.add(Material.LIGHT_GRAY_CARPET);
		allowed.add(Material.CYAN_CARPET);
		allowed.add(Material.PURPLE_CARPET);
		allowed.add(Material.BLUE_CARPET);
		allowed.add(Material.BROWN_CARPET);
		allowed.add(Material.GREEN_CARPET);
		allowed.add(Material.RED_CARPET);
		allowed.add(Material.BLACK_CARPET);
		allowed.add(Material.ACACIA_PRESSURE_PLATE);
		allowed.add(Material.BIRCH_PRESSURE_PLATE);
		allowed.add(Material.DARK_OAK_PRESSURE_PLATE);
		allowed.add(Material.HEAVY_WEIGHTED_PRESSURE_PLATE);
		allowed.add(Material.JUNGLE_PRESSURE_PLATE);
		allowed.add(Material.LIGHT_WEIGHTED_PRESSURE_PLATE);
		allowed.add(Material.OAK_PRESSURE_PLATE);
		allowed.add(Material.SPRUCE_PRESSURE_PLATE);
		allowed.add(Material.STONE_PRESSURE_PLATE);
		allowed.add(Material.LEGACY_STATIONARY_WATER);
		allowed.add(Material.LEGACY_STATIONARY_LAVA);
		allowed.add(Material.LEGACY_REDSTONE_COMPARATOR);
		allowed.add(Material.LEGACY_REDSTONE_COMPARATOR_OFF);
		allowed.add(Material.LEGACY_REDSTONE_COMPARATOR_ON);
		allowed.add(Material.ACACIA_BUTTON);
		allowed.add(Material.BIRCH_BUTTON);
		allowed.add(Material.DARK_OAK_BUTTON);
		allowed.add(Material.JUNGLE_BUTTON);
		allowed.add(Material.OAK_BUTTON);
		allowed.add(Material.SPRUCE_BUTTON);
		allowed.add(Material.RAIL);
		allowed.add(Material.REDSTONE_TORCH);
		allowed.add(Material.REDSTONE_WALL_TORCH);
		allowed.add(Material.LEGACY_REDSTONE_TORCH_OFF);
		allowed.add(Material.LEGACY_REDSTONE_TORCH_ON);
		allowed.add(Material.LEGACY_DIODE_BLOCK_OFF);
		allowed.add(Material.LEGACY_DIODE_BLOCK_ON);
		allowed.add(Material.LEGACY_DIODE);
		allowed.add(Material.WHEAT_SEEDS);
		allowed.add(Material.LEGACY_DOUBLE_PLANT);
		allowed.add(Material.LEGACY_LONG_GRASS);
		allowed.add(Material.COBWEB);
		allowed.add(Material.LILY_PAD);
		allowed.add(Material.ENCHANTING_TABLE);
		allowed.add(Material.END_PORTAL_FRAME);
		allowed.add(Material.END_PORTAL);
		allowed.add(Material.NETHER_PORTAL);
		allowed.add(Material.POPPY);
		allowed.add(Material.SUNFLOWER);
		allowed.add(Material.DANDELION);
		allowed.add(Material.BLUE_ORCHID);
		allowed.add(Material.ALLIUM);
		allowed.add(Material.AZURE_BLUET);
		allowed.add(Material.RED_TULIP);
		allowed.add(Material.ORANGE_TULIP);
		allowed.add(Material.WHITE_TULIP);
		allowed.add(Material.PINK_TULIP);
		allowed.add(Material.OXEYE_DAISY);
		allowed.add(Material.POTTED_POPPY);
		allowed.add(Material.POTTED_DANDELION);
		allowed.add(Material.POTTED_BLUE_ORCHID);
		allowed.add(Material.POTTED_ALLIUM);
		allowed.add(Material.POTTED_AZURE_BLUET);
		allowed.add(Material.POTTED_RED_TULIP);
		allowed.add(Material.POTTED_ORANGE_TULIP);
		allowed.add(Material.POTTED_WHITE_TULIP);
		allowed.add(Material.POTTED_PINK_TULIP);
		allowed.add(Material.POTTED_OXEYE_DAISY);
		allowed.add(Material.WHITE_BED);
		allowed.add(Material.ORANGE_BED);
		allowed.add(Material.MAGENTA_BED);
		allowed.add(Material.LIGHT_BLUE_BED);
		allowed.add(Material.YELLOW_BED);
		allowed.add(Material.LIME_BED);
		allowed.add(Material.PINK_BED);
		allowed.add(Material.GRAY_BED);
		allowed.add(Material.LIGHT_GRAY_BED);
		allowed.add(Material.CYAN_BED);
		allowed.add(Material.PURPLE_BED);
		allowed.add(Material.BLUE_BED);
		allowed.add(Material.BROWN_BED);
		allowed.add(Material.GREEN_BED);
		allowed.add(Material.RED_BED);
		allowed.add(Material.BLACK_BED);
		allowed.add(Material.LEGACY_SKULL);
		allowed.add(Material.LEGACY_SKULL_ITEM);
		allowed.add(Material.SKELETON_SKULL);
		allowed.add(Material.SKELETON_WALL_SKULL);
		allowed.add(Material.WITHER_SKELETON_SKULL);
		allowed.add(Material.WITHER_SKELETON_WALL_SKULL);
		allowed.add(Material.NETHER_WART);
		allowed.add(Material.NETHER_WART_BLOCK);
		allowed.add(Material.ACACIA_SAPLING);
		allowed.add(Material.BIRCH_SAPLING);
		allowed.add(Material.DARK_OAK_SAPLING);
		allowed.add(Material.JUNGLE_SAPLING);
		allowed.add(Material.SPRUCE_SAPLING);
		allowed.add(Material.LEGACY_SAPLING);
		allowed.add(Material.POTTED_ACACIA_SAPLING);
		allowed.add(Material.POTTED_BIRCH_SAPLING);
		allowed.add(Material.POTTED_DARK_OAK_SAPLING);
		allowed.add(Material.POTTED_JUNGLE_SAPLING);
		allowed.add(Material.POTTED_SPRUCE_SAPLING);
		allowed.add(Material.LEGACY_BED_BLOCK);
		allowed.add(Material.LEGACY_PISTON_EXTENSION);
		allowed.add(Material.LEGACY_PISTON_MOVING_PIECE);
		blockedPearlTypes.add(Material.GLASS_PANE);
		blockedPearlTypes.add(Material.BLACK_STAINED_GLASS_PANE);
		blockedPearlTypes.add(Material.BLUE_STAINED_GLASS_PANE);
		blockedPearlTypes.add(Material.RED_STAINED_GLASS_PANE);
		blockedPearlTypes.add(Material.YELLOW_STAINED_GLASS_PANE);
		blockedPearlTypes.add(Material.LIME_STAINED_GLASS_PANE);
		blockedPearlTypes.add(Material.CYAN_STAINED_GLASS_PANE);
		blockedPearlTypes.add(Material.WHITE_STAINED_GLASS_PANE);
		blockedPearlTypes.add(Material.ORANGE_STAINED_GLASS_PANE);
		blockedPearlTypes.add(Material.MAGENTA_STAINED_GLASS_PANE);
		blockedPearlTypes.add(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
		blockedPearlTypes.add(Material.PINK_STAINED_GLASS_PANE);
		blockedPearlTypes.add(Material.GRAY_STAINED_GLASS_PANE);
		blockedPearlTypes.add(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
		blockedPearlTypes.add(Material.PURPLE_STAINED_GLASS_PANE);
		blockedPearlTypes.add(Material.BROWN_STAINED_GLASS_PANE);
		blockedPearlTypes.add(Material.GREEN_STAINED_GLASS_PANE);
		blockedPearlTypes.add(Material.IRON_BARS);
		blockedPearlTypes.add(Material.ACACIA_FENCE);
		blockedPearlTypes.add(Material.BIRCH_FENCE);
		blockedPearlTypes.add(Material.DARK_OAK_FENCE);
		blockedPearlTypes.add(Material.JUNGLE_FENCE);
		blockedPearlTypes.add(Material.NETHER_BRICK_FENCE);
		blockedPearlTypes.add(Material.SPRUCE_FENCE);
		blockedPearlTypes.add(Material.ACACIA_FENCE_GATE);
		blockedPearlTypes.add(Material.BIRCH_FENCE_GATE);
		blockedPearlTypes.add(Material.DARK_OAK_FENCE_GATE);
		blockedPearlTypes.add(Material.JUNGLE_FENCE_GATE);
		blockedPearlTypes.add(Material.SPRUCE_FENCE_GATE);
		blockedPearlTypes.add(Material.ACACIA_STAIRS);
		blockedPearlTypes.add(Material.BIRCH_STAIRS);
		blockedPearlTypes.add(Material.BRICK_STAIRS);
		blockedPearlTypes.add(Material.COBBLESTONE_STAIRS);
		blockedPearlTypes.add(Material.DARK_OAK_STAIRS);
		blockedPearlTypes.add(Material.JUNGLE_STAIRS);
		blockedPearlTypes.add(Material.NETHER_BRICK_STAIRS);
		blockedPearlTypes.add(Material.QUARTZ_STAIRS);
		blockedPearlTypes.add(Material.SANDSTONE_STAIRS);
		blockedPearlTypes.add(Material.LEGACY_SMOOTH_STAIRS);
		blockedPearlTypes.add(Material.SPRUCE_STAIRS);
		blockedPearlTypes.add(Material.OAK_STAIRS);
		semi.add(Material.IRON_BARS);
		semi.add(Material.LEGACY_THIN_GLASS);
		semi.add(Material.LEGACY_STAINED_GLASS_PANE);
		semi.add(Material.COBBLESTONE_WALL);
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
	public static boolean isSolid2(byte block) {
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
	public static Block getBlockBehindPlayer(Player player) {
		// Won't get NPEs from me.
		if (player == null) {
			return null;
		}
		// Get player's location, but at head height. Add 1 to y.
		Location location = player.getLocation().add(0, 1, 0);
		// Get the player's direction and invert it on the x and z axis to get the opposite direction.
		Vector direction = location.getDirection().multiply(new Vector(-1, 0, -1));

		// Return the block at the location opposite of the direction the player is looking, 1 block forward.
		return player.getWorld().getBlockAt(location.add(direction));
	}

	public static boolean isClimbableBlock(Block block) {
		return block.getType() == Material.LADDER || block.getType() == Material.VINE;
	}


	public static boolean isIce(Block block) {
		return block.getType().equals(Material.ICE)
				|| block.getType().equals(Material.PACKED_ICE)
				|| block.getType().equals(Material.BLUE_ICE)
				|| block.getType().equals(Material.FROSTED_ICE);
	}

	@SuppressWarnings("deprecation")
	public static boolean isLiquid(Block block) {
		if (block != null && (block.getType() == Material.WATER || block.getType() == Material.LEGACY_STATIONARY_WATER || block.getType() == Material.LAVA || block.getType() == Material.LEGACY_STATIONARY_LAVA)) {
			return true;
		}
		return false;
	}


	@SuppressWarnings("deprecation")
	public static boolean isLava(Block block) {
		if (block != null && (block.getType() == Material.LAVA || block.getType() == Material.LEGACY_STATIONARY_LAVA)) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public static boolean isWater(Block block) {
		if (block != null && (block.getType() == Material.WATER || block.getType() == Material.LEGACY_STATIONARY_WATER)) {
			return true;
		}
		return false;
	}
	@SuppressWarnings("deprecation")
	public static boolean isSlab(Block block) {
		return block.getType().equals(Material.LEGACY_STEP)
				|| block.getType().equals(Material.PRISMARINE_BRICK_SLAB)
				|| block.getType().equals(Material.PRISMARINE_SLAB)
				|| block.getType().equals(Material.DARK_PRISMARINE_SLAB)
				|| block.getType().equals(Material.LEGACY_STEP)
				|| block.getType().equals(Material.BIRCH_SLAB)
				|| block.getType().equals(Material.ACACIA_SLAB)
				|| block.getType().equals(Material.BRICK_SLAB)
				|| block.getType().equals(Material.COBBLESTONE_SLAB)
				|| block.getType().equals(Material.DARK_OAK_SLAB)
				|| block.getType().equals(Material.JUNGLE_SLAB)
				|| block.getType().equals(Material.NETHER_BRICK_SLAB)
				|| block.getType().equals(Material.OAK_SLAB)
				|| block.getType().equals(Material.PETRIFIED_OAK_SLAB)
				|| block.getType().equals(Material.PURPUR_SLAB)
				|| block.getType().equals(Material.QUARTZ_SLAB)
				|| block.getType().equals(Material.RED_SANDSTONE_SLAB)
				|| block.getType().equals(Material.SANDSTONE_SLAB)
				|| block.getType().equals(Material.SPRUCE_SLAB)
				|| block.getType().getId() == 182
				|| block.getType().getId() == 44
				|| block.getType().getId() == 126
				|| block.getType().getId() == 205
				|| block.getType().equals(Material.STONE_BRICK_SLAB);
	}

	public static boolean isAllowed(Block block) {
		return block.getType().equals(Material.SLIME_BLOCK)
				|| block.getType().equals(Material.CAULDRON)
				|| block.getType().equals(Material.BREWING_STAND)
				|| block.getType().equals(Material.HOPPER)
				|| block.getType().equals(Material.WHITE_CARPET)
				|| block.getType().equals(Material.ORANGE_CARPET)
				|| block.getType().equals(Material.MAGENTA_CARPET)
				|| block.getType().equals(Material.LIGHT_BLUE_CARPET)
				|| block.getType().equals(Material.YELLOW_CARPET)
				|| block.getType().equals(Material.LIME_CARPET)
				|| block.getType().equals(Material.PINK_CARPET)
				|| block.getType().equals(Material.GRAY_CARPET)
				|| block.getType().equals(Material.LIGHT_GRAY_CARPET)
				|| block.getType().equals(Material.CYAN_CARPET)
				|| block.getType().equals(Material.PURPLE_CARPET)
				|| block.getType().equals(Material.BLUE_CARPET)
				|| block.getType().equals(Material.BROWN_CARPET)
				|| block.getType().equals(Material.GREEN_CARPET)
				|| block.getType().equals(Material.RED_CARPET)
				|| block.getType().equals(Material.BLACK_CARPET)
				|| isStair(block)
				|| isPiston(block);
	}


	@SuppressWarnings("deprecation")
	public static boolean isStair(Block block) {
		if(block.getType().equals(Material.ACACIA_STAIRS)
				|| block.getType().equals(Material.BRICK_STAIRS)
				|| block.getType().equals(Material.COBBLESTONE_STAIRS)
				|| block.getType().equals(Material.DARK_OAK_STAIRS)
				|| block.getType().equals(Material.NETHER_BRICK_STAIRS)
				|| block.getType().equals(Material.QUARTZ_STAIRS)
				|| block.getType().equals(Material.SANDSTONE_STAIRS)
				|| block.getType().equals(Material.PRISMARINE_BRICK_STAIRS)
				|| block.getType().equals(Material.PRISMARINE_STAIRS)
				|| block.getType().equals(Material.DARK_PRISMARINE_STAIRS)
				|| block.getType().equals(Material.BIRCH_STAIRS)
				|| block.getType().equals(Material.JUNGLE_STAIRS)
				|| block.getType().equals(Material.LEGACY_SMOOTH_STAIRS)
				|| block.getType().equals(Material.OAK_STAIRS)
				|| block.getType().equals(Material.SPRUCE_STAIRS)
				|| block.getType() == Material.RED_SANDSTONE_STAIRS
				|| block.getType().getId() == 203
				|| block.getType().getId()==180
				|| block.getType() == Material.PURPUR_STAIRS) {
			return true;
		}
		return false;
	}
	@SuppressWarnings("deprecation")
	public static boolean isPiston(Block block) {
		return block.getType().equals(Material.LEGACY_PISTON_MOVING_PIECE)
				|| block.getType().equals(Material.LEGACY_PISTON_EXTENSION)
				|| block.getType().equals(Material.LEGACY_PISTON_BASE)
				|| block.getType().equals(Material.LEGACY_PISTON_STICKY_BASE);
	}
	public static boolean isChest(Block block) {
		return block.getType().equals(Material.CHEST)|| block.getType().equals(Material.ENDER_CHEST)|| block.getType().equals(Material.TRAPPED_CHEST) || isShulker(block);

	}
	public static boolean isShulker(Block block) {
		return (block.getType().equals(Material.SHULKER_BOX)
				|| block.getType().equals(Material.WHITE_SHULKER_BOX)
				|| block.getType().equals(Material.ORANGE_SHULKER_BOX)
				|| block.getType().equals(Material.MAGENTA_SHULKER_BOX)
				|| block.getType().equals(Material.LIGHT_BLUE_SHULKER_BOX)
				|| block.getType().equals(Material.YELLOW_SHULKER_BOX)
				|| block.getType().equals(Material.LIME_SHULKER_BOX)
				|| block.getType().equals(Material.PINK_SHULKER_BOX)
				|| block.getType().equals(Material.GRAY_SHULKER_BOX)
				|| block.getType().equals(Material.LIGHT_GRAY_SHULKER_BOX)
				|| block.getType().equals(Material.CYAN_SHULKER_BOX)
				|| block.getType().equals(Material.PURPLE_SHULKER_BOX)
				|| block.getType().equals(Material.BLUE_SHULKER_BOX)
				|| block.getType().equals(Material.BROWN_SHULKER_BOX)
				|| block.getType().equals(Material.GREEN_SHULKER_BOX)
				|| block.getType().equals(Material.RED_SHULKER_BOX)
				|| block.getType().equals(Material.BLACK_SHULKER_BOX)
				|| block.getType().equals(Material.SHULKER_SHELL))
				|| block.getType().getId() == 219
				|| block.getType().getId() == 220
				|| block.getType().getId() == 221
				|| block.getType().getId() == 222
				|| block.getType().getId() == 223
				|| block.getType().getId() == 224
				|| block.getType().getId() == 225
				|| block.getType().getId() == 226
				|| block.getType().getId() == 227
				|| block.getType().getId() == 228
				|| block.getType().getId() == 229
				|| block.getType().getId() == 230
				|| block.getType().getId() == 231
				|| block.getType().getId() == 232
				|| block.getType().getId() == 233
				|| block.getType().getId() == 234
				|| block.getType().getId() == 250;
	}
	public static boolean isBar(Block block) {
		return block.getType().equals(Material.IRON_BARS);

	}
	public static boolean isWeb(Block block) {
		return block.getType().equals(Material.COBWEB);
	}
	public static boolean isFence(Block block) {
		return block.getType().equals(Material.ACACIA_FENCE)
				|| block.getType().equals(Material.BIRCH_FENCE)
				|| block.getType().equals(Material.DARK_OAK_FENCE)
				|| block.getType().equals(Material.JUNGLE_FENCE)
				|| block.getType().equals(Material.NETHER_BRICK_FENCE)
				|| block.getType().equals(Material.SPRUCE_FENCE);

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
		if (block != null && isSolid2(block.getType().getId())) {
			return true;
		}
		return false;
	}

	public static boolean isSolid2(int n) {
		return isSolid2((int)n);
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
	public static boolean isPressure(Block block) {
		return 
				block.getType().equals(Material.ACACIA_PRESSURE_PLATE)
				|| block.getType().equals(Material.BIRCH_PRESSURE_PLATE)
				|| block.getType().equals(Material.DARK_OAK_PRESSURE_PLATE)
				|| block.getType().equals(Material.HEAVY_WEIGHTED_PRESSURE_PLATE)
				|| block.getType().equals(Material.JUNGLE_PRESSURE_PLATE)
				|| block.getType().equals(Material.LIGHT_WEIGHTED_PRESSURE_PLATE)
				|| block.getType().equals(Material.OAK_PRESSURE_PLATE)
				|| block.getType().equals(Material.SPRUCE_PRESSURE_PLATE)
				|| block.getType().equals(Material.STONE_PRESSURE_PLATE);
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
	@SuppressWarnings("deprecation")
	public static boolean isNearPistion(Player p) {
		boolean out = false;
		for (Block b : getNearbyBlocks(p.getLocation(), 1)) {
			if (b.getType() == Material.LEGACY_PISTON_BASE || b.getType() == Material.LEGACY_PISTON_MOVING_PIECE || b.getType() == Material.LEGACY_PISTON_STICKY_BASE || b.getType() == Material.LEGACY_PISTON_EXTENSION) {
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

	public static boolean isNearLava(Player p) {
		boolean out = false;
		for (Block b : getNearbyBlocks(p.getLocation(), 2)) {
			if (isLava(b)) {
				out = true;
			}
		}
		return out;
	}

	public static boolean isNearWater(Player p) {
		boolean out = false;
		for (Block b : getNearbyBlocks(p.getLocation(), 2)) {
			if (isWater(b)) {
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
				|| block.getType().equals(Material.SIGN)
				|| block.getType().equals(Material.ANVIL)
				|| block.getType().equals(Material.ACACIA_TRAPDOOR)
				|| block.getType().equals(Material.BIRCH_TRAPDOOR)
				|| block.getType().equals(Material.DARK_OAK_TRAPDOOR)
				|| block.getType().equals(Material.IRON_TRAPDOOR)
				|| block.getType().equals(Material.JUNGLE_TRAPDOOR)
				|| block.getType().equals(Material.OAK_TRAPDOOR)
				|| block.getType().equals(Material.SPRUCE_TRAPDOOR)
				|| block.getType().equals(Material.ACACIA_FENCE)
				|| block.getType().equals(Material.BIRCH_FENCE)
				|| block.getType().equals(Material.PLAYER_HEAD)
				|| block.getType().equals(Material.PLAYER_WALL_HEAD)
				|| block.getType().equals(Material.CREEPER_HEAD)
				|| block.getType().equals(Material.CREEPER_WALL_HEAD)
				|| block.getType().equals(Material.ZOMBIE_HEAD)
				|| block.getType().equals(Material.ZOMBIE_WALL_HEAD)
				|| block.getType().equals(Material.DRAGON_HEAD)
				|| block.getType().equals(Material.DRAGON_WALL_HEAD)
				|| block.getType().equals(Material.DARK_OAK_FENCE)
				|| block.getType().equals(Material.JUNGLE_FENCE)
				|| block.getType().equals(Material.NETHER_BRICK_FENCE)
				|| block.getType().equals(Material.SPRUCE_FENCE)
				|| block.getType().equals(Material.LEGACY_BANNER)
				|| block.getType().equals(Material.IRON_TRAPDOOR)
				|| block.getType().equals(Material.LEGACY_WALL_BANNER)
				|| block.getType().equals(Material.LEGACY_STANDING_BANNER)
				|| block.getType().equals(Material.LEGACY_SIGN_POST)
				|| block.getType().equals(Material.WALL_SIGN)
				|| block.getType().equals(Material.LEGACY_SUGAR_CANE_BLOCK)
				|| block.getType().equals(Material.SUGAR_CANE)
				|| block.getType().equals(Material.WHEAT)
				|| block.getType().equals(Material.POTATO)
				|| block.getType().equals(Material.CARROT)
				|| block.getType().equals(Material.LEGACY_STEP)
				|| block.getType().equals(Material.BIRCH_SLAB)
				|| block.getType().equals(Material.ACACIA_SLAB)
				|| block.getType().equals(Material.BRICK_SLAB)
				|| block.getType().equals(Material.COBBLESTONE_SLAB)
				|| block.getType().equals(Material.DARK_OAK_SLAB)
				|| block.getType().equals(Material.DARK_PRISMARINE_SLAB)
				|| block.getType().equals(Material.JUNGLE_SLAB)
				|| block.getType().equals(Material.NETHER_BRICK_SLAB)
				|| block.getType().equals(Material.OAK_SLAB)
				|| block.getType().equals(Material.PETRIFIED_OAK_SLAB)
				|| block.getType().equals(Material.PRISMARINE_BRICK_SLAB)
				|| block.getType().equals(Material.PRISMARINE_SLAB)
				|| block.getType().equals(Material.PURPUR_SLAB)
				|| block.getType().equals(Material.QUARTZ_SLAB)
				|| block.getType().equals(Material.RED_SANDSTONE_SLAB)
				|| block.getType().equals(Material.SANDSTONE_SLAB)
				|| block.getType().equals(Material.SPRUCE_SLAB)
				|| block.getType().equals(Material.STONE_BRICK_SLAB)
				|| block.getType().equals(Material.STONE_SLAB)
				|| block.getType().equals(Material.AIR)
				|| block.getType().equals(Material.SOUL_SAND)
				|| block.getType().equals(Material.WHITE_CARPET)
				|| block.getType().equals(Material.ORANGE_CARPET)
				|| block.getType().equals(Material.MAGENTA_CARPET)
				|| block.getType().equals(Material.LIGHT_BLUE_CARPET)
				|| block.getType().equals(Material.YELLOW_CARPET)
				|| block.getType().equals(Material.LIME_CARPET)
				|| block.getType().equals(Material.PINK_CARPET)
				|| block.getType().equals(Material.GRAY_CARPET)
				|| block.getType().equals(Material.LIGHT_GRAY_CARPET)
				|| block.getType().equals(Material.CYAN_CARPET)
				|| block.getType().equals(Material.PURPLE_CARPET)
				|| block.getType().equals(Material.BLUE_CARPET)
				|| block.getType().equals(Material.BROWN_CARPET)
				|| block.getType().equals(Material.GREEN_CARPET)
				|| block.getType().equals(Material.RED_CARPET)
				|| block.getType().equals(Material.BLACK_CARPET)
				|| block.getType().equals(Material.ACACIA_PRESSURE_PLATE)
				|| block.getType().equals(Material.BIRCH_PRESSURE_PLATE)
				|| block.getType().equals(Material.DARK_OAK_PRESSURE_PLATE)
				|| block.getType().equals(Material.HEAVY_WEIGHTED_PRESSURE_PLATE)
				|| block.getType().equals(Material.JUNGLE_PRESSURE_PLATE)
				|| block.getType().equals(Material.LIGHT_WEIGHTED_PRESSURE_PLATE)
				|| block.getType().equals(Material.OAK_PRESSURE_PLATE)
				|| block.getType().equals(Material.SPRUCE_PRESSURE_PLATE)
				|| block.getType().equals(Material.STONE_PRESSURE_PLATE)
				|| block.getType().equals(Material.LADDER)
				|| block.getType().equals(Material.CHEST)
				|| block.getType().equals(Material.WATER)
				|| block.getType().equals(Material.LEGACY_STATIONARY_WATER)
				|| block.getType().equals(Material.LAVA)
				|| block.getType().equals(Material.LEGACY_STATIONARY_LAVA)
				|| block.getType().equals(Material.LEGACY_REDSTONE_COMPARATOR)
				|| block.getType().equals(Material.LEGACY_REDSTONE_COMPARATOR_OFF)
				|| block.getType().equals(Material.LEGACY_REDSTONE_COMPARATOR_ON)
				|| block.getType().equals(Material.DAYLIGHT_DETECTOR)
				|| block.getType().equals(Material.STONE_BUTTON)
				|| block.getType().equals(Material.ACACIA_BUTTON)
				|| block.getType().equals(Material.BIRCH_BUTTON)
				|| block.getType().equals(Material.DARK_OAK_BUTTON)
				|| block.getType().equals(Material.JUNGLE_BUTTON)
				|| block.getType().equals(Material.OAK_BUTTON)
				|| block.getType().equals(Material.SPRUCE_BUTTON)
				|| block.getType().equals(Material.HOPPER)
				|| block.getType().equals(Material.RAIL)
				|| block.getType().equals(Material.ACTIVATOR_RAIL)
				|| block.getType().equals(Material.DETECTOR_RAIL)
				|| block.getType().equals(Material.POWERED_RAIL)
				|| block.getType().equals(Material.TRIPWIRE_HOOK)
				|| block.getType().equals(Material.TRIPWIRE)
				|| block.getType().equals(Material.SNOW_BLOCK)
				|| block.getType().equals(Material.REDSTONE_TORCH)
				|| block.getType().equals(Material.REDSTONE_WALL_TORCH)
				|| block.getType().equals(Material.LEGACY_REDSTONE_TORCH_OFF)
				|| block.getType().equals(Material.LEGACY_REDSTONE_TORCH_ON)
				|| block.getType().equals(Material.LEGACY_DIODE_BLOCK_OFF)
				|| block.getType().equals(Material.LEGACY_DIODE_BLOCK_ON)
				|| block.getType().equals(Material.LEGACY_DIODE)
				|| block.getType().equals(Material.WHEAT_SEEDS)
				|| block.getType().equals(Material.MELON_SEEDS)
				|| block.getType().equals(Material.PUMPKIN_SEEDS)
				|| block.getType().equals(Material.LEGACY_DOUBLE_PLANT)
				|| block.getType().equals(Material.LEGACY_LONG_GRASS)
				|| block.getType().equals(Material.COBWEB)
				|| block.getType().equals(Material.SNOW)
				|| block.getType().equals(Material.FLOWER_POT)
				|| block.getType().equals(Material.BREWING_STAND)
				|| block.getType().equals(Material.CAULDRON)
				|| block.getType().equals(Material.CACTUS)
				|| block.getType().equals(Material.LILY_PAD)
				|| block.getType().equals(Material.ENCHANTING_TABLE)
				|| block.getType().equals(Material.END_PORTAL_FRAME)
				|| block.getType().equals(Material.END_PORTAL)
				|| block.getType().equals(Material.NETHER_PORTAL)
				|| block.getType().equals(Material.POPPY)
				|| block.getType().equals(Material.SUNFLOWER)
				|| block.getType().equals(Material.DANDELION)
				|| block.getType().equals(Material.BLUE_ORCHID)
				|| block.getType().equals(Material.ALLIUM)
				|| block.getType().equals(Material.AZURE_BLUET)
				|| block.getType().equals(Material.RED_TULIP)
				|| block.getType().equals(Material.ORANGE_TULIP)
				|| block.getType().equals(Material.WHITE_TULIP)
				|| block.getType().equals(Material.PINK_TULIP)
				|| block.getType().equals(Material.OXEYE_DAISY)
				|| block.getType().equals(Material.POTTED_POPPY)
				|| block.getType().equals(Material.POTTED_DANDELION)
				|| block.getType().equals(Material.POTTED_BLUE_ORCHID)
				|| block.getType().equals(Material.POTTED_ALLIUM)
				|| block.getType().equals(Material.POTTED_AZURE_BLUET)
				|| block.getType().equals(Material.POTTED_RED_TULIP)
				|| block.getType().equals(Material.POTTED_ORANGE_TULIP)
				|| block.getType().equals(Material.POTTED_WHITE_TULIP)
				|| block.getType().equals(Material.POTTED_PINK_TULIP)
				|| block.getType().equals(Material.POTTED_OXEYE_DAISY)
				|| block.getType().equals(Material.WHITE_BED)
				|| block.getType().equals(Material.ORANGE_BED)
				|| block.getType().equals(Material.MAGENTA_BED)
				|| block.getType().equals(Material.LIGHT_BLUE_BED)
				|| block.getType().equals(Material.YELLOW_BED)
				|| block.getType().equals(Material.LIME_BED)
				|| block.getType().equals(Material.PINK_BED)
				|| block.getType().equals(Material.GRAY_BED)
				|| block.getType().equals(Material.LIGHT_GRAY_BED)
				|| block.getType().equals(Material.CYAN_BED)
				|| block.getType().equals(Material.PURPLE_BED)
				|| block.getType().equals(Material.BLUE_BED)
				|| block.getType().equals(Material.BROWN_BED)
				|| block.getType().equals(Material.GREEN_BED)
				|| block.getType().equals(Material.RED_BED)
				|| block.getType().equals(Material.BLACK_BED)
				|| block.getType().equals(Material.LEGACY_SKULL)
				|| block.getType().equals(Material.LEGACY_SKULL_ITEM)
				|| block.getType().equals(Material.SKELETON_SKULL)
				|| block.getType().equals(Material.SKELETON_WALL_SKULL)
				|| block.getType().equals(Material.WITHER_SKELETON_SKULL)
				|| block.getType().equals(Material.WITHER_SKELETON_WALL_SKULL)
				|| block.getType().equals(Material.ENDER_CHEST)
				|| block.getType().equals(Material.NETHER_BRICK_FENCE)
				|| block.getType().equals(Material.NETHER_WART)
				|| block.getType().equals(Material.NETHER_WART_BLOCK)
				|| block.getType().equals(Material.REDSTONE_WIRE)
				|| block.getType().equals(Material.LEVER)
				|| block.getType().equals(Material.WATER)
				|| block.getType().equals(Material.LAVA)
				|| block.getType().equals(Material.TRAPPED_CHEST)
				|| block.getType().equals(Material.FIRE)
				|| block.getType().equals(Material.BROWN_MUSHROOM)
				|| block.getType().equals(Material.RED_MUSHROOM)
				|| block.getType().equals(Material.DEAD_BUSH)
				|| block.getType().equals(Material.ACACIA_SAPLING)
				|| block.getType().equals(Material.BIRCH_SAPLING)
				|| block.getType().equals(Material.DARK_OAK_SAPLING)
				|| block.getType().equals(Material.JUNGLE_SAPLING)
				|| block.getType().equals(Material.SPRUCE_SAPLING)
				|| block.getType().equals(Material.LEGACY_SAPLING)
				|| block.getType().equals(Material.POTTED_ACACIA_SAPLING)
				|| block.getType().equals(Material.POTTED_BIRCH_SAPLING)
				|| block.getType().equals(Material.POTTED_DARK_OAK_SAPLING)
				|| block.getType().equals(Material.POTTED_JUNGLE_SAPLING)
				|| block.getType().equals(Material.POTTED_SPRUCE_SAPLING)
				|| block.getType().equals(Material.TORCH)
				|| block.getType().equals(Material.MELON_STEM)
				|| block.getType().equals(Material.PUMPKIN_STEM)
				|| block.getType().equals(Material.COCOA)
				|| block.getType().equals(Material.LEGACY_BED_BLOCK)
				|| block.getType().equals(Material.LEGACY_PISTON_EXTENSION)
				|| block.getType().equals(Material.LEGACY_PISTON_MOVING_PIECE)
				|| block.getType().equals(Material.IRON_BARS)
				|| block.getType().equals(Material.COBBLESTONE_WALL)
				|| block.getType().equals(Material.GLASS_PANE)
				|| block.getType().equals(Material.BLACK_STAINED_GLASS_PANE)
				|| block.getType().equals(Material.BLUE_STAINED_GLASS_PANE)
				|| block.getType().equals(Material.RED_STAINED_GLASS_PANE)
				|| block.getType().equals(Material.YELLOW_STAINED_GLASS_PANE)
				|| block.getType().equals(Material.LIME_STAINED_GLASS_PANE)
				|| block.getType().equals(Material.CYAN_STAINED_GLASS_PANE)
				|| block.getType().equals(Material.WHITE_STAINED_GLASS_PANE)
				|| block.getType().equals(Material.ORANGE_STAINED_GLASS_PANE)
				|| block.getType().equals(Material.MAGENTA_STAINED_GLASS_PANE)
				|| block.getType().equals(Material.LIGHT_BLUE_STAINED_GLASS_PANE)
				|| block.getType().equals(Material.PINK_STAINED_GLASS_PANE)
				|| block.getType().equals(Material.GRAY_STAINED_GLASS_PANE)
				|| block.getType().equals(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
				|| block.getType().equals(Material.PURPLE_STAINED_GLASS_PANE)
				|| block.getType().equals(Material.BROWN_STAINED_GLASS_PANE)
				|| block.getType().equals(Material.GREEN_STAINED_GLASS_PANE);
	}

	public static boolean isSlime(Block block) {
		return block.getType() == Material.SLIME_BLOCK;
	}
	public static boolean isGrass(Block block) {
		return block.getType() == Material.GRASS
				|| block.getType() == Material.DIRT
				|| block.getType() == Material.COARSE_DIRT
				|| block.getType() == Material.PODZOL;
	}
	@SuppressWarnings("deprecation")
	public static boolean isSign(Block block) {
		return block.getType() == Material.SIGN
				|| block.getType() == Material.WALL_SIGN
				|| block.getType() == Material.LEGACY_SIGN_POST;
	}

	public static boolean isLog(Block block) {
		return block.getType() == Material.ACACIA_LOG
				|| block.getType() == Material.OAK_LOG
				|| block.getType() == Material.BIRCH_LOG
				|| block.getType() == Material.SPRUCE_LOG
				|| block.getType() == Material.DARK_OAK_LOG
				|| block.getType() == Material.JUNGLE_LOG
				|| block.getType() == Material.STRIPPED_ACACIA_LOG
				|| block.getType() == Material.STRIPPED_OAK_LOG
				|| block.getType() == Material.STRIPPED_BIRCH_LOG
				|| block.getType() == Material.STRIPPED_SPRUCE_LOG
				|| block.getType() == Material.STRIPPED_DARK_OAK_LOG
				|| block.getType() == Material.STRIPPED_JUNGLE_LOG;
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
		return block.getType().equals(Material.IRON_DOOR) 
				|| block.getType().equals(Material.LEGACY_IRON_DOOR_BLOCK) 
				|| block.getType().equals(Material.LEGACY_WOOD_DOOR) 
				|| block.getType().equals(Material.IRON_DOOR) 
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
				|| block.getType().equals(Material.LEGACY_JUNGLE_DOOR_ITEM)
				|| block.getType().getId() == 193 
				|| block.getType().getId() == 194 
				|| block.getType().getId() == 195 
				|| block.getType().getId() == 196 
				|| block.getType().getId() == 197 
				|| block.getType().getId() == 324 
				|| block.getType().getId() == 428 
				|| block.getType().getId() == 429 
				|| block.getType().getId() == 430 
				|| block.getType().getId() == 431;
	}

	@SuppressWarnings("deprecation")
	public static boolean isBed(Block block) {
		return block.getType().equals(Material.LEGACY_BED_BLOCK) 
				|| block.getType().equals(Material.LEGACY_BED)
				|| block.getType().equals(Material.WHITE_BED)
				|| block.getType().equals(Material.ORANGE_BED)
				|| block.getType().equals(Material.MAGENTA_BED)
				|| block.getType().equals(Material.LIGHT_BLUE_BED)
				|| block.getType().equals(Material.YELLOW_BED)
				|| block.getType().equals(Material.LIME_BED)
				|| block.getType().equals(Material.PINK_BED)
				|| block.getType().equals(Material.GRAY_BED)
				|| block.getType().equals(Material.LIGHT_GRAY_BED)
				|| block.getType().equals(Material.CYAN_BED)
				|| block.getType().equals(Material.PURPLE_BED)
				|| block.getType().equals(Material.BLUE_BED)
				|| block.getType().equals(Material.BROWN_BED)
				|| block.getType().equals(Material.GREEN_BED)
				|| block.getType().equals(Material.RED_BED)
				|| block.getType().equals(Material.BLACK_BED);
	}

	public static boolean isTrapDoor(Block block) {
		return block.getType().equals(Material.ACACIA_TRAPDOOR)
				|| block.getType().equals(Material.BIRCH_TRAPDOOR)
				|| block.getType().equals(Material.DARK_OAK_TRAPDOOR)
				|| block.getType().equals(Material.IRON_TRAPDOOR)
				|| block.getType().equals(Material.JUNGLE_TRAPDOOR)
				|| block.getType().equals(Material.OAK_TRAPDOOR)
				|| block.getType().equals(Material.SPRUCE_TRAPDOOR)
				|| block.getType().getId() == 167
				|| block.getType().getId() == 183 
				|| block.getType().getId() == 184 
				|| block.getType().getId() == 185 
				|| block.getType().getId() == 186 
				|| block.getType().getId() == 187;
	}




	public static boolean isFenceGate(Block block) {
		return (block.getType() == Material.ACACIA_FENCE_GATE
				|| block.getType() == Material.BIRCH_FENCE_GATE
				|| block.getType() == Material.DARK_OAK_FENCE_GATE
				|| block.getType() == Material.JUNGLE_FENCE_GATE
				|| block.getType() == Material.SPRUCE_FENCE_GATE);
	}


	@SuppressWarnings("deprecation")
	public static boolean isEdible(Material material) {
		return material.equals(Material.COOKED_BEEF)
				|| material.equals(Material.COOKED_CHICKEN)
				|| material.equals(Material.COOKED_MUTTON)
				|| material.equals(Material.COOKED_RABBIT)
				|| material.equals(Material.ROTTEN_FLESH)
				|| material.equals(Material.CARROT)
				|| material.equals(Material.GOLDEN_APPLE)
				|| material.equals(Material.GOLDEN_CARROT)
				|| material.equals(Material.SPIDER_EYE)
				|| material.equals(Material.POTATO)
				|| material.equals(Material.BAKED_POTATO)
				|| material.equals(Material.POISONOUS_POTATO)
				|| material.equals(Material.PUMPKIN_PIE)
				|| material.equals(Material.APPLE)
				|| material.equals(Material.MUTTON)
				|| material.equals(Material.RABBIT)
				|| material.equals(Material.MELON)
				|| material.equals(Material.COOKIE)
				|| material.equals(Material.COOKED_COD)
				|| material.equals(Material.COOKED_SALMON)
				|| material.equals(Material.LEGACY_CARROT_ITEM)
				|| material.equals(Material.CARROTS)
				|| material.equals(Material.ENCHANTED_GOLDEN_APPLE)
				|| material.equals(Material.COOKED_PORKCHOP)
				|| material.equals(Material.BEEF)
				|| material.equals(Material.CHICKEN)
				|| material.equals(Material.LEGACY_RAW_FISH)
				|| material.equals(Material.COD)
				|| material.equals(Material.SALMON)
				|| material.equals(Material.PUFFERFISH)
				|| material.equals(Material.MUSHROOM_STEM)
				|| material.equals(Material.MUSHROOM_STEW)
				|| material.equals(Material.LEGACY_MUSHROOM_SOUP)
				|| material.equals(Material.POTATOES)
				|| material.equals(Material.LEGACY_POTATO_ITEM)
				|| material.equals(Material.BEETROOT_SOUP)
				|| material.equals(Material.CHORUS_FRUIT)
				|| material.equals(Material.COOKED_CHICKEN)
				|| material.equals(Material.COOKED_COD)
				|| material.equals(Material.COOKED_SALMON)
				|| material.equals(Material.COOKED_MUTTON)
				|| material.equals(Material.COOKED_RABBIT)
				|| material.equals(Material.ROTTEN_FLESH)
				|| material.equals(Material.LEGACY_CARROT_ITEM)
				|| material.equals(Material.CARROTS)
				|| material.equals(Material.CARROT)
				|| material.equals(Material.GOLDEN_APPLE)
				|| material.equals(Material.ENCHANTED_GOLDEN_APPLE)
				|| material.equals(Material.GOLDEN_CARROT)
				|| material.equals(Material.COOKED_PORKCHOP)
				|| material.equals(Material.BEEF)
				|| material.equals(Material.CHICKEN)
				|| material.equals(Material.LEGACY_RAW_FISH)
				|| material.equals(Material.COD)
				|| material.equals(Material.SALMON)
				|| material.equals(Material.PUFFERFISH)
				|| material.equals(Material.SPIDER_EYE)
				|| material.equals(Material.BEETROOT_SOUP)
				|| material.equals(Material.MUSHROOM_STEM)
				|| material.equals(Material.MUSHROOM_STEW)
				|| material.equals(Material.LEGACY_MUSHROOM_SOUP)
				|| material.equals(Material.POTATO)
				|| material.equals(Material.POTATOES)
				|| material.equals(Material.LEGACY_POTATO_ITEM)
				|| material.equals(Material.BAKED_POTATO)
				|| material.equals(Material.POISONOUS_POTATO)
				|| material.equals(Material.PUMPKIN_PIE)
				|| material.equals(Material.APPLE)
				|| material.equals(Material.MUTTON)
				|| material.equals(Material.RABBIT)
				|| material.equals(Material.MELON)
				|| material.equals(Material.CHORUS_FRUIT)
				|| material.equals(Material.COOKIE)
				|| material.equals(Material.POTION);
	}

	@SuppressWarnings("deprecation")
	private void setupCollisionBB() {
		collisionBoundingBoxes.put(Material.BREWING_STAND, new BoundingBox[]{new BoundingBox(0.4375f, 0, 0.4375f, 0.5625f, 0.875f, 0.5625f), new BoundingBox(0, 0, 0, 1f, 0.125f, 1f)});
		Arrays.stream(Material.values()).filter(material -> material.name().contains("FENCE") && !material.name().contains("GATE")).forEach(material -> collisionBoundingBoxes.put(material, new BoundingBox[]{new BoundingBox(-69, 0, -69, -69, 1.5f, -69), new BoundingBox(0, 0, 0, 0, 0, 0)}));
		collisionBoundingBoxes.put(Material.LAVA, new BoundingBox[]{new BoundingBox(0, 0, 0, 1, 1, 1), new BoundingBox(0, 0, 0, 0, 0, 0)});
		collisionBoundingBoxes.put(Material.LEGACY_STATIONARY_LAVA, new BoundingBox[]{new BoundingBox(0, 0, 0, 1, 1, 1), new BoundingBox(0, 0, 0, 0, 0, 0)});
	}
}