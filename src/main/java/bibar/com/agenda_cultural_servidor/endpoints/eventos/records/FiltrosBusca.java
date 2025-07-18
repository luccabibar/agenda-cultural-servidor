package bibar.com.agenda_cultural_servidor.endpoints.eventos.records;

import java.util.List;

public record FiltrosBusca (
    List<String> regioes,
    List<String> categorias
) { }
