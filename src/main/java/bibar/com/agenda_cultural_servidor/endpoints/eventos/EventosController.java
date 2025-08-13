package bibar.com.agenda_cultural_servidor.endpoints.eventos;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bibar.com.agenda_cultural_servidor.endpoints.eventos.records.Evento;
import bibar.com.agenda_cultural_servidor.endpoints.eventos.records.FiltrosBusca;
import bibar.com.agenda_cultural_servidor.records.ResponseWrapper;

@RestController
@RequestMapping("/eventos")
public class EventosController
{
    private EventosService eventosService;

    public EventosController (
        EventosService eventosServiceInj
    ) {
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

        ResponseWrapper<List<Evento>> response = new ResponseWrapper<List<Evento>>(result); 
        return ResponseEntity.ok(response);
    }
    
    
    @GetMapping("/filtros")
    public ResponseEntity<ResponseWrapper<FiltrosBusca>> filtrosBuscaEventos()
    {
        FiltrosBusca result = eventosService.filtrosBusca();
        
        ResponseWrapper<FiltrosBusca> response = new ResponseWrapper<FiltrosBusca>(result); 
        return ResponseEntity.ok(response);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Evento>> getEvento(
        @PathVariable int id
    ) {
        Optional<Evento> result =  eventosService.getEvento(id);

        if(result.isPresent()){
            ResponseWrapper<Evento> response = new ResponseWrapper<Evento>(result.get());
            return ResponseEntity.ok(response);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}
