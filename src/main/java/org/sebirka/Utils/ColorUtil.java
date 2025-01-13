package org.sebirka.Utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtil {
    public static String getColorForValue(double value, FileConfiguration config, String section) {
        Map<String, Object> colorRangesRaw = config.getConfigurationSection(section).getValues(false);
        Map<String, String> colorRanges = new HashMap<>();
        for (Map.Entry<String, Object> entry : colorRangesRaw.entrySet()) {
            colorRanges.put(entry.getKey(), entry.getValue().toString());
        }

        for (String range : colorRanges.keySet()) {
            if (range.contains(">")) {
                double threshold = Double.parseDouble(range.replace(">", ""));
                if (value > threshold) {
                    return colorRanges.get(range);
                }
            } else if (range.contains("<")) {
                String[] limits = range.split("<");
                double lowerLimit = Double.parseDouble(limits[0]);
                double upperLimit = Double.parseDouble(limits[1]);
                if (value >= lowerLimit && value <= upperLimit) {
                    return colorRanges.get(range);
                }
            }
        }
        return "#FFFFFF";
    }
    public static String setColor(String from) {
        Pattern pattern = Pattern.compile("&#[a-fA-F\0-9]{6}");
        Matcher matcher = pattern.matcher(from);
        while (matcher.find()) {
            String hexCode = from.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace("&#", "x").replace("#", "x");
            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder();
            for (char c : ch)
                builder.append("&").append(c);
            from = from.replace(hexCode, builder.toString());
            matcher = pattern.matcher(from);
        }
        return ChatColor.translateAlternateColorCodes('&', from);
    }

}