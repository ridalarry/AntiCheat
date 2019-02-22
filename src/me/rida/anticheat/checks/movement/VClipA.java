package me.rida.anticheat.checks.movement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.utils.CheatUtil;

public class VClipA extends Check {

	public VClipA(AntiCheat AntiCheat) {
		super("VClipA", "VClip", CheckType.Movement, true, false, false, false, true, 40, 1, 10000L, AntiCheat);
	}

	private static List<Material> allowed = new ArrayList<>();
	public static ArrayList<Player> teleported = new ArrayList<>();
	public static HashMap<Player, Location> lastLocation = new HashMap<>();

	static {
		allowed.add(Material.PISTON_EXTENSION);
		allowed.add(Material.PISTON_STICKY_BASE);
		allowed.add(Material.PISTON_BASE);
		allowed.add(Material.SIGN_POST);
		allowed.add(Material.WALL_SIGN);
		allowed.add(Material.STRING);
		allowed.add(Material.AIR);
		allowed.add(Material.FENCE_GATE);
		allowed.add(Material.CHEST);
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onTeleport(PlayerTeleportEvent e) {
		if (e.getCause() != TeleportCause.UNKNOWN) {
			return;
		}
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onMove(PlayerMoveEvent e) {
		final Player p = e.getPlayer();

		final Location to = e.getTo().clone();
		final Location from = e.getFrom().clone();

		if (!getAntiCheat().isEnabled()
				|| from.getY() == to.getY()
				|| p.getAllowFlight()
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()
				|| p.getVehicle() != null
				|| teleported.remove(e.getPlayer())
				|| e.getTo().getY() <= 0 || e.getTo().getY() >= p.getWorld().getMaxHeight()
				|| !CheatUtil.blocksNear(p)
				|| (p.getLocation().getY() < 0.0D)
				|| (p.getLocation().getY() > p.getWorld().getMaxHeight())) {
			return;
		}

		final double yDist = from.getBlockY() - to.getBlockY();
		for (double y = 0; y < Math.abs(yDist); y++) {
			final Location l = yDist < -0.2 ? from.getBlock().getLocation().clone().add(0.0D, y, 0.0D) : to.getBlock().getLocation().clone().add(0.0D, y, 0.0D);
			if ((yDist > 20 || yDist < -20) && l.getBlock().getType() != Material.AIR
					&& l.getBlock().getType().isSolid() && !allowed.contains(l.getBlock().getType())) {

				AntiCheat.Instance.getServer().getScheduler().runTaskAsynchronously(AntiCheat.Instance, new Runnable(){
					Player p = e.getPlayer();
					@Override
					public void run() {
						e.setCancelled(true);
						p.kickPlayer("Flying is not enabled on this server");
					}
				});

				getAntiCheat().logCheat(this, p, "[1] More than 20 blocks.", "(Type: A)");
				e.setCancelled(true);
				p.teleport(from);
				return;
			}
			if (l.getBlock().getType() != Material.AIR && Math.abs(yDist) > 1.0 && l.getBlock().getType().isSolid()
					&& !allowed.contains(l.getBlock().getType())) {
				getAntiCheat().logCheat(this, p, "[2] " + y + " blocks", "(Type: A)");
				e.setCancelled(true);
				if (lastLocation.containsKey(p)) {
					p.teleport(lastLocation.get(p));
				}
			} else {
				lastLocation.put(p, p.getLocation());
			}
		}
	}
}