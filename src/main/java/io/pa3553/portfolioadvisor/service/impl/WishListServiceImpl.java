package io.pa3553.portfolioadvisor.service.impl;

import io.pa3553.portfolioadvisor.exception.*;
import io.pa3553.portfolioadvisor.entity.WishList;
import io.pa3553.portfolioadvisor.entity.WishListCryptoCurrency;
import io.pa3553.portfolioadvisor.model.AddCryptoCurrencyContext;
import io.pa3553.portfolioadvisor.model.RemoveCryptoContext;
import io.pa3553.portfolioadvisor.model.UpdateWishlistDetailsContext;
import io.pa3553.portfolioadvisor.repository.WishListCryptoCurrencyRepository;
import io.pa3553.portfolioadvisor.repository.WishListRepository;
import io.pa3553.portfolioadvisor.service.WishListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class WishListServiceImpl implements WishListService
{
    private final WishListRepository wishListRepository;
    private final WishListCryptoCurrencyRepository wishListCryptoCurrencyRepository;

    @Autowired
    public WishListServiceImpl(WishListRepository wishListRepository, WishListCryptoCurrencyRepository wishListCryptoCurrencyRepository)
    {
        this.wishListRepository = wishListRepository;
        this.wishListCryptoCurrencyRepository = wishListCryptoCurrencyRepository;
    }

    @Override
    public WishList createWishList(String name) throws WishListNameRequiredException
    {
        if (name.isEmpty())
        {
            throw new WishListNameRequiredException("Wish list name is required");
        }

        WishList wishList = new WishList(name);
        WishList wishListCreated = wishListRepository.save(wishList);

        log.info("Wishlist {} was created successfully!", name);
        return wishListCreated;
    }

    @Override
    public WishList updateWishListDetails(UpdateWishlistDetailsContext context)
            throws WishListNotFoundException, WishListDescriptionRequiredException, WishListNameRequiredException {
        Optional<WishList> wishListOpt = wishListRepository.findById(context.getWishListId());

        if (wishListOpt.isEmpty())
        {
            String errorMessage = "Wish list " + context.getWishListId() + " not found!";
            throw new WishListNotFoundException(errorMessage);
        }

        WishList wishList = wishListOpt.get();

        if (context.getName() == null || context.getName().isBlank())
        {
            throw new WishListNameRequiredException("Wish list name is required");
        }

        if (context.getDescription() == null || context.getDescription().isBlank())
        {
            throw new WishListDescriptionRequiredException("Wish list name is required");
        }

        wishList.setName(context.getName());
        wishList.setDescription(context.getDescription());
        WishList wishListUpdated = wishListRepository.save(wishList);

        log.info("Wishlist {'id': {}, 'name': '{}', description: '{}'} was updated successfully!",
                wishListUpdated.getId(), wishListUpdated.getName(), wishListUpdated.getDescription());
        return wishListUpdated;
    }

    @Override
    public Set<WishListCryptoCurrency> getCryptoCurrencies(Long wishListId) throws WishListNotFoundException
    {
        Optional<WishList> wishListOpt = wishListRepository.findById(wishListId);

        if (wishListOpt.isEmpty())
        {
            String errorMessage = "Wish list " + wishListId + " not found!";
            throw new WishListNotFoundException(errorMessage);
        }

        return wishListOpt.get().getCryptoCurrencies();
    }

    @Override
    public WishList getWishlist(Long wishListId) throws WishListNotFoundException
    {
        Optional<WishList> wishListOpt = wishListRepository.findById(wishListId);

        return wishListOpt.orElseThrow(
                () -> new WishListNotFoundException("Wish list " + wishListId + " not found!")
        );
    }

    @Override
    public WishListCryptoCurrency addCryptoCurrency(AddCryptoCurrencyContext context)
            throws WishListInvalidInputException, WishListNotFoundException, CryptoCurrencyNotFoundException
    {
        if (context.getWishListId() == null)
        {
            throw new WishListInvalidInputException("Wish list can't be null");
        }
        {

        if (context.getCryptoCurrency() == null)
            throw new WishListInvalidInputException("Crypto currency can't be null");
        }

        WishList wishList = wishListRepository.findById(context.getWishListId()).orElseThrow(
                () -> new WishListNotFoundException("Wish list " + context.getWishListId() + " not found!")
        );

        WishListCryptoCurrency cryptoCurrency = context.getCryptoCurrency();
        wishList.getCryptoCurrencies().add(context.getCryptoCurrency());
        cryptoCurrency.setWishList(wishList);
        WishList savedWishList = wishListRepository.save(wishList);

        log.info("Crypto currency {'id': {}, 'name': '{}', symbol: '{}'} added successfully to wish list {}!",
                cryptoCurrency.getId(), cryptoCurrency.getCryptoCurrency().getName(),
                cryptoCurrency.getCryptoCurrency().getSymbol(), wishList.getId()
        );

        // get wish list cryptocurrency stored in the db
        return savedWishList.getCryptoCurrencies()
                .stream().filter(wishListCryptoCurrency ->
                        wishListCryptoCurrency.getCryptoCurrency().getSymbol()
                                .equals(cryptoCurrency.getCryptoCurrency().getSymbol())
                ).findFirst().orElseThrow(
                        () -> new CryptoCurrencyNotFoundException("Crypto currency " + cryptoCurrency.getCryptoCurrency().getSymbol() + " not found!")
                );
    }

    @Override
    public void removeCryptoCurrency(RemoveCryptoContext context)
            throws WishListInvalidInputException, WishListNotFoundException, CryptoCurrencyNotFoundException
    {
        if (context.getWishListId() == null)
        {
            throw new WishListInvalidInputException("Wish list id can't be null");
        }

        if (context.getCryptoCurrency() == null || context.getCryptoCurrency().getId() == null)
        {
            throw new WishListInvalidInputException("Crypto currency can't be null");
        }

        Optional<WishList> wishListOpt = wishListRepository.findById(context.getWishListId());
        WishList wishList = wishListOpt.orElseThrow(
                () -> new WishListNotFoundException("Wish list " + context.getWishListId() + " not found!")
        );

        Set<WishListCryptoCurrency> cryptoCurrencies = wishList.getCryptoCurrencies();
        Optional<WishListCryptoCurrency> cryptoCurrencyOpt = cryptoCurrencies.stream()
                .filter(wishListCryptoCurrency ->
                        Objects.equals(wishListCryptoCurrency.getId(), context.getCryptoCurrency().getId())
                ).findFirst();

        WishListCryptoCurrency cryptoCurrency = cryptoCurrencyOpt.orElseThrow(
                () -> new CryptoCurrencyNotFoundException("Crypto currency " + context.getCryptoCurrency().getId() + " not found!")
        );

        cryptoCurrency.setRemoved(true);
        wishListRepository.save(wishList);

        log.info("Crypto currency {'id': {}, 'name': '{}', symbol: '{}'} removed successfully from wish list {}!",
                cryptoCurrency.getId(), cryptoCurrency.getCryptoCurrency().getName(),
                cryptoCurrency.getCryptoCurrency().getSymbol(), wishList.getId()
        );
    }

    @Override
    public WishListCryptoCurrency findCryptoCurrency(WishList wishList, String symbol)
            throws WishListInvalidInputException
    {
        if (wishList == null || wishList.getId() == null)
        {
            throw new WishListInvalidInputException("Wish list can't be null");
        }

        if (symbol == null)
        {
            throw new WishListInvalidInputException("Symbol can't be null");
        }

        return wishListCryptoCurrencyRepository.findCryptoCurrencyById(wishList.getId(), symbol);
    }

    @Override
    public boolean existsCryptoCurrencySymbol(Long wishListId, String symbol)
            throws WishListInvalidInputException
    {
        if (wishListId == null)
        {
            throw new WishListInvalidInputException("Wish list id can't be null");
        }

        if (symbol == null)
        {
            throw new WishListInvalidInputException("Symbol can't be null");
        }

        return wishListRepository.existsCryptoCurrencyById(wishListId, symbol);
    }
}
