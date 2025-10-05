package bibar.com.agenda_cultural_servidor.endpoints.usuarios.records;

import java.util.Optional;

import bibar.com.agenda_cultural_servidor.records.TipoUsuario;

public record Pessoa (
    Integer id,
    String email,
    String nome,
    TipoUsuario tipoUsuario
) implements UsuarioInterface
{
    public Pessoa(Integer id, String email, String nome, TipoUsuario tipoUsuario)
    {
        if(tipoUsuario != TipoUsuario.PESSOA)
            System.err.println("Pessoa: TipoUsuario " + tipoUsuario.valor + " informado nao corresponde ao esperado para esta classe.");

        this.id = id; 
        this.email = email; 
        this.nome = nome; 
        this.tipoUsuario = TipoUsuario.PESSOA;
    }

    public Pessoa(Integer id, String email, String nome)
    {
        this(id, email, nome, TipoUsuario.PESSOA);
    }

    public Pessoa(Integer id) { this(id, null, null); }

    public static Optional<Pessoa> of(UsuarioInterface user)
    {
        if(user instanceof Pessoa)
            return Optional.of((Pessoa) user);
        else
            return Optional.empty();
    }

    @Override
    public TipoUsuario tipoUsuario() { return TipoUsuario.PESSOA; }
}