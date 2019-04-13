package net.pwing.bungee.listeners;

import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.pwing.bungee.PwingBungeeUtils;

/**
 * Created by Redned on 4/13/2019.
 */
public class PlayerListener implements Listener {

    private PwingBungeeUtils plugin;

    public PlayerListener(PwingBungeeUtils plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLeave(PlayerDisconnectEvent event) {
        if (plugin.getMessageMap().containsKey(event.getPlayer().getUniqueId())) {
            plugin.getMessageMap().remove(event.getPlayer().getUniqueId());
        }
    }
}
