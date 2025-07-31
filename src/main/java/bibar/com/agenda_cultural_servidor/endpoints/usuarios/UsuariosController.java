package bibar.com.agenda_cultural_servidor.endpoints.usuarios;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bibar.com.agenda_cultural_servidor.records.ResponseWrapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@CrossOrigin
@RequestMapping("/usuarios")
public class UsuariosController
{
    private UsuariosService usuariosService;

    public UsuariosController(
        UsuariosService usuariosControllerInj
    ) {
        usuariosService = usuariosControllerInj;
    }


    @PostMapping("/pessoas")
    public ResponseEntity<ResponseWrapper<Boolean>> criaPessoa(
        @Valid @RequestBody CriaPessoaRequestBody body
    ) {
        String nome = body.nome();
        String email = body.email();
        String senha = body.senha();
       
        boolean result = usuariosService.criaPessoa(nome, email, senha);

        ResponseWrapper<Boolean> response = new ResponseWrapper<Boolean>(result);
        return ResponseEntity.ok(response);
    }
}

record CriaPessoaRequestBody (
    @NotBlank String nome, 
    @NotBlank String email, 
    @NotBlank String senha
) { }