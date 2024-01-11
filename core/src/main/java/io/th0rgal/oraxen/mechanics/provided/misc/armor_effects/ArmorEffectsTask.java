package io.th0rgal.oraxen.mechanics.provided.misc.armor_effects;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import io.th0rgal.oraxen.OraxenPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ArmorEffectsTask {

    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            ArmorEffectsMechanic.addEffects(player);
        }
    }

    public @NotNull ScheduledTask run(OraxenPlugin oraxenPlugin, int i, int delay) {
        if (i <= 0) {
            i = 1;
        }
        if (delay <= 0) {
            delay = 1;
        }
        return Bukkit.getGlobalRegionScheduler().runAtFixedRate(oraxenPlugin, task -> run(), i, delay);
    }
}
