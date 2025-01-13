package org.sebirka.Listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class DupeAnalyzer implements Listener {

    private static FileConfiguration config;

    public DupeAnalyzer(FileConfiguration config, JavaPlugin plugin) {
        this.config = config;
        startInventoryCheckTask(plugin);
    }

    public static void checkInventories() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Inventory inventory = player.getInventory();
            check(inventory, player);
        }
    }

    public static void check(Inventory inventory, Player player) {
        Map<Material, Integer> maxAmounts = loadMaxAmounts();

        for (ItemStack item : inventory.getContents()) {
            if (item != null) {
                Material itemType = item.getType();
                int itemAmount = item.getAmount();
                String itemName = item.getItemMeta() != null ? item.getItemMeta().getDisplayName() : "";
                if (piar(itemName)) {
                    removeItem(inventory, item);
                    sendMessage(itemType, itemName, player.getName(), "Удалены запрещенные предметы.", 1);
                }

                if (maxAmounts.containsKey(itemType)) {
                    int maxAmount = maxAmounts.get(itemType);
                    if (itemAmount > maxAmount) {
                        remove(inventory, itemType, itemAmount - maxAmount);
                        sendMessage(itemType, itemName, player.getName(), "Удалены предметы, превышающие лимит.", itemAmount - maxAmount);
                    }
                }
            }
        }
    }

    private static Map<Material, Integer> loadMaxAmounts() {
        Map<Material, Integer> maxAmounts = new HashMap<>();

        for (String key : config.getConfigurationSection("anti-dupe.items").getKeys(false)) {
            Material material = Material.getMaterial(key);
            if (material != null) {
                int maxAmount = config.getInt("anti-dupe.items." + key);
                maxAmounts.put(material, maxAmount);
            }
        }

        return maxAmounts;
    }

    private static void removeItem(Inventory inventory, ItemStack item) {
        inventory.remove(item);
    }

    private static void remove(Inventory inventory, Material material, int excess) {
        for (ItemStack item : inventory.getContents()) {
            if (item != null && item.getType() == material) {
                if (item.getAmount() > excess) {
                    item.setAmount(item.getAmount() - excess);
                    break;
                }

                excess -= item.getAmount();
                inventory.remove(item);
            }

            if (excess <= 0) {
                break;
            }
        }
    }

    private static boolean piar(String itemName) {
        for (String word : config.getStringList("anti-dupe.words")) {
            if (itemName.toLowerCase().contains(word.toLowerCase())) {
                return true;
            }
        }

        return false;
    }

    private static void sendMessage(Material itemType, String itemName, String playerName, String action, int quantity) {
        List<String> messageParts = new ArrayList<>();
        messageParts.add(action);
        messageParts.add(" ");
        messageParts.add("Информация:");
        messageParts.add(" ■ Игрок: <tg-spoiler>" + playerName + "</tg-spoiler>");
        messageParts.add(" ■ Предмет: <tg-spoiler>" + itemType + "</tg-spoiler>");
        messageParts.add(" ■ Название: <tg-spoiler>" + itemName + "</tg-spoiler>");
        if (quantity > 0) {
            messageParts.add(" ■ Количество: <tg-spoiler>" + quantity + "</tg-spoiler>");
        }

        String message = String.join("\n", messageParts);
        // Логика отправки сообщения может быть добавлена здесь.
    }

    private void startInventoryCheckTask(JavaPlugin plugin) {
        new BukkitRunnable() {
            @Override
            public void run() {
                checkInventories();
            }
        }.runTaskTimer(plugin, 0L, 200L); // Запуск каждые 10 секунд (200 тиков)
    }
}
