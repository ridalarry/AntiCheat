package me.rida.anticheat.checks.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;

public class AntiBlindnessA extends Check {
	public AntiBlindnessA(AntiCheat AntiCheat) {
		super("AntiBlindnessA", "AntiBlindness", CheckType.Player, true, false, false, false, true, 5, 1, 600000L, AntiCheat);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onMove(PlayerMoveEvent e) {
		final Player p = e.getPlayer();
		if (p.isSprinting() && p.hasPotionEffect(PotionEffectType.BLINDNESS)) {
			getAntiCheat().logCheat(this, p, "Sprnting while having blindness potion effect!", "(Type: A)");
		}
	}
}
