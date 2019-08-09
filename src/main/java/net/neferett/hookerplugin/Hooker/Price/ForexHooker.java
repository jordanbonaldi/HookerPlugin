package net.neferett.hookerplugin.Hooker.Price;

import lombok.Data;
import net.neferett.coreengine.Utils.ClassSerializer;
import net.neferett.hookerplugin.Hooker.Hooker;
import net.neferett.hookerplugin.Hooker.HookerTask.CryptoHookerTask;
import net.neferett.hookerplugin.Hooker.HookerTask.ForexHookerTask;
import net.neferett.hookerplugin.Hooker.HookerTask.HookerTastManager;
import net.neferett.hookerplugin.Instances.ForexResponse;
import net.neferett.hookerplugin.Instances.Pair;
import net.neferett.redisapi.Utils.SerializationUtils;
import org.json.JSONArray;

import java.math.BigDecimal;

public class ForexHooker extends Hooker {

    private String url;

    public ForexHooker() {
        super("ForexHooker");

        this.url = "https://www.freeforexapi.com/api/";
    }

    public ForexResponse getLastPriceObject(String symbol) {
        return (ForexResponse) SerializationUtils.deSerialize(ForexResponse.class, this.hookHttpData(this.url + "live?pairs=" + symbol).toString());
    }

    @Override
    public void pushPairs() {
        JSONArray array = this.hookHttpData(this.url + "live").getJSONArray("supportedPairs");

        array.toList().stream().map(e -> (String)e).forEach(e -> {
            this.pushPair(new Pair(e, "Forex", new BigDecimal(0)));
        });
    }

    @Override
    public void hook() {
        this.trades.stream().filter(e -> e.getAsset().equalsIgnoreCase("forex")).forEach(e -> HookerTastManager.getInstance().addTask(new ForexHookerTask(e.getPair(), this)));
    }
}
