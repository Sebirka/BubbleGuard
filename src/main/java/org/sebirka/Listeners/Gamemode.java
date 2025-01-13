package org.sebirka.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permission;
import org.sebirka.Utils.ColorUtil;

import static org.bukkit.ChatColor.*;

public class Gamemode implements Listener {
    public static final String NOTIFY_PERMISSION = "BubbleGuard.alert";

    @EventHandler
    public void Join(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.SURVIVAL) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.hasPermission(Gamemode.NOTIFY_PERMISSION)) {
                    onlinePlayer.sendMessage(ColorUtil.setColor("&7[&6Protect&7] " + GOLD + player.getName() + GRAY + " вошел с режимом игры " + player.getGameMode()));
                }
            }
            player.setGameMode(GameMode.SURVIVAL);
        }
    }



    @EventHandler
    public void creat(PlayerGameModeChangeEvent event) {
        if (event.getNewGameMode() == GameMode.CREATIVE) {
            Player player = event.getPlayer();

            String message = "включил гм 1.";
            String chatMessage =ColorUtil.setColor(  "&7[&6Protect&7] " + "Игрок " + GOLD + player.getName() + GRAY + " " + message);
            Bukkit.getOnlinePlayers().stream()
                    .filter(p -> p.hasPermission(Gamemode.NOTIFY_PERMISSION))
                    .forEach(p -> p.sendMessage(chatMessage));
        }
    }




    @EventHandler
    public void sur(PlayerGameModeChangeEvent event) {
        if (event.getNewGameMode() == GameMode.SURVIVAL) {
            Player player = event.getPlayer();
            String message = "включил гм 0.";
            String chatMessage =ColorUtil.setColor(  "&7[&6Protect&7] " + "Игрок " + GOLD + player.getName() + GRAY + " " + message);
            Bukkit.getOnlinePlayers().stream()
                    .filter(p -> p.hasPermission(Gamemode.NOTIFY_PERMISSION))
                    .forEach(p -> p.sendMessage(chatMessage));
        }
    }


    @EventHandler
    public void spec(PlayerGameModeChangeEvent event) {
        if (event.getNewGameMode() == GameMode.SPECTATOR) {
            Player player = event.getPlayer();
            String message = "включил гм 3.";
            String chatMessage = ColorUtil.setColor("&7[&6Protect&7] " + "Игрок " + GOLD + player.getName() + GRAY + " " + message);
            Bukkit.getOnlinePlayers().stream()
                    .filter(p -> p.hasPermission(Gamemode.NOTIFY_PERMISSION))
                    .forEach(p -> p.sendMessage(chatMessage));
        }
    }
}
