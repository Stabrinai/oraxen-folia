package io.th0rgal.oraxen.pack.dispatch;

import io.th0rgal.oraxen.OraxenPlugin;
import io.th0rgal.oraxen.config.Settings;
import io.th0rgal.oraxen.pack.upload.hosts.HostingProvider;
import io.th0rgal.oraxen.utils.AdventureUtils;
import io.th0rgal.oraxen.utils.VersionUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class BukkitPackSender extends PackSender implements Listener {

    private static final String prompt = Settings.SEND_PACK_PROMPT.toString();
    private static final boolean mandatory = Settings.SEND_PACK_MANDATORY.toBool();

    public BukkitPackSender(HostingProvider hostingProvider) {
        super(hostingProvider);
    }

    public void register() {
        Bukkit.getPluginManager().registerEvents(this, OraxenPlugin.get());
    }

    public void unregister() {
        HandlerList.unregisterAll(this);
    }

    @Override
    public void sendPack(Player player) {
        if (VersionUtil.atOrAbove("1.20.3"))
            if (VersionUtil.isPaperServer()) player.setResourcePack(UUID.randomUUID(), hostingProvider.getMinecraftPackURL(), hostingProvider.getSHA1(), AdventureUtils.MINI_MESSAGE.deserialize(prompt), mandatory);
            else player.setResourcePack(UUID.randomUUID(), hostingProvider.getMinecraftPackURL(), hostingProvider.getSHA1(), AdventureUtils.parseLegacy(prompt), mandatory);
        else if (VersionUtil.isPaperServer()) player.setResourcePack(hostingProvider.getMinecraftPackURL(), hostingProvider.getSHA1(), AdventureUtils.MINI_MESSAGE.deserialize(prompt), mandatory);
        else player.setResourcePack(hostingProvider.getMinecraftPackURL(), hostingProvider.getSHA1(), AdventureUtils.parseLegacy(prompt), mandatory);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerConnect(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (Settings.SEND_JOIN_MESSAGE.toBool()) sendWelcomeMessage(player, true);
        if (!Settings.SEND_PACK.toBool()) return;
        int delay = (int) Settings.SEND_PACK_DELAY.getValue();
        if (delay <= 0) sendPack(player);
        else Bukkit.getAsyncScheduler().runDelayed(OraxenPlugin.get(), t ->
                sendPack(player), delay * 20L*50, TimeUnit.MILLISECONDS);
    }
}
