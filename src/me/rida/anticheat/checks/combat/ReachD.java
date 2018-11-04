package me.rida.anticheat.checks.combat;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.utils.Chance;
import me.rida.anticheat.utils.Color;
import me.rida.anticheat.utils.needscleanup.UtilsA;

public class ReachD extends Check {

	public static HashMap<UUID, Integer> toBan;
	
	public ReachD(AntiCheat AntiCheat) {
		super("ReachD", "Reach (Type: D)", AntiCheat);

		this.setEnabled(true);
		this.setBannable(false);

		setViolationResetTime(30000);
		this.setViolationsToNotify(1);
		this.setMaxViolations(9);
		
		toBan = new HashMap<UUID, Integer>();
	}

	@EventHandler
	public void onATTACK(EntityDamageByEntityEvent e) {
		if (!e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)
				|| !(e.getEntity() instanceof Player) || !(e.getDamager() instanceof Player)
				|| getAntiCheat().isSotwMode()
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()) {
			return;
		}

		Player player = (Player) e.getDamager();
		Player damaged = (Player) e.getEntity();

		if (player.getAllowFlight()) {
			return;
		}

		double YawDifference = Math.abs(180 - Math.abs(damaged.getLocation().getYaw() - player.getLocation().getYaw()));
		double Difference = UtilsA.getEyeLocation(player).distance(damaged.getEyeLocation()) - 0.35;

		int Ping = getAntiCheat().getLag().getPing(player);
		double TPS = getAntiCheat().getLag().getTPS();
		double MaxReach = 4.0 + damaged.getVelocity().length();

		if (player.isSprinting()) {
			MaxReach += 0.2;
		}

		if (player.getLocation().getY() > damaged.getLocation().getY()) {
			Difference = player.getLocation().getY() - player.getLocation().getY();
			MaxReach += Difference / 2.5;
		} else if (player.getLocation().getY() > player.getLocation().getY()) {
			Difference = player.getLocation().getY() - player.getLocation().getY();
			MaxReach += Difference / 2.5;
		}
		for (PotionEffect effect : player.getActivePotionEffects()) {
			if (effect.getType().equals(PotionEffectType.SPEED)) {
				MaxReach += 0.2D * (effect.getAmplifier() + 1);
			}
		}
		
		double velocity = player.getVelocity().length() + damaged.getVelocity().length();
		
		MaxReach += velocity * 1.5;
		MaxReach += Ping < 250 ? Ping * 0.00212 : Ping * 0.031;
		MaxReach += YawDifference * 0.008;
		
		double ChanceVal = Math.round(Math.abs((Difference - MaxReach) * 100));

		if (ChanceVal > 100) {
			ChanceVal = 100;
		}

		if (MaxReach < Difference) {
			this.dumplog(player, "Logged for Reach Type A; Check is Bannable (so no special bans); Reach: " + Difference
					+ "; MaxReach; " + MaxReach + "; Chance: " + ChanceVal + "%" + "; Ping: " + Ping + "; TPS: " + TPS);
			Chance chance = Chance.LIKELY;
			if (ChanceVal >= 60) {
				chance = Chance.HIGH;
			}
			getAntiCheat().logCheat(this, player, Color.Red + "Experimental", null);
		}
	}

}