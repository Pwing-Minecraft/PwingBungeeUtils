package net.pwing.bungee;

import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.pwing.bungee.commands.ListCommand;
import net.pwing.bungee.commands.MessageCommand;
import net.pwing.bungee.commands.ReloadConfigCommand;
import net.pwing.bungee.commands.ReplyCommand;
import net.pwing.bungee.commands.StaffChatCommand;
import net.pwing.bungee.listeners.PingListener;
import net.pwing.bungee.listeners.PlayerListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PwingBungeeUtils extends Plugin {

    private List<String> staff = new ArrayList<String>();
    private Map<UUID, UUID> messageMap = new HashMap<UUID, UUID>();

    private File configFile;
    private Configuration config;

    @Override
    public void onEnable() {
        loadConfig();

        getProxy().getPluginManager().registerCommand(this, new ListCommand(this, "list", "who", "online"));
        getProxy().getPluginManager().registerCommand(this, new MessageCommand(this, "msg", "whisper", "w", "message", "pm", "tell"));
        getProxy().getPluginManager().registerCommand(this, new ReplyCommand(this, "reply", "r", "replymessage"));
        getProxy().getPluginManager().registerCommand(this, new ReloadConfigCommand(this, "reloadbungeeconfig"));
        getProxy().getPluginManager().registerCommand(this, new StaffChatCommand(this, "staffchat", "sc"));

        getProxy().getPluginManager().registerListener(this, new PingListener(this));
        getProxy().getPluginManager().registerListener(this, new PlayerListener(this));
    }

    public void loadConfig() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                try (InputStream is = getResourceAsStream("config.yml"); OutputStream os = new FileOutputStream(configFile)) {
                    ByteStreams.copy(is, os);
                }
            } catch (IOException e) {
                throw new RuntimeException("Unable to create configuration file", e);
            }
        }

        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        staff.clear();
        staff.addAll(config.getStringList("staff"));
    }

    public List<String> getStaff() {
        return staff;
    }

    public Configuration getConfig() {
        return config;
    }

    public Map<UUID, UUID> getMessageMap() {
        return messageMap;
    }
}
