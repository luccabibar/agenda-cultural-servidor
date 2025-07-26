package bibar.com.agenda_cultural_servidor.endpoints.eventos;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import bibar.com.agenda_cultural_servidor.endpoints.eventos.records.AtualizacaoEvento;
import bibar.com.agenda_cultural_servidor.endpoints.eventos.records.Evento;
import bibar.com.agenda_cultural_servidor.records.Organizador;

@Repository
public class EventosRepository
{
    private JdbcClient jdbcClient;

    public EventosRepository(
        JdbcClient jdbcClientInj
    ) {
        jdbcClient = jdbcClientInj;
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
                ev.nome, ev.descricao, ev.categoria, ev.contato,
                ev.hora_ini, ev.hora_fim, ev.regiao, ev.endereco, ev.endereco_link,
                us.nome AS us_nome,
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
                AND ev.status = 'Aprovado'
            ;        
        """;

        List<Map<String, Object>> resList = jdbcClient
            .sql(query)
            .param("id", id)
            .query()
            .listOfRows();
    

        // not found
        if (resList.size() == 0)
            return Optional.empty();
        
        // found, monta result
        Map<String, Object> resRow = resList.get(0);

        Organizador organizador = new Organizador(
            null,
            null,
            (String) resRow.get("us_nome"),
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
            null,
            null,

            (String) resRow.get("nome"),
            (String) resRow.get("descricao"),
            (String) resRow.get("categoria"),
            null,
            (String) resRow.get("contato"),
            
            organizador,
            null,

            (Timestamp) resRow.get("hora_ini"),
            (Timestamp) resRow.get("hora_fim"),
            
            (String) resRow.get("regiao"),
            (String) resRow.get("endereco"),
            (String) resRow.get("endereco_link"),

            atualizacoes
        );

        return Optional.of(evento);
    }
}
