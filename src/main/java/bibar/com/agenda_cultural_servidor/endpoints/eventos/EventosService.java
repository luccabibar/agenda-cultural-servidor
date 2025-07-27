package bibar.com.agenda_cultural_servidor.endpoints.eventos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import bibar.com.agenda_cultural_servidor.endpoints.eventos.records.Evento;
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


    public List<Evento> buscar(
        String texto,
        String categoria,
        String diaUpper,
        String diaLower,
        String horaUpper,
        String horaLower,
        String regiao
    ) {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        // converte em optionals
        List<Evento> res = eventosRepository.buscarEventos(
            Optional.ofNullable(texto),
            Optional.ofNullable(categoria),
            Optional.ofNullable(diaUpper != null ? LocalDate.parse(diaUpper, dateFormatter) : null), // converte esses dois
            Optional.ofNullable(diaLower != null ? LocalDate.parse(diaLower, dateFormatter) : null), // String -> LocalDate
            Optional.ofNullable(horaUpper != null ? LocalTime.parse(horaUpper, timeFormatter) : null), // converte esses dois
            Optional.ofNullable(horaLower != null ? LocalTime.parse(horaLower, timeFormatter) : null), // String -> LocalTime
            Optional.ofNullable(regiao)
        );

        return res;
    }

    
    public FiltrosBusca filtrosBusca()
    {
        List<String> regioes, categorias;

        regioes = eventosRepository.listRegioes();
        categorias = eventosRepository.listCategorias();

        FiltrosBusca filtros = new FiltrosBusca(regioes, categorias);

        return filtros;
    }


    public Optional<Evento> getEvento(int id)
    {
        Optional<Evento> res = eventosRepository.getEvento(id);

        return res;
    }
}