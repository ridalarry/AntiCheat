package me.rida.anticheat.checks.other;

import me.rida.anticheat.checks.Check;
import me.rida.anticheat.AntiCheat;

import org.bukkit.entity.*;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;

public class SkinBlinkerA extends Check {
    public SkinBlinkerA(AntiCheat AntiCheat) {
        super("SkinBlinkerA", "SkinBlinker", AntiCheat);
    }
    
    public void onPacketReceiving(final PacketEvent e) {
                if (e.getPacketType() != PacketType.Play.Client.SETTINGS) {
                    return;
                }
                final Player p = e.getPlayer();
                if (p.isSprinting() || p.isSneaking()) {
                    getAntiCheat().logCheat(this, p, null, "(Type: A)");
                }
            }
        
    }
    
