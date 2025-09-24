package bibar.com.agenda_cultural_servidor.endpoints.usuarios;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import bibar.com.agenda_cultural_servidor.endpoints.usuarios.records.Usuario;
import bibar.com.agenda_cultural_servidor.records.TipoUsuario;

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


    public Optional<Usuario> autenticaUsuario(String email, String senha)
    {
        String query = """
            SELECT
                us.id AS id,
                pes.id AS pes_id,
                org.id AS org_id,
                mdr.id AS mdr_id,
                us.email AS email,
                us.nome AS nome
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
        
        TipoUsuario tipo;

        // id pessoa presente; usuario eh pessoa
        if(result.get("pes_id") != null)
            tipo = TipoUsuario.PESSOA;

        // id organizador presente; usuario eh organizador
        else if(result.get("org_id") != null)
            tipo = TipoUsuario.ORGANIZADOR;

        // id moderador presente; usuario eh moderador
        else if(result.get("mdr_id") != null)
            tipo = TipoUsuario.MODERADOR;

        // nenhum id presente? usuario nao eh nada
        else
            return Optional.empty();

        
        // monta usuario e retorna
        Usuario response = new Usuario(
            (Integer) result.get("id"), 
            (String) result.get("email"), 
            (String) result.get("nome"), 
            tipo
        );

        return Optional.of(response);
    }


    public boolean criaOrganizador(String nome, String email, String cpf, String senha)
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

            INSERT INTO organizador (
                id,
                cpf_cnpj
            )
            VALUES (
                (
                    SELECT us.id 
                    FROM usuario AS us 
                    WHERE us.email = :email AND us.senha = :senha
                ),
                :cpf
            );

            COMMIT;
        """;

        jdbcClient
            .sql(query)
            .param("email", email)
            .param("senha", senha)
            .param("nome", nome)
            .param("cpf", cpf)
            .update();
        
        // TODO: aferir corretamente resultado do update
        return usuarioExiste(nome, email, cpf);
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


        jdbcClient
            .sql(query)
            .param("email", email)
            .param("senha", senha)
            .param("nome", nome)
            .update();
        
        // TODO: aferir corretamente resultado do update
        return usuarioExiste(nome, email, "");
    }
}
