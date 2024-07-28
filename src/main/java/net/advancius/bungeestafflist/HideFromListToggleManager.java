package net.advancius.bungeestafflist;

import java.util.HashMap;
import java.util.UUID;

public class HideFromListToggleManager {
    private final HashMap<UUID, Boolean> playerToggleState = new HashMap<>();

    public boolean getToggleState(UUID playerId) {
        return playerToggleState.getOrDefault(playerId, false);
    }

    public void setToggleState(UUID playerId, boolean state) {
        playerToggleState.put(playerId, state);
    }

    public void resetToggleState(UUID playerId) {
        playerToggleState.put(playerId, false);
    }
}