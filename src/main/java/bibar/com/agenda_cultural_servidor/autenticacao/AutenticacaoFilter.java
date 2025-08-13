package bibar.com.agenda_cultural_servidor.autenticacao;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import bibar.com.agenda_cultural_servidor.utils.JWTManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class AutenticacaoFilter extends OncePerRequestFilter
{
    // private List<Object> doNotFilter;
    private JWTManager JWTMan;

    AutenticacaoFilter(
        JWTManager JWTManInj
    ) {
        JWTMan = JWTManInj;
    }


    private boolean isAuthHeaderValid(String authHeader)
    {
        return authHeader != null
            && authHeader.matches("[Bb]earer [\\w\\d_-]+\\.[\\w\\d_-]+\\.[\\w\\d_-]+");
    }


    @Override
    public void doFilterInternal(
        HttpServletRequest request, 
        HttpServletResponse response, 
        FilterChain chain
    ) throws ServletException, IOException
    {
        System.out.println("AutenticacaoFilter:    " + request.getMethod() + " " + request.getRequestURL());
        
        // preflight
        if (request.getMethod() == "OPTIONS") {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        
        if(!isAuthHeaderValid(authHeader)){
            chain.doFilter(request, response); // TODO: remover pos should not fileter
            return; 
        }
        
        String token = authHeader.split(" ")[1];
        System.out.println("JWT:    " + token); 


        if(JWTMan.isValid(token))
            System.out.println("omagaaaa toekn valida!! :D");
            chain.doFilter(request, response);
    }


    // TODO: setup should not filter
    // @Override
    // protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException
    // {
    //     return doNotFilter
    //         .stream()
    //         .anyMatch(obj -> obj.equals(request.getRequestURL())); // TODO: implement filter
    // }
}
