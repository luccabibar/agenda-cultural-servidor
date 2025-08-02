package bibar.com.agenda_cultural_servidor.endpoints.usuarios;

import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

import bibar.com.agenda_cultural_servidor.utils.CpfChecker;
import bibar.com.agenda_cultural_servidor.utils.SenhaManager;

@Service
public class UsuariosService
{
    private UsuariosRepository usuariosRepository;

    public UsuariosService (
        UsuariosRepository usuariosRepositoryInj
    ) {
        usuariosRepository = usuariosRepositoryInj;
    }

    // Validacoes
    private boolean isNomeValid(String nome) 
    { 
        // string alfanumerica, espaco, ponto, underline, ate 32 chars
        return nome.length() >= 1 
            && nome.length() <= 32
            && nome.matches("[\\w\\dÁÀÂÃÄÉÈÊËÍÌÎÏÓÒÔÕÖÚÙÛÜÇÑáàâãäéèêëíìîïóòôõöúùûüçñ][\\w\\dÁÀÂÃÄÉÈÊËÍÌÎÏÓÒÔÕÖÚÙÛÜÇÑáàâãäéèêëíìîïóòôõöúùûüçñ \\._]*[\\w\\dÁÀÂÃÄÉÈÊËÍÌÎÏÓÒÔÕÖÚÙÛÜÇÑáàâãäéèêëíìîïóòôõöúùûüçñ]"); 
    } 
    
    private boolean isEmailValid(String email) 
    { 
        // emails, ate 48 chars
        return email.length() >= 3 
            && email.length() <= 48
            && email.matches("[\\w\\d][\\w\\d \\._+]*[\\w\\d]@[\\w\\d]+(?:\\.[\\w\\d]+)+"); 
    }
    
    private boolean isSenhaValid(String senha)
    {
        // senha, 8-64 chars, 
        return senha.length() >= 8
            && senha.length() <= 64;
            // && Pattern.matches("", senha);
    }

    public boolean criaPessoa(
        String nome,
        String email,
        String senha
    ) {
        // valida dados
        if(!isNomeValid(nome) || !isEmailValid(email) || !isSenhaValid(senha))
            return false;

        // verifica se ja esiste
        Boolean existe = usuariosRepository.usuarioExiste(nome, email, "");

        if(existe)
            return false;

        try {
            senha = SenhaManager.generateSenha(senha);
        }
        catch(NoSuchAlgorithmException ex){
            System.err.println(ex.toString());
            return false;
        }

        // realiza criacao
        Boolean res = usuariosRepository.criaPessoa(nome, email, senha);

        return res;
    }
}
