package io.pa3553.portfolioadvisor.exception;

public class CryptoCurrencySymbolRequiredException extends BaseException
{
    public CryptoCurrencySymbolRequiredException()
    {
    }

    public CryptoCurrencySymbolRequiredException(String message)
    {
        super(message);
    }

    public CryptoCurrencySymbolRequiredException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
