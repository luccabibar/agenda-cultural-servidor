package bibar.com.agenda_cultural_servidor.endpoints.eventos.records;

import java.time.LocalDateTime;
import java.util.List;

import bibar.com.agenda_cultural_servidor.endpoints.usuarios.records.Moderador;
import bibar.com.agenda_cultural_servidor.endpoints.usuarios.records.Organizador;

public record Evento (
    Integer id,
    StatusEvento status,

    String nome,
    String descricao,
    String categoria,
    String imagem,
    String contato,

    Organizador organizador,
    Moderador moderador,

    LocalDateTime horarioInicio,
    LocalDateTime horarioFim,
    
    String regiao,
    String endereco,
    String enderecoLink, 
    
    List<AtualizacaoEvento> atualizacoes
) { }


