package me.rida.anticheat.checks.combat;

import java.util.AbstractMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.other.Latency;
import me.rida.anticheat.utils.MathUtil;
import me.rida.anticheat.utils.PlayerUtil;
import me.rida.anticheat.utils.TimeUtil;

public class ReachB extends Check {

	public static Map<Player, Integer> count;
	public static Map<Player, Map.Entry<Double, Double>> offsets;

	public ReachB(AntiCheat AntiCheat) {
		super("ReachB", "Reach",  CheckType.Combat, true, true, false, true, 7, 1, 30000L, AntiCheat);

		offsets = new WeakHashMap<Player, Map.Entry<Double, Double>>();
		count = new WeakHashMap<Player, Integer>();
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	private void onMove(PlayerMoveEvent e) {
		if (e.getFrom().getX() == e.getTo().getX() && e.getFrom().getZ() == e.getTo().getZ()) {
			return;
		}
		double OffsetXZ = MathUtil.offset(MathUtil.getHorizontalVector(e.getFrom().toVector()),
				MathUtil.getHorizontalVector(e.getTo().toVector()));
		double horizontal = Math.sqrt(Math.pow(e.getTo().getX() - e.getFrom().getX(), 2.0)
				+ Math.pow(e.getTo().getZ() - e.getFrom().getZ(), 2.0));
		offsets.put(e.getPlayer(),
				new AbstractMap.SimpleEntry<Double, Double>(Double.valueOf(OffsetXZ), Double.valueOf(horizontal)));
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	private void onDamage(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player)
				|| !(e.getEntity() instanceof Player)
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel())
			return;
		Player d = (Player) e.getDamager();
		Player p = (Player) e.getEntity();
		if (d.getAllowFlight()
				|| p.getAllowFlight()
				|| p.getGameMode().equals(GameMode.CREATIVE)
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| getAntiCheat().getLag().getPing(d) > getAntiCheat().getPingCancel()) {
			return;
		}
		double Reach = MathUtil.trim(2, PlayerUtil.getEyeLocation(d).distance(p.getEyeLocation()) - 0.32);
		double Reach2 = MathUtil.trim(2, PlayerUtil.getEyeLocation(d).distance(p.getEyeLocation()) - 0.32);
		double Difference;

		if (!count.containsKey(d)) {
			count.put(d, 0);
		}

		int Count = count.get(d);
		long Time = System.currentTimeMillis();
		double MaxReach = 3.1;
		double YawDifference = Math.abs(d.getEyeLocation().getYaw() - p.getEyeLocation().getYaw());
		double speedToVelocityDif = 0;
		double offsets = 0.0D;

		double lastHorizontal = 0.0D;
		if (ReachB.offsets.containsKey(d)) {
			offsets = (ReachB.offsets.get(d)).getKey().doubleValue();
			lastHorizontal = (ReachB.offsets.get(d)).getValue().doubleValue();
		}
		if (Latency.getLag(d) > 92 || Latency.getLag(p) > 92) {
			return;
		}
		speedToVelocityDif = Math.abs(offsets - p.getVelocity().length());
		MaxReach += (YawDifference * 0.001);
		MaxReach += lastHorizontal * 1.5;
		MaxReach += speedToVelocityDif * 0.08;
		if (d.getLocation().getY() > p.getLocation().getY()) {
			Difference = d.getLocation().getY() - p.getLocation().getY();
			MaxReach += Difference / 2.5;
		} else if (p.getLocation().getY() > d.getLocation().getY()) {
			Difference = p.getLocation().getY() - d.getLocation().getY();
			MaxReach += Difference / 2.5;
		}
		MaxReach += d.getWalkSpeed() <= 0.2 ? 0 : d.getWalkSpeed() - 0.2;

		int PingD = this.getAntiCheat().getLag().getPing(d);
		int PingP = this.getAntiCheat().getLag().getPing(p);
		MaxReach += ((PingD + PingP) / 2) * 0.0024;
		if(PingD > 400) {
			MaxReach += 1.0D;
		}
		if (TimeUtil.elapsed(Time, 10000)) {
			count.remove(d);
			Time = System.currentTimeMillis();
		}
		if (Reach > MaxReach) {
			this.dumplog(d,
					"Logged for Reach Type B; Count Increase (+1); Reach: " + Reach2 + ", MaxReach: " + MaxReach + ", Damager Velocity: "
							+ d.getVelocity().length() + ", " + "Player Velocity: "
							+ p.getVelocity().length() + "; New Count: " + Count);
			count.put(d, Count + 1);
		} else {
			if (Count >= -2) {
				count.put(d, Count - 1);
			}
		}
		if (Reach2 > 6) {
			e.setCancelled(true);
		}
		if (Count >= 2 && Reach > MaxReach && Reach < 20.0) {
			count.remove(d);
			if (Latency.getLag(p) < 115) {
				getAntiCheat().logCheat(this, d, Reach + " > " + MaxReach + " MS: " + PingD + " Velocity Difference: " + speedToVelocityDif, "(Type: B)");

			}
			dumplog(d, "Logged for Reach Type B; Reach: " + Reach2 + " > " + "Max reach:" + MaxReach);
			return;
		}
	}
}