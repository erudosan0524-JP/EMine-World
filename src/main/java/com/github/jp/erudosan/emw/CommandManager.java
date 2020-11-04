package com.github.jp.erudosan.emw;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor {

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



            return true;
        } else if(args[0].equalsIgnoreCase("schedule")) {
            return true;
        }

        return false;
    }
}
