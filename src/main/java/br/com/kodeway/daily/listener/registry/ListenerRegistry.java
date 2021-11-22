package br.com.kodeway.daily.listener.registry;

import br.com.kodeway.daily.DailyPlugin;
import br.com.kodeway.daily.listener.UserConnectListener;
import br.com.kodeway.daily.listener.UserDisconnectListener;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

@Data(staticConstructor = "of")
public final class ListenerRegistry {

    private final DailyPlugin plugin;

    public void register() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new UserDisconnectListener(plugin.getProfileManager().getProfileStorage()), plugin);
        pluginManager.registerEvents(new UserConnectListener(plugin.getProfileManager().getProfileStorage()), plugin);
    }

}
