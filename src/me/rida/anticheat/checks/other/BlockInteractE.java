package me.rida.anticheat.checks.other;

import me.rida.anticheat.checks.Check;
import me.rida.anticheat.other.Ping;
import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.utils.lineofsight.BlockPathFinder;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;


public class BlockInteractE extends Check {
      public BlockInteractE(AntiCheat AntiCheat) {
        super("BlockInteractE", "BlockInteract", AntiCheat);
		setEnabled(true);
		setMaxViolations(10);
		setBannable(true);
    }

  	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent e) {
  		if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
                || e.getPlayer().getGameMode().equals(GameMode.CREATIVE)
				|| Ping.getPing(e.getPlayer()) > 400) {
  			return;
  		}
     Player p = e.getPlayer();
          if ((e.getBlock().getLocation().distance(p.getPlayer().getEyeLocation()) > 2)
             && !BlockPathFinder.line(p.getPlayer().getEyeLocation(), e.getBlock().getLocation()).contains(e.getBlock()) && !e.isCancelled()) {
              getAntiCheat().logCheat(this, p,"[1] Broke a block without a line of sight too it.", "(Type: E)");
              e.setCancelled(true);
          }
    }
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent e) {
  		if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
                || e.getPlayer().getGameMode().equals(GameMode.CREATIVE)
				|| Ping.getPing(e.getPlayer()) > 400) {
  			return;
  		}
          Player p = e.getPlayer();
            if ((e.getBlock().getLocation().distance(p.getPlayer().getEyeLocation()) > 2)
             && !BlockPathFinder.line(p.getPlayer().getEyeLocation(), e.getBlock().getLocation()).contains(e.getBlock()) && !e.isCancelled()) {
              getAntiCheat().logCheat(this, p,"[2] Placed a block without a line of sight too it.", "(Type: E)");
              e.setCancelled(true);
          }
    }
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onInteract(PlayerInteractEvent e) {
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
                    getAntiCheat().logCheat(this, p, "[3] Interacted without a line of sight too it.", "(Type: E)");
                    e.setCancelled(true);
                }
            }
        }
    }
}
