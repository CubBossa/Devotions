package me.xidentified.devotions.util;

import java.util.List;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.xidentified.devotions.Deity;
import me.xidentified.devotions.Devotions;
import me.xidentified.devotions.managers.FavorManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class Placeholders extends PlaceholderExpansion {

    private final Devotions plugin;

    public Placeholders(Devotions plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getAuthor() {
        return "xIdentified";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "devotions";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if (params.equalsIgnoreCase("deity")) {
            Deity deity = plugin.getDevotionManager().getPlayerDevotion(player.getUniqueId()).getDeity();
            return deity != null ? "§e" + deity.getName() : "\u00A0"; // Non-breaking space
        }

        if (params.equalsIgnoreCase("favor")) {
            FavorManager favorManager = plugin.getDevotionManager().getPlayerDevotion(player.getUniqueId());
            return favorManager.getFavor() + "";
        }

        if (params.equalsIgnoreCase("favor_top")) {
            List<FavorManager> sortedFavorData = plugin.getDevotionManager().getSortedFavorData();
            StringBuilder topPlayers = new StringBuilder();
            for (int i = 0; i < Math.min(3, sortedFavorData.size()); i++) {
                FavorManager data = sortedFavorData.get(i);
                String playerName = Bukkit.getOfflinePlayer(data.getUuid()).getName();
                topPlayers.append("§6").append(i + 1).append(". §a").append(playerName).append(" §7- ")
                        .append(data.getFavor()).append("\n");
            }
            return topPlayers.toString().trim();
        }

        return null; // Placeholder is unknown
    }


}