package me.rida.anticheat.checks.combat;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import com.comphenix.protocol.wrappers.EnumWrappers;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.packets.events.PacketUseEntityEvent;
import me.rida.anticheat.utils.TimeUtil;

public class AutoClickerB extends Check {

	public static Map<UUID, Long> LastMS;
	public static Map<UUID, List<Long>> Clicks;
	public static Map<UUID, Map.Entry<Integer, Long>> ClickTicks;

	public AutoClickerB(AntiCheat AntiCheat) {
		super("AutoClickerB", "AutoClicker",  CheckType.Combat, true, false, false, false, true, 12, 2, 600000L, AntiCheat);

		LastMS = new HashMap<>();
		Clicks = new HashMap<>();
		ClickTicks = new HashMap<>();
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	private void UseEntity(PacketUseEntityEvent e) {
		if (e.getAction() != EnumWrappers.EntityUseAction.ATTACK
				|| !((e.getAttacked()) instanceof Player)
				|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
				|| getAntiCheat().getLag().getPing(e.getAttacker()) > getAntiCheat().getPingCancel()) {
			return;
		}

		final Player damager = e.getAttacker();
		if (damager == null) {
			return;
		}
		int Count = 0;
		long Time = System.currentTimeMillis();
		if (ClickTicks.containsKey(damager.getUniqueId())) {
			Count = ClickTicks.get(damager.getUniqueId()).getKey();
			Time = ClickTicks.get(damager.getUniqueId()).getValue();
		}
		if (LastMS.containsKey(damager.getUniqueId())) {
			final long MS = TimeUtil.nowlong() - LastMS.get(damager.getUniqueId());
			if (MS > 500L || MS < 5L) {
				LastMS.put(damager.getUniqueId(), TimeUtil.nowlong());
				return;
			}
			if (Clicks.containsKey(damager.getUniqueId())) {
				final List<Long> Clicks = AutoClickerB.Clicks.get(damager.getUniqueId());
				if (Clicks.size() == 3) {
					AutoClickerB.Clicks.remove(damager.getUniqueId());
					Collections.sort(Clicks);
					final long Range = Clicks.get(Clicks.size() - 1) - Clicks.get(0);

					if (Range >= 0 && Range <= 2) {
						++Count;
						Time = System.currentTimeMillis();
						this.dumplog(damager,
								"Logged for AutoClicker Type B; New Count: " + Count + "; Range: " + Range + "; Ping: "
										+ getAntiCheat().getLag().getPing(damager) + "; TPS: " + getAntiCheat().getLag().getTPS());
					}
				} else {
					Clicks.add(MS);
					AutoClickerB.Clicks.put(damager.getUniqueId(), Clicks);
				}
			} else {
				final List<Long> Clicks = new ArrayList<>();
				Clicks.add(MS);
				AutoClickerB.Clicks.put(damager.getUniqueId(), Clicks);
			}
		}
		if (ClickTicks.containsKey(damager.getUniqueId()) && TimeUtil.elapsed(Time, 5000L)) {
			Count = 0;
			Time = TimeUtil.nowlong();
		}
		if ((Count > 4 && this.getAntiCheat().getLag().getPing(damager) < 100)
				|| (Count > 6 && this.getAntiCheat().getLag().getPing(damager) < 200)) {
			this.dumplog(damager, "Logged for AutoClicker Type B; Count: " + Count);
			Count = 0;
			this.getAntiCheat().logCheat(this, damager, "Continuous/Repeating Patterns", "(Type: B)");
			ClickTicks.remove(damager.getUniqueId());
		} else if (this.getAntiCheat().getLag().getPing(damager) > 250) {
			this.dumplog(damager, "Logged for AutoClicker Type B; Would set off Autoclicker (Constant) but latency is too high!");
		}
		LastMS.put(damager.getUniqueId(), TimeUtil.nowlong());
		ClickTicks.put(damager.getUniqueId(), new AbstractMap.SimpleEntry<>(Count, Time));
	}
}