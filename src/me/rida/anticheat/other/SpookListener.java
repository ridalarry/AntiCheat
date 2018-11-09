package me.rida.anticheat.other;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.rida.anticheat.checks.client.SpookA;
import me.rida.anticheat.utils.AngleUtil;
import me.rida.anticheat.utils.a.MathUtils;

public class SpookListener
implements Listener {
    public void playerSpookCheck(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (!(entityDamageByEntityEvent.getDamager() instanceof Player)) {
            return;
        }
        if (!(entityDamageByEntityEvent.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player)entityDamageByEntityEvent.getDamager();
        Player player2 = (Player)entityDamageByEntityEvent.getEntity();
        float f = MathUtils.getOffset(player, (LivingEntity)player2);
        SpookA.SpookAInstance().onAim(player, f);
    }
}

