package com.mythicalgames.crjm;

import lombok.extern.slf4j.Slf4j;

import org.allaymc.api.entity.interfaces.EntityPlayer;
import org.allaymc.api.eventbus.EventHandler;
import org.allaymc.api.eventbus.event.player.PlayerJoinEvent;
import org.allaymc.api.plugin.Plugin;
import org.allaymc.api.server.Server;

import com.mythicalgames.ranks.RankSystem;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.snakeyaml.YamlSnakeYamlConfigurer;

@Slf4j
public class CustomRankJoinMessages extends Plugin {
    private static CustomRankJoinMessages instance;
    public Config config;

    @Override
    public void onLoad() {
        instance = this;
        log.info("Loading Configuration file..!");
        config = ConfigManager.create(Config.class, config -> {
            config.withConfigurer(new YamlSnakeYamlConfigurer());
            config.withBindFile(pluginContainer.dataFolder().resolve("config.yml"));
            config.withRemoveOrphans(true);
            config.saveDefaults();
            config.load(true);
        });
    }

    @Override
    public void onEnable() {
        Server.getInstance().getEventBus().registerListener(this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        EntityPlayer player = event.getPlayer();
        Config config = CustomRankJoinMessages.getInstance().config;

        RankSystem.getAPI().getGroupName(player).thenAccept(groupName -> {
            String message = config.groups.getOrDefault(groupName.toLowerCase(), config.defaultMessage);
            message = message.replace("{player}", player.getOriginName());

            for (EntityPlayer onlinePlayer : Server.getInstance().getPlayerManager().getPlayers().values()) {
                onlinePlayer.sendMessage(message);
            }
        }).exceptionally(ex -> {
            log.error("Failed to get group for player {}: {}", player.getOriginName(), ex.getMessage());
            return null;
        });
    }

    public static CustomRankJoinMessages getInstance() {
        return instance;
    }
}