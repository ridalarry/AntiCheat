package me.rida.anticheat.events;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.data.DataPlayer;
import me.rida.anticheat.utils.BlockUtil;
import me.rida.anticheat.utils.MathUtil;
import me.rida.anticheat.utils.PlayerUtil;
import me.rida.anticheat.utils.TimerUtil;

public class MoveEvent implements Listener {


	public static int defaultWait = 15; // This is in ticks

	// We need to create hashmaps to store the amount of time left

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static HashMap<String, Integer> ticksLeft = new HashMap(); // This is the amount of ticks left to wait
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static HashMap<String, BukkitRunnable> cooldownTask = new HashMap(); // This is the task event

	// Checks to see if the person is in the timer
	public boolean inTimer(Player player) {
		if(ticksLeft.isEmpty() || !ticksLeft.containsKey(player.getName().toString())) {
			return false;
		}
		// Just making sure!
		if(ticksLeft.containsKey(player.getName().toString())) {
			return true;
		}
		return false;
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if(inTimer(p) == true) {
			return;
		}else {
			AntiCheat.Instance.startTimer(p);
		}
		if (e.getFrom().getX() != e.getTo().getX()
				|| e.getFrom().getY() != e.getTo().getY()
				|| e.getFrom().getZ() != e.getTo().getZ()) {
			DataPlayer data = AntiCheat.getInstance().getDataManager().getDataPlayer(p);

			if (data != null) {
				data.setOnGround(PlayerUtil.isOnTheGround(p));
				data.setOnStairSlab(PlayerUtil.isInStairs(p));
				data.setInLiquid(PlayerUtil.isInLiquid(p));
				data.setOnIce(PlayerUtil.isOnIce(p));
				data.setOnClimbable(PlayerUtil.isOnClimbable(p));
				data.setUnderBlock(PlayerUtil.inUnderBlock(p));

				if(data.isOnGround()) {
					data.groundTicks++;
					data.airTicks = 0;
				} else {
					data.airTicks++;
					data.groundTicks = 0;
				}

				data.iceTicks = Math.max(0, data.isOnIce() ? data.iceTicks + 1  : data.iceTicks - 1);
				data.liquidTicks = Math.max(0, data.isInLiquid() ? data.liquidTicks + 1  : data.liquidTicks - 1);
				data.blockTicks = Math.max(0, data.isUnderBlock() ? data.blockTicks + 1  : data.blockTicks - 1);
			}
		}

		DataPlayer data = AntiCheat.getInstance().getDataManager().getData(p);

		if (data.isNearIce()) {
			if (TimerUtil.elapsed(data.getIsNearIceTicks(),500L)) {
				if (!PlayerUtil.isNearIce(p)) {
					data.setNearIce(false);
				} else {
					data.setIsNearIceTicks(TimerUtil.nowlong());
				}
			}
		}
		Location l = p.getLocation();
		int x = l.getBlockX();
		int y = l.getBlockY();
		int z = l.getBlockZ();
		Location loc1 = new Location(p.getWorld(), x, y + 1, z);
		if (loc1.getBlock().getType() != Material.AIR) {
			if (!data.isBlockAbove_Set()) {
				data.setBlockAbove_Set(true);
				data.setBlockAbove(TimerUtil.nowlong());
			} else {
				if (TimerUtil.elapsed(data.getBlockAbove(),1000L)) {
					if (loc1.getBlock().getType() == Material.AIR) {
						data.setBlockAbove_Set(false);
					} else {
						data.setBlockAbove_Set(true);
						data.setBlockAbove(TimerUtil.nowlong());
					}
				}
			}
		} else {
			if (data.isBlockAbove_Set()) {
				if (TimerUtil.elapsed(data.getBlockAbove(), 1000L)) {
					if (loc1.getBlock().getType() == Material.AIR) {
						data.setBlockAbove_Set(false);
					} else {
						data.setBlockAbove_Set(true);
						data.setBlockAbove(TimerUtil.nowlong());
					}
				}
			}
		}

		if (PlayerUtil.isNearIce(p)) {
			if(data.getIceTicks() < 60) data.setIceTicks(data.getIceTicks() + 1);
		} else if(data.getIceTicks() > 0) {
			data.setIceTicks(data.getIceTicks() - 1);
		}
		if(PlayerUtil.isNearSlime(p.getLocation())) { 
			if (!(DataPlayer.lastNearSlime.contains(p.getPlayer().getName().toString()))) {
				DataPlayer.lastNearSlime.add(p.getPlayer().getName().toString());
				//Bukkit.broadcastMessage(p.getPlayer().getName().toString() + " is now added to the list");
			} 
		}
		if(!(PlayerUtil.isNearSlime(p.getLocation()))) {
			if (e.getFrom().getX() != e.getTo().getX() || e.getFrom().getZ() != e.getTo().getZ()) {
				if (DataPlayer.lastNearSlime.contains(p.getPlayer().getName().toString())) {
					DataPlayer.lastNearSlime.remove(p.getPlayer().getName().toString());
				//Bukkit.broadcastMessage(p.getPlayer().getName().toString() + " is now removed from the list");
				}
			}
		}
		if(p.getAllowFlight()) {
			if (DataPlayer.getWasFlying() < 5) {
				DataPlayer.setWasFlying(5);
			}
		}
		if(!p.getAllowFlight()) {
			if (DataPlayer.getWasFlying() > 0) {
				DataPlayer.setWasFlying(DataPlayer.getWasFlying() - 1);
			}
		}
		if (BlockUtil.isHalfBlock(p.getLocation().add(0,-0.50,0).getBlock())|| BlockUtil.isLessThanBlock(p.getLocation().add(0,-0.50,0).getBlock()) || PlayerUtil.isNearHalfBlock(p)) {
			if (!data.isHalfBlocks_MS_Set()) {
				data.setHalfBlocks_MS_Set(true);
				data.setHalfBlocks_MS(TimerUtil.nowlong());
			} else {
				if (TimerUtil.elapsed(data.getHalfBlocks_MS(),900L)) {
					if (BlockUtil.isHalfBlock(p.getLocation().add(0,-0.50,0).getBlock()) || PlayerUtil.isNearHalfBlock(p)) {
						data.setHalfBlocks_MS_Set(true);
						data.setHalfBlocks_MS(TimerUtil.nowlong());
					} else {
						data.setHalfBlocks_MS_Set(false);
					}
				}
			}
		} else {
			if (TimerUtil.elapsed(data.getHalfBlocks_MS(),900L)) {
				if (BlockUtil.isHalfBlock(p.getLocation().add(0,-0.50,0).getBlock()) || PlayerUtil.isNearHalfBlock(p)) {
					data.setHalfBlocks_MS_Set(true);
					data.setHalfBlocks_MS(TimerUtil.nowlong());
				} else {
					data.setHalfBlocks_MS_Set(false);
				}
			}
		}
		if (PlayerUtil.isNearIce(p) && !data.isNearIce()) {
			data.setNearIce(true);
			data.setIsNearIceTicks(TimerUtil.nowlong());
		} else if (PlayerUtil.isNearIce(p)) {
			data.setIsNearIceTicks(TimerUtil.nowlong());
		}

		double distance = MathUtil.getVerticalDistance(e.getFrom(), e.getTo());

		boolean onGround = PlayerUtil.isOnGround4(p);
		if(!onGround
				&& e.getFrom().getY() > e.getTo().getY()) {
			data.setFallDistance(data.getFallDistance() + distance);
		} else {
			data.setFallDistance(0);
		}

		if(onGround) {
			data.setGroundTicks(data.getGroundTicks() + 1);
			data.setAirTicks(0);
		} else {
			data.setAirTicks(data.getAirTicks() + 1);
			data.setGroundTicks(0);
		}

		if(PlayerUtil.isOnGround(p.getLocation().add(0, 2, 0))) {
			data.setAboveBlockTicks(data.getAboveBlockTicks() + 2);
		} else if(data.getAboveBlockTicks() > 0) {
			data.setAboveBlockTicks(data.getAboveBlockTicks() - 1);
		}
		if(PlayerUtil.isInWater(p.getLocation())
				|| PlayerUtil.isInWater(p.getLocation().add(0, 1, 0))) {
			data.setWaterTicks(data.getWaterTicks() + 1);
		} else if(data.getWaterTicks() > 0) {
			data.setWaterTicks(data.getWaterTicks() - 1);
		}
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onVelocity(PlayerVelocityEvent e) {
		DataPlayer data = AntiCheat.getInstance().getDataManager().getDataPlayer(e.getPlayer());

		if(data == null) {
			return;
		}
		if(e.getVelocity().getY() > -0.078 || e.getVelocity().getY() < -0.08) {
			data.lastVelocityTaken = System.currentTimeMillis();
		}
	}
}