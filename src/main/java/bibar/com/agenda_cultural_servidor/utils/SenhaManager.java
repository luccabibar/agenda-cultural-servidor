package bibar.com.agenda_cultural_servidor.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// TODO: revisar esse nome ae
public class SenhaManager
{
    public static String hashPassword(String senha, String sal) throws NoSuchAlgorithmException
    {
        senha += sal;
        
        // cria hash
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(senha.getBytes(StandardCharsets.UTF_8));
        
        // formata como string
        StringBuilder str = new StringBuilder(2 * hash.length);

        for(int ii = 0; ii < hash.length; ii++) {
            String hex = Integer.toHexString(0xff & hash[ii]);

            if(hex.length() == 1)
                str.append('0');
            
            str.append(hex);
        }

        return str.toString(); 
    }


    public static String generateSenha(String senha) throws NoSuchAlgorithmException
    { 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String salt = LocalDate.now().format(formatter);
    
        return hashPassword(senha, salt);
    }
}
