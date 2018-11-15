package me.rida.anticheat.checks.other;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;

public class ChatA extends Check{

	public ChatA(AntiCheat AntiCheat) {
		super("ChatA", "Chat", AntiCheat);
		setMaxViolations(0);
		this.setEnabled(true);
		this.setBannable(true);

}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onChat(PlayerChatEvent e) {
		Player p = e.getPlayer();
		InventoryView view = p.getOpenInventory();
		Inventory top = view.getTopInventory();
		Inventory bottom = view.getBottomInventory();
		if (view !=null) {
			if (top.toString().contains("CraftInventoryCrafting")
					||getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
        			|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
				return;
			} else {
				getAntiCheat().logCheat(this, p, "Chatting while a gui is open!", "(Type: A)");
			}
		}
		
	}
}
