package io.pa3553.portfolioadvisor.service;

import io.pa3553.portfolioadvisor.entity.CryptoCurrency;
import io.pa3553.portfolioadvisor.exception.*;
import io.pa3553.portfolioadvisor.model.CreateCryptoCurrencyContext;

public interface CryptoCurrencyService
{
    CryptoCurrency createCryptoCurrency(CreateCryptoCurrencyContext context)
            throws CryptoCurrencyDuplicatedNameException, CryptoCurrencyDuplicatedSymbolException,
            CryptoCurrencyNameRequiredException, CryptoCurrencySymbolRequiredException;
    CryptoCurrency findCryptoCurrency(Long id) throws CryptoCurrencyNotFoundException;

    CryptoCurrency findCryptoCurrency(String symbol) throws CryptoCurrencyNotFoundException;
    void removeCryptoCurrency(Long id) throws CryptoCurrencyNotFoundException;
}
