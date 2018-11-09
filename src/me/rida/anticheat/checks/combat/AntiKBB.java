package me.rida.anticheat.checks.combat;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;

import me.rida.anticheat.checks.Check;
import me.rida.anticheat.utils.Color;
import me.rida.anticheat.AntiCheat;

public class AntiKBB
extends Check {
    private Map<Player, Long> lastSprintStart = new HashMap<Player, Long>();
    private Map<Player, Long> lastSprintStop = new HashMap<Player, Long>();

    public AntiKBB(AntiCheat AntiCheat) {
        super("AntiKBB", "AntiKB", AntiCheat);
		setEnabled(true);
		setMaxViolations(10);
		setBannable(false);
		setViolationsToNotify(1);
    }

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (this.lastSprintStart.containsKey((Object)player)) {
            this.lastSprintStart.remove((Object)player);
        }
        if (this.lastSprintStop.containsKey((Object)player)) {
            this.lastSprintStop.remove((Object)player);
        }
    }

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void Sprint(PlayerToggleSprintEvent e) {
        Player p = e.getPlayer();
        if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
                || getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
        	return;
        }
        
        if (e.isSprinting() && this.lastSprintStop.containsKey((Object)p)) {
            int n = 0;
            int n2 = 1;
            long l = System.currentTimeMillis() - this.lastSprintStop.get((Object)p);
            n = l < 5 ? ++n : (l > 1000 ? --n : (n -= 2));
            if (n > n2) {
            	getAntiCheat().logCheat(this, p, Color.Red + "Experemental", "(Type: B)");
                n = 0;
            }
        }
        if (!e.isSprinting()) {
            this.lastSprintStop.put(p, System.currentTimeMillis());
        } else if (e.isSprinting()) {
            this.lastSprintStart.put(p, System.currentTimeMillis());
        }
    }
}

