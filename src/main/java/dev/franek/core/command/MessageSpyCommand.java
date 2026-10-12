package dev.franek.core.command;

import dev.franek.core.manager.SpyRegistry;
import dev.franek.core.util.TextFormatter;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * /socialspy - wlacza/wylacza podglad wiadomosci prywatnych graczy.
 */
public final class MessageSpyCommand extends net.md_5.bungee.api.plugin.Command {

    public MessageSpyCommand() {
        super("socialspy", "admin.socialspy");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage("Tylko gracze mogą używać tej komendy.");
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) sender;
        SpyRegistry.toggle(player.getUniqueId());
        boolean watching = SpyRegistry.isWatching(player.getUniqueId());
        player.sendMessage(TextFormatter.format("&7Widocznosc co gracze pisza na msg: "
                + (watching ? "&aWłączona" : "&cWyłączona") + "."));
    }
}
