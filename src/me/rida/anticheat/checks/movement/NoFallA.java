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
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.utils.UtilTime;
import me.rida.anticheat.utils.b.UtilsB;

public class NoFallA extends Check {
	public static Map<UUID, Map.Entry<Long, Integer>> NoFallTicks;
	public static Map<UUID, Double> FallDistance;
	public static ArrayList<Player> cancel;

	public NoFallA(AntiCheat AntiCheat) {
		super("NoFallA", "NoFall", AntiCheat);

		setEnabled(true);
		setBannable(true);
		
		NoFallTicks = new HashMap<UUID, Map.Entry<Long, Integer>>();
		FallDistance = new HashMap<UUID, Double>();
		cancel = new ArrayList<Player>();
		
		this.setViolationResetTime(120000);
		setMaxViolations(9);
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		cancel.add(e.getEntity());
	}

	@EventHandler
	public void onLogout(PlayerQuitEvent e) {
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

	@EventHandler
	public void onTeleport(PlayerTeleportEvent e) {
		if (e.getCause() == TeleportCause.ENDER_PEARL) {
			cancel.add(e.getPlayer());
		}
	}

	@EventHandler
	public void Move(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		if (player.getAllowFlight()
				|| player.getGameMode().equals(GameMode.CREATIVE)
				|| player.getVehicle() != null
				|| cancel.remove(player)
				|| UtilsB.isOnClimbable(player, 0)
				|| UtilsB.isInWater(player)) {
			return;
		}
		Damageable dplayer = (Damageable) e.getPlayer();

		if (dplayer.getHealth() <= 0.0D) {
			return;
		}

		double Falling = 0.0D;
		if ((!UtilsB.isOnGround(player)) && (e.getFrom().getY() > e.getTo().getY())) {
			if (FallDistance.containsKey(player.getUniqueId())) {
				Falling = FallDistance.get(player.getUniqueId()).doubleValue();
			}
			Falling += e.getFrom().getY() - e.getTo().getY();
		}
		FallDistance.put(player.getUniqueId(), Double.valueOf(Falling));
		if (Falling < 3.0D) {
			return;
		}
		long Time = System.currentTimeMillis();
		int Count = 0;
		if (NoFallTicks.containsKey(player.getUniqueId())) {
			Time = NoFallTicks.get(player.getUniqueId()).getKey().longValue();
			Count = NoFallTicks.get(player.getUniqueId()).getValue().intValue();
		}
		if ((player.isOnGround()) || (player.getFallDistance() == 0.0F)) {
			dumplog(player, "NoFall. Real Fall Distance: " + Falling);
			player.damage(5);
			Count += 2;
		} else {
			Count--;
		}
		if (NoFallTicks.containsKey(player.getUniqueId()) && UtilTime.elapsed(Time, 10000L)) {
			Count = 0;
			Time = System.currentTimeMillis();
		}
		if (Count >= 4) {
			dumplog(player, "Logged. Count: " + Count);
			Count = 0;

			FallDistance.put(player.getUniqueId(), Double.valueOf(0.0D));
			getAntiCheat().logCheat(this, player, "(Packet)", "(Type: A)");
		}
		NoFallTicks.put(player.getUniqueId(),
				new AbstractMap.SimpleEntry<Long, Integer>(Time, Count));
		return;
	}

}