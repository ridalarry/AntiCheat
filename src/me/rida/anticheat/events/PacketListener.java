package me.rida.anticheat.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.data.DataPlayer;
import me.rida.anticheat.packets.events.PacketPlayerEvent;

public class PacketListener implements Listener {
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onPacketPlayerEvent(PacketPlayerEvent e) {
        Player p = e.getPlayer();
        DataPlayer data = AntiCheat.getInstance().getDataManager().getData(p);
        if (data != null) {
        if (data.getLastPlayerPacketDiff() > 200) {
            data.setLastDelayedPacket(System.currentTimeMillis());
        }
        data.setLastPlayerPacket(System.currentTimeMillis());
        }
    }
}