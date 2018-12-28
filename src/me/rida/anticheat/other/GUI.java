package me.rida.anticheat.other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.CheckType;
import me.rida.anticheat.utils.Color;

public class GUI implements Listener {
	public static Inventory AntiCheatmain = Bukkit.createInventory(null, 36, Color.Gold + "Home");

	public static Inventory AntiCheatchecks = Bukkit.createInventory(null, 36, Color.Gold + "Checks: Toggle");

	public static Inventory AntiCheatchecksClient = Bukkit.createInventory(null, 45, Color.Gold + "Client: Toggle");
	public static Inventory AntiCheatchecksCombat = Bukkit.createInventory(null, 45, Color.Gold + "Combat: Toggle");
	public static Inventory AntiCheatchecksMovement = Bukkit.createInventory(null, 45, Color.Gold + "Movement: Toggle");
	public static Inventory AntiCheatchecksOther = Bukkit.createInventory(null, 45, Color.Gold + "Other: Toggle");
	public static Inventory AntiCheatchecksPlayer = Bukkit.createInventory(null, 45, Color.Gold + "Player: Toggle");

	public static Inventory AntiCheatJDay = Bukkit.createInventory(null, 36, Color.Gold + "Checks: JDay");

	public static Inventory AntiCheatJDayClient = Bukkit.createInventory(null, 45, Color.Gold + "Client: JDay");
	public static Inventory AntiCheatJDayCombat = Bukkit.createInventory(null, 45, Color.Gold + "Combat: JDay");
	public static Inventory AntiCheatJDayMovement = Bukkit.createInventory(null, 45, Color.Gold + "Movement: JDay");
	public static Inventory AntiCheatJDayOther = Bukkit.createInventory(null, 45, Color.Gold + "Other: JDay");
	public static Inventory AntiCheatJDayPlayer = Bukkit.createInventory(null, 45, Color.Gold + "Player: JDay");

	public static Inventory AntiCheatbannable = Bukkit.createInventory(null, 36, Color.Gold + "Checks: Bannable");

	public static Inventory AntiCheatbannableClient = Bukkit.createInventory(null, 45, Color.Gold + "Client: Bannable");
	public static Inventory AntiCheatbannableCombat = Bukkit.createInventory(null, 45, Color.Gold + "Combat: Bannable");
	public static Inventory AntiCheatbannableMovement = Bukkit.createInventory(null, 45, Color.Gold + "Movement: Bannable");
	public static Inventory AntiCheatbannableOther = Bukkit.createInventory(null, 45, Color.Gold + "Other: Bannable");
	public static Inventory AntiCheatbannablePlayer = Bukkit.createInventory(null, 45, Color.Gold + "Player: Bannable");

	public static Inventory AntiCheatTimer = Bukkit.createInventory(null, 36, Color.Gold + "Checks: BanTimer");

	public static Inventory AntiCheatTimerClient = Bukkit.createInventory(null, 45, Color.Gold + "Client: BanTimer");
	public static Inventory AntiCheatTimerCombat = Bukkit.createInventory(null, 45, Color.Gold + "Combat: BanTimer");
	public static Inventory AntiCheatTimerMovement = Bukkit.createInventory(null, 45, Color.Gold + "Movement: BanTimer");
	public static Inventory AntiCheatTimerPlayer = Bukkit.createInventory(null, 45, Color.Gold + "Player: BanTimer");
	public static Inventory AntiCheatTimerOther = Bukkit.createInventory(null, 45, Color.Gold + "Other: BanTimer");

	public static Inventory AntiCheatbans = Bukkit.createInventory(null, 54, Color.Gold + "Recent Bans");

	public static Inventory AntiCheatstatus = Bukkit.createInventory(null, 54, Color.Gold + "Status");

	public static Inventory AntiCheatKickable = Bukkit.createInventory(null, 36, Color.Gold + "Checks: Kickable");

	public static Inventory AntiCheatKickableClient = Bukkit.createInventory(null, 45, Color.Gold + "Client: Kickable");
	public static Inventory AntiCheatKickableCombat = Bukkit.createInventory(null, 45, Color.Gold + "Combat: Kickable");
	public static Inventory AntiCheatKickableMovement = Bukkit.createInventory(null, 45, Color.Gold + "Movement: Kickable");
	public static Inventory AntiCheatKickableOther = Bukkit.createInventory(null, 45, Color.Gold + "Other: Kickable");
	public static Inventory AntiCheatKickablePlayer = Bukkit.createInventory(null, 45, Color.Gold + "Player: Kickable");

	private static ItemStack back = createItem(Material.REDSTONE, 1, "&6Back", new String[0]);

	private static AntiCheat AntiCheat;

	public GUI(AntiCheat AntiCheat) {
		GUI.AntiCheat = AntiCheat;
		final ItemStack enabled = createItem(Material.COMPASS, 1, "&cChecks", new String[0]);
		final ItemStack Jday = createItem(Material.BOOK, 1, "&cJDay", new String[0]);
		final ItemStack bannable = createItem(Material.REDSTONE, 1, "&cAuto Bans", new String[0]);
		final ItemStack kickable = createItem(Material.BONE, 1, "&cAuto Kicks", new String[0]);
		final ItemStack timers = createItem(Material.WATCH, 1, "&cTimers", new String[0]);
		final ItemStack resetVio = createItem(Material.PAPER, 1, "&cReset Violations", new String[0]);
		final ItemStack reload = createItem(Material.LAVA_BUCKET, 1, "&cReload", new String[0]);
		final ItemStack info = createItem(Material.SIGN, 1, "&aInfo", new String[0]);
		final ItemStack combat = createItem(Material.REDSTONE_TORCH_ON, 1, "&aCombat", new String[0]);
		final ItemStack client = createItem(Material.REDSTONE_TORCH_ON, 1, "&aClient", new String[0]);
		final ItemStack movement = createItem(Material.REDSTONE_TORCH_ON, 1, "&aMovement", new String[0]);
		final ItemStack player = createItem(Material.REDSTONE_TORCH_ON, 1, "&aPlayer", new String[0]);
		final ItemStack other = createItem(Material.REDSTONE_TORCH_ON, 1, "&aOther", new String[0]);
		final ItemStack empty = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");
		final ItemStack checkered = createItem(Material.COAL, 1,
				AntiCheat.getConfig().getBoolean("settings.gui.checkered") ? "&aCheckered" : "&cCheckered",
						new String[0]);
		final ItemStack testmode = createItem(
				AntiCheat.getConfig().getBoolean("settings.testmode") ? Material.EMERALD_BLOCK : Material.TNT,
						1, "&cTest Mode", new String[0]);

		final ItemMeta infom = info.getItemMeta();
		infom.setLore(infoLore());
		info.setItemMeta(infom);
		AntiCheatchecks.setItem(9, combat);
		AntiCheatchecks.setItem(11, movement);
		AntiCheatchecks.setItem(13, player);
		AntiCheatchecks.setItem(15, other);
		AntiCheatchecks.setItem(17, client);
		AntiCheatchecks.setItem(0, empty);
		AntiCheatKickable.setItem(0, empty);
		AntiCheatbannable.setItem(0, empty);
		AntiCheatTimer.setItem(0, empty);
		AntiCheatJDay.setItem(0, empty);
		AntiCheatchecks.setItem(1, empty);
		AntiCheatchecks.setItem(1, empty);
		AntiCheatchecks.setItem(2, empty);
		AntiCheatchecks.setItem(3, empty);
		AntiCheatchecks.setItem(4, empty);
		AntiCheatchecks.setItem(5, empty);
		AntiCheatchecks.setItem(6, empty);
		AntiCheatchecks.setItem(7, empty);
		AntiCheatchecks.setItem(8, empty);
		AntiCheatchecks.setItem(10, empty);
		AntiCheatchecks.setItem(12, empty);
		AntiCheatchecks.setItem(14, empty);
		AntiCheatchecks.setItem(16, empty);
		AntiCheatchecks.setItem(18, empty);
		AntiCheatchecks.setItem(19, empty);
		AntiCheatchecks.setItem(20, empty);
		AntiCheatchecks.setItem(21, empty);
		AntiCheatchecks.setItem(22, empty);
		AntiCheatchecks.setItem(23, empty);
		AntiCheatchecks.setItem(24, empty);
		AntiCheatchecks.setItem(25, empty);
		AntiCheatchecks.setItem(26, empty);
		AntiCheatchecks.setItem(27, empty);
		AntiCheatchecks.setItem(28, empty);
		AntiCheatchecks.setItem(29, empty);
		AntiCheatchecks.setItem(30, empty);
		AntiCheatchecks.setItem(31, empty);
		AntiCheatchecks.setItem(32, empty);
		AntiCheatchecks.setItem(33, empty);
		AntiCheatchecks.setItem(34, empty);
		AntiCheatKickable.setItem(9, combat);
		AntiCheatKickable.setItem(11, movement);
		AntiCheatKickable.setItem(13, player);
		AntiCheatKickable.setItem(15, other);
		AntiCheatKickable.setItem(17, client);
		AntiCheatKickable.setItem(1, empty);
		AntiCheatKickable.setItem(1, empty);
		AntiCheatKickable.setItem(2, empty);
		AntiCheatKickable.setItem(3, empty);
		AntiCheatKickable.setItem(4, empty);
		AntiCheatKickable.setItem(5, empty);
		AntiCheatKickable.setItem(6, empty);
		AntiCheatKickable.setItem(7, empty);
		AntiCheatKickable.setItem(8, empty);
		AntiCheatKickable.setItem(10, empty);
		AntiCheatKickable.setItem(12, empty);
		AntiCheatKickable.setItem(14, empty);
		AntiCheatKickable.setItem(16, empty);
		AntiCheatKickable.setItem(18, empty);
		AntiCheatKickable.setItem(19, empty);
		AntiCheatKickable.setItem(20, empty);
		AntiCheatKickable.setItem(21, empty);
		AntiCheatKickable.setItem(22, empty);
		AntiCheatKickable.setItem(23, empty);
		AntiCheatKickable.setItem(24, empty);
		AntiCheatKickable.setItem(25, empty);
		AntiCheatKickable.setItem(26, empty);
		AntiCheatKickable.setItem(27, empty);
		AntiCheatKickable.setItem(28, empty);
		AntiCheatKickable.setItem(29, empty);
		AntiCheatKickable.setItem(30, empty);
		AntiCheatKickable.setItem(31, empty);
		AntiCheatKickable.setItem(32, empty);
		AntiCheatKickable.setItem(33, empty);
		AntiCheatKickable.setItem(34, empty);
		AntiCheatJDay.setItem(9, combat);
		AntiCheatJDay.setItem(11, movement);
		AntiCheatJDay.setItem(13, player);
		AntiCheatJDay.setItem(15, other);
		AntiCheatJDay.setItem(17, client);
		AntiCheatJDay.setItem(1, empty);
		AntiCheatJDay.setItem(1, empty);
		AntiCheatJDay.setItem(2, empty);
		AntiCheatJDay.setItem(3, empty);
		AntiCheatJDay.setItem(4, empty);
		AntiCheatJDay.setItem(5, empty);
		AntiCheatJDay.setItem(6, empty);
		AntiCheatJDay.setItem(7, empty);
		AntiCheatJDay.setItem(8, empty);
		AntiCheatJDay.setItem(10, empty);
		AntiCheatJDay.setItem(12, empty);
		AntiCheatJDay.setItem(14, empty);
		AntiCheatJDay.setItem(16, empty);
		AntiCheatJDay.setItem(18, empty);
		AntiCheatJDay.setItem(19, empty);
		AntiCheatJDay.setItem(20, empty);
		AntiCheatJDay.setItem(21, empty);
		AntiCheatJDay.setItem(22, empty);
		AntiCheatJDay.setItem(23, empty);
		AntiCheatJDay.setItem(24, empty);
		AntiCheatJDay.setItem(25, empty);
		AntiCheatJDay.setItem(26, empty);
		AntiCheatJDay.setItem(27, empty);
		AntiCheatJDay.setItem(28, empty);
		AntiCheatJDay.setItem(29, empty);
		AntiCheatJDay.setItem(30, empty);
		AntiCheatJDay.setItem(31, empty);
		AntiCheatJDay.setItem(32, empty);
		AntiCheatJDay.setItem(33, empty);
		AntiCheatJDay.setItem(34, empty);
		AntiCheatbannable.setItem(9, combat);
		AntiCheatbannable.setItem(11, movement);
		AntiCheatbannable.setItem(13, player);
		AntiCheatbannable.setItem(15, other);
		AntiCheatbannable.setItem(17, client);
		AntiCheatbannable.setItem(1, empty);
		AntiCheatbannable.setItem(1, empty);
		AntiCheatbannable.setItem(2, empty);
		AntiCheatbannable.setItem(3, empty);
		AntiCheatbannable.setItem(4, empty);
		AntiCheatbannable.setItem(5, empty);
		AntiCheatbannable.setItem(6, empty);
		AntiCheatbannable.setItem(7, empty);
		AntiCheatbannable.setItem(8, empty);
		AntiCheatbannable.setItem(10, empty);
		AntiCheatbannable.setItem(12, empty);
		AntiCheatbannable.setItem(14, empty);
		AntiCheatbannable.setItem(16, empty);
		AntiCheatbannable.setItem(18, empty);
		AntiCheatbannable.setItem(19, empty);
		AntiCheatbannable.setItem(20, empty);
		AntiCheatbannable.setItem(21, empty);
		AntiCheatbannable.setItem(22, empty);
		AntiCheatbannable.setItem(23, empty);
		AntiCheatbannable.setItem(24, empty);
		AntiCheatbannable.setItem(25, empty);
		AntiCheatbannable.setItem(26, empty);
		AntiCheatbannable.setItem(27, empty);
		AntiCheatbannable.setItem(28, empty);
		AntiCheatbannable.setItem(29, empty);
		AntiCheatbannable.setItem(30, empty);
		AntiCheatbannable.setItem(31, empty);
		AntiCheatbannable.setItem(32, empty);
		AntiCheatbannable.setItem(33, empty);
		AntiCheatbannable.setItem(34, empty);
		AntiCheatTimer.setItem(9, combat);
		AntiCheatTimer.setItem(11, movement);
		AntiCheatTimer.setItem(13, player);
		AntiCheatTimer.setItem(15, other);
		AntiCheatTimer.setItem(17, client);
		AntiCheatTimer.setItem(1, empty);
		AntiCheatTimer.setItem(1, empty);
		AntiCheatTimer.setItem(2, empty);
		AntiCheatTimer.setItem(3, empty);
		AntiCheatTimer.setItem(4, empty);
		AntiCheatTimer.setItem(5, empty);
		AntiCheatTimer.setItem(6, empty);
		AntiCheatTimer.setItem(7, empty);
		AntiCheatTimer.setItem(8, empty);
		AntiCheatTimer.setItem(10, empty);
		AntiCheatTimer.setItem(12, empty);
		AntiCheatTimer.setItem(14, empty);
		AntiCheatTimer.setItem(16, empty);
		AntiCheatTimer.setItem(18, empty);
		AntiCheatTimer.setItem(19, empty);
		AntiCheatTimer.setItem(20, empty);
		AntiCheatTimer.setItem(21, empty);
		AntiCheatTimer.setItem(22, empty);
		AntiCheatTimer.setItem(23, empty);
		AntiCheatTimer.setItem(24, empty);
		AntiCheatTimer.setItem(25, empty);
		AntiCheatTimer.setItem(26, empty);
		AntiCheatTimer.setItem(27, empty);
		AntiCheatTimer.setItem(28, empty);
		AntiCheatTimer.setItem(29, empty);
		AntiCheatTimer.setItem(30, empty);
		AntiCheatTimer.setItem(31, empty);
		AntiCheatTimer.setItem(32, empty);
		AntiCheatTimer.setItem(33, empty);
		AntiCheatTimer.setItem(34, empty);
		AntiCheatmain.setItem(1, grayGlass());
		AntiCheatmain.setItem(3, grayGlass());
		AntiCheatmain.setItem(5, grayGlass());
		AntiCheatmain.setItem(7, grayGlass());
		AntiCheatmain.setItem(9, enabled);
		AntiCheatmain.setItem(11, bannable);
		AntiCheatmain.setItem(13, timers);
		AntiCheatmain.setItem(15, reload);
		AntiCheatmain.setItem(17, resetVio);
		AntiCheatmain.setItem(19, grayGlass());
		AntiCheatmain.setItem(21, grayGlass());
		AntiCheatmain.setItem(23, grayGlass());
		AntiCheatmain.setItem(25, grayGlass());
		AntiCheatmain.setItem(27, testmode);
		AntiCheatmain.setItem(29, info);
		AntiCheatmain.setItem(31, kickable);
		AntiCheatmain.setItem(33, Jday);
		AntiCheatmain.setItem(35, checkered);
		if (AntiCheat.getConfig().contains("settings.gui.checkered")) {
			if (AntiCheat.getConfig().getBoolean("settings.gui.checkered")) {
				AntiCheatmain.setItem(0, whiteGlass());
				AntiCheatmain.setItem(2, whiteGlass());
				AntiCheatmain.setItem(4, whiteGlass());
				AntiCheatmain.setItem(6, whiteGlass());
				AntiCheatmain.setItem(8, whiteGlass());
				AntiCheatmain.setItem(10, whiteGlass());
				AntiCheatmain.setItem(12, whiteGlass());
				AntiCheatmain.setItem(14, whiteGlass());
				AntiCheatmain.setItem(16, whiteGlass());
				AntiCheatmain.setItem(18, whiteGlass());
				AntiCheatmain.setItem(20, whiteGlass());
				AntiCheatmain.setItem(22, whiteGlass());
				AntiCheatmain.setItem(24, whiteGlass());
				AntiCheatmain.setItem(26, whiteGlass());
				AntiCheatmain.setItem(28, whiteGlass());
				AntiCheatmain.setItem(30, whiteGlass());
				AntiCheatmain.setItem(32, whiteGlass());
				AntiCheatmain.setItem(34, whiteGlass());
			} else {
				AntiCheatmain.setItem(0, grayGlass());
				AntiCheatmain.setItem(2, grayGlass());
				AntiCheatmain.setItem(4, grayGlass());
				AntiCheatmain.setItem(6, grayGlass());
				AntiCheatmain.setItem(8, grayGlass());
				AntiCheatmain.setItem(10, grayGlass());
				AntiCheatmain.setItem(12, grayGlass());
				AntiCheatmain.setItem(14, grayGlass());
				AntiCheatmain.setItem(16, grayGlass());
				AntiCheatmain.setItem(18, grayGlass());
				AntiCheatmain.setItem(20, grayGlass());
				AntiCheatmain.setItem(22, grayGlass());
				AntiCheatmain.setItem(24, grayGlass());
				AntiCheatmain.setItem(26, grayGlass());
				AntiCheatmain.setItem(28, grayGlass());
				AntiCheatmain.setItem(30, grayGlass());
				AntiCheatmain.setItem(32, grayGlass());
				AntiCheatmain.setItem(34, grayGlass());
			}
		} else {
			AntiCheat.getConfig().set("settings.gui.checkered", true);
			AntiCheatmain.setItem(0, whiteGlass());
			AntiCheatmain.setItem(2, whiteGlass());
			AntiCheatmain.setItem(4, whiteGlass());
			AntiCheatmain.setItem(6, whiteGlass());
			AntiCheatmain.setItem(8, whiteGlass());
			AntiCheatmain.setItem(10, whiteGlass());
			AntiCheatmain.setItem(12, whiteGlass());
			AntiCheatmain.setItem(14, whiteGlass());
			AntiCheatmain.setItem(16, whiteGlass());
			AntiCheatmain.setItem(18, whiteGlass());
			AntiCheatmain.setItem(20, whiteGlass());
			AntiCheatmain.setItem(22, whiteGlass());
			AntiCheatmain.setItem(24, whiteGlass());
			AntiCheatmain.setItem(26, whiteGlass());
			AntiCheatmain.setItem(28, whiteGlass());
			AntiCheatmain.setItem(30, whiteGlass());
			AntiCheatmain.setItem(32, whiteGlass());
			AntiCheatmain.setItem(34, whiteGlass());
		}
	}

	private static ArrayList<String> infoLore() {
		final ArrayList<String> list = new ArrayList<>();
		list.add(" ");
		list.add(Color.translate("&7You can do &f/AntiCheat help &7to see your"));
		list.add(Color.translate("&7options for other &fcommands&7/&ffunctions&7!"));
		list.add(" ");
		list.add(Color.translate("&7Current Version: &f" + AntiCheat.getDescription().getVersion()));

		return list;
	}

	public static void openAntiCheatMain(Player player) {

		player.openInventory(AntiCheatmain);
	}

	public static void openBans(Player player) {
		final List<Map.Entry<String, Check>> entrybans = new ArrayList<>(AntiCheat.getNamesBanned().entrySet());
		for (int i = 0; i < entrybans.size(); i++) {
			final Map.Entry<String, Check> entry = entrybans.get(i);
			if (i <= 54) {
				final ItemStack offender = createItem(Material.PAPER, 1, Color.Red + entry.getKey(),
						new String[] { Color.Gray + entry.getValue().getName() });
				AntiCheatbans.setItem(i, offender);
			}
		}
	}

	public static void openStatus(Player player, Player target) {
		AntiCheatstatus = Bukkit.createInventory(player, 27, Color.Gold + "Status");

		final Map<Check, Integer> Checks = AntiCheat.getViolations(target);
		if ((Checks == null) || (Checks.isEmpty())) {
			player.sendMessage(Color.Gray + "This player set off 0 checks. Yay!");
		} else {
			int slot = 0;
			for (final Check Check : Checks.keySet()) {
				final Integer Violations = Checks.get(Check);

				final ItemStack vl = createItem(Material.PAPER, 1,
						Color.Aqua + Check.getIdentifier() + Color.DGray + " [" + Color.Red + Violations + Color.DGray + "]", new String[0]);
				AntiCheatstatus.setItem(slot, vl);
				slot++;
			}
		}
		player.openInventory(AntiCheatstatus);
	}

	public void openChecks(Player player) {
		int slot = 0;
		for (final Check check : AntiCheat.getChecks()) {
			if (AntiCheat.getConfig().getBoolean("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".enabled")) {
			}
			slot++;
		}
		for (int i = slot; i < 35; i++) {
			final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatchecks.setItem(i, c);
		}
		AntiCheatchecks.setItem(35, back);
		player.openInventory(AntiCheatchecks);
	}

	public void openChecksCombat(Player p) {
		int slot = 0;
		for (final Check check : AntiCheat.getChecks()) {
			if (check.getType().equals(CheckType.Combat)) {
				if (AntiCheat.getConfig().getBoolean("checks.Combat" + "." + check.getName() + "." + check.getIdentifier() + ".enabled")) {
					final ItemStack g = createGlass(Material.STAINED_GLASS_PANE, 5, 1, Color.Green + check.getIdentifier(), new String[0]);
					AntiCheatchecksCombat.setItem(slot, g);
				} if (!(AntiCheat.getConfig().getBoolean("checks.Combat" + "." + check.getName() + "." + check.getIdentifier() + ".enabled"))) {
					final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 14, 1, Color.Red + check.getIdentifier(), new String[0]);
					AntiCheatchecksCombat.setItem(slot, c);
				}
				slot++;
			}
		}
		for (int i = slot; i < 44; i++) {
			final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatchecksCombat.setItem(i, c);
		}
		AntiCheatchecksCombat.setItem(44, back);
		p.openInventory(AntiCheatchecksCombat);
	}
	public void openChecksClient(Player player) {
		int slot = 0;
		for (final Check check : AntiCheat.getChecks()) {
			if (check.getType().equals(CheckType.Client)) {
				if (AntiCheat.getConfig().getBoolean("checks.Client" + "." + check.getName() + "." + check.getIdentifier() + ".enabled")) {
					final ItemStack g = createGlass(Material.STAINED_GLASS_PANE, 5, 1, Color.Green + check.getIdentifier(), new String[0]);
					AntiCheatchecksClient.setItem(slot, g);
				} if (!(AntiCheat.getConfig().getBoolean("checks.Client" + "." + check.getName() + "." + check.getIdentifier() + ".enabled"))) {
					final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 14, 1, Color.Red + check.getIdentifier(), new String[0]);
					AntiCheatchecksClient.setItem(slot, c);
				}
				slot++;
			}
		}
		for (int i = slot; i < 44; i++) {
			final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatchecksClient.setItem(i, c);
		}
		AntiCheatchecksClient.setItem(44, back);
		player.openInventory(AntiCheatchecksClient);
	}
	public void openChecksMovement(Player player) {
		int slot = 0;
		for (final Check check : AntiCheat.getChecks()) {
			if (check.getType().equals(CheckType.Movement)) {
				if (AntiCheat.getConfig().getBoolean("checks.Movement" + "." + check.getName() + "." + check.getIdentifier() + ".enabled")) {
					final ItemStack g = createGlass(Material.STAINED_GLASS_PANE, 5, 1, Color.Green + check.getIdentifier(), new String[0]);
					AntiCheatchecksMovement.setItem(slot, g);
				} if (!(AntiCheat.getConfig().getBoolean("checks.Movement" + "." + check.getName() + "." + check.getIdentifier() + ".enabled"))) {
					final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 14, 1, Color.Red + check.getIdentifier(), new String[0]);
					AntiCheatchecksMovement.setItem(slot, c);
				}
				slot++;
			}
		}
		for (int i = slot; i < 44; i++) {
			final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatchecksMovement.setItem(i, c);
		}
		AntiCheatchecksMovement.setItem(44, back);
		player.openInventory(AntiCheatchecksMovement);
	}
	public void openChecksPlayer(Player player) {
		int slot = 0;
		for (final Check check : AntiCheat.getChecks()) {
			if (check.getType().equals(CheckType.Player)) {
				if (AntiCheat.getConfig().getBoolean("checks.Player" + "." + check.getName() + "." + check.getIdentifier() + ".enabled")) {
					final ItemStack g = createGlass(Material.STAINED_GLASS_PANE, 5, 1, Color.Green + check.getIdentifier(), new String[0]);
					AntiCheatchecksPlayer.setItem(slot, g);
				} if (!(AntiCheat.getConfig().getBoolean("checks.Player" + "." + check.getName() + "." + check.getIdentifier() + ".enabled"))) {
					final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 14, 1, Color.Red + check.getIdentifier(), new String[0]);
					AntiCheatchecksPlayer.setItem(slot, c);
				}
				slot++;
			}
		}
		for (int i = slot; i < 44; i++) {
			final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatchecksPlayer.setItem(i, c);
		}
		AntiCheatchecksPlayer.setItem(44, back);
		player.openInventory(AntiCheatchecksPlayer);
	}
	public void openChecksOther(Player p) {
		int slot = 0;
		for (final Check check : AntiCheat.getChecks()) {
			if (check.getType().equals(CheckType.Other)) {
				if (AntiCheat.getConfig().getBoolean("checks.Other" + "." + check.getName() + "." + check.getIdentifier() + ".enabled")) {
					final ItemStack g = createGlass(Material.STAINED_GLASS_PANE, 5, 1, Color.Green + check.getIdentifier(), new String[0]);
					AntiCheatchecksOther.setItem(slot, g);
				} if (!(AntiCheat.getConfig().getBoolean("checks.Other" + "." + check.getName() + "." + check.getIdentifier() + ".enabled"))) {
					final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 14, 1, Color.Red + check.getIdentifier(), new String[0]);
					AntiCheatchecksOther.setItem(slot, c);
				}
				slot++;
			}
		}
		for (int i = slot; i < 44; i++) {
			final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatchecksOther.setItem(i, c);
		}
		AntiCheatchecksOther.setItem(44, back);
		p.openInventory(AntiCheatchecksOther);
	}
	public void openAutoBans(Player player) {
		int slot = 0;
		for (final Check check : AntiCheat.getChecks()) {
			if (AntiCheat.getConfig().getBoolean("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".bannable")) {
			}
			slot++;
		}
		for (int i = slot; i < 35; i++) {
			final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatbannable.setItem(i, c);
		}
		AntiCheatbannable.setItem(35, back);
		player.openInventory(AntiCheatbannable);
	}

	public void openAutoBansCombat(Player p) {
		int slot = 0;
		for (final Check check : AntiCheat.getChecks()) {
			if (check.getType().equals(CheckType.Combat)) {
				if (AntiCheat.getConfig().getBoolean("checks.Combat" + "." + check.getName() + "." + check.getIdentifier() + ".bannable")) {
					final ItemStack g = createGlass(Material.STAINED_GLASS_PANE, 5, 1, Color.Green + check.getIdentifier(), new String[0]);
					AntiCheatbannableCombat.setItem(slot, g);
				} if (!(AntiCheat.getConfig().getBoolean("checks.Combat" + "." + check.getName() + "." + check.getIdentifier() + ".bannable"))) {
					final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 14, 1, Color.Red + check.getIdentifier(), new String[0]);
					AntiCheatbannableCombat.setItem(slot, c);
				}
				slot++;
			}
		}
		for (int i = slot; i < 44; i++) {
			final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatbannableCombat.setItem(i, c);
		}
		AntiCheatbannableCombat.setItem(44, back);
		p.openInventory(AntiCheatbannableCombat);
	}
	public void openAutoBansClient(Player player) {
		int slot = 0;
		for (final Check check : AntiCheat.getChecks()) {
			if (check.getType().equals(CheckType.Client)) {
				if (AntiCheat.getConfig().getBoolean("checks.Client" + "." + check.getName() + "." + check.getIdentifier() + ".bannable")) {
					final ItemStack g = createGlass(Material.STAINED_GLASS_PANE, 5, 1, Color.Green + check.getIdentifier(), new String[0]);
					AntiCheatbannableClient.setItem(slot, g);
				} if (!(AntiCheat.getConfig().getBoolean("checks.Client" + "." + check.getName() + "." + check.getIdentifier() + ".bannable"))) {
					final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 14, 1, Color.Red + check.getIdentifier(), new String[0]);
					AntiCheatbannableClient.setItem(slot, c);
				}
				slot++;
			}
		}
		for (int i = slot; i < 44; i++) {
			final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatbannableClient.setItem(i, c);
		}
		AntiCheatbannableClient.setItem(44, back);
		player.openInventory(AntiCheatbannableClient);
	}
	public void openAutoBansMovement(Player player) {
		int slot = 0;
		for (final Check check : AntiCheat.getChecks()) {
			if (check.getType().equals(CheckType.Movement)) {
				if (AntiCheat.getConfig().getBoolean("checks.Movement" + "." + check.getName() + "." + check.getIdentifier() + ".bannable")) {
					final ItemStack g = createGlass(Material.STAINED_GLASS_PANE, 5, 1, Color.Green + check.getIdentifier(), new String[0]);
					AntiCheatbannableMovement.setItem(slot, g);
				} if (!(AntiCheat.getConfig().getBoolean("checks.Movement" + "." + check.getName() + "." + check.getIdentifier() + ".bannable"))) {
					final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 14, 1, Color.Red + check.getIdentifier(), new String[0]);
					AntiCheatbannableMovement.setItem(slot, c);
				}
				slot++;
			}
		}
		for (int i = slot; i < 44; i++) {
			final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatbannableMovement.setItem(i, c);
		}
		AntiCheatbannableMovement.setItem(44, back);
		player.openInventory(AntiCheatbannableMovement);
	}
	public void openAutoBansPlayer(Player player) {
		int slot = 0;
		for (final Check check : AntiCheat.getChecks()) {
			if (check.getType().equals(CheckType.Player)) {
				if (AntiCheat.getConfig().getBoolean("checks.Player" + "." + check.getName() + "." + check.getIdentifier() + ".bannable")) {
					final ItemStack g = createGlass(Material.STAINED_GLASS_PANE, 5, 1, Color.Green + check.getIdentifier(), new String[0]);
					AntiCheatbannablePlayer.setItem(slot, g);
				} if (!(AntiCheat.getConfig().getBoolean("checks.Player" + "." + check.getName() + "." + check.getIdentifier() + ".bannable"))) {
					final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 14, 1, Color.Red + check.getIdentifier(), new String[0]);
					AntiCheatbannablePlayer.setItem(slot, c);
				}
				slot++;
			}
		}
		for (int i = slot; i < 44; i++) {
			final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatbannablePlayer.setItem(i, c);
		}
		AntiCheatbannablePlayer.setItem(44, back);
		player.openInventory(AntiCheatbannablePlayer);
	}
	public void openAutoBansOther(Player p) {
		int slot = 0;
		for (final Check check : AntiCheat.getChecks()) {
			if (check.getType().equals(CheckType.Other)) {
				if (AntiCheat.getConfig().getBoolean("checks.Other" + "." + check.getName() + "." + check.getIdentifier() + ".bannable")) {
					final ItemStack g = createGlass(Material.STAINED_GLASS_PANE, 5, 1, Color.Green + check.getIdentifier(), new String[0]);
					AntiCheatbannableOther.setItem(slot, g);
				} if (!(AntiCheat.getConfig().getBoolean("checks.Other" + "." + check.getName() + "." + check.getIdentifier() + ".bannable"))) {
					final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 14, 1, Color.Red + check.getIdentifier(), new String[0]);
					AntiCheatbannableOther.setItem(slot, c);
				}
				slot++;
			}
		}
		for (int i = slot; i < 44; i++) {
			final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatbannableOther.setItem(i, c);
		}
		AntiCheatbannableOther.setItem(44, back);
		p.openInventory(AntiCheatbannableOther);
	}


	public void openTimer(Player player) {
		int slot = 0;
		for (final Check check : AntiCheat.getChecks()) {
			if (AntiCheat.getConfig().getBoolean("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".banTimer")) {
			}
			slot++;
		}
		for (int i = slot; i < 35; i++) {
			final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatTimer.setItem(i, c);
		}
		AntiCheatTimer.setItem(35, back);
		player.openInventory(AntiCheatTimer);
	}

	public void openTimerCombat(Player p) {
		int slot = 0;
		for (final Check check : AntiCheat.getChecks()) {
			if (check.getType().equals(CheckType.Combat)) {
				if (AntiCheat.getConfig().getBoolean("checks.Combat" + "." + check.getName() + "." + check.getIdentifier() + ".banTimer")) {
					final ItemStack g = createGlass(Material.STAINED_GLASS_PANE, 5, 1, Color.Green + check.getIdentifier(), new String[0]);
					AntiCheatTimerCombat.setItem(slot, g);
				} if (!(AntiCheat.getConfig().getBoolean("checks.Combat" + "." + check.getName() + "." + check.getIdentifier() + ".banTimer"))) {
					final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 14, 1, Color.Red + check.getIdentifier(), new String[0]);
					AntiCheatTimerCombat.setItem(slot, c);
				}
				slot++;
			}
		}
		for (int i = slot; i < 44; i++) {
			final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatTimerCombat.setItem(i, c);
		}
		AntiCheatTimerCombat.setItem(44, back);
		p.openInventory(AntiCheatTimerCombat);
	}
	public void openTimerClient(Player player) {
		int slot = 0;
		for (final Check check : AntiCheat.getChecks()) {
			if (check.getType().equals(CheckType.Client)) {
				if (AntiCheat.getConfig().getBoolean("checks.Client" + "." + check.getName() + "." + check.getIdentifier() + ".banTimer")) {
					final ItemStack g = createGlass(Material.STAINED_GLASS_PANE, 5, 1, Color.Green + check.getIdentifier(), new String[0]);
					AntiCheatTimerClient.setItem(slot, g);
				} if (!(AntiCheat.getConfig().getBoolean("checks.Client" + "." + check.getName() + "." + check.getIdentifier() + ".banTimer"))) {
					final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 14, 1, Color.Red + check.getIdentifier(), new String[0]);
					AntiCheatTimerClient.setItem(slot, c);
				}
				slot++;
			}
		}
		for (int i = slot; i < 44; i++) {
			final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatTimerClient.setItem(i, c);
		}
		AntiCheatTimerClient.setItem(44, back);
		player.openInventory(AntiCheatTimerClient);
	}
	public void openTimerMovement(Player player) {
		int slot = 0;
		for (final Check check : AntiCheat.getChecks()) {
			if (check.getType().equals(CheckType.Movement)) {
				if (AntiCheat.getConfig().getBoolean("checks.Movement" + "." + check.getName() + "." + check.getIdentifier() + ".banTimer")) {
					final ItemStack g = createGlass(Material.STAINED_GLASS_PANE, 5, 1, Color.Green + check.getIdentifier(), new String[0]);
					AntiCheatTimerMovement.setItem(slot, g);
				} if (!(AntiCheat.getConfig().getBoolean("checks.Movement" + "." + check.getName() + "." + check.getIdentifier() + ".banTimer"))) {
					final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 14, 1, Color.Red + check.getIdentifier(), new String[0]);
					AntiCheatTimerMovement.setItem(slot, c);
				}
				slot++;
			}
		}
		for (int i = slot; i < 44; i++) {
			final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatTimerMovement.setItem(i, c);
		}
		AntiCheatTimerMovement.setItem(44, back);
		player.openInventory(AntiCheatTimerMovement);
	}
	public void openTimerPlayer(Player player) {
		int slot = 0;
		for (final Check check : AntiCheat.getChecks()) {
			if (check.getType().equals(CheckType.Player)) {
				if (AntiCheat.getConfig().getBoolean("checks.Player" + "." + check.getName() + "." + check.getIdentifier() + ".banTimer")) {
					final ItemStack g = createGlass(Material.STAINED_GLASS_PANE, 5, 1, Color.Green + check.getIdentifier(), new String[0]);
					AntiCheatTimerPlayer.setItem(slot, g);
				} if (!(AntiCheat.getConfig().getBoolean("checks.Player" + "." + check.getName() + "." + check.getIdentifier() + ".banTimer"))) {
					final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 14, 1, Color.Red + check.getIdentifier(), new String[0]);
					AntiCheatTimerPlayer.setItem(slot, c);
				}
				slot++;
			}
		}
		for (int i = slot; i < 44; i++) {
			final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatTimerPlayer.setItem(i, c);
		}
		AntiCheatTimerPlayer.setItem(44, back);
		player.openInventory(AntiCheatTimerPlayer);
	}
	public void openTimerOther(Player p) {
		int slot = 0;
		for (final Check check : AntiCheat.getChecks()) {
			if (check.getType().equals(CheckType.Other)) {
				if (AntiCheat.getConfig().getBoolean("checks.Other" + "." + check.getName() + "." + check.getIdentifier() + ".banTimer")) {
					final ItemStack g = createGlass(Material.STAINED_GLASS_PANE, 5, 1, Color.Green + check.getIdentifier(), new String[0]);
					AntiCheatTimerOther.setItem(slot, g);
				} if (!(AntiCheat.getConfig().getBoolean("checks.Other" + "." + check.getName() + "." + check.getIdentifier() + ".banTimer"))) {
					final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 14, 1, Color.Red + check.getIdentifier(), new String[0]);
					AntiCheatTimerOther.setItem(slot, c);
				}
				slot++;
			}
		}
		for (int i = slot; i < 44; i++) {
			final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatTimerOther.setItem(i, c);
		}
		AntiCheatTimerOther.setItem(44, back);
		p.openInventory(AntiCheatTimerOther);
	}

	public void openJDay(Player player) {
		int slot = 0;
		for (final Check check : AntiCheat.getChecks()) {
			if (AntiCheat.getConfig().getBoolean("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".judgementDay")) {
			}
			slot++;
		}
		for (int i = slot; i < 35; i++) {
			final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatJDay.setItem(i, c);
		}
		AntiCheatJDay.setItem(35, back);
		player.openInventory(AntiCheatJDay);
	}

	public void openJDayCombat(Player p) {
		int slot = 0;
		for (final Check check : AntiCheat.getChecks()) {
			if (check.getType().equals(CheckType.Combat)) {
				if (AntiCheat.getConfig().getBoolean("checks.Combat" + "." + check.getName() + "." + check.getIdentifier() + ".judgementDay")) {
					final ItemStack g = createGlass(Material.STAINED_GLASS_PANE, 5, 1, Color.Green + check.getIdentifier(), new String[0]);
					AntiCheatJDayCombat.setItem(slot, g);
				} if (!(AntiCheat.getConfig().getBoolean("checks.Combat" + "." + check.getName() + "." + check.getIdentifier() + ".judgementDay"))) {
					final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 14, 1, Color.Red + check.getIdentifier(), new String[0]);
					AntiCheatJDayCombat.setItem(slot, c);
				}
				slot++;
			}
		}
		for (int i = slot; i < 44; i++) {
			final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatJDayCombat.setItem(i, c);
		}
		AntiCheatJDayCombat.setItem(44, back);
		p.openInventory(AntiCheatJDayCombat);
	}
	public void openJDayClient(Player player) {
		int slot = 0;
		for (final Check check : AntiCheat.getChecks()) {
			if (check.getType().equals(CheckType.Client)) {
				if (AntiCheat.getConfig().getBoolean("checks.Client" + "." + check.getName() + "." + check.getIdentifier() + ".judgementDay")) {
					final ItemStack g = createGlass(Material.STAINED_GLASS_PANE, 5, 1, Color.Green + check.getIdentifier(), new String[0]);
					AntiCheatJDayClient.setItem(slot, g);
				} if (!(AntiCheat.getConfig().getBoolean("checks.Client" + "." + check.getName() + "." + check.getIdentifier() + ".judgementDay"))) {
					final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 14, 1, Color.Red + check.getIdentifier(), new String[0]);
					AntiCheatJDayClient.setItem(slot, c);
				}
				slot++;
			}
		}
		for (int i = slot; i < 44; i++) {
			final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatJDayClient.setItem(i, c);
		}
		AntiCheatJDayClient.setItem(44, back);
		player.openInventory(AntiCheatJDayClient);
	}
	public void openJDayMovement(Player player) {
		int slot = 0;
		for (final Check check : AntiCheat.getChecks()) {
			if (check.getType().equals(CheckType.Movement)) {
				if (AntiCheat.getConfig().getBoolean("checks.Movement" + "." + check.getName() + "." + check.getIdentifier() + ".judgementDay")) {
					final ItemStack g = createGlass(Material.STAINED_GLASS_PANE, 5, 1, Color.Green + check.getIdentifier(), new String[0]);
					AntiCheatJDayMovement.setItem(slot, g);
				} if (!(AntiCheat.getConfig().getBoolean("checks.Movement" + "." + check.getName() + "." + check.getIdentifier() + ".judgementDay"))) {
					final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 14, 1, Color.Red + check.getIdentifier(), new String[0]);
					AntiCheatJDayMovement.setItem(slot, c);
				}
				slot++;
			}
		}
		for (int i = slot; i < 44; i++) {
			final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatJDayMovement.setItem(i, c);
		}
		AntiCheatJDayMovement.setItem(44, back);
		player.openInventory(AntiCheatJDayMovement);
	}
	public void openJDayPlayer(Player player) {
		int slot = 0;
		for (final Check check : AntiCheat.getChecks()) {
			if (check.getType().equals(CheckType.Player)) {
				if (AntiCheat.getConfig().getBoolean("checks.Player" + "." + check.getName() + "." + check.getIdentifier() + ".judgementDay")) {
					final ItemStack g = createGlass(Material.STAINED_GLASS_PANE, 5, 1, Color.Green + check.getIdentifier(), new String[0]);
					AntiCheatJDayPlayer.setItem(slot, g);
				} if (!(AntiCheat.getConfig().getBoolean("checks.Player" + "." + check.getName() + "." + check.getIdentifier() + ".judgementDay"))) {
					final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 14, 1, Color.Red + check.getIdentifier(), new String[0]);
					AntiCheatJDayPlayer.setItem(slot, c);
				}
				slot++;
			}
		}
		for (int i = slot; i < 44; i++) {
			final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatJDayPlayer.setItem(i, c);
		}
		AntiCheatJDayPlayer.setItem(44, back);
		player.openInventory(AntiCheatJDayPlayer);
	}
	public void openJDayOther(Player p) {
		int slot = 0;
		for (final Check check : AntiCheat.getChecks()) {
			if (check.getType().equals(CheckType.Other)) {
				if (AntiCheat.getConfig().getBoolean("checks.Other" + "." + check.getName() + "." + check.getIdentifier() + ".judgementDay")) {
					final ItemStack g = createGlass(Material.STAINED_GLASS_PANE, 5, 1, Color.Green + check.getIdentifier(), new String[0]);
					AntiCheatJDayOther.setItem(slot, g);
				} if (!(AntiCheat.getConfig().getBoolean("checks.Other" + "." + check.getName() + "." + check.getIdentifier() + ".judgementDay"))) {
					final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 14, 1, Color.Red + check.getIdentifier(), new String[0]);
					AntiCheatJDayOther.setItem(slot, c);
				}
				slot++;
			}
		}
		for (int i = slot; i < 44; i++) {
			final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatJDayOther.setItem(i, c);
		}
		AntiCheatJDayOther.setItem(44, back);
		p.openInventory(AntiCheatJDayOther);
	}


	public void openKickable(Player player) {
		int slot = 0;
		for (final Check check : AntiCheat.getChecks()) {
			if (AntiCheat.getConfig().getBoolean("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".kickable")) {
			}
			slot++;
		}
		for (int i = slot; i < 35; i++) {
			final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatKickable.setItem(i, c);
		}
		AntiCheatKickable.setItem(35, back);
		player.openInventory(AntiCheatKickable);
	}

	public void openKickableCombat(Player p) {
		int slot = 0;
		for (final Check check : AntiCheat.getChecks()) {
			if (check.getType().equals(CheckType.Combat)) {
				if (AntiCheat.getConfig().getBoolean("checks.Combat" + "." + check.getName() + "." + check.getIdentifier() + ".kickable")) {
					final ItemStack g = createGlass(Material.STAINED_GLASS_PANE, 5, 1, Color.Green + check.getIdentifier(), new String[0]);
					AntiCheatKickableCombat.setItem(slot, g);
				} if (!(AntiCheat.getConfig().getBoolean("checks.Combat" + "." + check.getName() + "." + check.getIdentifier() + ".kickable"))) {
					final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 14, 1, Color.Red + check.getIdentifier(), new String[0]);
					AntiCheatKickableCombat.setItem(slot, c);
				}
				slot++;
			}
		}
		for (int i = slot; i < 44; i++) {
			final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatKickableCombat.setItem(i, c);
		}
		AntiCheatKickableCombat.setItem(44, back);
		p.openInventory(AntiCheatKickableCombat);
	}
	public void openKickableClient(Player player) {
		int slot = 0;
		for (final Check check : AntiCheat.getChecks()) {
			if (check.getType().equals(CheckType.Client)) {
				if (AntiCheat.getConfig().getBoolean("checks.Client" + "." + check.getName() + "." + check.getIdentifier() + ".kickable")) {
					final ItemStack g = createGlass(Material.STAINED_GLASS_PANE, 5, 1, Color.Green + check.getIdentifier(), new String[0]);
					AntiCheatKickableClient.setItem(slot, g);
				} if (!(AntiCheat.getConfig().getBoolean("checks.Client" + "." + check.getName() + "." + check.getIdentifier() + ".kickable"))) {
					final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 14, 1, Color.Red + check.getIdentifier(), new String[0]);
					AntiCheatKickableClient.setItem(slot, c);
				}
				slot++;
			}
		}
		for (int i = slot; i < 44; i++) {
			final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatKickableClient.setItem(i, c);
		}
		AntiCheatKickableClient.setItem(44, back);
		player.openInventory(AntiCheatKickableClient);
	}
	public void openKickableMovement(Player player) {
		int slot = 0;
		for (final Check check : AntiCheat.getChecks()) {
			if (check.getType().equals(CheckType.Movement)) {
				if (AntiCheat.getConfig().getBoolean("checks.Movement" + "." + check.getName() + "." + check.getIdentifier() + ".kickable")) {
					final ItemStack g = createGlass(Material.STAINED_GLASS_PANE, 5, 1, Color.Green + check.getIdentifier(), new String[0]);
					AntiCheatKickableMovement.setItem(slot, g);
				} if (!(AntiCheat.getConfig().getBoolean("checks.Movement" + "." + check.getName() + "." + check.getIdentifier() + ".kickable"))) {
					final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 14, 1, Color.Red + check.getIdentifier(), new String[0]);
					AntiCheatKickableMovement.setItem(slot, c);
				}
				slot++;
			}
		}
		for (int i = slot; i < 44; i++) {
			final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatKickableMovement.setItem(i, c);
		}
		AntiCheatKickableMovement.setItem(44, back);
		player.openInventory(AntiCheatKickableMovement);
	}
	public void openKickablePlayer(Player player) {
		int slot = 0;
		for (final Check check : AntiCheat.getChecks()) {
			if (check.getType().equals(CheckType.Player)) {
				if (AntiCheat.getConfig().getBoolean("checks.Player" + "." + check.getName() + "." + check.getIdentifier() + ".kickable")) {
					final ItemStack g = createGlass(Material.STAINED_GLASS_PANE, 5, 1, Color.Green + check.getIdentifier(), new String[0]);
					AntiCheatKickablePlayer.setItem(slot, g);
				} if (!(AntiCheat.getConfig().getBoolean("checks.Player" + "." + check.getName() + "." + check.getIdentifier() + ".kickable"))) {
					final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 14, 1, Color.Red + check.getIdentifier(), new String[0]);
					AntiCheatKickablePlayer.setItem(slot, c);
				}
				slot++;
			}
		}
		for (int i = slot; i < 44; i++) {
			final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatKickablePlayer.setItem(i, c);
		}
		AntiCheatKickablePlayer.setItem(44, back);
		player.openInventory(AntiCheatKickablePlayer);
	}
	public void openKickableOther(Player p) {
		int slot = 0;
		for (final Check check : AntiCheat.getChecks()) {
			if (check.getType().equals(CheckType.Other)) {
				if (AntiCheat.getConfig().getBoolean("checks.Other" + "." + check.getName() + "." + check.getIdentifier() + ".kickable")) {
					final ItemStack g = createGlass(Material.STAINED_GLASS_PANE, 5, 1, Color.Green + check.getIdentifier(), new String[0]);
					AntiCheatKickableOther.setItem(slot, g);
				} if (!(AntiCheat.getConfig().getBoolean("checks.Other" + "." + check.getName() + "." + check.getIdentifier() + ".kickable"))) {
					final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 14, 1, Color.Red + check.getIdentifier(), new String[0]);
					AntiCheatKickableOther.setItem(slot, c);
				}
				slot++;
			}
		}
		for (int i = slot; i < 44; i++) {
			final ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatKickableOther.setItem(i, c);
		}
		AntiCheatKickableOther.setItem(44, back);
		p.openInventory(AntiCheatKickableOther);
	}
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getInventory().getName().equals(Color.Gold + "Home")) {
			final Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (!e.getCurrentItem().hasItemMeta()) {
				return;
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.contains(Color.translate("&cChecks"))) {
				openChecks(player);
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.equals(Color.translate("&cAuto Bans"))) {
				openAutoBans(player);
			}

			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.equals(Color.translate("&cTimers"))) {
				openTimer(player);
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.equals(Color.translate("&cJDay"))) {
				openJDay(player);
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.equals(Color.translate("&cAuto Kicks"))) {
				openKickable(player);
			}

			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.equals(Color.translate("&cTest Mode"))) {
				if (AntiCheat.getConfig().getBoolean("settings.testmode")) {
					AntiCheat.getConfig().set("settings.testmode", false);
					AntiCheat.saveConfig();
					final ItemStack testmode = createItem(
							AntiCheat.getConfig().getBoolean("settings.testmode") ? Material.EMERALD_BLOCK
									: Material.TNT,
									1, "&cTest Mode", new String[0]);
					AntiCheatmain.setItem(27, testmode);
				} else {
					AntiCheat.getConfig().set("settings.testmode", true);
					AntiCheat.saveConfig();
					final ItemStack testmode = createItem(
							AntiCheat.getConfig().getBoolean("settings.testmode") ? Material.EMERALD_BLOCK
									: Material.TNT,
									1, "&cTest Mode", new String[0]);
					AntiCheatmain.setItem(27, testmode);
				}
			}

			if (Color.strip(e.getCurrentItem().getItemMeta().getDisplayName()).equals("Checkered")) {
				AntiCheat.getConfig().set("settings.gui.checkered",
						AntiCheat.getConfig().getBoolean("settings.gui.checkered") ? false : true);
				AntiCheat.saveConfig();
				@SuppressWarnings("unused")
				final
				ItemStack testmode = createItem(
						AntiCheat.getConfig().getBoolean("settings.testmode") ? Material.EMERALD_BLOCK
								: Material.TNT,
								1, "&cTest Mode", new String[0]);
				if (AntiCheat.getConfig().contains("settings.gui.checkered")) {
					if (AntiCheat.getConfig().getBoolean("settings.gui.checkered")) {
						AntiCheatmain.setItem(0, whiteGlass());
						AntiCheatmain.setItem(2, whiteGlass());
						AntiCheatmain.setItem(4, whiteGlass());
						AntiCheatmain.setItem(6, whiteGlass());
						AntiCheatmain.setItem(8, whiteGlass());
						AntiCheatmain.setItem(10, whiteGlass());
						AntiCheatmain.setItem(12, whiteGlass());
						AntiCheatmain.setItem(14, whiteGlass());
						AntiCheatmain.setItem(16, whiteGlass());
						AntiCheatmain.setItem(18, whiteGlass());
						AntiCheatmain.setItem(20, whiteGlass());
						AntiCheatmain.setItem(22, whiteGlass());
						AntiCheatmain.setItem(24, whiteGlass());
						AntiCheatmain.setItem(26, whiteGlass());
						AntiCheatmain.setItem(28, whiteGlass());
						AntiCheatmain.setItem(30, whiteGlass());
						AntiCheatmain.setItem(32, whiteGlass());
						AntiCheatmain.setItem(34, whiteGlass());
					} else {
						AntiCheatmain.setItem(0, grayGlass());
						AntiCheatmain.setItem(2, grayGlass());
						AntiCheatmain.setItem(4, grayGlass());
						AntiCheatmain.setItem(6, grayGlass());
						AntiCheatmain.setItem(8, grayGlass());
						AntiCheatmain.setItem(10, grayGlass());
						AntiCheatmain.setItem(12, grayGlass());
						AntiCheatmain.setItem(14, grayGlass());
						AntiCheatmain.setItem(16, grayGlass());
						AntiCheatmain.setItem(18, grayGlass());
						AntiCheatmain.setItem(20, grayGlass());
						AntiCheatmain.setItem(22, grayGlass());
						AntiCheatmain.setItem(24, grayGlass());
						AntiCheatmain.setItem(26, grayGlass());
						AntiCheatmain.setItem(28, grayGlass());
						AntiCheatmain.setItem(30, grayGlass());
						AntiCheatmain.setItem(32, grayGlass());
						AntiCheatmain.setItem(34, grayGlass());
					}
				} else {
					AntiCheat.getConfig().set("settings.gui.checkered", true);
					AntiCheatmain.setItem(0, whiteGlass());
					AntiCheatmain.setItem(2, whiteGlass());
					AntiCheatmain.setItem(4, whiteGlass());
					AntiCheatmain.setItem(6, whiteGlass());
					AntiCheatmain.setItem(8, whiteGlass());
					AntiCheatmain.setItem(10, whiteGlass());
					AntiCheatmain.setItem(12, whiteGlass());
					AntiCheatmain.setItem(14, whiteGlass());
					AntiCheatmain.setItem(16, whiteGlass());
					AntiCheatmain.setItem(18, whiteGlass());
					AntiCheatmain.setItem(20, whiteGlass());
					AntiCheatmain.setItem(22, whiteGlass());
					AntiCheatmain.setItem(24, whiteGlass());
					AntiCheatmain.setItem(26, whiteGlass());
					AntiCheatmain.setItem(28, whiteGlass());
					AntiCheatmain.setItem(30, whiteGlass());
					AntiCheatmain.setItem(32, whiteGlass());
					AntiCheatmain.setItem(34, whiteGlass());
				}
			}

			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.equals(Color.translate("&cReset Violations"))) {
				AntiCheat.resetAllViolations();
				final ItemMeta meta = e.getCurrentItem().getItemMeta();
				meta.setDisplayName(Color.Green + Color.Italics + "Success!");
				e.getCurrentItem().setItemMeta(meta);
				new BukkitRunnable() {
					@Override
					public void run() {
						final ItemMeta meta = e.getCurrentItem().getItemMeta();
						meta.setDisplayName(Color.Red + "Reset Violations");
						e.getCurrentItem().setItemMeta(meta);
					}
				}.runTaskLater(AntiCheat, 40L);
			}

			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.equals(Color.translate("&cReload"))) {
				final ItemMeta meta = e.getCurrentItem().getItemMeta();
				meta.setDisplayName(Color.Red + Color.Italics + "Reloading...");
				e.getCurrentItem().setItemMeta(meta);
				AntiCheat.reloadConfig();
				meta.setDisplayName(Color.Green + Color.Italics + "Success!");
				e.getCurrentItem().setItemMeta(meta);
				new BukkitRunnable() {
					@Override
					public void run() {
						final ItemMeta meta = e.getCurrentItem().getItemMeta();
						meta.setDisplayName(Color.Red + "Reload");
						e.getCurrentItem().setItemMeta(meta);
						openAntiCheatMain(player);
					}
				}.runTaskLater(AntiCheat, 40L);
			}
		} else if (e.getInventory().getName().equals(Color.Gold + "Client: Bannable")) {
			final Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().hasItemMeta()) {
				final String check_name = e.getCurrentItem().getItemMeta().getDisplayName();
				for (final Check check : AntiCheat.getChecks()) {
					if (check.getType().equals(CheckType.Client)) {
						if (check.getIdentifier().equals(Color.strip(check_name))) {
							check.getType().equals(CheckType.Client);
							if (AntiCheat.getConfig().getBoolean("checks.Client" + "." + check.getName() + "." + check.getIdentifier() + ".bannable")) {
								check.getType().equals(CheckType.Client);
								AntiCheat.getConfig().set("checks.Client" + "." + check.getName() + "." + check.getIdentifier() + ".bannable", false);
								AntiCheat.saveConfig();
								AntiCheat.reloadConfig();
								check.setBannable(false);
								openAutoBansClient(player);
								return;
							}
							check.getType().equals(CheckType.Client);
							AntiCheat.getConfig().set("checks.Client" + "." + check.getName() + "." + check.getIdentifier() + ".bannable", true);
							AntiCheat.saveConfig();
							AntiCheat.reloadConfig();
							check.setBannable(true);
							openAutoBansClient(player);
							return;
						}
					}
				}
				if (Color.strip(check_name).equals("Back")) {
					openAutoBans(player);
				}
			}
		} else if (e.getInventory().getName().equals(Color.Gold + "Combat: Bannable")) {
			final Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().hasItemMeta()) {
				final String check_name = e.getCurrentItem().getItemMeta().getDisplayName();
				for (final Check check : AntiCheat.getChecks()) {
					if (check.getType().equals(CheckType.Combat)) {
						if (check.getIdentifier().equals(Color.strip(check_name))) {
							check.getType().equals(CheckType.Combat);
							if (AntiCheat.getConfig().getBoolean("checks.Combat" + "." + check.getName() + "." + check.getIdentifier() + ".bannable")) {
								check.getType().equals(CheckType.Combat);
								AntiCheat.getConfig().set("checks.Combat" + "." + check.getName() + "." + check.getIdentifier() + ".bannable", false);
								AntiCheat.saveConfig();
								AntiCheat.reloadConfig();
								check.setBannable(false);
								openAutoBansCombat(player);
								return;
							}
							check.getType().equals(CheckType.Combat);
							AntiCheat.getConfig().set("checks.Combat" + "." + check.getName() + "." + check.getIdentifier() + ".bannable", true);
							AntiCheat.saveConfig();
							AntiCheat.reloadConfig();
							check.setBannable(true);
							openAutoBansCombat(player);
							return;
						}
					}
				}
				if (Color.strip(check_name).equals("Back")) {
					openAutoBans(player);
				}
			}
		} else if (e.getInventory().getName().equals(Color.Gold + "Movement: Bannable")) {
			final Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().hasItemMeta()) {
				final String check_name = e.getCurrentItem().getItemMeta().getDisplayName();
				for (final Check check : AntiCheat.getChecks()) {
					if (check.getType().equals(CheckType.Movement)) {
						if (check.getIdentifier().equals(Color.strip(check_name))) {
							check.getType().equals(CheckType.Movement);
							if (AntiCheat.getConfig().getBoolean("checks.Movement" + "." + check.getName() + "." + check.getIdentifier() + ".bannable")) {
								check.getType().equals(CheckType.Movement);
								AntiCheat.getConfig().set("checks.Movement" + "." + check.getName() + "." + check.getIdentifier() + ".bannable", false);
								AntiCheat.saveConfig();
								AntiCheat.reloadConfig();
								check.setBannable(false);
								openAutoBansMovement(player);
								return;
							}
							check.getType().equals(CheckType.Movement);
							AntiCheat.getConfig().set("checks.Movement" + "." + check.getName() + "." + check.getIdentifier() + ".bannable", true);
							AntiCheat.saveConfig();
							AntiCheat.reloadConfig();
							check.setBannable(true);
							openAutoBansMovement(player);
							return;
						}
					}
				}
				if (Color.strip(check_name).equals("Back")) {
					openAutoBans(player);
				}
			}
		} else if (e.getInventory().getName().equals(Color.Gold + "Player: Bannable")) {
			final Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().hasItemMeta()) {
				final String check_name = e.getCurrentItem().getItemMeta().getDisplayName();
				for (final Check check : AntiCheat.getChecks()) {
					if (check.getType().equals(CheckType.Player)) {
						if (check.getIdentifier().equals(Color.strip(check_name))) {
							check.getType().equals(CheckType.Player);
							if (AntiCheat.getConfig().getBoolean("checks.Player" + "." + check.getName() + "." + check.getIdentifier() + ".bannable")) {
								check.getType().equals(CheckType.Player);
								AntiCheat.getConfig().set("checks.Player" + "." + check.getName() + "." + check.getIdentifier() + ".bannable", false);
								AntiCheat.saveConfig();
								AntiCheat.reloadConfig();
								check.setBannable(false);
								openAutoBansPlayer(player);
								return;
							}
							check.getType().equals(CheckType.Player);
							AntiCheat.getConfig().set("checks.Player" + "." + check.getName() + "." + check.getIdentifier() + ".bannable", true);
							AntiCheat.saveConfig();
							AntiCheat.reloadConfig();
							check.setBannable(true);
							openAutoBansPlayer(player);
							return;
						}
					}
				}
				if (Color.strip(check_name).equals("Back")) {
					openAutoBans(player);
				}
			}
		} else if (e.getInventory().getName().equals(Color.Gold + "Other: Bannable")) {
			final Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().hasItemMeta()) {
				final String check_name = e.getCurrentItem().getItemMeta().getDisplayName();
				for (final Check check : AntiCheat.getChecks()) {
					if (check.getType().equals(CheckType.Other)) {
						if (check.getIdentifier().equals(Color.strip(check_name))) {
							check.getType().equals(CheckType.Other);
							if (AntiCheat.getConfig().getBoolean("checks.Other" + "." + check.getName() + "." + check.getIdentifier() + ".bannable")) {
								check.getType().equals(CheckType.Other);
								AntiCheat.getConfig().set("checks.Other" + "." + check.getName() + "." + check.getIdentifier() + ".bannable", false);
								AntiCheat.saveConfig();
								AntiCheat.reloadConfig();
								check.setBannable(false);
								openAutoBansOther(player);
								return;
							}
							check.getType().equals(CheckType.Other);
							AntiCheat.getConfig().set("checks.Other" + "." + check.getName() + "." + check.getIdentifier() + ".bannable", true);
							AntiCheat.saveConfig();
							AntiCheat.reloadConfig();
							check.setBannable(true);
							openAutoBansOther(player);
							return;
						}
					}
				}
				if (Color.strip(check_name).equals("Back")) {
					openAutoBans(player);
				}
			}
		} else if (e.getInventory().getName().equals(Color.Gold + "Checks: Toggle")) {
			final Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (!e.getCurrentItem().hasItemMeta()) {
				return;
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.contains(Color.translate("&aCombat"))) {
				openChecksCombat(player);
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.contains(Color.translate("&aClient"))) {
				openChecksClient(player);
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.contains(Color.translate("&aMovement"))) {
				openChecksMovement(player);
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.contains(Color.translate("&aOther"))) {
				openChecksOther(player);
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.contains(Color.translate("&aPlayer"))) {
				openChecksPlayer(player);
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Back")) {
				openAntiCheatMain(player);
			}
		} else if (e.getInventory().getName().equals(Color.Gold + "Checks: Kickable")) {
			final Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (!e.getCurrentItem().hasItemMeta()) {
				return;
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.contains(Color.translate("&aCombat"))) {
				openKickableCombat(player);
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.contains(Color.translate("&aClient"))) {
				openKickableClient(player);
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.contains(Color.translate("&aMovement"))) {
				openKickableMovement(player);
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.contains(Color.translate("&aOther"))) {
				openKickableOther(player);
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.contains(Color.translate("&aPlayer"))) {
				openKickablePlayer(player);
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Back")) {
				openAntiCheatMain(player);
			}
		} else if (e.getInventory().getName().equals(Color.Gold + "Client: JDay")) {
			final Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().hasItemMeta()) {
				final String check_name = e.getCurrentItem().getItemMeta().getDisplayName();
				for (final Check check : AntiCheat.getChecks()) {
					if (check.getType().equals(CheckType.Client)) {
						if (check.getIdentifier().equals(Color.strip(check_name))) {
							check.getType().equals(CheckType.Client);
							if (AntiCheat.getConfig().getBoolean("checks.Client" + "." + check.getName() + "." + check.getIdentifier() + ".judgementDay")) {
								check.getType().equals(CheckType.Client);
								AntiCheat.getConfig().set("checks.Client" + "." + check.getName() + "." + check.getIdentifier() + ".judgementDay", false);
								AntiCheat.saveConfig();
								AntiCheat.reloadConfig();
								check.setJudgementDay(false);
								openJDayClient(player);
								return;
							}
							check.getType().equals(CheckType.Client);
							AntiCheat.getConfig().set("checks.Client" + "." + check.getName() + "." + check.getIdentifier() + ".judgementDay", true);
							AntiCheat.saveConfig();
							AntiCheat.reloadConfig();
							check.setJudgementDay(true);
							openJDayClient(player);
							return;
						}
					}
				}
				if (Color.strip(check_name).equals("Back")) {
					openJDay(player);
				}
			}
		} else if (e.getInventory().getName().equals(Color.Gold + "Combat: JDay")) {
			final Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().hasItemMeta()) {
				final String check_name = e.getCurrentItem().getItemMeta().getDisplayName();
				for (final Check check : AntiCheat.getChecks()) {
					if (check.getType().equals(CheckType.Combat)) {
						if (check.getIdentifier().equals(Color.strip(check_name))) {
							check.getType().equals(CheckType.Combat);
							if (AntiCheat.getConfig().getBoolean("checks.Combat" + "." + check.getName() + "." + check.getIdentifier() + ".judgementDay")) {
								check.getType().equals(CheckType.Combat);
								AntiCheat.getConfig().set("checks.Combat" + "." + check.getName() + "." + check.getIdentifier() + ".judgementDay", false);
								AntiCheat.saveConfig();
								AntiCheat.reloadConfig();
								check.setJudgementDay(false);
								openJDayCombat(player);
								return;
							}
							check.getType().equals(CheckType.Combat);
							AntiCheat.getConfig().set("checks.Combat" + "." + check.getName() + "." + check.getIdentifier() + ".judgementDay", true);
							AntiCheat.saveConfig();
							AntiCheat.reloadConfig();
							check.setJudgementDay(true);
							openJDayCombat(player);
							return;
						}
					}
				}
				if (Color.strip(check_name).equals("Back")) {
					openJDay(player);
				}
			}
		} else if (e.getInventory().getName().equals(Color.Gold + "Movement: JDay")) {
			final Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().hasItemMeta()) {
				final String check_name = e.getCurrentItem().getItemMeta().getDisplayName();
				for (final Check check : AntiCheat.getChecks()) {
					if (check.getType().equals(CheckType.Movement)) {
						if (check.getIdentifier().equals(Color.strip(check_name))) {
							check.getType().equals(CheckType.Movement);
							if (AntiCheat.getConfig().getBoolean("checks.Movement" + "." + check.getName() + "." + check.getIdentifier() + ".judgementDay")) {
								check.getType().equals(CheckType.Movement);
								AntiCheat.getConfig().set("checks.Movement" + "." + check.getName() + "." + check.getIdentifier() + ".judgementDay", false);
								AntiCheat.saveConfig();
								AntiCheat.reloadConfig();
								check.setJudgementDay(false);
								openJDayMovement(player);
								return;
							}
							check.getType().equals(CheckType.Movement);
							AntiCheat.getConfig().set("checks.Movement" + "." + check.getName() + "." + check.getIdentifier() + ".judgementDay", true);
							AntiCheat.saveConfig();
							AntiCheat.reloadConfig();
							check.setJudgementDay(true);
							openJDayMovement(player);
							return;
						}
					}
				}
				if (Color.strip(check_name).equals("Back")) {
					openJDay(player);
				}
			}
		} else if (e.getInventory().getName().equals(Color.Gold + "Player: JDay")) {
			final Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().hasItemMeta()) {
				final String check_name = e.getCurrentItem().getItemMeta().getDisplayName();
				for (final Check check : AntiCheat.getChecks()) {
					if (check.getType().equals(CheckType.Player)) {
						if (check.getIdentifier().equals(Color.strip(check_name))) {
							check.getType().equals(CheckType.Player);
							if (AntiCheat.getConfig().getBoolean("checks.Player" + "." + check.getName() + "." + check.getIdentifier() + ".judgementDay")) {
								check.getType().equals(CheckType.Player);
								AntiCheat.getConfig().set("checks.Player" + "." + check.getName() + "." + check.getIdentifier() + ".judgementDay", false);
								AntiCheat.saveConfig();
								AntiCheat.reloadConfig();
								check.setJudgementDay(false);
								openJDayPlayer(player);
								return;
							}
							check.getType().equals(CheckType.Player);
							AntiCheat.getConfig().set("checks.Player" + "." + check.getName() + "." + check.getIdentifier() + ".judgementDay", true);
							AntiCheat.saveConfig();
							AntiCheat.reloadConfig();
							check.setJudgementDay(true);
							openJDayPlayer(player);
							return;
						}
					}
				}
				if (Color.strip(check_name).equals("Back")) {
					openJDay(player);
				}
			}
		} else if (e.getInventory().getName().equals(Color.Gold + "Other: JDay")) {
			final Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().hasItemMeta()) {
				final String check_name = e.getCurrentItem().getItemMeta().getDisplayName();
				for (final Check check : AntiCheat.getChecks()) {
					if (check.getType().equals(CheckType.Other)) {
						if (check.getIdentifier().equals(Color.strip(check_name))) {
							check.getType().equals(CheckType.Other);
							if (AntiCheat.getConfig().getBoolean("checks.Other" + "." + check.getName() + "." + check.getIdentifier() + ".judgementDay")) {
								check.getType().equals(CheckType.Other);
								AntiCheat.getConfig().set("checks.Other" + "." + check.getName() + "." + check.getIdentifier() + ".judgementDay", false);
								AntiCheat.saveConfig();
								AntiCheat.reloadConfig();
								check.setJudgementDay(false);
								openJDayOther(player);
								return;
							}
							check.getType().equals(CheckType.Other);
							AntiCheat.getConfig().set("checks.Other" + "." + check.getName() + "." + check.getIdentifier() + ".judgementDay", true);
							AntiCheat.saveConfig();
							AntiCheat.reloadConfig();
							check.setJudgementDay(true);
							openJDayOther(player);
							return;
						}
					}
				}
				if (Color.strip(check_name).equals("Back")) {
					openJDay(player);
				}
			}
		} else if (e.getInventory().getName().equals(Color.Gold + "Client: Kickable")) {
			final Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().hasItemMeta()) {
				final String check_name = e.getCurrentItem().getItemMeta().getDisplayName();
				for (final Check check : AntiCheat.getChecks()) {
					if (check.getType().equals(CheckType.Client)) {
						if (check.getIdentifier().equals(Color.strip(check_name))) {
							check.getType().equals(CheckType.Client);
							if (AntiCheat.getConfig().getBoolean("checks.Client" + "." + check.getName() + "." + check.getIdentifier() + ".kickable")) {
								check.getType().equals(CheckType.Client);
								AntiCheat.getConfig().set("checks.Client" + "." + check.getName() + "." + check.getIdentifier() + ".kickable", false);
								AntiCheat.saveConfig();
								AntiCheat.reloadConfig();
								check.setKickable(false);
								openKickableClient(player);
								return;
							}
							check.getType().equals(CheckType.Client);
							AntiCheat.getConfig().set("checks.Client" + "." + check.getName() + "." + check.getIdentifier() + ".kickable", true);
							AntiCheat.saveConfig();
							AntiCheat.reloadConfig();
							check.setKickable(true);
							openKickableClient(player);
							return;
						}
					}
				}
				if (Color.strip(check_name).equals("Back")) {
					openKickable(player);
				}
			}
		} else if (e.getInventory().getName().equals(Color.Gold + "Combat: Kickable")) {
			final Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().hasItemMeta()) {
				final String check_name = e.getCurrentItem().getItemMeta().getDisplayName();
				for (final Check check : AntiCheat.getChecks()) {
					if (check.getType().equals(CheckType.Combat)) {
						if (check.getIdentifier().equals(Color.strip(check_name))) {
							check.getType().equals(CheckType.Combat);
							if (AntiCheat.getConfig().getBoolean("checks.Combat" + "." + check.getName() + "." + check.getIdentifier() + ".kickable")) {
								check.getType().equals(CheckType.Combat);
								AntiCheat.getConfig().set("checks.Combat" + "." + check.getName() + "." + check.getIdentifier() + ".kickable", false);
								AntiCheat.saveConfig();
								AntiCheat.reloadConfig();
								check.setKickable(false);
								openKickableCombat(player);
								return;
							}
							check.getType().equals(CheckType.Combat);
							AntiCheat.getConfig().set("checks.Combat" + "." + check.getName() + "." + check.getIdentifier() + ".kickable", true);
							AntiCheat.saveConfig();
							AntiCheat.reloadConfig();
							check.setKickable(true);
							openKickableCombat(player);
							return;
						}
					}
				}
				if (Color.strip(check_name).equals("Back")) {
					openKickable(player);
				}
			}
		} else if (e.getInventory().getName().equals(Color.Gold + "Movement: Kickable")) {
			final Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().hasItemMeta()) {
				final String check_name = e.getCurrentItem().getItemMeta().getDisplayName();
				for (final Check check : AntiCheat.getChecks()) {
					if (check.getType().equals(CheckType.Movement)) {
						if (check.getIdentifier().equals(Color.strip(check_name))) {
							check.getType().equals(CheckType.Movement);
							if (AntiCheat.getConfig().getBoolean("checks.Movement" + "." + check.getName() + "." + check.getIdentifier() + ".kickable")) {
								check.getType().equals(CheckType.Movement);
								AntiCheat.getConfig().set("checks.Movement" + "." + check.getName() + "." + check.getIdentifier() + ".kickable", false);
								AntiCheat.saveConfig();
								AntiCheat.reloadConfig();
								check.setKickable(false);
								openKickableMovement(player);
								return;
							}
							check.getType().equals(CheckType.Movement);
							AntiCheat.getConfig().set("checks.Movement" + "." + check.getName() + "." + check.getIdentifier() + ".kickable", true);
							AntiCheat.saveConfig();
							AntiCheat.reloadConfig();
							check.setKickable(true);
							openKickableMovement(player);
							return;
						}
					}
				}
				if (Color.strip(check_name).equals("Back")) {
					openKickable(player);
				}
			}
		} else if (e.getInventory().getName().equals(Color.Gold + "Player: Kickable")) {
			final Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().hasItemMeta()) {
				final String check_name = e.getCurrentItem().getItemMeta().getDisplayName();
				for (final Check check : AntiCheat.getChecks()) {
					if (check.getType().equals(CheckType.Player)) {
						if (check.getIdentifier().equals(Color.strip(check_name))) {
							check.getType().equals(CheckType.Player);
							if (AntiCheat.getConfig().getBoolean("checks.Player" + "." + check.getName() + "." + check.getIdentifier() + ".kickable")) {
								check.getType().equals(CheckType.Player);
								AntiCheat.getConfig().set("checks.Player" + "." + check.getName() + "." + check.getIdentifier() + ".kickable", false);
								AntiCheat.saveConfig();
								AntiCheat.reloadConfig();
								check.setKickable(false);
								openKickablePlayer(player);
								return;
							}
							check.getType().equals(CheckType.Player);
							AntiCheat.getConfig().set("checks.Player" + "." + check.getName() + "." + check.getIdentifier() + ".kickable", true);
							AntiCheat.saveConfig();
							AntiCheat.reloadConfig();
							check.setKickable(true);
							openKickablePlayer(player);
							return;
						}
					}
				}
				if (Color.strip(check_name).equals("Back")) {
					openKickable(player);
				}
			}
		} else if (e.getInventory().getName().equals(Color.Gold + "Other: Kickable")) {
			final Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().hasItemMeta()) {
				final String check_name = e.getCurrentItem().getItemMeta().getDisplayName();
				for (final Check check : AntiCheat.getChecks()) {
					if (check.getType().equals(CheckType.Other)) {
						if (check.getIdentifier().equals(Color.strip(check_name))) {
							check.getType().equals(CheckType.Other);
							if (AntiCheat.getConfig().getBoolean("checks.Other" + "." + check.getName() + "." + check.getIdentifier() + ".kickable")) {
								check.getType().equals(CheckType.Other);
								AntiCheat.getConfig().set("checks.Other" + "." + check.getName() + "." + check.getIdentifier() + ".kickable", false);
								AntiCheat.saveConfig();
								AntiCheat.reloadConfig();
								check.setKickable(false);
								openKickableOther(player);
								return;
							}
							check.getType().equals(CheckType.Other);
							AntiCheat.getConfig().set("checks.Other" + "." + check.getName() + "." + check.getIdentifier() + ".kickable", true);
							AntiCheat.saveConfig();
							AntiCheat.reloadConfig();
							check.setKickable(true);
							openKickableOther(player);
							return;
						}
					}
				}
				if (Color.strip(check_name).equals("Back")) {
					openKickable(player);
				}
			}
		} else if (e.getInventory().getName().equals(Color.Gold + "Client: Toggle")) {
			final Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().hasItemMeta()) {
				final String check_name = e.getCurrentItem().getItemMeta().getDisplayName();
				for (final Check check : AntiCheat.getChecks()) {
					if (check.getType().equals(CheckType.Client)) {
						if (check.getIdentifier().equals(Color.strip(check_name))) {
							check.getType().equals(CheckType.Client);
							if (AntiCheat.getConfig().getBoolean("checks.Client" + "." + check.getName() + "." + check.getIdentifier() + ".enabled")) {
								check.getType().equals(CheckType.Client);
								AntiCheat.getConfig().set("checks.Client" + "." + check.getName() + "." + check.getIdentifier() + ".enabled", false);
								AntiCheat.saveConfig();
								AntiCheat.reloadConfig();
								check.setEnabled(false);
								openChecksClient(player);
								return;
							}
							check.getType().equals(CheckType.Client);
							AntiCheat.getConfig().set("checks.Client" + "." + check.getName() + "." + check.getIdentifier() + ".enabled", true);
							AntiCheat.saveConfig();
							AntiCheat.reloadConfig();
							check.setEnabled(true);
							openChecksClient(player);
							return;
						}
					}
				}
				if (Color.strip(check_name).equals("Back")) {
					openChecks(player);
				}
			}
		} else if (e.getInventory().getName().equals(Color.Gold + "Combat: Toggle")) {
			final Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().hasItemMeta()) {
				final String check_name = e.getCurrentItem().getItemMeta().getDisplayName();
				for (final Check check : AntiCheat.getChecks()) {
					if (check.getIdentifier().equals(Color.strip(check_name))) {
						check.getType();
						check.getType();
						if (AntiCheat.getConfig().getBoolean("checks." + CheckType.Combat + "." + check.getName() + "." + check.getIdentifier() + ".enabled")) {
							check.getType();
							check.getType();
							AntiCheat.getConfig().set("checks." + CheckType.Combat + "." + check.getName() + "." + check.getIdentifier() + ".enabled", false);
							AntiCheat.saveConfig();
							AntiCheat.reloadConfig();
							check.setEnabled(false);
							openChecksCombat(player);
							return;
						}
						check.getType();
						check.getType();
						AntiCheat.getConfig().set("checks." + CheckType.Combat + "." + check.getName() + "." + check.getIdentifier() + ".enabled", true);
						AntiCheat.saveConfig();
						AntiCheat.reloadConfig();
						check.setEnabled(true);
						openChecksCombat(player);
						return;
					}
				}
				if (Color.strip(check_name).equals("Back")) {
					openChecks(player);
				}
			}
		} else if (e.getInventory().getName().equals(Color.Gold + "Movement: Toggle")) {
			final Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().hasItemMeta()) {
				final String check_name = e.getCurrentItem().getItemMeta().getDisplayName();
				for (final Check check : AntiCheat.getChecks()) {
					if (check.getIdentifier().equals(Color.strip(check_name))) {
						check.getType();
						check.getType();
						if (AntiCheat.getConfig().getBoolean("checks." + CheckType.Movement + "." + check.getName() + "." + check.getIdentifier() + ".enabled")) {
							check.getType();
							check.getType();
							AntiCheat.getConfig().set("checks." + CheckType.Movement + "." + check.getName() + "." + check.getIdentifier() + ".enabled", false);
							AntiCheat.saveConfig();
							AntiCheat.reloadConfig();
							check.setEnabled(false);
							openChecksMovement(player);
							return;
						}
						check.getType();
						check.getType();
						AntiCheat.getConfig().set("checks." + CheckType.Movement + "." + check.getName() + "." + check.getIdentifier() + ".enabled", true);
						AntiCheat.saveConfig();
						AntiCheat.reloadConfig();
						check.setEnabled(true);
						openChecksMovement(player);
						return;
					}
				}
				if (Color.strip(check_name).equals("Back")) {
					openChecks(player);
				}
			}
		} else if (e.getInventory().getName().equals(Color.Gold + "Player: Toggle")) {
			final Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().hasItemMeta()) {
				final String check_name = e.getCurrentItem().getItemMeta().getDisplayName();
				for (final Check check : AntiCheat.getChecks()) {
					if (check.getIdentifier().equals(Color.strip(check_name))) {
						check.getType();
						check.getType();
						if (AntiCheat.getConfig().getBoolean("checks." + CheckType.Player + "." + check.getName() + "." + check.getIdentifier() + ".enabled")) {
							check.getType();
							check.getType();
							AntiCheat.getConfig().set("checks." + CheckType.Player + "." + check.getName() + "." + check.getIdentifier() + ".enabled", false);
							AntiCheat.saveConfig();
							AntiCheat.reloadConfig();
							check.setEnabled(false);
							openChecksPlayer(player);
							return;
						}
						check.getType();
						check.getType();
						AntiCheat.getConfig().set("checks." + CheckType.Player + "." + check.getName() + "." + check.getIdentifier() + ".enabled", true);
						AntiCheat.saveConfig();
						AntiCheat.reloadConfig();
						check.setEnabled(true);
						openChecksPlayer(player);
						return;
					}
				}
				if (Color.strip(check_name).equals("Back")) {
					openChecks(player);
				}
			}
		} else if (e.getInventory().getName().equals(Color.Gold + "Other: Toggle")) {
			final Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().hasItemMeta()) {
				final String check_name = e.getCurrentItem().getItemMeta().getDisplayName();
				for (final Check check : AntiCheat.getChecks()) {
					if (check.getIdentifier().equals(Color.strip(check_name))) {
						check.getType();
						check.getType();
						if (AntiCheat.getConfig().getBoolean("checks." + CheckType.Other + "." + check.getName() + "." + check.getIdentifier() + ".enabled")) {
							check.getType();
							check.getType();
							AntiCheat.getConfig().set("checks." + CheckType.Other + "." + check.getName() + "." + check.getIdentifier() + ".enabled", false);
							AntiCheat.saveConfig();
							AntiCheat.reloadConfig();
							check.setEnabled(false);
							openChecksOther(player);
							return;
						}
						check.getType();
						check.getType();
						AntiCheat.getConfig().set("checks." + CheckType.Other + "." + check.getName() + "." + check.getIdentifier() + ".enabled", true);
						AntiCheat.saveConfig();
						AntiCheat.reloadConfig();
						check.setEnabled(true);
						openChecksOther(player);
						return;
					}
				}
				if (Color.strip(check_name).equals("Back")) {
					openChecks(player);
				}
			}
		} else if (e.getInventory().getName().equals(Color.Gold + "Client: BanTimer")) {
			final Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().hasItemMeta()) {
				final String check_name = e.getCurrentItem().getItemMeta().getDisplayName();
				for (final Check check : AntiCheat.getChecks()) {
					if (check.getType().equals(CheckType.Client)) {
						if (check.getIdentifier().equals(Color.strip(check_name))) {
							check.getType().equals(CheckType.Client);
							if (AntiCheat.getConfig().getBoolean("checks.Client" + "." + check.getName() + "." + check.getIdentifier() + ".banTimer")) {
								check.getType().equals(CheckType.Client);
								AntiCheat.getConfig().set("checks.Client" + "." + check.getName() + "." + check.getIdentifier() + ".banTimer", false);
								AntiCheat.saveConfig();
								AntiCheat.reloadConfig();
								check.setAutobanTimer(false);
								openTimerClient(player);
								return;
							}
							check.getType().equals(CheckType.Client);
							AntiCheat.getConfig().set("checks.Client" + "." + check.getName() + "." + check.getIdentifier() + ".banTimer", true);
							AntiCheat.saveConfig();
							AntiCheat.reloadConfig();
							check.setAutobanTimer(true);
							openTimerClient(player);
							return;
						}
					}
				}
				if (Color.strip(check_name).equals("Back")) {
					openTimer(player);
				}
			}
		} else if (e.getInventory().getName().equals(Color.Gold + "Combat: BanTimer")) {
			final Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().hasItemMeta()) {
				final String check_name = e.getCurrentItem().getItemMeta().getDisplayName();
				for (final Check check : AntiCheat.getChecks()) {
					if (check.getType().equals(CheckType.Combat)) {
						if (check.getIdentifier().equals(Color.strip(check_name))) {
							check.getType().equals(CheckType.Combat);
							if (AntiCheat.getConfig().getBoolean("checks.Combat" + "." + check.getName() + "." + check.getIdentifier() + ".banTimer")) {
								check.getType().equals(CheckType.Combat);
								AntiCheat.getConfig().set("checks.Combat" + "." + check.getName() + "." + check.getIdentifier() + ".banTimer", false);
								AntiCheat.saveConfig();
								AntiCheat.reloadConfig();
								check.setAutobanTimer(false);
								openTimerCombat(player);
								return;
							}
							check.getType().equals(CheckType.Combat);
							AntiCheat.getConfig().set("checks.Combat" + "." + check.getName() + "." + check.getIdentifier() + ".banTimer", true);
							AntiCheat.saveConfig();
							AntiCheat.reloadConfig();
							check.setAutobanTimer(true);
							openTimerCombat(player);
							return;
						}
					}
				}
				if (Color.strip(check_name).equals("Back")) {
					openTimer(player);
				}
			}
		} else if (e.getInventory().getName().equals(Color.Gold + "Movement: BanTimer")) {
			final Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().hasItemMeta()) {
				final String check_name = e.getCurrentItem().getItemMeta().getDisplayName();
				for (final Check check : AntiCheat.getChecks()) {
					if (check.getType().equals(CheckType.Movement)) {
						if (check.getIdentifier().equals(Color.strip(check_name))) {
							check.getType().equals(CheckType.Movement);
							if (AntiCheat.getConfig().getBoolean("checks.Movement" + "." + check.getName() + "." + check.getIdentifier() + ".banTimer")) {
								check.getType().equals(CheckType.Movement);
								AntiCheat.getConfig().set("checks.Movement" + "." + check.getName() + "." + check.getIdentifier() + ".banTimer", false);
								AntiCheat.saveConfig();
								AntiCheat.reloadConfig();
								check.setAutobanTimer(false);
								openTimerMovement(player);
								return;
							}
							check.getType().equals(CheckType.Movement);
							AntiCheat.getConfig().set("checks.Movement" + "." + check.getName() + "." + check.getIdentifier() + ".banTimer", true);
							AntiCheat.saveConfig();
							AntiCheat.reloadConfig();
							check.setAutobanTimer(true);
							openTimerMovement(player);
							return;
						}
					}
				}
				if (Color.strip(check_name).equals("Back")) {
					openTimer(player);
				}
			}
		} else if (e.getInventory().getName().equals(Color.Gold + "Player: BanTimer")) {
			final Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().hasItemMeta()) {
				final String check_name = e.getCurrentItem().getItemMeta().getDisplayName();
				for (final Check check : AntiCheat.getChecks()) {
					if (check.getType().equals(CheckType.Player)) {
						if (check.getIdentifier().equals(Color.strip(check_name))) {
							check.getType().equals(CheckType.Player);
							if (AntiCheat.getConfig().getBoolean("checks.Player" + "." + check.getName() + "." + check.getIdentifier() + ".banTimer")) {
								check.getType().equals(CheckType.Player);
								AntiCheat.getConfig().set("checks.Player" + "." + check.getName() + "." + check.getIdentifier() + ".banTimer", false);
								AntiCheat.saveConfig();
								AntiCheat.reloadConfig();
								check.setAutobanTimer(false);
								openTimerPlayer(player);
								return;
							}
							check.getType().equals(CheckType.Player);
							AntiCheat.getConfig().set("checks.Player" + "." + check.getName() + "." + check.getIdentifier() + ".banTimer", true);
							AntiCheat.saveConfig();
							AntiCheat.reloadConfig();
							check.setAutobanTimer(true);
							openTimerPlayer(player);
							return;
						}
					}
				}
				if (Color.strip(check_name).equals("Back")) {
					openTimer(player);
				}
			}
		} else if (e.getInventory().getName().equals(Color.Gold + "Other: BanTimer")) {
			final Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().hasItemMeta()) {
				final String check_name = e.getCurrentItem().getItemMeta().getDisplayName();
				for (final Check check : AntiCheat.getChecks()) {
					if (check.getType().equals(CheckType.Other)) {
						if (check.getIdentifier().equals(Color.strip(check_name))) {
							check.getType().equals(CheckType.Other);
							if (AntiCheat.getConfig().getBoolean("checks.Other" + "." + check.getName() + "." + check.getIdentifier() + ".banTimer")) {
								check.getType().equals(CheckType.Other);
								AntiCheat.getConfig().set("checks.Other" + "." + check.getName() + "." + check.getIdentifier() + ".banTimer", false);
								AntiCheat.saveConfig();
								AntiCheat.reloadConfig();
								check.setAutobanTimer(false);
								openTimerOther(player);
								return;
							}
							check.getType().equals(CheckType.Other);
							AntiCheat.getConfig().set("checks.Other" + "." + check.getName() + "." + check.getIdentifier() + ".banTimer", true);
							AntiCheat.saveConfig();
							AntiCheat.reloadConfig();
							check.setAutobanTimer(true);
							openTimerOther(player);
							return;
						}
					}
				}
				if (Color.strip(check_name).equals("Back")) {
					openTimer(player);
				}
			}

		} else if (e.getInventory().getName().equals(Color.Gold + "Checks: JDay")) {
			final Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (!e.getCurrentItem().hasItemMeta()) {
				return;
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.contains(Color.translate("&aCombat"))) {
				openJDayCombat(player);
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.contains(Color.translate("&aClient"))) {
				openJDayClient(player);
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.contains(Color.translate("&aMovement"))) {
				openJDayMovement(player);
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.contains(Color.translate("&aOther"))) {
				openJDayOther(player);
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.contains(Color.translate("&aPlayer"))) {
				openJDayPlayer(player);
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Back")) {
				openAntiCheatMain(player);
			}
		} else if (e.getInventory().getName().equals(Color.Gold + "Checks: BanTimer")) {
			final Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (!e.getCurrentItem().hasItemMeta()) {
				return;
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.contains(Color.translate("&aCombat"))) {
				openTimerCombat(player);
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.contains(Color.translate("&aClient"))) {
				openTimerClient(player);
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.contains(Color.translate("&aMovement"))) {
				openTimerMovement(player);
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.contains(Color.translate("&aOther"))) {
				openTimerOther(player);
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.contains(Color.translate("&aPlayer"))) {
				openTimerPlayer(player);
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Back")) {
				openAntiCheatMain(player);
			}

		} else if (e.getInventory().getName().equals(Color.Gold + "Checks: Bannable")) {
			final Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (!e.getCurrentItem().hasItemMeta()) {
				return;
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.contains(Color.translate("&aCombat"))) {
				openAutoBansCombat(player);
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.contains(Color.translate("&aClient"))) {
				openAutoBansClient(player);
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.contains(Color.translate("&aMovement"))) {
				openAutoBansMovement(player);
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.contains(Color.translate("&aOther"))) {
				openAutoBansOther(player);
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.contains(Color.translate("&aPlayer"))) {
				openAutoBansPlayer(player);
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Back")) {
				openAntiCheatMain(player);
			}
		}
		if (e.getInventory().getName().equals(Color.Gold + "Recent Bans")) {
			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
		} if (e.getInventory().getName().equals(Color.Gold + "Status")) {
			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
		}
	}

	public static ItemStack createItem(Material material, int amount, String name, String... lore) {
		final ItemStack thing = new ItemStack(material, amount);
		final ItemMeta thingm = thing.getItemMeta();
		thingm.setDisplayName(Color.translate(name));
		thingm.setLore(Arrays.asList(lore));
		thing.setItemMeta(thingm);
		return thing;
	}

	public static ItemStack createGlass(Material material, int color, int amount, String name, String... lore) {
		final ItemStack thing = new ItemStack(material, amount, (short) color);
		final ItemMeta thingm = thing.getItemMeta();
		thingm.setDisplayName(Color.translate(name));
		thingm.setLore(Arrays.asList(lore));
		thing.setItemMeta(thingm);
		return thing;
	}

	public static ItemStack grayGlass() {
		final ItemStack thing = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		final ItemMeta thingm = thing.getItemMeta();
		thingm.setDisplayName(Color.translate("&b"));
		thing.setItemMeta(thingm);
		return thing;
	}

	public static ItemStack whiteGlass() {
		final ItemStack thing = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 0);
		final ItemMeta thingm = thing.getItemMeta();
		thingm.setDisplayName(Color.translate("&b"));
		thing.setItemMeta(thingm);
		return thing;
	}

	public String c(String str) {
		return str.replaceAll("&", "��");
	}
}