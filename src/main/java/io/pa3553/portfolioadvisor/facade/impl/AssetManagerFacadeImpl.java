package io.pa3553.portfolioadvisor.facade.impl;

import io.pa3553.portfolioadvisor.exception.CryptoCurrencyNotFoundException;
import io.pa3553.portfolioadvisor.exception.WishListInvalidInputException;
import io.pa3553.portfolioadvisor.exception.WishListNotFoundException;
import io.pa3553.portfolioadvisor.facade.AssetManagerFacade;
import io.pa3553.portfolioadvisor.entity.CryptoCurrency;
import io.pa3553.portfolioadvisor.entity.WishList;
import io.pa3553.portfolioadvisor.entity.WishListCryptoCurrency;
import io.pa3553.portfolioadvisor.model.AddCryptoCurrencyContext;
import io.pa3553.portfolioadvisor.model.RemoveCryptoContext;
import io.pa3553.portfolioadvisor.service.CryptoCurrencyService;
import io.pa3553.portfolioadvisor.service.WishListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AssetManagerFacadeImpl implements AssetManagerFacade
{
    private final WishListService wishListService;
    private final CryptoCurrencyService cryptoCurrencyService;

    @Autowired
    public AssetManagerFacadeImpl(
            WishListService wishListService, CryptoCurrencyService cryptoCurrencyService
    )
    {
        this.wishListService = wishListService;
        this.cryptoCurrencyService = cryptoCurrencyService;
    }

    @Override
    public void addWishListSymbol(Long wishListId, String symbol)
            throws WishListNotFoundException, CryptoCurrencyNotFoundException, WishListInvalidInputException
    {
        boolean hasSymbol = wishListService.existsCryptoCurrencySymbol(wishListId, symbol);

        if (hasSymbol)
        {
            log.error("Wishlist {} not found!", wishListId);
            String errorMessage = "Wish list " + wishListId + " not found!";
            throw new WishListNotFoundException(errorMessage);
        }

        CryptoCurrency cryptoCurrency = cryptoCurrencyService.findCryptoCurrency(symbol);

        WishListCryptoCurrency wishListCryptoCurrency = new WishListCryptoCurrency(cryptoCurrency);
        AddCryptoCurrencyContext context = new AddCryptoCurrencyContext(wishListId, wishListCryptoCurrency);
        wishListService.addCryptoCurrency(context);

        log.info("CryptoCurrency {'id': {}, 'name': '{}', symbol: '{}'} was successfully added to Wishlist {'id': {}, 'name': {}}!",
                cryptoCurrency.getId(), cryptoCurrency.getName(), cryptoCurrency.getSymbol(), wishListId, wishListId);
    }

    @Override
    public void removeElement(Long wishListId, String symbol)
            throws WishListNotFoundException, CryptoCurrencyNotFoundException, WishListInvalidInputException
    {
        boolean hasSymbol = wishListService.existsCryptoCurrencySymbol(wishListId, symbol);

        if (hasSymbol)
        {
            log.error("Wishlist {} not found!", wishListId);
            String errorMessage = "Wish list " + wishListId + " not found!";
            throw new WishListNotFoundException(errorMessage);
        }

        WishList wishList = wishListService.getWishlist(wishListId);
        WishListCryptoCurrency wishListCryptoCurrency = wishListService.findCryptoCurrency(wishList, symbol);

        RemoveCryptoContext context = new RemoveCryptoContext(wishListId, wishListCryptoCurrency);
        wishListService.removeCryptoCurrency(context);

        log.info("CryptoCurrency {'id': {}, 'name': '{}', symbol: '{}'} was successfully removed from Wishlist {'id': {}, 'name': {}}!",
                wishListCryptoCurrency.getId(), wishListCryptoCurrency.getCryptoCurrency().getName(),
                wishListCryptoCurrency.getCryptoCurrency().getSymbol(), wishList.getId(), wishList.getName()
        );
    }
}
