package de.centox.farmday.events;

import de.centox.farmday.utils.ScoreboardManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    @EventHandler
    public void handle(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ScoreboardManager.setScoreboard(player);
    }

}
