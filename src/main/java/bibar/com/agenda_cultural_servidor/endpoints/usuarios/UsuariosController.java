package bibar.com.agenda_cultural_servidor.endpoints.usuarios;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bibar.com.agenda_cultural_servidor.records.ResponseWrapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController
{
    private UsuariosService usuariosService;

    public UsuariosController(
        UsuariosService usuariosServiceInj
    ) {
        usuariosService = usuariosServiceInj;
    }


    @PostMapping("/login")
    public ResponseEntity<ResponseWrapper<String>> login(
        @Valid @RequestBody LoginRequestBody body
    ) {
        String email = body.email();
        String senha = body.senha();
         
        Optional<String> result = usuariosService.login(email, senha);

        // nao encontrou; unauthorized
        if(result.isEmpty())
            return ResponseEntity.status(401).build();

        ResponseWrapper<String> response = new ResponseWrapper<String>(result.get());
        return ResponseEntity.ok(response);
    }


    @PostMapping("/pessoas")
    public ResponseEntity<ResponseWrapper<Boolean>> criaPessoa(
        @Valid @RequestBody CriaPessoaRequestBody body
    ) {
        String nome = body.nome();
        String email = body.email();
        String senha = body.senha();
       
        // TODO: erros custom  
        boolean result = usuariosService.criaPessoa(nome, email, senha);

        ResponseWrapper<Boolean> response = new ResponseWrapper<Boolean>(result);
        return ResponseEntity.ok(response);
    }
}


record LoginRequestBody ( 
    @NotBlank String email, 
    @NotBlank String senha
) { }


record CriaPessoaRequestBody (
    @NotBlank String nome, 
    @NotBlank String email, 
    @NotBlank String senha
) { }