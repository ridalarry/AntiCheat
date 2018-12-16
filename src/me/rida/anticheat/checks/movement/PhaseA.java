package me.rida.anticheat.checks.movement;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.other.PearlGlitchEvent;
import me.rida.anticheat.other.PearlGlitchType;
import me.rida.anticheat.utils.BlockUtil;
import me.rida.anticheat.utils.CheatUtil;
import me.rida.anticheat.utils.Color;

public class PhaseA extends Check {
	public PhaseA(AntiCheat AntiCheat) {
		super("PhaseA", "Phase", CheckType.Movement, true, false, false, false, true, 40, 1, 600000L, AntiCheat);
	}

	@SuppressWarnings("deprecation")
	@EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
	private void onPlayerInteract(PlayerInteractEvent event) {
		Player p = (Player) event.getPlayer();

		if(AntiCheat.isInPhaseTimer(event.getPlayer())) {
			return;
		}

		if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.hasItem()
				&& event.getItem().getType() == Material.ENDER_PEARL) {
			Block block = event.getClickedBlock();
			if (block.getType().isSolid() && BlockUtil.blockedPearlTypes.contains(block.getType())
					&& !(block.getState() instanceof InventoryHolder)) {
				PearlGlitchEvent event2 = new PearlGlitchEvent(event.getPlayer(), event.getPlayer().getLocation(),
						event.getPlayer().getLocation(), event.getPlayer().getItemInHand(), PearlGlitchType.INTERACT);
				Bukkit.getPluginManager().callEvent(event2);

				if (!event2.isCancelled()) {
					event.setCancelled(true);
					p.sendMessage(getAntiCheat().PREFIX + Color.Red
							+ "Your pearl glitched, therefore your pearl was cancelled.");
					getAntiCheat().logCheat(this, p, "[1] Unsafe pearl interaction!", "(Type: A)");
					Player player = event.getPlayer();
					player.setItemInHand(event.getItem());
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
	private void onPearlClip(PlayerTeleportEvent event) {
		if(AntiCheat.isInPhaseTimer(event.getPlayer())) {
			return;
		}

		if (event.getCause() == TeleportCause.PLUGIN) {
			return;
		}
		if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
			Location to = event.getTo();
			if (BlockUtil.blockedPearlTypes.contains(to.getBlock().getType()) && to.getBlock().getType() != Material.FENCE_GATE
					&& to.getBlock().getType() != Material.TRAP_DOOR) {
				PearlGlitchEvent event2 = new PearlGlitchEvent(event.getPlayer(), event.getFrom(), event.getTo(),
						event.getPlayer().getItemInHand(), PearlGlitchType.TELEPORT);
				Bukkit.getPluginManager().callEvent(event2);
				if (!event2.isCancelled()) {
					Player player = event.getPlayer();
					player.sendMessage(getAntiCheat().PREFIX + Color.Red
							+ "You have been detected trying to pearl glitch, therefore your pearl was cancelled.");
					getAntiCheat().logCheat(this, player, "[2] Detected trying to pearl glitch!", "(Type: A)");
					event.setCancelled(true);
					player.getInventory().addItem(new ItemStack(Material.ENDER_PEARL, 1));
				}
				return;
			}
			to.setX(to.getBlockX() + 0.5);
			to.setZ(to.getBlockZ() + 0.5);
			if ((!BlockUtil.allowed.contains(to.getBlock().getType()) || !BlockUtil.allowed.contains(to.clone().add(0.0D, 1.0D, 0.0D).getBlock().getType()))
					&& (to.getBlock().getType().isSolid()
							|| to.clone().add(0.0D, 1.0D, 0.0D).getBlock().getType().isSolid())
					&& to.clone().subtract(0.0D, 1.0D, 0.0D).getBlock().getType().isSolid()
					& !CheatUtil.isSlab(to.getBlock())) {
				Player player = event.getPlayer();
				PearlGlitchEvent event2 = new PearlGlitchEvent(player, event.getFrom(), event.getTo(),
						event.getPlayer().getItemInHand(), PearlGlitchType.SAFE_LOCATION);
				Bukkit.getPluginManager().callEvent(event2);
				if (!event2.isCancelled()) {
					event.setCancelled(true);
					player.sendMessage(getAntiCheat().PREFIX + Color.Red
							+ "Could not find a safe location, therefore your pearl was cancelled.");
					getAntiCheat().logCheat(this, player, "[3] Attempted to pearl to unsafe location!", "(Type: A)");
					player.getInventory().addItem(new ItemStack(Material.ENDER_PEARL, 1));
				}
				return;
			} else if (!BlockUtil.allowed.contains(to.clone().add(0.0D, 1.0D, 0.0D).getBlock().getType())
					&& to.clone().add(0.0D, 1.0D, 0.0D).getBlock().getType().isSolid()
					&& !to.getBlock().getType().isSolid()) {
				to.setY(to.getY() - 0.7);
			}

			event.setTo(to);
		}
	}
}