package io.pa3553.portfolioadvisor.exception;

public class CryptoCurrencyNameRequiredException extends BaseException
{
    public CryptoCurrencyNameRequiredException()
    {
    }

    public CryptoCurrencyNameRequiredException(String message)
    {
        super(message);
    }

    public CryptoCurrencyNameRequiredException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
