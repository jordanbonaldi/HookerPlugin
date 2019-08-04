package net.neferett.hookerplugin.HookerManager;

import lombok.Data;
import lombok.experimental.Delegate;
import net.neferett.coreengine.CoreEngine;
import net.neferett.coreengine.Processors.Logger.Logger;
import net.neferett.hookerplugin.Hooker.Hooker;
import net.neferett.hookerplugin.Hooker.Pair.Pair;
import net.neferett.hookerplugin.HookerPlugin;
import net.neferett.redisapi.RedisAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class HookerManager {

    private List<Hooker> hookers = new ArrayList<>();

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

    public void hookAllData() {
        this.hookers.forEach(e -> {
            System.out.println(e.getName());

            e.hook();
        });
    }

}
