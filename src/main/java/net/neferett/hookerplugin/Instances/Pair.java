package net.neferett.hookerplugin.Instances;

import lombok.*;
import net.neferett.redisapi.Annotations.Redis;

import java.math.BigDecimal;

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
    private BigDecimal lastPrice;
}
