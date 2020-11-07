package com.github.jp.erudosan.emw;

import com.github.jp.erudosan.emw.task.SchedulerTask;
import com.github.jp.erudosan.emw.utils.Config;
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import com.onarandombox.MultiversePortals.MultiversePortals;
import com.onarandombox.MultiversePortals.PortalLocation;
import com.sk89q.worldedit.*;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Main extends JavaPlugin {

    private Main instance;

    @Getter
    private Config myConfig;

    @Getter
    private MultiverseCore mvCore;

    @Getter
    private MultiversePortals mvPortals;

    @Getter
    private WorldEditPlugin we;

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
        task.runTaskTimer(getInstance(), 0L, 20 * 60);

        //Command
        getServer().getPluginCommand("emw").setExecutor(new CommandManager(getInstance()));

        //Multiverse-API
        mvCore = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        mvPortals = (MultiversePortals) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Portals");

        //WorldEdit API
        we = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
    }

    private Main getInstance() {
        return this.instance;
    }

    private void setInstance(Main main) {
        this.instance = main;
    }

    public void createWorld() {
        Random rand = new Random();

        Bukkit.getServer().broadcastMessage("ワールドを再生成中です・・・");

        for (String name : this.getMyConfig().getWorlds().keySet()) {
            if (getMvCore().getMVWorldManager().loadWorld(name)) {
                this.getMvCore().getMVWorldManager().regenWorld(name, true, true, rand.toString());
            } else {
                this.getMvCore().getMVWorldManager().addWorld(name, this.getMyConfig().getWorlds().get(name), rand.toString(), WorldType.NORMAL, false, null);
            }

            createGate(name);
        }

        Bukkit.getServer().broadcastMessage("ワールドの再生成に成功しました");
    }

    public void createGate(String worldName) {
        if (!this.getMvCore().getMVWorldManager().loadWorld(worldName)) {
            return;
        }

        MultiverseWorld mvWorld = this.getMvCore().getMVWorldManager().getMVWorld(worldName);
        World world = this.getServer().getWorld(worldName);
        Location loc = mvWorld.getSpawnLocation();

        Location min = new Location(world, loc.getBlockX() + 5, loc.getBlockY(), loc.getBlockZ());
        Location max = new Location(world, loc.getBlockX() + 3, loc.getBlockY() + 4, loc.getBlockZ());

        org.bukkit.util.Vector minVec = new org.bukkit.util.Vector(min.getX(), min.getY(), min.getZ());
        org.bukkit.util.Vector maxVec = new org.bukkit.util.Vector(max.getX(), max.getY(), max.getZ());

        createGateFrame(this.getMyConfig().getGateBlock(), min, max);

        PortalLocation portalLocation = new PortalLocation(minVec, maxVec, mvWorld);

        mvPortals.getPortalManager().addPortal(mvWorld, worldName, null, portalLocation);

        Bukkit.getServer().broadcastMessage("ゲートの作成が完了しました。");
    }

    public void createGateFrame(Material material, Location min, Location max) {
        for (int x = min.getBlockX(); x < max.getBlockX(); x++) {
            for (int y = min.getBlockY(); y < max.getBlockY(); y++) {
                for (int z = min.getBlockZ(); z < max.getBlockZ(); z++) {

                    Location loc = new Location(min.getWorld(), x, y, z);
                    Block block = loc.getBlock();

                    if (y > min.getBlockY() || y < max.getBlockY()) {
                        if (x != min.getBlockX() || x != max.getBlockX()) {
                            block.setType(Material.AIR);
                            continue;
                        }
                    }

                    block.setType(material);

                }
            }
        }

    }
}
