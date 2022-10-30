package io.pa3553.portfolioadvisor.exception;

public class WishListInvalidInputException extends BaseException
{
    public WishListInvalidInputException()
    {
        super();
    }

    public WishListInvalidInputException(String message)
    {
        super(message);
    }

    public WishListInvalidInputException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
