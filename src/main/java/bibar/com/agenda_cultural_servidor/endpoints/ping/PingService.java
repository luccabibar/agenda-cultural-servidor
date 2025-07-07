package bibar.com.agenda_cultural_servidor.endpoints.ping;

import org.springframework.stereotype.Service;

@Service
public class PingService
{
    String ping()
    {
        return "pong";
    }
}
