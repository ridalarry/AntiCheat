package me.rida.anticheat.checks.movement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.material.Directional;
import org.bukkit.material.Door;
import org.bukkit.material.Gate;
import org.bukkit.material.TrapDoor;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.other.PearlGlitchEvent;
import me.rida.anticheat.other.PearlGlitchType;
import me.rida.anticheat.utils.Color;
import me.rida.anticheat.utils.CheatUtil;
import me.rida.anticheat.utils.BlockUtil;

public class PhaseA extends Check {
	public static List<Material> allowed = new ArrayList<Material>();
	public static List<Material> semi = new ArrayList<Material>();
	public static Set<UUID> teleported = new HashSet<UUID>();
	public static final Map<UUID, Location> lastLocation = new HashMap<UUID, Location>();

	private final ImmutableSet<Material> blockedPearlTypes = Sets.immutableEnumSet(Material.getMaterial("THIN_GLASS"),
			Material.getMaterial("IRON_FENCE"), Material.getMaterial("FENCE"), Material.getMaterial("NETHER_FENCE"), Material.getMaterial("FENCE_GATE"), Material.getMaterial("ACACIA_STAIRS"),
			Material.getMaterial("BIRCH_WOOD_STAIRS"), Material.BRICK_STAIRS, Material.COBBLESTONE_STAIRS, Material.DARK_OAK_STAIRS,
			Material.getMaterial("JUNGLE_WOOD_STAIRS"), Material.getMaterial("NETHER_BRICK_STAIRS"), Material.getMaterial("QUARTZ_STAIRS"),
			Material.SANDSTONE_STAIRS, Material.getMaterial("SMOOTH_STAIRS"), Material.getMaterial("SPRUCE_WOOD_STAIRS"), Material.getMaterial("WOOD_STAIRS"));

	static {
		allowed.add(Material.SIGN);
		allowed.add(Material.getMaterial("FENCE"));
		allowed.add(Material.ANVIL);
		allowed.add(Material.getMaterial("TRAP_DOOR"));
		allowed.add(Material.getMaterial("BANNER"));
		allowed.add(Material.getMaterial("IRON_TRAPDOOR"));
		allowed.add(Material.getMaterial("WALL_BANNER"));
		allowed.add(Material.getMaterial("STANDING_BANNER"));
		allowed.add(Material.getMaterial("SIGN_POST"));
		allowed.add(Material.getMaterial("WALL_SIGN"));
		allowed.add(Material.getMaterial("SUGAR_CANE_BLOCK"));
		allowed.add(Material.getMaterial("WHEAT"));
		allowed.add(Material.getMaterial("POTATO"));
		allowed.add(Material.getMaterial("CARROT"));
		allowed.add(Material.getMaterial("STEP"));
		allowed.add(Material.AIR);
		allowed.add(Material.getMaterial("WOOD_STEP"));
		allowed.add(Material.getMaterial("SOUL_SAND"));
		allowed.add(Material.getMaterial("CARPET"));
		allowed.add(Material.getMaterial("STONE_PLATE"));
		allowed.add(Material.getMaterial("WOOD_PLATE"));
		allowed.add(Material.getMaterial("LADDER"));
		allowed.add(Material.getMaterial("CHEST"));
		allowed.add(Material.getMaterial("WATER"));
		allowed.add(Material.getMaterial("STATIONARY_WATER"));
		allowed.add(Material.getMaterial("LAVA"));
		allowed.add(Material.getMaterial("STATIONARY_LAVA"));
		allowed.add(Material.getMaterial("REDSTONE_COMPARATOR"));
		allowed.add(Material.getMaterial("REDSTONE_COMPARATOR_OFF"));
		allowed.add(Material.getMaterial("REDSTONE_COMPARATOR_ON"));
		allowed.add(Material.getMaterial("IRON_PLATE"));
		allowed.add(Material.getMaterial("GOLD_PLATE"));
		allowed.add(Material.getMaterial("DAYLIGHT_DETECTOR"));
		allowed.add(Material.getMaterial("STONE_BUTTON"));
		allowed.add(Material.getMaterial("WOOD_BUTTON"));
		allowed.add(Material.getMaterial("HOPPER"));
		allowed.add(Material.getMaterial("RAILS"));
		allowed.add(Material.getMaterial("ACTIVATOR_RAIL"));
		allowed.add(Material.getMaterial("DETECTOR_RAIL"));
		allowed.add(Material.getMaterial("POWERED_RAIL"));
		allowed.add(Material.getMaterial("TRIPWIRE_HOOK"));
		allowed.add(Material.getMaterial("TRIPWIRE"));
		allowed.add(Material.getMaterial("SNOW_BLOCK"));
		allowed.add(Material.getMaterial("REDSTONE_TORCH_OFF"));
		allowed.add(Material.getMaterial("REDSTONE_TORCH_ON"));
		allowed.add(Material.getMaterial("DIODE_BLOCK_OFF"));
		allowed.add(Material.getMaterial("DIODE_BLOCK_ON"));
		allowed.add(Material.getMaterial("DIODE"));
		allowed.add(Material.getMaterial("SEEDS"));
		allowed.add(Material.getMaterial("MELON_SEEDS"));
		allowed.add(Material.getMaterial("PUMPKIN_SEEDS"));
		allowed.add(Material.getMaterial("DOUBLE_PLANT"));
		allowed.add(Material.getMaterial("LONG_GRASS"));
		allowed.add(Material.getMaterial("WEB"));
		allowed.add(Material.getMaterial("SNOW"));
		allowed.add(Material.getMaterial("FLOWER_POT"));
		allowed.add(Material.getMaterial("BREWING_STAND"));
		allowed.add(Material.getMaterial("CAULDRON"));
		allowed.add(Material.getMaterial("CACTUS"));
		allowed.add(Material.getMaterial("WATER_LILY"));
		allowed.add(Material.getMaterial("RED_ROSE"));
		allowed.add(Material.getMaterial("ENCHANTMENT_TABLE"));
		allowed.add(Material.getMaterial("ENDER_PORTAL_FRAME"));
		allowed.add(Material.getMaterial("PORTAL"));
		allowed.add(Material.getMaterial("ENDER_PORTAL"));
		allowed.add(Material.getMaterial("ENDER_CHEST"));
		allowed.add(Material.getMaterial("NETHER_FENCE"));
		allowed.add(Material.getMaterial("NETHER_WARTS"));
		allowed.add(Material.getMaterial("REDSTONE_WIRE"));
		allowed.add(Material.getMaterial("LEVER"));
		allowed.add(Material.getMaterial("YELLOW_FLOWER"));
		allowed.add(Material.getMaterial("CROPS"));
		allowed.add(Material.getMaterial("WATER"));
		allowed.add(Material.getMaterial("LAVA"));
		allowed.add(Material.getMaterial("SKULL"));
		allowed.add(Material.getMaterial("TRAPPED_CHEST"));
		allowed.add(Material.getMaterial("FIRE"));
		allowed.add(Material.getMaterial("BROWN_MUSHROOM"));
		allowed.add(Material.getMaterial("RED_MUSHROOM"));
		allowed.add(Material.getMaterial("DEAD_BUSH"));
		allowed.add(Material.getMaterial("SAPLING"));
		allowed.add(Material.getMaterial("TORCH"));
		allowed.add(Material.getMaterial("MELON_STEM"));
		allowed.add(Material.getMaterial("PUMPKIN_STEM"));
		allowed.add(Material.getMaterial("COCOA"));
		allowed.add(Material.getMaterial("BED"));
		allowed.add(Material.getMaterial("BED_BLOCK"));
		allowed.add(Material.getMaterial("PISTON_EXTENSION"));
		allowed.add(Material.getMaterial("PISTON_MOVING_PIECE"));
		semi.add(Material.getMaterial("IRON_FENCE"));
		semi.add(Material.getMaterial("THIN_GLASS"));
		semi.add(Material.getMaterial("STAINED_GLASS_PANE"));
		semi.add(Material.getMaterial("COBBLE_WALL"));
	}

	public PhaseA(AntiCheat AntiCheat) {
		super("PhaseA", "Phase", CheckType.Combat, AntiCheat);

		setEnabled(true);
		setBannable(false);
		setMaxViolations(40);
	}

	@EventHandler(ignoreCancelled = true)
	public void teleport(PlayerTeleportEvent e) {
		if (e.getCause() != TeleportCause.UNKNOWN && e.getCause() != TeleportCause.PLUGIN) {
			teleported.add(e.getPlayer().getUniqueId());
		}
	}

	@EventHandler
	public void death(PlayerDeathEvent e) {
		teleported.add(e.getEntity().getUniqueId());
	}

	@EventHandler
	public void respawn(PlayerRespawnEvent e) {
		teleported.add(e.getPlayer().getUniqueId());
	}

	@EventHandler
	public void update(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		if (player.isDead()
				|| (BlockUtil.isNearLiquid(player) && BlockUtil.isNearHalfBlock(player))
				|| (BlockUtil.isNearLiquid(player))) {
			return;
		}

		UUID playerId = player.getUniqueId();
		Location loc1 = lastLocation.containsKey(playerId) ? (Location) lastLocation.get(playerId)
				: player.getLocation();
		Location loc2 = player.getLocation();
		if (player.getAllowFlight()) {
			teleported.add(player.getUniqueId());
		}
		if (player.getGameMode().equals(GameMode.CREATIVE)) {
			teleported.add(player.getUniqueId());
		}
		if ((loc1.getWorld() == loc2.getWorld()) && (!teleported.contains(playerId))
				&& (loc1.distance(loc2) > 10.0D)) {
			if (BlockUtil.isNearAllowedPhase(player)) {
				return;
			}
			player.teleport((Location) lastLocation.get(playerId), PlayerTeleportEvent.TeleportCause.PLUGIN);
			if ((player.getLocation().getBlock().getType().isSolid())
					|| (player.getLocation().clone().add(0.0D, 1.0D, 0.0D).getBlock().getType().isSolid())) {
				player.teleport((Location) lastLocation.get(playerId), PlayerTeleportEvent.TeleportCause.PLUGIN);
				getAntiCheat().logCheat(this, player, "[1]", "(Type: A)");
				return;
			}
			getAntiCheat().logCheat(this, player, "[2]", "(Type: A)");
		} else if (isLegit(playerId, loc1, loc2)) {
			lastLocation.put(playerId, loc2);
		} else if (lastLocation.containsKey(playerId)) {
			player.teleport((Location) lastLocation.get(playerId), PlayerTeleportEvent.TeleportCause.PLUGIN);
			if ((player.getLocation().getBlock().getType().isSolid())
					|| (player.getLocation().clone().add(0.0D, 1.0D, 0.0D).getBlock().getType().isSolid())) {
				player.teleport((Location) lastLocation.get(playerId), PlayerTeleportEvent.TeleportCause.PLUGIN);

				getAntiCheat().logCheat(this, player, "[3]", "(Type: A)");
				return;
			}
			getAntiCheat().logCheat(this, player, "[4]", "(Type: A)");
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (!getAntiCheat().getConfig().getBoolean("checks.Movement.Phase.PhaseA.pearlFix")) {
			return;
		}
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.hasItem()
				&& event.getItem().getType() == Material.ENDER_PEARL) {
			Block block = event.getClickedBlock();
			if (block.getType().isSolid() && this.blockedPearlTypes.contains(block.getType())
					&& !(block.getState() instanceof InventoryHolder)) {
				final PearlGlitchEvent event2 = new PearlGlitchEvent(event.getPlayer(), event.getPlayer().getLocation(),
						event.getPlayer().getLocation(), event.getPlayer().getItemInHand(), PearlGlitchType.INTERACT);
				Bukkit.getPluginManager().callEvent(event2);

				if (!event2.isCancelled()) {
					event.setCancelled(true);
					Player player = event.getPlayer();
					player.setItemInHand(event.getItem());
				}
			}
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
	public void onPearlClip(PlayerTeleportEvent event) {
		if (!getAntiCheat().getConfig().getBoolean("checks.Movement.Phase.PhaseA.pearlFix")) {
			return;
		}
		if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
			Location to = event.getTo();
			if (blockedPearlTypes.contains(to.getBlock().getType()) && to.getBlock().getType() != Material.getMaterial("FENCE_GATE")
					&& to.getBlock().getType() != Material.getMaterial("TRAP_DOOR")) {
				final PearlGlitchEvent event2 = new PearlGlitchEvent(event.getPlayer(), event.getFrom(), event.getTo(),
						event.getPlayer().getItemInHand(), PearlGlitchType.TELEPORT);
				Bukkit.getPluginManager().callEvent(event2);
				if (!event2.isCancelled()) {
					Player player = event.getPlayer();
					player.sendMessage(getAntiCheat().PREFIX + Color.Red
							+ "You have been detected trying to pearl glitch, therefore your pearl was cancelled.");
					event.setCancelled(true);
				}
				return;
			}
			to.setX(to.getBlockX() + 0.5);
			to.setZ(to.getBlockZ() + 0.5);
			if ((!allowed.contains(to.getBlock().getType()) || !allowed.contains(to.clone().add(0.0D, 1.0D, 0.0D).getBlock().getType()))
					&& (to.getBlock().getType().isSolid()
							|| to.clone().add(0.0D, 1.0D, 0.0D).getBlock().getType().isSolid())
					&& to.clone().subtract(0.0D, 1.0D, 0.0D).getBlock().getType().isSolid()
							& !BlockUtil.isSlab(to.getBlock())) {
				Player player = event.getPlayer();
				final PearlGlitchEvent event2 = new PearlGlitchEvent(player, event.getFrom(), event.getTo(),
						event.getPlayer().getItemInHand(), PearlGlitchType.SAFE_LOCATION);
				Bukkit.getPluginManager().callEvent(event2);
				if (!event2.isCancelled()) {
					event.setCancelled(true);
					player.sendMessage(getAntiCheat().PREFIX + Color.Red
							+ "Could not find a safe location, therefore your pearl was cancelled.");
				}
				return;
			} else if (!allowed.contains(to.clone().add(0.0D, 1.0D, 0.0D).getBlock().getType())
					&& to.clone().add(0.0D, 1.0D, 0.0D).getBlock().getType().isSolid()
					&& !to.getBlock().getType().isSolid()) {
				to.setY(to.getY() - 0.7);
			}

			event.setTo(to);
		}
	}

	public boolean isLegit(UUID playerId, Location loc1, Location loc2) {
		if (loc1.getWorld() != loc2.getWorld()) {
			return true;
		}
		if (teleported.remove(playerId)) {
			return true;
		}
		int moveMaxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
		int moveMinX = Math.min(loc1.getBlockX(), loc2.getBlockX());
		int moveMaxY = Math.max(loc1.getBlockY(), loc2.getBlockY()) + 1;
		int moveMinY = Math.min(loc1.getBlockY(), loc2.getBlockY());
		int moveMaxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
		int moveMinZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
		if (moveMaxY > 256) {
			moveMaxX = 256;
		}
		if (moveMinY > 256) {
			moveMinY = 256;
		}
		for (int x = moveMinX; x <= moveMaxX; x++) {
			for (int z = moveMinZ; z <= moveMaxZ; z++) {
				for (int y = moveMinY; y <= moveMaxY; y++) {
					Block block = loc1.getWorld().getBlockAt(x, y, z);
					if (((y != moveMinY) || (loc1.getBlockY() == loc2.getBlockY()))
							&& (hasPhased(block, loc1, loc2, Bukkit.getPlayer(playerId)))) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private boolean hasPhased(Block block, Location loc1, Location loc2, Player p) {
		if (((allowed.contains(block.getType())) || (BlockUtil.isStair(block)) || (BlockUtil.isSlab(block))
				|| (BlockUtil.isClimbableBlock(block)) || (block.isLiquid()))) {
			return false;
		}
		double moveMaxX = Math.max(loc1.getX(), loc2.getX());
		double moveMinX = Math.min(loc1.getX(), loc2.getX());
		double moveMaxY = Math.max(loc1.getY(), loc2.getY()) + 1.8D;
		double moveMinY = Math.min(loc1.getY(), loc2.getY());
		double moveMaxZ = Math.max(loc1.getZ(), loc2.getZ());
		double moveMinZ = Math.min(loc1.getZ(), loc2.getZ());
		double blockMaxX = block.getLocation().getBlockX() + 1;
		double blockMinX = block.getLocation().getBlockX();
		double blockMaxY = block.getLocation().getBlockY() + 2;
		double blockMinY = block.getLocation().getBlockY();
		double blockMaxZ = block.getLocation().getBlockZ() + 1;
		double blockMinZ = block.getLocation().getBlockZ();
		if (blockMinY > moveMinY) {
			blockMaxY -= 1.0D;
		}
		if ((block.getType().equals(Material.getMaterial("IRON_DOOR_BLOCK"))) || (block.getType().equals(Material.getMaterial("WOODEN_DOOR")))) {
			Door door = (Door) block.getType().getNewData(block.getData());
			if (door.isTopHalf()) {
				return false;
			}
			BlockFace facing = door.getFacing();
			if (door.isOpen()) {
				Block up = block.getRelative(BlockFace.UP);
				boolean hinge;
				if ((up.getType().equals(Material.getMaterial("IRON_DOOR_BLOCK"))) || (up.getType().equals(Material.getMaterial("WOODEN_DOOR")))) {
					hinge = (up.getData() & 0x1) == 1;
				} else {
					return false;
				}
				if (facing == BlockFace.NORTH) {
					facing = hinge ? BlockFace.WEST : BlockFace.EAST;
				} else if (facing == BlockFace.EAST) {
					facing = hinge ? BlockFace.NORTH : BlockFace.SOUTH;
				} else if (facing == BlockFace.SOUTH) {
					facing = hinge ? BlockFace.EAST : BlockFace.WEST;
				} else {
					facing = hinge ? BlockFace.SOUTH : BlockFace.NORTH;
				}
			}
			if (facing == BlockFace.WEST) {
				blockMaxX -= 0.8D;
			}
			if (facing == BlockFace.EAST) {
				blockMinX += 0.8D;
			}
			if (facing == BlockFace.NORTH) {
				blockMaxZ -= 0.8D;
			}
			if (facing == BlockFace.SOUTH) {
				blockMinZ += 0.8D;
			}
		} else if (block.getType().equals(Material.getMaterial("FENCE_GATE"))) {
			if (((Gate) block.getType().getNewData(block.getData())).isOpen()) {
				return false;
			}
			BlockFace face = ((Directional) block.getType().getNewData(block.getData())).getFacing();
			if ((face == BlockFace.NORTH) || (face == BlockFace.SOUTH)) {
				blockMaxX -= 0.2D;
				blockMinX += 0.2D;
			} else {
				blockMaxZ -= 0.2D;
				blockMinZ += 0.2D;
			}
		} else if (block.getType().equals(Material.getMaterial("TRAP_DOOR"))) {
			TrapDoor door = (TrapDoor) block.getType().getNewData(block.getData());
			if (door.isOpen()) {
				return false;
			}
			if (door.isInverted()) {
				blockMinY += 0.85D;
			} else {
				blockMaxY -= (blockMinY > moveMinY ? 0.85D : 1.85D);
			}
		} else if (block.getType().equals(Material.getMaterial("FENCE")) || semi.contains(block.getType())) {
			blockMaxX -= 0.2D;
			blockMinX += 0.2D;
			blockMaxZ -= 0.2D;
			blockMinZ += 0.2D;
			if (((moveMaxX > blockMaxX) && (moveMinX > blockMaxX) && (moveMaxZ > blockMaxZ) && (moveMinZ > blockMaxZ))
					|| ((moveMaxX < blockMinX) && (moveMinX < blockMinX) && (moveMaxZ > blockMaxZ)
							&& (moveMinZ > blockMaxZ))
					|| ((moveMaxX > blockMaxX) && (moveMinX > blockMaxX) && (moveMaxZ < blockMinZ)
							&& (moveMinZ < blockMinZ))
					|| ((moveMaxX < blockMinX) && (moveMinX < blockMinX) && (moveMaxZ < blockMinZ)
							&& (moveMinZ < blockMinZ))) {
				return false;
			}
			if (block.getRelative(BlockFace.EAST).getType() == block.getType()) {
				blockMaxX += 0.2D;
			}
			if (block.getRelative(BlockFace.WEST).getType() == block.getType()) {
				blockMinX -= 0.2D;
			}
			if (block.getRelative(BlockFace.SOUTH).getType() == block.getType()) {
				blockMaxZ += 0.2D;
			}
			if (block.getRelative(BlockFace.NORTH).getType() == block.getType()) {
				blockMinZ -= 0.2D;
			}
		}
		boolean x = loc1.getX() < loc2.getX();
		boolean y = loc1.getY() < loc2.getY();
		boolean z = loc1.getZ() < loc2.getZ();

		double distance = loc1.distance(loc2) - Math.abs(loc1.getY() - loc2.getY());

		if (distance > 0.5 && block.getType().isSolid()) {
			return true;
		}

		return ((moveMinX != moveMaxX) && (moveMinY <= blockMaxY) && (moveMaxY >= blockMinY) && (moveMinZ <= blockMaxZ)
				&& (moveMaxZ >= blockMinZ)
				&& (((x) && (moveMinX <= blockMinX) && (moveMaxX >= blockMinX))
						|| ((!x) && (moveMinX <= blockMaxX) && (moveMaxX >= blockMaxX))))
				|| ((moveMinY != moveMaxY) && (moveMinX <= blockMaxX) && (moveMaxX >= blockMinX)
						&& (moveMinZ <= blockMaxZ) && (moveMaxZ >= blockMinZ)
						&& (((y) && (moveMinY <= blockMinY) && (moveMaxY >= blockMinY))
								|| ((!y) && (moveMinY <= blockMaxY) && (moveMaxY >= blockMaxY))))
				|| ((moveMinZ != moveMaxZ) && (moveMinX <= blockMaxX) && (moveMaxX >= blockMinX)
						&& (moveMinY <= blockMaxY) && (moveMaxY >= blockMinY)
						&& (((z) && (moveMinZ <= blockMinZ) && (moveMaxZ >= blockMinZ))
								|| ((!z) && (moveMinZ <= blockMaxZ) && (moveMaxZ >= blockMaxZ))));
	}
}