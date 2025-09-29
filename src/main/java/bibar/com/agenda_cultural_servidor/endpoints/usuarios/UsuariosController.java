package bibar.com.agenda_cultural_servidor.endpoints.usuarios;

import java.security.NoSuchAlgorithmException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bibar.com.agenda_cultural_servidor.endpoints.usuarios.records.UsuarioAutenticado;
import bibar.com.agenda_cultural_servidor.excessoes.ForbiddenAccessException;
import bibar.com.agenda_cultural_servidor.excessoes.ResourceAlreadyExistsException;
import bibar.com.agenda_cultural_servidor.excessoes.ResourceNotFoundException;
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
    public ResponseEntity<ResponseWrapper<UsuarioAutenticado>> login(
        @Valid @RequestBody LoginRequestBody body
    ) {
        String email = body.email();
        String senha = body.senha();
         
        UsuarioAutenticado result;
        
        try{
            result = usuariosService.login(email, senha);
        }
        catch(NoSuchAlgorithmException ex){
            System.err.println(ex);
            return ResponseEntity.status(500).build();
        }
        catch(ResourceNotFoundException ex){
            System.err.println(ex);
            return ResponseEntity.notFound().build();
        }
        catch(ForbiddenAccessException ex){
            System.err.println(ex);
            return ResponseEntity.status(401).build();
        }
        

        ResponseWrapper<UsuarioAutenticado> response = ResponseWrapper.of(result);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/pessoas")
    public ResponseEntity<ResponseWrapper<Boolean>> criaPessoa(
        @Valid @RequestBody CriaPessoaRequestBody body
    ) {
        String nome = body.nome();
        String email = body.email();
        String senha = body.senha();
       
        
        boolean result;
        
        try{
            result = usuariosService.criaPessoa(nome, email, senha);
        }
        catch(IllegalArgumentException ex){
            System.err.println(ex);
            return ResponseEntity.status(409).build();
        }
        catch(ResourceAlreadyExistsException ex){
            System.err.println(ex);
            return ResponseEntity.status(403).build();
        }

        ResponseWrapper<Boolean> response = ResponseWrapper.of(result);
        return ResponseEntity.ok(response);
    }
    

    @PostMapping("/organizadores")
    public ResponseEntity<ResponseWrapper<Boolean>> criaOrganizador(
        @Valid @RequestBody CriaOrganizadorRequestBody body
    ) {
        String nome = body.nome();
        String email = body.email();
        String cpf = body.cpf();
        String senha = body.senha();
       
         
        boolean result;
        
        try{
            result = usuariosService.criaOrganizador(nome, email, cpf, senha);
        }
        catch(IllegalArgumentException ex){
            System.err.println(ex);
            return ResponseEntity.status(409).build();
        }
        catch(ResourceAlreadyExistsException ex){
            System.err.println(ex);
            return ResponseEntity.status(403).build();
        }


        ResponseWrapper<Boolean> response = ResponseWrapper.of(result);
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


record CriaOrganizadorRequestBody (
    @NotBlank String nome, 
    @NotBlank String email, 
    @NotBlank String cpf, 
    @NotBlank String senha
) { }