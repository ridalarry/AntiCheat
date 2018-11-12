package me.rida.anticheat.checks.combat;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.utils.Color;
import me.rida.anticheat.utils.PlayerUtil;

public class ReachD extends Check {

	public static HashMap<UUID, Integer> toBan;
	
	public ReachD(AntiCheat AntiCheat) {
		super("ReachD", "Reach",  CheckType.Combat, AntiCheat);

		setEnabled(true);
		setBannable(false);

		setViolationResetTime(30000);
		setViolationsToNotify(1);
		setMaxViolations(9);
		
		toBan = new HashMap<UUID, Integer>();
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onATTACK(EntityDamageByEntityEvent e) {
		if (!e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)
				|| !(e.getDamager() instanceof Player)
				|| !(e.getEntity() instanceof Player)
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()) {
			return;
		}

		Player p = (Player) e.getDamager();
		Player d = (Player) e.getEntity();
		if (d.getAllowFlight()
				|| p.getAllowFlight()
		        || getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
		        || getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()
		        || getAntiCheat().getLag().getPing(d) > getAntiCheat().getPingCancel()) {
			return;
		}
		double YawDifference = Math.abs(180 - Math.abs(d.getLocation().getYaw() - p.getLocation().getYaw()));
		double Difference = PlayerUtil.getEyeLocation(p).distance(d.getEyeLocation()) - 0.35;

		int Ping = getAntiCheat().getLag().getPing(p);
		double TPS = getAntiCheat().getLag().getTPS();
		double MaxReach = 4.0 + d.getVelocity().length();

		if (p.isSprinting()) {
			MaxReach += 0.2;
		}

		if (p.getLocation().getY() > d.getLocation().getY()) {
			Difference = p.getLocation().getY() - p.getLocation().getY();
			MaxReach += Difference / 2.5;
		} else if (p.getLocation().getY() > p.getLocation().getY()) {
			Difference = p.getLocation().getY() - p.getLocation().getY();
			MaxReach += Difference / 2.5;
		}
		for (PotionEffect effect : p.getActivePotionEffects()) {
			if (effect.getType().equals(PotionEffectType.SPEED)) {
				MaxReach += 0.2D * (effect.getAmplifier() + 1);
			}
		}
		
		double velocity = p.getVelocity().length() + d.getVelocity().length();
		
		MaxReach += velocity * 1.5;
		MaxReach += Ping < 250 ? Ping * 0.00212 : Ping * 0.031;
		MaxReach += YawDifference * 0.008;
		
		double ChanceVal = Math.round(Math.abs((Difference - MaxReach) * 100));

		if (ChanceVal > 100) {
			ChanceVal = 100;
		}

		if (MaxReach < Difference) {
			this.dumplog(p, "Logged for Reach Type D; Reach: " + Difference
					+ "; MaxReach; " + MaxReach + "; Chance: " + ChanceVal + "%" + "; Ping: " + Ping + "; TPS: " + TPS);
			
			getAntiCheat().logCheat(this, p, Color.Red + "Experimental" + "; Reach: " + Difference
				+ "; MaxReach; " + MaxReach + "; Chance: " + ChanceVal + "%" + "; Ping: " + Ping + "; TPS: " + TPS, "(Type: D)");
		}
	}

}