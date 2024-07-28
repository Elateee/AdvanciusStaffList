package net.advancius.bungeestafflist;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class BungeeStaffList extends Plugin implements Listener {
    public static Plugin plugin;
    private static HideFromListToggleManager hideFromStaffListToggleManager;

    public void onEnable() {
        plugin = this;
        getProxy().getPluginManager().registerCommand(this, new BungeeStaffListCommand());
        hideFromStaffListToggleManager = new HideFromListToggleManager();
        getProxy().getPluginManager().registerCommand(this, new HideFromList("hide", hideFromStaffListToggleManager));
        getProxy().getPluginManager().registerListener(this, this);
    }

    public static HideFromListToggleManager getHideFromStaffListToggleManager() {
        return hideFromStaffListToggleManager;
    }

    // Toggles hidden status off automatically upon joining server
    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();
        if (player.hasPermission("advancius.staff")) {
            hideFromStaffListToggleManager.resetToggleState(player.getUniqueId());
        }
    }
}