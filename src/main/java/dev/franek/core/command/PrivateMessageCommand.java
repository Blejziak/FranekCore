package dev.franek.core.command;

import dev.franek.core.FranekCore;
import dev.franek.core.manager.SpyRegistry;
import dev.franek.core.util.TextFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * /msg &lt;gracz&gt; &lt;tresc&gt; - wiadomosc prywatna, takze miedzy serwerami.
 */
public final class PrivateMessageCommand extends net.md_5.bungee.api.plugin.Command {

    /** Czyja wiadomosc gracz moze ostatnio poddac odpowiedzi (/r). */
    private static final Map<UUID, UUID> lastIncoming = new HashMap<>();

    private final FranekCore plugin;

    public PrivateMessageCommand(FranekCore plugin) {
        super("msg");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage("Tylko gracze mogą używać tej komendy.");
            return;
        }
        if (args.length < 2) {
            sender.sendMessage(TextFormatter.format("&4Błąd: &cPoprawne użycie: &6/msg <gracz> <wiadomość>"));
            return;
        }
        ProxiedPlayer target = plugin.getProxy().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(TextFormatter.format("&cGracz " + args[0] + " nie jest online."));
            return;
        }
        ProxiedPlayer author = (ProxiedPlayer) sender;
        String message = formatFor(plugin.options().privateMessageFormat, author, target,
                TextFormatter.join(args, 1));
        target.sendMessage(message);
        author.sendMessage(message);
        lastIncoming.put(target.getUniqueId(), author.getUniqueId());
        for (ProxiedPlayer watcher : plugin.getProxy().getPlayers()) {
            if (SpyRegistry.isWatching(watcher.getUniqueId())) {
                watcher.sendMessage(TextFormatter.format("&8[&4SocialSpy&8] &r" + message));
            }
        }
    }

    /** Autor ostatniej wiadomosci prywatnej skierowanej do tego gracza. */
    public static UUID lastIncomingFrom(UUID recipientId) {
        return lastIncoming.get(recipientId);
    }

    /** Buduje wiadomosc wg formatu z konfiguracji. */
    static String formatFor(String format, ProxiedPlayer author, ProxiedPlayer target, String text) {
        String message = format
                .replace("{SENDER_SERVER}", author.getServer().getInfo().getName())
                .replace("{SENDER}", author.getName())
                .replace("{RECIPIENT_SERVER}", target.getServer().getInfo().getName())
                .replace("{RECIPIENT}", target.getName())
                .replace("{MESSAGES}", text);
        return TextFormatter.format(message);
    }
}
