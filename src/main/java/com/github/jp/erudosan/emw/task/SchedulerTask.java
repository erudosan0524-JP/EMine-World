package com.github.jp.erudosan.emw.task;

import com.github.jp.erudosan.emw.Main;
import com.onarandombox.MultiverseCore.MVWorld;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import com.onarandombox.MultiverseCore.api.WorldPurger;
import com.onarandombox.MultiverseCore.utils.PurgeWorlds;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class SchedulerTask extends BukkitRunnable {

    private Main plugin;

    public SchedulerTask(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        Calendar calendar = Calendar.getInstance();

        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        for(int i=0; i < plugin.getMyConfig().getCalendars().size(); i++) {
            if(plugin.getMyConfig().getMonth(i) == month
            && plugin.getMyConfig().getDate(i) == date
            && plugin.getMyConfig().getHour(i) == hour
            && plugin.getMyConfig().getMinute(i) == minute) {

                plugin.createWorld();

            }
        }

    }
}
