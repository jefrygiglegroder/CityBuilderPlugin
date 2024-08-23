package jefry.plugin.cityBuilderPlugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        PlayerInteractListener listener = new PlayerInteractListener();
        listener.updateHotbar(event.getPlayer(), 1);
    }
}
