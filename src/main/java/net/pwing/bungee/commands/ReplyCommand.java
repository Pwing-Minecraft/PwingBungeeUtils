package net.pwing.bungee.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.pwing.bungee.PwingBungeeUtils;

public class ReplyCommand extends Command {

    private PwingBungeeUtils plugin;

    public ReplyCommand(PwingBungeeUtils plugin, String name, String... aliases) {
        super(name, null, aliases);

        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new TextComponent(ChatColor.RED + "You must be a player to run this command."));
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;
        if (args.length < 1) {
            player.sendMessage(new TextComponent(ChatColor.RED + "You must supply a message."));
            return;
        }

        if (!plugin.getMessageMap().containsKey(player.getUniqueId())) {
            player.sendMessage(new TextComponent(ChatColor.RED + "You have not recently messaged any player."));
            return;
        }

        ProxiedPlayer target = plugin.getProxy().getPlayer(plugin.getMessageMap().get(player.getUniqueId()));
        if (target == null) {
            player.sendMessage(new TextComponent(ChatColor.RED + "That player is no longer online."));
            plugin.getMessageMap().remove(player.getUniqueId());
            return;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            builder.append(args[i] + " ");
        }

        String toFormat = ChatColor.DARK_PURPLE + "[" + ChatColor.WHITE + "me" + ChatColor.LIGHT_PURPLE + " -> " + ChatColor.WHITE + target.getName() + ChatColor.DARK_PURPLE + "] ";
        String fromFormat = ChatColor.DARK_PURPLE + "[" + ChatColor.WHITE + player.getName() + ChatColor.LIGHT_PURPLE + " -> " + ChatColor.WHITE + "me" + ChatColor.DARK_PURPLE + "] ";

        player.sendMessage(new TextComponent(toFormat + ChatColor.WHITE + builder.toString()));
        target.sendMessage(new TextComponent(fromFormat + ChatColor.WHITE + builder.toString()));
    }
}