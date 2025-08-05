package bibar.com.agenda_cultural_servidor.endpoints.usuarios;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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


    public Optional<String> getDataCriacao(String email)
    {
        String query = """
            SELECT
                us.data_criacao
            FROM
                usuario AS us
            WHERE
                email = :email
            ;
        """;

        Optional<Object> response =  jdbcClient
            .sql(query)
            .param("email", email)
            .query()
            .optionalValue();

        // retorna resultado desejado OU optional vazio
        return response.map(obj -> obj.toString());
    }


    public Optional<String> autenticaUsuario(String email, String senha)
    {
        String query = """
            SELECT
                pes.id AS pes_id,
                org.id AS org_id,
                mdr.id AS mdr_id
            FROM
                usuario AS us
            LEFT JOIN 
                pessoa AS pes
                ON pes.id = us.id
            LEFT JOIN
                organizador AS org
                ON org.id = us.id
            LEFT JOIN
                moderador AS mdr
                ON mdr.id = us.id
            WHERE
                email = :email
                and senha = :senha
            ;
        """;

        List<Map<String, Object>> resList = jdbcClient
            .sql(query)
            .param("email", email)
            .param("senha", senha)
            .query()
            .listOfRows();        
        
        // sem resultados; credenciais nao batem
        if (resList.isEmpty())
            return Optional.empty();

        Map<String, Object> result = resList.get(0);
        
        // TODO: ENUM

        // id pessoa presente; usuario eh pessoa
        if(result.get("pes_id") != null)
            return Optional.of("PESSOA");

        // id organizador presente; usuario eh organizador
        else if(result.get("org_id") != null)
            return Optional.of("ORGANIZADOR");

        // id moderador presente; usuario eh moderador
        else if(result.get("mdr_id") != null)
            return Optional.of("MODERADOR");

        // nenhum id presente? usuario nao eh nada
        else
            return Optional.empty();
    }


    public boolean criaPessoa(String nome, String email, String senha)
    {
        String query = """
            BEGIN TRANSACTION;

            INSERT INTO usuario (
                email,
                senha,
                nome,
                status
            )
            VALUES (
                :email,
                :senha,
                :nome,
                'Ativo'
            );

            INSERT INTO pessoa (
                id
            )
            VALUES (
                (
                    SELECT us.id 
                    FROM usuario AS us 
                    WHERE us.email = :email AND us.senha = :senha
                )
            );

            COMMIT;
        """;

        int res = jdbcClient
            .sql(query)
            .param("email", email)
            .param("senha", senha)
            .param("nome", nome)
            .update();

        return res == 1;
    }
}
