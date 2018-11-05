/*
 * Decompiled with CFR 0_122.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.player.PlayerMoveEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.event.player.PlayerVelocityEvent
 *  org.bukkit.util.Vector
 */
package me.rida.anticheat.checks.combat;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.util.Vector;
import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.utils.needscleanup.UtilsC;
import me.rida.anticheat.utils.needscleanup.UtilsB;
import me.rida.anticheat.utils.needscleanup.UtilsA;
import me.rida.anticheat.utils.Ping;
import me.rida.anticheat.utils.UtilVelocity;

public class NoVelocity extends Check {
    private Map<Player, Long> lastVelocity = new HashMap<Player, Long>();
    private Map<Player, Integer> awaitingVelocity = new HashMap<Player, Integer>();
    private Map<Player, Double> totalMoved = new HashMap<Player, Double>();

    public NoVelocity(AntiCheat AntiCheat) {
        super("NoVelocity", "AntiKnockBack", AntiCheat);
		setMaxViolations(10);
		setViolationResetTime(3000);
		setBannable(false);
		setViolationsToNotify(5);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent playerQuitEvent) {
        Player player = playerQuitEvent.getPlayer();
        if (this.lastVelocity.containsKey((Object)player)) {
            this.lastVelocity.remove((Object)player);
        }
        if (this.awaitingVelocity.containsKey((Object)player)) {
            this.awaitingVelocity.remove((Object)player);
        }
        if (this.totalMoved.containsKey((Object)player)) {
            this.totalMoved.remove((Object)player);
        }
    }

    @EventHandler
    public void Move(PlayerMoveEvent playerMoveEvent) {
        double d;
        Player player = playerMoveEvent.getPlayer();
        if (UtilsC.isOnBlock(player, 0, new Material[]{Material.WEB}) || UtilsC.isOnBlock(player, 1, new Material[]{Material.WEB})) {
            return;
        }
        if (UtilsC.isHoveringOverWater(player, 1) || UtilsC.isHoveringOverWater(player, 0)) {
            return;
        }
        if (player.getAllowFlight()) {
            return;
        }
        if (Ping.getPing(player) > 400) {
            return;
        }
        int n = 0;
        if (this.awaitingVelocity.containsKey((Object)player)) {
            n = this.awaitingVelocity.get((Object)player);
        }
        long l = 0;
        if (this.lastVelocity.containsKey((Object)player)) {
            l = this.lastVelocity.get((Object)player);
        }
        if (player.getLastDamageCause() == null || player.getLastDamageCause().getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK && player.getLastDamageCause().getCause() != EntityDamageEvent.DamageCause.PROJECTILE) {
            n = 0;
        }
        if (System.currentTimeMillis() - l > 2000 && n > 0) {
            --n;
        }
        double d2 = 0.0;
        if (this.totalMoved.containsKey((Object)player)) {
            d2 = this.totalMoved.get((Object)player);
        }
        if ((d = playerMoveEvent.getTo().getY() - playerMoveEvent.getFrom().getY()) > 0.0) {
            d2 += d;
        }
        int n2 = 0;
        int n3 = 1;
        if (n > 0) {
            if (d2 < 0.3) {
                n2 += 9;
            } else {
                n2 = 0;
                d2 = 0.0;
                --n;
            }
            if (UtilsC.isOnGround(player, -1) || UtilsC.isOnGround(player, -2) || UtilsC.isOnGround(player, -3)) {
                n2 -= 9;
            }
        }
        if (n2 > n3) {
            if (d2 == 0.0) {
                if (Ping.getPing(player) > 500) {
                    return;
                
                }
            	getAntiCheat().logCheat(this, player, null, null);
            	} else {
                if (Ping.getPing(player) > 220) {
                    return;
                }
            	getAntiCheat().logCheat(this, player, null, null);            }
            n2 = 0;
            d2 = 0.0;
            --n;
        }
        this.awaitingVelocity.put(player, n);
        this.totalMoved.put(player, d2);
    }

    @EventHandler
    public void Velocity(PlayerVelocityEvent playerVelocityEvent) {
        double d;
        long l;
        Player player = playerVelocityEvent.getPlayer();
        if (UtilsC.isOnBlock(player, 0, new Material[]{Material.WEB}) || UtilsC.isOnBlock(player, 1, new Material[]{Material.WEB})) {
            return;
        }
        if (UtilsC.isHoveringOverWater(player, 1) || UtilsC.isHoveringOverWater(player, 0)) {
            return;
        }
        if (UtilsC.isOnGround(player, -1) || UtilsC.isOnGround(player, -2) || UtilsC.isOnGround(player, -3)) {
            return;
        }
        if (player.getAllowFlight()) {
            return;
        }
        if (this.lastVelocity.containsKey((Object)player) && (l = System.currentTimeMillis() - this.lastVelocity.get((Object)player)) < 500) {
            return;
        }
        Vector vector = playerVelocityEvent.getVelocity();
        double d2 = Math.abs(vector.getY());
        if (d2 > 0.0 && (d = (double)((int)(Math.pow(d2 + 2.0, 2.0) * 5.0))) > 20.0) {
            int n = 0;
            if (this.awaitingVelocity.containsKey((Object)player)) {
                n = this.awaitingVelocity.get((Object)player);
            }
            this.awaitingVelocity.put(player, ++n);
            this.lastVelocity.put(player, System.currentTimeMillis());
        }
    }
}

