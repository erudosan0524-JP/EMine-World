package com.github.jp.erudosan.emw;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class CommandManager implements CommandExecutor {

    private Main plugin;

    public CommandManager(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }

        if(args.length <= 0) {
            return true;
        }

        Player player = (Player) sender;

        if(args[0].equalsIgnoreCase("generate")) {
            plugin.createWorld();

            player.sendMessage(ChatColor.BOLD + "Generated Worlds!");


            return true;
        } else if(args[0].equalsIgnoreCase("schedule")) {
            for(int i=0; i < plugin.getMyConfig().getCalendars().size(); i++) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7===== &f&l予定されている時間 &7====="));

                String date = plugin.getMyConfig().getMonth(i) + "/" + plugin.getMyConfig().getDate(i) + " " + plugin.getMyConfig().getHour(i) + ":" + plugin.getMyConfig().getMinute(i);
                player.sendMessage("・" + date);

                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7========================"));
            }

            return true;
        } else if (args[0].equalsIgnoreCase("reload")) {
            plugin.getMyConfig().reload();

            player.sendMessage("Reload Complete! ");
            return true;
        } else if(args[0].equalsIgnoreCase("create")) {
            plugin.createGateFrame(Material.GOLD_BLOCK,player.getLocation(),new Location(player.getWorld(),player.getLocation().getX() + 4,player.getLocation().getY() + 5,player.getLocation().getZ()));
            return true;
        }

        return false;
    }
}
