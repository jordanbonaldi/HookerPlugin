package net.neferett.hookerplugin.Hooker.HookerTask;

import lombok.SneakyThrows;
import net.neferett.hookerplugin.Hooker.Hooker;
import net.neferett.hookerplugin.Hooker.Price.ForexHooker;
import net.neferett.hookerplugin.Instances.ForexRates;
import net.neferett.tradingplugin.Manager.TradeManager;
import net.neferett.tradingplugin.Trade.Trade;

import java.math.BigDecimal;

public class ForexHookerTask extends HookerTask {

    private ForexHooker hooker;

    @SneakyThrows
    public ForexHookerTask(String symbol, Hooker hooker) {
        super(symbol, hooker);

        this.hooker = (ForexHooker) hooker;
    }

    @Override
    @SneakyThrows
    public void run() {
        Trade trade;

        long timestamp = 0;

        while ((trade = this.hooker.findTradeBySymbol(symbol)) != null) {
            ForexRates rate = this.hooker.getLastPriceObject(symbol).getRates().get(symbol);

            if (rate == null)
                break;

            if (timestamp == rate.getTimestamp())
                continue;

            timestamp = rate.getTimestamp();

            System.out.println(rate);
            TradeManager.getInstance().updatePrice(rate.getRate(), trade);
        }

        HookerTastManager.getInstance().removeTask(this);
    }
}
