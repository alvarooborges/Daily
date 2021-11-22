package br.com.kodeway.daily.listener;

import br.com.kodeway.daily.model.Profile;
import br.com.kodeway.daily.storage.ProfileStorage;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public final class UserDisconnectListener implements Listener {

    protected final ProfileStorage profileStorage;

    @EventHandler
    public void onDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Profile profile = profileStorage.get(player.getUniqueId());

        if (profile != null) {
            profileStorage.saveAndRemove(profile);
        }
    }


}
