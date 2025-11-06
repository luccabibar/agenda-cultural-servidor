package bibar.com.agenda_cultural_servidor.autenticacao;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    private List<EndpointMatch> doNotFilter;
    private JWTManager JWTMan;

    AutenticacaoFilter(
        JWTManager JWTManInj
    ) {
        JWTMan = JWTManInj;

        // TODO: essa lista deve ficar aqui??
        doNotFilter = Arrays.asList(
            EndpointMatch.of("GET","\\/ping(?:\\/[\\w\\d]+)*"), // match util: (?:\\/[\\w\\d]+)* = "/qualquer_coisa" 0 ou mais vezes
            EndpointMatch.of("POST","\\/ping(?:\\/[\\w\\d]+)*"), // match util: (?:\\/[\\w\\d]+)* = "/qualquer_coisa" 0 ou mais vezes
            EndpointMatch.of("GET","\\/recursos/img/[\\w\\d-_]+\\.[\\w\\d]+"), // recursos/img
            EndpointMatch.of("GET","\\/eventos"),
            EndpointMatch.of("GET","\\/eventos/filtros"),
            EndpointMatch.of("GET","\\/eventos/[\\d]+"),
            EndpointMatch.of("POST","\\/usuarios\\/login"),
            EndpointMatch.of("POST","\\/usuarios\\/pessoas"),
            EndpointMatch.of("POST","\\/usuarios\\/organizadores")
        );
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

        // verifica se valor em authHeader eh um token valido no formato certo, e entao extrai
        String authHeader = request.getHeader("Authorization");
        Optional<String> token = JWTManager.getTokenFromHeader(authHeader);

        // passa pro proximo filtro se token for valdia
        if(token.isPresent() && JWTMan.isTokenValid(token.get()))
            chain.doFilter(request, response);
        // set erro, nao passa pro proximo filtro
        else
            response.sendError(401, "Authorization: token nao e valido");
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException
    {
        String method = request.getMethod();
        String uri = request.getRequestURI();

        // verifica se URI combina com algum endpoint liberado
        boolean match = doNotFilter
            .stream()
            .anyMatch(edp -> edp.mathces(method, uri));
        
        System.out.println(method + " " + uri + " shuold not filter? " + (match ? "true" : "false"));

        // false = sera filtrado
        return match;
    }
}


record EndpointMatch (String method, String URIRegEx)
{
    public static EndpointMatch of(String methodInj, String URIRegExInj)
    {
        return new EndpointMatch(methodInj, URIRegExInj);
    }

    public boolean mathces(String cmpMethod, String cmpURI)
    {
        return cmpMethod.equals(method) && cmpURI.matches(URIRegEx);
    }
}
