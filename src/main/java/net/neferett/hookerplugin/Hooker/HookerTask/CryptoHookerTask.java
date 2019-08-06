package net.neferett.hookerplugin.Hooker.HookerTask;

import com.webcerebrium.binance.api.BinanceApi;
import com.webcerebrium.binance.datatype.BinanceEventDepthUpdate;
import com.webcerebrium.binance.datatype.BinanceSymbol;
import com.webcerebrium.binance.websocket.BinanceWebSocketAdapterDepth;
import lombok.SneakyThrows;
import net.neferett.hookerplugin.Hooker.Hooker;
import net.neferett.hookerplugin.Hooker.Price.CryptoHooker;
import net.neferett.tradingplugin.Manager.TradeManager;
import net.neferett.tradingplugin.Trade.Trade;

import java.math.BigDecimal;
import java.util.Objects;

public class CryptoHookerTask extends HookerTask {

    private BinanceApi api;

    public CryptoHookerTask(String symbol, Hooker hooker) {
        super(symbol, hooker);

        this.api = ((CryptoHooker)hooker).getApi();
    }

    private BinanceWebSocketAdapterDepth streamPrices() {
        return new BinanceWebSocketAdapterDepth() {
            @Override
            public void onMessage(BinanceEventDepthUpdate event) {
                BigDecimal lastPrice = Objects.requireNonNull(event.getBids().stream().min((a, b) -> b.getPrice().compareTo(a.getPrice())).orElse(null)).getPrice();

                Trade trade = hooker.findTradeBySymbol(symbol);

                TradeManager.getInstance().updatePrice(lastPrice, trade);
            }
        };
    }

    @Override
    @SneakyThrows
    public void run() {
        BinanceSymbol symbol = new BinanceSymbol(this.symbol);

        this.api.websocketDepth(symbol, this.streamPrices());
    }
}
