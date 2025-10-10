package bibar.com.agenda_cultural_servidor.endpoints.eventos.records;

public enum StatusEvento {
    EMANALISE ("EmAnalise"),
    APROVADO ("Aprovado"),
    REPROVADO ("Reprovado"),
    CANCELADO ("Cancelado");
    
    public String valor;

    StatusEvento(String str) { valor = str; }

    public static StatusEvento fromString(String str) throws IllegalArgumentException
    {

        for (StatusEvento item : StatusEvento.values())
            if (item.valor.equalsIgnoreCase(str))
                return item;
    
        throw new IllegalArgumentException("StatusEvento.fromString: impossivel definir valor a partir de '" + str  + "'");        
    }
}