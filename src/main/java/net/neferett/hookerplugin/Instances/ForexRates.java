package net.neferett.hookerplugin.Instances;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForexRates {

    private Float rate;

    private long timestamp;
}
