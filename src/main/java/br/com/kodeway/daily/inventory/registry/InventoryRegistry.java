package br.com.kodeway.daily.inventory.registry;

import br.com.kodeway.daily.DailyPlugin;
import br.com.kodeway.daily.inventory.RewardInventory;
import lombok.Data;

@Data(staticConstructor = "of")
public final class InventoryRegistry {

    private final DailyPlugin plugin;

    private RewardInventory rewardInventory;

    public void register() {
        this.rewardInventory = new RewardInventory(
                plugin.getProfileManager(),
                plugin.getRewardManager()
        ).init();
    }

}
