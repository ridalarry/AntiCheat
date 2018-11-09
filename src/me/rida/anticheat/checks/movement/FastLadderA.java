package me.rida.anticheat.checks.movement;

import java.util.Map;
import java.util.WeakHashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.utils.a.BlockUtils;
import me.rida.anticheat.utils.a.MathUtils;
import me.rida.anticheat.utils.a.ServerUtils;
import me.rida.anticheat.utils.b.UtilsB;

public class FastLadderA extends Check {
	
	public Map<Player, Integer> count;

	public FastLadderA(AntiCheat AntiCheat) {
		super("FastLadderA", "FastLadder", AntiCheat);

		setEnabled(true);
		setBannable(true);
		setMaxViolations(7);
		
		count = new WeakHashMap<Player, Integer>();
	}

	@EventHandler
	public void checkFastLadder(PlayerMoveEvent e) {
		Player player = e.getPlayer();

		if(e.isCancelled()
				|| (e.getFrom().getY() == e.getTo().getY())
				|| getAntiCheat().isSotwMode()
				|| player.getAllowFlight()
				|| getAntiCheat().getLastVelocity().containsKey(player.getUniqueId())
				|| !UtilsB.isOnClimbable(player, 1) 
				|| !UtilsB.isOnClimbable(player, 0)) {
			return;
		}

		int Count = count.getOrDefault(player, 0);
		double OffsetY = MathUtils.offset(MathUtils.getVerticalVector(e.getFrom().toVector()),
				MathUtils.getVerticalVector(e.getTo().toVector()));
		double Limit = 0.13;
		
		double updown = e.getTo().getY() - e.getFrom().getY();
		if (updown <= 0) {
			return;
		}

		
		
		if (OffsetY > Limit) {
			Count++;
			this.dumplog(player, "[Illegitmate] New Count: " + Count + " (+1); Speed: " + OffsetY + "; Max: " + Limit);
		} else {
			Count = Count > -2 ? Count - 1 : 0;
		}

		long percent = Math.round((OffsetY - Limit) * 120);
		
		if (Count > 11) {
			Count = 0;
			this.dumplog(player,
					"Flagged for FastLadder; Speed:" + OffsetY + "; Max: " + Limit + "; New Count: " + Count);
			this.getAntiCheat().logCheat(this, player, percent + "% faster than normal", "(Type: A)");
		}
		count.put(player, Count);
	}

}
