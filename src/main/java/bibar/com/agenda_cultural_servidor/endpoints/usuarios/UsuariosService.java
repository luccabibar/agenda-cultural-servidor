package bibar.com.agenda_cultural_servidor.endpoints.usuarios;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import bibar.com.agenda_cultural_servidor.endpoints.usuarios.records.Moderador;
import bibar.com.agenda_cultural_servidor.endpoints.usuarios.records.Organizador;
import bibar.com.agenda_cultural_servidor.endpoints.usuarios.records.Pessoa;
import bibar.com.agenda_cultural_servidor.endpoints.usuarios.records.UsuarioAutenticado;
import bibar.com.agenda_cultural_servidor.endpoints.usuarios.records.UsuarioInterface;
import bibar.com.agenda_cultural_servidor.excessoes.ForbiddenAccessException;
import bibar.com.agenda_cultural_servidor.excessoes.ResourceAlreadyExistsException;
import bibar.com.agenda_cultural_servidor.excessoes.ResourceNotFoundException;
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


    public UsuarioAutenticado login(String email, String senha) throws IllegalArgumentException, ResourceNotFoundException, ForbiddenAccessException, NoSuchAlgorithmException
    {
        if(!isEmailValid(email))
            throw new IllegalArgumentException("UsuariosService: um dos parametros enviados é considerado invalido");

        // obtem sal para senha
        Optional<String> salt = usuariosRepository.getDataCriacao(email);

        // email nao presente no banco
        if(salt.isEmpty())
            throw new ResourceNotFoundException("UsuariosService: email nao esta presente no banco");

        // gera senha
        try{
            senha = SenhaManager.hashPassword(senha, salt.get());
        }
        catch(NoSuchAlgorithmException ex){
            System.err.println(ex.toString());
            throw ex;
        }

        //  pega dados do usuario  
        Optional<UsuarioInterface> usuario = usuariosRepository.autenticaUsuario(email, senha);

        // se nao encontrou dados
        if(usuario.isEmpty())
            throw new ForbiddenAccessException("UsuariosService: impossivel fazer login (rpovavelmente senha incorreta)");

        // gera token de autenticacao
        Optional<String> authToken = JWTMan.encrypt(usuario.get());

        if(authToken.isEmpty())
            throw new RuntimeException("UsuariosService: impossivel encriptar dados do usuario " + usuario.get());


        // gera usuario autenticado e retorna
        UsuarioAutenticado response = UsuarioAutenticado.of(usuario.get(), authToken.get());

        return response;
    }


    public Optional<Pessoa> getPessoa(int id)
    {
        Optional<Pessoa> res = usuariosRepository.getPessoa(id);

        return res;
    }


    public Optional<Organizador> getOrganizador(int id)
    {
        Optional<Organizador> res = usuariosRepository.getOrganizador(id);

        return res;
    }


    public Optional<Moderador> getModerador(int id)
    {
        Optional<Moderador> res = usuariosRepository.getModerador(id);

        return res;
    }


    public boolean criaPessoa(
        String nome,
        String email,
        String senha
    ) throws IllegalArgumentException, ResourceAlreadyExistsException 
    {
        // valida dados
        if(!isNomeValid(nome) || !isEmailValid(email) || !isSenhaValid(senha))
            throw new IllegalArgumentException("UsuariosService: um dos parametros enviados é considerado invalido");

        // verifica se ja esiste
        Boolean existe = usuariosRepository.usuarioExiste(nome, email, "");

        if(existe)
            throw new ResourceAlreadyExistsException();

        try {
            senha = SenhaManager.generateSenha(senha);
        }
        catch(NoSuchAlgorithmException ex){
            System.err.println(ex.toString());
            throw new IllegalArgumentException(ex); // TODO: rehtorw com ecessao mais adequada
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
    ) throws IllegalArgumentException, ResourceAlreadyExistsException
    {
        // valida dados
        cpf = CpfChecker.limpaCpfCnpj(cpf);
        
        if(!isNomeValid(nome) || !isEmailValid(email) || !CpfChecker.isCpfCnpjValid(cpf) || !isSenhaValid(senha))
            throw new IllegalArgumentException("UsuariosService: um dos parametros enviados é considerado invalido");

        // verifica se ja esiste
        Boolean existe = usuariosRepository.usuarioExiste(nome, email, cpf);

        if(existe)
            throw new ResourceAlreadyExistsException();

        try {
            senha = SenhaManager.generateSenha(senha);
        }
        catch(NoSuchAlgorithmException ex){
            System.err.println(ex.toString());
            throw new IllegalArgumentException(ex); // TODO: rehtorw com ecessao mais adequada
        }

        // realiza criacao
        Boolean res = usuariosRepository.criaOrganizador(nome, email, cpf, senha);

        return res;
    }
}
