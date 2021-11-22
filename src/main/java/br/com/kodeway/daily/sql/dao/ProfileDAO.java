package br.com.kodeway.daily.sql.dao;

import br.com.kodeway.daily.sql.dao.adapter.ProfileAdapter;
import br.com.kodeway.daily.util.MapHelper;
import com.henryfabio.sqlprovider.executor.SQLExecutor;
import br.com.kodeway.daily.model.Profile;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
public final class ProfileDAO {

    private final String TABLE = "profile_data";

    private final SQLExecutor sqlExecutor;

    public void createTable() {
        sqlExecutor.updateQuery("CREATE TABLE IF NOT EXISTS " + TABLE + "(" +
                "uniqueId CHAR(36) NOT NULL PRIMARY KEY," +
                "totalRedeemedRewards INTEGER," +
                "redeemedRewards TEXT" +
                ");"
        );
    }

    public Profile selectOne(UUID uniqueId) {
        return sqlExecutor.resultOneQuery(String.format("SELECT * FROM %s WHERE uniqueId = ?", TABLE),
                statement -> statement.set(1, uniqueId.toString()),
                ProfileAdapter.class
        );
    }

    public Set<Profile> selectAll() {
        return sqlExecutor.resultManyQuery(String.format("SELECT * FROM %s", TABLE),
                k -> {
                },
                ProfileAdapter.class
        );
    }

    public Set<Profile> selectAll(String query) {
        return sqlExecutor.resultManyQuery(String.format("SELECT * FROM %s %s", TABLE, query),
                k -> {
                },
                ProfileAdapter.class
        );
    }

    public void insertOne(Profile profile) {
        sqlExecutor.updateQuery(
                String.format("REPLACE INTO %s VALUES(?,?,?);", TABLE),
                statement -> {
                    statement.set(1, profile.getUniqueId().toString());
                    statement.set(2, String.valueOf(profile.getTotalRedeemedRewards()));
                    statement.set(3, MapHelper.toDatabase(profile.getRedeemedRewards()));
                }
        );
    }

    public void deleteOne(Profile profile) {
        sqlExecutor.updateQuery(String.format("DELETE FROM %s WHERE uniqueId = '%s'",
                TABLE,
                profile.getUniqueId().toString()
        ));
    }

}
