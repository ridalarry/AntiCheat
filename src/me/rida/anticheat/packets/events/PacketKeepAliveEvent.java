package me.rida.anticheat.packets.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PacketKeepAliveEvent extends Event {
	private final Player Player;
	private static final HandlerList handlers;

	static {
		handlers = new HandlerList();
	}

	public PacketKeepAliveEvent(final Player Player) {
		super();
		this.Player = Player;
	}

	public Player getPlayer() {
		return this.Player;
	}

	@Override
	public HandlerList getHandlers() {
		return PacketKeepAliveEvent.handlers;
	}

	public static HandlerList getHandlerList() {
		return PacketKeepAliveEvent.handlers;
	}
}