package net.pwing.bungee.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.pwing.bungee.PwingBungeeUtils;

public class ReloadConfigCommand extends Command {

    private PwingBungeeUtils plugin;

    public ReloadConfigCommand(PwingBungeeUtils plugin, String name) {
        super(name, "pbungeeutils.admin");

        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission(this.getPermission())) {
            sender.sendMessage(new TextComponent(ChatColor.RED + "You do not have permission to run this command!"));
            return;
        }

        plugin.getMessageMap().clear();
        plugin.loadConfig();
        sender.sendMessage(new TextComponent(ChatColor.GREEN + "Successfully reloaded the config file."));
    }
}
