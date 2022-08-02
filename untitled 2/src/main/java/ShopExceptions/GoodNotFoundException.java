package ShopExceptions;

public class GoodNotFoundException extends Exception
{
    public GoodNotFoundException()
    {
        super();
    }

    public  GoodNotFoundException(String message) {
        super(message);
    }
}
