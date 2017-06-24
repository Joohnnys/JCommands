package me.joohnnys.jcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.joohnnys.jcommands.util.Config;
import me.joohnnys.jcommands.util.FormatString;

public class JCommandExecutor implements CommandExecutor {
	static Config Config = new Config();

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("JCommands")) {
			if (sender.hasPermission("jcmds.admin")) {
				if (!(args.length > 0) || args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
					sender.sendMessage(FormatString.colored(Config.getLangConfig().getStringList("help")));
					return true;
				} else {
					if (args[0].equalsIgnoreCase("reload")) {
						Config.reload();
						sender.sendMessage(FormatString.colored(Config.getLangConfig().getString("reload")));
						return true;
					}
					if (args[0].equalsIgnoreCase("disable")) {
						return Disable.add(sender, args);
					}

					if (args[0].equalsIgnoreCase("enable")) {
						return Disable.del(sender, args);
					}

					if (args[0].equalsIgnoreCase("alias") || args[0].equalsIgnoreCase("aliases")) {
						if (args.length >= 3) {
							if (args[1].equalsIgnoreCase("add")) {
								return Aliases.add(sender, args);
							}
							if (args[1].equalsIgnoreCase("set")) {
								return Aliases.set(sender, args);
							}
							if (args[1].equalsIgnoreCase("del")) {
								return Aliases.del(sender, args);
							}

						}
					}

					if (args[0].equalsIgnoreCase("msg") || args[0].equalsIgnoreCase("message")) {
						if (args.length >= 3) {
							if (args[1].equalsIgnoreCase("add")) {
								return Messages.add(sender, args);
							} else {
								if (args[1].equalsIgnoreCase("set")) {
									return Messages.set(sender, args);
								} else if (args[1].equalsIgnoreCase("del")) {
									return Messages.del(sender, args);
								}
							}
						}
					}

					sender.sendMessage(FormatString.colored(Config.getMessage("error")));
					return true;
				}
			}
		}
		return false;
	}

}
