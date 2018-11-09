package me.rida.anticheat.checks.combat;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Difficulty;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.utils.PlayerUtil;
import me.rida.anticheat.utils.TimeUtil;

public class RegenA extends Check {
	public RegenA(AntiCheat AntiCheat) {
		super("RegenA", "Regen", AntiCheat);

		setEnabled(true);
		setBannable(true);
		setViolationsToNotify(3);
		setMaxViolations(12);
		setViolationResetTime(60000L);
	}

	public static Map<UUID, Long> LastHeal = new HashMap<UUID, Long>();
	public static Map<UUID, Map.Entry<Integer, Long>> FastHealTicks = new HashMap<UUID, Map.Entry<Integer, Long>>();

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onLog(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();

		if (LastHeal.containsKey(uuid)) {
			LastHeal.remove(uuid);
		}
		if (FastHealTicks.containsKey(uuid)) {
			FastHealTicks.remove(uuid);
		}
	}

	public boolean checkFastHeal(Player p) {
		if (LastHeal.containsKey(p.getUniqueId())) {
			long l = LastHeal.get(p.getUniqueId()).longValue();
			LastHeal.remove(p.getUniqueId());
			if (System.currentTimeMillis() - l < 3000L) {
				return true;
			}
		}
		return false;
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onHeal(EntityRegainHealthEvent e) {
		Player p = (Player) e.getEntity();
		if (!e.getRegainReason().equals(EntityRegainHealthEvent.RegainReason.SATIATED)
				|| !(p instanceof Player)
				|| p.getWorld().getDifficulty().equals(Difficulty.PEACEFUL)
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()) {
			return;
		}
		int Count = 0;
		long Time = System.currentTimeMillis();
		if (FastHealTicks.containsKey(p.getUniqueId())) {
			Count = FastHealTicks.get(p.getUniqueId()).getKey().intValue();
			Time = FastHealTicks.get(p.getUniqueId()).getValue().longValue();
		}
		if (checkFastHeal(p) && !PlayerUtil.isFullyStuck(p) && !PlayerUtil.isPartiallyStuck(p)) {
			Count++;
		} else {
			Count = Count > 0 ? Count - 1 : Count;
		}
		
		if(Count > 2) {
			getAntiCheat().logCheat(this, p, null, "(Type: A)");
		}
		if (FastHealTicks.containsKey(p.getUniqueId()) && TimeUtil.elapsed(Time, 60000L)) {
			Count = 0;
			Time = TimeUtil.nowlong();
		}
		LastHeal.put(p.getUniqueId(), Long.valueOf(System.currentTimeMillis()));
		FastHealTicks.put(p.getUniqueId(),
				new AbstractMap.SimpleEntry<Integer, Long>(Count, Time));
	}
}
