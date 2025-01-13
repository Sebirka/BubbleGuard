package org.sebirka.Listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.util.StringUtil;
import org.sebirka.BubbleGuard;
import org.sebirka.Utils.ColorUtil;

import static org.sebirka.BubbleGuard.send;


public class PiarAnalyzer implements Listener {

    private final FileConfiguration config;
    public static StringUtil stringUtil;

    public PiarAnalyzer( FileConfiguration config) {

        this.config = config;
        this.stringUtil = new StringUtil();
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage().toLowerCase();
        String[] args = command.split(" ");
        List<String> whitelist = this.config.getStringList("antipiar.whitelist");
        boolean isWhitelisted = false;

        for (String whitelistCommand : whitelist) {
            if (args[0].equalsIgnoreCase(whitelistCommand)) {
                isWhitelisted = true;
                break;
            }
        }

        String[] forbiddenWords = this.load();
        boolean containsForbiddenWords = false;

        StringBuilder formattedCommand = new StringBuilder();

        for (String arg : args) {
            String normalizedArg = BubbleGuard.getInstance().normalize(arg).toLowerCase();
            boolean isForbidden = false;

            for (String forbiddenWord : forbiddenWords) {
                String normalizedForbiddenWord = BubbleGuard.getInstance().normalize(forbiddenWord).toLowerCase();
                if (normalizedArg.contains(normalizedForbiddenWord)) {
                    isForbidden = true;
                    containsForbiddenWords = true;
                    break;
                }
            }

            if (isForbidden) {
                formattedCommand.append("&c").append(arg).append(" ");
            } else {
                formattedCommand.append("&7").append(arg).append(" ");
            }
        }

        if (!isWhitelisted && containsForbiddenWords) {
            List<String> messageParts = new ArrayList<>();
            String name = event.getPlayer().getName();
            String ip = event.getPlayer().getAddress().getAddress().getHostAddress();
            Player player = event.getPlayer();

            player.sendMessage(this.config.getString("antipiar.message-cmd", "§cВ вашей команде замечены запрещенные слова."));


            String notificationMessage = "&7[&6Protect&7] Игрок " + ChatColor.GOLD + name + ChatColor.GRAY +
                    " ввел запрещенную команду. \n&6Команда: " + formattedCommand.toString().trim();
            send(ColorUtil.setColor(notificationMessage));
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        String[] forbiddenWords = this.load();
        boolean containsForbiddenWords = false;
        String normalizedMessage = BubbleGuard.getInstance().normalize(message).toLowerCase();
        StringBuilder formattedMessage = new StringBuilder();

        String[] words = message.split("\\s+");
        for (String word : words) {
            String normalizedWord = BubbleGuard.getInstance().normalize(word).toLowerCase();
            boolean isForbidden = false;

            for (String forbiddenWord : forbiddenWords) {
                String normalizedForbiddenWord = BubbleGuard.getInstance().normalize(forbiddenWord).toLowerCase();
                if (normalizedWord.contains(normalizedForbiddenWord)) {
                    isForbidden = true;
                    containsForbiddenWords = true;
                    break;
                }
            }
            if (isForbidden) {
                formattedMessage.append("&c").append(word).append(" ");
            } else {
                formattedMessage.append("&7").append(word).append(" ");
            }
        }

        if (containsForbiddenWords) {
            Player player = event.getPlayer();
            player.sendMessage(this.config.getString("antipiar.message", "§cВ вашем сообщении замечены запрещенные слова."));
            List<String> messageParts = new ArrayList<>();
            String name = player.getName();
            String ip = player.getAddress().getAddress().getHostAddress();
            String command = this.config.getString("antipiar.command", "muteip {user} -s 30d Замечена попытка рекламы.").replace("{user}", player.getName());
            Bukkit.getScheduler().runTask(BubbleGuard.getInstance(), () -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command));

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.hasPermission("BubbleGuard.mod")) {
                    String notificationMessage = "&7[&6Protect&7] Игрок " + ChatColor.GOLD + name + ChatColor.GRAY +
                            " написал запрещенное слово. \n&7[&6Protect&7] &7Сообщение: " + formattedMessage.toString().trim();
                    onlinePlayer.sendMessage(ColorUtil.setColor(notificationMessage));

                    event.setCancelled(true);
                }
            }
        }
    }


    private String[] load() {
        String fws = this.config.getString("antipiar.words");
        return fws != null ? (String[])Arrays.stream(fws.split("\\|")).map(String::toLowerCase).toArray((x$0) -> new String[x$0]) : new String[0];
    }
}
