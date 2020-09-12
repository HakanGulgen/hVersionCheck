package io.github.hakangulgen.hvercheck.bukkit.listener;

import io.github.hakangulgen.hvercheck.bukkit.hVersionCheckPlugin;
import io.github.hakangulgen.hvercheck.bukkit.util.ConfigurationVariables;
import io.github.hakangulgen.hvercheck.shared.StaffManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import us.myles.ViaVersion.api.ViaAPI;
import us.myles.ViaVersion.api.protocol.ProtocolVersion;

public class ConnectionListener implements Listener {

    private final hVersionCheckPlugin plugin;
    private final ConfigurationVariables variables;
    private final StaffManager staffManager;

    public ConnectionListener(hVersionCheckPlugin plugin, ConfigurationVariables variables, StaffManager staffManager) {
        this.plugin = plugin;
        this.variables = variables;
        this.staffManager = staffManager;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        if (player.hasPermission("hversioncheck.notify")) {
            staffManager.addStaff(player.getName());
        }

        if (!variables.isVersionMessageEnabled()) return;

        final ViaAPI viaAPI = plugin.getViaAPI();

        final int playerVersion = viaAPI.getPlayerVersion(player.getUniqueId());
        final String protocolName = ProtocolVersion.getProtocol(playerVersion).getName();

        player.sendMessage(variables.getMessage()
                .replace("%version%", protocolName)
                .replace("%versionNumber%", playerVersion + ""));

        if (!variables.isNotifyEnabled()) return;

        for (final String staffName : staffManager.getAllStaff()) {
            final Player staff = plugin.getServer().getPlayer(staffName);
            if (staff != null) {
                if (staff != player) {
                    staff.sendMessage(variables.getNotificationMessage()
                            .replace("%player%", player.getName())
                            .replace("%version%", protocolName)
                            .replace("%versionNumber%", playerVersion + ""));
                }
            }
        }
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final String name = player.getName();

        staffManager.removeStaff(name);
    }

    @EventHandler
    public void onKick(final PlayerKickEvent event) {
        final Player player = event.getPlayer();
        final String name = player.getName();

        staffManager.removeStaff(name);
    }
}
