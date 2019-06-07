package net.pwing.bungee.listeners;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.pwing.bungee.PwingBungeeUtils;

import java.util.List;
import java.util.UUID;

public class PingListener implements Listener {

    private PwingBungeeUtils plugin;

    public PingListener(PwingBungeeUtils plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onProxyPing(ProxyPingEvent event) {
        int online = plugin.getProxy().getOnlineCount();
        int max = plugin.getProxy().getConfig().getPlayerLimit();
        ServerPing ping = event.getResponse();
        ping.setDescriptionComponent(new TextComponent(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("motd.top")
                + "\n" + plugin.getConfig().getString("motd.bottom"))));

        List<String> news = plugin.getConfig().getStringList("motd.news");
        ServerPing.PlayerInfo[] players = new ServerPing.PlayerInfo[news.size()];
        for (int i = 0; i < news.size(); i++) {
            players[i] = new ServerPing.PlayerInfo(ChatColor.translateAlternateColorCodes('&', news.get(i))
                    .replace("$onlineplayers", String.valueOf(online)).replace("$maxplayers", String.valueOf(max)), UUID.randomUUID());
        }

        ping.setPlayers(new ServerPing.Players(max, online, players));
        event.setResponse(ping);
    }
}
