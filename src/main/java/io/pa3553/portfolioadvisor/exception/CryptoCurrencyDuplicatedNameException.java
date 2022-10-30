package io.pa3553.portfolioadvisor.exception;

public class CryptoCurrencyDuplicatedNameException extends BaseException
{
    public CryptoCurrencyDuplicatedNameException()
    {
    }

    public CryptoCurrencyDuplicatedNameException(String message)
    {
        super(message);
    }

    public CryptoCurrencyDuplicatedNameException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
