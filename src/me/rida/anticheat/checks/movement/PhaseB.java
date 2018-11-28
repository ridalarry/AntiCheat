package me.rida.anticheat.checks.movement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.utils.BlockUtil;
import me.rida.anticheat.utils.BoundingBox;
import me.rida.anticheat.utils.MathUtil;
import me.rida.anticheat.utils.ReflectionUtil;


public class PhaseB extends Check implements Listener {
	public static List<Material> allowed = new ArrayList<>();
	public static List<Material> semi = new ArrayList<>();
	public static Set<UUID> teleported = new HashSet<>();
	public static Map<UUID, Location> lastLocation = new HashMap<>();

	@SuppressWarnings("unused")
	private ImmutableSet<Material> blockedPearlTypes = Sets.immutableEnumSet(Material.THIN_GLASS,
			Material.IRON_FENCE, Material.FENCE, Material.NETHER_FENCE, Material.FENCE_GATE, Material.ACACIA_STAIRS,
			Material.BIRCH_WOOD_STAIRS, Material.BRICK_STAIRS, Material.COBBLESTONE_STAIRS, Material.DARK_OAK_STAIRS,
			Material.JUNGLE_WOOD_STAIRS, Material.NETHER_BRICK_STAIRS, Material.QUARTZ_STAIRS,
			Material.SANDSTONE_STAIRS, Material.SMOOTH_STAIRS, Material.SPRUCE_WOOD_STAIRS, Material.WOOD_STAIRS);

	static {
		allowed.add(Material.SIGN);
		allowed.add(Material.FENCE);
		allowed.add(Material.ANVIL);
		allowed.add(Material.TRAP_DOOR);
		allowed.add(Material.BANNER);
		allowed.add(Material.IRON_TRAPDOOR);
		allowed.add(Material.WALL_BANNER);
		allowed.add(Material.STANDING_BANNER);
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
	}
	private Map<Player, Long> lastDoorSwing;

	public PhaseB(AntiCheat AntiCheat) {
		super("PhaseB", "Phase", CheckType.Combat, true, false, false, 40, 3, 600000L, AntiCheat);
		lastDoorSwing = new WeakHashMap<>();
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPhase(PlayerMoveEvent e) {
		Player player = e.getPlayer();

		if (player.getAllowFlight()
				|| player.getVehicle() != null
				|| MathUtil.elapsed(lastDoorSwing.getOrDefault(player, 0L)) < 500
				|| BlockUtil.isNearPistion(player)) {
			return;
		}
		if (BlockUtil.isNearLava(player) && BlockUtil.isNearWater(player)) {
			return;
		}
		if (e.getFrom().distance(e.getTo()) > AntiCheat.Instance.maxMove) {
			e.setTo(e.getFrom());
			return;
		}

		float minX = (float) Math.min(e.getFrom().getX(), e.getTo().getX()), minY = (float) Math.min(e.getFrom().getY(), e.getTo().getY()), minZ = (float) Math.min(e.getFrom().getZ(), e.getTo().getZ()),
				maxX = (float) Math.max(e.getFrom().getX(), e.getTo().getX()), maxY = (float) Math.max(e.getFrom().getY(), e.getTo().getY()), maxZ = (float) Math.max(e.getFrom().getZ(), e.getTo().getZ());

		Object box = new BoundingBox(minX, minY, minZ, maxX, maxY, maxZ).add(0f, 0f, 0f, 0f, 1.8f, 0f).toAxisAlignedBB();

		if(ReflectionUtil.getCollidingBlocks(e.getPlayer(), box).size() > 0) {
			getAntiCheat().logCheat(this, player, "[1]", "(Type: B)");
		}

	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if ((BlockUtil.isDoor(event.getClickedBlock())
					|| BlockUtil.isFenceGate(event.getClickedBlock())
					|| BlockUtil.isTrapDoor(event.getClickedBlock()))
					&& !event.isCancelled()) {
				lastDoorSwing.put(event.getPlayer(), System.currentTimeMillis());
			}
		}
	}
}