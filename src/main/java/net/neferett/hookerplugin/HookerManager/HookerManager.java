package net.neferett.hookerplugin.HookerManager;

import lombok.Data;
import lombok.SneakyThrows;
import net.neferett.coreengine.CoreEngine;
import net.neferett.coreengine.Processors.Logger.Logger;
import net.neferett.hookerplugin.Hooker.Hooker;
import net.neferett.hookerplugin.Hooker.HookerTask.HookerTask;
import net.neferett.hookerplugin.Instances.Pair;
import net.neferett.hookerplugin.HookerPlugin;
import net.neferett.redisapi.RedisAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class HookerManager {

    private List<Hooker> hookers = new ArrayList<>();

    private HookerTask hookerTask;

    private Thread hookerThread;

    private boolean stopHooking = false;

    protected RedisAPI redisAPI = CoreEngine.getInstance().getRedisAPI();

    public static HookerManager getInstance() {
        return HookerPlugin.getInstance().getManager();
    }

    public void addHooker(Hooker hooker) {
        this.hookers.add(hooker);
        Logger.log("Adding Hooker : " + hooker.getName());
    }

    public Pair getPair(String symbol) {
        return Objects.requireNonNull(this.hookers.stream().findFirst().orElse(null)).getPair(symbol);
    }

    public void hookAllPairs() {
        this.hookers.forEach(Hooker::pushPairs);
    }

    public void hookAllTrades() {
        this.hookerTask = new HookerTask("HookerManager:41 - Hooking", null) {
            @Override
            @SneakyThrows
            public void run() {
                while (!stopHooking) {
                    hookers.forEach(Hooker::testAndHook);
                    Thread.sleep(1000);
                }
            }
        };

        this.hookerThread = new Thread(this.hookerTask);

        this.hookerThread.start();
    }

}
