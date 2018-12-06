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
import me.rida.anticheat.packets.PacketPlayerType;
import me.rida.anticheat.packets.events.PacketKillauraEvent;
import me.rida.anticheat.packets.events.PacketUseEntityEvent;

public class KillAuraK extends Check {
	
	private Map<UUID, Integer> verbose;
	private Map<UUID, Long> lastArmSwing;
	
	public KillAuraK(AntiCheat AntiCheat) {
		super("KillauraG", "Killaura", CheckType.Combat, true, false, false, false, 10, 1, 600000L, AntiCheat);
		
		setEnabled(true);
		setBannable(false);
		
		verbose = new HashMap<UUID, Integer>();
		lastArmSwing = new HashMap<UUID, Long>();
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onQuit(PlayerQuitEvent e) {
		UUID uuid = e.getPlayer().getUniqueId();
		
		if(verbose.containsKey(uuid)) {
			verbose.remove(uuid);
		}
		if(lastArmSwing.containsKey(uuid)) {
			lastArmSwing.containsKey(uuid);
		}
	}
	
	@EventHandler
	public void onHit(PacketUseEntityEvent e) { 
		
		if(!getAntiCheat().isEnabled()) {
			return;
		}
		
		Player player = (Player) e.getAttacker();
		
		int verbose = this.verbose.getOrDefault(player.getUniqueId(), 0);
		
		if(player.isDead()) {
			verbose++;
		} else if(this.verbose.containsKey(player.getUniqueId())) {
			this.verbose.remove(player.getUniqueId());
			return;
		}
		
		if(verbose > 4) {
			verbose = 0;
			getAntiCheat().logCheat(this, player, "Hit another player while dead.", "(Type: K)");
		}
		
		this.verbose.put(player.getUniqueId(), verbose);
	}
	
	@EventHandler
	public void onSwing(PacketKillauraEvent e) {
		if(!getAntiCheat().isEnabled()
				|| getAntiCheat().getLag().getTPS() < 19) {
			return;
		}
		
		Player player = e.getPlayer();
		if(e.getType() == PacketPlayerType.ARM_SWING) {
		    lastArmSwing.put(player.getUniqueId(), System.currentTimeMillis());
		}
		
		if(e.getType() == PacketPlayerType.USE) {
			long lastArmSwing = this.lastArmSwing.getOrDefault(player.getUniqueId(), System.currentTimeMillis());
			
			if((System.currentTimeMillis() - lastArmSwing) > 100 && Latency.getLag(player) < 50) {
				getAntiCheat().logCheat(this, player, "Missed while looking at player", "(Type: K)");
			}
		}
	}
	
	

}