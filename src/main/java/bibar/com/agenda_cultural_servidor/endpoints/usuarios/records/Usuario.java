package bibar.com.agenda_cultural_servidor.endpoints.usuarios.records;

public record Usuario (
    Integer id,
    String email,
    String nome
) { }