package net.pwing.bungee.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.pwing.bungee.PwingBungeeUtils;
import net.pwing.bungee.utils.MessageUtil;

public class ListCommand extends Command {

    private PwingBungeeUtils plugin;

    public ListCommand(PwingBungeeUtils plugin, String name, String... aliases) {
        super(name, null, aliases);

        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        String header = ChatColor.DARK_GRAY + "------------------"
                + ChatColor.GRAY + "[ " + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Players Online"
                + ChatColor.GRAY + " ]" + ChatColor.DARK_GRAY + "-----------------";

        sender.sendMessage(new TextComponent(header));
        sender.sendMessage(new TextComponent("Players Online: " + plugin.getProxy().getPlayers().size()));
        for (ServerInfo server : ProxyServer.getInstance().getServers().values()) {
            if (server.isRestricted() && !sender.hasPermission("bungeecord.server." + server.getName()))
                continue;

            StringBuilder builder = new StringBuilder();
            for (ProxiedPlayer player : server.getPlayers()) {
                if (builder.length() > 0)
                    builder.append(", ");

                builder.append(ChatColor.LIGHT_PURPLE + player.getDisplayName() + ChatColor.WHITE);
            }
            sender.sendMessage(new TextComponent(MessageUtil.capitalize(server.getName()) + " (" + server.getPlayers().size() + "):"));
            sender.sendMessage(new TextComponent(builder.toString()));
        }
    }
}
