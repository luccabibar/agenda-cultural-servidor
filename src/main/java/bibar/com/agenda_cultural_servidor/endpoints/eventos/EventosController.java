package bibar.com.agenda_cultural_servidor.endpoints.eventos;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import bibar.com.agenda_cultural_servidor.endpoints.eventos.records.Evento;
import bibar.com.agenda_cultural_servidor.endpoints.eventos.records.FiltrosBusca;
import bibar.com.agenda_cultural_servidor.endpoints.usuarios.records.UsuarioInterface;
import bibar.com.agenda_cultural_servidor.excessoes.ForbiddenAccessException;
import bibar.com.agenda_cultural_servidor.excessoes.ResourceNotFoundException;
import bibar.com.agenda_cultural_servidor.records.ArquivoTipo;
import bibar.com.agenda_cultural_servidor.records.JWTUser;
import bibar.com.agenda_cultural_servidor.records.ResponseWrapper;
import bibar.com.agenda_cultural_servidor.records.TipoUsuario;
import bibar.com.agenda_cultural_servidor.utils.JWTManager;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/eventos")
public class EventosController
{
    private JWTManager JWTMan;
    private EventosService eventosService;

    public EventosController (
        JWTManager JWTManInj,
        EventosService eventosServiceInj
    ) {
        JWTMan = JWTManInj;
        eventosService = eventosServiceInj;
    }


    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Evento>>> buscaEventos(
        @RequestParam(required = false) String texto,
        @RequestParam(required = false) String categoria,
        @RequestParam(required = false) String diaUpper,
        @RequestParam(required = false) String diaLower,
        @RequestParam(required = false) String horaUpper,
        @RequestParam(required = false) String horaLower,
        @RequestParam(required = false) String regiao,
        @RequestParam(required = false) Integer organizador,
        @RequestParam(required = false) Integer moderador,
        @RequestParam(required = false) List<String> status
    ) {
        List<Evento> result = eventosService.buscar(
            texto,
            categoria,
            diaUpper,
            diaLower,
            horaUpper,
            horaLower,
            regiao,
            organizador,
            moderador,
            status
        );

        ResponseWrapper<List<Evento>> response = ResponseWrapper.of(result); 
        return ResponseEntity.ok(response);
    }
    

    @PostMapping
    public ResponseEntity<ResponseWrapper<Integer>> criaEvento(
        // este request deve receber dados por meio de @RequestParam porque utiliza o encoding multipart/form-data 
        @RequestParam(required = true) String nome, 
        @RequestParam(required = true) String descricao, 
        @RequestParam(required = true) String categoria, 
        @RequestParam(required = true) String contato, 
        @RequestParam(required = true) String horaIni, 
        @RequestParam(required = true) String horaFim, 
        @RequestParam(required = true) String regiao,
        @RequestParam(required = true) String endereco,
        @RequestParam(required = true) MultipartFile imagem,
        @RequestHeader(name = "Authorization") String authHeader
    ) {
        // pega user
        Optional<JWTUser> userJWT = JWTMan.fullDecryptFromHeader(authHeader);
        
        if (userJWT.isEmpty())
            return ResponseEntity.status(401).build();

        UsuarioInterface usuario = UsuarioInterface.of(userJWT.get()).get();

        if(usuario.tipoUsuario() != TipoUsuario.ORGANIZADOR)
            return ResponseEntity.status(403).build(); 
        
            
        int result;

        try{
            result = eventosService.criaEvento(
                usuario,
                nome,
                descricao,
                categoria,
                contato,
                horaIni,
                horaFim,
                regiao,
                endereco,
                imagem
            );
        }
        catch(IllegalArgumentException ex){
            System.err.println(ex);
            return ResponseEntity.status(409).build();
        }
        catch(IOException ex){
            System.err.println(ex);
            // TODO: Mensagem de erro mais clara
            return ResponseEntity.status(409).build();
        }
        
        ResponseWrapper<Integer> response = ResponseWrapper.of(result);
        // return ResponseEntity.created(201); // nao sera implementado desta forma pois nao trabalhamos com URI 
        return ResponseEntity.status(201).body(response); 
    }
    

    @GetMapping("/filtros")
    public ResponseEntity<ResponseWrapper<FiltrosBusca>> filtrosBuscaEventos()
    {
        FiltrosBusca result = eventosService.filtrosBusca();
        
        ResponseWrapper<FiltrosBusca> response = ResponseWrapper.of(result); 
        return ResponseEntity.ok(response);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Evento>> getEvento(
        @PathVariable int id
    ) {
        Optional<Evento> result =  eventosService.getEvento(id);

        if(result.isPresent()){
            ResponseWrapper<Evento> response = ResponseWrapper.of(result.get());
            return ResponseEntity.ok(response);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }


    @PatchMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Boolean>> editaEvento(
        @PathVariable int id,
        @Valid @RequestBody PatchEventoRequestBody body,
        @RequestHeader(name = "Authorization") String authHeader
    ) {
        // pega dados do body
        String descricao = body.descricao();
        String contato = body.contato();
        String horaIni = body.horaIni();
        String horaFim = body.horaFim();
        String regiao = body.regiao();
        String endereco = body.endereco();


        //pega user
        Optional<JWTUser> userJWT = JWTMan.fullDecryptFromHeader(authHeader);
        
        if (userJWT.isEmpty())
            return ResponseEntity.status(401).build();

        UsuarioInterface usuario = UsuarioInterface.of(userJWT.get()).get();

        if(usuario.tipoUsuario() != TipoUsuario.ORGANIZADOR)
            return ResponseEntity.status(403).build(); 


        boolean result;
        
        try{
            result = eventosService.editaEvento(id, usuario, descricao, contato, horaIni, horaFim, regiao, endereco);
        }
        catch(IllegalArgumentException ex){
            System.err.println(ex);
            return ResponseEntity.status(409).build();
        }
        catch(ResourceNotFoundException ex){
            System.err.println(ex);
            return ResponseEntity.notFound().build();               
        }        
        catch(ForbiddenAccessException ex){
            System.err.println(ex);
            return ResponseEntity.status(403).build();
        }
    
        ResponseWrapper<Boolean> response = ResponseWrapper.of(result);
        
        if(result)
            return ResponseEntity.ok(response);
        else
            return ResponseEntity.status(409).body(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Boolean>> deletaEvento(
        @PathVariable int id,
        @RequestHeader(name = "Authorization") String authHeader
    ) {
        //pega user
        Optional<JWTUser> userJWT = JWTMan.fullDecryptFromHeader(authHeader);
        
        if (userJWT.isEmpty())
            return ResponseEntity.status(401).build();

        UsuarioInterface usuario = UsuarioInterface.of(userJWT.get()).get();

        if(usuario.tipoUsuario() != TipoUsuario.ORGANIZADOR)
            return ResponseEntity.status(403).build(); 


        boolean result;
        
        try{
            result = eventosService.deletaEvento(id, usuario);
        }
        catch(ResourceNotFoundException ex){
            System.err.println(ex);
            return ResponseEntity.notFound().build();               
        }        
        catch(ForbiddenAccessException ex){
            System.err.println(ex);
            return ResponseEntity.status(403).build();
        }
    
        ResponseWrapper<Boolean> response = ResponseWrapper.of(result);
        
        if(result)
            return ResponseEntity.ok(response);
        else
            return ResponseEntity.status(409).body(response);
    }


    @GetMapping("/{id}/imagem")
    public ResponseEntity<byte[]> getEventoOImagem(
        @PathVariable int id
    ) {
        Optional<ArquivoTipo> result = eventosService.getEventoImage(id);

        if(result.isPresent())
            return ResponseEntity.ok()
                .contentType(result.get().tipo())
                .body(result.get().arquivo());
        else
            return ResponseEntity.notFound().build();
        
    }


    @PostMapping("/{id}/atualizacoes")
    public ResponseEntity<ResponseWrapper<Boolean>> criaAtualizacaoEvento(
        @PathVariable int id,
        @Valid @RequestBody PostAtualizacaoRequestBody body,
        @RequestHeader(name = "Authorization") String authHeader
    ) {
        // pega dados do body
        String titulo = body.titulo();
        String texto = body.texto();

        //pega user
        Optional<JWTUser> userJWT = JWTMan.fullDecryptFromHeader(authHeader);
        
        if (userJWT.isEmpty())
            return ResponseEntity.status(401).build();

        UsuarioInterface usuario = UsuarioInterface.of(userJWT.get()).get();

        if(usuario.tipoUsuario() != TipoUsuario.ORGANIZADOR)
            return ResponseEntity.status(403).build(); 


        boolean result;
        
        try{
            result = eventosService.addAtualizacaoEvento(id, usuario, titulo, texto);
        }
        catch(IllegalArgumentException ex){
            System.err.println(ex);
            return ResponseEntity.status(409).build();
        }
        catch(ResourceNotFoundException ex){
            System.err.println(ex);
            return ResponseEntity.notFound().build();               
        }        
        catch(ForbiddenAccessException ex){
            System.err.println(ex);
            return ResponseEntity.status(403).build();
        }
    
        ResponseWrapper<Boolean> response = ResponseWrapper.of(result);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/analise")
    public ResponseEntity<ResponseWrapper<Boolean>> analisaEvento(
        @PathVariable int id,
        @Valid @RequestBody PostAnaliseRequestBody body,
        @RequestHeader(name = "Authorization") String authHeader
    ) {
        // pega dados do body
        String status = body.status();

        //pega user
        Optional<JWTUser> userJWT = JWTMan.fullDecryptFromHeader(authHeader);
        
        if (userJWT.isEmpty())
            return ResponseEntity.status(401).build();

        UsuarioInterface usuario = UsuarioInterface.of(userJWT.get()).get();

        if(usuario.tipoUsuario() != TipoUsuario.MODERADOR)
            return ResponseEntity.status(403).build(); 


        boolean result;
        result = true;
        
        try{
            result = eventosService.analisaEvento(id, usuario, status);
        }
        catch(IllegalArgumentException ex){
            System.err.println(ex);
            return ResponseEntity.status(409).build();
        }
        catch(ResourceNotFoundException ex){
            System.err.println(ex);
            return ResponseEntity.notFound().build();               
        }        
        catch(ForbiddenAccessException ex){
            System.err.println(ex);
            return ResponseEntity.status(403).build();
        }
    
        ResponseWrapper<Boolean> response = ResponseWrapper.of(result);
        return ResponseEntity.ok(response);
    }
}


record PostEventoRequestBody (
    @NotBlank String nome, 
    @NotBlank String descricao, 
    @NotBlank String categoria, 
    @NotBlank String contato, 
    @NotBlank String horaIni, 
    @NotBlank String horaFim, 
    @NotBlank String regiao,
    @NotBlank String endereco
) { }

record PostAtualizacaoRequestBody (
    @NotBlank String titulo,
    @NotBlank String texto
) { }

record PatchEventoRequestBody (
    String descricao, 
    String contato, 
    String horaIni, 
    String horaFim, 
    String regiao,
    String endereco
) { }


record PostAnaliseRequestBody (
    @NotBlank String status 
) { }
