package me.joohnnys.jcommands;

import java.util.List;

import org.bukkit.command.CommandSender;

import me.joohnnys.jcommands.util.Config;
import me.joohnnys.jcommands.util.FormatString;

public class Disable {
	static Config Config = new Config();

	public static boolean add(CommandSender sender, String[] args) {
		if (args.length > 2) {
			List<String> disable = Config.pluginConfig().getStringList("disable");
			String cmd = "";
			for (int i = 1; i < args.length; i++) {
				cmd = cmd + args[i] + " ";
			}
			
			if(disable.contains(cmd)) {
				sender.sendMessage(FormatString.command(Config.getMessage("command-add-error"), cmd, ""));
				return true;
			}
			
			disable.add(cmd);
			Config.pluginConfig().set("disable", disable);
			Config.saveConfig();
			sender.sendMessage(FormatString.command(Config.getMessage("command-add-success"), cmd, ""));
			return true;
		} else {
			sender.sendMessage(FormatString.colored(Config.getMessage("error")));
			return true;
		}
		
	}
	
	public static boolean del(CommandSender sender, String[] args) {
		if(args.length > 1) {
			List<String> disable = Config.pluginConfig().getStringList("disable");
			String cmd = "";
			for (int i = 1; i < args.length; i++) {
				cmd = cmd + args[i] + " ";
			}
			
			if(!disable.contains(cmd)) {
				sender.sendMessage(FormatString.command(Config.getMessage("command-del-error"), cmd, ""));
				return true;
			}
			
			disable.remove(cmd);
			Config.pluginConfig().set("disable", disable);
			Config.saveConfig();
			sender.sendMessage(FormatString.command(Config.getMessage("command-del-success"), cmd, ""));
			return true;
		} else {
			sender.sendMessage(FormatString.colored(Config.getMessage("error")));
			return true;
		}
	}
	
}
