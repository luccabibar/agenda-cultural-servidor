package bibar.com.agenda_cultural_servidor.endpoints.usuarios.records;

import java.util.Optional;

import bibar.com.agenda_cultural_servidor.records.TipoUsuario;

public record Organizador (
    Integer id,
    String email,
    String nome,
    String cpf,
    TipoUsuario tipoUsuario
) implements UsuarioInterface
{
    public Organizador(Integer id, String email, String nome, String cpf, TipoUsuario tipoUsuario)
    {
        if(tipoUsuario != TipoUsuario.ORGANIZADOR)
            System.err.println("Organizador: TipoUsuario " + tipoUsuario.valor + " informado nao corresponde ao esperado para esta classe.");

        this.id = id; 
        this.email = email; 
        this.nome = nome; 
        this.cpf = cpf; 
        this.tipoUsuario = TipoUsuario.ORGANIZADOR;
    }

    public Organizador(Integer id, String email, String nome, String cpf)
    {
        this(id, email, nome, cpf, TipoUsuario.ORGANIZADOR);
    }

    public Organizador(Integer id) { this(id, null, null, null); }

    public static Optional<Organizador> of(UsuarioInterface user)
    {
        if(user instanceof Organizador)
            return Optional.of((Organizador) user);
        else
            return Optional.empty();
    }
}