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
public class AutoClickerD extends Check {

	public Map<UUID, Integer> clicks;
	private Map<UUID, Long> recording;
	public AutoClickerD(AntiCheat AntiCheat) {
		super("AutoClickerD", "AutoClicker",  CheckType.Combat, true, true, false, false, 5, 1, 600000, AntiCheat);
		setEnabled(true);
		setBannable(false);
		setJudgementDay(false);
		
		setAutobanTimer(false);
		
		setMaxViolations(5);
		setViolationsToNotify(1);
		setViolationResetTime(600000L);

		clicks = new HashMap<UUID, Integer>();
		recording = new HashMap<UUID, Long>();
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
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
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
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
				getAntiCheat().logCheat(this, p, clicks + " Clicks/Second", "(Type: D)");
			}
			clicks = 0;
			recording.remove(p.getUniqueId());
		} else {
			clicks++;
		}

		this.clicks.put(p.getUniqueId(), clicks);
		recording.put(p.getUniqueId(), time);
	}
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onLeave(PlayerQuitEvent e) {
		if(clicks.containsKey(e.getPlayer().getUniqueId())) {
			clicks.remove(e.getPlayer().getUniqueId());
		}
		if(recording.containsKey(e.getPlayer().getUniqueId())) {
			recording.remove(e.getPlayer().getUniqueId());
		}
	}
}