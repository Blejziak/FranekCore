package dev.franek.core.command;

import dev.franek.core.FranekCore;
import dev.franek.core.util.TextFormatter;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * /ac &lt;tresc&gt; - wiadomosc widoczna tylko dla administracji (uprawnienie "mod").
 */
public final class StaffChatCommand extends net.md_5.bungee.api.plugin.Command {

    private final FranekCore plugin;

    public StaffChatCommand(FranekCore plugin) {
        super("ac");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new TextComponent("Tylko gracze mogą używać tej komendy."));
            return;
        }
        if (args.length == 0) {
            sender.sendMessage(new TextComponent(
                    TextFormatter.format("&4Błąd: &cPoprawne użycie: &6/ac <wiadomość>")));
            return;
        }
        String message = plugin.options().staffChatFormat
                .replace("{SENDER}", sender.getName())
                .replace("{MESSAGES}", TextFormatter.join(args, 0));
        for (ProxiedPlayer player : plugin.getProxy().getPlayers()) {
            if (player.hasPermission("mod")) {
                player.sendMessage(TextFormatter.format(message));
            }
        }
    }
}
