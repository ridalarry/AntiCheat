package me.rida.anticheat.checks.movement;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
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

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        int verbose = this.verbose.getOrDefault(p, 0);
        float yDelta = (float) (e.getTo().getY() - e.getFrom().getY());
        if (p.getAllowFlight()
                || !lastYMovement.containsKey(p)
                || Math.abs(yDelta - lastYMovement.get(p)) > 0.002) return;
        if (verbose++ > 5) {
        	AntiCheat.Instance.logCheat(this, p, Math.abs(yDelta - lastYMovement.get(p)) + "<-" + 0.002, "(Type B)");
        }
        lastYMovement.put(p, yDelta);
        this.verbose.put(p, verbose);
    }
}