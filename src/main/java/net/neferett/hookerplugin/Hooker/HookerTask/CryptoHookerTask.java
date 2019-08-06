package net.neferett.hookerplugin.Hooker.HookerTask;

import com.webcerebrium.binance.api.BinanceApi;
import com.webcerebrium.binance.api.BinanceApiException;
import com.webcerebrium.binance.datatype.BinanceEventDepthUpdate;
import com.webcerebrium.binance.datatype.BinanceSymbol;
import com.webcerebrium.binance.websocket.BinanceWebSocketAdapterDepth;
import lombok.SneakyThrows;
import net.neferett.hookerplugin.Hooker.Hooker;
import net.neferett.hookerplugin.Hooker.Price.CryptoHooker;
import net.neferett.tradingplugin.Trade.Trade;

public class CryptoHookerTask extends HookerTask {

    private BinanceApi api;

    public CryptoHookerTask(String symbol, Hooker hooker) {
        super(symbol, hooker);

        this.api = ((CryptoHooker)hooker).getApi();
    }

    private BinanceWebSocketAdapterDepth streamPrices() {
        return new BinanceWebSocketAdapterDepth() {
            @Override
            public void onMessage(BinanceEventDepthUpdate event) throws BinanceApiException {
                Trade trade = hooker.findTradeBySymbol(symbol);

                System.out.println(trade);
            }
        };
    }

    @Override
    @SneakyThrows
    public void run() {
        BinanceSymbol symbol = new BinanceSymbol(this.symbol);

        this.api.websocketDepth(symbol, streamPrices());
    }
}
