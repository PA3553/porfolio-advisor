package io.pa3553.portfolioadvisor.repository;

import io.pa3553.portfolioadvisor.entity.WishList;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<WishList,Long>
{
    @Query("select case when count(1) > 0 then true else false end from wish_list wl inner join wl.cryptoCurrencies wcc inner join wcc.cryptoCurrency cc where wl.removed = false and wcc.removed = false and cc.removed = false and wl.id = :wishListId and cc.symbol = :symbol")
    boolean existsCryptoCurrencyById(Long wishListId, String symbol);

    @NotNull
    @Query("select wl from wish_list wl where wl.removed = false and wl.id = :id")
    Optional<WishList> findById(@NotNull Long id);
}
