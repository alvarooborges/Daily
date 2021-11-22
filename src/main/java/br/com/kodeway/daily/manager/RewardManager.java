package br.com.kodeway.daily.manager;

import br.com.kodeway.daily.model.icon.RewardIcon;
import br.com.kodeway.daily.model.type.RewardType;
import br.com.kodeway.daily.util.ColorUtil;
import com.google.common.collect.Maps;
import br.com.kodeway.daily.DailyPlugin;
import br.com.kodeway.daily.model.Reward;
import br.com.kodeway.daily.util.item.ItemParser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public final class RewardManager {

    private final DailyPlugin plugin;

    @Getter private final Map<String, Reward> rewards = Maps.newLinkedHashMap();

    public void init() {
        ConfigurationSection rewardSection = plugin.getConfig().getConfigurationSection("rewards");

        for (String key : rewardSection.getKeys(false)) {

            ConfigurationSection itemSection = rewardSection.getConfigurationSection(key + ".items");

            List<String> commandList = rewardSection.getStringList(key + ".commands");

            Reward reward = Reward.builder()
                    .id(key)
                    .name(rewardSection.getString(key + ".name"))
                    .type(RewardType.valueOf(rewardSection.getString(key + ".type")))
                    .permission(rewardSection.getString(key + ".permission"))
                    .commands(commandList == null ? new ArrayList<>() : rewardSection.getStringList(key + ".commands"))
                    .items(itemSection == null ? new ArrayList<>() : new ItemParser().parseItemCollection(itemSection))
                    .rewardIcon(
                            RewardIcon.builder()
                                    .displayName(ColorUtil.colored(rewardSection.getString(key + ".icon.display-name")))
                                    .lore(ColorUtil.colored(rewardSection.getStringList(key + ".icon.lore")))
                                    .materialData(new MaterialData(
                                            Material.valueOf(rewardSection.getString(key + ".icon.material")),
                                            (byte) rewardSection.getInt(key + ".icon.data")))
                                    .inventorySlot(rewardSection.getInt(key + ".icon.inventory-slot"))
                                    .build()
                    )
                    .build();

            rewards.put(key, reward);

        }
    }

}
