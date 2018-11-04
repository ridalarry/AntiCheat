package me.rida.anticheat.checks.other;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;

public class VapeCracked extends Check implements PluginMessageListener {

	public VapeCracked(AntiCheat AntiCheat) {
		super("Vape", "Vape", AntiCheat);

		this.setEnabled(true);
		this.setBannable(true);

		this.setMaxViolations(0);
	}

	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent event) {
		event.getPlayer().sendMessage("§8 §8 §1 §3 §3 §7 §8 ");
	}

	public void onPluginMessageReceived(String s, Player player, byte[] data) {
		String str;
		try {
			str = new String(data);
		} catch (Exception ex) {
			str = "";
		}

		getAntiCheat().logCheat(this, player, "Using Cracked Vape!", "Banned");
	}

}
