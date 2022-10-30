package io.pa3553.portfolioadvisor.service.impl

import com.litesoftwares.coingecko.CoinGeckoApiClient
import com.litesoftwares.coingecko.CoinGeckoApiError
import com.litesoftwares.coingecko.domain.Coins.CoinFullData
import com.litesoftwares.coingecko.domain.Coins.MarketData
import com.litesoftwares.coingecko.exception.CoinGeckoApiException
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@ActiveProfiles("test")
class CryptoTickerServiceImplTestSpecification extends Specification
{
    def coinGeckoApiClient = Mock(CoinGeckoApiClient)
    def cryptoTickerService = new CryptoTickerServiceImpl(coinGeckoApiClient)

    def "check crypto api is alive >> success"()
    {
        given:
        def result

        when:
        result = cryptoTickerService.isAlive()
        coinGeckoApiClient.ping() >> "gecko says"

        then:
        result
    }

    def "check crypto api is alive >> failed"()
    {
        given:
        def result
        def error = new CoinGeckoApiError()
        def exception = new CoinGeckoApiException(error)

        when:
        result = cryptoTickerService.isAlive()

        then:
        1 * coinGeckoApiClient.ping() >> { throw exception }
        !result
    }

    def "get coin data >> success"()
    {
        given:
        def marketData = new MarketData()

        def symbol = "eth"
        def name = "ethereum"
        def baseCurrency = "eur"

        def ath = new HashMap()
        ath.put(baseCurrency, 4000d)
        marketData.setAth(ath)

        def changePercentages = new HashMap()
        changePercentages.put(baseCurrency, 50d)
        marketData.setAthChangePercentage(changePercentages)

        def currentPrices = new HashMap()
        currentPrices.put(baseCurrency, 2000d)
        marketData.setCurrentPrice(currentPrices)

        def coinFullData = new CoinFullData()
        coinFullData.setName(name)
        coinFullData.setSymbol(symbol)

        coinFullData.setMarketData(marketData)

        when:
        def result = cryptoTickerService.getCoinData(symbol)

        then:
        1 * coinGeckoApiClient.getCoinById(symbol) >> coinFullData
        result
        result.getName() == name
        result.getSymbol() == symbol
        result.getAth() == 4000d
        result.getAthChangePercentage() == 50d
        result.getCurrentPrice() == 2000d
    }

    def "get coin data >> empty coin symbol"()
    {
        given:
        def name = ""

        when:
        cryptoTickerService.getCoinData(name)

        then:
        thrown(NullPointerException)
    }

    def "GetCurrentPrices"()
    {
    }
}
