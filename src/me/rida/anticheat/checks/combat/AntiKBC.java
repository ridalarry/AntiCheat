package me.rida.anticheat.checks.combat;

import me.rida.anticheat.AntiCheat;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import me.rida.anticheat.checks.Check;
import me.rida.anticheat.utils.Color;
import me.rida.anticheat.utils.UtilVelocity;
public class AntiKBC extends Check {

    public AntiKBC(AntiCheat AntiCheat) {
        super("AntiKBC", "AntiKB", AntiCheat);
		setEnabled(true);
		setMaxViolations(10);
		setBannable(false);
		setViolationsToNotify(1);
    }
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
    	if (e.getEntity() instanceof  Player) {
    		Player p = (Player) e.getEntity();
    		if(p.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
    			Entity damager = (Player) p.getLastDamageCause().getEntity();
    			Entity damagerx = p.getLastDamageCause().getEntity();
    			if (UtilVelocity.didTakeVelocity(p)) {
    				return;
    			}
    			else {
    				if (!(UtilVelocity.didTakeVelocity(p))) {
    					if(p.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
    						if (damager != p){
    							
    						
    					getAntiCheat().logCheat(this, p, Color.Red + "Experemental " + damager + " " + damagerx, "(Type: C)");
    					}
    					else {
    						return;
    				}	
    			}
    			
    		}
    	}
    }
    
}
    	}
}