package me.rida.anticheat.checks.other;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.utils.Color;
import me.rida.anticheat.utils.PlayerUtil;
import me.rida.anticheat.utils.ServerUtil;
import me.rida.anticheat.AntiCheat;

public class ChangeA extends Check {
    private List<UUID> built = new ArrayList<UUID>();
    private List<UUID> falling = new ArrayList<UUID>();

    public ChangeA(AntiCheat AntiCheat) {
        super("ChangeA", "Change", CheckType.Other, AntiCheat);
		setEnabled(true);
		setMaxViolations(10);
		setBannable(false);
		setViolationsToNotify(1);
    }

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onMove(PlayerMoveEvent e) {
        if (!this.isEnabled()) {
            return;
        }
        Player p = e.getPlayer();
        UUID u = p.getUniqueId();
        if (p.getAllowFlight()) {
            return;
        }
        if (p.isInsideVehicle()) {
            return;
        }
        if (!p.getNearbyEntities(1.0, 1.0, 1.0).isEmpty()) {
            return;
        }
        if (this.built.contains(u)) {
            return;
        }
        int n = 0;
        int n2 = 5;
        if (!(PlayerUtil.isOnTheGround(p) || ServerUtil.isOnBlock(p, 0, new Material[]{Material.getMaterial("CARPET")}) || ServerUtil.isHoveringOverWater(p, 0) || p.getLocation().getBlock().getType() != Material.AIR)) {
            if (e.getFrom().getY() > e.getTo().getY()) {
                if (!this.falling.contains(u)) {
                    this.falling.add(u);
                }
            } else {
                n = e.getTo().getY() > e.getFrom().getY() ? (this.falling.contains(u) ? ++n : --n) : --n;
            }
        } else {
            this.falling.remove(u);
        }
        if (n > n2) {
        	if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
    		        || getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
        		return;
        	}
        	getAntiCheat().logCheat(this, p, Color.Red + "Experemental", "(Type: A)");
            n = 0;
            this.falling.remove(u);
        }
    }

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        UUID u = p.getUniqueId();
        if (this.falling.contains(u)) {
            this.falling.remove(u);
        }
    }

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onAttack(BlockPlaceEvent e) {
        if (e.getPlayer() instanceof Player) {
            Player p = e.getPlayer();
            UUID u = p.getUniqueId();
            this.built.add(u);
            Bukkit.getScheduler().runTaskLater((Plugin)AntiCheat.Instance, () -> {
                boolean bl = this.built.remove(u);
            }
            , 60);
        }
    }
}

