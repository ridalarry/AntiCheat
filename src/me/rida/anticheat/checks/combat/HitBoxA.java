package me.rida.anticheat.checks.combat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.other.Latency;
import me.rida.anticheat.utils.CheatUtil;

public class HitBoxA extends Check {
public HitBoxA(AntiCheat AntiCheat) {
	super("HitBoxA", "HitBox", CheckType.Combat, true, false, false, false, true, 10, 1, 600000L, AntiCheat);
	}

	public static Map<UUID, Integer> count = new HashMap<UUID, Integer>();
	public static Map<UUID, Player> lastHit = new HashMap<UUID, Player>();
	public static Map<UUID, Double> yawDif = new HashMap<UUID, Double>();

	@SuppressWarnings("static-access")
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onMove(PlayerMoveEvent e) {
		double yawDif = Math.abs(e.getFrom().getYaw() - e.getTo().getYaw());
		this.yawDif.put(e.getPlayer().getUniqueId(), yawDif);
	}

	@SuppressWarnings("static-access")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onUse(EntityDamageByEntityEvent e) {
		if (e.getCause() != DamageCause.ENTITY_ATTACK) {
			return;
		}
		if (!(e.getEntity() instanceof Player) 
				|| !(e.getDamager() instanceof Player)) {
			return;
		}

		Player player = (Player) e.getDamager();
		Player attacked = (Player) e.getEntity();
		if (player.getGameMode().equals(GameMode.CREATIVE)) {
			return;
		}

		int Count = 0;
		double yawDif = 0;
		Player lastPlayer = attacked;

		if (lastHit.containsKey(player.getUniqueId())) {
			lastPlayer = lastHit.get(player.getUniqueId());
		}

		if (count.containsKey(player.getUniqueId())) {
			Count = count.get(player.getUniqueId());
		}
		if (this.yawDif.containsKey(player.getUniqueId())) {
			yawDif = this.yawDif.get(player.getUniqueId());
		}

		if (lastPlayer != attacked) {
			lastHit.put(player.getUniqueId(), attacked);
			return;
		}

		double offset = CheatUtil.getOffsetOffCursor(player, attacked);
		double Limit = 108D;
		double distance = CheatUtil.getHorizontalDistance(player.getLocation(), attacked.getLocation());
		Limit += distance * 57;
		Limit += (attacked.getVelocity().length() + player.getVelocity().length()) * 64;
		Limit += yawDif * 6;

		if (Latency.getLag(player) > 80 || Latency.getLag(attacked) > 80) {
			return;
		}

		if (offset > Limit) {
			Count++;
		} else {
			Count = Count > 0 ? Count - 1 : Count;
		}

		if (Count > 8) {
			getAntiCheat().logCheat(this, player, offset + " > " + Limit,
					"(Type: A)");
			Count = 0;
		}

		count.put(player.getUniqueId(), Count);
		lastHit.put(player.getUniqueId(), attacked);
	}

}