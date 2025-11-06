package bibar.com.agenda_cultural_servidor.endpoints.ping;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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


    @PostMapping("/imagem")
    public ResponseEntity<Boolean> imagem(
        // @RequestBody PostArquivoRequestBody body
        @RequestParam() String texto,
        @RequestParam() MultipartFile imagem
    ) {
        // TODO: interceptor para tipo / tamanho do arquivp
        if(!imagem.getContentType().matches("image\\/[\\w]+"))
            return ResponseEntity.status(415).build();

        Boolean res = pingService.storeImage(texto, imagem);

        return ResponseEntity.ok(res);
    }
}

record PostArquivoRequestBody (
    String texto,
    MultipartFile arquivo 
) { }