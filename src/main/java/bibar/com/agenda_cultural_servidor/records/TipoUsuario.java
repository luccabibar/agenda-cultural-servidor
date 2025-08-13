package bibar.com.agenda_cultural_servidor.records;

public enum TipoUsuario {
    PESSOA ("PESSOA"),
    ORGANIZADOR ("ORGANIZADOR"),
    MODERADOR ("MODERADOR");

    public String valor;

    TipoUsuario(String str) { valor = str; }
}