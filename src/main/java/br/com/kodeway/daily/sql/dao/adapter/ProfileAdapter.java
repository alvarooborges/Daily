package br.com.kodeway.daily.sql.dao.adapter;

import br.com.kodeway.daily.util.MapHelper;
import com.henryfabio.sqlprovider.executor.adapter.SQLResultAdapter;
import com.henryfabio.sqlprovider.executor.result.SimpleResultSet;
import br.com.kodeway.daily.model.Profile;

import java.util.UUID;

public final class ProfileAdapter implements SQLResultAdapter<Profile> {

    @Override
    public Profile adaptResult(SimpleResultSet resultSet) {

        int totalRedeemedRewards = resultSet.get("totalRedeemedRewards");

        return Profile.builder()
                .uniqueId(UUID.fromString(resultSet.get("uniqueId")))
                .totalRedeemedRewards(totalRedeemedRewards)
                .redeemedRewards(MapHelper.fromDatabase(resultSet.get("redeemedRewards")))
                .build();
    }

}
