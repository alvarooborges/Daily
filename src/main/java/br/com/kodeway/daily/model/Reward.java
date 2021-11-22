package br.com.kodeway.daily.model;

import br.com.kodeway.daily.model.icon.RewardIcon;
import br.com.kodeway.daily.model.type.RewardType;
import lombok.Builder;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Data
@Builder
public class Reward {

    private final String id;
    private final String name;

    private final RewardIcon rewardIcon;

    private final String permission;
    private final RewardType type;

    private final List<ItemStack> items;
    private final List<String> commands;


}
