package bibar.com.agenda_cultural_servidor.endpoints.eventos;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bibar.com.agenda_cultural_servidor.endpoints.eventos.records.Evento;
import bibar.com.agenda_cultural_servidor.endpoints.eventos.records.FiltrosBusca;
import bibar.com.agenda_cultural_servidor.endpoints.usuarios.records.UsuarioInterface;
import bibar.com.agenda_cultural_servidor.excessoes.ForbiddenAccessException;
import bibar.com.agenda_cultural_servidor.excessoes.ResourceNotFoundException;
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
        @RequestParam(required = false) String regiao
    ) {
        List<Evento> result = eventosService.buscar(
            texto,
            categoria,
            diaUpper,
            diaLower,
            horaUpper,
            horaLower,
            regiao
        );

        ResponseWrapper<List<Evento>> response = ResponseWrapper.of(result); 
        return ResponseEntity.ok(response);
    }
    

    @PostMapping
    public ResponseEntity<ResponseWrapper<Integer>> criaEvento(
        @Valid @RequestBody PostEventoRequestBody body,
        @RequestHeader(name = "Authorization") String authHeader
    ) {
        String nome = body.nome();
        String descricao = body.descricao();
        String categoria = body.categoria();
        String contato = body.contato();
        String horaIni = body.horaIni();
        String horaFim = body.horaFim();
        String regiao = body.regiao();
        String endereco = body.endereco();

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
                endereco
            );
        }
        catch(IllegalArgumentException ex){
            System.err.println(ex);
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