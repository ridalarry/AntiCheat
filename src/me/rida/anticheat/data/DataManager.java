package me.rida.anticheat.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

import me.rida.anticheat.checks.Check;

import java.util.*;

public class DataManager {
    public List<Check> checks;
    private Map<Player, Map<Check, Integer>> violations;
    public List<DataPlayer> players;
    
    private Set<DataPlayer> dataSet = new HashSet<>();

    public DataManager() {
        Bukkit.getOnlinePlayers().forEach(this::add);
        checks = new ArrayList<>();
        violations = new WeakHashMap<>();
        players = new ArrayList<>();
        addChecks();
    }

    public DataPlayer getDataPlayer(Player player) {
        return dataSet.stream().filter(dataPlayer -> dataPlayer.player == player).findFirst().orElse(null);
    }

    public void add(Player player) {
        dataSet.add(new DataPlayer(player));
    }

    public void remove(Player player) {
        dataSet.removeIf(dataPlayer -> dataPlayer.player == player);
    }


    private void addChecks() {
    }

    public void removeCheck(Check check) {
        if(checks.contains(check)) checks.remove(check);
    }

    public boolean isCheck(Check check) {
        return checks.contains(check);
    }

    public Check getCheckAyName(String checkName) {
        for(Check checkLoop : Collections.synchronizedList(checks)) {
            if(checkLoop.getName().equalsIgnoreCase(checkName)) return checkLoop;
        }

        return null;
    }

    public Map<Player, Map<Check, Integer>> getViolationsMap() {
        return violations;
    }

    public int getViolatonsPlayer(Player player, Check check) {
        if(violations.containsKey(player)) {
            Map<Check, Integer> vlMap = violations.get(player);

            return vlMap.getOrDefault(check, 0);
        }
        return 0;
    }

    public void addViolation(Player player, Check check) {
        if (violations.containsKey(player)) {
            Map<Check, Integer> vlMap = violations.get(player);

            vlMap.put(check, vlMap.getOrDefault(check, 0) + 1);
            violations.put(player, vlMap);
        } else {
            Map<Check, Integer> vlMap = new HashMap<>();

            vlMap.put(check, 1);

            violations.put(player, vlMap);
        }
    }

    public void addPlayerData(Player player) {
        players.add(new DataPlayer(player));
    }

    public DataPlayer getData(Player player) {
        for(DataPlayer dataLoop : Collections.synchronizedList(players)) {
            if(dataLoop.getPlayer() == player) {
                return dataLoop;
            }
        }
        return null;
    }

    public void removePlayerData(Player player) {
        for(DataPlayer dataLoop : Collections.synchronizedList(players)) {
            if(dataLoop.getPlayer() == player) {
                players.remove(dataLoop);
                break;
            }
        }
    }


    public void addCheck(Check check) {
        if(!checks.contains(check)) checks.add(check);
    }
    public List<Check> getChecks() {
        return checks;
    }
}