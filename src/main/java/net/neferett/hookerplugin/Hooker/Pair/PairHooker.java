package net.neferett.hookerplugin.Hooker.Pair;

import net.neferett.hookerplugin.Hooker.Hooker;
import org.json.JSONObject;

public class PairHooker extends Hooker {

    public PairHooker() {
        super("https://simplefx.com/utils/instruments.json", "PairHooker");
    }

    public void hook() {
        JSONObject obj = this.hookHttpData();

        System.out.println(obj);

        obj.keySet().forEach(e -> {
            JSONObject jsonPair = obj.getJSONObject(e);

            this.pushPair(new Pair(
                    jsonPair.getString("symbol"),
                    jsonPair.getString("type"),
                    jsonPair.getString("priceCurrency"),
                    jsonPair.getString("marginCurrency"),
                    jsonPair.getString("description"),
                    jsonPair.getJSONObject("quote").getFloat("b"),
                    jsonPair.getJSONObject("quote").getFloat("a")
            ));
        });

    }
}
