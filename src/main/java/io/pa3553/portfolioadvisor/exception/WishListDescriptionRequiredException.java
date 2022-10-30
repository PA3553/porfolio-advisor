package io.pa3553.portfolioadvisor.exception;

public class WishListDescriptionRequiredException extends BaseException
{
    public WishListDescriptionRequiredException()
    {
        super();
    }

    public WishListDescriptionRequiredException(String message)
    {
        super(message);
    }

    public WishListDescriptionRequiredException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
