package com.github.jp.erudosan.emw;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onDisable() {
        getLogger().info("プラグインを停止しました");
    }

    @Override
    public void onEnable() {
        getLogger().info("プラグインを起動しました");

        getServer().getPluginCommand("emw").setExecutor(new CommandManager());
    }
}
