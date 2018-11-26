package me.rida.anticheat.checks.movement;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.utils.PlayerUtil;
import me.rida.anticheat.utils.TimeUtil;

public class NoFallA extends Check {
	public static Map<UUID, Map.Entry<Long, Integer>> NoFallTicks;
	public static Map<UUID, Double> FallDistance;
	private static ArrayList<Player> cancel;

	public NoFallA(AntiCheat AntiCheat) {
		super("NoFallA", "NoFall", CheckType.Movement, AntiCheat);

		setEnabled(true);
		setBannable(true);
		
		NoFallTicks = new HashMap<UUID, Map.Entry<Long, Integer>>();
		FallDistance = new HashMap<UUID, Double>();
		cancel = new ArrayList<Player>();
		
		this.setViolationResetTime(120000);
		setMaxViolations(9);
	}

	@EventHandler
	private void onDeath(PlayerDeathEvent e) {
		cancel.add(e.getEntity());
	}

	@EventHandler
	private void onLogout(PlayerQuitEvent e) {
		if (FallDistance.containsKey(e.getPlayer().getUniqueId())) {
			FallDistance.remove(e.getPlayer().getUniqueId());
		}
		if (FallDistance.containsKey(e.getPlayer().getUniqueId())) {
			FallDistance.containsKey(e.getPlayer().getUniqueId());
		}
		if(cancel.contains(e.getPlayer())) {
			cancel.remove(e.getPlayer());
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	private void onTeleport(PlayerTeleportEvent e) {
		if (e.getCause() == TeleportCause.ENDER_PEARL) {
			cancel.add(e.getPlayer());
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	private void Move(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (p.getAllowFlight()
				|| p.getGameMode().equals(GameMode.CREATIVE)
				|| p.getVehicle() != null
				|| cancel.remove(p)
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
		        || getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()
				|| PlayerUtil.isOnClimbable(p, 0)
				|| PlayerUtil.isInWater(p)) {
			return;
		}
		Damageable dp = (Damageable) e.getPlayer();

		if (dp.getHealth() <= 0.0D) {
			return;
		}

		double Falling = 0.0D;
		if ((!PlayerUtil.isInGround(p)) && (e.getFrom().getY() > e.getTo().getY())) {
			if (FallDistance.containsKey(p.getUniqueId())) {
				Falling = FallDistance.get(p.getUniqueId()).doubleValue();
			}
			Falling += e.getFrom().getY() - e.getTo().getY();
		}
		FallDistance.put(p.getUniqueId(), Double.valueOf(Falling));
		if (Falling < 3.0D) {
			return;
		}
		long Time = System.currentTimeMillis();
		int Count = 0;
		if (NoFallTicks.containsKey(p.getUniqueId())) {
			Time = NoFallTicks.get(p.getUniqueId()).getKey().longValue();
			Count = NoFallTicks.get(p.getUniqueId()).getValue().intValue();
		}
		if ((p.isOnGround()) || (p.getFallDistance() == 0.0F)) {
			dumplog(p, "Logged for NoFall Type A; . Real Fall Distance: " + Falling);
			p.damage(5);
			Count += 2;
		} else {
			Count--;
		}
		if (NoFallTicks.containsKey(p.getUniqueId()) && TimeUtil.elapsed(Time, 10000L)) {
			Count = 0;
			Time = System.currentTimeMillis();
		}
		if (Count >= 4) {
			dumplog(p, "Logged for NoFall Type A;  Count: " + Count);
			Count = 0;

			FallDistance.put(p.getUniqueId(), Double.valueOf(0.0D));
			getAntiCheat().logCheat(this, p, "(Packet)", "(Type: A)");
		}
		NoFallTicks.put(p.getUniqueId(),
				new AbstractMap.SimpleEntry<Long, Integer>(Time, Count));
		return;
	}

}