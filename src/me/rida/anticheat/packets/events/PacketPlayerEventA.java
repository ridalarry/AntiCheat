package me.rida.anticheat.packets.events;


import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.rida.anticheat.packets.PacketPlayerType;
public class PacketPlayerEventA extends Event {
    private Player Player;
    private double x;
    private double y;
    private double z;
    private float pitch;
    private float yaw;
    private PacketPlayerType type;
    private static final HandlerList handlers = new HandlerList();
    public PacketPlayerEventA(Player player, double x, double y, double z, float yaw, float pitch, PacketPlayerType type) {
        this.Player = player;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
    }
    public Player getPlayer() {
        return this.Player;
    }
    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
    public double getZ() {
        return this.z;
    }
    public float getYaw() {
        return this.yaw;
    }
    public float getPitch() {
        return this.pitch;
    }
    public PacketPlayerType getType() {
        return type;
    }
    public void setType(PacketPlayerType type) {
        this.type = type;
    }
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
