package bibar.com.agenda_cultural_servidor.endpoints.usuarios.records;

import java.util.Optional;

import bibar.com.agenda_cultural_servidor.records.JWTUser;
import bibar.com.agenda_cultural_servidor.records.TipoUsuario;

public interface UsuarioInterface
{
    public Integer id();
    public String email();
    public String nome();
    public TipoUsuario tipoUsuario();

    public static Optional<UsuarioInterface> of(JWTUser user)
    {
        // valor invalido
        if(user.sub() == null)
            return Optional.empty();

        // especializacoes
        switch (user.role()) {
        case TipoUsuario.PESSOA:
            return Optional.of(new Pessoa(user.sub()));       
            // break;
                    
        case TipoUsuario.ORGANIZADOR:
            return Optional.of(new Organizador(user.sub()));
            // break;

        case TipoUsuario.MODERADOR:
            return Optional.of(new Moderador(user.sub()));
            // break;

        default:
            return Optional.empty();
            // break;
        }
    }
}