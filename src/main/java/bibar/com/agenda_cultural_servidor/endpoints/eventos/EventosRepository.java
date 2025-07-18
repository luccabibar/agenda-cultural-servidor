package bibar.com.agenda_cultural_servidor.endpoints.eventos;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class EventosRepository
{
    private JdbcClient jdbcClient;

    public EventosRepository(
        JdbcClient jdbcClientInj
    ) {
        jdbcClient = jdbcClientInj;
    }


    List<String> listRegioes()
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


    List<String> listCategorias()
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
}
