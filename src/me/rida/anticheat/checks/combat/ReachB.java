package me.rida.anticheat.checks.combat;

import java.util.AbstractMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.other.Latency;
import me.rida.anticheat.utils.MathUtils;
import me.rida.anticheat.utils.PlayerUtils;
import me.rida.anticheat.utils.UtilTime;

public class ReachB extends Check {

	public Map<Player, Integer> count;
	public Map<Player, Map.Entry<Double, Double>> offsets;

	public ReachB(AntiCheat AntiCheat) {
		super("ReachB", "Reach", AntiCheat);

		setEnabled(true);
		setMaxViolations(7);
		setViolationResetTime(30000);
		setBannable(true);
		setViolationsToNotify(1);

		offsets = new WeakHashMap<Player, Map.Entry<Double, Double>>();
		count = new WeakHashMap<Player, Integer>();
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if (event.getFrom().getX() == event.getTo().getX() && event.getFrom().getZ() == event.getTo().getZ()) {
			return;
		}
		double OffsetXZ = MathUtils.offset(MathUtils.getHorizontalVector(event.getFrom().toVector()),
				MathUtils.getHorizontalVector(event.getTo().toVector()));
		double horizontal = Math.sqrt(Math.pow(event.getTo().getX() - event.getFrom().getX(), 2.0)
				+ Math.pow(event.getTo().getZ() - event.getFrom().getZ(), 2.0));
		offsets.put(event.getPlayer(),
				new AbstractMap.SimpleEntry<Double, Double>(Double.valueOf(OffsetXZ), Double.valueOf(horizontal)));
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onDamage(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player)
				|| !(e.getEntity() instanceof Player)
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()) {
			return;
		}
		Player damager = (Player) e.getDamager();
		Player player = (Player) e.getEntity();
		double Reach = MathUtils.trim(2, PlayerUtils.getEyeLocation(damager).distance(player.getEyeLocation()) - 0.32);
		double Reach2 = MathUtils.trim(2, PlayerUtils.getEyeLocation(damager).distance(player.getEyeLocation()) - 0.32);

		double Difference;
		
		if (damager.getAllowFlight()
				|| player.getAllowFlight()) {
			return;
		}

		if (!count.containsKey(damager)) {
			count.put(damager, 0);
		}

		int Count = count.get(damager);
		long Time = System.currentTimeMillis();
		double MaxReach = 3.1;
		double YawDifference = Math.abs(damager.getEyeLocation().getYaw() - player.getEyeLocation().getYaw());
		double speedToVelocityDif = 0;
		double offsets = 0.0D;

		double lastHorizontal = 0.0D;
		if (this.offsets.containsKey(damager)) {
			offsets = (this.offsets.get(damager)).getKey().doubleValue();
			lastHorizontal = (this.offsets.get(damager)).getValue().doubleValue();
		}
		if (Latency.getLag(damager) > 92 || Latency.getLag(player) > 92) {
			return;
		}
		speedToVelocityDif = Math.abs(offsets - player.getVelocity().length());
		MaxReach += (YawDifference * 0.001);
		MaxReach += lastHorizontal * 1.5;
		MaxReach += speedToVelocityDif * 0.08;
		if (damager.getLocation().getY() > player.getLocation().getY()) {
			Difference = damager.getLocation().getY() - player.getLocation().getY();
			MaxReach += Difference / 2.5;
		} else if (player.getLocation().getY() > damager.getLocation().getY()) {
			Difference = player.getLocation().getY() - damager.getLocation().getY();
			MaxReach += Difference / 2.5;
		}
		MaxReach += damager.getWalkSpeed() <= 0.2 ? 0 : damager.getWalkSpeed() - 0.2;

		int PingD = this.getAntiCheat().getLag().getPing(damager);
		int PingP = this.getAntiCheat().getLag().getPing(player);
		MaxReach += ((PingD + PingP) / 2) * 0.0024;
		if(PingD > 400) {
		     MaxReach += 1.0D;
		}
		if (UtilTime.elapsed(Time, 10000)) {
			count.remove(damager);
			Time = System.currentTimeMillis();
		}
		if (Reach > MaxReach) {
			this.dumplog(damager,
					"Count Increase (+1); Reach: " + Reach2 + ", MaxReach: " + MaxReach + ", Damager Velocity: "
							+ damager.getVelocity().length() + ", " + "Player Velocity: "
							+ player.getVelocity().length() + "; New Count: " + Count);
			count.put(damager, Count + 1);
		} else {
			if (Count >= -2) {
				count.put(damager, Count - 1);
			}
		}
		if (Reach2 > 6) {
			e.setCancelled(true);
		}
		if (Count >= 2 && Reach > MaxReach && Reach < 20.0) {
			count.remove(damager);
			if (Latency.getLag(player) < 115) {
				getAntiCheat().logCheat(this, damager, Reach + " > " + MaxReach + " MS: " + PingD + " Velocity Difference: " + speedToVelocityDif, "(Type: B)");

			}
			dumplog(damager, "Logged for Reach" + Reach2 + " > " + MaxReach);
			return;
		}
	}

}