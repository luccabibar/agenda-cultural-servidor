package bibar.com.agenda_cultural_servidor.endpoints.eventos.records;

public enum StatusEvento {
    EMANALISE ("EmAnalise"),
    APROVADO ("Aprovado"),
    REPROVADO ("Reprovado"),
    CANCELADO ("Cancelado");
    
    public String valor;

    StatusEvento(String str) { valor = str; }
}