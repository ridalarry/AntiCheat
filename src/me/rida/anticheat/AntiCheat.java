package me.rida.anticheat;

import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.reflect.accessors.Accessors;
import com.comphenix.protocol.reflect.accessors.MethodAccessor;
import com.comphenix.protocol.reflect.fuzzy.FuzzyMethodContract;
import com.comphenix.protocol.reflect.FuzzyReflection;
import com.comphenix.protocol.utility.ByteBufferInputStream;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.comphenix.protocol.wrappers.nbt.NbtList;
import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;
import me.rida.anticheat.checks.Check;
import me.rida.anticheat.checks.client.*;
import me.rida.anticheat.checks.combat.*;
import me.rida.anticheat.checks.movement.*;
import me.rida.anticheat.checks.other.*;
import me.rida.anticheat.checks.player.*;
import me.rida.anticheat.commands.*;
import me.rida.anticheat.data.DataManager;
import me.rida.anticheat.events.*;
import me.rida.anticheat.other.*;
import me.rida.anticheat.packets.PacketCore;
import me.rida.anticheat.pluginlogger.*;
import me.rida.anticheat.update.*;
import me.rida.anticheat.utils.*;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class AntiCheat extends JavaPlugin implements Listener {

    public static ArrayList<Player> getOnlinePlayers() {
        ArrayList<Player> list = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            list.add(player);
        }
        return list;
    }
	public static final Map<Player, Long> PACKET_USAGE = new ConcurrentHashMap<>();
	public static final Set<String> PACKET_NAMES = new HashSet<>(Arrays.asList("MC|BSign", "MC|BEdit", "REGISTER"));

    private Logger logger = null;
    private DataManager dataManager;
    public static long MS_PluginLoad;
    public static String coreVersion;
	public static AntiCheat Instance;
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
		this.Checks.add(new AimAssistA(this));
		this.Checks.add(new ChatA(this));
		this.Checks.add(new AimAssistB(this));
		this.Checks.add(new AimAssistC(this));
		this.Checks.add(new AntiKBA(this));
		this.Checks.add(new AntiKBB(this));
		this.Checks.add(new AntiKBC(this));
		this.Checks.add(new AscensionA(this));
		this.Checks.add(new AscensionB(this));
		this.Checks.add(new AutoClickerA(this));
		this.Checks.add(new AutoClickerB(this));
		this.Checks.add(new AutoClickerC(this));
		this.Checks.add(new BlockInteractA(this));
		this.Checks.add(new BlockInteractB(this));
		this.Checks.add(new BlockInteractC(this));
		this.Checks.add(new BlockInteractD(this));
		this.Checks.add(new BlockInteractE(this));
		this.Checks.add(new ChangeA(this));
		this.Checks.add(new CrashA(this));
		this.Checks.add(new CriticalsA(this));
		this.Checks.add(new CriticalsB(this));
		this.Checks.add(new ExploitA(this));
		this.Checks.add(new FastBowA(this));
		this.Checks.add(new FastLadderA(this));
		this.Checks.add(new FlyA(this));
		this.Checks.add(new FlyB(this));
		this.Checks.add(new InvMoveA(this));
		this.Checks.add(new InvMoveB(this));
		this.Checks.add(new InvMoveC(this));
		this.Checks.add(new GlideA(this));
		this.Checks.add(new GravityA(this));
		this.Checks.add(new GroundSpoofA(this));
		this.Checks.add(new HitBoxA(this));
		this.Checks.add(new HitBoxB(this));
		this.Checks.add(new ImpossibleMovementsA(this));
		this.Checks.add(new ImpossiblePitchA(this));
		this.Checks.add(new JesusA(this));
		this.Checks.add(new KillAuraA(this));
		this.Checks.add(new KillAuraB(this));
		this.Checks.add(new KillAuraC(this));
		this.Checks.add(new KillAuraD(this));
		this.Checks.add(new SkinBlinkerA(this));
		this.Checks.add(new KillAuraE(this));
		this.Checks.add(new KillAuraF(this));
		this.Checks.add(new KillAuraG(this));
		this.Checks.add(new KillAuraH(this));
		this.Checks.add(new KillAuraI(this));
		this.Checks.add(new KillAuraJ(this));
		this.Checks.add(new KillAuraK(this));
		this.Checks.add(new NoFallA(this));
		this.Checks.add(new NoSlowdownA(this));
		this.Checks.add(new PacketsA(this));
		this.Checks.add(new PhaseA(this));
		this.Checks.add(new PMEA(this));
		this.Checks.add(new ReachA(this));
		this.Checks.add(new ReachB(this));
		this.Checks.add(new ReachC(this));
		this.Checks.add(new ReachD(this));
		this.Checks.add(new RegenA(this));
		this.Checks.add(new SneakA(this));
		this.Checks.add(new SneakB(this));
		this.Checks.add(new SpeedA(this));
		this.Checks.add(new SpeedB(this));
		this.Checks.add(new SpeedC(this));
		this.Checks.add(new SpiderA(this));
		this.Checks.add(new SpookA(this));
		this.Checks.add(new StepA(this));
		this.Checks.add(new TimerA(this));
		this.Checks.add(new TimerB(this));
		this.Checks.add(new TwitchA(this));
		this.Checks.add(new VapeA(this));
		this.Checks.add(new VClipA(this));
	}

    @Override
	public void onEnable() {
        Instance = this;
        dataManager = new DataManager();
        registerListeners();
        loadChecks();
        new Ping(this);
        addDataPlayers();
        PacketCore.init();
        MS_PluginLoad = TimerUtils.nowlong();
        coreVersion = Bukkit.getServer().getClass().getPackage().getName().substring(23);
        dataManager = new DataManager();
        saveChecks();
		AntiCheat.Instance = this;
		this.addChecks();
		this.packet = new PacketCore(this);
		this.lag = new LagCore(this);
		this.updater = new Updater(this);
		VapeA vapers = new VapeA(this);
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
			this.getConfig().addDefault("EnableCustomLog", true);
			this.getConfig().addDefault("CustomLogFormat", "[%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$s]: %5$s%6$s%n");
			this.getConfig().addDefault("bans", 0);
			this.getConfig().addDefault("testmode", false);
			this.getConfig().addDefault("prefix", "&8[&c&lAntiCheat&8] ");
			this.getConfig().addDefault("alerts.primary", "&7");
			this.getConfig().addDefault("alerts.secondary", "&c");
			this.getConfig().addDefault("alerts.checkColor", "&b");
			this.getConfig().addDefault("bancmd", "ban %player% [AntiCheat] Unfair Advantage!");
			this.getConfig().addDefault("broadcastmsg",
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
			}
			this.getConfig().addDefault("checks.Movement.Phase.PhaseA.pearlFix", true);
			this.getConfig().options().copyDefaults(true);
			saveConfig();
		}
		for (Check check : Checks) {
			if (!getConfig().isConfigurationSection("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier())) {
				this.getConfig().set("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".enabled", check.isEnabled());
				this.getConfig().set("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".bannable", check.isBannable());
				this.getConfig().set("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".banTimer", check.hasBanTimer());
				this.getConfig().set("checks." + check.getType() + "." + check.getName() + "." + check.getIdentifier() + ".maxViolations", check.getMaxViolations());
				this.saveConfig();
			}
		}
		this.PREFIX = Color.translate(getConfig().getString("prefix"));
		new BukkitRunnable() {
			public void run() {
				getLogger().log(Level.INFO, "Reset Violations!");
				if (getConfig().getBoolean("resetViolationsAutomatically")) {
					if (getConfig().getBoolean("settings.broadcastResetViolationsMsg")) {
						for (Player online : Bukkit.getServer().getOnlinePlayers()) {
							if (online.hasPermission("anticheat.admin") && hasAlertsOn(online)) {
								online.sendMessage(PREFIX + Color.translate(
										"&7Reset violations for all players!"));
							}
						}
					}
					resetAllViolations();
				}
			}
		}.runTaskTimerAsynchronously(this, 0L,
				TimeUnit.SECONDS.toMillis(getConfig().getLong("settings.violationResetTime")));

		saveDefaultConfig();
        if (getConfig().getBoolean("EnableCustomLog")) {
            try {
                logger = PluginLoggerHelper.openLogger(new File(getDataFolder(), "exploits.log"), getConfig().getString("CustomLogFormat"));
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
            getLogger().severe("Reloading... will kick all online players to avoid crash.");
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
			if (!AntiKBA.lastVelocity.isEmpty())
				AntiKBA.lastVelocity.clear();
			if (!AntiKBA.awaitingVelocity.isEmpty())
				AntiKBA.awaitingVelocity.clear();
			if (!AntiKBA.totalMoved.isEmpty())
				AntiKBA.totalMoved.clear();
			if (!AntiKBB.lastSprintStart.isEmpty())
				AntiKBB.lastSprintStart.clear();
			if (!AntiKBB.lastSprintStop.isEmpty())
				AntiKBB.lastSprintStop.clear();
			if (!AutoClickerA.clicks.isEmpty())
				AutoClickerA.clicks.clear();
			if (!AutoClickerA.recording.isEmpty())
				AutoClickerA.recording.clear();
			if (!AutoClickerB.Clicks.isEmpty())
				AutoClickerB.Clicks.clear();
			if (!AutoClickerB.LastMS.isEmpty())
				AutoClickerB.LastMS.clear();
			if (!AutoClickerB.ClickTicks.isEmpty())
				AutoClickerB.ClickTicks.clear();
			if (!AutoClickerC.hitsSinceLastCheck.isEmpty())
				AutoClickerC.hitsSinceLastCheck.clear();
			if (!AutoClickerC.lastCheckedTick.isEmpty())
				AutoClickerC.lastCheckedTick.clear();
			if (!AutoClickerC.packetHitsSinceLastCheck.isEmpty())
				AutoClickerC.packetHitsSinceLastCheck.clear();
			if (!AutoClickerC.lastPacketTick.isEmpty())
				AutoClickerC.lastPacketTick.clear();
			if (!AutoClickerC.lastTickWithPacketSent.isEmpty())
				AutoClickerC.lastTickWithPacketSent.clear();
			if (!AutoClickerC.cps.isEmpty())
				AutoClickerC.cps.clear();
			if (!AutoClickerC.cpsMS.isEmpty())
				AutoClickerC.cpsMS.clear();
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
			if (!KillAuraD.packetTicks.isEmpty())
				KillAuraD.packetTicks.clear();
			if (!KillAuraE.lastAttack.isEmpty())
				KillAuraE.lastAttack.clear();
			if (!KillAuraF.counts.isEmpty())
				KillAuraF.counts.clear();
			if (!KillAuraI.hits.isEmpty())
				KillAuraI.hits.clear();
			if (!ReachB.count.isEmpty())
				ReachB.count.clear();
			if (!ReachB.offsets.isEmpty())
				ReachB.offsets.clear();
			if (!ReachC.reachTicks.isEmpty())
				ReachC.reachTicks.clear();
			if (!ReachC.projectileHit.isEmpty())
				ReachC.projectileHit.clear();
			if (!ReachD.toBan.isEmpty())
				ReachD.toBan.clear();
			if (!RegenA.FastHealTicks.isEmpty())
				RegenA.FastHealTicks.clear();
			if (!RegenA.LastHeal.isEmpty())
				RegenA.LastHeal.clear();
			if (!AscensionA.AscensionTicks.isEmpty())
				AscensionA.AscensionTicks.clear();
			if (!AscensionB.verbose.isEmpty())
				AscensionB.verbose.clear();
			if (!AscensionB.lastYMovement.isEmpty())
				AscensionB.lastYMovement.clear();
			if (!FastLadderA.count.isEmpty())
				FastLadderA.count.clear();
			if (!FlyB.flyTicksA.isEmpty())
				FlyB.flyTicksA.clear();
			if (!GlideA.flyTicks.isEmpty())
				GlideA.flyTicks.clear();
			if (!JesusA.onWater.isEmpty())
				JesusA.onWater.clear();
			if (!JesusA.placedBlockOnWater.isEmpty())
				JesusA.placedBlockOnWater.clear();
			if (!JesusA.count.isEmpty())
				JesusA.count.clear();
			if (!JesusA.velocity.isEmpty())
				JesusA.velocity.clear();
			if (!NoFallA.FallDistance.isEmpty())
				NoFallA.FallDistance.clear();
			if (!NoFallA.NoFallTicks.isEmpty())
				NoFallA.NoFallTicks.clear();
			if (!NoSlowdownA.speedTicks.isEmpty())
				NoSlowdownA.speedTicks.clear();
			if (!SpeedB.speedTicks.isEmpty())
				SpeedB.speedTicks.clear();
			if (!SpeedB.tooFastTicks.isEmpty())
				SpeedB.tooFastTicks.clear();
			if (!SpeedB.lastHit.isEmpty())
				SpeedB.lastHit.isEmpty();
			if (!SpeedC.speedTicks.isEmpty())
				SpeedC.speedTicks.clear();
			if (!SpeedC.tooFastTicks.isEmpty())
				SpeedC.tooFastTicks.clear();
			if (!SpeedC.lastHit.isEmpty())
				SpeedC.lastHit.isEmpty();
			if (!SpeedC.velocity.isEmpty())
				SpeedC.velocity.isEmpty();
			if (!SpiderA.AscensionTicks.isEmpty())
				SpiderA.AscensionTicks.clear();
			if (!TimerA.packets.isEmpty())
				TimerA.packets.clear();
			if (!TimerA.verbose.isEmpty())
				TimerA.verbose.clear();
			if (!TimerA.lastPacket.isEmpty())
				TimerA.lastPacket.clear();
			if (!TimerA.toCancel.isEmpty())
				TimerA.toCancel.clear();
			if (!TimerB.timerTicks.isEmpty())
				TimerB.timerTicks.clear();
			if (!VClipA.teleported.isEmpty())
				VClipA.teleported.clear();
			if (!VClipA.lastLocation.isEmpty())
				VClipA.lastLocation.clear();
			if (!BlockInteractB.speedTicks.isEmpty())
				BlockInteractB.speedTicks.clear();
			if (!ChangeA.built.isEmpty())
				ChangeA.built.clear();
			if (!ChangeA.falling.isEmpty())
				ChangeA.falling.clear();
			if (!CrashA.crashTicks.isEmpty())
				CrashA.crashTicks.clear();
			if (!CrashA.crash2Ticks.isEmpty())
				CrashA.crash2Ticks.clear();
			if (!CrashA.crash3Ticks.isEmpty())
				CrashA.crash3Ticks.clear();
			if (!PacketsA.lastPacket.isEmpty())
				PacketsA.lastPacket.clear();
			if (!PacketsA.packetTicks.isEmpty())
				PacketsA.packetTicks.clear();
			if (!PacketsA.blacklist.isEmpty())
				PacketsA.blacklist.clear();
			if (!SneakA.sneakTicks.isEmpty())
				SneakA.sneakTicks.clear();
			if (!HitBoxA.count.isEmpty())
				HitBoxA.count.clear();
			if (!HitBoxA.lastHit.isEmpty())
				HitBoxA.lastHit.clear();
			if (!HitBoxA.yawDif.isEmpty())
				HitBoxA.yawDif.clear();
			if (!FastBowA.count.isEmpty())
				FastBowA.count.clear();
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
		return new ArrayList<Check>(this.Checks);
	}

	public boolean isCheckingUpdates() {
		return this.getConfig().getBoolean("settings.checkUpdates");
	}

	public String getVersion() {
		return this.getDescription().getVersion();
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
			logFile.addLine("- " + check.getType() + "." + check.getName() + " x" + Violations);
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
			final ActionMessageUtil msg = new ActionMessageUtil();
			msg.addText(PREFIX);
			msg.addText(Color.translate(
					getConfig().getString("alerts.secondary") + player.getName()))
					.addHoverText(Color.Gray + "(Click to teleport to " + Color.Red + player.getName() + Color.Gray + ")")
					.setClickEvent(ActionMessageUtil.ClickableType.RunCommand, "/tp " + player.getName());
			msg.addText(Color.translate(
					getConfig().getString("alerts.primary") + " set off " + getConfig().getString("alerts.secondary")
							+ check.getType() + "." + check.getName() + getConfig().getString("alerts.primary") + " and will "
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
							Bukkit.broadcastMessage(Color.translate(
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
					+ "x" + violations);
			if (violations % check.getViolationsToNotify() == 0) {
					for (Player playerplayer : this.AlertsOn) {
						if (check.isJudgmentDay() && !playerplayer.hasPermission("anticheat.staff")) {
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
        ProtocolLibrary.getProtocolManager().removePacketListeners(this);
        if (logger != null){
            logger.log(Level.INFO, "Plugin disabled");
            PluginLoggerHelper.closeLogger(logger);
        }}
   
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

        dispatchCommand = getConfig().getString("bancmd");
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
}