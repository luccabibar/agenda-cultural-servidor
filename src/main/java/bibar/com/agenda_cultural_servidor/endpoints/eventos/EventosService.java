package bibar.com.agenda_cultural_servidor.endpoints.eventos;

import java.util.List;

import org.springframework.stereotype.Service;

import bibar.com.agenda_cultural_servidor.endpoints.eventos.records.FiltrosBusca;

@Service
public class EventosService
{
    private EventosRepository eventosRepository;

    public EventosService (
        EventosRepository eventosRepositoryInj
    ) {
        eventosRepository = eventosRepositoryInj;
    }


    void buscar()
    {

    }

    
    FiltrosBusca filtrosBusca()
    {
        List<String> regioes, categorias;

        regioes = eventosRepository.listRegioes();
        categorias = eventosRepository.listCategorias();

        FiltrosBusca filtros = new FiltrosBusca(regioes, categorias);

        return filtros;
    }


    void getEvento()
    {

    }
}