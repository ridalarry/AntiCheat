package me.rida.anticheat.events;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.data.DataPlayer;
import me.rida.anticheat.packets.events.PacketPlayerEventA;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PacketListener implements Listener {
    @EventHandler
    public void onPacketPlayerEvent(PacketPlayerEventA e) {
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
