package me.rida.anticheat.checks.other;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;

public class ChatA extends Check{

	public ChatA(AntiCheat AntiCheat) {
		super("ChatA", "Chat", CheckType.Other, AntiCheat);
		setMaxViolations(0);
		this.setEnabled(true);
		this.setBannable(false);

}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		InventoryView view = p.getOpenInventory();
		Inventory top = view.getTopInventory();
		Inventory bottom = view.getBottomInventory();
		if (view !=null) {
			if (getAntiCheat().getLag().getTPS() < getAntiCheat().getTPSCancel()
        			|| getAntiCheat().getLag().getPing(p) > getAntiCheat().getPingCancel()) {
				return;
			} if (!top.toString().contains("CraftInventoryCrafting")) {
				getAntiCheat().logCheat(this, p, "Chatting while a gui is open!", "(Type: A)");
			} if (p.isSneaking()) {
				getAntiCheat().logCheat(this, p, "Sprinting while chatting!", "(Type: A)");
			} if (p.isSprinting()) {
				getAntiCheat().logCheat(this, p, "Sneaking while chatting!", "(Type: A)");
			}if (p.isDead()) {
				getAntiCheat().logCheat(this, p, "Chatting while being dead!", "(Type: A)");
			}
		}
		
	}
}
