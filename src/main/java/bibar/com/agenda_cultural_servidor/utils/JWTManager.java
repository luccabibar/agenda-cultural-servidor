package bibar.com.agenda_cultural_servidor.utils;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import bibar.com.agenda_cultural_servidor.endpoints.usuarios.records.Usuario;
import bibar.com.agenda_cultural_servidor.records.JWTUser;

@Service
public class JWTManager
{
    private String privateKey;
    private Long prazoValidade;
    private Algorithm algo;

    
    public JWTManager(
        @Value("${autenticacao.secret_key}") String privateKeyInj, 
        @Value("${autenticacao.tempo_validade}") Long prazoValidadeInj
    ) {
        prazoValidade = prazoValidadeInj;
        privateKey = privateKeyInj;

        // Define o algoritmo com a private key
        algo = Algorithm.HMAC256(privateKey);
    }


    public static boolean isAuthHeaderValid(String authHeader)
    {
        return authHeader != null
            && authHeader.matches("[Bb]earer [\\w\\d_-]+\\.[\\w\\d_-]+\\.[\\w\\d_-]+");
    }


    public static Optional<String> getTokenFromHeader(String authHeader)
    {
        if(JWTManager.isAuthHeaderValid(authHeader))
            return Optional.of(authHeader.split(" ")[1]);

        else
            return Optional.empty();
    }


    // talvez deva ser private ??
    public Optional<String> encrypt(JWTUser target)
    {
        try {
            // constroi token
            String token = JWT
                .create()
                .withClaim("sub", target.sub())
                .withClaim("role", target.role().valor)
                .withClaim("iat", target.iat())
                .withClaim("exp", target.iat().plusMillis(prazoValidade))
                .sign(algo);

                // .withIssuer(ISSUER) // Define o emissor do token
                // .withIssuedAt(creationDate()) // Define a data de emissão do token
                // .withExpiresAt(expirationDate()) // Define a data de expiração do token
                // .withSubject(user.getUsername()) // Define o assunto do token (neste caso, o nome de usuário)
                // .sign(algorithm); // Assina o token usando o algoritmo especificado
            
            return Optional.of(token);
        }
        catch (JWTCreationException ex){
            System.err.println("JWTManager:    Erro ao gerar token \n" + target.toString() + " \n" + ex.toString());
            return Optional.empty();
        }
    }


    public Optional<String> encrypt(Usuario target)
    {
        // altenrativa de implementacao: criar um JWTUser e preencher seus dados do zero, a fim de controlar o iat
        Optional<JWTUser> user = JWTUser.of(target);

        if(user.isPresent())
            return encrypt(user.get());
        else
            return Optional.empty();
    }


    public Optional<JWTUser> decrypt(String target)
    {
        try {
            DecodedJWT token = JWT
                .require(algo)
                .build()
                .verify(target);

            JWTUser result = new JWTUser(
                token.getClaim("sub").asInt(),
                token.getClaim("role").asString(),
                token.getClaim("iat").asInstant()
            );

            return Optional.of(result);
        }
        catch (TokenExpiredException ex){
            System.err.println("JWTManager:    Token expirado \n" + target.toString() + " \n" + ex.toString());
            return Optional.empty();
        }
        catch (JWTCreationException ex){
            System.err.println("JWTManager:    Erro ao ler token \n" + target.toString() + " \n" + ex.toString());
            return Optional.empty();
        }
    }


    public boolean isTokenValid(String target)
    {
        try {
            JWT.require(algo)
                .build()
                .verify(target);

            return true;
        }
        catch (JWTCreationException ex){
            System.err.println("JWTManager:    Token expirado \n" + target.toString() + " \n" + ex.toString());
            return false;
        }
        catch (TokenExpiredException ex){
            System.err.println("JWTManager:    Token invalido \n" + target.toString() + " \n" + ex.toString());
            return false;
        }
        catch (Exception ex){
            System.err.println("JWTManager:    Token invalido \n" + target.toString() + " \n" + ex.toString());
            return false;
        }
    }


    public Optional<JWTUser> fullDecryptFromHeader(String authHeader)
    {
        Optional<String> token = getTokenFromHeader(authHeader);
        
        if(token.isEmpty()){
            System.err.println("JWTManager:    Impossível processar decrypt: Header de autorizacao invalida: " + authHeader);
            return Optional.empty();
        }
        if(!isTokenValid(token.get())){
            System.err.println("JWTManager:    Impossível processar decrypt: Token invalido (vide log anterior): " + token);
            return Optional.empty();
        }

        Optional<JWTUser> res = decrypt(token.get());
        
        if(res.isEmpty()){
            System.err.println("JWTManager:    Impossível processar decrypt: Erro desencriptar (vide log anterior): " + token);
            return Optional.empty();
        }

        return res;
    } 
}
