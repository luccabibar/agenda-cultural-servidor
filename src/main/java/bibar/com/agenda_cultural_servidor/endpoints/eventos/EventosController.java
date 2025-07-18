package bibar.com.agenda_cultural_servidor.endpoints.eventos;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bibar.com.agenda_cultural_servidor.endpoints.eventos.records.FiltrosBusca;
import bibar.com.agenda_cultural_servidor.records.ResponseWrapper;

@RestController
@CrossOrigin
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
    void buscaEventos(
        @RequestParam(required = false) String texto,
        @RequestParam(required = false) String categoria,
        @RequestParam(required = false) String diaUpper,
        @RequestParam(required = false) String diaLower,
        @RequestParam(required = false) String horaUpper,
        @RequestParam(required = false) String horaLower,
        @RequestParam(required = false) String regiao
    ) {

    }
    
    
    @GetMapping("filtros")
    ResponseEntity<ResponseWrapper<FiltrosBusca>> filtrosBuscaEventos()
    {
        FiltrosBusca result = eventosService.filtrosBusca();
        
        ResponseWrapper<FiltrosBusca> response = new ResponseWrapper<FiltrosBusca>(result); 
        return ResponseEntity.ok(response);
    }
    

    @GetMapping("/{id}")
    void getEvento(
        @PathVariable int id
    ) {
        
    }
}
