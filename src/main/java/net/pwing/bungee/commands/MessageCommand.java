package net.pwing.bungee.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.pwing.bungee.PwingBungeeUtils;

public class MessageCommand extends Command {

    private PwingBungeeUtils plugin;

    public MessageCommand(PwingBungeeUtils plugin, String name, String... aliases) {
        super(name, null, aliases);

        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(new TextComponent(ChatColor.RED + "You must supply a message."));
            return;
        }

        ProxiedPlayer target = plugin.getProxy().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(new TextComponent(ChatColor.RED + "That player is not online."));
            return;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            builder.append(args[i] + " ");
        }

        String toFormat = ChatColor.DARK_PURPLE + "[" + ChatColor.WHITE + "me" + ChatColor.LIGHT_PURPLE + " -> " + ChatColor.WHITE + target.getName() + ChatColor.DARK_PURPLE + "] ";
        String fromFormat = ChatColor.DARK_PURPLE + "[" + ChatColor.WHITE + sender.getName() + ChatColor.LIGHT_PURPLE + " -> " + ChatColor.WHITE + "me" + ChatColor.DARK_PURPLE + "] ";

        sender.sendMessage(new TextComponent(toFormat + ChatColor.WHITE + builder.toString()));
        target.sendMessage(new TextComponent(fromFormat + ChatColor.WHITE + builder.toString()));

        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            plugin.getMessageMap().put(player.getUniqueId(), target.getUniqueId());
            plugin.getMessageMap().put(target.getUniqueId(), player.getUniqueId());
        }
    }
}