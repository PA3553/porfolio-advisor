package io.pa3553.portfolioadvisor.service.impl;

import com.litesoftwares.coingecko.CoinGeckoApiClient;
import com.litesoftwares.coingecko.domain.Coins.CoinFullData;
import com.litesoftwares.coingecko.domain.Coins.MarketData;
import com.litesoftwares.coingecko.exception.CoinGeckoApiException;
import io.pa3553.portfolioadvisor.model.response.CGCoin;
import io.pa3553.portfolioadvisor.service.CryptoTickerService;
import io.pa3553.portfolioadvisor.utils.CoinGeckoCurrency;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CryptoTickerServiceImpl implements CryptoTickerService
{
    private final CoinGeckoApiClient client;

    @Autowired
    public CryptoTickerServiceImpl(CoinGeckoApiClient client)
    {
        this.client = client;
    }

    public void shutdown()
    {
        client.shutdown();
    }

    @Override
    public boolean isAlive() {
        try
        {
            client.ping();
            return true;
        }
        catch (CoinGeckoApiException exception)
        {
            log.error(exception.getMessage());
            return false;
        }
    }

    @Override
    public CGCoin getCoinData(String symbol) {
        CoinFullData coinData = client.getCoinById(symbol);
        MarketData marketData = coinData.getMarketData();

        return new CGCoin(
                coinData.getName(),
                coinData.getSymbol(),
                marketData.getCurrentPrice().get(CoinGeckoCurrency.EUR.getSymbol()),
                marketData.getAth().get(CoinGeckoCurrency.EUR.getSymbol()),
                marketData.getAthChangePercentage().get(CoinGeckoCurrency.EUR.getSymbol())
        );
    }

    @Override
    public Map<String, Double> getCurrentPrices(List<String> symbols) {
        return null;
    }
}
