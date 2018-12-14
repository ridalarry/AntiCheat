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
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.data.DataPlayer;

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
	}

	static {
		allowed.add(Material.SIGN);
		allowed.add(Material.FENCE);
		allowed.add(Material.ANVIL);
		allowed.add(Material.TRAP_DOOR);
		allowed.add(Material.SIGN_POST);
		allowed.add(Material.WALL_SIGN);
		allowed.add(Material.SUGAR_CANE_BLOCK);
		allowed.add(Material.WHEAT);
		allowed.add(Material.POTATO);
		allowed.add(Material.CARROT);
		allowed.add(Material.STEP);
		allowed.add(Material.AIR);
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
		semi.add(Material.IRON_FENCE);
		semi.add(Material.THIN_GLASS);
		semi.add(Material.STAINED_GLASS_PANE);
		semi.add(Material.COBBLE_WALL);
		blockedPearlTypes.add(Material.IRON_FENCE);
		blockedPearlTypes.add(Material.FENCE);
		blockedPearlTypes.add(Material.NETHER_FENCE);
		blockedPearlTypes.add(Material.FENCE_GATE);
		blockedPearlTypes.add(Material.ACACIA_STAIRS);
		blockedPearlTypes.add(Material.BIRCH_WOOD_STAIRS);
		blockedPearlTypes.add(Material.BRICK_STAIRS);
		blockedPearlTypes.add(Material.COBBLESTONE_STAIRS);
		blockedPearlTypes.add(Material.DARK_OAK_STAIRS);
		blockedPearlTypes.add(Material.JUNGLE_WOOD_STAIRS);
		blockedPearlTypes.add(Material.NETHER_BRICK_STAIRS);
		blockedPearlTypes.add(Material.QUARTZ_STAIRS);
		blockedPearlTypes.add(Material.SANDSTONE_STAIRS);
		blockedPearlTypes.add(Material.SMOOTH_STAIRS);
		blockedPearlTypes.add(Material.SPRUCE_WOOD_STAIRS);
		blockedPearlTypes.add(Material.WOOD_STAIRS);
		if (!ServerUtil.isBukkitVerison("1_7")) {
			allowed.add(Material.BANNER);
			allowed.add(Material.IRON_TRAPDOOR);
			allowed.add(Material.WALL_BANNER);
			allowed.add(Material.STANDING_BANNER);
		}
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public static boolean on1_13Spoof(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		DataPlayer data = AntiCheat.getInstance().getDataManager().getData(p);
		if (data != null) {
			if (e.getTo().getY() > e.getFrom().getY()) {
				return true;
			}
			if (DataPlayer.lastNearSlime !=null) {
				if (DataPlayer.lastNearSlime.contains(p.getPlayer().getName().toString())) {
					return true;
				}
			}
			if (data.isLastBlockPlaced_GroundSpoof()) {
				if (TimerUtil.elapsed(data.getLastBlockPlacedTicks(),500L)) {
					data.setLastBlockPlaced_GroundSpoof(false);
				}
				return true;
			}
			if (p.hasPotionEffect(PotionEffectType.LEVITATION)) {
				return true;
			}
			Location to = e.getTo();
			Location from = e.getFrom();
			double diff = to.toVector().distance(from.toVector());
			int dist = PlayerUtil.getDistanceToGround(p);
			if (p.getLocation().add(0,-1.50,0).getBlock().getType() != Material.AIR) {
				data.setGroundSpoofVL(0);
				return true;
			}
			if (e.getTo().getY() > e.getFrom().getY() || PlayerUtil.isOnGround4(p) || VelocityUtil.didTakeVelocity(p)) {
				data.setGroundSpoofVL(0);
				return true;
			}

			if (BlockUtil.isSolid(p.getLocation().getBlock())
					|| PlayerUtil.isNearSolid(p)) {
				return true;
			}
			if (p.isOnGround() && diff > 0.0 && !PlayerUtil.isOnGround(e,p) && dist >= 2 && e.getTo().getY() < e.getFrom().getY()) {
				if (data.getGroundSpoofVL() >= 4) {
					if (data.getAirTicks() >= 10) {
						return true;
					} 
				} else {
					data.setGroundSpoofVL(data.getGroundSpoofVL()+1);
				}
			}
		}
		return false;
	}
	@SuppressWarnings("deprecation")
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
	@SuppressWarnings("deprecation")
	public static boolean Block_1_13 (Block b) {
		return (b.getType().equals(Material.getMaterial("TUBE_CORAL_BLOCK"))
				|| b.getType().equals(Material.getMaterial("BRAIN_CORAL_BLOCK"))
				|| b.getType().equals(Material.getMaterial("BUBBLE_CORAL_BLOCK"))
				|| b.getType().equals(Material.getMaterial("FIRE_CORAL_BLOCK"))
				|| b.getType().equals(Material.getMaterial("HORN_CORAL_BLOCK"))
				|| b.getType().equals(Material.getMaterial("DEAD_TUBE_CORAL_BLOCK"))
				|| b.getType().equals(Material.getMaterial("DEAD_BRAIN_CORAL_BLOCK"))
				|| b.getType().equals(Material.getMaterial("DEAD_BUBBLE_CORAL_BLOCK"))
				|| b.getType().equals(Material.getMaterial("DEAD_FIRE_CORAL_BLOCK"))
				|| b.getType().equals(Material.getMaterial("DEAD_HORN_CORAL_BLOCK"))
				|| b.getType().equals(Material.getMaterial("CORAL_BLOCK"))
				|| b.getType().equals(Material.getMaterial("CAVE_AIR"))
				|| b.getType().equals(Material.getMaterial("VOID_AIR"))
				|| b.getType().equals(Material.getMaterial("BLUE_ICE"))
				|| b.getType().equals(Material.getMaterial("STONE_BUTTON"))
				|| b.getType().equals(Material.getMaterial("OAK_BUTTON"))
				|| b.getType().equals(Material.getMaterial("SPRUCE_BUTTON"))
				|| b.getType().equals(Material.getMaterial("BIRCH_BUTTON"))
				|| b.getType().equals(Material.getMaterial("JUNGLE_BUTTON"))
				|| b.getType().equals(Material.getMaterial("ACACIA_BUTTON"))
				|| b.getType().equals(Material.getMaterial("DARK_OAK_BUTTON"))
				|| b.getType().equals(Material.getMaterial("STONE_PRESSURE_PLATE"))
				|| b.getType().equals(Material.getMaterial("HEAVY_WEIGHTED_PRESSURE_PLATE"))
				|| b.getType().equals(Material.getMaterial("LIGHT_WEIGHTED_PRESSURE_PLATE"))
				|| b.getType().equals(Material.getMaterial("OAK_PRESSURE_PLATE"))
				|| b.getType().equals(Material.getMaterial("SPRUCE_PRESSURE_PLATE"))
				|| b.getType().equals(Material.getMaterial("BIRCH_PRESSURE_PLATE"))
				|| b.getType().equals(Material.getMaterial("JUNGLE_PRESSURE_PLATE"))
				|| b.getType().equals(Material.getMaterial("ACACIA_PRESSURE_PLATE"))
				|| b.getType().equals(Material.getMaterial("DARK_OAK_PRESSURE_PLATE"))
				|| b.getType().equals(Material.getMaterial("IRON_TRAPDOOR"))
				|| b.getType().equals(Material.getMaterial("OAK_TRAPDOOR"))
				|| b.getType().equals(Material.getMaterial("SPRUCE_TRAPDOOR"))
				|| b.getType().equals(Material.getMaterial("BIRCH_TRAPDOOR"))
				|| b.getType().equals(Material.getMaterial("JUNGLE_TRAPDOOR"))
				|| b.getType().equals(Material.getMaterial("ACACIA_TRAPDOOR"))
				|| b.getType().equals(Material.getMaterial("DARK_OAK_TRAPDOOR"))
				|| b.getType().equals(Material.getMaterial("PUMPKIN"))
				|| b.getType().equals(Material.getMaterial("CARVED_PUMPKIN"))
				|| b.getType().equals(Material.getMaterial("TUBE_CORAL"))
				|| b.getType().equals(Material.getMaterial("BRAIN_CORAL"))
				|| b.getType().equals(Material.getMaterial("BUBBLE_CORAL"))
				|| b.getType().equals(Material.getMaterial("FIRE_CORAL"))
				|| b.getType().equals(Material.getMaterial("HORN_CORAL"))
				|| b.getType().equals(Material.getMaterial("DEAD_TUBE_CORAL"))
				|| b.getType().equals(Material.getMaterial("DEAD_BRAIN_CORAL"))
				|| b.getType().equals(Material.getMaterial("DEAD_BUBBLE_CORAL"))
				|| b.getType().equals(Material.getMaterial("DEAD_FIRE_CORAL"))
				|| b.getType().equals(Material.getMaterial("DEAD_HORN_CORAL"))
				|| b.getType().equals(Material.getMaterial("TUBE_CORAL_FAN	"))
				|| b.getType().equals(Material.getMaterial("BUBBLE_CORAL_FAN"))
				|| b.getType().equals(Material.getMaterial("FIRE_CORAL_FAN"))
				|| b.getType().equals(Material.getMaterial("HORN_CORAL_FAN"))
				|| b.getType().equals(Material.getMaterial("DEAD_TUBE_CORAL_FAN"))
				|| b.getType().equals(Material.getMaterial("DEAD_BRAIN_CORAL_FAN"))
				|| b.getType().equals(Material.getMaterial("DEAD_BUBBLE_CORAL_FAN"))
				|| b.getType().equals(Material.getMaterial("DEAD_FIRE_CORAL_FAN"))
				|| b.getType().equals(Material.getMaterial("DEAD_HORN_CORAL_FAN"))
				|| b.getType().equals(Material.getMaterial("TUBE_CORAL_WALL_FAN"))
				|| b.getType().equals(Material.getMaterial("BRAIN_CORAL_WALL_FAN"))
				|| b.getType().equals(Material.getMaterial("BUBBLE_CORAL_WALL_FAN"))
				|| b.getType().equals(Material.getMaterial("FIRE_CORAL_WALL_FAN"))
				|| b.getType().equals(Material.getMaterial("HORN_CORAL_WALL_FAN"))
				|| b.getType().equals(Material.getMaterial("DEAD_TUBE_CORAL_WALL_FAN"))
				|| b.getType().equals(Material.getMaterial("DEAD_BRAIN_CORAL_WALL_FAN"))
				|| b.getType().equals(Material.getMaterial("DEAD_BUBBLE_CORAL_WALL_FAN"))
				|| b.getType().equals(Material.getMaterial("DEAD_FIRE_CORAL_WALL_FAN"))
				|| b.getType().equals(Material.getMaterial("DEAD_HORN_CORAL_WALL_FAN"))
				|| b.getType().equals(Material.getMaterial("BRAIN_CORAL_FAN"))
				|| b.getType().equals(Material.getMaterial("DRIED_KELP_BLOCK"))
				|| b.getType().equals(Material.getMaterial("CONDUIT"))
				|| b.getType().equals(Material.getMaterial("SEAGRASS"))
				|| b.getType().equals(Material.getMaterial("TALL_SEAGRASS"))
				|| b.getType().equals(Material.getMaterial("STRIPPED_OAK_LOG"))
				|| b.getType().equals(Material.getMaterial("STRIPPED_SPRUCE_LOG"))
				|| b.getType().equals(Material.getMaterial("STRIPPED_BIRCH_LOG"))
				|| b.getType().equals(Material.getMaterial("STRIPPED_JUNGLE_LOG"))
				|| b.getType().equals(Material.getMaterial("STRIPPED_ACACIA_LOG"))
				|| b.getType().equals(Material.getMaterial("PRISMARINE_BRICK_SLAB"))
				|| b.getType().equals(Material.getMaterial("PRISMARINE_SLAB"))
				|| b.getType().equals(Material.getMaterial("DARK_PRISMARINE_SLAB"))
				|| b.getType().equals(Material.getMaterial("PRISMARINE_BRICK_STAIRS"))
				|| b.getType().equals(Material.getMaterial("PRISMARINE_STAIRS"))
				|| b.getType().equals(Material.getMaterial("DARK_PRISMARINE_STAIRS"))
				|| b.getType().equals(Material.getMaterial("STRIPPED_DARK_OAK_LOG"))
				|| b.getType().equals(Material.getMaterial("PRISMARINE_BRICK_SLAB"))
				|| b.getType().equals(Material.getMaterial("TURTLE_EGG"))
				|| b.getType().equals(Material.getMaterial("PRISMARINE_SLAB"))
				|| b.getType().equals(Material.getMaterial("DARK_PRISMARINE_SLAB"))
				|| b.getType().equals(Material.getMaterial("PRISMARINE_BRICK_STAIRS"))
				|| b.getType().equals(Material.getMaterial("PRISMARINE_STAIRS"))
				|| b.getType().equals(Material.getMaterial("DARK_PRISMARINE_STAIRS"))
				|| b.getType().equals(Material.getMaterial("STRIPPED_DARK_OAK_LOG"))
				|| b.getType().getId() == 23723
				|| b.getType().getId() == 30618
				|| b.getType().getId() == 15437
				|| b.getType().getId() == 12119
				|| b.getType().getId() == 19958
				|| b.getType().getId() == 28350
				|| b.getType().getId() == 12979
				|| b.getType().getId() == 28220
				|| b.getType().getId() == 5307
				|| b.getType().getId() == 15103
				|| b.getType().getId() == 17422
				|| b.getType().getId() == 13668
				|| b.getType().getId() == 22449
				|| b.getType().getId() == 12279
				|| b.getType().getId() == 6214
				|| b.getType().getId() == 23281
				|| b.getType().getId() == 26934
				|| b.getType().getId() == 25317
				|| b.getType().getId() == 13993
				|| b.getType().getId() == 6214
				|| b.getType().getId() == 22591
				|| b.getType().getId() == 16970
				|| b.getType().getId() == 14875
				|| b.getType().getId() == 20108
				|| b.getType().getId() == 15932
				|| b.getType().getId() == 9664
				|| b.getType().getId() == 11376
				|| b.getType().getId() == 17586
				|| b.getType().getId() == 31375
				|| b.getType().getId() == 17095
				|| b.getType().getId() == 16927
				|| b.getType().getId() == 10289
				|| b.getType().getId() == 32585
				|| b.getType().getId() == 8626
				|| b.getType().getId() == 18343
				|| b.getType().getId() == 10355
				|| b.getType().getId() == 19170
				|| b.getType().getId() == 25833
				|| b.getType().getId() == 23048
				|| b.getType().getId() == 31316
				|| b.getType().getId() == 12464
				|| b.getType().getId() == 29151
				|| b.getType().getId() == 19511
				|| b.getType().getId() == 18028
				|| b.getType().getId() == 9116
				|| b.getType().getId() == 30583
				|| b.getType().getId() == 8365
				|| b.getType().getId() == 5755
				|| b.getType().getId() == 19929
				|| b.getType().getId() == 10795
				|| b.getType().getId() == 11112
				|| b.getType().getId() == 13610
				|| b.getType().getId() == 17628
				|| b.getType().getId() == 26150
				|| b.getType().getId() == 17322
				|| b.getType().getId() == 27073
				|| b.getType().getId() == 11387
				|| b.getType().getId() == 25282
				|| b.getType().getId() == 22685
				|| b.getType().getId() == 20382
				|| b.getType().getId() == 20100
				|| b.getType().getId() == 28883
				|| b.getType().getId() == 5128
				|| b.getType().getId() == 23718
				|| b.getType().getId() == 18453
				|| b.getType().getId() == 23375
				|| b.getType().getId() == 27550
				|| b.getType().getId() == 13849
				|| b.getType().getId() == 12966
				|| b.getType().getId() == 5148
				|| b.getType().getId() == 23942
				|| b.getType().getId() == 27189
				|| b.getType().getId() == 20523
				|| b.getType().getId() == 6140
				|| b.getType().getId() == 8838
				|| b.getType().getId() == 15476
				|| b.getType().getId() == 18167
				|| b.getType().getId() == 26672
				|| b.getType().getId() == 32101
				|| b.getType().getId() == 31323
				|| b.getType().getId() == 7577
				|| b.getType().getId() == 15445
				|| b.getType().getId() == 19217
				|| b.getType().getId() == 26511
				|| b.getType().getId() == 6492

				);
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


	public static boolean isLava(Block block) {
		if (block != null && (block.getType() == Material.LAVA || block.getType() == Material.STATIONARY_LAVA)) {
			return true;
		}
		return false;
	}

	public static boolean isWater(Block block) {
		if (block != null && (block.getType() == Material.WATER || block.getType() == Material.STATIONARY_WATER)) {
			return true;
		}
		return false;
	}
	@SuppressWarnings("deprecation")
	public static boolean isSlab(Block block) {
		return block.getType().equals(Material.WOOD_STEP)
				|| block.getType().equals(Material.STEP)
				|| block.getType().equals(Material.getMaterial("PRISMARINE_BRICK_SLAB"))
				|| block.getType().equals(Material.getMaterial("PRISMARINE_SLAB"))
				|| block.getType().equals(Material.getMaterial("DARK_PRISMARINE_SLAB"))
				|| block.getType().getId() == 182
				|| block.getType().getId() == 44
				|| block.getType().getId() == 126
				|| block.getType().getId() == 205;
	}

	@SuppressWarnings("deprecation")
	public static boolean isAllowed(Block block) {
		return block.getType().getId() == 165
				|| block.getType().equals(Material.CAULDRON)
				|| block.getType().equals(Material.BREWING_STAND)
				|| block.getType().equals(Material.HOPPER)
				|| block.getType().equals(Material.CARPET)
				|| isStair(block)
				|| isPiston(block);
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
				|| block.getType().equals(Material.getMaterial("PRISMARINE_BRICK_STAIRS"))
				|| block.getType().equals(Material.getMaterial("PRISMARINE_STAIRS"))
				|| block.getType().equals(Material.getMaterial("DARK_PRISMARINE_STAIRS"))
				|| block.getType().getId()==180
				|| block.getType().getId() == 203) {
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
	public static boolean isShulker(Block block) {
		return (block.getType().getId() == 219
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
				|| block.getType().getId() == 250);
	}
	public static boolean isBar(Block block) {
		return block.getType().equals(Material.IRON_FENCE);

	}
	public static boolean isWeb(Block block) {
		return block.getType().equals(Material.WEB);
	}
	@SuppressWarnings("deprecation")
	public static boolean isFence(Block block) {
		return block.getType().equals(Material.FENCE)
				|| block.getType().getId() == 85
				|| block.getType().getId() == 139
				|| block.getType().getId() == 113
				|| block.getType().getId() == 188
				|| block.getType().getId() == 189
				|| block.getType().getId() == 190
				|| block.getType().getId() == 191
				|| block.getType().getId() == 192
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
		if (block != null && isSolid(block.getType().getId())) {
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
		return block.getType().getId() == 70
				|| block.getType().getId() == 72
				|| block.getType().getId() == 147
				|| block.getType().getId() == 148;
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

	static String[] Blocks_1_13 = { "tube_coral_block", "brain_coral_block", "bubble_coral_block", "fire_coral_block", "horn_coral_block",
			"dead_tube_coral_block", "dead_brain_coral_block", "dead_bubble_coral_block", "dead_fire_coral_block", "dead_horn_coral_block",
			"coral_block", "cave_air", "void_air", "blue_ice", "stone_button", "oak_button", "spruce_button", "birch_button", 
			"jungle_button", "acacia_button", "dark_oak_button", "stone_pressure_plate", "heavy_weighted_pressure_plate", 
			"light_weighted_pressure_plate", "oak_pressure_plate", "spruce_pressure_plate", "birch_pressure_plate", 
			"jungle_pressure_plate", "acacia_pressure_plate", "dark_oak_pressure_plate", "iron_trapdoor", "oak_trapdoor", 
			"spruce_trapdoor", "birch_trapdoor", "jungle_trapdoor", "acacia_trapdoor", "dark_oak_trapdoor", "pumpkin", "carved_pumpkin", 
			"tube_coral", "brain_coral", "bubble_coral", "fire_coral", "horn_coral", "dead_tube_coral", "dead_brain_coral", 
			"dead_bubble_coral", "dead_fire_coral", "dead_horn_coral", "tube_coral_fan	", "bubble_coral_fan", "fire_coral_fan", 
			"horn_coral_fan", "dead_tube_coral_fan", "dead_brain_coral_fan", "dead_bubble_coral_fan", "dead_fire_coral_fan", 
			"dead_horn_coral_fan", "tube_coral_wall_fan", "brain_coral_wall_fan", "bubble_coral_wall_fan", "fire_coral_wall_fan", 
			"horn_coral_wall_fan", "dead_tube_coral_wall_fan", "dead_brain_coral_wall_fan", "dead_bubble_coral_wall_fan", 
			"dead_fire_coral_wall_fan", "dead_horn_coral_wall_fan", "brain_coral_fan", "dried_kelp_block", "conduit", "seagrass", 
			"tall_seagrass", "stripped_oak_log", "stripped_spruce_log", "stripped_birch_log", "stripped_jungle_log", 
			"stripped_acacia_log", "prismarine_brick_slab", "prismarine_slab", "dark_prismarine_slab", "prismarine_brick_stairs", 
			"prismarine_stairs", "dark_prismarine_stairs", "stripped_dark_oak_log", "prismarine_brick_slab", "turtle_egg", 
			"prismarine_slab", "dark_prismarine_slab", "prismarine_brick_stairs", "prismarine_stairs", "dark_prismarine_stairs", 
	"stripped_dark_oak_log"};

	public static boolean isHalfBlock(Block block) {
		Material type = block.getType();
		for (String types : HalfBlocksArray) {
			if (type.toString().toLowerCase().contains(types)) {
				return true;
			}
		}
		return false;
	}
	public static boolean B_1_13(Block block) {
		Material type = block.getType();
		for (String types : Blocks_1_13) {
			if (type.toString().toLowerCase().equals(types)) {
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
				|| block.getType().getId() == 425
				|| block.getType().getId() == 167
				|| block.getType().getId() == 177
				|| block.getType().getId() == 176
				|| block.getType().getId() == 165
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
		return block.getType().getId() == 165;
	}
	@SuppressWarnings("deprecation")
	public static boolean isGrass(Block block) {
		return block.getType().getId() == 2
				|| block.getType().getId() == 3;
	}
	@SuppressWarnings("deprecation")
	public static boolean isSign(Block block) {
		return block.getType().getId() == 63
				|| block.getType().getId() == 68
				|| block.getType().getId() == 323;
	}

	@SuppressWarnings("deprecation")
	public static boolean isLog(Block block) {
		return block.getType().getId() == 17
				|| block.getType().getId() == 162;
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
		return block.getType().equals(Material.IRON_DOOR) || block.getType().equals(Material.IRON_DOOR_BLOCK) || block.getType().equals(Material.WOOD_DOOR) || block.getType().equals(Material.WOODEN_DOOR) || block.getType().getId() == 193 || block.getType().getId() == 194 || block.getType().getId() == 195 || block.getType().getId() == 196 || block.getType().getId() == 197 || block.getType().getId() == 324 || block.getType().getId() == 428 || block.getType().getId() == 429 || block.getType().getId() == 430 || block.getType().getId() == 431;
	}

	public static boolean isBed(Block block) {
		return block.getType().equals(Material.BED_BLOCK) || block.getType().equals(Material.BED);
	}

	@SuppressWarnings("deprecation")
	public static boolean isTrapDoor(Block block) {
		return block.getType().equals(Material.TRAP_DOOR) || block.getType().getId() == 167;
	}


	@SuppressWarnings("deprecation")
	public static boolean isFenceGate(Block block) {
		return block.getType().equals(Material.FENCE_GATE) || block.getType().getId() == 183 || block.getType().getId() == 184 || block.getType().getId() == 185 || block.getType().getId() == 186 || block.getType().getId() == 187;
	}


	public static boolean isEdible(Material material) {
		return material.equals(Material.COOKED_BEEF) || material.equals(Material.COOKED_CHICKEN) || material.equals(Material.COOKED_FISH) || material.equals(Material.getMaterial("COOKED_MUTTON")) || material.equals(Material.getMaterial("COOKED_RABBIT")) || material.equals(Material.ROTTEN_FLESH) || material.equals(Material.CARROT_ITEM) || material.equals(Material.CARROT) || material.equals(Material.GOLDEN_APPLE) || material.equals(Material.GOLDEN_CARROT) || material.equals(Material.GRILLED_PORK) || material.equals(Material.RAW_BEEF) || material.equals(Material.RAW_CHICKEN) || material.equals(Material.RAW_FISH) || material.equals(Material.SPIDER_EYE) || material.equals(Material.getMaterial("BEETROOT_SOUP")) || material.equals(Material.MUSHROOM_SOUP) || material.equals(Material.POTATO) || material.equals(Material.POTATO_ITEM) || material.equals(Material.BAKED_POTATO) || material.equals(Material.POISONOUS_POTATO) || material.equals(Material.PUMPKIN_PIE) || material.equals(Material.APPLE) || material.equals(Material.getMaterial("MUTTON")) || material.equals(Material.getMaterial("RABBIT")) || material.equals(Material.MELON) || material.equals(Material.getMaterial("CHORUS_FRUIT")) || material.equals(Material.COOKIE) || material.equals(Material.POTION);
	}

	private void setupCollisionBB() {
		collisionBoundingBoxes.put(Material.BREWING_STAND, new BoundingBox[]{new BoundingBox(0.4375f, 0, 0.4375f, 0.5625f, 0.875f, 0.5625f), new BoundingBox(0, 0, 0, 1f, 0.125f, 1f)});
		Arrays.stream(Material.values()).filter(material -> material.name().contains("FENCE") && !material.name().contains("GATE")).forEach(material -> collisionBoundingBoxes.put(material, new BoundingBox[]{new BoundingBox(-69, 0, -69, -69, 1.5f, -69), new BoundingBox(0, 0, 0, 0, 0, 0)}));
		collisionBoundingBoxes.put(Material.STATIONARY_LAVA, new BoundingBox[]{new BoundingBox(0, 0, 0, 1, 1, 1), new BoundingBox(0, 0, 0, 0, 0, 0)});
	}
}