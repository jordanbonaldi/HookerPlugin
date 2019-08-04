package net.neferett.hookerplugin.Hooker;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.neferett.coreengine.Utils.UrlRequester;
import net.neferett.hookerplugin.Hooker.Pair.Pair;
import net.neferett.hookerplugin.HookerManager.HookerManager;
import org.json.JSONObject;

import java.util.HashMap;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class Hooker extends HookerManager {

    private final String url;

    private final String name;

    protected void pushPair(Pair pair) {
        this.redisAPI.serialize(pair, pair.getSymbol());
    }

    public Pair getPair(String symbol) {
        return this.redisAPI.contains(Pair.class, symbol).values().stream()
                .map(e -> (Pair)e)
                .filter(e -> e.getSymbol().equalsIgnoreCase(symbol))
                .findFirst().orElse(null);
    }

    public abstract void hook();

    protected JSONObject hookHttpData() {
        try {
            return new JSONObject(UrlRequester.launchRequest(this.url, new HashMap<>()));
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
