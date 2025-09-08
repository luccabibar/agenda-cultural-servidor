package bibar.com.agenda_cultural_servidor.excessoes;

public class ForbiddenAccessException extends Exception
{
    public ForbiddenAccessException(String message)
    {
        super(message);
    }
}
