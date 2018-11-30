package me.rida.anticheat.checks.combat;

import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.utils.Color;

public class HitBoxB extends Check {
	private final double HITBOX_LENGTH = 1.05;

	public HitBoxB(AntiCheat AntiCheat) {
		super("HitBoxB", "HitBox", CheckType.Combat, true, false, false, false, 10, 1, 600000L, AntiCheat);
		setEnabled(true);
		setBannable(false);
		setJudgementDay(false);
		
		setAutobanTimer(false);
		
		setMaxViolations(10);
		setViolationsToNotify(1);
		setViolationResetTime(600000L);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void onHitPlayer(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player) || !(e.getEntity() instanceof Player)) {
			return;
		}
		Player p = (Player)e.getDamager();
		Player p2 = (Player)e.getEntity();
		if (p.getGameMode().equals(GameMode.CREATIVE)
				|| p2.getGameMode().equals(GameMode.CREATIVE)
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()
				|| getAntiCheat().getLag().getPing(p2) > getAntiCheat().getPingCancel()) {
			return;
		}
		if (!this.hasInHitBox((LivingEntity)p2)) {
			getAntiCheat().logCheat(this, p, Color.Red + "Experemental", "(Type: B)");
		}
	}

	private boolean hasInHitBox(LivingEntity e) {
		boolean bl = false;
		Vector vector = e.getLocation().toVector().subtract(e.getLocation().toVector());
		Vector vector2 = e.getLocation().toVector().subtract(e.getLocation().toVector());
		if (!(e.getLocation().getDirection().normalize().crossProduct(vector).lengthSquared() >= this.HITBOX_LENGTH && e.getLocation().getDirection().normalize().crossProduct(vector2).lengthSquared() >= this.HITBOX_LENGTH || vector.normalize().dot(e.getLocation().getDirection().normalize()) < 0.0 && vector2.normalize().dot(e.getLocation().getDirection().normalize()) < 0.0)) {
			bl = true;
		}
		return bl;
	}
}