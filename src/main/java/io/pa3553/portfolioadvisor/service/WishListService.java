package io.pa3553.portfolioadvisor.service;

import io.pa3553.portfolioadvisor.exception.*;
import io.pa3553.portfolioadvisor.entity.WishList;
import io.pa3553.portfolioadvisor.entity.WishListCryptoCurrency;
import io.pa3553.portfolioadvisor.model.AddCryptoCurrencyContext;
import io.pa3553.portfolioadvisor.model.RemoveCryptoContext;
import io.pa3553.portfolioadvisor.model.UpdateWishlistDetailsContext;

import java.util.Set;

public interface WishListService
{
    /**
     * Create a new wish list
     *
     * @param name wish list name
     * @return the new wish list
     * @throws WishListNameRequiredException when name is null
     */
    WishList createWishList(String name) throws WishListNameRequiredException;

    /**
     * Update wish list name and description
     *
     * @param context wish list update context
     * @return wish list updated
     * @throws WishListNotFoundException when wish list does not exist in the database
     * @throws WishListDescriptionRequiredException when wish list description is missing
     * @throws WishListNameRequiredException when wish list name is missing
     */
    WishList updateWishListDetails(UpdateWishlistDetailsContext context)
            throws WishListNotFoundException, WishListDescriptionRequiredException, WishListNameRequiredException;

    /**
     * Get all cryptocurrencies in a wish list
     *
     * @param wishListId wish list id
     * @return list of cryptocurrency in the wish list
     * @throws WishListNotFoundException when wish list does not exist in the database
     */
    Set<WishListCryptoCurrency> getCryptoCurrencies(Long wishListId) throws WishListNotFoundException;
// TODO
    WishList getWishlist(Long wishListId) throws WishListNotFoundException;

    WishListCryptoCurrency addCryptoCurrency(AddCryptoCurrencyContext context)
            throws WishListInvalidInputException, WishListNotFoundException, CryptoCurrencyNotFoundException;

    void removeCryptoCurrency(RemoveCryptoContext context)
            throws WishListInvalidInputException, WishListNotFoundException, CryptoCurrencyNotFoundException;

    WishListCryptoCurrency findCryptoCurrency(WishList wishList, String symbol) throws WishListInvalidInputException;

    boolean existsCryptoCurrencySymbol(Long wishListId, String symbol) throws WishListInvalidInputException;
}
