package me.rida.anticheat.checks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import me.rida.anticheat.AntiCheat;
import me.rida.anticheat.utils.TxtFile;

public class Check implements Listener {
	private String Identifier;
	private String Name;
	protected CheckType Type;
	private AntiCheat AntiCheat;
	private boolean Enabled = true;
	private boolean BanTimer = false;
	private boolean Bannable = true;
	private boolean JudgementDay = false;
	private Integer MaxViolations = Integer.valueOf(5);
	private Integer ViolationsToNotify = Integer.valueOf(1);
	private Long ViolationResetTime = Long.valueOf(600000L);
	public Map<String, List<String>> DumpLogs = new HashMap<String, List<String>>();

	public Check(String Identifier, String Name, CheckType Type, boolean Enabled, boolean Bannable, boolean JudgementDay, boolean BanTimer, Integer MaxViolations, Integer ViolationsToNotify, long ViolationResetTime, AntiCheat AntiCheat) {
		this.Name = Name;
		this.AntiCheat = AntiCheat;
		this.Identifier = Identifier;
		this.Type = Type;
		
		this.Enabled = Enabled;
		this.setEnabled(Enabled);
		
		this.Bannable = Bannable;
		this.setBannable(Bannable);
		
		this.JudgementDay = JudgementDay;
		this.setJudgementDay(JudgementDay);
		
		this.BanTimer = BanTimer;
		this.setAutobanTimer(BanTimer);
		
		this.MaxViolations = MaxViolations;
		this.setMaxViolations(MaxViolations);
		
		this.ViolationsToNotify = ViolationsToNotify;
		this.setViolationsToNotify(ViolationsToNotify);
		
		this.ViolationResetTime = ViolationResetTime;
		this.setViolationResetTime(ViolationResetTime);
	}


	public void dumplog(Player player, String log) {
		if (!this.DumpLogs.containsKey(player.getName())) {
			List<String> logs = new ArrayList<String>();
			logs.add(log);
			this.DumpLogs.put(player.getName(), logs);
		} else {
			this.DumpLogs.get(player.getName()).add(log);
		}
	}

	public void onEnable() {
	}

	public void onDisable() {
	}

	public boolean isEnabled() {
		return this.Enabled;
	}

	public boolean isBannable() {
		return this.Bannable;
	}

	public boolean hasBanTimer() {
		return this.BanTimer;
	}

	public boolean isJudgmentDay() {
		return this.JudgementDay;
	}

	public AntiCheat getAntiCheat() {
		return this.AntiCheat;
	}

	public boolean hasDump(Player player) {
		return DumpLogs.containsKey(player.getName());
	}

	public void clearDump(Player player) {
		DumpLogs.remove(player.getName());
	}

	public void clearDumps() {
		DumpLogs.clear();
	}

	public Integer getMaxViolations() {
		return this.MaxViolations;
	}

	public Integer getViolationsToNotify() {
		return this.ViolationsToNotify;
	}

	public Long getViolationResetTime() {
		return this.ViolationResetTime;
	}

	public void setEnabled(boolean Enabled) {
		if (AntiCheat.getConfig().getBoolean("checks." + this.getType() + "." + this.getName() + "." + this.getIdentifier() + ".enabled") != Enabled
				&& AntiCheat.getConfig().get("checks." + this.getType() + "." + this.getName() + "." + this.getIdentifier() + ".enabled") != null) {
			this.Enabled = AntiCheat.getConfig().getBoolean("checks." + this.getType() + "." + this.getName() + "." + this.getIdentifier() + ".enabled");
			return;
		}
		if (Enabled) {
			if (!isEnabled()) {
				this.AntiCheat.RegisterListener(this);
			}
		} else if (isEnabled()) {
			HandlerList.unregisterAll(this);
		}
		this.Enabled = Enabled;
	}

	public void checkValues() {
		if (AntiCheat.getConfig().getBoolean("checks." + this.getType() + "." + this.getName() + "." + this.getIdentifier() + ".enabled") == true) {
			this.setEnabled(true);
		} else {
			this.setEnabled(false);
		}
		if (AntiCheat.getConfig().getBoolean("checks." + this.getType() + "." + this.getName() + "." + this.getIdentifier() + ".bannable") == true) {
			this.setBannable(true);
		} else {
			this.setBannable(false);
		}
		if (AntiCheat.getConfig().getBoolean("checks." + this.getType() + "." + this.getName() + "." + this.getIdentifier() + ".judgementDay") == true) {
			this.setJudgementDay(true);
		} else {
			this.setJudgementDay(false);
		}
		if (AntiCheat.getConfig().getBoolean("checks." + this.getType() + "." + this.getName() + "." + this.getIdentifier() + ".banTimer") == true) {
			this.setAutobanTimer(true);
		} else {
			this.setAutobanTimer(false);
		}
		if (AntiCheat.getConfig().getInt("checks." + this.getType() + "." + this.getName() + "." + this.getIdentifier() + ".violationsToNotify") != 0) {
			this.setViolationsToNotify((AntiCheat.getConfig().getInt("checks." + this.getType() + "." + this.getName() + "." + this.getIdentifier() + ".violationsToNotify")));
		} else {
			this.setViolationsToNotify(0);
		}
		if (AntiCheat.getConfig().getInt("checks." + this.getType() + "." + this.getName() + "." + this.getIdentifier() + ".maxViolations") != 0) {
			this.setMaxViolations((AntiCheat.getConfig().getInt("checks." + this.getType() + "." + this.getName() + "." + this.getIdentifier() + ".maxViolations")));
		} else {
			this.setMaxViolations(0);
		}
		if (AntiCheat.getConfig().getLong("checks." + this.getType() + "." + this.getName() + "." + this.getIdentifier() + ".violationResetTime") != 0) {
			this.setViolationResetTime((AntiCheat.getConfig().getInt("checks." + this.getType() + "." + this.getName() + "." + this.getIdentifier() + ".violationResetTime")));
		} else {
			this.setViolationResetTime(0);
		}
	}

	public void setBannable(boolean Bannable) {
		if (AntiCheat.getConfig().getBoolean("checks." + this.getType() + "." + this.getName() + "." + this.getIdentifier() + ".bannable") != Bannable
				&& AntiCheat.getConfig().get("checks." + this.getType() + "." + this.getName() + "." + this.getIdentifier() + ".bannable") != null) {
			this.Bannable = AntiCheat.getConfig().getBoolean("checks." + this.getType() + "." + this.getName() + "." + this.getIdentifier() + ".bannable");
			return;
		}
		this.Bannable = Bannable;
	}

	public void setAutobanTimer(boolean BanTimer) {
		if ((AntiCheat.getConfig().getBoolean("checks." + this.getType() + "." + this.getName() + "." + this.getIdentifier() + ".banTimer") != BanTimer
				&& AntiCheat.getConfig().get("checks." + this.getType() + "." + this.getName() + "." + this.getIdentifier() + ".banTimer") != null)) {
			this.BanTimer = AntiCheat.getConfig().getBoolean("checks." + this.getType() + "." + this.getName() + "." + this.getIdentifier() + ".banTimer");
			return;
		}
		this.BanTimer = BanTimer;
	}

	public void setMaxViolations(int MaxViolations) {
		if (AntiCheat.getConfig().getInt("checks." + this.getType() + "." + this.getName() + "." + this.getIdentifier() + ".maxViolations") != MaxViolations
				&& AntiCheat.getConfig().get("checks." + this.getType() + "." + this.getName() + "." + this.getIdentifier() + ".maxViolations") != null) {
			this.MaxViolations = AntiCheat.getConfig().getInt("checks." + this.getType() + "." + this.getName() + "." + this.getIdentifier() + ".maxViolations");
			return;
		}
		this.MaxViolations = MaxViolations;
	}

	public void setViolationsToNotify(int ViolationsToNotify) {
		if (AntiCheat.getConfig().getInt("checks." + this.getType() + "." + this.getName() + "." + this.getIdentifier() + ".violationsToNotify") != ViolationsToNotify
				&& AntiCheat.getConfig().get("checks." + this.getType() + "." + this.getName() + "." + this.getIdentifier() + ".violationsToNotify") != null) {
			this.ViolationsToNotify = AntiCheat.getConfig().getInt("checks." + this.getType() + "." + this.getName() + "." + this.getIdentifier() + ".violationsToNotify");
			return;
		}
		this.ViolationsToNotify = ViolationsToNotify;
	}

	public void setViolationResetTime(long ViolationResetTime) {
		if (AntiCheat.getConfig().getInt("checks." + this.getType() + "." + this.getName() + "." + this.getIdentifier() + ".violationResetTime") != ViolationResetTime
				&& AntiCheat.getConfig().get("checks." + this.getType() + "." + this.getName() + "." + this.getIdentifier() + ".violationResetTime") != null) {
			this.ViolationResetTime = AntiCheat.getConfig().getLong("checks." + this.getType() + "." + this.getName() + "." + this.getIdentifier() + ".violationResetTime");
			return;
		}
		this.ViolationResetTime = ViolationResetTime;
	}

	public void setJudgementDay(boolean JudgementDay) {
		if (AntiCheat.getConfig().getBoolean("checks." + this.getType() + "." + this.getName() + "." + this.getIdentifier() + ".judgementDay") != JudgementDay
				&& AntiCheat.getConfig().get("checks." + this.getType() + "." + this.getName() + "." + this.getIdentifier() + ".judgementDay") != null) {
			this.JudgementDay = AntiCheat.getConfig().getBoolean("checks." + this.getType() + "." + this.getName() + "." + this.getIdentifier() + ".judgementDay");
			return;
		}
		this.JudgementDay = JudgementDay;
	}
	public CheckType getType() {
		return this.Type;
	}

	public String getName() {
		return this.Name;
	}

	public String getIdentifier() {
		return this.Identifier;
	}

	public List<String> getDump(Player player) {
		return this.DumpLogs.get(player.getName());
	}

	public String dump(String player) {
		if (!this.DumpLogs.containsKey(player)) {
			return null;
		}
		TxtFile file = new TxtFile(this.getAntiCheat(), "/Dumps", player + "_" + this.getType() + "." + this.getName() + "." + this.getIdentifier());
		file.clear();
		for (String Line : this.DumpLogs.get(player)) {
			file.addLine(Line);
		}
		file.write();
		return file.getName();
	}
}