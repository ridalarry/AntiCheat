package me.rida.anticheat;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.clients.*;
import me.rida.anticheat.checks.combat.*;
import me.rida.anticheat.checks.experemintal.*;
import me.rida.anticheat.checks.movement.*;
import me.rida.anticheat.checks.other.*;
import me.rida.anticheat.checks.player.*;
import me.rida.anticheat.commands.AntiCheatCommand;
import me.rida.anticheat.commands.AlertsCommand;
import me.rida.anticheat.commands.AutobanCommand;
import me.rida.anticheat.commands.GetLogCommand;
import me.rida.anticheat.data.DataManager;
import me.rida.anticheat.events.JoinQuitEvents;
import me.rida.anticheat.events.MoveEvents;
import me.rida.anticheat.events.UtilityJoinQuitEvent;
import me.rida.anticheat.events.UtilityMoveEvent;
import me.rida.anticheat.other.LagCore;
import me.rida.anticheat.packets.PacketCoreA;
import me.rida.anticheat.packets.PacketCoreB;
import me.rida.anticheat.update.UpdateEvent;
import me.rida.anticheat.update.UpdateType;
import me.rida.anticheat.update.Updater;
import me.rida.anticheat.utils.Color;
import me.rida.anticheat.utils.ConfigFile;
import me.rida.anticheat.utils.Ping;
import me.rida.anticheat.utils.SetBackSystem;
import me.rida.anticheat.utils.TimerUtils;
import me.rida.anticheat.utils.TxtFile;
import me.rida.anticheat.utils.UtilActionMessage;
import me.rida.anticheat.utils.UtilNewVelocity;
import me.rida.anticheat.utils.UtilVelocity;
import me.rida.anticheat.utils.needscleanup.UtilsB;


public class AntiCheat extends JavaPlugin implements Listener {
    private DataManager dataManager;
    public static long MS_PluginLoad;
    public static String coreVersion;
	public static AntiCheat Instance;
	public String PREFIX;
	public Updater updater;
	public PacketCoreB packet;
	public LagCore lag;
	public List<Check> Checks;
	private static ConfigFile file;
	public Map<UUID, Map<Check, Integer>> Violations;
	public Map<UUID, Map<Check, Long>> ViolationReset;
	public List<Player> AlertsOn;
	public Map<Player, Map.Entry<Check, Long>> AutoBan;
	public Map<String, Check> NamesBanned;
	Random rand;
	private Check check;
	public TxtFile autobanMessages;
	public Map<UUID, Long> LastVelocity;
	public ArrayList<UUID> hasInvOpen = new ArrayList<UUID>();
	public Integer pingToCancel = getConfig().getInt("settings.latency.ping");
	public Integer tpsToCancel = getConfig().getInt("settings.latency.tps");

	public AntiCheat() {
		super();
		this.Checks = new ArrayList<Check>();
		this.Violations = new HashMap<UUID, Map<Check, Integer>>();
		this.ViolationReset = new HashMap<UUID, Map<Check, Long>>();
		this.AlertsOn = new ArrayList<Player>();
		this.AutoBan = new HashMap<Player, Map.Entry<Check, Long>>();
		this.NamesBanned = new HashMap<String, Check>();
		this.rand = new Random();
		this.LastVelocity = new HashMap<UUID, Long>();
	}

	public void addChecks() {
		this.Checks.add(new Scaffold(this));
		this.Checks.add(new AntiKBA(this));
		this.Checks.add(new AutoClickerA(this));
		this.Checks.add(new AutoClickerB(this));
		this.Checks.add(new CriticalsB(this));
		this.Checks.add(new CriticalsA(this));
		this.Checks.add(new FastBow(this));
		this.Checks.add(new HitBoxA(this));
		this.Checks.add(new KillAuraA(this));
		this.Checks.add(new KillAuraB(this));
		this.Checks.add(new KillAuraC(this));
		this.Checks.add(new KillAuraD(this));
		this.Checks.add(new KillAuraE(this));
		this.Checks.add(new KillAuraF(this));
		this.Checks.add(new KillAuraG(this));
		this.Checks.add(new AimAssistA(this));
		this.Checks.add(new ReachA(this));
		this.Checks.add(new ReachB(this));
		this.Checks.add(new ReachC(this));
		this.Checks.add(new ReachD(this));
		this.Checks.add(new Regen(this));
		this.Checks.add(new Twitch(this));
		this.Checks.add(new AscensionA(this));
		this.Checks.add(new AscensionB(this));
		this.Checks.add(new FastLadder(this));
		this.Checks.add(new FlyABCD(this));
		this.Checks.add(new FlyE(this));
		this.Checks.add(new Glide(this));
		this.Checks.add(new Gravity(this));
		this.Checks.add(new ImpossibleMovements(this));
		this.Checks.add(new Jesus(this));
		this.Checks.add(new NoFall(this));
		this.Checks.add(new NoSlowdown(this));
		this.Checks.add(new Phase(this));
		this.Checks.add(new Sneak(this));
		this.Checks.add(new SpeedAB(this));
		this.Checks.add(new SpeedD(this));
		this.Checks.add(new SpeedC(this));
		this.Checks.add(new Spider(this));
		this.Checks.add(new Step(this));
		this.Checks.add(new TimerA(this));
		this.Checks.add(new TimerB(this));
		this.Checks.add(new VClip(this));
		this.Checks.add(new CrashABC(this));
		this.Checks.add(new Exploit(this));
		this.Checks.add(new BlockInteract(this));
		this.Checks.add(new PacketsB(this));
		this.Checks.add(new VapeCracked(this));
		this.Checks.add(new GroundSpoof(this));
		this.Checks.add(new ImpossiblePitch(this));
		this.Checks.add(new LineOfSight(this));
		this.Checks.add(new PacketsA(this));
		this.Checks.add(new KillAuraH(this));
		this.Checks.add(new KillAuraI(this));
		this.Checks.add(new Change(this));
		this.Checks.add(new PME(this));
		this.Checks.add(new KillAuraK(this));
		this.Checks.add(new HitBoxB(this));
		this.Checks.add(new KillAuraJ(this));
		this.Checks.add(new AntiKBB(this));
		this.Checks.add(new AutoClickerC(this));
		this.Checks.add(new AimAssistB(this));
		this.Checks.add(new Spook(this));
		this.Checks.add(new AimAssistC(this));
	}

    @Override
	public void onEnable() {
        Instance = this;
        dataManager = new DataManager();
        registerListeners();
        loadChecks();
        new Ping(this);
        addDataPlayers();
        PacketCoreA.init();
        MS_PluginLoad = TimerUtils.nowlong();
        coreVersion = Bukkit.getServer().getClass().getPackage().getName().substring(23);
        dataManager = new DataManager();
        Bukkit.getPluginManager().registerEvents(new MoveEvents(), this);
        Bukkit.getPluginManager().registerEvents(new JoinQuitEvents(), this);
        saveChecks();
		AntiCheat.Instance = this;
		this.addChecks();
		this.packet = new PacketCoreB(this);
		this.lag = new LagCore(this);
		this.updater = new Updater(this);
		VapeCracked vapers = new VapeCracked(this);
		new AntiCheatAPI(this);
		this.getServer().getMessenger().registerIncomingPluginChannel((Plugin) this, "LOLIMAHCKER",
				(PluginMessageListener) vapers);
		for (final Check check : this.Checks) {
			if (check.isEnabled()) {
				this.RegisterListener((Listener) check);
			}
		}
		File file = new File(getDataFolder(), "config.yml");
		this.getCommand("alerts").setExecutor(new AlertsCommand(this));
		this.getCommand("autoban").setExecutor(new AutobanCommand(this));
		this.getCommand("anticheat").setExecutor(new AntiCheatCommand(this));
		this.getCommand("getLog").setExecutor(new GetLogCommand(this));
		this.RegisterListener(this);
		Bukkit.getServer().getPluginManager().registerEvents(new Latency(this), this);
		if (!file.exists()) {
			this.getConfig().addDefault("bans", 0);
			this.getConfig().addDefault("testmode", false);
			this.getConfig().addDefault("prefix", "&8[&c&lAntiCheat&8] ");
			this.getConfig().addDefault("alerts.primary", "&7");
			this.getConfig().addDefault("alerts.secondary", "&c");
			this.getConfig().addDefault("alerts.checkColor", "&b");
			this.getConfig().addDefault("bancmd", "ban %player% [AntiCheat] Unfair Advantage: %check%");
			this.getConfig().addDefault("broadcastmsg",
					"&c&lAntiCheat &7has detected &c%player% &7to be cheating and has been removed from the network.");
			this.getConfig().addDefault("settings.broadcastResetViolationsMsg", true);
			this.getConfig().addDefault("settings.violationResetTime", 60);
			this.getConfig().addDefault("settings.resetViolationsAutomatically", true);
			this.getConfig().addDefault("settings.gui.checkered", true);
			this.getConfig().addDefault("settings.latency.ping", 300);
			this.getConfig().addDefault("settings.latency.tps", 17);
			this.getConfig().addDefault("settings.sotwMode", false);
			for (Check check : Checks) {
				this.getConfig().addDefault("checks." + check.getIdentifier() + ".enabled", check.isEnabled());
				this.getConfig().addDefault("checks." + check.getIdentifier() + ".bannable", check.isBannable());
				this.getConfig().addDefault("checks." + check.getIdentifier() + ".banTimer", check.hasBanTimer());
				this.getConfig().addDefault("checks." + check.getIdentifier() + ".maxViolations",
						check.getMaxViolations());
			}
			this.getConfig().addDefault("checks.Phase.pearlFix", true);
			this.getConfig().options().copyDefaults(true);
			saveConfig();
		}
		for (Check check : Checks) {
			if (!getConfig().isConfigurationSection("checks." + check.getIdentifier())) {
				this.getConfig().set("checks." + check.getIdentifier() + ".enabled", check.isEnabled());
				this.getConfig().set("checks." + check.getIdentifier() + ".bannable", check.isBannable());
				this.getConfig().set("checks." + check.getIdentifier() + ".banTimer", check.hasBanTimer());
				this.getConfig().set("checks." + check.getIdentifier() + ".maxViolations", check.getMaxViolations());
				this.saveConfig();
			}
		}
		this.PREFIX = ChatColor.translateAlternateColorCodes('&', getConfig().getString("prefix"));
		// Reset Violations Counter
		new BukkitRunnable() {
			public void run() {
				getLogger().log(Level.INFO, "Reset Violations!");
				if (getConfig().getBoolean("resetViolationsAutomatically")) {
					if (getConfig().getBoolean("settings.broadcastResetViolationsMsg")) {
						for (Player online : Bukkit.getServer().getOnlinePlayers()) {
							if (online.hasPermission("anticheat.admin") && hasAlertsOn(online)) {
								online.sendMessage(PREFIX + ChatColor.translateAlternateColorCodes('&',
										"&7Reset violations for all players!"));
							}
						}
					}
					resetAllViolations();
				}
			}
		}.runTaskTimerAsynchronously(this, 0L,
				TimeUnit.SECONDS.toMillis(getConfig().getLong("settings.violationResetTime")));

	}

	public void resetDumps(Player player) {
		for (Check check : Checks) {
			if (check.hasDump(player)) {
				check.clearDump(player);
			}
		}
	}

	public void resetAllViolations() {
		this.Violations.clear();
		this.ViolationReset.clear();
	}

	public String resetData() {
		try {
			resetAllViolations();
			if (!AutoClickerB.Clicks.isEmpty())
				AutoClickerB.Clicks.clear();
			if (!AutoClickerB.LastMS.isEmpty())
				AutoClickerB.LastMS.clear();
			if (!AutoClickerB.ClickTicks.isEmpty())
				AutoClickerB.ClickTicks.clear();
			if (!CriticalsB.CritTicks.isEmpty())
				CriticalsB.CritTicks.clear();
			if (!KillAuraA.ClickTicks.isEmpty())
				KillAuraA.ClickTicks.clear();
			if (!KillAuraA.Clicks.isEmpty())
				KillAuraA.Clicks.clear();
			if (!KillAuraA.LastMS.isEmpty())
				KillAuraA.LastMS.clear();
			if (!KillAuraB.AuraTicks.isEmpty())
				KillAuraB.AuraTicks.clear();
			if (!KillAuraC.Differences.isEmpty())
				KillAuraC.Differences.clear();
			if (!KillAuraC.LastLocation.isEmpty())
				KillAuraC.LastLocation.clear();
			if (!KillAuraC.AimbotTicks.isEmpty())
				KillAuraC.AimbotTicks.clear();
			if (!KillAuraE.lastAttack.isEmpty())
				KillAuraE.lastAttack.clear();
			if (!KillAuraF.counts.isEmpty())
				KillAuraF.counts.clear();
			if (!Regen.FastHealTicks.isEmpty())
				Regen.FastHealTicks.clear();
			if (!Regen.LastHeal.isEmpty())
				Regen.LastHeal.clear();
			if (!AscensionA.AscensionTicks.isEmpty())
				AscensionA.AscensionTicks.clear();
			if (!FlyE.flyTicksA.isEmpty())
				FlyE.flyTicksA.clear();
			if (!Glide.flyTicks.isEmpty())
				Glide.flyTicks.clear();
			if (!NoFall.FallDistance.isEmpty())
				NoFall.FallDistance.clear();
			if (!NoFall.NoFallTicks.isEmpty())
				NoFall.NoFallTicks.clear();
			if (!NoSlowdown.speedTicks.isEmpty())
				NoSlowdown.speedTicks.clear();
			if (!SpeedD.speedTicks.isEmpty())
				SpeedD.speedTicks.clear();
			if (!SpeedD.tooFastTicks.isEmpty())
				SpeedD.tooFastTicks.clear();
			if (!SpeedD.lastHit.isEmpty())
				SpeedD.lastHit.isEmpty();
			if (!PacketsB.lastPacket.isEmpty())
				PacketsB.lastPacket.clear();
			if (!PacketsB.packetTicks.isEmpty())
				PacketsB.packetTicks.clear();
			if (!Sneak.sneakTicks.isEmpty())
				Sneak.sneakTicks.clear();
			if (!HitBoxA.count.isEmpty())
				HitBoxA.count.clear();
			if (!HitBoxA.lastHit.isEmpty())
				HitBoxA.lastHit.clear();
			if (!HitBoxA.yawDif.isEmpty())
				HitBoxA.yawDif.clear();
			if (!FastBow.count.isEmpty())
				FastBow.count.clear();
		} catch (Exception e) {
			return ChatColor.translateAlternateColorCodes('&', PREFIX + Color.Red + "Unknown error occured!");
		}
		return ChatColor.translateAlternateColorCodes('&', PREFIX + Color.Green + "Successfully reset data!");
	}

	public Integer getPingCancel() {
		return pingToCancel;
	}

	public Integer getTPSCancel() {
		return tpsToCancel;
	}

	public List<Check> getChecks() {
		return new ArrayList<Check>(this.Checks);
	}

	public boolean isCheckingUpdates() {
		return this.getConfig().getBoolean("settings.checkUpdates");
	}

	public String getVersion() {
		return this.getDescription().getVersion();
	}

	public boolean isSotwMode() {
		return getConfig().getBoolean("settings.sotwMode");
	}

	public boolean hasNewVersion() {
		if (!this.getVersion().equalsIgnoreCase(getPasteVersion())) {
			return true;
		}
		return false;
	}

	public String getPasteVersion() {
		try {
			URL url = new URL(UtilsB.decrypt("aHR0cDovL3Bhc3RlYmluLmNvbS9yYXcvQU4yWEtqTlM="));
			URLConnection connection = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			line = in.readLine();
			if (line != null) {
				return line;
			}
		} catch (Exception e) {
			e.printStackTrace();
			getLogger().log(Level.SEVERE, UtilsB.decrypt("RXJyb3IhIENvdWxkIG5vdCBjaGVjayBmb3IgYSBuZXcgdmVyc2lvbiE="));
		}
		return "Error";
	}

	public Map<String, Check> getNamesBanned() {
		return new HashMap<String, Check>(this.NamesBanned);
	}

	public String getCraftBukkitVersion() {
		return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
	}

	public List<Player> getAutobanQueue() {
		return new ArrayList<Player>(this.AutoBan.keySet());
	}

	public void createLog(Player player, Check checkBanned) {
		TxtFile logFile = new TxtFile(this, File.separator + "logs", player.getName());
		Map<Check, Integer> Checks = getViolations(player);
		logFile.addLine("------------------- Player was banned for: " + checkBanned.getName() + " -------------------");
		logFile.addLine("Set off checks:");
		for (Check check : Checks.keySet()) {
			Integer Violations = Checks.get(check);
			logFile.addLine("- " + check.getName() + " (" + Violations + " VL)");
		}
		logFile.addLine(" ");
		logFile.addLine("Dump-Log for all checks set off:");
		for (Check check : Checks.keySet()) {
			logFile.addLine(" ");
			logFile.addLine(check.getName() + ":");
			if (check.getDump(player) != null) {
				for (String line : check.getDump(player)) {
					logFile.addLine(line);
				}
			} else {
				logFile.addLine("Checks had no dump logs.!");
			}
			logFile.addLine(" ");
		}
		logFile.write();
	}

	public void removeFromAutobanQueue(Player player) {
		this.AutoBan.remove(player);
	}

	public void removeViolations(Player player) {
		this.Violations.remove(player.getUniqueId());
	}

	public boolean hasAlertsOn(Player player) {
		return this.AlertsOn.contains(player);
	}

	public void toggleAlerts(Player player) {
		if (this.hasAlertsOn(player)) {
			this.AlertsOn.remove(player);
		} else {
			this.AlertsOn.add(player);
		}
	}

	public LagCore getLag() {
		return this.lag;
	}

	@EventHandler
	public void Join(PlayerJoinEvent e) {
		if (!e.getPlayer().hasPermission("anticheat.staff")) {
			return;
		}
		this.AlertsOn.add(e.getPlayer());
	}

	@EventHandler
	public void autobanupdate(UpdateEvent event) {
		if (!event.getType().equals(UpdateType.SEC)) {
			return;
		}
		Map<Player, Map.Entry<Check, Long>> AutoBan = new HashMap<Player, Map.Entry<Check, Long>>(this.AutoBan);
		for (Player player : AutoBan.keySet()) {
			if (player == null || !player.isOnline()) {
				this.AutoBan.remove(player);
			} else {
				Long time = AutoBan.get(player).getValue();
				if (System.currentTimeMillis() < time) {
					continue;
				}
				this.autobanOver(player);
			}
		}
		final Map<UUID, Map<Check, Long>> ViolationResets = new HashMap<UUID, Map<Check, Long>>(this.ViolationReset);
		for (UUID uid : ViolationResets.keySet()) {
			if (!this.Violations.containsKey(uid)) {
				continue;
			}
			Map<Check, Long> Checks = new HashMap<Check, Long>(ViolationResets.get(uid));
			for (Check check : Checks.keySet()) {
				Long time2 = Checks.get(check);
				if (System.currentTimeMillis() >= time2) {
					this.ViolationReset.get(uid).remove(check);
					this.Violations.get(uid).remove(check);
				}
			}
		}
	}

	public Integer getViolations(Player player, Check check) {
		if (this.Violations.containsKey(player.getUniqueId())) {
			return this.Violations.get(player.getUniqueId()).get(check);
		}
		return 0;
	}

	public Map<Check, Integer> getViolations(Player player) {
		if (this.Violations.containsKey(player.getUniqueId())) {
			return new HashMap<Check, Integer>(this.Violations.get(player.getUniqueId()));
		}
		return null;
	}

	private void wqminoiwn() {
		Bukkit.getPluginManager().disablePlugin(this);
	}

	public void addViolation(Player player, Check check) {
		Map<Check, Integer> map = new HashMap<Check, Integer>();
		if (this.Violations.containsKey(player.getUniqueId())) {
			map = this.Violations.get(player.getUniqueId());
		}
		if (!map.containsKey(check)) {
			map.put(check, 1);
		} else {
			map.put(check, map.get(check) + 1);
		}
		this.Violations.put(player.getUniqueId(), map);
	}

	public void removeViolations(Player player, Check check) {
		if (this.Violations.containsKey(player.getUniqueId())) {
			this.Violations.get(player.getUniqueId()).remove(check);
		}
	}

	public void setViolationResetTime(Player player, Check check, long time) {
		Map<Check, Long> map = new HashMap<Check, Long>();
		if (this.ViolationReset.containsKey(player.getUniqueId())) {
			map = this.ViolationReset.get(player.getUniqueId());
		}
		map.put(check, time);
		this.ViolationReset.put(player.getUniqueId(), map);
	}

	public void autobanOver(Player player) {
		final Map<Player, Map.Entry<Check, Long>> AutoBan = new HashMap<Player, Map.Entry<Check, Long>>(this.AutoBan);
		if (AutoBan.containsKey(player)) {
			this.banPlayer(player, AutoBan.get(player).getKey());
			this.AutoBan.remove(player);
		}
	}

	public void autoban(Check check, Player player) {
		if (this.lag.getTPS() < 17.0) {
			return;
		}
		if (check.hasBanTimer()) {
			if (this.AutoBan.containsKey(player)) {
				return;
			}
			this.AutoBan.put(player,
					new AbstractMap.SimpleEntry<Check, Long>(check, System.currentTimeMillis() + 10000L));
			final UtilActionMessage msg = new UtilActionMessage();
			msg.addText(PREFIX);
			msg.addText(ChatColor.translateAlternateColorCodes('&',
					getConfig().getString("alerts.secondary") + player.getName()))
					.addHoverText(Color.Gray + "(Click to teleport to " + Color.Red + player.getName() + Color.Gray + ")")
					.setClickEvent(UtilActionMessage.ClickableType.RunCommand, "/tp " + player.getName());
			msg.addText(ChatColor.translateAlternateColorCodes('&',
					getConfig().getString("alerts.primary") + " set off " + getConfig().getString("alerts.secondary")
							+ check.getName() + getConfig().getString("alerts.primary") + " and will "
							+ getConfig().getString("alerts.primary") + "be " + getConfig().getString("alerts.primary")
							+ "banned" + getConfig().getString("alerts.primary") + " if you don't take action. "
							+ Color.DGray + Color.Bold + "["));
			msg.addText(ChatColor.translateAlternateColorCodes('&',
					getConfig().getString("alerts.secondary") + Color.Bold + "ban"))
					.addHoverText(Color.Gray + "Autoban " + Color.Green + player.getName())
					.setClickEvent(UtilActionMessage.ClickableType.RunCommand, "/autoban ban " + player.getName());
			msg.addText(ChatColor.translateAlternateColorCodes('&', getConfig().getString("alerts.primary")) + " or ");
			msg.addText(Color.Green + Color.Bold + "cancel").addHoverText(Color.Gray + "Click to Cancel")
					.setClickEvent(UtilActionMessage.ClickableType.RunCommand, "/autoban cancel " + player.getName());
			msg.addText(Color.DGray + Color.Bold + "]");
			ArrayList<Player> players;
			for (int length = (players = UtilsB.getOnlinePlayers()).size(), i = 0; i < length; ++i) {
				Player playerplayer = players.get(i);
				if (playerplayer.hasPermission("anticheat.staff")) {
					msg.sendToPlayer(playerplayer);
				}
			}
		} else {
			this.banPlayer(player, check);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void Velocity(PlayerVelocityEvent event) {
		this.LastVelocity.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
	}

	public void banPlayer(Player player, Check check) {
		if (!getConfig().getBoolean("testmode")) {
			this.createLog(player, check);
		}
		if (NamesBanned.containsKey(player.getName()) && !getConfig().getBoolean("testmode")) {
			return;
		}
		this.NamesBanned.put(player.getName(), check);
		this.removeViolations(player, check);
		new BukkitRunnable() {
			@Override
			public void run() {
				if (NamesBanned.containsKey(player.getName()) && getConfig().getBoolean("testmode")) {
					return;
				}
				if (Latency.getLag(player) < 250) {
					if (getConfig().getBoolean("testmode")) {
						player.sendMessage(
								PREFIX + Color.Gray + "You would be banned right now for: " + Color.Red + check.getName());
					} else {
						if (!getConfig().getString("broadcastmsg").equalsIgnoreCase("")) {
							Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
									getConfig().getString("broadcastmsg").replaceAll("%player%", player.getName())));
						}
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("bancmd")
								.replaceAll("%player%", player.getName()).replaceAll("%check%", check.getName()));
					}
				}
				NamesBanned.put(player.getName(), check);
			}
		}.runTaskLater(this, 10L);
		if (Violations.containsKey(player))
			this.Violations.remove(player);
		this.getConfig().set("bans", (Object) (this.getConfig().getInt("bans") + 1));
		this.saveConfig();
	}

	public void alert(String message) {
		for (Player playerplayer : this.AlertsOn) {
			playerplayer.sendMessage(String.valueOf(PREFIX) + message);
		}
	}

	public void logCheat(Check check, Player player, String hoverabletext, String... identefier) {
		String a = "";
		if (identefier != null) {
			for (String b : identefier) {
				a = a + " " + b;
			}
		}
		this.addViolation(player, check);
		this.setViolationResetTime(player, check, System.currentTimeMillis() + check.getViolationResetTime());
		Integer violations = this.getViolations(player, check);
		if (violations >= check.getViolationsToNotify()) {
			UtilActionMessage msg = new UtilActionMessage();
			msg.addText(PREFIX);
			msg.addText(ChatColor.translateAlternateColorCodes('&', getConfig().getString("alerts.secondary"))
					+ player.getName())
					.addHoverText(ChatColor.translateAlternateColorCodes('&', getConfig().getString("alerts.primary"))
							+ "(Click to teleport to "
							+ ChatColor.translateAlternateColorCodes('&', getConfig().getString("alerts.secondary"))
							+ player.getName()
							+ ChatColor.translateAlternateColorCodes('&', getConfig().getString("alerts.primary"))
							+ ")")
					.setClickEvent(UtilActionMessage.ClickableType.RunCommand, "/tp " + player.getName());
			msg.addText(ChatColor.translateAlternateColorCodes('&', getConfig().getString("alerts.primary"))
					+ " failed " + (check.isJudgmentDay() ? "JD check " : ""));
			UtilActionMessage.AMText CheckText = msg
					.addText(ChatColor.translateAlternateColorCodes('&', getConfig().getString("alerts.checkColor"))
							+ check.getName());
			if (hoverabletext != null) {
				CheckText.addHoverText(hoverabletext);
			}
			msg.addText(ChatColor.translateAlternateColorCodes('&', getConfig().getString("alerts.checkColor")) + a
					+ ChatColor.translateAlternateColorCodes('&', getConfig().getString("alerts.primary")) + " ");
			msg.addText(ChatColor.translateAlternateColorCodes('&', getConfig().getString("alerts.primary")) + "["
					+ ChatColor.translateAlternateColorCodes('&', getConfig().getString("alerts.secondary"))
					+ violations + ChatColor.translateAlternateColorCodes('&', getConfig().getString("alerts.primary"))
					+ " VL]");
			if (violations % check.getViolationsToNotify() == 0) {
				if (getConfig().getBoolean("testmode") == true) {
					msg.sendToPlayer(player);
				} else {
					for (Player playerplayer : this.AlertsOn) {
						if (check.isJudgmentDay() && !playerplayer.hasPermission("anticheat.admin")) {
							continue;
						}
						msg.sendToPlayer(playerplayer);
					}
				}
			}
			if (check.isJudgmentDay()) {
				return;
			}
			if (violations > check.getMaxViolations() && check.isBannable()) {
				this.autoban(check, player);
			}
		}
	}

	public void RegisterListener(Listener listener) {
		this.getServer().getPluginManager().registerEvents(listener, (Plugin) this);
	}

	public Map<UUID, Long> getLastVelocity() {
		return this.LastVelocity;
	}

	@EventHandler
	public void Kick(PlayerKickEvent event) {
		if (event.getReason().equals("Flying is not enabled on this server")) {
			this.alert(String.valueOf(Color.Gray) + event.getPlayer().getName() + " was kicked for flying");
		}
	}
	public static AntiCheat getInstance() {
        return Instance;
    }


    public DataManager getDataManager() {
        return dataManager;
    }

    public void onDisable() {
        saveChecks();
    }

   
    private void loadChecks() {
        for(Check check : getDataManager().getChecks()) {
            if(getConfig().get("checks." + check.getName() + ".enabled") != null) {
                check.setEnabled(getConfig().getBoolean("checks." + check.getName() + ".enabled"));
            } else {
                getConfig().set("checks." + check.getName() + ".enabled", check.isEnabled());
                saveConfig();
            }
        }
    }

    private void saveChecks() {
        for(Check check : getDataManager().getChecks()) {
            getConfig().set("checks." + check.getName() + ".enabled", check.isEnabled());
            saveConfig();
        }
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new UtilityMoveEvent(), this);
        getServer().getPluginManager().registerEvents(new UtilityJoinQuitEvent(), this);
        getServer().getPluginManager().registerEvents(new SetBackSystem(), this);
        getServer().getPluginManager().registerEvents(new UtilVelocity(), this);
        getServer().getPluginManager().registerEvents(new UtilNewVelocity(), this);
    }


    private void addDataPlayers() {
        for (Player playerLoop : Bukkit.getOnlinePlayers()) {
            getInstance().getDataManager().addPlayerData(playerLoop);
        }
    }
}