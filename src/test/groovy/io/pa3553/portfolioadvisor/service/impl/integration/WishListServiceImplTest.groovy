package io.pa3553.portfolioadvisor.service.impl.integration

import io.pa3553.portfolioadvisor.exception.*
import io.pa3553.portfolioadvisor.entity.WishListCryptoCurrency
import io.pa3553.portfolioadvisor.model.AddCryptoCurrencyContext
import io.pa3553.portfolioadvisor.model.CreateCryptoCurrencyContext
import io.pa3553.portfolioadvisor.model.RemoveCryptoContext
import io.pa3553.portfolioadvisor.model.UpdateWishlistDetailsContext
import io.pa3553.portfolioadvisor.repository.CryptoCurrencyRepository
import io.pa3553.portfolioadvisor.repository.WishListCryptoCurrencyRepository
import io.pa3553.portfolioadvisor.repository.WishListRepository
import io.pa3553.portfolioadvisor.service.impl.CryptoCurrencyServiceImpl
import io.pa3553.portfolioadvisor.service.impl.WishListServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
class WishListServiceImplTest extends Specification
{
    @Autowired
    WishListServiceImpl wishListService

    @Autowired
    CryptoCurrencyServiceImpl cryptoCurrencyService

    @Autowired
    WishListRepository wishListRepository

    @Autowired
    WishListCryptoCurrencyRepository wishListCryptoCurrencyRepository

    @Autowired
    CryptoCurrencyRepository cryptoCurrencyRepository

    def cleanup()
    {
        wishListRepository.deleteAll()
        wishListCryptoCurrencyRepository.deleteAll()
        cryptoCurrencyRepository.deleteAll()
    }

    def "create wish list >> success"()
    {
        given:
        def name = "Test Wish List"

        when:
        def result = wishListService.createWishList(name)

        then:
        result && result.name == name

        cleanup:
        cleanup()
    }

    def "create wish list >> empty name"()
    {
        given:
        def name = ""

        when:
        wishListService.createWishList(name)

        then:
        thrown(WishListNameRequiredException)

        cleanup:
        cleanup()
    }

    def "update wish list >> success"() {
        given:
        def name = "Test Wish List"

        and:
        def newName = "New Wish List"
        def description = "New Description"
        def result = wishListService.createWishList(name)
        def context = new UpdateWishlistDetailsContext(result.id, newName, description)

        when:
        def updatedWishList = wishListService.updateWishListDetails(context)

        then:
        updatedWishList
        updatedWishList.name == newName
        updatedWishList.description == description

        cleanup:
        cleanup()
    }

    def "update wish list >> wish list not found"() {
        given:
        def newName = "New Wish List"
        def description = "New Description"
        def invalidWishlistId = 100L
        def context = new UpdateWishlistDetailsContext(invalidWishlistId, newName, description)

        when:
        wishListService.updateWishListDetails(context)

        then:
        thrown(WishListNotFoundException)

        cleanup:
        cleanup()
    }

    def "update wish list >> empty name"() {
        given:
        def name = "Test Wish List"

        and:
        def newName = ""
        def description = "New Description"
        def result = wishListService.createWishList(name)
        def context = new UpdateWishlistDetailsContext(result.id, newName, description)

        when:
        wishListService.updateWishListDetails(context)

        then:
        thrown(WishListNameRequiredException)

        cleanup:
        cleanup()
    }

    def "update wish list >> empty description"() {
        given:
        def name = "Test Wish List"

        and:
        def newName = "New Wish List"
        def description = ""
        def result = wishListService.createWishList(name)
        def context = new UpdateWishlistDetailsContext(result.id, newName, description)

        when:
        wishListService.updateWishListDetails(context)

        then:
        thrown(WishListDescriptionRequiredException)

        cleanup:
        cleanup()
    }

    def "add element to wish list >> success"() {
        given:
        def symbol = "eth"
        def coinName = "Ethereum"
        def name = "Wish List"
        def wishList = wishListService.createWishList(name)

        and:
        def context = new CreateCryptoCurrencyContext(coinName, symbol)
        def cryptoCurrency = cryptoCurrencyService.createCryptoCurrency(context)

        and:
        def wishListCryptoCurrency = new WishListCryptoCurrency(cryptoCurrency)
        def addCryptoContext = new AddCryptoCurrencyContext(wishList.id, wishListCryptoCurrency)

        when:
        wishListService.addCryptoCurrency(addCryptoContext)

        then:
        def result = wishListService.existsCryptoCurrencySymbol(wishList.id, symbol)
        result

        cleanup:
        cleanup()
    }

    def "add element to wish list >> null wishlist"() {
        given:
        def symbol = "eth"
        def coinName = "Ethereum"
        def context = new CreateCryptoCurrencyContext(coinName, symbol)
        def cryptoCurrency = cryptoCurrencyService.createCryptoCurrency(context)

        and:
        def wishListCryptoCurrency = new WishListCryptoCurrency(cryptoCurrency)
        def addCryptoContext = new AddCryptoCurrencyContext(null, wishListCryptoCurrency)

        when:
        wishListService.addCryptoCurrency(addCryptoContext)

        then:
        thrown(WishListInvalidInputException)

        cleanup:
        cleanup()
    }

    def "add element to wish list >> wish list not found"() {
        given:
        def symbol = "eth"
        def coinName = "Ethereum"
        def invalidWishlistId = 100L
        def context = new CreateCryptoCurrencyContext(coinName, symbol)
        def cryptoCurrency = cryptoCurrencyService.createCryptoCurrency(context)

        and:
        def wishListCryptoCurrency = new WishListCryptoCurrency(cryptoCurrency)
        def addCryptoContext = new AddCryptoCurrencyContext(invalidWishlistId, wishListCryptoCurrency)

        when:
        wishListService.addCryptoCurrency(addCryptoContext)

        then:
        thrown(WishListNotFoundException)

        cleanup:
        cleanup()
    }

    def "get wish list crypto currencies >> success"() {
        given:
        def symbol = "eth"
        def coinName = "Ethereum"
        def name = "Wish List"
        def wishList = wishListService.createWishList(name)
        def context = new CreateCryptoCurrencyContext(coinName, symbol)
        def cryptoCurrency = cryptoCurrencyService.createCryptoCurrency(context)

        and:
        def wishListCryptoCurrency = new WishListCryptoCurrency(cryptoCurrency)
        def addCryptoContext = new AddCryptoCurrencyContext(wishList.id, wishListCryptoCurrency)

        and:
        wishListService.addCryptoCurrency(addCryptoContext)

        when:
        def cryptoCurrencies = wishListService.getCryptoCurrencies(wishList.id)

        then:
        cryptoCurrencies && cryptoCurrencies.size() > 0

        cleanup:
        cleanup()
    }

    def "get wish list crypto currencies >> wish list not found"() {
        given:
        def invalidWishlistId = 100L

        when:
        wishListService.getCryptoCurrencies(invalidWishlistId)

        then:
        thrown(WishListNotFoundException)

        cleanup:
        cleanup()
    }

    def "get wish list >> success"() {
        given:
        def name = "Wish List"

        and:
        def wishList = wishListService.createWishList(name)

        when:
        def result = wishListService.getWishlist(wishList.id)

        then:
        result

        cleanup:
        cleanup()
    }

    def "get wish list >> wish list not found"() {
        given:
        def invalidWishlistId = 100L

        when:
        wishListService.getWishlist(invalidWishlistId)

        then:
        thrown(WishListNotFoundException)

        cleanup:
        cleanup()
    }

    def "remove element to wish list >> success"() {
        given:
        def symbol = "eth"
        def coinName = "Ethereum"
        def name = "Wish List"
        def wishList = wishListService.createWishList(name)
        def context = new CreateCryptoCurrencyContext(coinName, symbol)
        def cryptoCurrency = cryptoCurrencyService.createCryptoCurrency(context)

        and:
        def wishListCryptoCurrency = new WishListCryptoCurrency(cryptoCurrency)
        def AddCryptoContext = new AddCryptoCurrencyContext(wishList.id, wishListCryptoCurrency)

        and:
        def wishListCryptoCurrencySaved = wishListService.addCryptoCurrency(AddCryptoContext)
        def removeCryptoContext = new RemoveCryptoContext(wishList.id, wishListCryptoCurrencySaved)

        when:
        wishListService.removeCryptoCurrency(removeCryptoContext)

        then:
        def result = wishListService.existsCryptoCurrencySymbol(wishList.id, symbol)
        !result

        cleanup:
        cleanup()
    }
}
