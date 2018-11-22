package me.rida.anticheat.checks.combat;

import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.packets.PacketPlayerType;
import me.rida.anticheat.packets.events.PacketAttackEvent;
import me.rida.anticheat.utils.MathUtil;
import me.rida.anticheat.AntiCheat;

import org.bukkit.GameMode;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class ReachA extends Check {

    public ReachA(AntiCheat AntiCheat) {
        super("ReachA", "Reach",  CheckType.Combat, AntiCheat);
		setEnabled(true);
		setMaxViolations(7);
		setBannable(true);
		setViolationResetTime(30000);
		setViolationsToNotify(1);
    }

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onAttack(PacketAttackEvent e) {
        Entity p2 = e.getEntity();
        Player p = e.getPlayer();
        if(e.getType() != PacketPlayerType.USE
                || e.getEntity() == null 
        		|| p2 instanceof Enderman 
        		|| p2.isDead()
        		|| p.getGameMode().equals(GameMode.CREATIVE)
        		|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
                || getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()){
            return;
        }


        double distance = MathUtil.getHorizontalDistance(p.getLocation(), p2.getLocation()) - 0.35;
        double maxReach = 4.2;
        double yawDifference = 180 - Math.abs(Math.abs(p.getEyeLocation().getYaw()) - Math.abs(p2.getLocation().getYaw()));

        maxReach+= Math.abs(p.getVelocity().length() + p2.getVelocity().length()) * 0.4;
        maxReach+= yawDifference * 0.01;

        if(maxReach < 4.2) maxReach = 4.2;
        

        if(distance > maxReach) {
        	getAntiCheat().logCheat(this, p, MathUtil.trim(3, distance) + " > " + MathUtil.trim(3, maxReach), "(Type: A)");
        }
    }
}
