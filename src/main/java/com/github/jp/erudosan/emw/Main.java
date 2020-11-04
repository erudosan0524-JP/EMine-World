package com.github.jp.erudosan.emw;

import com.github.jp.erudosan.emw.utils.Config;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private Main instance;

    @Getter
    private Config myConfig;

    @Override
    public void onDisable() {
        getLogger().info("プラグインを停止しました");
    }

    @Override
    public void onEnable() {
        setInstance(this);
        getLogger().info("プラグインを起動しました");

        myConfig = new Config(getInstance());

        getServer().getPluginCommand("emw").setExecutor(new CommandManager());
    }

    private Main getInstance() {
        return this.instance;
    }

    private void setInstance(Main main) {
        this.instance = main;
    }
}
