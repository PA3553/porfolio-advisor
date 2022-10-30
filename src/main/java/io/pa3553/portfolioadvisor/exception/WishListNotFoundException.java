package io.pa3553.portfolioadvisor.exception;

public class WishListNotFoundException extends BaseException
{
    public WishListNotFoundException()
    {
        super();
    }

    public WishListNotFoundException(String message)
    {
        super(message);
    }

    public WishListNotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
