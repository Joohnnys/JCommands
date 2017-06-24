package me.joohnnys.jcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import me.joohnnys.jcommands.util.Config;
import me.joohnnys.jcommands.util.FormatString;

public class Aliases {
	static Config Config = new Config();
	static ConfigurationSection configSection = Config.pluginConfig().getConfigurationSection("aliases");

	public static boolean add(CommandSender sender, String[] args) {
		if (args.length >= 4) {
			String cmd = args[2].toLowerCase();
			if (configSection.getKeys(false).contains(cmd)) {
				sender.sendMessage(FormatString.colored(Config.getMessage("command-add-error")));
				return true;
			}

			String alias = "";
			for (int i = 3; i < args.length; i++) {
				if (i < (args.length - 1)) {
					alias = alias + args[i] + " ";
				} else {
					alias = alias + args[i];
				}
			}

			Config.pluginConfig().set("aliases." + cmd, alias);
			Config.saveConfig();
			sender.sendMessage(FormatString.command(Config.getMessage("command-add-success"), cmd, ""));

			return true;
		} else {
			sender.sendMessage(FormatString.colored(Config.getMessage("error")));
			return true;
		}
	}

	public static boolean set(CommandSender sender, String[] args) {
		if (args.length >= 4) {
			String cmd = args[2];
			if (!configSection.getKeys(false).contains(cmd)) {
				sender.sendMessage(FormatString.colored(Config.getMessage("command-set-error")));
				return true;
			}

			String alias = "";
			for (int i = 3; i < args.length; i++) {
				if (i < (args.length - 1)) {
					alias = alias + args[i] + " ";
				} else {
					alias = alias + args[i];
				}
			}

			Config.pluginConfig().set("aliases." + cmd, alias);
			Config.saveConfig();
			sender.sendMessage(FormatString.command(Config.getMessage("command-set-success"), cmd, ""));

			return true;
		} else {
			sender.sendMessage(FormatString.colored(Config.getMessage("error")));
			return true;
		}
	}

	public static boolean del(CommandSender sender, String[] args) {
		if (args.length >= 3) {
			String cmd = args[2];
			if (!configSection.getKeys(false).contains(cmd)) {
				sender.sendMessage(FormatString.colored(Config.getMessage("command-del-error")));
				return true;
			}

			Config.pluginConfig().set("aliases." + cmd, null);
			Config.saveConfig();
			sender.sendMessage(FormatString.command(Config.getMessage("command-del-success"), cmd, ""));

			return true;
		} else {
			sender.sendMessage(FormatString.colored(Config.getMessage("error")));
			return true;
		}
	}

}
