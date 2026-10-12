package dev.franek.core.command;

import dev.franek.core.FranekCore;
import dev.franek.core.manager.SpyRegistry;
import dev.franek.core.util.TextFormatter;
import java.util.UUID;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * /r &lt;tresc&gt; - odpowiedz na ostatnia wiadomosc prywatna.
 */
public final class MessageReplyCommand extends net.md_5.bungee.api.plugin.Command {

    private final FranekCore plugin;

    public MessageReplyCommand(FranekCore plugin) {
        super("r");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage("Tylko gracze mogą używać tej komendy.");
            return;
        }
        if (args.length == 0) {
            sender.sendMessage(TextFormatter.format("&4Błąd: &cPoprawne użycie: &6/r <wiadomość>"));
            return;
        }
        ProxiedPlayer author = (ProxiedPlayer) sender;
        UUID targetId = PrivateMessageCommand.lastIncomingFrom(author.getUniqueId());
        ProxiedPlayer target = targetId == null ? null : plugin.getProxy().getPlayer(targetId);
        if (target == null) {
            sender.sendMessage(TextFormatter.format("&cNie masz nikogo do kogo możesz odpowiedzieć."));
            return;
        }
        String message = PrivateMessageCommand.formatFor(plugin.options().privateMessageFormat,
                author, target, TextFormatter.join(args, 0));
        target.sendMessage(message);
        author.sendMessage(message);
        for (ProxiedPlayer watcher : plugin.getProxy().getPlayers()) {
            if (SpyRegistry.isWatching(watcher.getUniqueId())) {
                watcher.sendMessage(TextFormatter.format("&8[&4SocialSpy&8] &r" + message));
            }
        }
    }
}
