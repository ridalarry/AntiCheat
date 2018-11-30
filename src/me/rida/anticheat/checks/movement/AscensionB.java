package me.rida.anticheat.checks.movement;
import java.util.Map;
import java.util.WeakHashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;

public class AscensionB extends Check {
	private Map<Player, Integer> verbose = new WeakHashMap<>();
	private Map<Player, Float> lastYMovement = new WeakHashMap<>();
	public AscensionB(me.rida.anticheat.AntiCheat AntiCheat) {
		super("AscensionB", "Ascension",  CheckType.Combat, true, true, false, false, 5, 1, 600000L, AntiCheat);
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		int verbose = this.verbose.getOrDefault(p, 0);
		float yDelta = (float) (e.getTo().getY() - e.getFrom().getY());
		if (p.getAllowFlight()
				|| !lastYMovement.containsKey(p)
				|| Math.abs(yDelta - lastYMovement.get(p)) > 0.002
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) return;
		if (verbose++ > 5) {
			AntiCheat.Instance.logCheat(this, p, Math.abs(yDelta - lastYMovement.get(p)) + "<-" + 0.002, "(Type B)");
		}
		lastYMovement.put(p, yDelta);
		this.verbose.put(p, verbose);
	}
} 
