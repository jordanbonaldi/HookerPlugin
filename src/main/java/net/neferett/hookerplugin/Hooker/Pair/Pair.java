package net.neferett.hookerplugin.Hooker.Pair;

import lombok.*;
import net.neferett.redisapi.Annotations.Redis;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Redis(db = 2, folder = true)
public class Pair {

    @NonNull
    private String symbol;

    @NonNull
    private String type;

    @NonNull
    private String priceCurrency;

    @NonNull
    private String marginCurrency;

    @NonNull
    private String description;

    @NonNull
    private Float lastBidPrice;

    @NonNull
    private Float lastAskPrice;
}
