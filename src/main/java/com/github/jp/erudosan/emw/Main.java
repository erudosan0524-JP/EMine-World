package com.github.jp.erudosan.emw;

import com.github.jp.erudosan.emw.task.SchedulerTask;
import com.github.jp.erudosan.emw.utils.Config;
import com.onarandombox.MultiverseCore.MultiverseCore;
import lombok.Getter;
import org.bukkit.WorldType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class Main extends JavaPlugin {

    private Main instance;

    @Getter
    private Config myConfig;

    @Getter
    private MultiverseCore mvCore;

    @Override
    public void onDisable() {
        getLogger().info("プラグインを停止しました");
    }

    @Override
    public void onEnable() {
        setInstance(this);
        getLogger().info("プラグインを起動しました");

        //Config
        myConfig = new Config(getInstance());

        //Scheduler
        SchedulerTask task = new SchedulerTask(getInstance());
        task.runTaskTimer(getInstance(),0L,20 * 60);

        //Command
        getServer().getPluginCommand("emw").setExecutor(new CommandManager(getInstance()));

        //Multiverse-API
        mvCore = getPlugin(MultiverseCore.class);
    }

    private Main getInstance() {
        return this.instance;
    }

    private void setInstance(Main main) {
        this.instance = main;
    }

    public void createWorld() {
        for(String name : this.getMyConfig().getWorlds().keySet()) {
            Random rand = new Random();

            this.getMvCore().getMVWorldManager().addWorld(name,this.getMyConfig().getWorlds().get(name),rand.toString(), WorldType.NORMAL,false,null);
        }
    }
}
