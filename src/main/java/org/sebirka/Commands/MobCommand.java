package org.sebirka.Commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.sebirka.Utils.ColorUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.sebirka.BubbleGuard.send;

public class MobCommand implements CommandExecutor, Listener, TabCompleter {
    public static boolean mobSpawningEnabled = true;
    public static int mobLimit = Integer.MAX_VALUE;

    private final String togglePermission = "BubbleGuard.mob";
    private final JavaPlugin plugin;

    public MobCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        startMobCheckTask();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof ConsoleCommandSender || sender.hasPermission(togglePermission)) {
            if (command.getName().equalsIgnoreCase("mobspawn")) {
                if (args.length == 0) {
                    sender.sendMessage(ColorUtil.setColor("&8(&6SC&8) &8» &7Команды для управления мобами:"));
                    sender.sendMessage(ColorUtil.setColor("&8(&6SC&8) &8» &7/mobspawn off - Отключить AI у мобов"));
                    sender.sendMessage(ColorUtil.setColor("&8(&6SC&8) &8» &7/mobspawn on - Включить AI у мобов"));
                    sender.sendMessage(ColorUtil.setColor("&8(&6SC&8) &8» &7/mobspawn limit <число> - Установить лимит мобов"));
                    sender.sendMessage(ColorUtil.setColor("&8(&6SC&8) &8» &7AI у мобов: " + (mobSpawningEnabled ? "&aВключено" : "&cОтключено")));
                    sender.sendMessage(ColorUtil.setColor("&8(&6SC&8) &8» &7Лимит мобов: " + (mobLimit > 5000 ? "&cВыключено" : mobLimit)));
                }

                if (args.length >= 1) {
                    if (args[0].equalsIgnoreCase("off")) {
                        mobSpawningEnabled = false;
                        send(ColorUtil.setColor("&8(&6SC&8) &6grief-1 &6" + sender.getName() + " &8» &7У мобов было выключено AI"));
                        return true;
                    } else if (args[0].equalsIgnoreCase("on")) {
                        mobSpawningEnabled = true;
                        send(ColorUtil.setColor("&8(&6SC&8) &6grief-1 &6" + sender.getName() + " &8» &7У мобов было включено AI"));
                        return true;
                    } else if (args[0].equalsIgnoreCase("limit") && args.length == 2) {
                        try {
                            mobLimit = Integer.parseInt(args[1]);
                            send(ColorUtil.setColor("&8(&6SC&8) &6grief-1 &6" + sender.getName() + " &8» &7Лимит мобов был изменён на " + mobLimit));
                        } catch (NumberFormatException e) {
                            sender.sendMessage("Неверное число. Используйте: /mobspawn limit <число>");
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
                entity.setAI(false);
        }
    }
    private void startMobCheckTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (mobSpawningEnabled) {
                    int currentMobCount = getMobCount();
                    if (currentMobCount >= mobLimit) {
                        removeAllMobs();
                        send(ColorUtil.setColor("&8(&6SC&8) &6grief-1 Server &8» &7Количество мобов превысило лимит: удалены лишние мобы."));
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 100L);
    }

    public static void removeAllMobs() {
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getType() != EntityType.PLAYER && entity.getType().isAlive()) {
                    entity.remove();
                }
            }
        }
    }

    private int getMobCount() {
        int count = 0;
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getType() != EntityType.PLAYER && entity.getType().isAlive()) {
                    count++;
                }
            }
        }
        return count;
    }



    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("mobspawn")) {
            List<String> completions = new ArrayList<>();
            if (sender.hasPermission(togglePermission)) {
                completions.add("on");
                completions.add("off");
                completions.add("limit");
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("limit")) {
            // В случае команды /mobspawn limit возвращаем возможные значения для лимита
            List<String> limitSuggestions = new ArrayList<>();
            limitSuggestions.add("10");
            limitSuggestions.add("50");
            limitSuggestions.add("100");
            limitSuggestions.add("150");
            limitSuggestions.add("200");
            return limitSuggestions;
        }
        return Collections.emptyList();
    }
}
