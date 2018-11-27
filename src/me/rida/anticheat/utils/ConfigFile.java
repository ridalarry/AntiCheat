package me.rida.anticheat.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import me.rida.anticheat.AntiCheat;

public class ConfigFile {

    private File file;
    private YamlConfiguration configuration;

    public ConfigFile() {
        this.file = new File(AntiCheat.Instance.getDataFolder(), "config.yml");
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    public void load() {
        this.file = new File(AntiCheat.Instance.getDataFolder(), "config.yml");
        if (!this.file.exists()) {
            AntiCheat.Instance.saveResource("config.yml", false);
        }
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    public void save() {

        try {
            this.configuration.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public YamlConfiguration getConfiguration() {
        return this.configuration;
    }

    public File getFile() {
        return file;
    }

    public double getDouble(String path) {
        if (this.configuration.contains(path)) {
            return this.configuration.getDouble(path);
        }
        return 0.0;
    }

    public void set(String path, Object object) {
        this.configuration.set(path, object);
    }

    public Object get(String path) {
        if (this.configuration.contains(path)) {
            return this.configuration.get(path);
        }
        return null;
    }

    public int getInt(String path) {
        if (this.configuration.contains(path)) {
            return this.configuration.getInt(path);
        }
        return 0;
    }

    public boolean getBoolean(String path) {
        return this.configuration.contains(path) && this.configuration.getBoolean(path);
    }

    public String getString(String path) {
        if (this.configuration.contains(path)) {
            return Color.translate(this.configuration.getString(path));
        }
        return "String at path: " + path + " not found!";
    }

    public List<String> getStringList(String path) {
        if (this.configuration.contains(path)) {
            final ArrayList<String> strings = new ArrayList<>();
            for (final String string : this.configuration.getStringList(path)) {
                strings.add(Color.translate(string));
            }
            return strings;
        }
        return Arrays.asList("String List at path: " + path + " not found!");
    }
}