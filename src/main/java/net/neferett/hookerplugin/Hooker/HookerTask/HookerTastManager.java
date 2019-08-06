package net.neferett.hookerplugin.Hooker.HookerTask;

import net.neferett.hookerplugin.HookerPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HookerTastManager {

    HashMap<HookerTask, Thread> hookerTasks = new HashMap<>();

    public static HookerTastManager getInstance() {
        return HookerPlugin.getInstance().getHookerTastManager();
    }

    public boolean isExist(String symbol) {
        return this.hookerTasks.keySet().stream().filter(e -> e.getSymbol().equalsIgnoreCase(symbol)).findFirst().orElse(null) != null;
    }

    public void addTask(HookerTask task) {
        if (this.isExist(task.getSymbol()))
            return;

        Thread thread = new Thread(task);

        thread.start();

        this.hookerTasks.put(task, thread);
    }

}
