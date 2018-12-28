package me.rida.anticheat.checks.combat;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.packets.PacketPlayerType;
import me.rida.anticheat.packets.events.PacketAttackEvent;
import me.rida.anticheat.utils.PlayerUtil;

public class ReachE extends Check {


	public ReachE(AntiCheat AntiCheat) {
		super("ReachE", "Reach",  CheckType.Combat, true, false, false, false, true, 20, 1, 600000L, AntiCheat);
	}
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	private void onAttack(PacketAttackEvent e) {
		if (e.getType() != PacketPlayerType.USE
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()) {
			return;
		}
		Player p = (Player) e.getPlayer();
		Entity d = e.getEntity();
		if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
			return;
		}
		double YawDifference = Math.abs(180 - Math.abs(d.getLocation().getYaw() - p.getLocation().getYaw()));
		double Difference = PlayerUtil.getEyeLocation(p).distance(d.getLocation()) - 0.35;
		int Ping = getAntiCheat().getLag().getPing(p);
		double TPS = getAntiCheat().getLag().getTPS();
		double maxReach = 3.0 + d.getVelocity().length();
		if (p.getAllowFlight()) {
			maxReach += p.getFlySpeed();
		}

		if (p.isSprinting()) {
			maxReach += 0.2;
		}
		if (!(d instanceof Player)) {
			maxReach += 1.0;
		}
		if (PlayerUtil.isNearSlime(p.getLocation())
				|| PlayerUtil.isNearSlime(d.getLocation())) {
			maxReach += 1.0;
		}
		if (d instanceof Slime || d instanceof Spider || d instanceof Giant) {
			maxReach += 1.0;
		}
		if (p.getGameMode().equals(GameMode.CREATIVE)) {
			maxReach += 1.0;
		}
		if (p.getLocation().getY() > d.getLocation().getY()) {
			Difference = p.getLocation().getY() - p.getLocation().getY();
			maxReach += Difference / 2.5;
		} else if (p.getLocation().getY() > p.getLocation().getY()) {
			Difference = p.getLocation().getY() - p.getLocation().getY();
			maxReach += Difference / 2.5;
		}
		for (PotionEffect effect : p.getActivePotionEffects()) {
			if (effect.getType().equals(PotionEffectType.SPEED)) {
				maxReach += 0.2D * (effect.getAmplifier() + 1);
			}
		}
		if (TPS <20) {
			double TPSMultiplier = TPS / 20;
			double tmp = maxReach / TPSMultiplier;
			maxReach = tmp;
		}
		float velocity = (float)((Ping*0.0025) + Math.abs(d.getVelocity().length()) * 0.8);
		maxReach += velocity;
		maxReach += Ping < 250 ? Ping * 0.01262 : Ping * 0.0415;
		maxReach += YawDifference * 0.008;
		double ChanceVal = Math.round(Math.abs((Difference - maxReach) * 100));
		double x = Math.abs(Math.abs(p.getLocation().getX()) - Math.abs(d.getLocation().getX()));
		double y = Math.abs(Math.abs(p.getLocation().getY()) - Math.abs(d.getLocation().getY()));
		double z = Math.abs(Math.abs(p.getLocation().getZ()) - Math.abs(d.getLocation().getZ()));
		double distance = x+y+z;
		double Reach1 = Difference - maxReach;
		double Reach = Reach1;
		if (p.getGameMode().equals(GameMode.CREATIVE)) {
			Reach += 4;
		}
		else {
			Reach += 3;
		}
		String en = d.getName().toString();
		if (maxReach < Difference) {
			getAntiCheat().logCheat(this, p, "Attacked: " + en + "; Reach: " + Reach + "; Max Reach: " + maxReach + "; Distance: " + distance + "; Chance: " + ChanceVal + "%", "(Type: E)");
		}
	}
}