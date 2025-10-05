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
import bibar.com.agenda_cultural_servidor.endpoints.usuarios.records.UsuarioInterface;
import bibar.com.agenda_cultural_servidor.excessoes.ForbiddenAccessException;
import bibar.com.agenda_cultural_servidor.excessoes.ResourceNotFoundException;

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
    private boolean isNomeEventoValid(String nome) { return nome != null && nome.length() <= 24; }
    private boolean isDescricaoEventoValid(String descricao) { return descricao != null && descricao.length() <= 256; }
    private boolean isCategoriaEventoValid(String categoria) { return categoria != null && categoria.length() <= 16; }
    private boolean isContatoEventoValid(String contato) { return contato != null && contato.length() <= 32; }
    private boolean isRegiaoEventoValid(String regiao) { return regiao != null && regiao.length() <= 24; }
    private boolean isEnderecoEventoValid(String endereco) { return endereco != null && endereco.length() <= 64; }
    private boolean isIdEventoValid(Integer id) { return id != null && id > 0; }

    private boolean isTituloAttValid(String titulo) { return titulo != null && titulo.length() <= 24; }
    private boolean isTextoAttValid(String texto) { return texto != null && texto.length() <= 256; }


    public List<Evento> buscar(
        String texto,
        String categoria,
        String diaUpperStr,
        String diaLowerStr,
        String horaUpperStr,
        String horaLowerStr,
        String regiao
    ) {
        LocalTime horaUpper, horaLower;
        LocalDate diaUpper, diaLower;
        
        // prepara dados emdata e hora
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            
            diaUpper = (diaUpperStr != null) ? LocalDate.parse(diaUpperStr, dateFormatter) : null;
            diaLower = (diaLowerStr != null) ? LocalDate.parse(diaLowerStr, dateFormatter) : null;
            horaUpper = (horaUpperStr != null) ? LocalTime.parse(horaUpperStr, timeFormatter) : null;
            horaLower = (horaLowerStr != null) ? LocalTime.parse(horaLowerStr, timeFormatter) : null;

        }
        catch(DateTimeParseException ex){
            System.err.println("EventosService: " + ex);

            diaUpper = null;
            diaLower = null;
            horaUpper = null;
            horaLower = null;
        }

        // converte em optionals
        List<Evento> res = eventosRepository.buscarEventos(
            Optional.ofNullable(texto),
            Optional.ofNullable(categoria),
            Optional.ofNullable(diaUpper),
            Optional.ofNullable(diaLower),
            Optional.ofNullable(horaUpper),
            Optional.ofNullable(horaLower),
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
        UsuarioInterface usuario,
        String nome,
        String descricao,
        String categoria,
        String contato,
        String horaIniStr,
        String horaFimStr,
        String regiao,
        String endereco
    ) throws IllegalArgumentException 
    {
        // valida dados, realzia conversoes necessarias
        // TODO: endereco -> google maps link
        LocalDateTime horaIni, horaFim;
        
        try{
            horaIni = LocalDateTime.parse(horaIniStr);          
            horaFim = LocalDateTime.parse(horaFimStr);
        }
        catch(DateTimeParseException ex){
            System.out.println(ex.toString());
            throw new IllegalArgumentException(ex);
        }
        
        if(
            !isNomeEventoValid(nome)
            || !isDescricaoEventoValid(descricao)
            || !isCategoriaEventoValid(categoria)
            || !isContatoEventoValid(contato)
            || !isRegiaoEventoValid(regiao)
            || !isEnderecoEventoValid(endereco)
        )
            throw new IllegalArgumentException("EventosService: um dos parametros enviados é considerado invalido");
            

        boolean res = eventosRepository.criaEvento(
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

        return res;
    }


    public boolean addAtualizacaoEvento(
        Integer idEvento,
        UsuarioInterface organizador,
        String titulo,
        String texto
    ) throws IllegalArgumentException, ResourceNotFoundException, ForbiddenAccessException
    {
        if(
            !isIdEventoValid(idEvento)
            || !isTituloAttValid(titulo)
            || !isTextoAttValid(texto)
        )
            throw new IllegalArgumentException("EventosService: um dos parametros enviados é considerado invalido");

        /*
            nao eh necessario checar se o usuario eh o dono deste evento,
            esta checagem eh feita na propria query de insercao

            entretanto, decido checar por tornar o codigo mais seguro, e menos ambiguo
            mais devagar (duas requisicoes), mais seguro
        */ 
        Optional<Evento> evento = getEvento(idEvento);

        if(evento.isEmpty())
            throw new ResourceNotFoundException("EventosService: impossivel encontrar evento (id: " + idEvento + ")" );

        if(evento.get().organizador().id() != organizador.id())
            throw new ForbiddenAccessException("EventosService: usuario nao tem acesso a esse recurso");

        boolean res = eventosRepository.addAtualizacaoEvento(
            idEvento,
            organizador.id(),
            titulo,
            texto
        );

        return res;
    }
}