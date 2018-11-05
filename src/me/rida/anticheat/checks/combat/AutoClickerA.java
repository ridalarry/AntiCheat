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
import me.rida.anticheat.checks.other.Latency;
import me.rida.anticheat.packets.events.PacketSwingArmEvent;
import me.rida.anticheat.utils.UtilTime;

public class AutoClickerA extends Check {
	
	public Map<UUID, Integer> clicks;
	private Map<UUID, Long> recording;

	public AutoClickerA(AntiCheat AntiCheat) {
		super("AutoClickerA", "AutoClicker", AntiCheat);

		setEnabled(true);
		setBannable(true);
		setViolationsToNotify(1);
		setMaxViolations(5);
		
		clicks = new HashMap<UUID, Integer>();
		recording = new HashMap<UUID, Long>();
	}

	@EventHandler
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

	@EventHandler
	public void onSwing(PacketSwingArmEvent e) {
		Player player = e.getPlayer();

		if (getAntiCheat().isSotwMode()
				|| getAntiCheat().getLag().getTPS() < 17
				|| Latency.getLag(player) > 100) {
			return;
		}
		
		int clicks = this.clicks.getOrDefault(this, 0);
		long time = recording.getOrDefault(player.getUniqueId(), System.currentTimeMillis());
		if(UtilTime.elapsed(time, 1000L)) {
			if(clicks > 18) {
				getAntiCheat().logCheat(this, player, clicks + " Clicks/Second", "(Type: A)");
			}
			clicks = 0;
			recording.remove(player.getUniqueId());
		} else {
			clicks++;
		}
		
		this.clicks.put(player.getUniqueId(), clicks);
		recording.put(player.getUniqueId(), time);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onLeave(PlayerQuitEvent e) {
		if(clicks.containsKey(e.getPlayer().getUniqueId())) {
			clicks.remove(e.getPlayer().getUniqueId());
		}
		if(recording.containsKey(e.getPlayer().getUniqueId())) {
			recording.remove(e.getPlayer().getUniqueId());
		}
	}
}
