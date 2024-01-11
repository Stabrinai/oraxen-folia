package io.th0rgal.oraxen.mechanics.provided.cosmetic.aura.aura;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import io.th0rgal.oraxen.OraxenPlugin;
import io.th0rgal.oraxen.mechanics.provided.cosmetic.aura.AuraMechanic;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public abstract class Aura {

    protected final AuraMechanic mechanic;
    private ScheduledTask runnable;

    protected Aura(AuraMechanic mechanic) {
        this.mechanic = mechanic;
    }

    void getRunnable() {
        for (Player player : mechanic.players) {
            Aura.this.spawnParticles(player);
        }
    }

    protected abstract void spawnParticles(Player player);

    protected abstract long getDelay();

    public void start() {
        runnable = Bukkit.getAsyncScheduler().runAtFixedRate(OraxenPlugin.get(), t -> getRunnable(), 1L*50, getDelay()*50, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        runnable.cancel();
    }


}
