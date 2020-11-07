package com.github.jp.erudosan.emw.task;

import com.github.jp.erudosan.emw.Main;
import com.onarandombox.MultiverseCore.MVWorld;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import com.onarandombox.MultiverseCore.api.WorldPurger;
import com.onarandombox.MultiverseCore.utils.PurgeWorlds;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.sql.Time;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

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
            int monthDiff = plugin.getMyConfig().getMonth(i) - month;
            int dateDiff = plugin.getMyConfig().getDate(i) - date;
            int hourDiff = plugin.getMyConfig().getHour(i) - hour;
            int minuteDiff = plugin.getMyConfig().getMinute(i) - minute;

            if(monthDiff < 0 || dateDiff < 0 || hourDiff < 0 || minuteDiff < 0) {
                continue;
            }

            if(monthDiff == 0 && dateDiff == 0 && hourDiff == 0) {
                if(minuteDiff % 15 == 0) {
                    Bukkit.getServer().broadcastMessage("あと" + Math.abs(minuteDiff) + "分後に資源ワールドがリセットされます。");
                }

                if(minuteDiff == 0) {
                    plugin.createWorld();
                }
            }
        }

    }
}
