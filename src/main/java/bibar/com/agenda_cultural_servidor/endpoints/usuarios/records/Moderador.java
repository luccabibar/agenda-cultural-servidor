package bibar.com.agenda_cultural_servidor.endpoints.usuarios.records;

import java.util.Optional;

import bibar.com.agenda_cultural_servidor.records.TipoUsuario;

public record Moderador (
    Integer id,
    String email,
    String nome,
    String cpf,
    TipoUsuario tipoUsuario
) implements UsuarioInterface
{
    public Moderador(Integer id, String email, String nome, String cpf, TipoUsuario tipoUsuario)
    {
        if(tipoUsuario != TipoUsuario.MODERADOR)
            System.err.println("Moderador: TipoUsuario " + tipoUsuario.valor + " informado nao corresponde ao esperado para esta classe.");

        this.id = id; 
        this.email = email; 
        this.nome = nome; 
        this.cpf = cpf; 
        this.tipoUsuario = TipoUsuario.MODERADOR;
    }

    public Moderador(Integer id, String email, String nome, String cpf)
    {
        this(id, email, nome, cpf, TipoUsuario.MODERADOR);
    }

    public Moderador(Integer id) { this(id, null, null, null); }

    public static Optional<Moderador> of(UsuarioInterface user)
    {
        if(user instanceof Moderador)
            return Optional.of((Moderador) user);
        else
            return Optional.empty();
    }
}
