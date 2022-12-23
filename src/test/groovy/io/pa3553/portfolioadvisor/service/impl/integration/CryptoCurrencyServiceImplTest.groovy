package io.pa3553.portfolioadvisor.service.impl.integration

import io.pa3553.portfolioadvisor.model.CreateCryptoCurrencyContext
import io.pa3553.portfolioadvisor.service.CryptoCurrencyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
class CryptoCurrencyServiceImplTest extends Specification
{
    @Autowired
    CryptoCurrencyService cryptoCurrencyService

    def cleanup()
    {
//        wishListRepository.deleteAll()
//        wishListCryptoCurrencyRepository.deleteAll()
//        cryptoCurrencyRepository.deleteAll()
    }

    def "test createCryptoCurrency"() {
        given:
        def coinName = "Ethereum"
        def coinSymbol = "ETH"
        def context = new CreateCryptoCurrencyContext(coinName, coinSymbol)

        when:
        def cryptoCurrency = cryptoCurrencyService.createCryptoCurrency(context)

        then:
        cryptoCurrency
    }

    def "test findCryptoCurrency"() {
        given:
        def coinName = "Ethereum"
        def coinSymbol = "ETH"
        def context = new CreateCryptoCurrencyContext(coinName, coinSymbol)

        when:
        def cryptoCurrency = cryptoCurrencyService.createCryptoCurrency(context)

        then:
        cryptoCurrency
        def cryptoCurrencyDB = cryptoCurrencyService.findCryptoCurrency(cryptoCurrency.id)
        cryptoCurrency == cryptoCurrencyDB
    }

    def "test testFindCryptoCurrency"() {
        given:
        def coinName = "Ethereum"
        def coinSymbol = "ETH"
        def context = new CreateCryptoCurrencyContext(coinName, coinSymbol)

        when:
        def cryptoCurrency = cryptoCurrencyService.createCryptoCurrency(context)

        then:
        cryptoCurrency
        def cryptoCurrencyDB = cryptoCurrencyService.findCryptoCurrency(coinSymbol)
        cryptoCurrency == cryptoCurrencyDB
    }

    def "test removeCryptoCurrency"() {
        given:
        def coinName = "Ethereum"
        def coinSymbol = "ETH"
        def context = new CreateCryptoCurrencyContext(coinName, coinSymbol)

        when:
        def cryptoCurrency = cryptoCurrencyService.createCryptoCurrency(context)

        then:
        cryptoCurrency
        def cryptoCurrencyDB = cryptoCurrencyService.findCryptoCurrency(cryptoCurrency.id)
        cryptoCurrency == cryptoCurrencyDB

        when:
        cryptoCurrencyService.removeCryptoCurrency(cryptoCurrency.id)

        then:
        !cryptoCurrencyService.findCryptoCurrency(cryptoCurrency.id)
    }
}
