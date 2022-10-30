package io.pa3553.portfolioadvisor.facade;

import io.pa3553.portfolioadvisor.exception.CryptoCurrencyNotFoundException;
import io.pa3553.portfolioadvisor.exception.WishListInvalidInputException;
import io.pa3553.portfolioadvisor.exception.WishListNotFoundException;

/**
 * Facade to manage user portfolio and wish list
 */
public interface AssetManagerFacade
{
    /**
     * Add a cryptocurrency to the wish list
     *
     * @param wishListId wish list id
     * @param symbol cryptocurrency symbol
     * @throws WishListNotFoundException when wish list does not exist in the database
     * @throws CryptoCurrencyNotFoundException when cryptocurrency does not exist in the database
     */
    void addWishListSymbol(Long wishListId, String symbol)
            throws WishListNotFoundException, CryptoCurrencyNotFoundException, WishListInvalidInputException;

    /**
     * Remove a cryptocurrency from a wishlist
     *
     * @param wishListId wish list id
     * @param symbol cryptocurrency symbol
     * @throws WishListNotFoundException when wish list does not exist in the database
     * @throws CryptoCurrencyNotFoundException when cryptocurrency does not exist in the database
     */
    void removeElement(Long wishListId, String symbol) throws WishListNotFoundException, CryptoCurrencyNotFoundException, WishListInvalidInputException;
}
