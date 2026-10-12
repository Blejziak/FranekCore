package dev.franek.core.settings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Ustawienia pluginu trzymane w config.json.
 */
public final class PluginSettings {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public Options options = new Options();

    /** Wczytuje config.json; gdy pliku brak lub jest uszkodzony, bierze wartosci domyslne. */
    public static PluginSettings load(Path file) {
        PluginSettings settings = null;
        if (Files.exists(file)) {
            try {
                String json = new String(Files.readAllBytes(file), StandardCharsets.UTF_8);
                settings = GSON.fromJson(json, PluginSettings.class);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (settings == null || settings.options == null) {
            settings = new PluginSettings();
        }
        return settings;
    }

    public static void save(PluginSettings settings, Path file) {
        try {
            Files.createDirectories(file.getParent());
            Files.write(file, GSON.toJson(settings).getBytes(StandardCharsets.UTF_8));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final class Options {
        public boolean privateMessages = true;
        public boolean helpOp = true;
        public boolean staffChat = true;
        public long helpOpCooldownSeconds = 5L;
        public String privateMessageFormat =
                "&8[&7{SENDER_SERVER}&8] &e{SENDER} &8-> &8[&7{RECIPIENT_SERVER}&8] &e{RECIPIENT}&8: &f{MESSAGES}";
        public String staffChatFormat = "&8[&6&lAdminChat&8] » &7{SENDER}&8: &f{MESSAGES}";
        public String helpOpFormat = "&8[&c&lHelpop&8] &7{SENDER}&8: &f{MESSAGES}";
        public String helpOpCooldownMessage = "&cPoczekaj jeszcze {TIME} sekund(y), zanim ponownie użyjesz /helpop.";
    }
}
