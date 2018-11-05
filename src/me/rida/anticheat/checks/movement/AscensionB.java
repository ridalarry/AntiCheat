package me.rida.anticheat.checks.movement;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Map;
import java.util.WeakHashMap;

public class AscensionB extends Check {

    private Map<Player, Integer> verbose = new WeakHashMap<>();
    private Map<Player, Float> lastYMovement = new WeakHashMap<>();

    public AscensionB(me.rida.anticheat.AntiCheat AntiCheat) {
        super("AscensionB", "Ascension", AntiCheat);
        setBannable(true);
        setEnabled(true);
        setMaxViolations(5);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        int verbose = this.verbose.getOrDefault(player, 0);
        float yDelta = (float) (e.getTo().getY() - e.getFrom().getY());
        if (player.getAllowFlight()
                || !lastYMovement.containsKey(player)
                || Math.abs(yDelta - lastYMovement.get(player)) > 0.002) return;
        if (verbose++ > 5) {
        	AntiCheat.Instance.logCheat(this, player, Math.abs(yDelta - lastYMovement.get(player)) + "<-" + 0.002, "(Type B)");
        }
        lastYMovement.put(player, yDelta);
        this.verbose.put(player, verbose);
    }
}