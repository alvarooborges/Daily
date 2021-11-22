package br.com.kodeway.daily.listener;

import com.google.common.collect.Maps;
import br.com.kodeway.daily.model.Profile;
import br.com.kodeway.daily.storage.ProfileStorage;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@RequiredArgsConstructor
public final class UserConnectListener implements Listener {

    protected final ProfileStorage profileStorage;

    @EventHandler
    public void onConnect(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        Profile profile = profileStorage.getFromDatabase(player.getUniqueId());

        if (profile == null) {
            profileStorage.storageNew(Profile.builder()
                    .uniqueId(player.getUniqueId())
                    .redeemedRewards(Maps.newLinkedHashMap())
                    .totalRedeemedRewards(0)
                    .build()
            );
        } else {
            profileStorage.addToMemory(profile);
        }
    }

}
