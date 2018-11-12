package me.rida.anticheat.checks.combat;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.other.Latency;
import me.rida.anticheat.packets.events.PacketSwingArmEvent;
import me.rida.anticheat.utils.TimeUtil;

public class AutoClickerA extends Check {
	
	public static Map<UUID, Integer> clicks;
	public static Map<UUID, Long> recording;

	public AutoClickerA(AntiCheat AntiCheat) {
		super("AutoClickerA", "AutoClicker",  CheckType.Combat, AntiCheat);

		setEnabled(true);
		setBannable(true);
		setViolationsToNotify(1);
		setMaxViolations(5);
		
		clicks = new HashMap<UUID, Integer>();
		recording = new HashMap<UUID, Long>();
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onLog(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();

		if (clicks.containsKey(uuid)) {
			clicks.remove(uuid);
		}
		if(recording.containsKey(uuid)) {
			recording.remove(uuid);
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onSwing(PacketSwingArmEvent e) {
		Player p = e.getPlayer();

		if (getAntiCheat().getLag().getTPS() < 17
				|| Latency.getLag(p) > 100
        		|| getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
                || getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
			return;
		}
		
		int clicks = this.clicks.getOrDefault(this, 0);
		long time = recording.getOrDefault(p.getUniqueId(), System.currentTimeMillis());
		if(TimeUtil.elapsed(time, 1000L)) {
			if(clicks > 18) {
				getAntiCheat().logCheat(this, p, clicks + " Clicks/Second", "(Type: A)");
			}
			clicks = 0;
			recording.remove(p.getUniqueId());
		} else {
			clicks++;
		}
		
		this.clicks.put(p.getUniqueId(), clicks);
		recording.put(p.getUniqueId(), time);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onLeave(PlayerQuitEvent e) {
		if(clicks.containsKey(e.getPlayer().getUniqueId())) {
			clicks.remove(e.getPlayer().getUniqueId());
		}
		if(recording.containsKey(e.getPlayer().getUniqueId())) {
			recording.remove(e.getPlayer().getUniqueId());
		}
	}
}
