package bibar.com.agenda_cultural_servidor.excessoes;

public class ResourceNotFoundException extends Exception
{
    public ResourceNotFoundException(String message)
    {
        super(message);
    }
}
