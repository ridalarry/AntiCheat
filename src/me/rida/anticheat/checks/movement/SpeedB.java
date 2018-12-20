package me.rida.anticheat.checks.movement;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.data.DataPlayer;
import me.rida.anticheat.utils.BlockUtil;
import me.rida.anticheat.utils.MathUtil;
import me.rida.anticheat.utils.PlayerUtil;
import me.rida.anticheat.utils.ServerUtil;

public class SpeedB extends Check {

	public static Map<UUID, Map.Entry<Integer, Long>> speedTicks;
	public static Map<UUID, Map.Entry<Integer, Long>> tooFastTicks;
	public static Map<UUID, Long> lastHit;

	public SpeedB(AntiCheat AntiCheat) {
		super("SpeedB", "Speed", CheckType.Movement, true, true, false, true, false, 15, 4, 120000L, AntiCheat);
		SpeedB.lastHit = new HashMap<UUID, Long>();
		SpeedB.tooFastTicks = new HashMap<UUID, Map.Entry<Integer, Long>>();
		SpeedB.speedTicks = new HashMap<UUID, Map.Entry<Integer, Long>>();
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onMove(PlayerMoveEvent e) {

		Location from = e.getFrom().clone();
		Location to = e.getTo().clone();
		Player p = e.getPlayer();
		double velx = Math.abs(p.getVelocity().getX());
		double vely = Math.abs(p.getVelocity().getX());
		double velz = Math.abs(p.getVelocity().getX());

		Location l = p.getLocation();
		int x = l.getBlockX();
		int y = l.getBlockY();
		int z = l.getBlockZ();
		Location blockLoc = new Location(p.getWorld(), x, y - 1, z);
		Location loc = new Location(p.getWorld(), x, y, z);
		Location loc2 = new Location(p.getWorld(), x, y + 1, z);
		Location above = new Location(p.getWorld(), x, y + 2, z);
		Location above3 = new Location(p.getWorld(), x - 1, y + 2, z - 1);
		long lastHitDiff = Math.abs(System.currentTimeMillis() - SpeedC.lastHit.getOrDefault(p.getUniqueId(), 0L));

		if ((e.getTo().getX() == e.getFrom().getX()) && (e.getTo().getZ() == e.getFrom().getZ())
				&& (e.getTo().getY() == e.getFrom().getY())
				|| lastHitDiff < 1500L 
				|| p.getNoDamageTicks() != 0
				|| p.getVehicle() != null
				|| p.getGameMode().equals(GameMode.CREATIVE)
				|| p.getAllowFlight()
				|| DataPlayer.getWasFlying() > 0
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()
				|| PlayerUtil.isNearIce(p)
				|| PlayerUtil.isNearSlime(p)
				|| PlayerUtil.isNearPiston(p)
				|| PlayerUtil.wasOnSlime(p)){
			return;
		}

		double Airmaxspeed = 0.4;
		double maxSpeed = 0.42;
		double newmaxspeed = 0.75;
		if (isOnIce(p)) {
			newmaxspeed = 1.0;
		}

		double ig = 0.28;
		double speed = MathUtil.offset(getHV(to.toVector()), getHV(from.toVector()));
		double onGroundDiff = (to.getY() - from.getY());

		if (p.hasPotionEffect(PotionEffectType.SPEED)) {
			int level = getPotionEffectLevel(p, PotionEffectType.SPEED);
			if (level > 0) {
				newmaxspeed = (newmaxspeed * (((level * 20) * 0.011) + 1));
				Airmaxspeed = (Airmaxspeed * (((level * 20) * 0.011) + 1));
				maxSpeed = (maxSpeed * (((level * 20) * 0.011) + 1));
				ig = (ig * (((level * 20) * 0.011) + 1));
			}
		}
		Airmaxspeed += p.getWalkSpeed() > 0.2 ? p.getWalkSpeed() * 0.8 : 0;
		maxSpeed += p.getWalkSpeed() > 0.2 ? p.getWalkSpeed() * 0.8 : 0;
		maxSpeed += velx;
		Airmaxspeed += velx;
		maxSpeed += vely;
		Airmaxspeed += vely;
		maxSpeed += velz;
		Airmaxspeed += velz;

		if (isReallyOnGround(p) && to.getY() == from.getY()) {
			if (speed >= maxSpeed && p.isOnGround() && p.getFallDistance() < 0.15
					&& blockLoc.getBlock().getType() != Material.ICE
					&& blockLoc.getBlock().getType() != Material.PACKED_ICE
					&& loc2.getBlock().getType() != Material.ACACIA_TRAPDOOR && loc2.getBlock().getType() != Material.BIRCH_TRAPDOOR &&
					loc2.getBlock().getType() != Material.DARK_OAK_TRAPDOOR && loc2.getBlock().getType() != Material.IRON_TRAPDOOR &&
					loc2.getBlock().getType() != Material.JUNGLE_TRAPDOOR && loc2.getBlock().getType() != Material.OAK_TRAPDOOR && 
					loc2.getBlock().getType() != Material.SPRUCE_TRAPDOOR && above.getBlock().getType() == Material.AIR
					&& above3.getBlock().getType() == Material.AIR) {
				getAntiCheat().logCheat(this, p, "On Ground VelX; " + velx + " VelY; " + vely + " VelZ; " + velz, "(Type: B)");
			}
		}
		if (!isReallyOnGround(p) && speed >= Airmaxspeed && !isOnIce(p)
				&& blockLoc.getBlock().getType() != Material.ICE && !blockLoc.getBlock().isLiquid()
				&& !loc.getBlock().isLiquid() && blockLoc.getBlock().getType() != Material.PACKED_ICE
				&& above.getBlock().getType() == Material.AIR && above3.getBlock().getType() == Material.AIR
				&& blockLoc.getBlock().getType() != Material.AIR) {
			if (ServerUtil.isBukkitVerison("1_13")) {
				if (velx == 0
						&& vely == 0
						&& velz == 0) {
					return;
				}
			}
			getAntiCheat().logCheat(this, p, "Mid Air VelX; " + velx + " VelY; " + vely + " VelZ; " + velz, "(Type: B)");
		}
		if (speed >= newmaxspeed && isOnIce(p) && p.getFallDistance() < 0.6
				&& loc2.getBlock().getType() != Material.ACACIA_TRAPDOOR && loc2.getBlock().getType() != Material.BIRCH_TRAPDOOR &&
				loc2.getBlock().getType() != Material.DARK_OAK_TRAPDOOR && loc2.getBlock().getType() != Material.IRON_TRAPDOOR &&
				loc2.getBlock().getType() != Material.JUNGLE_TRAPDOOR && loc2.getBlock().getType() != Material.OAK_TRAPDOOR && 
				loc2.getBlock().getType() != Material.SPRUCE_TRAPDOOR && above.getBlock().getType() == Material.AIR
				&& loc2.getBlock().getType() == Material.AIR) {
			getAntiCheat().logCheat(this, p, "Limit VelX; " + velx + " VelY; " + vely + " VelZ; " + velz, "(Type: B)");

		}

		if (speed > ig && !isAir(p) && onGroundDiff <= -0.4 && p.getFallDistance() <= 0.4
				&& !flaggyStuffNear(p.getLocation()) && blockLoc.getBlock().getType() != Material.ICE
				&& e.getTo().getY() != e.getFrom().getY() && blockLoc.getBlock().getType() != Material.PACKED_ICE
				&& loc2.getBlock().getType() != Material.ACACIA_TRAPDOOR && loc2.getBlock().getType() != Material.BIRCH_TRAPDOOR &&
				loc2.getBlock().getType() != Material.DARK_OAK_TRAPDOOR && loc2.getBlock().getType() != Material.IRON_TRAPDOOR &&
				loc2.getBlock().getType() != Material.JUNGLE_TRAPDOOR && loc2.getBlock().getType() != Material.OAK_TRAPDOOR && 
				loc2.getBlock().getType() != Material.SPRUCE_TRAPDOOR && above.getBlock().getType() == Material.AIR
				&& above3.getBlock().getType() == Material.AIR) {
			getAntiCheat().logCheat(this, p, "Vanilla VelX;" + velx + " VelY; " + vely + " VelZ; " + velz, "(Type: B)");
		}
	}

	private boolean isOnIce(final Player player) {
		final Location a = player.getLocation();
		a.setY(a.getY() - 1.0);
		if (a.getBlock().getType().equals((Object) Material.ICE)
				|| a.getBlock().getType().equals((Object) Material.getMaterial("PACKED_ICE"))
				|| a.getBlock().getType().equals((Object) Material.getMaterial("FROSTED_ICE"))) {
			return true;
		}
		a.setY(a.getY() - 1.0);
		return a.getBlock().getType().equals((Object) Material.ICE)
				|| a.getBlock().getType().equals((Object) Material.getMaterial("PACKED_ICE"))
				|| a.getBlock().getType().equals((Object) Material.getMaterial("FROSTED_ICE"));
	}

	private int getPotionEffectLevel(Player p, PotionEffectType pet) {
		for (PotionEffect pe : p.getActivePotionEffects()) {
			if (pe.getType().getName().equals(pet.getName())) {
				return pe.getAmplifier() + 1;
			}
		}
		return 0;
	}

	private Vector getHV(Vector V) {
		V.setY(0);
		return V;
	}
	private static boolean isReallyOnGround(Player p) {
		Location l = p.getLocation();
		int x = l.getBlockX();
		int y = l.getBlockY();
		int z = l.getBlockZ();
		Location b = new Location(p.getWorld(), x, y - 1, z);

		if (p.isOnGround() && b.getBlock().getType() != Material.AIR && b.getBlock().getType() != Material.COBWEB
				&& !b.getBlock().isLiquid()) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("deprecation")
	private static boolean flaggyStuffNear(Location loc) {
		boolean nearBlocks = false;
		for (Block bl : BlockUtil.getSurrounding(loc.getBlock(), true)) {
			if (bl.getType().equals(Material.LEGACY_DOUBLE_STEP)|| bl.getType().equals(Material.LEGACY_DOUBLE_STONE_SLAB2)|| bl.getType().equals(Material.LEGACY_PURPUR_DOUBLE_SLAB)|| bl.getType().equals(Material.LEGACY_WOOD_DOUBLE_STEP)
					|| bl.getType().equals(Material.WHITE_BED)
					|| bl.getType().equals(Material.ORANGE_BED)
					|| bl.getType().equals(Material.MAGENTA_BED)
					|| bl.getType().equals(Material.LIGHT_BLUE_BED)
					|| bl.getType().equals(Material.YELLOW_BED)
					|| bl.getType().equals(Material.LIME_BED)
					|| bl.getType().equals(Material.PINK_BED)
					|| bl.getType().equals(Material.GRAY_BED)
					|| bl.getType().equals(Material.LIGHT_GRAY_BED)
					|| bl.getType().equals(Material.CYAN_BED)
					|| bl.getType().equals(Material.PURPLE_BED)
					|| bl.getType().equals(Material.BLUE_BED)
					|| bl.getType().equals(Material.BROWN_BED)
					|| bl.getType().equals(Material.GREEN_BED)
					|| bl.getType().equals(Material.RED_BED)
					|| bl.getType().equals(Material.BLACK_BED)
					|| bl.getType().equals(Material.LEGACY_BED)
					|| bl.getType().equals(Material.LEGACY_BED_BLOCK)
					|| bl.getType().equals(Material.LEGACY_STEP)
					|| bl.getType().equals(Material.BIRCH_SLAB)
					|| bl.getType().equals(Material.ACACIA_SLAB)
					|| bl.getType().equals(Material.BRICK_SLAB)
					|| bl.getType().equals(Material.COBBLESTONE_SLAB)
					|| bl.getType().equals(Material.DARK_OAK_SLAB)
					|| bl.getType().equals(Material.DARK_PRISMARINE_SLAB)
					|| bl.getType().equals(Material.JUNGLE_SLAB)
					|| bl.getType().equals(Material.NETHER_BRICK_SLAB)
					|| bl.getType().equals(Material.OAK_SLAB)
					|| bl.getType().equals(Material.PETRIFIED_OAK_SLAB)
					|| bl.getType().equals(Material.PRISMARINE_BRICK_SLAB)
					|| bl.getType().equals(Material.PRISMARINE_SLAB)
					|| bl.getType().equals(Material.PURPUR_SLAB)
					|| bl.getType().equals(Material.QUARTZ_SLAB)
					|| bl.getType().equals(Material.RED_SANDSTONE_SLAB)
					|| bl.getType().equals(Material.SANDSTONE_SLAB)
					|| bl.getType().equals(Material.SPRUCE_SLAB)
					|| bl.getType().equals(Material.STONE_BRICK_SLAB)
					|| bl.getType().equals(Material.STONE_SLAB)) {
				nearBlocks = true;
				break;
			}
		}
		for (Block bl : BlockUtil.getSurrounding(loc.getBlock(), false)) {
			if (bl.getType().equals(Material.LEGACY_DOUBLE_STEP)|| bl.getType().equals(Material.LEGACY_DOUBLE_STONE_SLAB2)|| bl.getType().equals(Material.LEGACY_PURPUR_DOUBLE_SLAB)|| bl.getType().equals(Material.LEGACY_WOOD_DOUBLE_STEP)
					|| bl.getType().equals(Material.WHITE_BED)
					|| bl.getType().equals(Material.ORANGE_BED)
					|| bl.getType().equals(Material.MAGENTA_BED)
					|| bl.getType().equals(Material.LIGHT_BLUE_BED)
					|| bl.getType().equals(Material.YELLOW_BED)
					|| bl.getType().equals(Material.LIME_BED)
					|| bl.getType().equals(Material.PINK_BED)
					|| bl.getType().equals(Material.GRAY_BED)
					|| bl.getType().equals(Material.LIGHT_GRAY_BED)
					|| bl.getType().equals(Material.CYAN_BED)
					|| bl.getType().equals(Material.PURPLE_BED)
					|| bl.getType().equals(Material.BLUE_BED)
					|| bl.getType().equals(Material.BROWN_BED)
					|| bl.getType().equals(Material.GREEN_BED)
					|| bl.getType().equals(Material.RED_BED)
					|| bl.getType().equals(Material.BLACK_BED)
					|| bl.getType().equals(Material.LEGACY_BED)
					|| bl.getType().equals(Material.LEGACY_BED_BLOCK)
					|| bl.getType().equals(Material.LEGACY_STEP)
					|| bl.getType().equals(Material.BIRCH_SLAB)
					|| bl.getType().equals(Material.ACACIA_SLAB)
					|| bl.getType().equals(Material.BRICK_SLAB)
					|| bl.getType().equals(Material.COBBLESTONE_SLAB)
					|| bl.getType().equals(Material.DARK_OAK_SLAB)
					|| bl.getType().equals(Material.DARK_PRISMARINE_SLAB)
					|| bl.getType().equals(Material.JUNGLE_SLAB)
					|| bl.getType().equals(Material.NETHER_BRICK_SLAB)
					|| bl.getType().equals(Material.OAK_SLAB)
					|| bl.getType().equals(Material.PETRIFIED_OAK_SLAB)
					|| bl.getType().equals(Material.PRISMARINE_BRICK_SLAB)
					|| bl.getType().equals(Material.PRISMARINE_SLAB)
					|| bl.getType().equals(Material.PURPUR_SLAB)
					|| bl.getType().equals(Material.QUARTZ_SLAB)
					|| bl.getType().equals(Material.RED_SANDSTONE_SLAB)
					|| bl.getType().equals(Material.SANDSTONE_SLAB)
					|| bl.getType().equals(Material.SPRUCE_SLAB)
					|| bl.getType().equals(Material.STONE_BRICK_SLAB)
					|| bl.getType().equals(Material.STONE_SLAB)) {
				nearBlocks = true;
				break;
			}
		}
		if (isBlock(loc.getBlock().getRelative(BlockFace.DOWN), new Material[] {  
				Material.WHITE_BED,
				Material.ORANGE_BED,
				Material.MAGENTA_BED,
				Material.LIGHT_BLUE_BED,
				Material.YELLOW_BED,
				Material.LIME_BED,
				Material.PINK_BED,
				Material.GRAY_BED,
				Material.LIGHT_GRAY_BED,
				Material.CYAN_BED,
				Material.PURPLE_BED,
				Material.BLUE_BED,
				Material.BROWN_BED,
				Material.GREEN_BED,
				Material.RED_BED,
				Material.BLACK_BED,
				Material.LEGACY_BED,
				Material.LEGACY_BED_BLOCK,
				Material.LEGACY_STEP,
				Material.BIRCH_SLAB,
				Material.ACACIA_SLAB,
				Material.BRICK_SLAB,
				Material.COBBLESTONE_SLAB,
				Material.DARK_OAK_SLAB,
				Material.DARK_PRISMARINE_SLAB,
				Material.JUNGLE_SLAB,
				Material.NETHER_BRICK_SLAB,
				Material.OAK_SLAB,
				Material.PETRIFIED_OAK_SLAB,
				Material.PRISMARINE_BRICK_SLAB,
				Material.PRISMARINE_SLAB,
				Material.PURPUR_SLAB,
				Material.QUARTZ_SLAB,
				Material.RED_SANDSTONE_SLAB,
				Material.SANDSTONE_SLAB,
				Material.SPRUCE_SLAB,
				Material.STONE_BRICK_SLAB,
				Material.STONE_SLAB,
				Material.LEGACY_DOUBLE_STEP, Material.LEGACY_DOUBLE_STONE_SLAB2, Material.LEGACY_PURPUR_DOUBLE_SLAB, Material.LEGACY_WOOD_DOUBLE_STEP })) {
			nearBlocks = true;
		}
		return nearBlocks;
	}


	private static boolean isBlock(Block block, Material[] materials) {
		Material type = block.getType();
		Material[] arrayOfMaterial;
		int j = (arrayOfMaterial = materials).length;
		for (int i = 0; i < j; i++) {
			Material m = arrayOfMaterial[i];
			if (m == type) {
				return true;
			}
		}
		return false;
	}

	private static boolean isAir(final Player player) {
		final Block b = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
		return b.getType().equals((Object) Material.AIR)
				&& b.getRelative(BlockFace.WEST).getType().equals((Object) Material.AIR)
				&& b.getRelative(BlockFace.NORTH).getType().equals((Object) Material.AIR)
				&& b.getRelative(BlockFace.EAST).getType().equals((Object) Material.AIR)
				&& b.getRelative(BlockFace.SOUTH).getType().equals((Object) Material.AIR);
	}
}