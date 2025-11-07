package bibar.com.agenda_cultural_servidor.records;

import org.springframework.http.MediaType;

public record ArquivoTipo(
    byte[] arquivo,
    MediaType tipo
) { }
