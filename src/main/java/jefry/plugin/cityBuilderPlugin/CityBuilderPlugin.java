package jefry.plugin.cityBuilderPlugin;

import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class CityBuilderPlugin extends JavaPlugin implements Listener {
    @Getter
    private static Economy economy = null;

    public static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        if (!setupEconomy()) {
            getLogger().severe("Disabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), plugin);
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }

        economy = rsp.getProvider();

        return true;
    }

    @Override
    public void onDisable() {
        getLogger().info("CityBuilder plugin disabled!");
    }
}
