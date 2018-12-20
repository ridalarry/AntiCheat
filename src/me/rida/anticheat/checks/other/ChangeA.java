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
import org.bukkit.plugin.Plugin;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.utils.PlayerUtil;
import me.rida.anticheat.utils.ServerUtil;

public class ChangeA extends Check {
	private List<UUID> built = new ArrayList<UUID>();
	public static List<UUID> falling = new ArrayList<UUID>();

	public ChangeA(AntiCheat AntiCheat) {
		super("ChangeA", "Change", CheckType.Other, true, false, false, false, true, 10, 1, 600000L, AntiCheat);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onMove(PlayerMoveEvent e) {
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
		if (!ServerUtil.isBukkitVerison("1_13") && !ServerUtil.isBukkitVerison("1_7")) {
			if (!(PlayerUtil.isOnTheGround(p) || ServerUtil.isOnBlock(p, 0, new Material[]{Material.WHITE_CARPET, Material.ORANGE_CARPET,
					Material.MAGENTA_CARPET,
					Material.LIGHT_BLUE_CARPET,
					Material.YELLOW_CARPET,
					Material.LIME_CARPET,
					Material.PINK_CARPET,
					Material.GRAY_CARPET,
					Material.LIGHT_GRAY_CARPET,
					Material.CYAN_CARPET,
					Material.PURPLE_CARPET,
					Material.BLUE_CARPET,
					Material.BROWN_CARPET,
					Material.GREEN_CARPET,
					Material.RED_CARPET,
					Material.BLACK_CARPET}) || ServerUtil.isHoveringOverWater(p, 0) || p.getLocation().getBlock().getType() != Material.AIR)) {
				if (e.getFrom().getY() > e.getTo().getY()) {
					if (!ChangeA.falling.contains(u)) {
						ChangeA.falling.add(u);
					}
				} else {
					n = e.getTo().getY() > e.getFrom().getY() ? (ChangeA.falling.contains(u) ? ++n : --n) : --n;
				}
			} else {
				ChangeA.falling.remove(u);
			}
			if (n > n2) {
				if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
						|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
					return;
				}
				getAntiCheat().logCheat(this, p, "[1]", "(Type: A)");
				n = 0;
				ChangeA.falling.remove(u);
			}
		}
		else {

			if (!(PlayerUtil.isOnGround(e,p) || ServerUtil.isOnBlock(p, 0, new Material[]{Material.WHITE_CARPET, Material.ORANGE_CARPET,
					Material.MAGENTA_CARPET,
					Material.LIGHT_BLUE_CARPET,
					Material.YELLOW_CARPET,
					Material.LIME_CARPET,
					Material.PINK_CARPET,
					Material.GRAY_CARPET,
					Material.LIGHT_GRAY_CARPET,
					Material.CYAN_CARPET,
					Material.PURPLE_CARPET,
					Material.BLUE_CARPET,
					Material.BROWN_CARPET,
					Material.GREEN_CARPET,
					Material.RED_CARPET,
					Material.BLACK_CARPET}) || ServerUtil.isHoveringOverWater(p, 0) || p.getLocation().getBlock().getType() != Material.AIR)) {
				if (e.getFrom().getY() > e.getTo().getY()) {
					if (!ChangeA.falling.contains(u)) {
						ChangeA.falling.add(u);
					}
				} else {
					n = e.getTo().getY() > e.getFrom().getY() ? (ChangeA.falling.contains(u) ? ++n : --n) : --n;
				}
			} else {
				ChangeA.falling.remove(u);
			}
			if (n > n2) {
				if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
						|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
					return;
				}
				getAntiCheat().logCheat(this, p, "[2]", "(Type: A)");
				n = 0;
				ChangeA.falling.remove(u);
			}
		}
	}

	@SuppressWarnings("unused")
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onAttack(BlockPlaceEvent e) {
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