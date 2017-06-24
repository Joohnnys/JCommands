package me.joohnnys.jcommands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class JCommands extends JavaPlugin {
	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(new Listeners(), this);
		Bukkit.getPluginCommand("JCommands").setExecutor(new JCommandExecutor());
		File pt_BR = new File(getDataFolder(), "pt_BR.yml");
		if (!pt_BR.exists()) {
			saveResource("pt_BR.yml", false);
		}

		File en_US = new File(getDataFolder(), "en_US.yml");
		if (!en_US.exists()) {
			saveResource("en_US.yml", false);
		}

		saveDefaultConfig();
	}
}
