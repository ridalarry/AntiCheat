package me.rida.anticheat.packets.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.comphenix.protocol.events.PacketEvent;

public class PacketBlockPlacementEvent extends Event {
	private final Player Player;
	private final PacketEvent Event;
	private static final HandlerList handlers;

	static {
		handlers = new HandlerList();
	}

	public PacketBlockPlacementEvent(final PacketEvent Event, final Player Player) {
		super();
		this.Player = Player;
		this.Event = Event;
	}

	public PacketEvent getPacketEvent() {
		return this.Event;
	}

	public Player getPlayer() {
		return this.Player;
	}

	@Override
	public HandlerList getHandlers() {
		return PacketBlockPlacementEvent.handlers;
	}

	public static HandlerList getHandlerList() {
		return PacketBlockPlacementEvent.handlers;
	}
}