package me.rida.anticheat.checks.other;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.other.Ping;
import me.rida.anticheat.utils.PlayerUtil;
import me.rida.anticheat.utils.lineofsight.BlockPathFinder;


public class BlockInteractB extends Check {
	public BlockInteractB(AntiCheat AntiCheat) {
		super("BlockInteractE", "BI", CheckType.Other, true, true, false, false, 10, 1, 600000L, AntiCheat);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onBlockBreak(BlockBreakEvent e) {
		if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| e.getPlayer().getGameMode().equals(GameMode.CREATIVE)
				|| Ping.getPing(e.getPlayer()) > 400) {
			return;
		}
		Player p = e.getPlayer();
		if (getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
			return;
		}
		double x = PlayerUtil.getEff(p);
		double y = 2;
		y += x;
		if ((e.getBlock().getLocation().distance(p.getPlayer().getEyeLocation()) > y)
				&& !BlockPathFinder.line(p.getPlayer().getEyeLocation(), e.getBlock().getLocation()).contains(e.getBlock()) && !e.isCancelled()) {
			getAntiCheat().logCheat(this, p,"[1] Broke a block without a line of sight too it.", "(Type: B)");
			e.setCancelled(true);
		}
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onBlockPlace(BlockPlaceEvent e) {
		if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| e.getPlayer().getGameMode().equals(GameMode.CREATIVE)
				|| Ping.getPing(e.getPlayer()) > 400) {
			return;
		}
		Player p = e.getPlayer();
		if ((e.getBlock().getLocation().distance(p.getPlayer().getEyeLocation()) > 2)
				&& !BlockPathFinder.line(p.getPlayer().getEyeLocation(), e.getBlock().getLocation()).contains(e.getBlock()) && !e.isCancelled()) {
			getAntiCheat().logCheat(this, p,"[2] Placed a block without a line of sight too it.", "(Type: B)");
			e.setCancelled(true);
		}
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onInteract(PlayerInteractEvent e) {
		if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| e.getPlayer().getGameMode().equals(GameMode.CREATIVE)
				|| Ping.getPing(e.getPlayer()) > 400) {
			return;
		}
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (e.getClickedBlock().getType() == Material.CHEST || e.getClickedBlock().getType() == Material.TRAPPED_CHEST || e.getClickedBlock().getType() == Material.ENDER_CHEST) {
				Player p = e.getPlayer();
				if ((e.getClickedBlock().getLocation().distance(p.getPlayer().getEyeLocation()) > 2)
						&& !BlockPathFinder.line(p.getPlayer().getEyeLocation(), e.getClickedBlock().getLocation()).contains(e.getClickedBlock()) && !e.isCancelled()) {
					getAntiCheat().logCheat(this, p, "[3] Interacted without a line of sight too it.", "(Type: B)");
					e.setCancelled(true);
				}
			}
		}
	}
}