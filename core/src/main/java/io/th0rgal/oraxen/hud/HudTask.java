package io.th0rgal.oraxen.hud;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import io.th0rgal.oraxen.OraxenPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class HudTask {

    private final HudManager manager = OraxenPlugin.get().getHudManager();

    private List<? extends Player> hudEnabledPlayers() {
        return Bukkit.getOnlinePlayers().stream().filter(manager::getHudStateForPlayer).toList();
    }

    public void run() {
        for (Player player : hudEnabledPlayers()) {
            player.getScheduler().run(OraxenPlugin.get(), task -> {
                Hud hud = manager.getActiveHudForPlayer(player) != null ? manager.getActiveHudForPlayer(player) : manager.getDefaultEnabledHuds().stream().findFirst().orElse(null);

                if (hud == null || manager.getHudID(hud) == null) return;
                if (hud.disableWhilstInWater() && player.isInWater()) return;
                if (!player.hasPermission(hud.getPerm())) return;

                manager.updateHud(player);
            }, null);
        }
    }

    public ScheduledTask run(OraxenPlugin oraxenPlugin, int i, int hudUpdateTime) {
        if (i <= 0) {
            i = 1;
        }
        if (hudUpdateTime <= 0) {
            hudUpdateTime = 1;
        }
        return Bukkit.getGlobalRegionScheduler().runAtFixedRate(oraxenPlugin, task -> run(), i, hudUpdateTime);
    }
}
