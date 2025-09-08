package bibar.com.agenda_cultural_servidor.endpoints.ping;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bibar.com.agenda_cultural_servidor.records.ResponseWrapper;

@RestController
@CrossOrigin
@RequestMapping("/ping")
public class PingController
{
    private PingService pingService;

    public PingController (
        PingService pingServiceInj
    ) {
        pingService = pingServiceInj;        
    }

    
    @GetMapping
    public ResponseEntity<ResponseWrapper<String>> ping()
    {
        String result = pingService.ping();
        
        ResponseWrapper<String> response = ResponseWrapper.of(result);
        return ResponseEntity.ok(response);
    }
}
