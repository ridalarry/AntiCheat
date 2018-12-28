package me.rida.anticheat.other;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class PearlGlitchEvent extends Event implements Cancellable {

	private final Player player;
	private final Location from;
	private final Location to;
	private final ItemStack pearls;
	private final PearlGlitchType type;
	private static HandlerList handlers = new HandlerList();
	private boolean cancelled = false;

	public PearlGlitchEvent(Player player, Location from, Location to, ItemStack pearls, PearlGlitchType type) {
		this.player = player;
		this.from = from;
		this.to = to;
		this.pearls = pearls;
		this.type = type;
	}

	public Player getPlayer() {
		return this.player;
	}

	public Location getFrom() {
		return this.from;
	}

	public Location getTo() {
		return this.to;
	}

	public ItemStack getItems() {
		return this.pearls;
	}

	public PearlGlitchType getType() {
		return type;
	}

	public boolean isType(PearlGlitchType type) {
		if (type == this.type) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
}