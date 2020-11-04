package com.github.jp.erudosan.emw.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Config {

    private Plugin plugin;
    private FileConfiguration config;

    //設定一覧
    private List<String> date;
    private List<Calendar> calendarList;

    public Config(Plugin plugin) {
        this.plugin = plugin;

        load();
    }

    private void load() {
        plugin.saveDefaultConfig();

        if(Objects.nonNull(config)) {
            reload();
        }

        date = config.getStringList("date");
        setCalendars();
    }

    private void reload() {
        plugin.reloadConfig();
        plugin.saveConfig();
    }

    private void setCalendars() {
        for(String s : date) {
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd,HH:mm");
            try {
                date = dateFormat.parse(s);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendarList.add(calendar);
        }
    }

    public List<Calendar> getCalendars() {
        return this.calendarList;
    }

    public Calendar getCalender(int index) {
        if(Objects.nonNull(this.calendarList.get(index))) {
            return this.calendarList.get(index);
        } else {
            return null;
        }
    }

    public int getHour(int index) {
        if(Objects.nonNull(this.calendarList.get(index))) {
            return this.calendarList.get(index).get(Calendar.HOUR);
        } else {
            return 0;
        }
    }

    public int getMinute(int index) {
        if(Objects.nonNull(this.calendarList.get(index))) {
            return this.calendarList.get(index).get(Calendar.MINUTE);
        } else {
            return 0;
        }
    }

    public int getMonth(int index) {
        if(Objects.nonNull(this.calendarList.get(index))) {
            return this.calendarList.get(index).get(Calendar.MONTH) + 1;
        } else {
            return 0;
        }
    }

    public int getDate(int index) {
        if(Objects.nonNull(this.calendarList.get(index))) {
            return this.calendarList.get(index).get(Calendar.DATE);
        } else {
            return 0;
        }
    }
}
