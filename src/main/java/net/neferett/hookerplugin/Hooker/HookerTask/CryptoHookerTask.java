package net.neferett.hookerplugin.Hooker.HookerTask;

import com.google.gson.annotations.SerializedName;
import com.webcerebrium.binance.api.BinanceApi;
import com.webcerebrium.binance.datatype.BinanceEventDepthUpdate;
import com.webcerebrium.binance.datatype.BinanceSymbol;
import com.webcerebrium.binance.websocket.BinanceWebSocketAdapterDepth;
import lombok.SneakyThrows;
import net.neferett.hookerplugin.Hooker.Hooker;
import net.neferett.hookerplugin.Hooker.Price.CryptoHooker;
import net.neferett.tradingplugin.Manager.TradeManager;
import net.neferett.tradingplugin.Trade.Trade;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.math.BigDecimal;
import java.util.Objects;

public class CryptoHookerTask extends HookerTask {

    private BinanceApi api;

    private BinanceSymbol binanceSymbol;

    @SneakyThrows
    public CryptoHookerTask(String symbol, Hooker hooker) {
        super(symbol, hooker);

        this.binanceSymbol = new BinanceSymbol(symbol);
        this.api = ((CryptoHooker)hooker).getApi();
    }

    @Override
    @SneakyThrows
    public void run() {
        Trade trade;

        while ((trade = hooker.findTradeBySymbol(symbol)) != null) {
            System.out.println(this.api.pricesMap().get(this.symbol));
            TradeManager.getInstance().updatePrice(this.api.pricesMap().get(this.symbol), trade);
        }
    }
}
