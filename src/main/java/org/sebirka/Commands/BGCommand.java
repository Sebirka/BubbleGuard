package org.sebirka.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.sebirka.BubbleGuard;
import org.sebirka.Utils.ColorUtil;
import org.sebirka.Utils.MsptMonitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.sebirka.Commands.MobCommand.mobLimit;
import static org.sebirka.Commands.MobCommand.mobSpawningEnabled;
import static org.sebirka.Utils.MsptMonitor.isRedstoneEnabled;

public class BGCommand implements CommandExecutor, TabCompleter {

    private final BubbleGuard plugin;

    public BGCommand(BubbleGuard plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§cИспользуйте: /bubbleguard <reload|stats>");
            return false;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("BubbleGuard.admin")) {
                sender.sendMessage(ChatColor.RED + "У вас нет прав для выполнения этой команды.");
                return true;
            }

            plugin.reloadConfig();
            String message = ChatColor.GREEN + "Конфигурация успешно перезагружена.";

            if (sender instanceof Player) {
                sender.sendMessage(message);
            } else {
                plugin.getLogger().info(message);
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("stats")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!player.hasPermission("BubbleGuard.admin")) {
                    player.sendMessage("§cУ вас нет прав для использования этой команды.");
                    return true;
                }
            }


            double currentMspt = MsptMonitor.getServerMspt();

            String tpsMessage = String.format("§8[§6BubbleGuard§8] 8» §6Текущий TPS: §f%.2f", getServerTps());
            String msptMessage = String.format("§8[§6BubbleGuard§8] 8» §6Текущий MSPT: §f%.2f", currentMspt);

            sender.sendMessage("§8[§6BubbleGuard§8] §7Серверная статистика:");
            sender.sendMessage(tpsMessage);
            sender.sendMessage(msptMessage);
            sender.sendMessage(ColorUtil.setColor("§8[§6BubbleGuard§8] &8» &7Физика: " + (isRedstoneEnabled ? "&aВключена" : "&cВыключена")));
            sender.sendMessage(ColorUtil.setColor("§8[§6BubbleGuard§8] &8» &7AI у мобов: " + (mobSpawningEnabled ? "&aВключено" : "&cОтключено")));
            sender.sendMessage(ColorUtil.setColor("§8[§6BubbleGuard§8] &8» &7Лимит мобов: " + (mobLimit > 5000 ? "&cВыключено" : mobLimit)));
            return true;
        }

        sender.sendMessage("§cНеизвестная команда. Используйте: /bubbleguard <reload|stats>");
        return false;
    }
    public static double getServerTps() {
        double[] tps = Bukkit.getServer().getTPS();
        return tps[0];
    }
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("bubbleguard") || command.getName().equalsIgnoreCase("bg")) {
            List<String> completions = new ArrayList<>();
                completions.add("reload");
                completions.add("stats");

        }
        return Collections.emptyList();
    }
}
