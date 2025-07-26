package bibar.com.agenda_cultural_servidor.endpoints.eventos.records;

import java.sql.Timestamp;
import java.util.List;

import bibar.com.agenda_cultural_servidor.records.Moderador;
import bibar.com.agenda_cultural_servidor.records.Organizador;

public record Evento (
    Integer id,
    statusEvento status,

    String nome,
    String descricao,
    String categoria,
    String imagem,
    String contato,

    Organizador organizador,
    Moderador moderador,

    Timestamp horarioInicio,
    Timestamp horarioFim,
    
    String regiao,
    String endereco,
    String enderecoLink, 
    
    List<AtualizacaoEvento> atualizacoes
) { }

enum statusEvento
{
    EmAnalise,
    Aprovado,    
    Reprovado,   
    Cancelado   
}


