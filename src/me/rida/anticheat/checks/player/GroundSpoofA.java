package me.rida.anticheat.checks.player;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.data.DataPlayer;
import me.rida.anticheat.utils.BlockUtil;
import me.rida.anticheat.utils.PlayerUtil;
import me.rida.anticheat.utils.ServerUtil;
import me.rida.anticheat.utils.TimerUtil;
import me.rida.anticheat.utils.VelocityUtil;

public class GroundSpoofA extends Check {
	public GroundSpoofA(AntiCheat AntiCheat) {
		super("GroundsSpoofA", "GroundSpoof", CheckType.Player, true, false, false, false, 10, 1, 600000L, AntiCheat);
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		DataPlayer data = AntiCheat.getInstance().getDataManager().getData(p);
		if (data != null) {
			if (e.getTo().getY() > e.getFrom().getY()
					|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
					|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
				return;
			}
    		if (DataPlayer.lastNearSlime !=null) {
    			if (DataPlayer.lastNearSlime.contains(p.getPlayer().getName().toString())) {
    				return;
    			}
    		}
			if (data.isLastBlockPlaced_GroundSpoof()) {
				if (TimerUtil.elapsed(data.getLastBlockPlacedTicks(),500L)) {
					data.setLastBlockPlaced_GroundSpoof(false);
				}
				return;
			}
			if (ServerUtil.isBukkitVerison("1_13")) {
				return;
			}
			if (!ServerUtil.isBukkitVerison("1_8")
					&&!ServerUtil.isBukkitVerison("1_7")) {
				if (p.hasPotionEffect(PotionEffectType.LEVITATION)) {
					return;
				}
			}
			Location to = e.getTo();
			Location from = e.getFrom();
			double diff = to.toVector().distance(from.toVector());
			int dist = PlayerUtil.getDistanceToGround(p);
			if (p.getLocation().add(0,-1.50,0).getBlock().getType() != Material.AIR) {
				data.setGroundSpoofVL(0);
				return;
			}
			if (e.getTo().getY() > e.getFrom().getY() || PlayerUtil.isOnGround4(p) || VelocityUtil.didTakeVelocity(p)) {
				data.setGroundSpoofVL(0);
				return;
			}
			if (!ServerUtil.isBukkitVerison("1_13") && !ServerUtil.isBukkitVerison("1_7") ) {
				if (p.isOnGround() && diff > 0.0 && !PlayerUtil.isOnTheGround(p) && dist >= 2 && e.getTo().getY() < e.getFrom().getY()) {
					if (data.getGroundSpoofVL() >= 4) {
						if (data.getAirTicks() >= 10) {
							getAntiCheat().logCheat(this, p, "[1] Spoofed On-Ground Packet.", "(Type: A)");
						} else {
							getAntiCheat().logCheat(this, p, "[2] Spoofed On-Ground Packet.", "(Type: A)");
						}
					} else {
						data.setGroundSpoofVL(data.getGroundSpoofVL()+1);
					}
				}
			}
			else {
				if (BlockUtil.isSolid(p.getLocation().getBlock())
						|| PlayerUtil.isNearSolid(p)) {
					return;
				}
				if (p.isOnGround() && diff > 0.0 && !PlayerUtil.isOnGround(e,p) && dist >= 2 && e.getTo().getY() < e.getFrom().getY()) {
					if (data.getGroundSpoofVL() >= 4) {
						if (data.getAirTicks() >= 10) {
							getAntiCheat().logCheat(this, p, "[1] Spoofed On-Ground Packet.", "(Type: A)");
						} else {
							getAntiCheat().logCheat(this, p, "[2] Spoofed On-Ground Packet.", "(Type: A)");
						}
					} else {
						data.setGroundSpoofVL(data.getGroundSpoofVL()+1);
					}
				}
			}
		}
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		DataPlayer data = AntiCheat.getInstance().getDataManager().getData(p);
		if (!ServerUtil.isBukkitVerison("1_8")
				&& !ServerUtil.isBukkitVerison("1_7")) {
			if (p.hasPotionEffect(PotionEffectType.LEVITATION)) {
				return;
			}
		}
		if (data != null) {
			if (!data.isLastBlockPlaced_GroundSpoof()) {
				data.setLastBlockPlaced_GroundSpoof(true);
				data.setLastBlockPlacedTicks(TimerUtil.nowlong());
			}
		}
	}
}