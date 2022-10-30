package io.pa3553.portfolioadvisor.exception;

public class WishListNameRequiredException extends BaseException
{
    public WishListNameRequiredException()
    {
        super();
    }

    public WishListNameRequiredException(String message)
    {
        super(message);
    }

    public WishListNameRequiredException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
