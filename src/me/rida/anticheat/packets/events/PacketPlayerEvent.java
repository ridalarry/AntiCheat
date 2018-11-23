package me.rida.anticheat.packets.events;

import me.rida.anticheat.packets.PacketPlayerType;
import org.bukkit.event.HandlerList;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class PacketPlayerEvent extends Event {
	private Player Player;
	private double x;
	private double y;
	private double z;
	private float pitch;
	private float yaw;
	private static final HandlerList handlers;
	private PacketPlayerType type;
	static { handlers = new HandlerList();}
	public PacketPlayerEvent(Player Player, double x, double y, double z, float yaw, float pitch, PacketPlayerType type) {
		super();
		this.Player = Player;
        	this.x = x;
        	this.y = y;
        	this.z = z;
        	this.pitch = pitch;
        	this.yaw = yaw;
        	this.type = type;
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
		return this.type;
	}
	public void setType(PacketPlayerType type) {
		this.type = type;
	}
	@SuppressWarnings("static-access")
	public HandlerList getHandlers() {
		return this.handlers;
	}
	public static HandlerList getHandlerList() {
		return PacketPlayerEvent.handlers;
	}
}
