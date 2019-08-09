package net.neferett.hookerplugin;

import lombok.Getter;
import net.neferett.coreengine.CoreEngine;
import net.neferett.coreengine.Processors.Logger.Logger;
import net.neferett.coreengine.Processors.Plugins.ExtendablePlugin;
import net.neferett.coreengine.Processors.Plugins.Plugin;
import net.neferett.hookerplugin.Hooker.HookerTask.HookerTastManager;
import net.neferett.hookerplugin.Hooker.Price.CryptoHooker;
import net.neferett.hookerplugin.Hooker.Price.ForexHooker;
import net.neferett.hookerplugin.HookerManager.HookerManager;

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
        this.manager.addHooker(new ForexHooker());

        /** Update all datas **/
        this.manager.hookAllPairs();

        Logger.log("Launching hooker for all running trades");

        this.manager.hookAllTrades();
    }

    @Override
    public void onDisable() {

    }
}
