package io.pa3553.portfolioadvisor.service;

import io.pa3553.portfolioadvisor.model.response.CGCoin;

import java.util.List;
import java.util.Map;

public interface CryptoTickerService
{
    /**
     * Check if crypto ticker is available
     * @return true if ticker is available and false otherwise
     */
    boolean isAlive();

    /**
     * Get crypto currency details
     * @param symbol crypto currency symbol
     * @return crypto currency details
     */
    CGCoin getCoinData(String symbol);
    Map<String, Double> getCurrentPrices(List<String> symbols);
}
