package me.joohnnys.jcommands.util;

import java.util.List;

import net.md_5.bungee.api.ChatColor;

public class FormatString {
	
	public static String colored(String s) {
		s = ChatColor.translateAlternateColorCodes('&', s);
		return s;
	}
	
	public static String[] colored(List<String> list) {
		String[] args = list.toArray(new String[0]);
		for(int i = 0; i < list.size(); i++) {
			args[i] = ChatColor.translateAlternateColorCodes('&', args[i]);
		}
		
		return args;
	}
	
	public static String message(String s, String player) {
		s = colored(s).replace("%player%", player);
		return s;
	}
	
	public static String command(String s, String command, String alias) {
		s = colored(s).replace("%command%", command).replace("%alias%", alias);
		return s;
	}
	
	public static String number(String s, String n) {
		s = colored(s).replace("%number%", n);
		return s;
	}
}
