package bibar.com.agenda_cultural_servidor.endpoints.eventos.records;

public record AtualizacaoEvento(
    Integer id,

    String titulo,
    String texto,
    String imagem
) { }
