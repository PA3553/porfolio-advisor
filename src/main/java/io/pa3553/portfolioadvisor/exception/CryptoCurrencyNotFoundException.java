package io.pa3553.portfolioadvisor.exception;

public class CryptoCurrencyNotFoundException extends BaseException
{
    public CryptoCurrencyNotFoundException()
    {
        super();
    }

    public CryptoCurrencyNotFoundException(String message)
    {
        super(message);
    }

    public CryptoCurrencyNotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
