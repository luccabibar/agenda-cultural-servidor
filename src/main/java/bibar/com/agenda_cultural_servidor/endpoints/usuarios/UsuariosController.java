package bibar.com.agenda_cultural_servidor.endpoints.usuarios;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bibar.com.agenda_cultural_servidor.records.ResponseWrapper;

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


    // original
    // @PostMapping("/pessoas")
    // public ResponseEntity<ResponseWrapper<Boolean>> criaPessoa(
    //     @RequestBody String nome,
    //     @RequestBody String email,
    //     @RequestBody String senha
    // ) {
    //     System.out.println(nome + "\n" + email + "\n" + senha + "\n");

    //     boolean result = usuariosService.criaPessoa(nome, email, senha);

    //     ResponseWrapper<Boolean> response = new ResponseWrapper<Boolean>(result);
    //     return ResponseEntity.ok(response);
    // }

    // alternativa A - Map
    // @PostMapping("/pessoas")
    // public ResponseEntity<ResponseWrapper<Boolean>> criaPessoa(
    //     @RequestBody Map<String, Object> body
    // ) {
    //     String nome, email, senha;

    //     try {
            
    //         nome = (String) body.get("nome");
    //         email = (String) body.get("email");
    //         senha = (String) body.get("senha");
    //     }
    //     catch(Exception ex) {
    //         return ResponseEntity.badRequest().build();
    //     }
        
    //     boolean result = usuariosService.criaPessoa(nome, email, senha);

    //     ResponseWrapper<Boolean> response = new ResponseWrapper<Boolean>(result);
    //     return ResponseEntity.ok(response);
    // }

    @PostMapping("/pessoas")
    public ResponseEntity<ResponseWrapper<Boolean>> criaPessoa(
        @RequestBody CriaPessoaRequestBody body
    ) {
        if(body.isValid())
            return ResponseEntity.badRequest().build();

        String nome = body.nome();
        String email = body.email();
        String senha = body.senha();
        
        System.out.println(nome + "\n" + email + "\n" + senha + "\n");

        boolean result = usuariosService.criaPessoa(nome, email, senha);

        ResponseWrapper<Boolean> response = new ResponseWrapper<Boolean>(result);
        return ResponseEntity.ok(response);
    }
}

record CriaPessoaRequestBody (String nome, String email, String senha)
{
    // cheque se valores nao requeridos nao esao sendo verificados
    public boolean isValid()
    {
        return
            nome == null || 
            email == null ||
            senha == null;
    }
}