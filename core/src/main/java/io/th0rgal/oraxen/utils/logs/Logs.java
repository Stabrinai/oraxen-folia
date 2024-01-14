package io.th0rgal.oraxen.utils.logs;

import io.th0rgal.oraxen.OraxenPlugin;
import io.th0rgal.oraxen.config.Message;
import io.th0rgal.oraxen.config.Settings;
import io.th0rgal.oraxen.utils.AdventureUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;

public class Logs {

    private Logs() {}

    public static void logInfo(String message) {
        System.out.println("loginfo1");
        logInfo(message, false);
    }

    private static final TagResolver PrefixResolver = TagResolver.resolver("prefix", Tag.selfClosingInserting(AdventureUtils.MINI_MESSAGE_EMPTY.deserialize(Message.PREFIX.toString())));

    public static void logInfo(String message, boolean newline) {
        System.out.println("loginfo2");
        Component info = AdventureUtils.MINI_MESSAGE_EMPTY.deserialize("<prefix><#529ced>" + message + "</#529ced>", PrefixResolver);
        OraxenPlugin.get().getAudience().console().sendMessage(newline ? info.append(Component.newline()) : info);
    }

    public static void logSuccess(String message) {
        System.out.println("loginfo3");
        logSuccess(message, false);
    }

    public static void logSuccess(String message, boolean newline) {
        System.out.println("loginfo4");
        Component success = AdventureUtils.MINI_MESSAGE_EMPTY.deserialize("<prefix><#55ffa4>" + message + "</#55ffa4>", PrefixResolver);
        OraxenPlugin.get().getAudience().console().sendMessage(newline ? success.append(Component.newline()) : success);
    }

    public static void logError(String message) {
        System.out.println("loginfo5");
        logError(message, false);
    }

    public static void logError(String message, boolean newline) {
        System.out.println("loginfo6");
        Component error = AdventureUtils.MINI_MESSAGE_EMPTY.deserialize("<prefix><#e73f34>" + message + "</#e73f34>", PrefixResolver);
        OraxenPlugin.get().getAudience().console().sendMessage(newline ? error.append(Component.newline()) : error);
    }

    public static void logWarning(String message) {
        System.out.println("loginfo7");
        logWarning(message, false);
    }

    public static void logWarning(String message, boolean newline) {
        System.out.println("loginfo8");
        Component warning = AdventureUtils.MINI_MESSAGE_EMPTY.deserialize("<prefix><#f9f178>" + message + "</#f9f178>", PrefixResolver);
        OraxenPlugin.get().getAudience().console().sendMessage(newline ? warning.append(Component.newline()) : warning);
    }

    public static void newline() {
        System.out.println("loginfo9");
        OraxenPlugin.get().getAudience().console().sendMessage(Component.empty());
    }

    public static void debug(Object object) { if ( Settings.DEBUG.toBool()) Bukkit.broadcastMessage(String.valueOf(object)); }
    public static void debug(Object object, String prefix) { if ( Settings.DEBUG.toBool()) Bukkit.broadcastMessage(prefix + object); }
    public static <T> T debugVal(T object) { if ( Settings.DEBUG.toBool()) Bukkit.broadcastMessage(String.valueOf(object)); return object; }
    public static <T> T debugVal(T object, String prefix) { if ( Settings.DEBUG.toBool()) Bukkit.broadcastMessage(prefix + object); return object; }
    public static void debug(Component component) { if ( Settings.DEBUG.toBool()) OraxenPlugin.get().getAudience().console().sendMessage(component != null ? component : Component.text("null")); }

}
