package net.neferett.hookerplugin;

import com.webcerebrium.binance.api.BinanceApi;
import com.webcerebrium.binance.api.BinanceApiException;
import com.webcerebrium.binance.datatype.BinanceEventDepthUpdate;
import com.webcerebrium.binance.datatype.BinanceSymbol;
import com.webcerebrium.binance.websocket.BinanceWebSocketAdapterDepth;
import lombok.Getter;
import net.neferett.coreengine.CoreEngine;
import net.neferett.coreengine.Processors.Plugins.ExtendablePlugin;
import net.neferett.coreengine.Processors.Plugins.Plugin;
import net.neferett.hookerplugin.Hooker.HookerTask.HookerTastManager;
import net.neferett.hookerplugin.Hooker.Price.CryptoHooker;
import net.neferett.hookerplugin.Hooker.Price.PriceHooker;
import net.neferett.hookerplugin.HookerManager.HookerManager;
import org.eclipse.jetty.websocket.api.Session;

@Plugin(name = "HookerPlugin", configPath = "HookerPlugin/config.json")
@Getter
public class HookerPlugin extends ExtendablePlugin {

    private HookerManager manager;

    private HookerTastManager hookerTastManager;

    public static HookerPlugin getInstance() {
        return (HookerPlugin) CoreEngine.getInstance().getPlugin(HookerPlugin.class);
    }

    @Override
    public void onEnable() {
        this.manager = new HookerManager();
        this.hookerTastManager = new HookerTastManager();

        this.manager.addHooker(new CryptoHooker());

        /** Update all datas **/
        this.manager.hookAllPairs();

        this.manager.hookAllTrades();

    }

    @Override
    public void onDisable() {

    }
}
