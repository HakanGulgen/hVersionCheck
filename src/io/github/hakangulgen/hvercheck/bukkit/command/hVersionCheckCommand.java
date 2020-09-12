package io.github.hakangulgen.hvercheck.bukkit.command;

import io.github.hakangulgen.hvercheck.bukkit.hVersionCheckPlugin;
import io.github.hakangulgen.hvercheck.bukkit.util.ConfigurationVariables;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.myles.ViaVersion.api.ViaAPI;
import us.myles.ViaVersion.api.protocol.ProtocolVersion;

public class hVersionCheckCommand implements CommandExecutor {

    private final hVersionCheckPlugin plugin;
    private final ConfigurationVariables variables;

    public hVersionCheckCommand(hVersionCheckPlugin plugin, ConfigurationVariables variables) {
        this.plugin = plugin;
        this.variables = variables;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            if (!player.hasPermission("hversioncheck.usage")) {
                player.sendMessage(variables.getNoPermission());
                return  true;
            }
        }
        if (args.length != 1) {
            final String prefix = variables.getPrefix();

            sender.sendMessage(prefix + " §bAuthor: §7HKNGLGN (hknn)");
            sender.sendMessage(prefix + " §b/hvcheck reload §8- §7Reload configuration.");
            sender.sendMessage(prefix + " §b/hvcheck <player> §8- §7Check player's version.");
        } else if (args[0].equalsIgnoreCase("reload")) {
            variables.reloadConfig();

            sender.sendMessage(variables.getPluginReloaded());
        } else {
            final Player target = plugin.getServer().getPlayer(args[0]);

            if (target != null) {
                final ViaAPI viaAPI = plugin.getViaAPI();

                final int playerVersion = viaAPI.getPlayerVersion(target.getUniqueId());
                final String protocolName = ProtocolVersion.getProtocol(playerVersion).getName();

                sender.sendMessage(variables.getCheckPlayerVersion().replace("%version%", protocolName)
                        .replace("%player%", target.getName())
                        .replace("%versionNumber%", playerVersion + ""));
            } else {
                sender.sendMessage(variables.getPlayerNotFound());
            }
        }
        return false;
    }
}
