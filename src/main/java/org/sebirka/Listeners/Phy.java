package org.sebirka.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;

import static org.sebirka.Utils.MsptMonitor.isRedstoneEnabled;


public class Phy implements Listener {
    @EventHandler
    public void onRedstone(BlockRedstoneEvent event) {
        if (!isRedstoneEnabled) {
            event.setNewCurrent(0);
        }
    }
    @EventHandler
    public void onPistonExtend(BlockPistonExtendEvent event) {
        if (!isRedstoneEnabled) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPistonExtend(BlockExplodeEvent event) {
        if (!isRedstoneEnabled) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPistonRetract(BlockPistonRetractEvent event) {
        if (!isRedstoneEnabled) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPistonRetract(LeavesDecayEvent event) {
        if (!isRedstoneEnabled) {
            event.setCancelled(true);
        }
    }
}
