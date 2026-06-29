package git.otrixzy.ocooldowns;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SoundUtils {

    public static void playSound(OCooldowns plugin, Player player, String path) {
        FileConfiguration config = plugin.getConfig();
        if (!config.getBoolean("sounds." + path + ".enabled", true)) return;

        try {
            String soundName = config.getString("sounds." + path + ".sound", "block.note_block.pling").toLowerCase();
            float volume = (float) config.getDouble("sounds." + path + ".volume", 1.0);
            float pitch = (float) config.getDouble("sounds." + path + ".pitch", 1.0);

            Sound sound = Registry.SOUNDS.get(NamespacedKey.minecraft(soundName));

            if (sound != null) {
                player.playSound(player.getLocation(), sound, volume, pitch);
            } else {
                plugin.getLogger().warning("Could not find the sound: " + soundName + " for " + path);
            }

        } catch (Exception e) {
            plugin.getLogger().warning("Error attempting to play sound for: " + path);
            e.printStackTrace();
        }
    }
}