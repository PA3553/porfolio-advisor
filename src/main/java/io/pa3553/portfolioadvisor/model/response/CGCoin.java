package io.pa3553.portfolioadvisor.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CGCoin
{
    private String name;
    private String symbol;
    private double currentPrice;
    private double ath;
    private double athChangePercentage;
}
