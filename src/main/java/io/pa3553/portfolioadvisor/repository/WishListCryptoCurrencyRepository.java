package io.pa3553.portfolioadvisor.repository;

import io.pa3553.portfolioadvisor.entity.WishListCryptoCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListCryptoCurrencyRepository extends JpaRepository<WishListCryptoCurrency, Long>
{
    @Query("select wcc " +
            "from wish_list wl " +
            "inner join wl.cryptoCurrencies wcc " +
            "inner join wcc.cryptoCurrency cc " +
            "where wl.removed = false " +
            "and wcc.removed = false " +
            "and cc.removed = false " +
            "and wl.id = :wishListId and cc.symbol = :symbol")
    WishListCryptoCurrency findCryptoCurrencyById(Long wishListId, String symbol);
}
