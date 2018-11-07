package me.rida.anticheat.checks.combat;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.packets.events.PacketUseEntityEvent;
import me.rida.anticheat.utils.UtilCheat;
import me.rida.anticheat.utils.needscleanup.UtilsB;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

public class HitBoxA extends Check {

	public HitBoxA(AntiCheat AntiCheat) {
		super("HitBoxA", "Hitbox", AntiCheat);

		setEnabled(true);
		setBannable(false);
		setViolationResetTime(1000);
		setViolationsToNotify(150);
		setMaxViolations(300);
	}

	public static Map<UUID, Integer> count = new HashMap<>();
	public static Map<UUID, Player> lastHit = new HashMap<>();
	public static Map<UUID, Double> yawDif = new HashMap<>();

	@EventHandler(priority = EventPriority.MONITOR)
	public void onQuit(PlayerQuitEvent e) {
		if (count.containsKey(e.getPlayer().getUniqueId())) {
			count.remove(e.getPlayer().getUniqueId());
		}
		if (yawDif.containsKey(e.getPlayer().getUniqueId())) {
			yawDif.remove(e.getPlayer().getUniqueId());
		}
		if (lastHit.containsKey(e.getPlayer().getUniqueId())) {
			lastHit.remove(e.getPlayer().getUniqueId());
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onUse(PacketUseEntityEvent e) {

		Player player = e.getAttacker();
		
		LivingEntity attacked = (Player) e.getAttacked();
		if (player.getAllowFlight()) {
			return;
		}

		int verbose = count.getOrDefault(player.getUniqueId(), 0);

		double offset = UtilCheat.getOffsetOffCursor(player, attacked);

		if(offset > 30) {
			if((verbose+= 2) > 25) {
				getAntiCheat().logCheat(this, player, UtilsB.round(offset, 4) + ">-30", "(Type: A)");
			}
		} else if(verbose > 0) {
			verbose--;
		}

		count.put(player.getUniqueId(), verbose);
	}

}
