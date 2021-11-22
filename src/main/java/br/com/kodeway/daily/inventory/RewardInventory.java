package br.com.kodeway.daily.inventory;

import br.com.kodeway.daily.manager.ProfileManager;
import br.com.kodeway.daily.manager.RewardManager;
import br.com.kodeway.daily.model.Profile;
import br.com.kodeway.daily.model.Reward;
import br.com.kodeway.daily.model.type.RewardType;
import br.com.kodeway.daily.util.ColorUtil;
import br.com.kodeway.daily.util.item.ItemBuilder;
import br.com.kodeway.daily.util.time.TimeUtil;
import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.simple.SimpleInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

public final class RewardInventory extends SimpleInventory {

    private final ProfileManager profileManager;
    private final RewardManager rewardManager;

    public RewardInventory(ProfileManager profileManager, RewardManager rewardManager) {
        super(
                "daily.inventory.id",
                "Recompensas diárias",
                6 * 9
        );

        this.profileManager = profileManager;
        this.rewardManager = rewardManager;
    }

    @Override
    protected void configureInventory(Viewer viewer, InventoryEditor editor) {

        Profile profile = profileManager.getProfileStorage().get(viewer.getPlayer().getUniqueId());

        if (profile == null) return;

        editor.setItem(4, InventoryItem.of(
                new ItemBuilder(Bukkit.getPlayer(profile.getUniqueId()).getName())
                        .name(ColorUtil.colored("&eInformações"))
                        .setDurability((short) 3)
                        .lore(ColorUtil.colored(
                                "",
                                " &7Sistema de recompensas diárias, entre todos",
                                " &7os dias e colete suas recompensas.",
                                "",
                                String.format("&8Você já coletou &6%s&8 recompensas.", profile.getTotalRedeemedRewards())
                        ))
                        .addItemFlags(ItemFlag.values())
                        .result()
        ));

        for (Reward reward : rewardManager.getRewards().values()) {

            InventoryItem inventoryItem;

            if (profile.getRedeemedRewards().get(reward.getId()) != null
                    && !(System.currentTimeMillis() >= profile.getRedeemedRewards().get(reward.getId()))
            ) {
                inventoryItem = InventoryItem.of(new ItemBuilder(reward.getType() == RewardType.VIP
                        ? Material.MINECART
                        : reward.getRewardIcon().getItemStack().getType()
                )
                        .name(reward.getRewardIcon().getDisplayName())
                        .lore(ColorUtil.colored("", "&cRecompensa já coletada."))
                        .addItemFlags(ItemFlag.values())
                        .result());
            } else {
                inventoryItem = InventoryItem.of(reward.getRewardIcon().getItemStack());
            }

            editor.setItem(
                    reward.getRewardIcon().getInventorySlot(),
                    inventoryItem.defaultCallback(callBack -> {
                        Player player = callBack.getPlayer();

                        if (player.hasPermission(reward.getPermission())) {

                            if (profile.getRedeemedRewards().containsKey(reward.getId())) {

                                long redeemedTime = profile.getRedeemedRewards().get(reward.getId());

                                if (System.currentTimeMillis() >= redeemedTime) {
                                    profileManager.redeemReward(profile, reward);
                                } else {
                                    player.sendMessage(
                                            ColorUtil.colored(
                                                    String.format("&cAguarde para coletar esta recompensa novamente! (%s)",
                                                            TimeUtil.formatTime(redeemedTime - System.currentTimeMillis()))
                                            )
                                    );
                                }

                            } else {
                                profileManager.redeemReward(profile, reward);
                            }

                        } else {
                            player.sendMessage(ColorUtil.colored("&cVocê não possui permissão para resgatar esta recompensa!"));
                        }
                        player.closeInventory();
                    })
            );

        }

    }

}
