package me.joohnnys.jcommands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import me.joohnnys.jcommands.util.Config;
import me.joohnnys.jcommands.util.FormatString;

public class Messages {
	static Config Config = new Config();
	static ConfigurationSection configSection = Config.pluginConfig().getConfigurationSection("messages");

	public static boolean add(CommandSender sender, String[] args) {
		if (args.length >= 4) {
			String cmd = args[2];
			if (configSection.getKeys(false).contains(cmd)) {
				sender.sendMessage(FormatString.command(Config.getMessage("command-add-error"), cmd, ""));
				return true;
			}

			String msg = "";
			for (int i = 3; i < args.length; i++) {
				msg = msg + args[i] + " ";
			}
			List<String> list = new ArrayList<String>();
			list.add(msg);
			Config.pluginConfig().set("messages." + cmd, list);
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
				sender.sendMessage(FormatString.command(Config.getMessage("command-set-error"), cmd, ""));
				return true;
			}

			List<String> list = Config.pluginConfig().getStringList("messages." + cmd);

			Integer n = null;

			if (args[3].equalsIgnoreCase("line")) {
				if (args.length > 5) {
					try {
						n = Integer.parseInt(args[4]);
					} catch (NumberFormatException e) {
						sender.sendMessage(FormatString.number(Config.getMessage("number-exception"), args[4]));
						return true;
					}
				} else {
					sender.sendMessage(FormatString.command(Config.getMessage("command-set-error"), cmd, ""));
					return true;
				}
			}

			String line = "";
			if (n != null) {
				n--;
				for (int i = 5; i < args.length; i++) {
					line = line + args[i] + " ";
				}
				if (!(list.size() > n)) {
					list.add(line);
				} else {
					list.set(n, line);
				}
			} else {
				for (int i = 3; i < args.length; i++) {
					line = line + args[i] + " ";
				}
				list.clear();
				list.add(line);
			}

			Config.pluginConfig().set("messages." + cmd, list);
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
				sender.sendMessage(FormatString.command(Config.getMessage("command-del-error"), cmd, ""));
				return true;
			}

			Config.pluginConfig().set("messages." + cmd, null);
			Config.saveConfig();
			sender.sendMessage(FormatString.command(Config.getMessage("command-del-success"), cmd, ""));

			return true;
		} else {
			sender.sendMessage(FormatString.colored(Config.getMessage("error")));
			return true;
		}
	}
}
