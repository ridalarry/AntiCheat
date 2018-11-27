package me.rida.anticheat.checks.other;

import java.util.Set;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.other.Ping;
import me.rida.anticheat.utils.Color;
import me.rida.anticheat.utils.VelocityUtil;

public class BlockInteractC extends Check {
    public BlockInteractC(AntiCheat AntiCheat) {
        super("BlockInteractC", "BI", CheckType.Other, AntiCheat);
		setEnabled(true);
		setMaxViolations(20);
		setBannable(false);
		setViolationsToNotify(1);
    }
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	private void onPlaceBlock(BlockPlaceEvent e) {
        Player p = e.getPlayer();
		Block t = p.getTargetBlock((Set)null, 5);
        if (p.getAllowFlight()
                || e.getPlayer().getVehicle() != null
				|| !getAntiCheat().isEnabled()
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
		        || getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()
				|| Ping.getPing(e.getPlayer()) > 100
				|| p.getGameMode().equals(GameMode.CREATIVE)
                || VelocityUtil.didTakeVelocity(p)) return;
        if (e.getBlock().getWorld().getBlockAt(e.getBlock().getLocation().subtract(0.0, 1.0, 0.0)).getType() == Material.AIR) {
            if (!e.getBlock().getLocation().equals((Object)t.getLocation()) && !e.isCancelled() && t.getType().isSolid() && !t.getType().name().toLowerCase().contains("sign") && !t.getType().toString().toLowerCase().contains("fence") && p.getLocation().getY() > e.getBlock().getLocation().getY()) {
            	getAntiCheat().logCheat(this, p, Color.Red + "Experemental" + " [1]", "(Type: C)");
            	
            }

            if (e.getBlockAgainst().isLiquid() && e.getBlock().getType() != Material.WATER_LILY) {
            	getAntiCheat().logCheat(this, p, Color.Red + "Experemental" + " [2]", "(Type: C)");
            }
        }
    }
}