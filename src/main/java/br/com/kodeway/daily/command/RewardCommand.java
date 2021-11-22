package br.com.kodeway.daily.command;

import br.com.kodeway.daily.DailyPlugin;
import br.com.kodeway.daily.inventory.RewardInventory;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public final class RewardCommand {

    private final DailyPlugin plugin;

    @Command(
            name = "rewards",
            aliases = {"recompensas", "recompensasdiarias", "daily", "dailyrewards"}
    )
    public void execution(Context<Player> context) {
        Player player = context.getSender();

        RewardInventory rewardInventory = plugin.getInventoryRegistry().getRewardInventory();
        rewardInventory.openInventory(player);
    }

}
