package me.rida.anticheat.checks.combat;

import java.text.DecimalFormat;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import me.rida.anticheat.checks.Check;
import me.rida.anticheat.utils.Color;
import me.rida.anticheat.AntiCheat;

public class HitBoxB
extends Check {
    private double HITBOX_LENGTH = 1.05;

    public HitBoxB(AntiCheat AntiCheat) {
        super("HitBoxB", "HitBox", AntiCheat);
		setEnabled(true);
		setMaxViolations(10);
		setBannable(false);
		setViolationsToNotify(1);
    }

    @EventHandler
    public void onHitPlayer(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (!(entityDamageByEntityEvent.getDamager() instanceof Player)) {
            return;
        }
        if (!(entityDamageByEntityEvent.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player)entityDamageByEntityEvent.getDamager();
        Player player2 = (Player)entityDamageByEntityEvent.getDamager();
        if (!this.hasInHitBox((LivingEntity)player2)) {
        	getAntiCheat().logCheat(this, player, Color.Red + "Experemental", "(Type: B)");
             }
    }

    public boolean hasInHitBox(LivingEntity livingEntity) {
        boolean bl = false;
        Vector vector = livingEntity.getLocation().toVector().subtract(livingEntity.getLocation().toVector());
        Vector vector2 = livingEntity.getLocation().toVector().subtract(livingEntity.getLocation().toVector());
        if (!(livingEntity.getLocation().getDirection().normalize().crossProduct(vector).lengthSquared() >= this.HITBOX_LENGTH && livingEntity.getLocation().getDirection().normalize().crossProduct(vector2).lengthSquared() >= this.HITBOX_LENGTH || vector.normalize().dot(livingEntity.getLocation().getDirection().normalize()) < 0.0 && vector2.normalize().dot(livingEntity.getLocation().getDirection().normalize()) < 0.0)) {
            bl = true;
        }
        return bl;
    }
}

