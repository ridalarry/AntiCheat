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
import me.rida.anticheat.utils.MathUtil;
import me.rida.anticheat.utils.PlayerUtil;

public class FastLadderA extends Check {
	
	public static Map<Player, Integer> count;

	public FastLadderA(AntiCheat AntiCheat) {
		super("FastLadderA", "FastLadder", CheckType.Movement, AntiCheat);

		setEnabled(true);
		setBannable(true);
		setMaxViolations(7);
		
		count = new WeakHashMap<Player, Integer>();
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void checkFastLadder(PlayerMoveEvent e) {
		Player p = e.getPlayer();

		if(e.isCancelled()
				|| (e.getFrom().getY() == e.getTo().getY())
				|| p.getAllowFlight()
				|| getAntiCheat().getLastVelocity().containsKey(p.getUniqueId())
				|| !PlayerUtil.isOnClimbable(p, 1) 
				|| !PlayerUtil.isOnClimbable(p, 0)
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
		        || getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
			return;
		}

		int Count = count.getOrDefault(p, 0);
		double OffsetY = MathUtil.offset(MathUtil.getVerticalVector(e.getFrom().toVector()),
				MathUtil.getVerticalVector(e.getTo().toVector()));
		double Limit = 0.13;
		
		double updown = e.getTo().getY() - e.getFrom().getY();
		if (updown <= 0) {
			return;
		}

		
		
		if (OffsetY > Limit) {
			Count++;
			this.dumplog(p, "Logged for FastLadder Type A;  New Count: " + Count + " (+1); Speed: " + OffsetY + "; Max: " + Limit);
		} else {
			Count = Count > -2 ? Count - 1 : 0;
		}

		long percent = Math.round((OffsetY - Limit) * 120);
		
		if (Count > 11) {
			Count = 0;
			this.dumplog(p,
					"Logged for FastLadder Type A; Speed:" + OffsetY + "; Max: " + Limit + "; New Count: " + Count);
			this.getAntiCheat().logCheat(this, p, percent + "% faster than normal", "(Type: A)");
		}
		count.put(p, Count);
	}

}
