package bibar.com.agenda_cultural_servidor.endpoints.usuarios.records;

public record UsuarioAutenticado(
    UsuarioInterface usuario,
    String authToken
) {
    public static UsuarioAutenticado of(UsuarioInterface user, String token)
    {
        return new UsuarioAutenticado(user, token);
    }
}