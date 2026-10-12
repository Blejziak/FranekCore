package dev.franek.core.command;

import dev.franek.core.FranekCore;
import dev.franek.core.util.TextFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * /helpop &lt;tresc&gt; - zgloszenie gracza do administracji
 * (odbieraja gracze z uprawnieniem "helpop.receive"), z cooldownem na gracza.
 */
public final class HelpRequestCommand extends net.md_5.bungee.api.plugin.Command {

    /** Do tego momentu (millis) gracz nie moze ponownie uzyc komendy. */
    private final Map<String, Long> blockedUntil = new HashMap<>();

    private final FranekCore plugin;

    public HelpRequestCommand(FranekCore plugin) {
        super("helpop");
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
                    TextFormatter.format("&4Błąd: &cPoprawne użycie: &6/helpop <wiadomość>")));
            return;
        }
        long now = System.currentTimeMillis();
        long cooldownMillis = TimeUnit.SECONDS.toMillis(plugin.options().helpOpCooldownSeconds);
        Long blockedTo = blockedUntil.get(sender.getName());
        if (blockedTo != null && now < blockedTo) {
            long timeLeft = (blockedTo - now) / 1000L;
            String waitMessage = plugin.options().helpOpCooldownMessage
                    .replace("{TIME}", String.valueOf(timeLeft));
            sender.sendMessage(new TextComponent(TextFormatter.format(waitMessage)));
            return;
        }
        blockedUntil.put(sender.getName(), now + cooldownMillis);
        String message = plugin.options().helpOpFormat
                .replace("{SENDER}", sender.getName())
                .replace("{MESSAGES}", TextFormatter.join(args, 0));
        for (ProxiedPlayer player : plugin.getProxy().getPlayers()) {
            if (player.hasPermission("helpop.receive")) {
                player.sendMessage(TextFormatter.format(message));
            }
        }
    }
}
