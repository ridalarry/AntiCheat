package me.rida.anticheat.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.data.DataPlayer;
import me.rida.anticheat.utils.needscleanup.UtilsA;

/**
 * Created by Mr_JaVa_ on 2018-04-08 Package cc.funkemunky.dreya.util
 */
public class SetBackSystem implements Listener {
    public static void setBack(Player p) {
        DataPlayer data = AntiCheat.getInstance().getDataManager().getData(p);
        if (data != null) {
            if (!data.isShouldSetBack()) {
                data.setShouldSetBack(true);
            }
        }
    }
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        DataPlayer data = AntiCheat.getInstance().getDataManager().getData(p);
        if (data != null) {
            if (data.isShouldSetBack()) {
                if (data.getSetBackTicks() >= 5) {
                    Location setback = data.getSetbackLocation() != null ? data.getSetbackLocation() : e.getFrom();
                    e.setTo(setback);
                    data.setShouldSetBack(false);
                } else {
                    Location setback = data.getSetbackLocation() != null ? data.getSetbackLocation() : e.getFrom();
                    e.setTo(setback);
                    data.setSetBackTicks(data.getSetBackTicks() + 1);
                }
            } else if(UtilsA.isOnGround(p)) {
                data.setSetbackLocation(e.getFrom());
            }
        }
    }
}
