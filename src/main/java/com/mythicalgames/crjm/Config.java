package com.mythicalgames.crjm;

import java.util.HashMap;
import java.util.Map;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;

@Header({
    "###############################################",
    "# Thank you for downloading CustomRankJoinMessages",
    "# You are now part of our Mythical Ecosystem",
    "###############################################"
})

public class Config extends OkaeriConfig {
    @Comment(" ")
    @Comment(" ")

    @Comment("Default join message if group-specific one is not found")
    public String defaultMessage = "§e{player} joined the game.";

    @Comment("Per-group join messages")
    public Map<String, String> groups = new HashMap<String, String>() {{
        put("owner", "§c[OWNER] §f{player} is here to cause destruction!");
        put("moderator", "§b[Mod] §f{player} joined the game!");
    }};
}
