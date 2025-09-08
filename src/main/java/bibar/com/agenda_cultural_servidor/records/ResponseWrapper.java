package bibar.com.agenda_cultural_servidor.records;

import org.springframework.http.ResponseEntity;

public record ResponseWrapper<T>(T response) 
{
    public static <V> ResponseWrapper<V> of(V target)
    {
        return new ResponseWrapper<V>(target);
    }
 
    public ResponseEntity<ResponseWrapper<T>> createResponseEntity(int status)
    {
        return ResponseEntity.status(status).body(this);
    }
}
