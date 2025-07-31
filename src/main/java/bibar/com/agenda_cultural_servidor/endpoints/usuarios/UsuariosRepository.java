package bibar.com.agenda_cultural_servidor.endpoints.usuarios;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class UsuariosRepository
{
    private JdbcClient jdbcClient;

    public UsuariosRepository (
        JdbcClient jdbcClientInj
    ) {
        jdbcClient = jdbcClientInj;
    }

    public boolean usuarioExiste (String nome, String email, String cpf)
    {
        String query = """
            SELECT
                us.email, 
                us.nome, 
                og.cpf_cnpj, 
                md.cpf_cnpj 
            FROM usuario AS us
            FULL OUTER JOIN organizador AS og
                ON us.id = og.id
            FULL OUTER JOIN moderador AS md
                ON us.id = md.id
            WHERE
                UPPER(us.email) = UPPER(:email)
                OR UPPER(us.nome) = UPPER(:nome)
                OR og.cpf_cnpj = :cpf
                OR md.cpf_cnpj = :cpf
            ;
        """;

        List<Map<String, Object>> resList = jdbcClient
            .sql(query)
            .param("nome", nome)
            .param("email", email)
            .param("cpf", cpf)
            .query()
            .listOfRows();

        return resList.size() > 0;
    }
}
