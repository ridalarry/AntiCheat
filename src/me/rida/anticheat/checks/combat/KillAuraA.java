package me.rida.anticheat.checks.combat;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerQuitEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.checks.movement.PhaseB;
import me.rida.anticheat.utils.CheatUtil;

public class KillAuraA extends Check {

	public KillAuraA(AntiCheat AntiCheat) {
		super("KillAuraA", "KillAura",  CheckType.Combat, true, false, false, false, 7, 1, 600000L, AntiCheat);
	}

	public static HashMap<Player, Integer> counts = new HashMap<>();
	private ArrayList<Player> blockGlitched = new ArrayList<>();

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onPlayerLogout(PlayerQuitEvent e) {
		if (counts.containsKey(e.getPlayer())) {
			counts.remove(e.getPlayer());
		}
		if (blockGlitched.contains(e.getPlayer())) {
			blockGlitched.remove(e.getPlayer());
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onBreak(BlockBreakEvent e) {
		if (e.isCancelled()) {
			blockGlitched.add(e.getPlayer());
		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	private void checkKillaura(EntityDamageByEntityEvent e) {
		if (e.getCause() != DamageCause.ENTITY_ATTACK
				|| !getAntiCheat().isEnabled()
				|| !(e.getDamager() instanceof Player)
				|| !(e.getEntity() instanceof Player)) {
			return;
		}

		Player p = (Player) e.getDamager();
		if (CheatUtil.slabsNear(p.getEyeLocation())
				|| CheatUtil.slabsNear(p.getEyeLocation().clone().add(0.0D, 0.5D, 0.0D))
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
			return;
		}
		int Count = 0;

		if (counts.containsKey(p)) {
			Count = counts.get(p);
		}

		Player a = (Player) e.getEntity();
		Location dloc = p.getLocation();
		Location aloc = a.getLocation();
		double zdif = Math.abs(dloc.getZ() - aloc.getZ());
		double xdif = Math.abs(dloc.getX() - aloc.getX());

		if (xdif == 0 || zdif == 0
				|| CheatUtil.getOffsetOffCursor(p, a) > 20) {
			return;
		}

		for (int y = 0; y < 1; y += 1) {
			Location zBlock = zdif < -0.2 ? dloc.clone().add(0.0D, y, zdif) : aloc.clone().add(0.0D, y, zdif);
			if (!PhaseB.allowed.contains(zBlock.getBlock().getType()) && zBlock.getBlock().getType().isSolid()
					&& !p.hasLineOfSight(a) && !CheatUtil.isSlab(zBlock.getBlock())) {
				Count++;
			}
			Location xBlock = xdif < -0.2 ? dloc.clone().add(xdif, y, 0.0D) : aloc.clone().add(xdif, y, 0.0D);
			if (!PhaseB.allowed.contains(xBlock.getBlock().getType()) && xBlock.getBlock().getType().isSolid()
					&& !p.hasLineOfSight(a) && !CheatUtil.isSlab(xBlock.getBlock())) {
				Count++;
			}

		}
		if (Count > 3) {
			getAntiCheat().logCheat(this, p, "Wall", "(Type: A)");
			Count = 0;
		}
		counts.put(p, Count);
	}
}