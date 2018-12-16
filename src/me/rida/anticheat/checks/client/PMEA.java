package me.rida.anticheat.checks.client;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.json.simple.parser.JSONParser;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;

public class PMEA extends Check implements PluginMessageListener, Listener {
	private static String type;
	private final JSONParser parser = new JSONParser();
	public final static Map<UUID, Map<String, String>> forgeMods;

	static {
		forgeMods = new HashMap<UUID, Map<String, String>>();
	}
	public PMEA(AntiCheat AntiCheat) {
		super("PMEA", "PME", CheckType.Client, true, false, false, false, true, 10, 1, 600000L, AntiCheat);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onJoin(PlayerJoinEvent e) {
		this.getClientType(e.getPlayer());
	}

	@Override
	@SuppressWarnings("unchecked")
	public void onPluginMessageReceived(String string, Player p, byte[] arrby) {
		ByteArrayDataInput byteArrayDataInput = ByteStreams.newDataInput((byte[])arrby);
		if ("ForgeMods".equals(byteArrayDataInput.readUTF())) {
			String string2 = byteArrayDataInput.readUTF();
			try {
				@SuppressWarnings("rawtypes")
				Map map = (Map)this.parser.parse(string2);
				forgeMods.put(p.getUniqueId(), map);
				String string3 = this.getClientType(p);
				if (string3 != null) {
					type = string3;
					getAntiCheat().logCheat(this, p, "[2] detection of a hack client!", "(Type: A)");
				}
			}
			catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}


	public String getClientType(Player p) {
		Map<String, String> map = forgeMods.get(p.getUniqueId());
		if (map != null) {
			if (map.containsKey("gc")) {
				type = "gc";
				getAntiCheat().logCheat(this, p, "Type: " + type, "(Type: A)");
				return "gc";
			}
			if (map.containsKey("ethylene")) {
				type = "ethylene";
				getAntiCheat().logCheat(this, p, "Type: " + type, "(Type: A)");
				return "ethylene";
			}
			if ("1.0".equals(map.get("OpenComputers"))) {
				type = "OpenComputers 1.0";
				getAntiCheat().logCheat(this, p, "Type: " + type, "(Type A)");
				return "C";
			}
			if ("1.7.6.git".equals(map.get("Schematica"))) {
				type = "Schematica 1.7.6.git";
				getAntiCheat().logCheat(this, p, "Type: " + type, "(Type: A)");
				return "Schematica 1.7.6.git";
			}
		}
		return null;
	}
}