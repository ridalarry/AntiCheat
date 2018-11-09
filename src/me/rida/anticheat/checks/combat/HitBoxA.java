package me.rida.anticheat.checks.combat;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.packets.events.PacketUseEntityEvent;
import me.rida.anticheat.utils.MathUtil;
import me.rida.anticheat.utils.CheatUtil;

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
		setViolationsToNotify(1);
		setMaxViolations(10);
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

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onUse(PacketUseEntityEvent e) {

		Player p = e.getAttacker();
		
		LivingEntity attacked = (Player) e.getAttacked();
		if (p.getAllowFlight()) {
			return;
		}

		int verbose = count.getOrDefault(p.getUniqueId(), 0);

		double offset = CheatUtil.getOffsetOffCursor(p, attacked);

		if(offset > 30) {
			if((verbose+= 2) > 25) {
				getAntiCheat().logCheat(this, p, MathUtil.round(offset, 4) + ">-30", "(Type: A)");
			}
		} else if(verbose > 0) {
			verbose--;
		}

		count.put(p.getUniqueId(), verbose);
	}

}
