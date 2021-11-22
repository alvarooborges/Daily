package br.com.kodeway.daily;

import br.com.kodeway.daily.command.registry.CommandRegistry;
import br.com.kodeway.daily.inventory.registry.InventoryRegistry;
import br.com.kodeway.daily.listener.registry.ListenerRegistry;
import br.com.kodeway.daily.manager.ProfileManager;
import br.com.kodeway.daily.manager.RewardManager;
import br.com.kodeway.daily.sql.SQLProvider;
import com.henryfabio.minecraft.inventoryapi.manager.InventoryManager;
import com.henryfabio.sqlprovider.connector.SQLConnector;
import com.henryfabio.sqlprovider.executor.SQLExecutor;
import lombok.Getter;
import me.bristermitten.pdm.PluginDependencyManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class DailyPlugin extends JavaPlugin {

    private SQLConnector sqlConnector;
    private SQLExecutor sqlExecutor;

    private InventoryRegistry inventoryRegistry;

    private final ProfileManager profileManager = new ProfileManager(this);
    private final RewardManager rewardManager = new RewardManager(this);

    @Override
    public void onLoad() {
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        PluginDependencyManager.of(this).loadAllDependencies().thenRun(() -> {
            try {
                sqlConnector = SQLProvider.of(this).setup();
                sqlExecutor = new SQLExecutor(sqlConnector);

                profileManager.init();
                rewardManager.init();

                ListenerRegistry.of(this).register();

                InventoryManager.enable(this);

                inventoryRegistry = InventoryRegistry.of(this);
                inventoryRegistry.register();

                CommandRegistry.of(this).register();

                getLogger().info("Plugin inicializado com sucesso!");

            } catch (Throwable t) {
                t.printStackTrace();
                getLogger().severe("Ocorreu um erro durante a inicialização do plugin!");
                Bukkit.getPluginManager().disablePlugin(this);
            }
        });
    }

    public static DailyPlugin getInstance() {
        return getPlugin(DailyPlugin.class);
    }

}
