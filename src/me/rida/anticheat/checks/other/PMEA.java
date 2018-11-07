package me.rida.anticheat.checks.other;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import me.rida.anticheat.checks.Check;
import me.rida.anticheat.utils.Color;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.json.simple.parser.JSONParser;
import me.rida.anticheat.AntiCheat;

public class PMEA extends Check implements PluginMessageListener, Listener {
    public static String type;
    private final JSONParser parser = new JSONParser();
    public static final Map<UUID, Map<String, String>> forgeMods;

    static {
        forgeMods = new HashMap<UUID, Map<String, String>>();
    }

    public PMEA(AntiCheat AntiCheat) {
        super("PMEA", "PME", AntiCheat);
		setEnabled(true);
		setMaxViolations(10);
		setBannable(false);
		setViolationsToNotify(1);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent playerJoinEvent) {
        this.getClientType(playerJoinEvent.getPlayer());
    }

    public void addVio(Player player) {
    	getAntiCheat().logCheat(this, player, Color.Red + "Experemental detection of a hack client!", "(Type: A)");
    }

    public void onPluginMessageReceived(String string, Player player, byte[] arrby) {
        ByteArrayDataInput byteArrayDataInput = ByteStreams.newDataInput((byte[])arrby);
        if ("ForgeMods".equals(byteArrayDataInput.readUTF())) {
            String string2 = byteArrayDataInput.readUTF();
            try {
                Map map = (Map)this.parser.parse(string2);
                forgeMods.put(player.getUniqueId(), map);
                String string3 = this.getClientType(player);
                if (string3 != null) {
                    type = string3;
                	getAntiCheat().logCheat(this, player, Color.Red + "Experemental", "(Type: B)");
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent playerQuitEvent) {
        forgeMods.remove(playerQuitEvent.getPlayer().getUniqueId());
    }

    public String getClientType(Player player) {
        Map<String, String> map = forgeMods.get(player.getUniqueId());
        if (map != null) {
            if (map.containsKey("gc")) {
                type = "A";
            	getAntiCheat().logCheat(this, player, Color.Red + "Experemental", type);
                return "A";
            }
            if (map.containsKey("ethylene")) {
                type = "B";
            	getAntiCheat().logCheat(this, player, Color.Red + "Experemental", type);
                return "B";
            }
            if ("1.0".equals(map.get("OpenComputers"))) {
                type = "C";
            	getAntiCheat().logCheat(this, player, Color.Red + "Experemental", type);
                return "C";
            }
            if ("1.7.6.git".equals(map.get("Schematica"))) {
                type = "D";
            	getAntiCheat().logCheat(this, player, Color.Red + "Experemental", type);
                return "D";
            }
        }
        return null;
    }
}

