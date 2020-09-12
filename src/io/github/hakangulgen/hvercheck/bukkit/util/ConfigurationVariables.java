package io.github.hakangulgen.hvercheck.bukkit.util;

import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;

public class ConfigurationVariables {

    private final ConfigurationUtil configurationUtil;

    private String prefix, noPermission, pluginReloaded, playerNotFound, checkPlayerVersion,
            message, notificationMessage;

    private boolean versionMessageEnabled, notifyEnabled;

    public ConfigurationVariables(ConfigurationUtil configurationUtil) {
        this.configurationUtil = configurationUtil;

        reloadConfig();
    }

    public void reloadConfig() {
        final Configuration config = configurationUtil.getConfiguration("%datafolder%/config.yml");

        prefix = ChatColor.translateAlternateColorCodes('&', config.getString("messages.prefix"));
        noPermission = ChatColor.translateAlternateColorCodes('&', config.getString("messages.noPermission").replace("%prefix%", prefix));
        pluginReloaded = ChatColor.translateAlternateColorCodes('&', config.getString("messages.pluginReloaded").replace("%prefix%", prefix));
        playerNotFound = ChatColor.translateAlternateColorCodes('&', config.getString("messages.playerNotFound").replace("%prefix%", prefix));
        checkPlayerVersion = ChatColor.translateAlternateColorCodes('&', config.getString("messages.checkPlayerVersion").replace("%prefix%", prefix));
        message = ChatColor.translateAlternateColorCodes('&', config.getString("versionMessageOnJoin.message").replace("%prefix%", prefix));
        notificationMessage = ChatColor.translateAlternateColorCodes('&', config.getString("notify.message").replace("%prefix%", prefix));

        versionMessageEnabled = config.getBoolean("versionMessageOnJoin.enabled");
        notifyEnabled = config.getBoolean("notify.enabled");
    }

    public String getPrefix() { return prefix; }

    public String getNoPermission() { return noPermission; }

    public String getPluginReloaded() { return pluginReloaded; }

    public String getPlayerNotFound() { return playerNotFound; }

    public String getCheckPlayerVersion() { return checkPlayerVersion; }

    public String getMessage() { return message; }

    public String getNotificationMessage() { return notificationMessage; }

    public boolean isVersionMessageEnabled() { return versionMessageEnabled; }

    public boolean isNotifyEnabled() { return notifyEnabled; }

}
