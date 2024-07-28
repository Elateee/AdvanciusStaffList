package net.advancius.bungeestafflist;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.GroupManager;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;
import net.luckperms.api.track.Track;
import net.luckperms.api.track.TrackManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

import java.util.*;

public class BungeeStaffListCommand extends Command {
    public BungeeStaffListCommand() {
        super("stafflist", "advancius.onlineplayers", new String[] { "sl", "staff", "slist", "staffl"});
    }

    public void execute(CommandSender sender, String[] strings) {
        LuckPerms luckPerms = LuckPermsProvider.get();
        // Custom order
        List<String> order = new ArrayList<>();
        order.add("[Owner]");
        order.add("[HeadDev]");
        order.add("[HeadStaffManager]");
        order.add("[HeadMod]");
        order.add("[HeadCommunityManager]");
        order.add("[HeadBuilder]");
        order.add("[SrDev]");
        order.add("[SrStaffManager]");
        order.add("[SrMod]");
        order.add("[SrCommunityManager]");
        order.add("[SrBuilder]");
        order.add("[Dev]");
        order.add("[StaffManager]");
        order.add("[Mod]");
        order.add("[CommunityManager]");
        order.add("[Builder]");
        order.add("[JrDev]");
        order.add("[JrStaffManager]");
        order.add("[JrMod]");
        order.add("[JrCommunityManager");
        order.add("[JrBuilder]");


        // Create map
        Map<String, Integer> orderMap = new HashMap<>();
        for (int i = 0; i < order.size(); i++) {
            orderMap.put(order.get(i), i);
        }
        // Create format arraylist
        ArrayList<String> format = new ArrayList<>();
         // Existing logic
        ProxyServer.getInstance().getPlayers().forEach(player -> {
            UUID playerId = player.getUniqueId();
            HideFromListToggleManager hideFromListToggleManager = BungeeStaffList.getHideFromStaffListToggleManager();
            if (player.hasPermission("advancius.staff") && (!hideFromListToggleManager.getToggleState(player.getUniqueId()))) {
                User user = luckPerms.getUserManager().getUser(player.getUniqueId());
                GroupManager groupManager = luckPerms.getGroupManager();
                TrackManager trackManager = luckPerms.getTrackManager();
                Track staffTrack = trackManager.getTrack("staff");
                String prefix = "&a";
                for (int i = 0; i < staffTrack.getGroups().size(); i++) {
                    if (user.getInheritedGroups(QueryOptions.defaultContextualOptions()).contains(groupManager.getGroup(staffTrack.getGroups().get(i))))
                        prefix = groupManager.getGroup(staffTrack.getGroups().get(i)).getCachedData().getMetaData().getPrefix();
                        //prefix = String.valueOf(luckPerms.getPlayerAdapter(BungeeStaffListCommand.class).getMetaData((BungeeStaffListCommand) player));
                }
                format.add("&7" + prefix + player.getName() + " &7- &e" + player.getServer().getInfo().getName());
            }
        });

        int numOnline = format.size();

        // sorting
        Collections.sort(format, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                Integer order1 = getOrderIndex(o1, orderMap);
                Integer order2 = getOrderIndex(o2, orderMap);

                // Consistent default order for items not in the custom order list
                if (order1 == null) {
                    order1 = order.size(); // Place at end
                }
                if (order2 == null) {
                    order2 = order.size();
                }

                return order1.compareTo(order2);
            }

            private Integer getOrderIndex(String element, Map<String, Integer> orderMap) {
                for (String key : orderMap.keySet()) {
                    if (element.contains(key)) {
                        return orderMap.get(key);
                    }
                }
                return null;
            }
        });
        // send output to player
        sender.sendMessage((BaseComponent)new TextComponent(ChatColor.translateAlternateColorCodes('&', "&8&m                                   &r")));
        sender.sendMessage((BaseComponent)new TextComponent(ChatColor.translateAlternateColorCodes('&', "&b&lOnline Staff (" + numOnline + "):")));
        sender.sendMessage((BaseComponent)new TextComponent(ChatColor.translateAlternateColorCodes('&', "&r")));
        for (String stafflistFormat : format)
            sender.sendMessage((BaseComponent)new TextComponent((ChatColor.translateAlternateColorCodes('&', String.valueOf(stafflistFormat)))));
    }
}
