package me.joohnnys.jcommands;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class JCommands extends JavaPlugin implements Listener, CommandExecutor {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginCommand("JCommands").setExecutor(this);
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

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("JCommands") || command.getName().equalsIgnoreCase("JCmds")) {
			String lang = getConfig().getString("lang");
			File file = new File(getDataFolder(), lang + ".yml");
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			if (args.length == 0) {
				if (sender.hasPermission("jcmds.admin"))
					ajuda(sender, lang);
			} else {
				if (args[0].equalsIgnoreCase("help")) {
					if (sender.hasPermission("jcmds.admin"))
						ajuda(sender, lang);
				}
				if (args[0].equalsIgnoreCase("reload")) {
					if (sender.hasPermission("jcmds.admin"))
						reload(sender, lang);
				}
				if (args[0].equalsIgnoreCase("add")) {
					if (sender.hasPermission("jcmds.admin")) {
						if (args.length > 2) {
							add(sender, args[1], args, lang);
						} else {
							sender.sendMessage(colored(config.getString("Error")));
						}
					}
				}
				if (args[0].equalsIgnoreCase("del")) {
					if (sender.hasPermission("jcmds.admin")) {
						if (args.length > 1) {
							del(sender, args[1], lang);
						} else {
							sender.sendMessage(colored(config.getString("Error")));
						}
					}
				}

				if (args[0].equalsIgnoreCase("set")) {
					if (sender.hasPermission("jcmds.admin")) {
						if (args.length > 2) {
							set(sender, args[1], args, lang);
						} else {
							sender.sendMessage(colored(config.getString("Error")));
						}
					}
				}
			}
		}
		return false;
	}

	private boolean reload(CommandSender sender, String lang) {
		File file = new File(getDataFolder(), lang + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);

		reloadConfig();
		sender.sendMessage(colored(config.getString("Reload")));

		return true;
	}

	private boolean add(CommandSender sender, String cmd, String[] args, String lang) {
		File file = new File(getDataFolder(), lang + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		ConfigurationSection configSection = getConfig().getConfigurationSection("commands");

		for (String command : configSection.getKeys(false)) {
			if (command.equalsIgnoreCase(cmd)) {
				sender.sendMessage(cmdReplace(colored(config.getString("AddError")), cmd));
				return true;
			}
		}
		
		String endMSG = "";
		for (int i = 2; i < args.length; i++) {
			endMSG = endMSG + args[i] + " ";					
		}
		getConfig().set("commands." + cmd, endMSG);
		saveConfig();
		sender.sendMessage(cmdReplace(colored(config.getString("AddSuccess")), cmd));
		return true;
	}

	private boolean set(CommandSender sender, String cmd, String[] args, String lang) {
		File file = new File(getDataFolder(), lang + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		ConfigurationSection configSection = getConfig().getConfigurationSection("commands");

		for (String command : configSection.getKeys(false)) {
			if (command.equalsIgnoreCase(cmd)) {
				String endMSG = "";
				for (int i = 2; i < args.length; i++) {
					endMSG = endMSG + args[i] + " ";					
				}
				getConfig().set("commands." + cmd, endMSG);
				saveConfig();
				sender.sendMessage(cmdReplace(colored(config.getString("SetSuccess")), cmd));
				return true;
			}
		}

		sender.sendMessage(cmdReplace(colored(config.getString("SetError")), cmd));
		return true;
	}

	private boolean del(CommandSender sender, String cmd, String lang) {
		File file = new File(getDataFolder(), lang + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		ConfigurationSection configSection = getConfig().getConfigurationSection("commands");

		for (String command : configSection.getKeys(false)) {
			if (command.equalsIgnoreCase(cmd)) {
				getConfig().set("commands." + cmd, null);
				sender.sendMessage(cmdReplace(colored(config.getString("DelSuccess")), cmd));
				saveConfig();
				return true;
			}
		}

		sender.sendMessage(cmdReplace(colored(config.getString("DelError")), cmd));
		return true;
	}

	private boolean ajuda(CommandSender sender, String lang) {
		File file = new File(getDataFolder(), lang + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);

		String linha1 = colored(config.getString("1"));
		String linha2 = colored(config.getString("2"));
		String linha3 = colored(config.getString("3"));
		String linha4 = colored(config.getString("4"));
		String linha5 = colored(config.getString("5"));
		String linha6 = colored(config.getString("6"));
		String linha7 = colored(config.getString("7"));
		String linha8 = colored(config.getString("8"));

		sender.sendMessage(linha1);
		sender.sendMessage(linha2);
		sender.sendMessage(linha3);
		sender.sendMessage(linha4);
		sender.sendMessage(linha5);
		sender.sendMessage(linha6);
		sender.sendMessage(linha7);
		sender.sendMessage(linha8);

		return true;
	}

	@EventHandler
	public void aoComando(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		ConfigurationSection configSection = getConfig().getConfigurationSection("commands");

		for (String cmd : configSection.getKeys(false)) {
			if (event.getMessage().equalsIgnoreCase("/" + cmd)) {

				String colored = colored(getConfig().getString("commands." + cmd));
				String pName = colored.replace("%player%", player.getName());

				String formatted = pName;
				player.sendMessage(formatted);
				event.setCancelled(true);
			}

		}

		if (!(configSection.getKeys(false).contains(event.getMessage().replace("/", ""))))
			event.setCancelled(false);
	}

	private String colored(String s) {
		String colored = ChatColor.translateAlternateColorCodes('&', s);
		return colored;
	}
	
	private String cmdReplace(String s, String cmd) {
		String replace = s.replace("%command%", cmd);
		return replace;
	}
}
