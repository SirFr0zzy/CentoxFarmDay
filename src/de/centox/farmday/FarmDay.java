package de.centox.farmday;

import de.centox.farmday.utils.ScoreboardManager;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class FarmDay extends JavaPlugin {

    private static FarmDay instance;
    private static LuckPerms luckPerms;

    public static FarmDay getInstance() {
        return instance;
    }
    public static LuckPerms getLuckPerms() {
        return luckPerms;
    }

    @Override
    public void onEnable() {
        instance = this;
        setupLuckperms();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            Bukkit.getOnlinePlayers().forEach(ScoreboardManager::setScoreboard);
        }, 0, 20);

    }

    @Override
    public void onDisable() {

    }

    public void setupLuckperms() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) luckPerms = provider.getProvider();
    }

}