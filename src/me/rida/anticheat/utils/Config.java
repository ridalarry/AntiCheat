package me.rida.anticheat.utils;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.rida.anticheat.AntiCheat;

public class Config {

	File userFile;
	FileConfiguration userConfig;

	public Config(String name){
		userFile = new File(AntiCheat.getInstance().getDataFolder() + File.separator, name + ".yml");
		userConfig = YamlConfiguration.loadConfiguration(userFile);
	}

	public void makeConfigFile(){
		if ( !userFile.exists() ) {
			try {
				userConfig.save(userFile);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	public FileConfiguration getConfigFile(){
		return userConfig;
	}

	public void saveConfigFile(){
		try {
			getConfigFile().save(userFile);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}