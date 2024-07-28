package net.advancius.bungeestafflist;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.UUID;

public class HideFromList extends Command {

    private final HideFromListToggleManager toggleManager;

    public HideFromList(String name, HideFromListToggleManager toggleManager) {
        super("hide", "advancius.canhide");
        this.toggleManager = toggleManager;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            UUID playerId = player.getUniqueId();

            boolean currentState = toggleManager.getToggleState(playerId);

            if (currentState) {
                sender.sendMessage((BaseComponent)new TextComponent(ChatColor.translateAlternateColorCodes('&', "&aYou are no longer hidden from staff list!")));
            } else {
                sender.sendMessage((BaseComponent)new TextComponent(ChatColor.translateAlternateColorCodes('&', "&cYou are now hidden from staff list!")));
            }

            toggleManager.setToggleState(playerId, !currentState);
        } else {
            sender.sendMessage((BaseComponent)new TextComponent("This command may only be executed by a player.") );
        }
    }
}