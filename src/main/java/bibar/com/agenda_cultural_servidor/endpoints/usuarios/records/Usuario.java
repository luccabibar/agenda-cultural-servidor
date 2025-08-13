package bibar.com.agenda_cultural_servidor.endpoints.usuarios.records;

import bibar.com.agenda_cultural_servidor.records.TipoUsuario;

// representa uma Pessoa, Organizador ou Moderador
public record Usuario (
    Integer id,
    String email,
    String nome,
    TipoUsuario tipoUsuario // indica a qual tipo de usuario se refere
) {
    public Usuario(Integer idVal, TipoUsuario tipoVal)
    {
        this(idVal, null, null, tipoVal);
    }

    public Usuario(Integer idVal, String emailVal, String nomeVal)
    {
        this(idVal, emailVal, nomeVal, null);
    }
}