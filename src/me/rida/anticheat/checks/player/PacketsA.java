package me.rida.anticheat.checks.player;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.data.DataPlayer;
import me.rida.anticheat.packets.PacketCoreB;
import me.rida.anticheat.packets.events.PacketPlayerEventA;
import me.rida.anticheat.utils.Color;
import me.rida.anticheat.utils.TimerUtils;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class PacketsA extends Check {
    private Map<UUID, Integer> packets;
    private Map<UUID, Integer> verbose;
    private Map<UUID, Long> lastPacket;
    private List<Player> toCancel;

    public PacketsA(AntiCheat AntiCheat) {
        super("PacketsA", "Packets", AntiCheat);
        packets = new HashMap<>();
        verbose = new HashMap<>();
        toCancel = new ArrayList<>();
        lastPacket = new HashMap<>();
		setEnabled(true);
		setBannable(false);
		setMaxViolations(10);
    }
    @EventHandler
    public void onLogout(PlayerQuitEvent e) {
        if(packets.containsKey(e.getPlayer().getUniqueId())) {
            packets.remove(e.getPlayer().getUniqueId());
        }
        if(verbose.containsKey(e.getPlayer().getUniqueId())) {
            verbose.remove(e.getPlayer().getUniqueId());
        }
        if(lastPacket.containsKey(e.getPlayer().getUniqueId())) {
            lastPacket.remove(e.getPlayer().getUniqueId());
        }
        if(toCancel.contains(e.getPlayer())) {
            toCancel.remove(e.getPlayer());
        }
    }
    @EventHandler
    public final void packetPlayer(PacketPlayerEventA event) {
        Player player = event.getPlayer();
        DataPlayer data = AntiCheat.getInstance().getDataManager().getData(player);

        int packets = this.packets.getOrDefault(player.getUniqueId(), 0);
        long Time = this.lastPacket.getOrDefault(player.getUniqueId(), System.currentTimeMillis());
        int verbose = this.verbose.getOrDefault(player.getUniqueId(), 0);

        if((System.currentTimeMillis() - data.getLastPacket()) > 100L) {
            toCancel.add(player);
        }
        double threshold = 42;
        if(TimerUtils.elapsed(Time, 1000L)) {
            if(toCancel.remove(player) && packets <= 67) {
                this.packets.put(player.getUniqueId(), 0);
               return;
            }
            if(packets > threshold + PacketCoreB.movePackets.getOrDefault(player.getUniqueId(), 0)) {
                verbose++;
            } else {
                verbose = 0;
            }

            if(verbose > 2) {
				getAntiCheat().logCheat(this, player, "sent over " + packets  + " packets! ", "(Type: A)");
            }
            if(packets > 400) {
				getAntiCheat().logCheat(this, player, Color.Red + "Kicked, " + Color.White + "sent over " + packets  + " packets! " , "(Type: A)");

		        AntiCheat.Instance.getServer().getScheduler().runTask((Plugin)AntiCheat.Instance, new Runnable(){
		        	final Player p = event.getPlayer();
		            @Override
		            public void run() {
		                player.kickPlayer("Too many packets");
		            }
		        });
		    }		
            
            packets = 0;
            Time = System.currentTimeMillis();
            PacketCoreB.movePackets.remove(player.getUniqueId());
        }
        packets++;

        this.packets.put(player.getUniqueId(), packets);
        this.verbose.put(player.getUniqueId(), verbose);
        this.lastPacket.put(player.getUniqueId(), Time);
    }
}
