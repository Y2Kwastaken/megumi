package sh.miles.megumi.core.chat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import sh.miles.megumi.core.chat.style.ModifierType;
import sh.miles.megumi.core.chat.style.patterns.ChatStylePattern;
import sh.miles.megumi.core.chat.style.patterns.GradientPattern;
import sh.miles.megumi.core.chat.style.patterns.HexColorPattern;
import sh.miles.megumi.core.chat.style.patterns.ModifierPattern;
import sh.miles.megumi.core.chat.style.patterns.WordColorPattern;

@UtilityClass
public class ChatUtil {

    private static final ChatStylePattern[] COLOR_PATTERNS = new ChatStylePattern[] {
            new HexColorPattern(),
            new GradientPattern(),
            new WordColorPattern(),
            new ModifierPattern(),
    };

    public static String bukkitColor(@NonNull final String message) {
        return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', message);
    }

    public static net.md_5.bungee.api.ChatColor getColorFromHex(@NonNull final String color) {
        return net.md_5.bungee.api.ChatColor.of(new Color(Integer.parseInt(color, 16)));
    }

    public static String style(@NonNull final String message) {
        String processedMessage = message;
        for (final ChatStylePattern pattern : COLOR_PATTERNS) {
            processedMessage = pattern.process(processedMessage);
        }
        return processedMessage;
    }

    public static List<String> style(@NonNull final List<String> messages) {
        final List<String> processedMessages = new ArrayList<>();
        for (final String message : messages) {
            processedMessages.add(style(message));
        }
        return processedMessages;
    }

    public static net.md_5.bungee.api.ChatColor[] gradient(String color0, String color1, int size) {
        net.md_5.bungee.api.ChatColor[] colors = new net.md_5.bungee.api.ChatColor[size];

        Color c0 = getColorFromHex(color0).getColor();
        Color c1 = getColorFromHex(color1).getColor();

        int r = Math.abs(c0.getRed() - c1.getRed()) / (size - 1);
        int g = Math.abs(c0.getGreen() - c1.getGreen()) / (size - 1);
        int b = Math.abs(c0.getBlue() - c1.getBlue()) / (size - 1);

        for (int i = 0; i < size; i++) {
            int red = c0.getRed() < c1.getRed() ? c0.getRed() + (r * i) : c0.getRed() - (r * i);
            int green = c0.getGreen() < c1.getGreen() ? c0.getGreen() + (g * i) : c0.getGreen() - (g * i);
            int blue = c0.getBlue() < c1.getBlue() ? c0.getBlue() + (b * i) : c0.getBlue() - (b * i);

            colors[i] = net.md_5.bungee.api.ChatColor.of(new Color(red, green, blue));
        }

        return colors;
    }

    public static String applyColorArray(String message, net.md_5.bungee.api.ChatColor[] colors) {
        StringBuilder builder = new StringBuilder();
        final List<ModifierType> modifiers = new ArrayList<>();
        for (int i = 0; i < message.length(); i++) {
            final char c = message.charAt(i);

            // Checks for already parsed color codes
            if (c == '&' || c == '§') {
                // pushes the index forward to skip the color code that follows
                i++;
                continue;
            }

            // Checks for unparsed color codes
            final int endIndex = message.indexOf('>', i);
            if (c == '<' && endIndex != -1) {
                final String format = message.substring(i, endIndex + 1);
                i = endIndex;
                // Special case for modifiers
                if (format.contains("mod")) {
                    modifiers.add(ModifierType.fromString(format.split(":")[1].replace(">", "")));
                    continue;
                } else {
                    final int nextFormatIndex = message.indexOf('<', i);
                    final int endOfFormatIndex = nextFormatIndex == -1 ? message.length() : nextFormatIndex;
                    final String text = message.substring(i + 1, endOfFormatIndex);
                    i = endOfFormatIndex - 1;
                    builder.append(format).append(text);
                    continue;
                }
            }
            builder.append(colors[i]);
            for (ModifierType modifier : modifiers) {
                builder.append(modifier.getModifier());
            }
            builder.append(c);
        }
        return style(builder.toString());
    }

    public static String applyModifier(String message, ModifierType modifier) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            final char c = message.charAt(i);
            builder.append(modifier.getModifier()).append(c);
        }
        return builder.toString();
    }

}
