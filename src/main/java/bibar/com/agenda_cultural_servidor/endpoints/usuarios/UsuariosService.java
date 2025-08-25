package bibar.com.agenda_cultural_servidor.endpoints.usuarios;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import bibar.com.agenda_cultural_servidor.endpoints.usuarios.records.Usuario;
import bibar.com.agenda_cultural_servidor.utils.CpfChecker;
import bibar.com.agenda_cultural_servidor.utils.JWTManager;
import bibar.com.agenda_cultural_servidor.utils.SenhaManager;

@Service
public class UsuariosService
{
    private JWTManager JWTMan;
    private UsuariosRepository usuariosRepository;

    public UsuariosService (
        JWTManager JWTManInj,
        UsuariosRepository usuariosRepositoryInj
    ) {
        JWTMan = JWTManInj;
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


    public Optional<String> login(String email, String senha)
    {
        if(!isEmailValid(email))
            return Optional.empty();

        // obtem sal para senha
        Optional<String> salt = usuariosRepository.getDataCriacao(email);

        // email nao presente no banco
        if(salt.isEmpty())
            return Optional.empty();

        try{
            // gera senha
            senha = SenhaManager.hashPassword(senha, salt.get());
        }
        catch(NoSuchAlgorithmException ex){
            System.err.println(ex.toString());
            return Optional.empty();
        }

        //  pega dados do usuario  
        Optional<Usuario> usuario = usuariosRepository.autenticaUsuario(email, senha);

        // se nao encontrou dados
        if(usuario.isEmpty())
            return Optional.empty();

        // gera token de autenticacao
        Optional<String> authToken = JWTMan.encrypt(usuario.get());

        return authToken;
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
    

    public boolean criaOrganizador(
        String nome,
        String email,
        String cpf,
        String senha
    ) {
        // valida dados
        cpf = CpfChecker.limpaCpfCnpj(cpf);
        
        if(!isNomeValid(nome) || !isEmailValid(email) || !CpfChecker.isCpfCnpjValid(cpf) || !isSenhaValid(senha))
            return false;

        // verifica se ja esiste
        Boolean existe = usuariosRepository.usuarioExiste(nome, email, cpf);
        System.out.println(existe ? "existe" : "nao existe");

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
        Boolean res = usuariosRepository.criaOrganizador(nome, email, cpf, senha);

        return res;
    }
}
