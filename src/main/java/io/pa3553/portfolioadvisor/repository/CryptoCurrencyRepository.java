package io.pa3553.portfolioadvisor.repository;

import io.pa3553.portfolioadvisor.entity.CryptoCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CryptoCurrencyRepository  extends JpaRepository<CryptoCurrency,Long>
{
    Optional<CryptoCurrency> findByIdAndRemoved(Long id, boolean removed);
    Optional<CryptoCurrency> findBySymbolAndRemoved(String symbol, boolean removed);
    boolean existsByNameAndRemoved(String name, boolean removed);
    boolean existsBySymbolAndRemoved(String symbol, boolean removed);

    @Modifying
    @Query("update crypto_currency cc set cc.removed = true where cc.id = :cryptoCurrencyId")
    void markAsRemoved(Long cryptoCurrencyId);
}
