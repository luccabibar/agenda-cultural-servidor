package bibar.com.agenda_cultural_servidor.records;

import java.time.Instant;
import java.util.Optional;

import bibar.com.agenda_cultural_servidor.endpoints.usuarios.records.UsuarioInterface;

public record JWTUser(
    Integer sub,
    TipoUsuario role,
    Instant iat
) {
    public JWTUser(Integer subVal, String roleVal, Instant iatVal)
    {
        this(
            subVal,
            TipoUsuario.valueOf(roleVal),
            iatVal
        );
    }


    public static Optional<JWTUser> of(UsuarioInterface user)
    {
        if(user.id() == null || user.tipoUsuario() == null)
            return Optional.empty();
        else    
            return Optional.of(new JWTUser(user.id(), user.tipoUsuario(), Instant.now()));        
    }
}