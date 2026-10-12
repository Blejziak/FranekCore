package dev.franek.core;

import dev.franek.core.command.HelpRequestCommand;
import dev.franek.core.command.MessageReplyCommand;
import dev.franek.core.command.MessageSpyCommand;
import dev.franek.core.command.PrivateMessageCommand;
import dev.franek.core.command.StaffChatCommand;
import dev.franek.core.settings.PluginSettings;
import java.nio.file.Path;

/**
 * FranekCore - komunikacja miedzy serwerami (msg, helpop, chat administracji).
 *
 * Plugin made by franek_ - https://github.com/Blejziak
 */
public final class FranekCore extends net.md_5.bungee.api.plugin.Plugin {

    private PluginSettings settings;

    @Override
    public void onEnable() {
        settings = PluginSettings.load(settingsFile());
        PluginSettings.save(settings, settingsFile());
        registerCommands();
    }

    public PluginSettings.Options options() {
        return settings.options;
    }

    private Path settingsFile() {
        return getDataFolder().toPath().resolve("config.json");
    }

    private void registerCommands() {
        PluginSettings.Options options = settings.options;
        if (options.helpOp) {
            getProxy().getPluginManager().registerCommand(this, new HelpRequestCommand(this));
        }
        if (options.staffChat) {
            getProxy().getPluginManager().registerCommand(this, new StaffChatCommand(this));
        }
        if (options.privateMessages) {
            getProxy().getPluginManager().registerCommand(this, new PrivateMessageCommand(this));
            getProxy().getPluginManager().registerCommand(this, new MessageReplyCommand(this));
            getProxy().getPluginManager().registerCommand(this, new MessageSpyCommand());
        }
    }
}
