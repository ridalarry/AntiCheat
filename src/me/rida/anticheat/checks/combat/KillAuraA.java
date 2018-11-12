package me.rida.anticheat.checks.combat;

import com.comphenix.protocol.wrappers.EnumWrappers;

import java.util.*;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.packets.events.PacketUseEntityEvent;
import me.rida.anticheat.utils.TimeUtil;

public class KillAuraA extends Check {
	public static Map<UUID, Long> LastMS;
	public static Map<UUID, List<Long>> Clicks;
	public static Map<UUID, Map.Entry<Integer, Long>> ClickTicks;

	public KillAuraA(final AntiCheat AntiCheat) {
		super("KillAuraA", "KillAura",  CheckType.Combat, AntiCheat);
		this.LastMS = new HashMap<>();
		this.Clicks = new HashMap<>();
		this.ClickTicks = new HashMap<>();

		this.setEnabled(true);
		this.setBannable(true);
		this.setViolationResetTime(250000);
		this.setMaxViolations(10);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onLog(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		UUID u = p.getUniqueId();

		if (LastMS.containsKey(u)) {
			LastMS.remove(u);
		}
		if (Clicks.containsKey(u)) {
			Clicks.remove(u);
		}
		if (ClickTicks.containsKey(u)) {
			ClickTicks.remove(u);
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void UseEntity(PacketUseEntityEvent e) {
		if (e.getAction() != EnumWrappers.EntityUseAction.ATTACK
				|| !(e.getAttacked() instanceof Player)) {
			return;
		}

		Player p = e.getAttacker();
		UUID u = p.getUniqueId();
		int Count = 0;
		long Time = System.currentTimeMillis();
		if (ClickTicks.containsKey(u)) {
			Count = ClickTicks.get(u).getKey();
			Time = ClickTicks.get(u).getValue();
		}
		if (LastMS.containsKey(u)) {
			long MS = TimeUtil.nowlong() - LastMS.get(u);
			if (MS > 500L || MS < 5L) {
				LastMS.put(p.getUniqueId(), TimeUtil.nowlong());
				return;
			}
			if (Clicks.containsKey(u)) {
				List<Long> Clicks = this.Clicks.get(u);
				if (Clicks.size() == 10) {
					this.Clicks.remove(u);
					Collections.sort(Clicks);
					final long Range = Clicks.get(Clicks.size() - 1) - Clicks.get(0);
					if (Range < 30L) {
						++Count;
						Time = System.currentTimeMillis();
						this.dumplog(p, "Logged for KillAura Type A; New Range: " + Range +"; New Count: " + Count);
					}
				} else {
					Clicks.add(MS);
					this.Clicks.put(u, Clicks);
				}
			} else {
				final List<Long> Clicks = new ArrayList<Long>();
				Clicks.add(MS);
				this.Clicks.put(p.getUniqueId(), Clicks);
			}
		}
		if (ClickTicks.containsKey(u) && TimeUtil.elapsed(Time, 5000L)) {
			Count = 0;
			Time = TimeUtil.nowlong();
		}
		if ((Count > 2 && this.getAntiCheat().getLag().getPing(p) < 100)
				|| (Count > 4 && this.getAntiCheat().getLag().getPing(p) <= 400)) {
				dumplog(p, "Logged. Count: " + Count);
			Count = 0;
			if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
                || getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
				return;
			}
			getAntiCheat().logCheat(this, p, "Click Pattern", "(Type: A)");
			ClickTicks.remove(p.getUniqueId());
		} else if (this.getAntiCheat().getLag().getPing(p) > 400) {
			dumplog(p, "Would set off Killaura (Click Pattern) but latency is too high!");
		}
		LastMS.put(p.getUniqueId(), TimeUtil.nowlong());
		ClickTicks.put(p.getUniqueId(), new AbstractMap.SimpleEntry<Integer, Long>(Count, Time));
	}
}