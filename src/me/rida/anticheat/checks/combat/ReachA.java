package me.rida.anticheat.checks.combat;

import me.rida.anticheat.checks.Check;
import me.rida.anticheat.packets.PacketPlayerType;
import me.rida.anticheat.packets.events.PacketAttackEvent;
import me.rida.anticheat.utils.MathUtils;
import me.rida.anticheat.AntiCheat;

import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class ReachA extends Check {

    public ReachA(AntiCheat AntiCheat) {
        super("ReachA", "Reach", AntiCheat);
		setEnabled(true);
		setMaxViolations(7);
		setBannable(true);
		setViolationResetTime(30000);
		setViolationsToNotify(1);
    }

    @EventHandler
    public void onAttack(PacketAttackEvent e) {
        Entity entity = e.getEntity();
        Player player = e.getPlayer();
        if(e.getType() != PacketPlayerType.USE
                || e.getEntity() == null 
        		|| entity instanceof Enderman 
        		|| entity.isDead()){
            return;
        }


        double distance = MathUtils.getHorizontalDistance(player.getLocation(), entity.getLocation()) - 0.35;
        double maxReach = 4.2;
        double yawDifference = 180 - Math.abs(Math.abs(player.getEyeLocation().getYaw()) - Math.abs(entity.getLocation().getYaw()));

        maxReach+= Math.abs(player.getVelocity().length() + entity.getVelocity().length()) * 0.4;
        maxReach+= yawDifference * 0.01;

        if(maxReach < 4.2) maxReach = 4.2;
        

        if(distance > maxReach) {
        	getAntiCheat().logCheat(this, player, MathUtils.trim(3, distance) + " > " + MathUtils.trim(3, maxReach), "(Type: A)");
        }
    }
}
