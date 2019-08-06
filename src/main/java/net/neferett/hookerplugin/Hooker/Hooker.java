package net.neferett.hookerplugin.Hooker;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.neferett.coreengine.Utils.UrlRequester;
import net.neferett.hookerplugin.Instances.Pair;
import net.neferett.hookerplugin.HookerManager.HookerManager;
import net.neferett.tradingplugin.Trade.Trade;
import net.neferett.tradingplugin.Trade.TradeStatus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class Hooker extends HookerManager {

    private final String name;

    protected List<Trade> trades = new ArrayList<>();

    protected void pushPair(Pair pair) {
        this.redisAPI.serialize(pair, pair.getSymbol());
    }

    public Pair getPair(String symbol) {
        return this.redisAPI.contains(Pair.class, symbol).values().stream()
                .map(e -> (Pair)e)
                .filter(e -> e.getSymbol().equalsIgnoreCase(symbol))
                .findFirst().orElse(null);
    }

    public abstract void pushPairs();

    public abstract void hook();

    public Trade findTradeBySymbol(String symbol) {
        return this.trades.stream().filter(e -> e.getPair().equalsIgnoreCase(symbol) && e.getStatus() == TradeStatus.OPENED).findFirst().orElse(null);
    }

    private boolean newTrades() {
        List<Trade> newTrades = this.redisAPI.contains(Trade.class).values().stream().map(e -> (Trade) e).collect(Collectors.toList());

        if (this.trades.size() == 0) {
            this.trades = newTrades;

            return this.trades.size() != 0;
        }

        boolean equals = newTrades.equals(this.trades);

        if (!equals)
            this.trades = newTrades;

        return equals;
    }

    public void testAndHook() {
        if (this.newTrades())
            this.hook();
    }

    protected JSONObject hookHttpData(String url) {
        try {
            return new JSONObject(UrlRequester.launchRequest(url, new HashMap<>()));
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
