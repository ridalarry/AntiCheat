package me.rida.anticheat.checks.combat;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.data.DataPlayer;
import me.rida.anticheat.utils.needscleanup.UtilsA;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CriticalsA extends Check {

    public CriticalsA(AntiCheat AntiCheat) {
        super("CriticalsA", "Criticals (Type: A)", AntiCheat);
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {
        if(!(e.getDamager() instanceof Player)) {
            return;
        }

        Player player = (Player) e.getDamager();

        if(!Bukkit.getOnlinePlayers().contains(player)) {
            return;
        }

        Entity entity = e.getEntity();
        DataPlayer data = AntiCheat.getInstance().getDataManager().getData(player);

        if(data.getAboveBlockTicks() > 0
                || UtilsA.isInWeb(player)
                || data.getWaterTicks() > 0
                || UtilsA.hasSlabsNear(player.getLocation())) {
            return;
        }

        int verbose = data.getCriticalsVerbose();

        if(player.getFallDistance() > 0 && data.getFallDistance() == 0) {
            if(++verbose > 3) {
            	getAntiCheat().logCheat(this, player, "Packet", null);
                verbose = 0;
            }
        } else {
            verbose = 0;
        }

        data.setCriticalsVerbose(verbose);
    }
}
