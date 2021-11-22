package br.com.kodeway.daily.model.icon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.List;

@Builder
@Getter
@RequiredArgsConstructor
public class RewardIcon {

    private final MaterialData materialData;
    private final String displayName;
    private final List<String> lore;
    private final int inventorySlot;
    private ItemStack itemStack;


}
