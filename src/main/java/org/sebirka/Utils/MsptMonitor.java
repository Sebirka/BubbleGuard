//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.sebirka.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.sebirka.Commands.MobCommand;

import static org.sebirka.Commands.PhyCommand.offphy;

public class MsptMonitor {
    public static boolean isRedstoneEnabled = true;
    public static JavaPlugin plugin = null;
    public static boolean hasNotified = false;
    public static long lastMessageTime = 0L;
    public static long lowMsptStartTime = -1L;

    public MsptMonitor(JavaPlugin plugin) {
        MsptMonitor.plugin = plugin;
    }

    public static void MsptCheck() {
        long checkIntervalTicks = plugin.getConfig().getLong("checkIntervalTicks", 60L);
        new BukkitRunnable() {
            @Override
            public void run() {
                double currentMspt = getServerMspt();
                long currentTime = System.currentTimeMillis();
                long messageCooldown = plugin.getConfig().getLong("messageCooldown", 20000L);

                List<String> actionsPerformed = new ArrayList<>();
                List<String> hoverMessages = new ArrayList<>();

                checkMsptThresholds(currentMspt, actionsPerformed, hoverMessages);


                if (!actionsPerformed.isEmpty() && shouldNotify(currentTime, messageCooldown)) {
                    logAndNotify(currentMspt, String.join("\n", hoverMessages));
                }

                handleLowMspt(currentMspt, currentTime);

            }
        }.runTaskTimer(plugin, 0L, checkIntervalTicks);

    }

    public static double getServerMspt() {
        return Bukkit.getServer().getAverageTickTime();
    }

    private static void checkMsptThresholds(double currentMspt, List<String> actionsPerformed, List<String> hoverMessages) {
        ConfigurationSection msptThresholds = plugin.getConfig().getConfigurationSection("msptThresholds");
        if (msptThresholds != null) {
            for(String key : msptThresholds.getKeys(false)) {
                double threshold = Double.parseDouble(key);
                Map<String, Object> actionInfo = msptThresholds.getConfigurationSection(key).getValues(false);
                if (currentMspt > threshold) {
                    String action = (String)actionInfo.get("action");
                    String hoverMessage = (String)actionInfo.get("hoverMessage");
                    performAction(action);
                    actionsPerformed.add(action);
                    hoverMessages.add(hoverMessage);
                }
            }

        }
    }

    private static boolean shouldNotify(long currentTime, long messageCooldown) {
        return !hasNotified || currentTime - lastMessageTime >= messageCooldown;
    }

    private static void handleLowMspt(double currentMspt, long currentTime) {
        if (currentMspt <= (double)15.0F) {
            if (lowMsptStartTime == -1L) {
                lowMsptStartTime = currentTime;
            } else if (currentTime - lowMsptStartTime >= 60000L) {
                if (!isRedstoneEnabled) {
                    enablePhysics();
                }

                lowMsptStartTime = -1L;
            }
        } else {
            lowMsptStartTime = -1L;
        }

        hasNotified = false;
    }

    private static void performAction(String action) {
        switch (action.toLowerCase()) {
            case "remove_mobs":
                MobCommand.removeAllMobs();
                break;
            case "disable_physics":
                isRedstoneEnabled = false;
        }

    }

    private static void enablePhysics() {
        if (offphy) {
            return;
    }
        isRedstoneEnabled = true;
        String message = plugin.getConfig().getString("messages.phyenable","Физика включена");
        logAndNotify(getServerMspt(), message);
    }

    private static void logAndNotify(double currentMspt, String hoverMessage) {
        String message = plugin.getConfig().getString("messages.msptMessage", "&8(&6SC&8) &6grief-1 Server &8» &7>%.2f mspt");
        plugin.getLogger().info(String.format(message, currentMspt));
        String permission = plugin.getConfig().getString("permissions.notify", "BubbleGuard.alert");

        for(Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission(permission)) {
                TextComponent textComponent = new TextComponent(ChatColor.translateAlternateColorCodes('&', String.format(message, currentMspt)));
                textComponent.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, (new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', hoverMessage))).create()));
                player.spigot().sendMessage(textComponent);
            }
        }

        lastMessageTime = System.currentTimeMillis();
        hasNotified = true;
    }
}
