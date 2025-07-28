package bibar.com.agenda_cultural_servidor.endpoints.usuarios;

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
}
