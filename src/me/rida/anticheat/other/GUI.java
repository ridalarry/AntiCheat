package me.rida.anticheat.other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import me.rida.anticheat.utils.Color;

public class GUI implements Listener {
	public static Inventory AntiCheatmain = Bukkit.createInventory(null, 36, Color.Gold + "Home");
	public static Inventory AntiCheatchecks = Bukkit.createInventory(null, 90, Color.Gold + "Checks: Toggle");
	public static Inventory AntiCheatJDay = Bukkit.createInventory(null, 90, Color.Gold + "Checks: JDay");
	public static Inventory AntiCheatbannable = Bukkit.createInventory(null, 90, Color.Gold + "Checks: Bannable");
	public static Inventory AntiCheatTimer = Bukkit.createInventory(null, 90, Color.Gold + "Checks: BanTimer");
	public static Inventory AntiCheatbans = Bukkit.createInventory(null, 90, Color.Gold + "Recent Bans");
	public static Inventory AntiCheatstatus = Bukkit.createInventory(null, 27, Color.Gold + "Status");

	private static ItemStack back = createItem(Material.REDSTONE, 1, "&6Back", new String[0]);

	private static AntiCheat AntiCheat;

	@SuppressWarnings("static-access")
	public GUI(AntiCheat AntiCheat) {
		this.AntiCheat = AntiCheat;
		ItemStack enabled = createItem(Material.COMPASS, 1, "&cChecks", new String[0]);
		ItemStack Jday = createItem(Material.BOOK, 1, "&cJDay", new String[0]);
		ItemStack bannable = createItem(Material.REDSTONE, 1, "&cAuto Bans", new String[0]);
		ItemStack timers = createItem(Material.WATCH, 1, "&cTimers", new String[0]);
		ItemStack resetVio = createItem(Material.PAPER, 1, "&cReset Violations", new String[0]);
		ItemStack reload = createItem(Material.LAVA_BUCKET, 1, "&cReload", new String[0]);
		ItemStack info = createItem(Material.SIGN, 1, "&aInfo", new String[0]);
		ItemStack checkered = createItem(Material.COAL_BLOCK, 1,
				AntiCheat.getConfig().getBoolean("settings.gui.checkered") ? "&aCheckered" : "&cCheckered",
						new String[0]);
		ItemStack testmode = createItem(
				AntiCheat.getConfig().getBoolean("settings.testmode") ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK,
						1, "&cTest Mode", new String[0]);

		ItemMeta infom = info.getItemMeta();
		infom.setLore(infoLore());

		info.setItemMeta(infom);

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
		AntiCheatmain.setItem(31, grayGlass());
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
		ArrayList<String> list = new ArrayList<String>();

		list.add(" ");
		list.add(Color.translate("&7You can do &f/AntiCheat help &7to see your"));
		list.add(Color.translate("&7options for other &fcommands&7/&ffunctions&7!"));
		list.add(" ");
		list.add(ChatColor.translateAlternateColorCodes('&',
				"&7Current Version: &fb" + AntiCheat.getDescription().getVersion()));

		return list;
	}

	public static void openAntiCheatMain(Player player) {

		player.openInventory(AntiCheatmain);
	}

	public static void openBans(Player player) {
		List<Map.Entry<String, Check>> entrybans = new ArrayList<Map.Entry<String, Check>>(AntiCheat.getNamesBanned().entrySet());
		for (int i = 0; i < entrybans.size(); i++) {
			Map.Entry<String, Check> entry = entrybans.get(i);
			if (i <= 54) {
				ItemStack offender = createItem(Material.PAPER, 1, Color.Red + (String) entry.getKey(),
						new String[] { Color.Gray + ((Check) entry.getValue()).getName() });
				AntiCheatbans.setItem(i, offender);
			}
		}
	}

	public static void openStatus(Player player, Player target) {
		AntiCheatstatus = Bukkit.createInventory(player, 27, Color.Gold + "Status");

		Map<Check, Integer> Checks = AntiCheat.getViolations(target);
		if ((Checks == null) || (Checks.isEmpty())) {
			player.sendMessage(Color.Gray + "This player set off 0 checks. Yay!");
		} else {
			int slot = 0;
			for (Check Check : Checks.keySet()) {
				Integer Violations = (Integer) Checks.get(Check);

				ItemStack vl = createItem(Material.PAPER, 1,
						Color.Aqua + Check.getIdentifier() + Color.DGray + " [" + Color.Red + Violations + Color.DGray + "]", new String[0]);
				AntiCheatstatus.setItem(slot, vl);
				slot++;
			}
		}
		player.openInventory(AntiCheatstatus);
	}

	public void openChecks(Player player) {
		int slot = 0;
		for (Check check : AntiCheat.getChecks()) {
			if (AntiCheat.getConfig().getBoolean("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".enabled")) {
				ItemStack g = createGlass(Material.STAINED_GLASS_PANE, 5, 1, Color.Green + check.getIdentifier(), new String[0]);
				AntiCheatchecks.setItem(slot, g);
			} else {
				ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 14, 1, Color.Red + check.getIdentifier(), new String[0]);
				AntiCheatchecks.setItem(slot, c);
			}
			slot++;
		}
		for (int i = slot; i < 44; i++) {
			ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatchecks.setItem(i, c);
		}
		AntiCheatchecks.setItem(89, back);
		player.openInventory(AntiCheatchecks);
	}

	public void openAutoBans(Player player) {
		int slot = 0;
		for (Check check : AntiCheat.getChecks()) {
			if (AntiCheat.getConfig().getBoolean("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".bannable")) {
				ItemStack g = createGlass(Material.STAINED_GLASS_PANE, 5, 1, Color.Green + check.getIdentifier(), new String[0]);
				AntiCheatbannable.setItem(slot, g);
			} else {
				ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 14, 1, Color.Red + check.getIdentifier(), new String[0]);
				AntiCheatbannable.setItem(slot, c);
			}
			slot++;
		}
		for (int i = slot; i < 44; i++) {
			ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatbannable.setItem(i, c);
		}
		AntiCheatbannable.setItem(89, back);
		player.openInventory(AntiCheatbannable);
	}

	public void openTimer(Player player) {
		int slot = 0;
		for (Check check : AntiCheat.getChecks()) {
			if (AntiCheat.getConfig().getBoolean("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".banTimer")) {
				ItemStack g = createGlass(Material.STAINED_GLASS_PANE, 5, 1, Color.Green + check.getIdentifier(), new String[0]);
				AntiCheatTimer.setItem(slot, g);
			} else {
				ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 14, 1, Color.Red + check.getIdentifier(), new String[0]);
				AntiCheatTimer.setItem(slot, c);
			}
			slot++;
		}
		for (int i = slot; i < 44; i++) {
			ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatTimer.setItem(i, c);
		}
		AntiCheatTimer.setItem(89, back);
		player.openInventory(AntiCheatTimer);
	}

	public void openJDay(Player player) {
		int slot = 0;
		for (Check check : AntiCheat.getChecks()) {
			if (AntiCheat.getConfig().getBoolean("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".judgementDay")) {
				ItemStack g = createGlass(Material.STAINED_GLASS_PANE, 5, 1, Color.Green + check.getIdentifier(), new String[0]);
				AntiCheatJDay.setItem(slot, g);
			} else {
				ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 14, 1, Color.Red + check.getIdentifier(), new String[0]);
				AntiCheatJDay.setItem(slot, c);
			}
			slot++;
		}
		for (int i = slot; i < 44; i++) {
			ItemStack c = createGlass(Material.STAINED_GLASS_PANE, 15, 1, Color.Gray + "N/A");

			AntiCheatJDay.setItem(i, c);
		}
		AntiCheatJDay.setItem(89, back);
		player.openInventory(AntiCheatJDay);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getInventory().getName().equals(Color.Gold + "Home")) {
			Player player = (Player) e.getWhoClicked();

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
					.equals(Color.translate("&cTest Mode"))) {
				if (AntiCheat.getConfig().getBoolean("settings.testmode")) {
					AntiCheat.getConfig().set("settings.testmode", false);
					AntiCheat.saveConfig();
					ItemStack testmode = createItem(
							AntiCheat.getConfig().getBoolean("settings.testmode") ? Material.EMERALD_BLOCK
									: Material.REDSTONE_BLOCK,
									1, "&cTest Mode", new String[0]);
					AntiCheatmain.setItem(27, testmode);
				} else {
					AntiCheat.getConfig().set("settings.testmode", true);
					AntiCheat.saveConfig();
					ItemStack testmode = createItem(
							AntiCheat.getConfig().getBoolean("settings.testmode") ? Material.EMERALD_BLOCK
									: Material.REDSTONE_BLOCK,
									1, "&cTest Mode", new String[0]);
					AntiCheatmain.setItem(27, testmode);
				}
			}

			if (Color.strip(e.getCurrentItem().getItemMeta().getDisplayName()).equals("Checkered")) {
				AntiCheat.getConfig().set("settings.gui.checkered",
						AntiCheat.getConfig().getBoolean("settings.gui.checkered") ? false : true);
				AntiCheat.saveConfig();
				@SuppressWarnings("unused")
				ItemStack testmode = createItem(
						AntiCheat.getConfig().getBoolean("settings.testmode") ? Material.EMERALD_BLOCK
								: Material.REDSTONE_BLOCK,
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
				ItemMeta meta = e.getCurrentItem().getItemMeta();
				meta.setDisplayName(Color.Green + Color.Italics + "Success!");
				e.getCurrentItem().setItemMeta(meta);
				new BukkitRunnable() {
					@Override
					public void run() {
						ItemMeta meta = e.getCurrentItem().getItemMeta();
						meta.setDisplayName(Color.Red + "Reset Violations");
						e.getCurrentItem().setItemMeta(meta);
					}
				}.runTaskLater(AntiCheat, 40L);
			}

			if (e.getCurrentItem().getItemMeta().getDisplayName()
					.equals(Color.translate("&cReload"))) {
				ItemMeta meta = e.getCurrentItem().getItemMeta();
				meta.setDisplayName(Color.Red + Color.Italics + "Reloading...");
				e.getCurrentItem().setItemMeta(meta);
				AntiCheat.reloadConfig();
				meta.setDisplayName(Color.Green + Color.Italics + "Success!");
				e.getCurrentItem().setItemMeta(meta);
				new BukkitRunnable() {
					@Override
					public void run() {
						ItemMeta meta = e.getCurrentItem().getItemMeta();
						meta.setDisplayName(Color.Red + "Reload");
						e.getCurrentItem().setItemMeta(meta);
						openAntiCheatMain(player);
					}
				}.runTaskLater(AntiCheat, 40L);
			}
		} else if (e.getInventory().getName().equals(Color.Gold + "Checks: Bannable")) {
			Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().hasItemMeta()) {
				String check_name = e.getCurrentItem().getItemMeta().getDisplayName();
				for (Check check : AntiCheat.getChecks()) {
					if (check.getIdentifier().equals(Color.strip(check_name))) {
						if (AntiCheat.getConfig().getBoolean("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".bannable")) {
							AntiCheat.getConfig().set("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".bannable", false);
							AntiCheat.saveConfig();
							AntiCheat.reloadConfig();
							check.setBannable(false);
							openAutoBans(player);
							return;
						}
						AntiCheat.getConfig().set("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".bannable", true);
						AntiCheat.saveConfig();
						AntiCheat.reloadConfig();
						check.setBannable(true);
						openAutoBans(player);
						return;
					}
				}
				if (Color.strip(check_name).equals("Back")) {
					openAntiCheatMain(player);
				}
			}
		} else if (e.getInventory().getName().equals(Color.Gold + "Checks: Toggle")) {
			Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().hasItemMeta()) {
				String check_name = e.getCurrentItem().getItemMeta().getDisplayName();
				for (Check check : AntiCheat.getChecks()) {
					if (check.getIdentifier().equals(Color.strip(check_name))) {
						if (AntiCheat.getConfig().getBoolean("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".enabled")) {
							AntiCheat.getConfig().set("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".enabled", false);
							AntiCheat.saveConfig();
							AntiCheat.reloadConfig();
							check.setEnabled(false);
							openChecks(player);
							return;
						}
						AntiCheat.getConfig().set("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".enabled", true);
						AntiCheat.saveConfig();
						AntiCheat.reloadConfig();
						check.setEnabled(true);
						openChecks(player);
						return;
					}
				}
				if (Color.strip(check_name).equals("Back")) {
					openAntiCheatMain(player);
				}
			}
		} else if (e.getInventory().getName().equals(Color.Gold + "Checks: BanTimer")) {
			Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().hasItemMeta()) {
				String check_name = e.getCurrentItem().getItemMeta().getDisplayName();
				for (Check check : AntiCheat.getChecks()) {
					if (check.getIdentifier().equals(Color.strip(check_name))) {
						if (AntiCheat.getConfig().getBoolean("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".banTimer")) {
							AntiCheat.getConfig().set("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".banTimer", false);
							AntiCheat.saveConfig();
							AntiCheat.reloadConfig();
							check.setAutobanTimer(false);
							openTimer(player);
							return;
						}
						AntiCheat.getConfig().set("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".banTimer", true);
						AntiCheat.saveConfig();
						AntiCheat.reloadConfig();
						check.setAutobanTimer(true);
						openTimer(player);
						return;
					}
				}
				if (Color.strip(check_name).equals("Back")) {
					openAntiCheatMain(player);
				}
			}
		} else if (e.getInventory().getName().equals(Color.Gold + "Checks: JDay")) {
			Player player = (Player) e.getWhoClicked();

			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().hasItemMeta()) {
				String check_name = e.getCurrentItem().getItemMeta().getDisplayName();
				for (Check check : AntiCheat.getChecks()) {
					if (check.getIdentifier().equals(Color.strip(check_name))) {
						if (AntiCheat.getConfig().getBoolean("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".judgementDay")) {
							AntiCheat.getConfig().set("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".judgementDay", false);
							AntiCheat.saveConfig();
							AntiCheat.reloadConfig();
							check.setJudgementDay(false);
							openJDay(player);
							return;
						}
						AntiCheat.getConfig().set("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".judgementDay", true);
						AntiCheat.saveConfig();
						AntiCheat.reloadConfig();
						check.setJudgementDay(true);
						openJDay(player);
						return;
					}
				}
				if (Color.strip(check_name).equals("Back")) {
					openAntiCheatMain(player);
				}
			}
		} if (e.getInventory().getName().equals(Color.Gold + "Recent Bans")) {
			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
		} if (e.getInventory().getName().equals(Color.Gold + "Status")) {
			e.setCancelled(true);
			e.setResult(Event.Result.DENY);
		}
	}

	public static ItemStack createItem(Material material, int amount, String name, String... lore) {
		ItemStack thing = new ItemStack(material, amount);
		ItemMeta thingm = thing.getItemMeta();
		thingm.setDisplayName(Color.translate(name));
		thingm.setLore(Arrays.asList(lore));
		thing.setItemMeta(thingm);
		return thing;
	}

	public static ItemStack createGlass(Material material, int color, int amount, String name, String... lore) {
		ItemStack thing = new ItemStack(material, amount, (short) color);
		ItemMeta thingm = thing.getItemMeta();
		thingm.setDisplayName(Color.translate(name));
		thingm.setLore(Arrays.asList(lore));
		thing.setItemMeta(thingm);
		return thing;
	}

	public static ItemStack grayGlass() {
		ItemStack thing = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		ItemMeta thingm = thing.getItemMeta();
		thingm.setDisplayName(Color.translate("&b"));
		thing.setItemMeta(thingm);
		return thing;
	}

	public static ItemStack whiteGlass() {
		ItemStack thing = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 0);
		ItemMeta thingm = thing.getItemMeta();
		thingm.setDisplayName(Color.translate("&b"));
		thing.setItemMeta(thingm);
		return thing;
	}

	public String c(String str) {
		return str.replaceAll("&", "��");
	}
}