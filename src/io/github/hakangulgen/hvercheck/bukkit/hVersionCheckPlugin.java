package io.github.hakangulgen.hvercheck.bukkit;

import io.github.hakangulgen.hvercheck.bukkit.command.hVersionCheckCommand;
import io.github.hakangulgen.hvercheck.bukkit.listener.ConnectionListener;
import io.github.hakangulgen.hvercheck.bukkit.util.ConfigurationUtil;
import io.github.hakangulgen.hvercheck.bukkit.util.ConfigurationVariables;
import io.github.hakangulgen.hvercheck.shared.StaffManager;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.ViaAPI;

public class hVersionCheckPlugin extends JavaPlugin {

    private ViaAPI viaAPI;

    private ConfigurationVariables configurationVariables;

    @Override
    public void onEnable() {
        final Server server = this.getServer();
        final PluginManager pluginManager = server.getPluginManager();

        if (pluginManager.getPlugin("ViaVersion") == null) {
            this.getLogger().info("The plugin requires 'ViaVersion' to work.");
            pluginManager.disablePlugin(this);
            return;
        }

        final ConfigurationUtil configurationUtil = new ConfigurationUtil(this);

        configurationUtil.createConfiguration("%datafolder%/config.yml");

        this.configurationVariables = new ConfigurationVariables(configurationUtil);

        this.viaAPI = Via.getAPI();

        final StaffManager staffManager = new StaffManager();

        pluginManager.registerEvents(new ConnectionListener(this, configurationVariables, staffManager), this);

        getCommand("hversioncheck").setExecutor(new hVersionCheckCommand(this, configurationVariables));
    }

    public ViaAPI getViaAPI() { return viaAPI; }

}
