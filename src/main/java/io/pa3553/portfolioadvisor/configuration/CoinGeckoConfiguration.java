package io.pa3553.portfolioadvisor.configuration;

import com.litesoftwares.coingecko.CoinGeckoApiClient;
import com.litesoftwares.coingecko.impl.CoinGeckoApiClientImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoinGeckoConfiguration
{
    @Bean
    public CoinGeckoApiClient coinGeckoApiClient()
    {
        return new CoinGeckoApiClientImpl();
    }
}
