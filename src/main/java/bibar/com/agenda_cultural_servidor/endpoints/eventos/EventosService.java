package bibar.com.agenda_cultural_servidor.endpoints.eventos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import bibar.com.agenda_cultural_servidor.endpoints.eventos.records.Evento;
import bibar.com.agenda_cultural_servidor.endpoints.eventos.records.FiltrosBusca;
import bibar.com.agenda_cultural_servidor.endpoints.eventos.records.StatusEvento;
import bibar.com.agenda_cultural_servidor.endpoints.usuarios.records.Usuario;

@Service
public class EventosService
{
    private EventosRepository eventosRepository;

    public EventosService (
        EventosRepository eventosRepositoryInj
    ) {
        eventosRepository = eventosRepositoryInj;
    }


    // validacoes
    private boolean isNomeValid(String nome) { return nome != null && nome.length() <= 24; }
    private boolean isDescricaoValid(String descricao) { return descricao != null && descricao.length() <= 256; }
    private boolean isCategoriaValid(String categoria) { return categoria != null && categoria.length() <= 16; }
    private boolean isContatoValid(String contato) { return contato != null && contato.length() <= 32; }
    private boolean isRegiaoValid(String regiao) { return regiao != null && regiao.length() <= 24; }
    private boolean isEnderecoValid(String endereco) { return endereco != null && endereco.length() <= 64; }


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


    public boolean criaEvento(
        Usuario usuario,
        String nome,
        String descricao,
        String categoria,
        String contato,
        String horaIniStr,
        String horaFimStr,
        String regiao,
        String endereco
    ) {
        // valida dados, realzia conversoes necessarias
        // TODO: endereco -> google maps link
        LocalDateTime horaIni, horaFim;
        
        try{
            horaIni = LocalDateTime.parse(horaIniStr);          
            horaFim = LocalDateTime.parse(horaFimStr);

        }
        catch(DateTimeParseException ex){
            System.out.println(ex.toString());
            return false;
        }
        
        if(
            !isNomeValid(nome)
            || !isDescricaoValid(descricao)
            || !isCategoriaValid(categoria)
            || !isContatoValid(contato)
            || !isRegiaoValid(regiao)
            || !isEnderecoValid(endereco)
        )
            return false;

        eventosRepository.criaEvento(
            StatusEvento.APROVADO,
            nome,
            descricao,
            categoria,
            contato,
            usuario.id(),
            horaIni,
            horaFim,
            regiao,
            endereco,
            "\"null\"" // link endereco
        );

        return true;
    }
}