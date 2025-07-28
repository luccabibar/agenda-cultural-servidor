package bibar.com.agenda_cultural_servidor.endpoints.usuarios.records;

// import java.util.Optional;

public record Organizador (
    Integer id,
    String email,
    String nome,
    String cpf
) { }