package net.pwing.bungee.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.pwing.bungee.PwingBungeeUtils;
import net.pwing.bungee.utils.MessageUtil;

public class StaffChatCommand extends Command {

    private PwingBungeeUtils plugin;

    public StaffChatCommand(PwingBungeeUtils plugin, String name, String... aliases) {
        super(name, null, aliases);

        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(new TextComponent(ChatColor.RED + "You must supply a message."));
            return;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            builder.append(args[i] + " ");
        }

        String serverName = "PROXY";
        if (sender instanceof ProxiedPlayer)
            serverName = ((ProxiedPlayer) sender).getServer().getInfo().getName();

        for (String str : plugin.getStaff()) {
            ProxiedPlayer player = plugin.getProxy().getPlayer(str);
            if (player == null)
                continue;

            player.sendMessage(new TextComponent(ChatColor.AQUA + "[" + ChatColor.WHITE + MessageUtil.capitalize(serverName) + " - " + ChatColor.RED + sender.getName() + ChatColor.AQUA + "] " + builder.toString()));
        }
    }
}
