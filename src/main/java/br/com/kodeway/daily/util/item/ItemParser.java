package br.com.kodeway.daily.util.item;

import br.com.kodeway.daily.util.ColorUtil;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemParser {

    public List<ItemStack> parseItemCollection(ConfigurationSection section) {

        List<ItemStack> items = new ArrayList<>();
        for (String key : section.getKeys(false)) {

            items.add(
                    parseItemSection(section.getConfigurationSection(key))
            );

        }

        return items;
    }

    public ItemStack parseItemSection(ConfigurationSection section) {

        try {

            ItemBuilder itemBuilder;

            if (section.contains("head")) itemBuilder = new ItemBuilder(section.getString("head"));

            else {

                String material = section.getString("material");

                itemBuilder = new ItemBuilder(
                        Material.valueOf(material),
                        1,
                        section.contains("data") ? (short) section.getInt("data") : 0
                );
            }

            if (section.contains("amount")) itemBuilder.setAmount(section.getInt("amount"));

            if (section.contains("glow") && section.getBoolean("glow")) itemBuilder.glow();

            if (section.contains("unbreakable") && section.getBoolean("unbreakable")) itemBuilder.setUnbreakable(true);

            if (section.contains("hide-flags") && section.getBoolean("hide-flags"))
                itemBuilder.addItemFlags(ItemFlag.values());

            if (section.contains("display-name"))
                itemBuilder.name(ColorUtil.colored(section.getString("display-name")));

            if (section.contains("lore")) {

                final List<String> lore = new ArrayList<>();
                for (String description : section.getStringList("lore")) {
                    lore.add(ColorUtil.colored(description));
                }

                itemBuilder.lore(lore);
            }

            if (section.contains("enchantments")) {

                for (String enchantments : section.getStringList("enchantments")) {

                    String[] split = enchantments.split(":");

                    int id = Integer.parseInt(split[0]);
                    int level = Integer.parseInt(split[1]);

                    itemBuilder.addEnchantment(Enchantment.getById(id), level, true);

                }

            }

            return itemBuilder.result();

        } catch (Throwable throwable) {

            throwable.printStackTrace();
            return null;

        }
    }
}
