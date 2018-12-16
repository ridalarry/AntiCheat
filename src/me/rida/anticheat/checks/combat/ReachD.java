package me.rida.anticheat.checks.combat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;

import com.comphenix.protocol.wrappers.EnumWrappers;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.other.Latency;
import me.rida.anticheat.packets.events.PacketUseEntityEvent;
import me.rida.anticheat.utils.MathUtil;
import me.rida.anticheat.utils.PlayerUtil;
import me.rida.anticheat.utils.TimeUtil;

public class ReachD extends Check {
	public static Map<Player, Map.Entry<Double, Double>> offsets;
	public static Map<Player, Long> reachTicks;
	public static ArrayList<Player> projectileHit;
	public ReachD(AntiCheat AntiCheat) {
		super("ReachD", "Reach",  CheckType.Combat, true, true, false, true, false, 5, 1, 30000L, AntiCheat);
		offsets = new HashMap<>();
		reachTicks = new HashMap<>();
		projectileHit = new ArrayList<>();
	}
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if (event.getFrom().getX() == event.getTo().getX() && event.getFrom().getZ() == event.getTo().getZ()) return;

		double OffsetXZ = MathUtil.offset(MathUtil.getHorizontalVector(event.getFrom().toVector()),
				MathUtil.getHorizontalVector(event.getTo().toVector()));
		double horizontal = Math.sqrt(Math.pow(event.getTo().getX() - event.getFrom().getX(), 2.0)
				+ Math.pow(event.getTo().getZ() - event.getFrom().getZ(), 2.0));
		ReachD.offsets.put(event.getPlayer(),
				new AbstractMap.SimpleEntry<>(OffsetXZ, horizontal));
	}
	@EventHandler
	public void onDmg(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player)
				|| e.getCause() != DamageCause.PROJECTILE) return;
		Player player = (Player) e.getDamager();
		projectileHit.add(player);
	}
	@EventHandler
	public void onDamage(PacketUseEntityEvent e) {
		if (e.getAction() != EnumWrappers.EntityUseAction.ATTACK
				|| !(e.getAttacked() instanceof Player)
				|| e.getAttacker().getAllowFlight()
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()) return;
		Player damager = e.getAttacker();
		Player player = (Player) e.getAttacked();
		double ydist = Math.abs(damager.getEyeLocation().getY() - player.getEyeLocation().getY());
		double Reach = MathUtil.trim(2,
				(PlayerUtil.getEyeLocation(damager).distance(player.getEyeLocation()) - ydist) - 0.32);
		int PingD = this.getAntiCheat().getLag().getPing(damager);
		int PingP = this.getAntiCheat().getLag().getPing(player);
		long attackTime = System.currentTimeMillis();
		if (ReachD.reachTicks.containsKey(damager)) {
			attackTime = reachTicks.get(damager);
		}
		double yawdif = Math.abs(180 - Math.abs(damager.getLocation().getYaw() - player.getLocation().getYaw()));
		if (Latency.getLag(damager) > 92 || Latency.getLag(player) > 92) return;
		double offsetsp = 0.0D;
		double lastHorizontal = 0.0D;
		double offsetsd = 0.0D;
		if (ReachD.offsets.containsKey(damager)) {
			offsetsd = (ReachD.offsets.get(damager)).getKey();
			lastHorizontal = (ReachD.offsets.get(damager)).getValue();
		}
		if (ReachD.offsets.containsKey(player)) {
			offsetsp = (ReachD.offsets.get(player)).getKey();
			lastHorizontal = (ReachD.offsets.get(player)).getValue();
		}
		Reach -= MathUtil.trim(2, offsetsd);
		Reach -= MathUtil.trim(2, offsetsp);
		double maxReach2 = 3.1;
		if (yawdif > 90) {
			maxReach2 += 0.38;
		}
		maxReach2 += lastHorizontal * 0.87;
		maxReach2 += ((PingD + PingP) / 2) * 0.0024;
		if (Reach > maxReach2 && TimeUtil.elapsed(attackTime, 1100) && !projectileHit.contains(player)) {
			this.dumplog(damager,
					"Logged for Reach Type D (First Hit Reach) " + Reach + " > " + maxReach2 + " blocks. Ping: "
							+ getAntiCheat().getLag().getPing(damager) + " TPS: " + getAntiCheat().getLag().getTPS()
							+ " Elapsed: " + TimeUtil.elapsed(attackTime));
			getAntiCheat().logCheat(this, damager, "(First Hit Reach) Range: " + Reach + " > " + maxReach2 + " Ping: "
					+ getAntiCheat().getLag().getPing(damager), "(Type: D)");
		}
		reachTicks.put(damager, TimeUtil.nowlong());
		projectileHit.remove(player);
	}
}