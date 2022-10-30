package io.pa3553.portfolioadvisor.exception;

public class CryptoCurrencyDuplicatedSymbolException extends BaseException
{
    public CryptoCurrencyDuplicatedSymbolException()
    {
    }

    public CryptoCurrencyDuplicatedSymbolException(String message)
    {
        super(message);
    }

    public CryptoCurrencyDuplicatedSymbolException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
