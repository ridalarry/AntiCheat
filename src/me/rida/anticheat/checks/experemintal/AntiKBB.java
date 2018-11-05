package me.rida.anticheat.checks.experemintal;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
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

    @EventHandler
    public void onQuit(PlayerQuitEvent playerQuitEvent) {
        Player player = playerQuitEvent.getPlayer();
        if (this.lastSprintStart.containsKey((Object)player)) {
            this.lastSprintStart.remove((Object)player);
        }
        if (this.lastSprintStop.containsKey((Object)player)) {
            this.lastSprintStop.remove((Object)player);
        }
    }

    @EventHandler
    public void Sprint(PlayerToggleSprintEvent playerToggleSprintEvent) {
        Player player = playerToggleSprintEvent.getPlayer();
        
        if (playerToggleSprintEvent.isSprinting() && this.lastSprintStop.containsKey((Object)player)) {
            int n = 0;
            //threshold
            int n2 = 1;
            long l = System.currentTimeMillis() - this.lastSprintStop.get((Object)player);
            n = l < 5 ? ++n : (l > 1000 ? --n : (n -= 2));
            if (n > n2) {
            	getAntiCheat().logCheat(this, player, Color.Red + "Experemental", "(Type: B)");
                n = 0;
            }
        }
        if (!playerToggleSprintEvent.isSprinting()) {
            this.lastSprintStop.put(player, System.currentTimeMillis());
        } else if (playerToggleSprintEvent.isSprinting()) {
            this.lastSprintStart.put(player, System.currentTimeMillis());
        }
    }
}

