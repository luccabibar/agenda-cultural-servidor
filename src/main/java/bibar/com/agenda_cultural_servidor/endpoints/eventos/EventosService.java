package bibar.com.agenda_cultural_servidor.endpoints.eventos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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
    private DateTimeFormatter dateFormatter, timeFormatter, dateTimeFormatter;
    private EventosRepository eventosRepository;

    public EventosService (
        EventosRepository eventosRepositoryInj
    ) {
        eventosRepository = eventosRepositoryInj;

        dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;           // yyyy-MM-dd
        timeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;           // HH:mm:ss
        dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;  // yyyy-MM-ddTHH:mm:ss
    }


    // validacoes
    private boolean isNomeEventoValid(String nome) { return nome != null && nome.length() <= 24; }
    private boolean isDescricaoEventoValid(String descricao) { return descricao != null && descricao.length() <= 256; }
    private boolean isCategoriaEventoValid(String categoria) { return categoria != null && categoria.length() <= 16; }
    private boolean isContatoEventoValid(String contato) { return contato != null && contato.length() <= 32; }
    private boolean isRegiaoEventoValid(String regiao) { return regiao != null && regiao.length() <= 24; }
    private boolean isEnderecoEventoValid(String endereco) { return endereco != null && endereco.length() <= 64; }
    private boolean isIdEventoValid(Integer id) { return id != null && id > 0; }

    private boolean isHorasValid(LocalDateTime horaIni, LocalDateTime horaFim)
    {
        return horaFim != null && horaIni != null && horaIni.isBefore(horaFim) && LocalDateTime.now().isBefore(horaIni);
    }

    private boolean isTituloAttValid(String titulo) { return titulo != null && titulo.length() <= 24; }
    private boolean isTextoAttValid(String texto) { return texto != null && texto.length() <= 256; }


    public List<Evento> buscar(
        String texto,
        String categoria,
        String diaUpperStr,
        String diaLowerStr,
        String horaUpperStr,
        String horaLowerStr,
        String regiao,
        Integer organizador,
        Integer moderador,
        List<String> statusStr
    ) {
        List<StatusEvento> status;
        LocalTime horaUpper, horaLower;
        LocalDate diaUpper, diaLower;
        
        // prepara dados emdata e hora
        try {            
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

        status = new ArrayList<StatusEvento>();

        if(statusStr != null){
            for (String st : statusStr){
                try {
                    status.add(StatusEvento.valueOf(st));
                }
                catch(Exception ex){            
                    System.err.println("EventosService: " + ex);
                }
            } 
        }

        // converte em optionals
        List<Evento> res = eventosRepository.buscarEventos(
            Optional.ofNullable(texto),
            Optional.ofNullable(categoria),
            Optional.ofNullable(diaUpper),
            Optional.ofNullable(diaLower),
            Optional.ofNullable(horaUpper),
            Optional.ofNullable(horaLower),
            Optional.ofNullable(regiao),
            Optional.ofNullable(organizador),
            Optional.ofNullable(moderador),
            status
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


    public int criaEvento(
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
            horaIni = LocalDateTime.parse(horaIniStr, dateTimeFormatter);          
            horaFim = LocalDateTime.parse(horaFimStr, dateTimeFormatter);
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
            || !isHorasValid(horaIni, horaFim)
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

        if(!res)
            return 0;

        // busca evento que acabou de ser criado
        List<Evento> eventos = buscar(
            nome, 
            categoria, 
            horaIni.format(dateFormatter), // busca <= data && busca >= data 
            horaIni.format(dateFormatter), 
            horaIni.format(timeFormatter), 
            horaIni.format(timeFormatter), 
            regiao,
            usuario.id(),
            null,
            null
        );

        if(!eventos.isEmpty())
            return eventos.getFirst().id();
        else
            return 0;
    }


    // TODO: evitar de editar se todos os dados forem vazios / iguais
    public boolean editaEvento(
        int idEvento,
        UsuarioInterface organizador,
        String descricao,
        String contato,
        String horaIniStr,
        String horaFimStr,
        String regiao,
        String endereco
    ) throws IllegalArgumentException, ResourceNotFoundException, ForbiddenAccessException 
    {
        // busca evento que vai ser editado
        Optional<Evento> evento = getEvento(idEvento);

        if(evento.isEmpty())
            throw new ResourceNotFoundException("EventosService: evento a ser editado nao pode ser encontrado. id: " + idEvento);
        
        if(evento.get().organizador().id() != organizador.id())
            throw new ForbiddenAccessException("EventosService: usuario nao tem acesso a esse recurso");

 
        // prepara parametros
        LocalDateTime horaIni, horaFim;

        // prepara dados emdata e hora
        try {            
            horaFim = (horaFimStr != null) ? LocalDateTime.parse(horaFimStr, dateTimeFormatter) : null;
        }
        catch(DateTimeParseException ex){
            throw new IllegalArgumentException("EventosService: um dos parametros enviados é considerado invalido" + ex);
        }
        
        try {            
            horaIni = (horaIniStr != null) ? LocalDateTime.parse(horaIniStr, dateTimeFormatter) : null;
        }
        catch(DateTimeParseException ex){
            throw new IllegalArgumentException("EventosService: um dos parametros enviados é considerado invalido" + ex);
        }

        // validacao das horas sao um pouco mais chatas
        Boolean horasValida;

        if(horaIni == null && horaFim == null)
            horasValida = true;
        else if(horaIni == null && horaFim != null)
            horasValida = isHorasValid(evento.get().horarioInicio(), horaFim); 
        else if(horaIni != null && horaFim == null)
            horasValida = isHorasValid(horaIni, evento.get().horarioFim());
        else
            horasValida = isHorasValid(horaIni, horaFim);

        // para cada item: deve ser null OU invalido
        if(
            (descricao != null && !isDescricaoEventoValid(descricao))
            || (contato != null && !isContatoEventoValid(contato))
            || (regiao != null && !isRegiaoEventoValid(regiao))
            || (endereco != null && !isEnderecoEventoValid(endereco))
            || !horasValida
        )
            throw new IllegalArgumentException("EventosService: um dos parametros enviados é considerado invalido");
            

        boolean res = eventosRepository.patchEvento(
            idEvento,
            organizador.id(),
            Optional.empty(),
            Optional.ofNullable(descricao),
            Optional.ofNullable(contato),
            Optional.ofNullable(horaIni),
            Optional.ofNullable(horaFim),
            Optional.ofNullable(regiao),
            Optional.ofNullable(endereco)
        );

        return res;
    }


    public boolean deletaEvento(
        int idEvento,
        UsuarioInterface organizador
    ) throws ResourceNotFoundException, ForbiddenAccessException 
    {
        // busca evento que vai ser editado
        Optional<Evento> evento = getEvento(idEvento);

        if(evento.isEmpty())
            throw new ResourceNotFoundException("EventosService: evento a ser editado nao pode ser encontrado. id: " + idEvento);
        
        if(evento.get().organizador().id() != organizador.id())
            throw new ForbiddenAccessException("EventosService: usuario nao tem acesso a esse recurso");

 
        boolean res = eventosRepository.deleteEvento(idEvento, organizador.id());

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


    public boolean analisaEvento(Integer idEvento, UsuarioInterface moderador, String statusStr) throws IllegalArgumentException, ResourceNotFoundException, ForbiddenAccessException
    {
        StatusEvento status; 

        try {
            status = StatusEvento.valueOf(statusStr);
        }
        catch(Exception ex){            
            System.err.println("EventosService: String status invlálida " + statusStr);
            throw new IllegalArgumentException(ex.toString());
        }
        
        if(!status.equals(StatusEvento.APROVADO) && !status.equals(StatusEvento.REPROVADO))
            throw new IllegalArgumentException("EventosService: Status deve ser APROVADO ou REPROVADO");

        Optional<Evento> evento = getEvento(idEvento);

        if(evento.isEmpty())
            throw new ResourceNotFoundException("EventosService: impossivel encontrar evento (id: " + idEvento + ")" );

        if(evento.get().moderador().id() != moderador.id())
            throw new ForbiddenAccessException("EventosService: usuario nao tem acesso a esse recurso");

        System.out.println(evento.get());


        boolean res = eventosRepository.atualizaStatusEvento(idEvento, moderador.id(), status);

        return res;
    }
}
