package me.joohnnys.jcommands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import me.joohnnys.jcommands.util.Config;
import me.joohnnys.jcommands.util.FormatString;

public class Listeners implements Listener {
	static Config Config = new Config();
	JCommands plugin = JCommands.getPlugin(JCommands.class);

	@EventHandler
	public void aoComando(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		ConfigurationSection configSectionM = plugin.getConfig().getConfigurationSection("messages");
		ConfigurationSection configSectionA = plugin.getConfig().getConfigurationSection("aliases");
		List<String> disabledCommands = plugin.getConfig().getStringList("disable");
		
		for (String cmd : disabledCommands) {
			if (event.getMessage().equalsIgnoreCase("/" + cmd)) {
				event.setCancelled(true);
				return;
			}
		}
		
		for (String cmd : configSectionA.getKeys(false)) {
			String msg = event.getMessage().toLowerCase();
			if (msg.startsWith("/" + cmd)) {
				Bukkit.getServer().dispatchCommand(player, msg.replaceFirst("/" + cmd, plugin.getConfig().getString("aliases." + cmd)));
				event.setCancelled(true);
				return;
			}
		}

		for (String cmd : configSectionM.getKeys(false)) {
			if (event.getMessage().equalsIgnoreCase("/" + cmd)) {
				player.sendMessage(FormatString.colored(Config.pluginConfig().getStringList("messages." + cmd)));
				event.setCancelled(true);
				return;
			}
		}
	}
}
