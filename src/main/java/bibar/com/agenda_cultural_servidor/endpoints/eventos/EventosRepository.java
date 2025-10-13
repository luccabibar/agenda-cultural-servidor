package bibar.com.agenda_cultural_servidor.endpoints.eventos;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.core.simple.JdbcClient.StatementSpec;
import org.springframework.stereotype.Repository;

import bibar.com.agenda_cultural_servidor.endpoints.eventos.records.AtualizacaoEvento;
import bibar.com.agenda_cultural_servidor.endpoints.eventos.records.Evento;
import bibar.com.agenda_cultural_servidor.endpoints.eventos.records.StatusEvento;
import bibar.com.agenda_cultural_servidor.endpoints.usuarios.records.Organizador;

@Repository
public class EventosRepository
{
    private JdbcClient jdbcClient;

    public EventosRepository(
        JdbcClient jdbcClientInj
    ) {
        jdbcClient = jdbcClientInj;
    }


    public List<Evento> buscarEventos(
        Optional<String> texto,
        Optional<String> categoria,
        Optional<LocalDate> diaUpper,
        Optional<LocalDate> diaLower,
        Optional<LocalTime> horaUpper,
        Optional<LocalTime> horaLower,
        Optional<String> regiao,
        Optional<Integer> organizadorId,
        Optional<StatusEvento> status
    ) {
        String query = """
            SELECT
                ev.id, ev.status, 
                ev.nome, ev.descricao, ev.categoria,
                ev.hora_ini, ev.regiao,
                us.nome AS us_nome
            FROM evento AS ev
            JOIN organizador AS og
            ON
                ev.organizador = og.id
            JOIN usuario AS us
            ON
                og.id = us.id 
            
        """;

        // prepara params
        List<ParamBusca> params = preparaParamsBuscar(
            texto,
            categoria,
            diaUpper,
            diaLower,
            horaUpper,
            horaLower,
            regiao,
            organizadorId,
            status
        );
        
        // monta where clause
        String whereClause = """
            WHERE
                1 = 1

        """; // macete veio para garantir que ha sempre, ao menos, uma condicao no where (torna adicionar as seguintes mais facil)
        
        for(int ii = 0; ii < params.size(); ii++)
            whereClause += "AND " + params.get(ii).condicao() + " "; // SE ATENTAR se ha WHERE antes ou nao
        
        whereClause += ";";

        // transforma o objeto estruturado em pares K V (conforme especificado pelo jdbc)
        Map<String, Object> paramMap = params
            .stream()    
            .collect(Collectors.toMap(
                ParamBusca::nome,
                ParamBusca::value
            ));

        // realiza query
        List<Map<String, Object>> resList = jdbcClient
            .sql(query + whereClause)
            .params(paramMap)
            .query()
            .listOfRows();

        // monta resultados
        List<Evento> result = new ArrayList<Evento>();

        for(Map<String, Object> resRow : resList){
            Organizador organizador = new Organizador(
                null,
                null,
                (String) resRow.get("us_nome"),
                null
            );

            Evento evento = new Evento(
                (Integer) resRow.get("id"),
                (StatusEvento) StatusEvento.fromString((String) resRow.get("status")),
    
                (String) resRow.get("nome"),
                (String) resRow.get("descricao"),
                (String) resRow.get("categoria"),
                null,
                null,
                
                organizador,
                null,
    
                ((Timestamp) resRow.get("hora_ini")).toLocalDateTime(),
                null,
                
                (String) resRow.get("regiao"),
                null,
                null,
    
                null
            );

            result.add(evento);
        }

        return result;
    }


    private List<ParamBusca> preparaParamsBuscar(
        Optional<String> texto,
        Optional<String> categoria,
        Optional<LocalDate> diaUpper,
        Optional<LocalDate> diaLower,
        Optional<LocalTime> horaUpper,
        Optional<LocalTime> horaLower,
        Optional<String> regiao,
        Optional<Integer> organizador,
        Optional<StatusEvento> status
    ) {
        List<ParamBusca> params = new ArrayList<ParamBusca>();

        if(texto.isPresent())
            params.add(new ParamBusca(
                texto.get(), 
                "texto", 
                """
                   (
                        UPPER(ev.nome) LIKE CONCAT('%%', UPPER(:texto), '%%')
                        OR UPPER(ev.descricao) LIKE CONCAT('%%', UPPER(:texto), '%%')
                   )
                """
            ));

        if(categoria.isPresent())
            params.add(new ParamBusca(
                categoria.get(), 
                "categoria", 
                "UPPER(ev.categoria) = UPPER(:categoria)"
            ));

        if(diaUpper.isPresent())
            params.add(new ParamBusca(
                diaUpper.get(), 
                "diaUpper", 
                "CAST(ev.hora_ini AS DATE) <= :diaUpper"
            ));

        if(diaLower.isPresent())
            params.add(new ParamBusca(
                diaLower.get(), 
                "diaLower", 
                "CAST(ev.hora_ini AS DATE) >= :diaLower"
            ));

        if(horaUpper.isPresent())
            params.add(new ParamBusca(
                horaUpper.get(), 
                "horaUpper", 
                "CAST(ev.hora_ini AS TIME) <= :horaUpper"
            ));

        if(horaLower.isPresent())
            params.add(new ParamBusca(
                horaLower.get(), 
                "horaLower", 
                "CAST(ev.hora_ini AS TIME) >= :horaLower"
            ));

        if(regiao.isPresent())
            params.add(new ParamBusca(
                regiao.get(), 
                "regiao", 
                "UPPER(ev.regiao) = UPPER(:regiao)"
            ));
        
        if(organizador.isPresent())
            params.add(new ParamBusca(
                organizador.get(), 
                "organizador", 
                "ev.organizador = :organizador"
            ));

        if(status.isPresent())
            params.add(new ParamBusca(
                status.get().valor, 
                "status", 
                "ev.status = :status"
            ));

        return params;
    }



    public List<String> listRegioes()
    {
        String query = """
            SELECT
                rg.regiao
            FROM regiao_evento AS rg
            ;
        """;

        List<Object> resObject =  jdbcClient
            .sql(query)
            .query()
            .singleColumn();

        List<String> res = resObject
            .stream()
            .map(obj -> obj.toString())
            .toList();

        return res;
    }


    public List<String> listCategorias()
    {
        String query = """
            SELECT
                ct.categoria
            FROM categoria_evento AS ct
            ;
        """;

        List<Object> resObject =  jdbcClient
            .sql(query)
            .query()
            .singleColumn();

        List<String> res = resObject
            .stream()
            .map(obj -> obj.toString())
            .toList();

        return res;
    }


    public Optional<Evento> getEvento(int id)
    {
        // faz query
        String query = """
            SELECT
                ev.nome, ev.id, ev.status, ev.descricao, ev.categoria, ev.contato,
                ev.hora_ini, ev.hora_fim, ev.regiao, ev.endereco, ev.endereco_link,
                og.id AS og_id, us.nome AS og_nome,
                att.titulo AS att_titulo, att.texto AS att_texto
            FROM evento AS ev
            JOIN organizador AS og
            ON
                ev.organizador = og.id
            JOIN usuario AS us
            ON
                og.id = us.id
            LEFT JOIN atualizacao_evento AS att
            ON
                ev.id = att.evento
            WHERE
                ev.id = :id
                AND ev.status = :status
            ;        
        """;

        List<Map<String, Object>> resList = jdbcClient
            .sql(query)
            .param("id", id)
            .param("status", StatusEvento.APROVADO.valor)
            .query()
            .listOfRows();
    

        // not found
        if (resList.size() == 0)
            return Optional.empty();
        
        // found, monta result
        Map<String, Object> resRow = resList.get(0);

        Organizador organizador = new Organizador(
            (Integer) resRow.get("og_id"),
            null,
            (String) resRow.get("og_nome"),
            null            
        );

        List<AtualizacaoEvento> atualizacoes = new ArrayList<AtualizacaoEvento>();
        
        // tem atualizacoes
        if(resRow.get("att_titulo") != null){
            AtualizacaoEvento att;

            for (Map<String, Object> rr : resList) {
                att = new AtualizacaoEvento(
                    null,
                    (String) rr.get("att_titulo"),
                    (String) rr.get("att_texto"),
                    null
                );

                atualizacoes.add(att);
            }
        }

        Evento evento = new Evento(
            (Integer) resRow.get("id"),
            (StatusEvento) StatusEvento.fromString((String) resRow.get("status")),

            (String) resRow.get("nome"),
            (String) resRow.get("descricao"),
            (String) resRow.get("categoria"),
            null,
            (String) resRow.get("contato"),
            
            organizador,
            null,

            ((Timestamp) resRow.get("hora_ini")).toLocalDateTime(),
            ((Timestamp) resRow.get("hora_fim")).toLocalDateTime(),
            
            (String) resRow.get("regiao"),
            (String) resRow.get("endereco"),
            (String) resRow.get("endereco_link"),

            atualizacoes
        );

        return Optional.of(evento);
    }


    public boolean criaEvento(
        StatusEvento status,
        String nome,
        String descricao,
        String categoria,
        String contato,
        int idOrganizador,
        LocalDateTime horaIni,
        LocalDateTime horaFim,
        String regiao,
        String endereco,
        String enderecoLink
    ) {
        String query = """
            INSERT INTO evento (
                status,
                nome,
                descricao,
                categoria,
                contato,
                organizador,
                hora_ini,
                hora_fim,
                regiao,
                endereco,
                endereco_link
            )
            VALUES (
                :status,
                :nome,
                :descricao,
                :categoria,
                :contato,
                :organizador,
                :hora_ini,
                :hora_fim,
                :regiao,
                :endereco,
                :endereco_link
            );
        """;

        int res = jdbcClient
            .sql(query)
            .param("status", status.valor)
            .param("nome", nome)
            .param("descricao", descricao)
            .param("categoria", categoria)
            .param("contato", contato)
            .param("organizador", idOrganizador)
            .param("hora_ini", horaIni)
            .param("hora_fim", horaFim)
            .param("regiao", regiao)
            .param("endereco", endereco)
            .param("endereco_link", enderecoLink)
            .update();

        return res == 1;
    }


    public boolean addAtualizacaoEvento(
        Integer idEvento,
        Integer idOrganizador,
        String titulo,
        String texto
    ) {
        // query de insercao deve checar se usuario eh o dono do evento 
        String query = """
            INSERT INTO atualizacao_evento (
                evento,
                titulo,
                texto,
                imagem
            )
            VALUES (
                (SELECT ev.id FROM evento AS ev WHERE ev.organizador = :id_org AND ev.id = :id_ev),
                :titulo,
                :texto,
                null
            );                
        """; 

        int res = 0;
        
        StatementSpec stSpec = jdbcClient
            .sql(query)
            .param("id_org", idOrganizador)
            .param("id_ev", idEvento)
            .param("titulo", titulo)
            .param("texto", texto);

        try{
            res = stSpec.update();
        }
        // provavelmente causada porque usuario nao eh dono desde evento
        catch(DataIntegrityViolationException ex){
            System.err.println(ex);
            res = 0;
        }
        
        return res == 1;
    }
}


record ParamBusca (
    Object value,
    String nome,
    String condicao
) { }
