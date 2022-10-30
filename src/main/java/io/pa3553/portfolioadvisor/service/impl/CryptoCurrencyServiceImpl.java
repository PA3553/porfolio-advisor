package io.pa3553.portfolioadvisor.service.impl;

import io.pa3553.portfolioadvisor.entity.CryptoCurrency;
import io.pa3553.portfolioadvisor.exception.*;
import io.pa3553.portfolioadvisor.model.CreateCryptoCurrencyContext;
import io.pa3553.portfolioadvisor.repository.CryptoCurrencyRepository;
import io.pa3553.portfolioadvisor.service.CryptoCurrencyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CryptoCurrencyServiceImpl implements CryptoCurrencyService
{
    private final CryptoCurrencyRepository cryptoCurrencyRepository;

    @Autowired
    public CryptoCurrencyServiceImpl(CryptoCurrencyRepository cryptoCurrencyRepository)
    {
        this.cryptoCurrencyRepository = cryptoCurrencyRepository;
    }

    @Override
    public CryptoCurrency createCryptoCurrency(CreateCryptoCurrencyContext context)
            throws CryptoCurrencyDuplicatedNameException, CryptoCurrencyDuplicatedSymbolException,
            CryptoCurrencyNameRequiredException, CryptoCurrencySymbolRequiredException
    {
        validateCryptoCurrencyInput(context.getName(), context.getSymbol());

        CryptoCurrency cryptoCurrency = new CryptoCurrency(context.getName(), context.getSymbol());
        CryptoCurrency savedCryptoCurrency = cryptoCurrencyRepository.save(cryptoCurrency);

        log.info("Crypto currency {}[{}] was created successfully!", savedCryptoCurrency.getName(), savedCryptoCurrency.getSymbol());

        return savedCryptoCurrency;
    }

    private void validateCryptoCurrencyInput(String name, String symbol)
            throws CryptoCurrencyDuplicatedNameException, CryptoCurrencyDuplicatedSymbolException,
            CryptoCurrencySymbolRequiredException, CryptoCurrencyNameRequiredException
    {
        if (name.isEmpty())
        {
            throw new CryptoCurrencyNameRequiredException("Crypto currency name is required");
        }

        if (symbol.isEmpty())
        {
            throw new CryptoCurrencySymbolRequiredException("Crypto currency symbol is required");
        }

        if (cryptoCurrencyRepository.existsByNameAndRemoved(name, false))
        {
            throw new CryptoCurrencyDuplicatedNameException("Crypto currency name already exists!");
        }

        if (cryptoCurrencyRepository.existsBySymbolAndRemoved(symbol, false))
        {
            throw new CryptoCurrencyDuplicatedSymbolException("Crypto currency symbol already exists!");
        }
    }

    @Override
    public CryptoCurrency findCryptoCurrency(Long id) throws CryptoCurrencyNotFoundException
    {
        return cryptoCurrencyRepository.findByIdAndRemoved(id, false).orElseThrow(
                () -> new CryptoCurrencyNotFoundException("Crypto currency id " + id + " not found")
        );
    }

    @Override
    public CryptoCurrency findCryptoCurrency(String symbol) throws CryptoCurrencyNotFoundException
    {
        Optional<CryptoCurrency> cryptoCurrencyOpt = cryptoCurrencyRepository.findBySymbolAndRemoved(
                symbol, false
        );

        return cryptoCurrencyOpt.orElseThrow(
                () -> new CryptoCurrencyNotFoundException("Cryptocurrency " + symbol + " not found!")
        );
    }

    @Override
    public void removeCryptoCurrency(Long id) throws CryptoCurrencyNotFoundException
    {
        CryptoCurrency cryptoCurrency = cryptoCurrencyRepository.findByIdAndRemoved(id, false).orElseThrow(
                () -> new CryptoCurrencyNotFoundException("Crypto currency id " + id + " not found")
        );

        cryptoCurrencyRepository.markAsRemoved(cryptoCurrency.getId());

        log.info("Crypto currency {'id': {}, 'name': '{}', symbol: '{}'} removed successfully!",
                cryptoCurrency.getId(), cryptoCurrency.getName(), cryptoCurrency.getSymbol()
        );
    }
}
