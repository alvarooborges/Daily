package br.com.kodeway.daily.manager;

import br.com.kodeway.daily.storage.ProfileStorage;
import br.com.kodeway.daily.util.ColorUtil;
import br.com.kodeway.daily.DailyPlugin;
import br.com.kodeway.daily.model.Profile;
import br.com.kodeway.daily.model.Reward;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RequiredArgsConstructor
public final class ProfileManager {

    private final DailyPlugin plugin;

    @Getter
    private ProfileStorage profileStorage;

    public void init() {
        profileStorage = new ProfileStorage(plugin);
        profileStorage.init();
    }

    public void redeemReward(Profile profile, Reward reward) {
        List<String> commands = reward.getCommands();
        List<ItemStack> items = reward.getItems();

        Player player = Bukkit.getPlayer(profile.getUniqueId());

        if (commands != null) {
            for (String command : commands) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("$player", player.getName()));
            }
        }

        if (items != null) {
            for (ItemStack item : items) {
                player.getInventory().addItem(item);
            }
        }

        Instant now = Instant.now();

        profile.getRedeemedRewards().put(reward.getId(), now.plus(1, ChronoUnit.DAYS).toEpochMilli());
        player.sendMessage(ColorUtil.colored(String.format("&aVocê coletou a recompensa `%s`!", reward.getName())));
        profile.incrementTotalRedeemed();
    }

}
