package me.rida.anticheat.checks.player;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.data.DataPlayer;
import me.rida.anticheat.utils.TimerUtils;
import me.rida.anticheat.utils.UtilVelocity;
import me.rida.anticheat.utils.UtilsA;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class GroundSpoofA extends Check {
     public GroundSpoofA(AntiCheat AntiCheat) {
        super("GroundsSpoofA", "GroundSpoof" , AntiCheat);
		setEnabled(true);
		setMaxViolations(10);
		setBannable(false);
    }
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
         Player p = e.getPlayer();
          DataPlayer data = AntiCheat.getInstance().getDataManager().getData(p);
         if (data != null) {
            if (e.getTo().getY() > e.getFrom().getY()) {
                return;
            }
             if (data.isLastBlockPlaced_GroundSpoof()) {
                 if (TimerUtils.elapsed(data.getLastBlockPlacedTicks(),500L)) {
                     data.setLastBlockPlaced_GroundSpoof(false);
                 }
                 return;
             }
             Location to = e.getTo();
             Location from = e.getFrom();
             double diff = to.toVector().distance(from.toVector());
             int dist = UtilsA.getDistanceToGround(p);
             if (p.getLocation().add(0,-1.50,0).getBlock().getType() != Material.AIR) {
                 data.setGroundSpoofVL(0);
                 return;
             }
             if (e.getTo().getY() > e.getFrom().getY() || UtilsA.isOnGround4(p) || UtilVelocity.didTakeVelocity(p)) {
                 data.setGroundSpoofVL(0);
                 return;
             }
             if (p.isOnGround() && diff > 0.0 && !UtilsA.isOnGround(p) && dist >= 2 && e.getTo().getY() < e.getFrom().getY()) {
                 if (data.getGroundSpoofVL() >= 4) {
                     if (data.getAirTicks() >= 10) {
                         getAntiCheat().logCheat(this, p, "[1] Spoofed On-Ground Packet.", "(Type: A)");
                     } else {
                         getAntiCheat().logCheat(this, p, "[2] Spoofed On-Ground Packet.", "(Type: A)");
                     }
                 } else {
                     data.setGroundSpoofVL(data.getGroundSpoofVL()+1);
                 }
             }
         }
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
         Player p = e.getPlayer();
        DataPlayer data = AntiCheat.getInstance().getDataManager().getData(p);
         if (data != null) {
             if (!data.isLastBlockPlaced_GroundSpoof()) {
                 data.setLastBlockPlaced_GroundSpoof(true);
                 data.setLastBlockPlacedTicks(TimerUtils.nowlong());
             }
         }
    }
}
