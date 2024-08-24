package jefry.plugin.cityBuilderPlugin.libraries;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class ColorUtils {
    public static String RED = "ffadad";
    public static String ORANGE = "ffd6a5";
    public static String YELLOW = "fdffb6";
    public static String GREEN = "caffbf";
    public static String CYAN = "9bf6ff";
    public static String BLUE = "a0c4ff";
    public static String PURPLE = "bdb2ff";
    public static String PINK = "ffc6ff";

    public static String colorify(String string) {
        return string.replace("&", "§");
    }

    public static List<String> colorify(List<String> str) {
        List<String> string = new ArrayList<>();
        for (String s : str) {
            string.add(s.replace("&", "§"));
        }

        return string;
    }

    public static String decolorify(String str) {
        return ChatColor.stripColor(str);
    }

    public static List<String> decolorify(@Nullable List<String> str) {
        if (str == null) return null;
        List<String> string = new ArrayList<>();
        for (String s : str) {
            string.add(decolorify(s));
        }

        return string;
    }

    public static String hex(String color) {
        if (color.startsWith("#")) color = color.substring(1);
        if (color.length() == 3) {
            color = "" + color.charAt(0) + color.charAt(0)
                + color.charAt(1) + color.charAt(1)
                + color.charAt(2) + color.charAt(2);
        }

        String[] chars = color.split("");
        StringBuilder builder = new StringBuilder("&x");

        for (String character : chars) {
            builder.append("&").append(character);
        }

        return builder.toString();
    }

    public static Color getColorByName(String string) {
        return switch (string.toUpperCase()) {
            case "RED" -> Color.RED;
            case "BLACK" -> Color.BLACK;
            case "BLUE" -> Color.BLUE;
            case "PURPLE" -> Color.PURPLE;
            case "AQUA" -> Color.AQUA;
            case "FUCHSIA" -> Color.FUCHSIA;
            case "GRAY" -> Color.GRAY;
            case "GREEN" -> Color.GREEN;
            case "LIME" -> Color.LIME;
            case "MAROON" -> Color.MAROON;
            case "NAVY" -> Color.NAVY;
            case "OLIVE" -> Color.OLIVE;
            case "ORANGE" -> Color.ORANGE;
            case "SILVER" -> Color.SILVER;
            case "TEAL" -> Color.TEAL;
            case "WHITE" -> Color.WHITE;
            case "YELLOW" -> Color.YELLOW;
            default -> null;
        };
    }
}
