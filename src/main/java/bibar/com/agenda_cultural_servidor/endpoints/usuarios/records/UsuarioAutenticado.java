package bibar.com.agenda_cultural_servidor.endpoints.usuarios.records;

import bibar.com.agenda_cultural_servidor.records.TipoUsuario;

public record UsuarioAutenticado(
    Integer id,
    String email,
    String nome,
    TipoUsuario tipoUsuario,
    String token
) {
    public static UsuarioAutenticado of(Usuario user, String token)
    {
        return new UsuarioAutenticado(
            user.id(), 
            user.email(), 
            user.nome(), 
            user.tipoUsuario(),
            token
        );
    }
}