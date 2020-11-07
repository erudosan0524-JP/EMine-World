package com.github.jp.erudosan.emw.utils;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.World;
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
    private List<Calendar> calendarList = new ArrayList<>();

    private List<String> worlds;
    //world_name,Environment
    private HashMap<String, World.Environment> worldMap = new HashMap<>();

    private String gateBlock;
    private String baseBlock;

    public Config(Plugin plugin) {
        this.plugin = plugin;

        load();
    }

    private void load() {
        plugin.saveDefaultConfig();

        if(Objects.nonNull(config)) {
            reload();
        }

        config = plugin.getConfig();

        date = config.getStringList("date");
        setCalendars();

        worlds = config.getStringList("worlds");
        setWorlds();

        gateBlock = config.getString("gate_block");
        baseBlock = config.getString("base_block");
    }

    public void reload() {
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

    private void setWorlds() {
        for(String s : worlds) {
            World.Environment env;
            String[] args = s.split(",",0);

            if(args.length <= 0) {
                return;
            }

            switch (args[1].toLowerCase()) {
                case "normal":
                    env = World.Environment.NORMAL;
                    break;
                case "nether":
                    env = World.Environment.NETHER;
                    break;
                case "end":
                    env = World.Environment.THE_END;
                    break;
                default:
                    env = World.Environment.NORMAL;
                    break;
            }

            worldMap.put(args[0],env);
        }
    }

    public HashMap<String, World.Environment> getWorlds() {
        return this.worldMap;
    }

    public Material getGateBlock() {
        return Material.getMaterial(this.gateBlock);
    }

    public Material getBaseBlock() {
        return Material.getMaterial(this.baseBlock);
    }




}
