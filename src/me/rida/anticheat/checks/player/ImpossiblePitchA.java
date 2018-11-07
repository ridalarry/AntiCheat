package me.rida.anticheat.checks.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;

public class ImpossiblePitchA extends Check {
      public ImpossiblePitchA(AntiCheat AntiCheat) {
        super("ImpossiblePitchA", "ImpossiblePitch", AntiCheat);
    }
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        double Pitch = e.getPlayer().getLocation().getPitch();
        if (Pitch > 90 || Pitch < -90) {
            getAntiCheat().logCheat(this, e.getPlayer(),"Players head went back too far. P:["+Pitch+"]", "(Type: A)");
        }
    }
}
