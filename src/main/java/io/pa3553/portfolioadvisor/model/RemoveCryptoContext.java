package io.pa3553.portfolioadvisor.model;

import io.pa3553.portfolioadvisor.entity.WishListCryptoCurrency;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RemoveCryptoContext
{
    private Long wishListId;
    private WishListCryptoCurrency cryptoCurrency;
}
