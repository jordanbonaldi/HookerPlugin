package net.neferett.hookerplugin.Hooker.Price;

import com.webcerebrium.binance.api.BinanceApi;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import net.neferett.hookerplugin.Hooker.HookerTask.CryptoHookerTask;
import net.neferett.hookerplugin.Hooker.HookerTask.HookerTastManager;
import net.neferett.hookerplugin.Instances.Pair;

@EqualsAndHashCode(callSuper = true)
@Data
public class CryptoHooker extends PriceHooker {

    public CryptoHooker() {
        super("CryptoHooker");
    }

    private BinanceApi api = new BinanceApi();

    @Override
    @SneakyThrows
    public void pushPairs() {
        this.api.pricesMap().forEach((a, b) -> this.pushPair(new Pair(a, "Crypto", b)));
    }

    @Override
    @SneakyThrows
    public void hook() {
        this.trades.forEach(e -> HookerTastManager.getInstance().addTask(new CryptoHookerTask(e.getPair(), this)));
    }
}
