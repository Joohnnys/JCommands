package me.joohnnys.jcommands.util;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.joohnnys.jcommands.JCommands;

public class Config {
	JCommands plugin = JCommands.getPlugin(JCommands.class);
	String lang = plugin.getConfig().getString("lang");
	
	public FileConfiguration pluginConfig() {
		return plugin.getConfig();
	}
	
	public FileConfiguration getLangConfig() {
		File file = new File(plugin.getDataFolder(), lang + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		return config;
	}
	
	public String getMessage(String s) {
		return getLangConfig().getString(s);
	}
	
	public void saveConfig() {
		plugin.saveConfig();
	}

	public void reload() {
		plugin.reloadConfig();
	}

}
