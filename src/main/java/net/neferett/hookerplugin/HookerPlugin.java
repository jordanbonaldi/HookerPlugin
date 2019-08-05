package net.neferett.hookerplugin;

import lombok.Getter;
import net.neferett.coreengine.CoreEngine;
import net.neferett.coreengine.Processors.Plugins.ExtendablePlugin;
import net.neferett.coreengine.Processors.Plugins.Plugin;
import net.neferett.hookerplugin.Hooker.Pair.PairHooker;
import net.neferett.hookerplugin.Hooker.Price.PriceHooker;
import net.neferett.hookerplugin.HookerManager.HookerManager;

@Plugin(name = "HookerPlugin", configPath = "HookerPlugin/config.json")
@Getter
public class HookerPlugin extends ExtendablePlugin {

    private HookerManager manager;

    public static HookerPlugin getInstance() {
        return (HookerPlugin) CoreEngine.getInstance().getPlugin(HookerPlugin.class);
    }

    @Override
    public void onEnable() {
        this.manager = new HookerManager();

        this.manager.addHooker(new PairHooker());
        this.manager.addHooker(new PriceHooker(""));

        /** Update all datas **/
        this.manager.hookAllData();

    }

    @Override
    public void onDisable() {

    }
}
