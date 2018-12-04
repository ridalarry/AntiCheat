package me.rida.anticheat;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.FuzzyReflection;
import com.comphenix.protocol.reflect.accessors.Accessors;
import com.comphenix.protocol.reflect.accessors.MethodAccessor;
import com.comphenix.protocol.reflect.fuzzy.FuzzyMethodContract;
import com.comphenix.protocol.utility.ByteBufferInputStream;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.comphenix.protocol.wrappers.nbt.NbtList;
import com.google.common.base.Charsets;

import io.netty.buffer.ByteBuf;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.commands.AlertsCommand;
import me.rida.anticheat.commands.AntiCheatCommand;
import me.rida.anticheat.commands.AutobanCommand;
import me.rida.anticheat.commands.GetLogCommand;
import me.rida.anticheat.data.DataManager;
import me.rida.anticheat.events.JoinQuitEvent;
import me.rida.anticheat.events.MoveEvent;
import me.rida.anticheat.other.LagCore;
import me.rida.anticheat.other.Latency;
import me.rida.anticheat.other.Ping;
import me.rida.anticheat.packets.PacketCore;
import me.rida.anticheat.pluginlogger.PluginLoggerHelper;
import me.rida.anticheat.update.UpdateEvent;
import me.rida.anticheat.update.UpdateType;
import me.rida.anticheat.update.Updater;
import me.rida.anticheat.utils.ActionMessageUtil;
import me.rida.anticheat.utils.BlockUtil;
import me.rida.anticheat.utils.Color;
import me.rida.anticheat.utils.NewVelocityUtil;
import me.rida.anticheat.utils.ReflectionUtil;
import me.rida.anticheat.utils.TimerUtil;
import me.rida.anticheat.utils.TxtFile;
import me.rida.anticheat.utils.VelocityUtil;

public class AntiCheat extends JavaPlugin implements Listener {
	public List<Material> excludedBlocks;
	public Set<UUID> hasAlertsOn;
	public int maxMove = 10;
	public ExecutorService service;
	public static ArrayList<Player> getOnlinePlayers() {
		ArrayList<Player> list = new ArrayList<>();
		for (Player player : Bukkit.getOnlinePlayers()) {
			list.add(player);
		}
		return list;
	}
	public static Map<Player, Long> PACKET_USAGE = new ConcurrentHashMap<>();
	public static Set<String> PACKET_NAMES = new HashSet<>(Arrays.asList("MC|BSign", "MC|BEdit", "REGISTER"));
	private Logger logger = null;
	private DataManager dataManager;
	public static long MS_PluginLoad;
	public static String coreVersion;
	public static AntiCheat Instance;
	public static Plugin plugin = Instance;
	public String PREFIX;
	public Updater updater;
	public PacketCore packet;
	public LagCore lag;
	public List<Check> Checks;
	public Map<UUID, Map<Check, Integer>> Violations;
	public Map<UUID, Map<Check, Long>> ViolationReset;
	public List<Player> AlertsOn;
	public Map<Player, Map.Entry<Check, Long>> AutoBan;
	public Map<String, Check> NamesBanned;
	Random rand;
	public TxtFile autobanMessages;
	public Map<UUID, Long> LastVelocity;
	public ArrayList<UUID> hasInvOpen = new ArrayList<>();
	public Integer pingToCancel = getConfig().getInt("settings.latency.ping");
	public Integer tpsToCancel = getConfig().getInt("settings.latency.tps");

	public AntiCheat() {
		super();
		this.Checks = new ArrayList<>();
		this.Violations = new HashMap<>();
		this.ViolationReset = new HashMap<>();
		this.AlertsOn = new ArrayList<>();
		this.AutoBan = new HashMap<>();
		this.NamesBanned = new HashMap<>();
		this.rand = new Random();
		this.LastVelocity = new HashMap<>();
	}

	public void addChecks() {
		this.Checks.add(new me.rida.anticheat.checks.other.ChatA(this));
		this.Checks.add(new me.rida.anticheat.checks.movement.PhaseB(this));
		this.Checks.add(new me.rida.anticheat.checks.combat.AimAssistA(this));
		this.Checks.add(new me.rida.anticheat.checks.combat.AimAssistB(this));
		this.Checks.add(new me.rida.anticheat.checks.combat.AntiKBA(this));
		this.Checks.add(new me.rida.anticheat.checks.combat.AutoClickerB(this));
		this.Checks.add(new me.rida.anticheat.checks.other.BlockInteractA(this));
		this.Checks.add(new me.rida.anticheat.checks.other.BlockInteractC(this));
		this.Checks.add(new me.rida.anticheat.checks.other.BlockInteractD(this));
		this.Checks.add(new me.rida.anticheat.checks.other.BlockInteractB(this));
		this.Checks.add(new me.rida.anticheat.checks.other.ChangeA(this));
		this.Checks.add(new me.rida.anticheat.checks.other.CrashA(this));
		this.Checks.add(new me.rida.anticheat.checks.combat.CriticalsA(this));
		this.Checks.add(new me.rida.anticheat.checks.combat.CriticalsB(this));
		this.Checks.add(new me.rida.anticheat.checks.other.ExploitA(this));
		this.Checks.add(new me.rida.anticheat.checks.combat.FastBowA(this));
		this.Checks.add(new me.rida.anticheat.checks.movement.FastLadderA(this));
		this.Checks.add(new me.rida.anticheat.checks.movement.FlyA(this));
		this.Checks.add(new me.rida.anticheat.checks.movement.FlyB(this));
		this.Checks.add(new me.rida.anticheat.checks.movement.FlyC(this));
		this.Checks.add(new me.rida.anticheat.checks.other.InvMoveA(this));
		this.Checks.add(new me.rida.anticheat.checks.other.InvMoveB(this));
		this.Checks.add(new me.rida.anticheat.checks.other.InvMoveC(this));
		this.Checks.add(new me.rida.anticheat.checks.other.InvMoveD(this));
		this.Checks.add(new me.rida.anticheat.checks.movement.GlideA(this));
		this.Checks.add(new me.rida.anticheat.checks.player.GroundSpoofA(this));
		this.Checks.add(new me.rida.anticheat.checks.combat.HitBoxA(this));
		this.Checks.add(new me.rida.anticheat.checks.combat.HitBoxB(this));
		this.Checks.add(new me.rida.anticheat.checks.movement.ImpossibleMovementsA(this));
		this.Checks.add(new me.rida.anticheat.checks.player.ImpossiblePitchA(this));
		this.Checks.add(new me.rida.anticheat.checks.movement.JesusA(this));
		this.Checks.add(new me.rida.anticheat.checks.combat.AutoClickerA(this));
		this.Checks.add(new me.rida.anticheat.checks.combat.KillAuraD(this));
		this.Checks.add(new me.rida.anticheat.checks.combat.KillAuraA(this));
		this.Checks.add(new me.rida.anticheat.checks.combat.KillAuraB(this));
		this.Checks.add(new me.rida.anticheat.checks.combat.KillAuraC(this));
		this.Checks.add(new me.rida.anticheat.checks.combat.KillAuraE(this));
		this.Checks.add(new me.rida.anticheat.checks.movement.NoFallA(this));
		this.Checks.add(new me.rida.anticheat.checks.movement.NoSlowdownA(this));
		this.Checks.add(new me.rida.anticheat.checks.movement.NoSlowdownB(this));
		this.Checks.add(new me.rida.anticheat.checks.player.PacketsA(this));
		this.Checks.add(new me.rida.anticheat.checks.movement.PhaseA(this));
		this.Checks.add(new me.rida.anticheat.checks.client.PMEA(this));
		this.Checks.add(new me.rida.anticheat.checks.combat.ReachA(this));
		this.Checks.add(new me.rida.anticheat.checks.combat.ReachB(this));
		this.Checks.add(new me.rida.anticheat.checks.combat.ReachC(this));
		this.Checks.add(new me.rida.anticheat.checks.combat.RegenA(this));
		this.Checks.add(new me.rida.anticheat.checks.movement.SneakA(this));
		this.Checks.add(new me.rida.anticheat.checks.movement.SneakB(this));
		this.Checks.add(new me.rida.anticheat.checks.movement.SpeedA(this));
		this.Checks.add(new me.rida.anticheat.checks.movement.SpeedB(this));
		this.Checks.add(new me.rida.anticheat.checks.movement.SpeedC(this));
		this.Checks.add(new me.rida.anticheat.checks.movement.SpiderA(this));
		this.Checks.add(new me.rida.anticheat.checks.client.SpookA(this));
		this.Checks.add(new me.rida.anticheat.checks.movement.StepA(this));
		this.Checks.add(new me.rida.anticheat.checks.other.TimerA(this));
		this.Checks.add(new me.rida.anticheat.checks.other.TimerB(this));
		this.Checks.add(new me.rida.anticheat.checks.combat.TwitchA(this));
		this.Checks.add(new me.rida.anticheat.checks.client.VapeA(this));
		this.Checks.add(new me.rida.anticheat.checks.movement.VClipA(this));
		this.Checks.add(new me.rida.anticheat.checks.combat.AimAssistC(this));
		this.Checks.add(new me.rida.anticheat.checks.combat.AntiKBB(this));
		this.Checks.add(new me.rida.anticheat.checks.combat.AntiKBC(this));
		this.Checks.add(new me.rida.anticheat.checks.combat.AutoClickerC(this));
		this.Checks.add(new me.rida.anticheat.checks.combat.AutoClickerD(this));
		this.Checks.add(new me.rida.anticheat.checks.combat.ReachD(this));
		this.Checks.add(new me.rida.anticheat.checks.combat.KillAuraF(this));
		this.Checks.add(new me.rida.anticheat.checks.combat.KillAuraG(this));
		this.Checks.add(new me.rida.anticheat.checks.combat.KillAuraH(this));
		this.Checks.add(new me.rida.anticheat.checks.combat.KillAuraI(this));
		this.Checks.add(new me.rida.anticheat.checks.combat.KillAuraJ(this));
		this.Checks.add(new me.rida.anticheat.checks.movement.AscensionA(this));
		this.Checks.add(new me.rida.anticheat.checks.movement.AscensionB(this));
		this.Checks.add(new me.rida.anticheat.checks.movement.GravityA(this));
		this.Checks.add(new me.rida.anticheat.checks.other.BlockInteractE(this));
	}

	@Override
	public void onEnable() {
		excludedBlocks = new ArrayList<>();
		service = Executors.newSingleThreadExecutor();

		getConfig().getStringList("excluded_blocks").forEach(string -> {
			try {
				Material material = Material.getMaterial(string);

				excludedBlocks.add(material);
			} catch (NullPointerException e) {
				throw new NullPointerException("The material '" + string + "' in the config does not exist!");
			}
		});
		new ReflectionUtil();
		new BlockUtil();
		new Ping(this);
		Instance = this;
		dataManager = new DataManager();
		registerListeners();
		loadChecks();
		addDataPlayers();
		PacketCore.init();
		MS_PluginLoad = TimerUtil.nowlong();
		coreVersion = Bukkit.getServer().getClass().getPackage().getName().substring(23);
		dataManager = new DataManager();
		saveChecks();
		AntiCheat.Instance = this;
		this.addChecks();
		this.packet = new PacketCore(this);
		this.lag = new LagCore(this);
		this.updater = new Updater(this);
		new AntiCheatAPI(this);
		me.rida.anticheat.checks.client.VapeA vapeA = new me.rida.anticheat.checks.client.VapeA(this);
		this.getServer().getMessenger().registerIncomingPluginChannel((Plugin)this, "LOLIMAHCKER", (PluginMessageListener)vapeA);
		this.getServer().getPluginManager().registerEvents((Listener)vapeA, (Plugin)this);
		me.rida.anticheat.checks.client.VapeA vapers = new me.rida.anticheat.checks.client.VapeA(this);
		this.getServer().getMessenger().registerIncomingPluginChannel(this, "LOLIMAHCKER", vapers);
		for (final Check check : this.Checks) {
			if (check.isEnabled()) {
				this.RegisterListener(check);
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
			this.getConfig().addDefault("settings.EnableCustomLog", true);
			this.getConfig().addDefault("settings.CustomLogFormat", "[%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$s]: %5$s%6$s%n");
			this.getConfig().addDefault("settings.bans", 0);
			this.getConfig().addDefault("settings.testmode", false);
			this.getConfig().addDefault("settings.prefix", "&8[&c&lAntiCheat&8] ");
			this.getConfig().addDefault("alerts.primary", "&7");
			this.getConfig().addDefault("alerts.secondary", "&c");
			this.getConfig().addDefault("alerts.checkColor", "&b");
			this.getConfig().addDefault("settings.bancmd", "ban %player% [AntiCheat] Unfair Advantage!");
			this.getConfig().addDefault("settings.broadcastmsg",
					"&c&lAntiCheat &7has detected &c%player% &7to be cheating and has been removed from the network.");
			this.getConfig().addDefault("settings.broadcastResetViolationsMsg", true);
			this.getConfig().addDefault("settings.violationResetTime", 60);
			this.getConfig().addDefault("settings.resetViolationsAutomatically", true);
			this.getConfig().addDefault("settings.latency.ping", 300);
			this.getConfig().addDefault("settings.latency.tps", 17);
			for (Check check : Checks) {
				this.getConfig().addDefault("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".enabled", check.isEnabled());
				this.getConfig().addDefault("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".bannable", check.isBannable());
				this.getConfig().addDefault("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".banTimer", check.hasBanTimer());
				this.getConfig().addDefault("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".maxViolations",
						check.getMaxViolations());
				this.getConfig().addDefault("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".violationsToNotify", check.getViolationsToNotify());
				this.getConfig().addDefault("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".violationResetTime", check.getViolationResetTime());
				this.getConfig().addDefault("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".judgementDay", check.isJudgmentDay());
			}
			this.getConfig().options().copyDefaults(true);
			saveConfig();
		}
		for (Check check : Checks) {
			if (!getConfig().isConfigurationSection("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier())) {
				this.getConfig().set("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".enabled", check.isEnabled());
				this.getConfig().set("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".bannable", check.isBannable());
				this.getConfig().set("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".banTimer", check.hasBanTimer());
				this.getConfig().set("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".maxViolations", check.getMaxViolations());
				this.getConfig().set("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".violationsToNotify", check.getViolationsToNotify());
				this.getConfig().set("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".violationResetTime", check.getViolationResetTime());
				this.getConfig().set("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".judgementDay", check.isJudgmentDay());
				this.saveConfig();
			}
		}
		this.PREFIX = Color.translate(getConfig().getString("settings.prefix"));
		new BukkitRunnable() {
			@Override
			public void run() {
				getLogger().log(Level.INFO, "Reset Violations!");
				if (getConfig().getBoolean("resetViolationsAutomatically")) {
					if (getConfig().getBoolean("settings.broadcastResetViolationsMsg")) {
						for (Player online : Bukkit.getServer().getOnlinePlayers()) {
							if (online.hasPermission("anticheat.admin") && hasAlertsOn(online)) {
								online.sendMessage(PREFIX + Color.translate("&7Reset violations for all players!"));
							}
						}
					}
					resetAllViolations();
				}
			}
		}.runTaskTimerAsynchronously(this, 0L,
				TimeUnit.SECONDS.toMillis(getConfig().getLong("settings.violationResetTime")));

		saveDefaultConfig();
		if (getConfig().getBoolean("settings.EnableCustomLog")) {
			try {
				logger = PluginLoggerHelper.openLogger(new File(getDataFolder(), "exploits.log"), getConfig().getString("settings.CustomLogFormat"));
			} catch (Throwable ex) {
				getLogger().log(Level.SEVERE, ex.getMessage());
			}
		}


		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, PacketType.Play.Client.CUSTOM_PAYLOAD) {
			@Override
			public void onPacketReceiving(PacketEvent event) {
				checkPacket(event);
			}
		});

		Bukkit.getScheduler().runTaskTimer(this, () -> {
			for (Iterator<Map.Entry<Player, Long>> iterator = PACKET_USAGE.entrySet().iterator(); iterator.hasNext(); ) {
				Player player = iterator.next().getKey();
				if (!player.isOnline() || !player.isValid())
					iterator.remove();
			}
		}, 20L, 20L);
		getLogger().info("Reloading... will kick all online players to avoid crash.");
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.kickPlayer(Color.translate(PREFIX + "&7Reloading..."));
		}
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
			if (!me.rida.anticheat.checks.combat.AntiKBA.lastVelocity.isEmpty())
				me.rida.anticheat.checks.combat.AntiKBA.lastVelocity.clear();
			if (!me.rida.anticheat.checks.combat.AntiKBA.awaitingVelocity.isEmpty())
				me.rida.anticheat.checks.combat.AntiKBA.awaitingVelocity.clear();
			if (!me.rida.anticheat.checks.combat.AntiKBA.totalMoved.isEmpty())
				me.rida.anticheat.checks.combat.AntiKBA.totalMoved.clear();
			if (!me.rida.anticheat.checks.combat.AutoClickerB.Clicks.isEmpty())
				me.rida.anticheat.checks.combat.AutoClickerB.Clicks.clear();
			if (!me.rida.anticheat.checks.combat.AutoClickerB.LastMS.isEmpty())
				me.rida.anticheat.checks.combat.AutoClickerB.LastMS.clear();
			if (!me.rida.anticheat.checks.combat.AutoClickerB.ClickTicks.isEmpty())
				me.rida.anticheat.checks.combat.AutoClickerB.ClickTicks.clear();
			if (!me.rida.anticheat.checks.combat.CriticalsB.CritTicks.isEmpty())
				me.rida.anticheat.checks.combat.CriticalsB.CritTicks.clear();
			if (!me.rida.anticheat.checks.combat.AutoClickerA.ClickTicks.isEmpty())
				me.rida.anticheat.checks.combat.AutoClickerA.ClickTicks.clear();
			if (!me.rida.anticheat.checks.combat.AutoClickerA.Clicks.isEmpty())
				me.rida.anticheat.checks.combat.AutoClickerA.Clicks.clear();
			if (!me.rida.anticheat.checks.combat.AutoClickerA.LastMS.isEmpty())
				me.rida.anticheat.checks.combat.AutoClickerA.LastMS.clear();
			if (!me.rida.anticheat.checks.combat.KillAuraD.packetTicks.isEmpty())
				me.rida.anticheat.checks.combat.KillAuraD.packetTicks.clear();
			if (!me.rida.anticheat.checks.combat.KillAuraA.counts.isEmpty())
				me.rida.anticheat.checks.combat.KillAuraA.counts.clear();
			if (!me.rida.anticheat.checks.combat.ReachB.count.isEmpty())
				me.rida.anticheat.checks.combat.ReachB.count.clear();
			if (!me.rida.anticheat.checks.combat.ReachB.offsets.isEmpty())
				me.rida.anticheat.checks.combat.ReachB.offsets.clear();
			if (!me.rida.anticheat.checks.combat.ReachC.toBan.isEmpty())
				me.rida.anticheat.checks.combat.ReachC.toBan.clear();
			if (!me.rida.anticheat.checks.combat.RegenA.FastHealTicks.isEmpty())
				me.rida.anticheat.checks.combat.RegenA.FastHealTicks.clear();
			if (!me.rida.anticheat.checks.combat.RegenA.LastHeal.isEmpty())
				me.rida.anticheat.checks.combat.RegenA.LastHeal.clear();
			if (!me.rida.anticheat.checks.movement.FlyB.flyTicks.isEmpty())
				me.rida.anticheat.checks.movement.FlyB.flyTicks.clear();
			if (!me.rida.anticheat.checks.movement.GlideA.flyTicks.isEmpty())
				me.rida.anticheat.checks.movement.GlideA.flyTicks.clear();
			if (!me.rida.anticheat.checks.movement.NoFallA.FallDistance.isEmpty())
				me.rida.anticheat.checks.movement.NoFallA.FallDistance.clear();
			if (!me.rida.anticheat.checks.movement.NoFallA.NoFallTicks.isEmpty())
				me.rida.anticheat.checks.movement.NoFallA.NoFallTicks.clear();
			if (!me.rida.anticheat.checks.movement.NoSlowdownA.speedTicks.isEmpty())
				me.rida.anticheat.checks.movement.NoSlowdownA.speedTicks.clear();
			if (!me.rida.anticheat.checks.movement.SpeedB.speedTicks.isEmpty())
				me.rida.anticheat.checks.movement.SpeedB.speedTicks.clear();
			if (!me.rida.anticheat.checks.movement.SpeedB.tooFastTicks.isEmpty())
				me.rida.anticheat.checks.movement.SpeedB.tooFastTicks.clear();
			if (!me.rida.anticheat.checks.movement.SpeedB.lastHit.isEmpty())
				me.rida.anticheat.checks.movement.SpeedB.lastHit.isEmpty();
			if (!me.rida.anticheat.checks.movement.SpeedC.speedTicks.isEmpty())
				me.rida.anticheat.checks.movement.SpeedC.speedTicks.clear();
			if (!me.rida.anticheat.checks.movement.SpeedC.tooFastTicks.isEmpty())
				me.rida.anticheat.checks.movement.SpeedC.tooFastTicks.clear();
			if (!me.rida.anticheat.checks.movement.SpeedC.lastHit.isEmpty())
				me.rida.anticheat.checks.movement.SpeedC.lastHit.isEmpty();
			if (!me.rida.anticheat.checks.movement.SpeedC.velocity.isEmpty())
				me.rida.anticheat.checks.movement.SpeedC.velocity.isEmpty();
			if (!me.rida.anticheat.checks.movement.SpiderA.AscensionTicks.isEmpty())
				me.rida.anticheat.checks.movement.SpiderA.AscensionTicks.clear();
			if (!me.rida.anticheat.checks.other.TimerA.packets.isEmpty())
				me.rida.anticheat.checks.other.TimerA.packets.clear();
			if (!me.rida.anticheat.checks.other.TimerA.verbose.isEmpty())
				me.rida.anticheat.checks.other.TimerA.verbose.clear();
			if (!me.rida.anticheat.checks.other.TimerA.lastPacket.isEmpty())
				me.rida.anticheat.checks.other.TimerA.lastPacket.clear();
			if (!me.rida.anticheat.checks.other.TimerA.toCancel.isEmpty())
				me.rida.anticheat.checks.other.TimerA.toCancel.clear();
			if (!me.rida.anticheat.checks.other.TimerB.timerTicks.isEmpty())
				me.rida.anticheat.checks.other.TimerB.timerTicks.clear();
			if (!me.rida.anticheat.checks.movement.VClipA.teleported.isEmpty())
				me.rida.anticheat.checks.movement.VClipA.teleported.clear();
			if (!me.rida.anticheat.checks.movement.VClipA.lastLocation.isEmpty())
				me.rida.anticheat.checks.movement.VClipA.lastLocation.clear();
			if (!me.rida.anticheat.checks.other.CrashA.crashTicks.isEmpty())
				me.rida.anticheat.checks.other.CrashA.crashTicks.clear();
			if (!me.rida.anticheat.checks.other.CrashA.crash2Ticks.isEmpty())
				me.rida.anticheat.checks.other.CrashA.crash2Ticks.clear();
			if (!me.rida.anticheat.checks.other.CrashA.crash3Ticks.isEmpty())
				me.rida.anticheat.checks.other.CrashA.crash3Ticks.clear();
			if (!me.rida.anticheat.checks.player.PacketsA.lastPacket.isEmpty())
				me.rida.anticheat.checks.player.PacketsA.lastPacket.clear();
			if (!me.rida.anticheat.checks.player.PacketsA.packetTicks.isEmpty())
				me.rida.anticheat.checks.player.PacketsA.packetTicks.clear();
			if (!me.rida.anticheat.checks.movement.SneakA.sneakTicks.isEmpty())
				me.rida.anticheat.checks.movement.SneakA.sneakTicks.clear();
			if (!me.rida.anticheat.checks.combat.HitBoxA.count.isEmpty())
				me.rida.anticheat.checks.combat.HitBoxA.count.clear();
			if (!me.rida.anticheat.checks.combat.HitBoxA.lastHit.isEmpty())
				me.rida.anticheat.checks.combat.HitBoxA.lastHit.clear();
			if (!me.rida.anticheat.checks.combat.HitBoxA.yawDif.isEmpty())
				me.rida.anticheat.checks.combat.HitBoxA.yawDif.clear();
			if (!me.rida.anticheat.checks.combat.FastBowA.count.isEmpty())
				me.rida.anticheat.checks.combat.FastBowA.count.clear();
		} catch (Exception e) {
			return Color.translate(PREFIX + Color.Red + "Unknown error occured!");
		}
		return Color.translate(PREFIX + Color.Green + "Successfully reset data!");
	}

	public Integer getPingCancel() {
		return pingToCancel;
	}

	public Integer getTPSCancel() {
		return tpsToCancel;
	}

	public List<Check> getChecks() {
		return new ArrayList<>(this.Checks);
	}

	public boolean isCheckingUpdates() {
		return this.getConfig().getBoolean("settings.checkUpdates");
	}

	public String getVersion() {
		return this.getDescription().getVersion();
	}


	public Map<String, Check> getNamesBanned() {
		return new HashMap<>(this.NamesBanned);
	}

	public String getCraftBukkitVersion() {
		return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
	}

	public List<Player> getAutobanQueue() {
		return new ArrayList<>(this.AutoBan.keySet());
	}

	public void createLog(Player player, Check checkBanned) {
		TxtFile logFile = new TxtFile(this, File.separator + "logs", player.getName());
		Map<Check, Integer> Checks = getViolations(player);
		logFile.addLine("---- Player was banned for: " + checkBanned.getName() + " ----");
		logFile.addLine("Set off checks:");
		for (Check check : Checks.keySet()) {
			Integer Violations = Checks.get(check);
			logFile.addLine("- " + check.getType() + "." + check.getIdentifier() + " x" + Violations);
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
		Map<Player, Map.Entry<Check, Long>> AutoBan = new HashMap<>(this.AutoBan);
		for (Player player : AutoBan.keySet()) {
			if (player == null || !player.isOnline()) {
				this.AutoBan.remove(player);
			} else {
				Long time = AutoBan.get(player).getValue();
				if (System.currentTimeMillis() < time) {
					continue;
				}
				this.banPlayer(player);
			}
		}
		final Map<UUID, Map<Check, Long>> ViolationResets = new HashMap<>(this.ViolationReset);
		for (UUID uid : ViolationResets.keySet()) {
			if (!this.Violations.containsKey(uid)) {
				continue;
			}
			Map<Check, Long> Checks = new HashMap<>(ViolationResets.get(uid));
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
			return new HashMap<>(this.Violations.get(player.getUniqueId()));
		}
		return null;
	}

	public void addViolation(Player player, Check check) {
		Map<Check, Integer> map = new HashMap<>();
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
		Map<Check, Long> map = new HashMap<>();
		if (this.ViolationReset.containsKey(player.getUniqueId())) {
			map = this.ViolationReset.get(player.getUniqueId());
		}
		map.put(check, time);
		this.ViolationReset.put(player.getUniqueId(), map);
	}

	public void autoban(Check check, Player player) {
		if (this.lag.getTPS() < 17.0) {
			return;
		}
		if (check.hasBanTimer()) {
			if (this.AutoBan.containsKey(player)) {
				return;
			}
			this.AutoBan.put(player, new AbstractMap.SimpleEntry<>(check, System.currentTimeMillis() + 10000L));
			System.out.println(Color.strip(PREFIX) + player.getName() + " will be banned in 15s for " + check.getType() + "." + check.getIdentifier() + ".");
			final ActionMessageUtil msg = new ActionMessageUtil();
			msg.addText(PREFIX);
			msg.addText(Color.translate(
					getConfig().getString("alerts.secondary") + player.getName()))
			.addHoverText(Color.Gray + "(Click to teleport to " + Color.Red + player.getName() + Color.Gray + ")")
			.setClickEvent(ActionMessageUtil.ClickableType.RunCommand, "/tp " + player.getName());
			msg.addText(Color.translate(
					getConfig().getString("alerts.primary") + " set off " + getConfig().getString("alerts.secondary")
					+ check.getType() + "." + check.getIdentifier() + getConfig().getString("alerts.primary") + " and will "
					+ getConfig().getString("alerts.primary") + "be " + getConfig().getString("alerts.primary")
					+ "banned" + getConfig().getString("alerts.primary") + " if you don't take action. "
					+ Color.DGray + Color.Bold + "["));
			msg.addText(Color.translate(
					getConfig().getString("alerts.secondary") + Color.Bold + "ban"))
			.addHoverText(Color.Gray + "Autoban " + Color.Green + player.getName())
			.setClickEvent(ActionMessageUtil.ClickableType.RunCommand, "/autoban ban " + player.getName());
			msg.addText(Color.translate(getConfig().getString("alerts.primary")) + " or ");
			msg.addText(Color.Green + Color.Bold + "cancel").addHoverText(Color.Gray + "Click to Cancel")
			.setClickEvent(ActionMessageUtil.ClickableType.RunCommand, "/autoban cancel " + player.getName());
			msg.addText(Color.DGray + Color.Bold + "]");
			ArrayList<Player> players;
			this.banPlayer(player, check);
			for (int length = (players = getOnlinePlayers()).size(), i = 0; i < length; ++i) {
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

	@SuppressWarnings("unlikely-arg-type")
	public void banPlayer(Player player, Check check) {
		if (!getConfig().getBoolean("settings.testmode")) {
			this.createLog(player, check);
		}
		if (NamesBanned.containsKey(player.getName()) && !getConfig().getBoolean("settings.testmode")) {
			return;
		}
		this.NamesBanned.put(player.getName(), check);
		this.removeViolations(player, check);
		new BukkitRunnable() {
			@Override
			public void run() {
				if (NamesBanned.containsKey(player.getName()) && getConfig().getBoolean("settings.testmode")) {
					return;
				}
				if (Latency.getLag(player) < 250) {
					if (getConfig().getBoolean("settings.testmode")) {
						player.sendMessage(
								PREFIX + Color.Gray + "You would be banned right now for: " + Color.Red + check.getName());
					} else {
						if (!getConfig().getString("settings.broadcastmsg").equalsIgnoreCase("")) {
							Bukkit.broadcastMessage(Color.translate(
									getConfig().getString("settings.broadcastmsg").replaceAll("%player%", player.getName())));
						}
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("settings.bancmd")
								.replaceAll("%player%", player.getName()).replaceAll("%check%", check.getName()));
					}
				}
				NamesBanned.put(player.getName(), check);
			}
		}.runTaskLater(this, 10L);
		if (Violations.containsKey(player))
			this.Violations.remove(player);
		this.getConfig().set("settings.bans", this.getConfig().getInt("settings.bans") + 1);
		this.saveConfig();
	}

	@SuppressWarnings("unlikely-arg-type")
	public void banPlayer(Player player) {
		new BukkitRunnable() {
			@Override
			public void run() {
				Bukkit.broadcastMessage(Color.translate(
						getConfig().getString("settings.broadcastmsg").replaceAll("%player%", player.getName())));
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("settings.bancmd")
						.replaceAll("%player%", player.getName()).replaceAll("%check%", ""));
			}


		}.runTaskLater(this, 10L);
		if (Violations.containsKey(player))
			this.Violations.remove(player);
		this.getConfig().set("settings.bans", this.getConfig().getInt("settings.bans") + 1);
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
		
		int x = (check.getViolationsToNotify() !=0) ? violations / check.getViolationsToNotify() : 1;
		System.out.println(Color.strip(PREFIX) + player.getName() + " failed " + (check.isJudgmentDay() ? "JD check " : "") + check.getName() + a + " x" + x);
		ActionMessageUtil msg = new ActionMessageUtil();
		msg.addText(PREFIX);
		msg.addText(Color.translate(getConfig().getString("alerts.secondary"))
				+ player.getName())
		.addHoverText(Color.translate(getConfig().getString("alerts.primary"))
				+ "(Click to teleport to "
				+ Color.translate(getConfig().getString("alerts.secondary"))
				+ player.getName()
				+ Color.translate(getConfig().getString("alerts.primary"))
				+ ")")
		.setClickEvent(ActionMessageUtil.ClickableType.RunCommand, "/tp " + player.getName());
		msg.addText(Color.translate(getConfig().getString("alerts.primary"))
				+ " failed " + (check.isJudgmentDay() ? "JD check " : ""));
		ActionMessageUtil.AMText CheckText = msg
				.addText(Color.translate(getConfig().getString("alerts.checkColor"))
						+ check.getName());
		if (hoverabletext != null) {
			CheckText.addHoverText(hoverabletext);
		}
		msg.addText(Color.translate(getConfig().getString("alerts.checkColor")) + a
				+ Color.translate(getConfig().getString("alerts.primary")) + " ");
		msg.addText(Color.translate(getConfig().getString("alerts.secondary"))
				+ "x" + x);
		if (violations % check.getViolationsToNotify() == 0) {
			for (Player playerplayer : this.AlertsOn) {
				if (check.isJudgmentDay() && !playerplayer.hasPermission("anticheat.staff")) {
					continue;
				}
				msg.sendToPlayer(playerplayer);
			}
		}
		if (check.isJudgmentDay()) {
			return;
		}
		if (violations > check.getMaxViolations() && check.isBannable()) {
			this.autoban(check, player);
		}
	}

	public void RegisterListener(Listener listener) {
		this.getServer().getPluginManager().registerEvents(listener, this);
	}

	public Map<UUID, Long> getLastVelocity() {
		return this.LastVelocity;
	}

	@EventHandler
	public void Kick(PlayerKickEvent event) {
		if (event.getReason().contains("Too many packets")) {
			this.alert(String.valueOf(Color.Gray) + event.getPlayer().getName() + " was kicked for sending too many packets!");
		}
		if (event.getReason().equals("Flying is not enabled on this server")) {
			this.alert(String.valueOf(Color.Gray) + event.getPlayer().getName() + " was kicked for flying!");
		}
	}
	public static AntiCheat getInstance() {
		return Instance;
	}

	public DataManager getDataManager() {
		return dataManager;
	}

	@Override
	public void onDisable() {
		saveChecks();
		ProtocolLibrary.getProtocolManager().removePacketListeners(this);
		if (logger != null){
			logger.log(Level.INFO, "Plugin disabled");
			PluginLoggerHelper.closeLogger(logger);
		}
		Bukkit.shutdown();
	}

	private void loadChecks() {
		for(Check check : getDataManager().getChecks()) {
			if(getConfig().get("checks." + check.getType() + "." + check.getName() + ".enabled") != null) {
				check.setEnabled(getConfig().getBoolean("checks." + check.getType() + "." + check.getName() + ".enabled"));
			} else {
				getConfig().set("checks." + check.getType() + "." + check.getName() + ".enabled", check.isEnabled());
				saveConfig();
			}
		}
	}

	private void saveChecks() {
		for(Check check : getDataManager().getChecks()) {
			getConfig().set("checks." + check.getType() + "." + check.getName() + ".enabled", check.isEnabled());
			saveConfig();
		}
	}

	private void registerListeners() {
		getServer().getPluginManager().registerEvents(new MoveEvent(), this);
		getServer().getPluginManager().registerEvents(new JoinQuitEvent(), this);
		getServer().getPluginManager().registerEvents(new VelocityUtil(), this);
		getServer().getPluginManager().registerEvents(new NewVelocityUtil(), this);
	}

	private void addDataPlayers() {
		for (Player playerLoop : Bukkit.getOnlinePlayers()) {
			getInstance().getDataManager().addPlayerData(playerLoop);
		}
	}
	public String dispatchCommand;
	private void checkPacket(PacketEvent event) {

		dispatchCommand = getConfig().getString("settings.bancmd");
		Player player = event.getPlayer();
		if (player == null) {
			String name = event.getPacket().getStrings().readSafely(0);
			getLogger().log(Level.SEVERE, "packet ''{0}'' without player ", name);
			if (logger != null) logger.log(Level.SEVERE, "packet ''{0}'' without player ", name);
			event.setCancelled(true);
			return;
		}
		long lastPacket = PACKET_USAGE.getOrDefault(player, -1L);

		if (lastPacket == -2L) {
			event.setCancelled(true);
			return;
		}

		String packetName = event.getPacket().getStrings().readSafely(0);
		if (packetName == null || !PACKET_NAMES.contains(packetName))
			return;

		try {
			if ("REGISTER".equals(packetName)) {
				checkChannels(event);
			} else {
				if (elapsed(lastPacket, 100L)) {
					PACKET_USAGE.put(player, System.currentTimeMillis());
				} else {
					throw new ExploitException("Packet flood");
				}

				checkNbtTags(event);
			}
		} catch (ExploitException ex) {
			PACKET_USAGE.put(player, -2L);

			Bukkit.getScheduler().runTask(this, () -> {
				player.kickPlayer("You failed to use an exploit that would crash the server!");

				if (dispatchCommand != null)
					getServer().dispatchCommand(Bukkit.getConsoleSender(),
							dispatchCommand.replace("%player%", player.getName()));
			});

			getLogger().warning(player.getName() + " tried to exploit CustomPayload: " + ex.getMessage());
			if (logger != null) logger.log(Level.WARNING, "{0} tried exploit CustomPayload: {1}{2}", new Object[]{player.getName(), ex.getMessage(), ex.itemstackToLogString(" ")});
			event.setCancelled(true);
		} catch (Throwable ex) {
			getLogger().severe(String.format("Failed to check packet '%s' for %s: %s", packetName, player.getName(), ex.getMessage()));
			if (logger != null) logger.log(Level.SEVERE, String.format("Failed to check packet '%s': ", packetName, player.getName()), ex);
			event.setCancelled(true);
		}
	}

	private void checkNbtTags(PacketEvent event) throws ExploitException {
		PacketContainer container = event.getPacket();
		ByteBuf buffer = container.getSpecificModifier(ByteBuf.class).read(0).copy();

		try {
			ItemStack itemStack = null;
			try {
				itemStack = deserializeItemStack(buffer);
			} catch (Throwable ex) {
				throw new ExploitException("Unable to deserialize ItemStack", ex);
			}
			if (itemStack == null)
				throw new ExploitException("Unable to deserialize ItemStack");

			NbtCompound root = (NbtCompound) NbtFactory.fromItemTag(itemStack);
			if (root == null)
				throw new ExploitException("No NBT tag?!", itemStack);

			if (!root.containsKey("pages"))
				throw new ExploitException("No 'pages' NBT compound was found", itemStack);

			NbtList<String> pages = root.getList("pages");
			if (pages.size() > 50)
				throw new ExploitException("Too much pages", itemStack);

			if (pages.size() > 0 && "CustomPayloadFixer".equalsIgnoreCase(pages.getValue(0)))
				throw new ExploitException("Testing exploit", itemStack);

		} finally {
			buffer.release();
		}
	}

	private void checkChannels(PacketEvent event) throws ExploitException {
		int channelsSize = event.getPlayer().getListeningPluginChannels().size();

		PacketContainer container = event.getPacket();
		ByteBuf buffer = container.getSpecificModifier(ByteBuf.class).read(0).copy();

		try {
			for (int i = 0; i < buffer.toString(Charsets.UTF_8).split("\0").length; i++)
				if (++channelsSize > 124)
					throw new ExploitException("Too much channels");
		} finally {
			buffer.release();
		}
	}

	private boolean elapsed(long from, long required) {
		return from == -1L || System.currentTimeMillis() - from > required;
	}

	private static MethodAccessor READ_ITEM_METHOD;
	public ItemStack deserializeItemStack(ByteBuf buf) throws IOException {
		Validate.notNull(buf, "input cannot be null!");
		Object nmsItem = null;
		if (MinecraftReflection.isUsingNetty()) {
			if (READ_ITEM_METHOD == null) {
				READ_ITEM_METHOD = Accessors.getMethodAccessor(FuzzyReflection.fromClass(MinecraftReflection.getPacketDataSerializerClass(), true).getMethodByParameters("readItemStack", MinecraftReflection.getItemStackClass(), new Class[0]));
			}

			Object serializer = MinecraftReflection.getPacketDataSerializer(buf);
			nmsItem = READ_ITEM_METHOD.invoke(serializer);
		} else {
			if (READ_ITEM_METHOD == null) {
				READ_ITEM_METHOD = Accessors.getMethodAccessor(FuzzyReflection.fromClass(MinecraftReflection.getPacketClass()).getMethod(FuzzyMethodContract.newBuilder().parameterCount(1).parameterDerivedOf(DataInput.class).returnDerivedOf(MinecraftReflection.getItemStackClass()).build()));
			}

			DataInputStream input = new DataInputStream(new ByteBufferInputStream(buf.nioBuffer()));
			nmsItem = READ_ITEM_METHOD.invoke((Object)null, new Object[]{input});
		}

		return nmsItem != null ? MinecraftReflection.getBukkitItemStack(nmsItem) : null;
	}

	public String formatArrayToString(List<String> array) {
		StringBuilder toReturn = new StringBuilder();
		for (int i = 0; i < array.size(); i++) {
			String string = array.get(i);

			toReturn.append(string).append(array.size() - i > 1 ? ", " : "");
		}
		return toReturn.toString();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static HashMap<String, Integer> timerLeft = new HashMap(); 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static HashMap<String, BukkitRunnable> cooldownTask = new HashMap();

	public void startTimerPhaseCheck(Player player)
	{
		timerLeft.put(player.getName(), 3);
		cooldownTask.put(player.getName(), new BukkitRunnable(){
			@Override
			public void run()
			{
				timerLeft.put(player.getName(), Integer.valueOf(((Integer)timerLeft.get(player.getName())).intValue() - 1));
				if (((Integer)timerLeft.get(player.getName())).intValue() == 0){
					timerLeft.remove(player.getName());
					cooldownTask.remove(player.getName());
					Bukkit.getServer().getScheduler().cancelTask(getTaskId());
					cancel();
					return;
				}
			}
		});
		((BukkitRunnable)cooldownTask.get(player.getName())).runTaskTimer(this, 0L, 20L);
	}

	public static boolean isInPhaseTimer(Player player) {
		if(!timerLeft.isEmpty() && timerLeft.containsKey(player.getName().toString())) {
			return true; // They are in the timer
		}

		return false; // They aren't in the timer
	}

	public void startTimer(Player player)
	{
		MoveEvent.ticksLeft.put(player.getName(), MoveEvent.defaultWait);
		MoveEvent.cooldownTask.put(player.getName(), new BukkitRunnable(){
			@Override
			public void run()
			{
				MoveEvent.ticksLeft.put(player.getName(), Integer.valueOf(((Integer)MoveEvent.ticksLeft.get(player.getName())).intValue() - 1));
				if (((Integer)MoveEvent.ticksLeft.get(player.getName())).intValue() == 0){
					MoveEvent.ticksLeft.remove(player.getName());
					MoveEvent.cooldownTask.remove(player.getName());
					Bukkit.getServer().getScheduler().cancelTask(getTaskId());
					cancel();
					return;
				}
			}
		});
		((BukkitRunnable)MoveEvent.cooldownTask.get(player.getName())).runTaskTimer(this, 0L, 1L);
	}
}