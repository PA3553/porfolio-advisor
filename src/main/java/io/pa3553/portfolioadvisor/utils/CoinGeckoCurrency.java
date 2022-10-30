package io.pa3553.portfolioadvisor.utils;

public enum CoinGeckoCurrency
{
    USD("Dollar", "usd"),
    EUR("Euro", "eur"),
    GBP("Pound", "gbp");

    private final String name;
    private final String symbol;
    private CoinGeckoCurrency(String name, String symbol)
    {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName()
    {
        return name;
    }

    public String getSymbol()
    {
        return symbol;
    }
}
