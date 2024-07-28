package net.advancius.bungeestafflist;

import net.md_5.bungee.api.plugin.Plugin;

public class BungeeStaffList extends Plugin {
    public static Plugin plugin;
    private static HideFromListToggleManager hideFromStaffListToggleManager;

    public void onEnable() {
        plugin = this;
        getProxy().getPluginManager().registerCommand(this, new BungeeStaffListCommand());
        hideFromStaffListToggleManager = new HideFromListToggleManager();
        getProxy().getPluginManager().registerCommand(this, new HideFromList("hide", hideFromStaffListToggleManager));
    }

    public static HideFromListToggleManager getHideFromStaffListToggleManager() {
        return hideFromStaffListToggleManager;
    }
}