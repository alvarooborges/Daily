package br.com.kodeway.daily.sql;

import com.henryfabio.sqlprovider.connector.SQLConnector;
import com.henryfabio.sqlprovider.connector.type.impl.MySQLDatabaseType;
import com.henryfabio.sqlprovider.connector.type.impl.SQLiteDatabaseType;
import br.com.kodeway.daily.DailyPlugin;
import lombok.Data;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;

@Data(staticConstructor = "of")
public final class SQLProvider {

    private final DailyPlugin plugin;

    public SQLConnector setup() {
        ConfigurationSection database = plugin.getConfig().getConfigurationSection("database");

        String type = database.getString("type");

        SQLConnector sqlConnector;

        if (type.equalsIgnoreCase("mysql")) {
            ConfigurationSection mysql = database.getConfigurationSection("mysql");

            sqlConnector = MySQLDatabaseType.builder()
                    .address(mysql.getString("address"))
                    .username(mysql.getString("username"))
                    .password(mysql.getString("password"))
                    .database(mysql.getString("database"))
                    .build()
                    .connect();

        } else if (type.equalsIgnoreCase("sqlite")) {
            ConfigurationSection sqlite = database.getConfigurationSection("sqlite");

            sqlConnector = SQLiteDatabaseType.builder()
                    .file(new File(plugin.getDataFolder(), sqlite.getString("file")))
                    .build()
                    .connect();

        } else {

            throw new IllegalArgumentException(
                    String.format("O tipo de database seleciona não é válido. (%s)", type)
            );

        }

        return sqlConnector;
    }

}
