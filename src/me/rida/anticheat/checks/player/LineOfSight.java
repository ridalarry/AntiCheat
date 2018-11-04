package me.rida.anticheat.checks.player;

import me.rida.anticheat.checks.Check;
import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.utils.lineofsight.BlockPathFinder;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;


public class LineOfSight extends Check {
      public LineOfSight(AntiCheat AntiCheat) {
        super("LineOfSight", "LineOfSight", AntiCheat);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
     Player p = e.getPlayer();
          if ((e.getBlock().getLocation().distance(p.getPlayer().getEyeLocation()) > 2)
             && !BlockPathFinder.line(p.getPlayer().getEyeLocation(), e.getBlock().getLocation()).contains(e.getBlock()) && !e.isCancelled()) {
              getAntiCheat().logCheat(this, p,"Broke a block without a line of sight too it.", null);
              e.setCancelled(true);
          }
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
          Player p = e.getPlayer();
            if ((e.getBlock().getLocation().distance(p.getPlayer().getEyeLocation()) > 2)
             && !BlockPathFinder.line(p.getPlayer().getEyeLocation(), e.getBlock().getLocation()).contains(e.getBlock()) && !e.isCancelled()) {
              getAntiCheat().logCheat(this, p,"Placed a block without a line of sight too it.", null);
              e.setCancelled(true);
          }
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (e.getClickedBlock().getType() == Material.CHEST || e.getClickedBlock().getType() == Material.TRAPPED_CHEST || e.getClickedBlock().getType() == Material.ENDER_CHEST) {
                Player p = e.getPlayer();
                if ((e.getClickedBlock().getLocation().distance(p.getPlayer().getEyeLocation()) > 2)
                        && !BlockPathFinder.line(p.getPlayer().getEyeLocation(), e.getClickedBlock().getLocation()).contains(e.getClickedBlock()) && !e.isCancelled()) {
                    getAntiCheat().logCheat(this, p, "Interacted without a line of sight too it.", null);
                    e.setCancelled(true);
                }
            }
        }
    }
}
