package me.rida.anticheat.checks.client;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;

public class VapeA extends Check implements PluginMessageListener {

	public VapeA(AntiCheat AntiCheat) {
		super("VapeA", "Vape", CheckType.Client, AntiCheat);
		setEnabled(true);
		setBannable(true);
		setMaxViolations(0);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	private void onPlayerJoin(PlayerJoinEvent event) {
		event.getPlayer().sendMessage("§8 §8 §1 §3 §3 §7 §8 ");
	}

	public void onPluginMessageReceived(String s, Player p, byte[] data) {
		@SuppressWarnings("unused")
		String str;
		try {
			str = new String(data);
		} catch (Exception ex) {
			str = "";
		}
		getAntiCheat().logCheat(this, p, "Banned for using Cracked Vape!", "(Type: A)");
	}

}
