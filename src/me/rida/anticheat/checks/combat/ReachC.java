package me.rida.anticheat.checks.combat;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.comphenix.protocol.wrappers.EnumWrappers;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.other.Latency;
import me.rida.anticheat.packets.events.PacketUseEntityEvent;
import me.rida.anticheat.utils.MathUtil;
import me.rida.anticheat.utils.PlayerUtil;
import me.rida.anticheat.utils.TimeUtil;

public class ReachC extends Check {

	public static Map<Player, Map.Entry<Double, Double>> offsets;
	public static Map<Player, Long> reachTicks;
	public static ArrayList<Player> projectileHit;

	public ReachC(AntiCheat AntiCheat) {
		super("ReachC", "Reach",  CheckType.Combat, AntiCheat);
		this.offsets = new HashMap<Player, Map.Entry<Double, Double>>();
		this.reachTicks = new HashMap<Player, Long>();
		this.projectileHit = new ArrayList<Player>();
		setViolationResetTime(30000);
		setEnabled(true);
		setBannable(true);
		setMaxViolations(5);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onMove(PlayerMoveEvent e) {
		if (e.getFrom().getX() == e.getTo().getX() && e.getFrom().getZ() == e.getTo().getZ()) {
			return;
		}
		double OffsetXZ = MathUtil.offset(MathUtil.getHorizontalVector(e.getFrom().toVector()),
				MathUtil.getHorizontalVector(e.getTo().toVector()));
		double horizontal = Math.sqrt(Math.pow(e.getTo().getX() - e.getFrom().getX(), 2.0)
				+ Math.pow(e.getTo().getZ() - e.getFrom().getZ(), 2.0));
		this.offsets.put(e.getPlayer(),
				new AbstractMap.SimpleEntry<Double, Double>(Double.valueOf(OffsetXZ), Double.valueOf(horizontal)));
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onDmg(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player)
				|| e.getCause() != DamageCause.PROJECTILE) {
			return;
		}

		Player p = (Player) e.getDamager();
		if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
                || getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
			return;
		}
		this.projectileHit.add(p);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onLogout(PlayerQuitEvent e) {
		if (offsets.containsKey(e.getPlayer())) {
			offsets.remove(e.getPlayer());
		}
		if (reachTicks.containsKey(e.getPlayer())) {
			reachTicks.remove(e.getPlayer());
		}
		if (projectileHit.contains(e.getPlayer())) {
			projectileHit.remove(e.getPlayer());
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onDamage(PacketUseEntityEvent e) {
		if (e.getAction() != EnumWrappers.EntityUseAction.ATTACK
				|| !(e.getAttacker() instanceof Player)
				|| e.getAttacker().getAllowFlight()
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()) {
			return;
		}
		Player d = (Player) e.getAttacker();
		Player p = (Player) e.getAttacked();

		double ydist = Math.abs(d.getEyeLocation().getY() - p.getEyeLocation().getY());
		double Reach = MathUtil.trim(2,
				(PlayerUtil.getEyeLocation(d).distance(p.getEyeLocation()) - ydist) - 0.32);
		int PingD = this.getAntiCheat().getLag().getPing(d);
		int PingP = this.getAntiCheat().getLag().getPing(p);

		long attackTime = System.currentTimeMillis();
		if (this.reachTicks.containsKey(d)) {
			attackTime = reachTicks.get(d);
		}
		double yawdif = Math.abs(180 - Math.abs(d.getLocation().getYaw() - p.getLocation().getYaw()));
		if (Latency.getLag(d) > 92 || Latency.getLag(p) > 92) {
			return;
		}

		double offsetsp = 0.0D;
		double lastHorizontal = 0.0D;
		double offsetsd = 0.0D;
		if (this.offsets.containsKey(d)) {
			offsetsd = ((this.offsets.get(d)).getKey()).doubleValue();
			lastHorizontal = ((this.offsets.get(d)).getValue()).doubleValue();
		}
		if (this.offsets.containsKey(p)) {
			offsetsp = ((this.offsets.get(p)).getKey()).doubleValue();
			lastHorizontal = ((this.offsets.get(p)).getValue()).doubleValue();
		}
		Reach -= MathUtil.trim(2, offsetsd);
		Reach -= MathUtil.trim(2, offsetsp);
		double maxReach2 = 3.1;
		if (yawdif > 90) {
			maxReach2 += 0.38;
		}
		maxReach2 += lastHorizontal * 0.87;

		maxReach2 += ((PingD + PingP) / 2) * 0.0024;
		if (Reach > maxReach2 && TimeUtil.elapsed(attackTime, 1100) && !projectileHit.contains(p)) {
			;
			this.dumplog(d,
					"Logged for Reach Type C (First Hit Reach) " + Reach + " > " + maxReach2 + " blocks. Ping: "
							+ getAntiCheat().getLag().getPing(d) + " TPS: " + getAntiCheat().getLag().getTPS()
							+ " Elapsed: " + TimeUtil.elapsed(attackTime));
			getAntiCheat().logCheat(this, d, "First Hit Reach", "(Type: C)");
		}
		reachTicks.put(d, TimeUtil.nowlong());
		projectileHit.remove(p);
	}

}
