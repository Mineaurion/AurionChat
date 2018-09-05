package com.mineaurion.aurionchat.sponge;

import com.google.inject.Inject;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.asset.Asset;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Config {

    @Inject
    private Logger logger;

    private AurionChat plugin;

    public CommentedConfigurationNode config;

    public Config(AurionChat plugin) throws IOException {
        this.plugin = plugin;
        HoconConfigurationLoader loader = HoconConfigurationLoader.builder().setPath(plugin.defaultConf).build();
        config = loader.load();

        if(!plugin.defaultConfigFile.exists()){
            plugin.sendConsoleMessage("&eConfig file copied");
            Asset configAsset = plugin.pluginContainer.getAsset("config.conf").get();
            configAsset.copyToFile(plugin.defaultConf);
        }
    }

    public String getFormatChannel(String channelName){
        return config.getNode("channels", channelName, "format").getString();
    }

    public String getChannelAlias(String channelName){
        return config.getNode("channels", channelName, "alias").getString();
    }

    public Set<String> getAllChannel(){
        Set<String> channels = new HashSet<>();
        Map<Object,? extends CommentedConfigurationNode> nodeList = config.getNode("channels").getChildrenMap();
        for(Object node: nodeList.keySet()){
            channels.add(node.toString());
        }
        return channels;
    }
}