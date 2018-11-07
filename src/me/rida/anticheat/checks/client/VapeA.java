package me.rida.anticheat.checks.client;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;

public class VapeA extends Check implements PluginMessageListener {

	public VapeA(AntiCheat AntiCheat) {
		super("VapeA", "Vape", AntiCheat);

		setEnabled(true);
		setBannable(true);

		setMaxViolations(0);
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

		getAntiCheat().logCheat(this, player, "Banned for using Cracked Vape!", "(Type: A)");
	}

}
