package me.rida.anticheat.checks.combat;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.packets.events.PacketUseEntityEvent;
import me.rida.anticheat.utils.CheatUtil;
import me.rida.anticheat.utils.MathUtil;

public class HitBoxC extends Check {

	public HitBoxC(AntiCheat AntiCheat) {
		super("HitBoxC", "HitBox", CheckType.Combat, true, false, false, false, true, 10, 1, 600000L, AntiCheat);
	}

	public static Map<UUID, Integer> count = new HashMap<>();
	public static Map<UUID, Player> lastHit = new HashMap<>();
	public static Map<UUID, Double> yawDif = new HashMap<>();

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onUse(PacketUseEntityEvent e) {

		if (!(e.getAttacker() instanceof Player) || !(e.getAttacked() instanceof Player) || e.getAttacker().getGameMode().equals(GameMode.CREATIVE)) {
			return;
		}
		final Player p = e.getAttacker();

		final LivingEntity attacked = (Player) e.getAttacked();

		int verbose = count.getOrDefault(p.getUniqueId(), 0);

		final double offset = CheatUtil.getOffsetOffCursor(p, attacked);

		if(offset > 30) {
			if((verbose+= 2) > 25) {
				getAntiCheat().logCheat(this, p, "Offset: " + MathUtil.round(offset, 4) + "> 30", "(Type: A)");
			}
		} else if(verbose > 0) {
			verbose--;
		}
		count.put(p.getUniqueId(), verbose);
	}
}