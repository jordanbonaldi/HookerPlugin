package net.neferett.hookerplugin.Hooker.HookerTask;

import lombok.Data;
import net.neferett.hookerplugin.Hooker.Hooker;

@Data
public abstract class HookerTask implements Runnable {

    protected final String symbol;

    protected final Hooker hooker;

}
