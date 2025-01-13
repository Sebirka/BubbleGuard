package org.sebirka.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.NotNull;
import org.sebirka.Utils.ColorUtil;

import static org.sebirka.BubbleGuard.send;
import static org.sebirka.Utils.MsptMonitor.isRedstoneEnabled;
import static org.sebirka.Commands.MobCommand.removeAllMobs;

public class PhyCommand implements CommandExecutor {
    private final String togglePermission = "BubbleGuard.physic";
    public static Boolean offphy = false ;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof ConsoleCommandSender || sender.hasPermission(togglePermission)) {
            if (command.getName().equalsIgnoreCase("physic")) {
                if (args.length == 0) {
                    sender.sendMessage(ColorUtil.setColor("&8(&6SC&8) &8» &7Команды для управления физикой:"));
                    sender.sendMessage(ColorUtil.setColor("&8(&6SC&8) &8» &7/physic off - Выключить физику"));
                    sender.sendMessage(ColorUtil.setColor("&8(&6SC&8) &8» &7/physic on - Включить физику"));
                    sender.sendMessage(ColorUtil.setColor("&8(&6SC&8) &8» &7Физика: " + (isRedstoneEnabled ? "&aВключена" : "&cВыключена")));
                }
                if (args.length >= 1) {
                    if (args[0].equalsIgnoreCase("off")) {
                        isRedstoneEnabled = false;
                        send(ColorUtil.setColor("&8(&6SC&8) &6grief-1 &6" + sender.getName() + " &8» &7Физика выключена"));
                        return true;
                    } else if (args[0].equalsIgnoreCase("on")) {
                        isRedstoneEnabled = true;
                        send(ColorUtil.setColor("&8(&6SC&8) &6grief-1 &6" + sender.getName() + " &8» &7Физика включена"));
                    }
                }
            }
        }
        return false;
    }
}
