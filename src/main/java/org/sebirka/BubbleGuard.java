package org.sebirka;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import org.sebirka.Commands.BGCommand;
import org.sebirka.Commands.MobCommand;

import org.sebirka.Commands.PhyCommand;
import org.sebirka.Listeners.DupeAnalyzer;
import org.sebirka.Listeners.Gamemode;
import org.sebirka.Listeners.Phy;
import org.sebirka.Listeners.PiarAnalyzer;
import org.sebirka.Utils.Metrics.Metrics;
import org.sebirka.Utils.MsptMonitor;

import static org.sebirka.Utils.StringUtil.replacements;


public class BubbleGuard extends JavaPlugin implements Listener  {
    private static final String notifyPermission = "BubbleGuard.alert";
    private static BubbleGuard instance;



    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        this.getCommand("bubbleguard").setExecutor(new BGCommand(this));
        // Инициализация AlertManager и запуск всех необходимых задач

        registerListeners();
        getLogger().info("BubbleGuard включен.");
    }

    @Override
    public void onDisable() {
        getLogger().info("BubbleGuard выключен.");
    }

    private void registerListeners() {
        Metrics metrics = new Metrics(this, 24422);
        Bukkit.getPluginManager().registerEvents(new Gamemode(), this);
        Bukkit.getPluginManager().registerEvents(new Phy(), this);
        Bukkit.getPluginManager().registerEvents(new MobCommand(this), this);
        Bukkit.getPluginManager().registerEvents(new PiarAnalyzer(getConfig()), this);
        new DupeAnalyzer(getConfig(), this);

        getCommand("mobspawn").setExecutor(new MobCommand(this));
        getCommand("physic").setExecutor(new PhyCommand());
        getCommand("mobspawn").setTabCompleter(new MobCommand(this));
        MsptMonitor monitor = new MsptMonitor(this);
        monitor.MsptCheck();

    }
    public static void send(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission(notifyPermission)) {
                player.sendMessage(message);
            }
        }
    }
    public static BubbleGuard getInstance() {
        return instance;
    }
    public String normalize(String input) {
        StringBuilder normalized = new StringBuilder();

        for(char c : input.toCharArray()) {
            normalized.append((String)replacements.getOrDefault(String.valueOf(c), String.valueOf(c)));
        }

        return normalized.toString();
    }
}
