package pl.nehorowo.tools.factory;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import pl.nehorowo.tools.ToolsPlugin;
import pl.nehorowo.tools.user.User;
import pl.nehorowo.tools.user.bubble.ChatBubble;

import java.util.ArrayList;
import java.util.List;

public class ChatBubbleFactory {

    public void createHolo(String text, Location location, User user) {
        List<String> format = new ArrayList<>(ToolsPlugin.getInstance().getConfiguration().getChatBubbleFormat());
        format.replaceAll(s -> s
                .replace("[MESSAGE]", text)
                .replace("[PLAYER]", Bukkit.getPlayer(user.getUuid()).getName())
        );
        Location holoLoc = location.clone();
        holoLoc.add(0.0D, 3, 0.0D);
        String holoName = "chat-bubble-" + Bukkit.getPlayer(user.getUuid()).getName();
        Hologram hologram1 = DHAPI.getHologram(holoName);
        if (hologram1 == null) DHAPI.createHologram(holoName, holoLoc, false, format);
        else DHAPI.setHologramLines(hologram1, format);

        user.setChatBubble(new ChatBubble(holoName, text, System.currentTimeMillis()));
    }
}
