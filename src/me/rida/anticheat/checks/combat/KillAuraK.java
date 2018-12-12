package me.rida.anticheat.checks.combat;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.other.Latency;
import me.rida.anticheat.packets.PacketPlayerType;
import me.rida.anticheat.packets.events.PacketKillauraEvent;
import me.rida.anticheat.packets.events.PacketUseEntityEvent;
import me.rida.anticheat.utils.ServerUtil;

public class KillAuraK extends Check {
	
	public static Map<UUID, Integer> verbose;
	public static Map<UUID, Long> lastArmSwing;
	
	public KillAuraK(AntiCheat AntiCheat) {
		super("KillauraK", "Killaura", CheckType.Combat, true, false, false, false, 10, 1, 600000L, AntiCheat);
		verbose = new HashMap<UUID, Integer>();
		lastArmSwing = new HashMap<UUID, Long>();
	}
	
	@EventHandler
	public void onHit(PacketUseEntityEvent e) { 
		
		if(!getAntiCheat().isEnabled()
				|| getAntiCheat().getLag().getTPS() < 19) {
			return;
		}
		
		Player player = (Player) e.getAttacker();
		
		int verbose = KillAuraK.verbose.getOrDefault(player.getUniqueId(), 0);
		
		if(player.isDead()) {
			verbose++;
		} else if(KillAuraK.verbose.containsKey(player.getUniqueId())) {
			KillAuraK.verbose.remove(player.getUniqueId());
			return;
		}
		
		if(verbose > 4) {
			verbose = 0;
			getAntiCheat().logCheat(this, player, "Attacking while dead.", "(Type: K)");
		}
		
		KillAuraK.verbose.put(player.getUniqueId(), verbose);
	}
	
	@EventHandler
	public void onSwing(PacketKillauraEvent e) {
		if(!getAntiCheat().isEnabled()
				|| getAntiCheat().getLag().getTPS() < 19
				|| ServerUtil.isBukkitVerison("1_13")) {
			return;
		}
		
		Player player = e.getPlayer();
		if(e.getType() == PacketPlayerType.ARM_SWING) {
		    lastArmSwing.put(player.getUniqueId(), System.currentTimeMillis());
		}
		
		if(e.getType() == PacketPlayerType.USE) {
			long lastArmSwing = KillAuraK.lastArmSwing.getOrDefault(player.getUniqueId(), System.currentTimeMillis());
			
			if((System.currentTimeMillis() - lastArmSwing) > 100 && Latency.getLag(player) < 50) {
				getAntiCheat().logCheat(this, player, "Missed while looking at victim.", "(Type: K)");
			}
		}
	}
	
	

}