package net.neferett.hookerplugin.Instances;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForexResponse {

    private HashMap<String, ForexRates> rates;

    private int code;
}
