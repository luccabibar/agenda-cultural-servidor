package bibar.com.agenda_cultural_servidor.recursos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RecursosEstaticosConfig implements WebMvcConfigurer
{
    String recursosDir;

    RecursosEstaticosConfig (
        @Value("${recursos.diretorio}") String recursosDirInj
    ) {
        recursosDir = recursosDirInj;
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        String resourceHandler = recursosDir + "**"; // i.e. recursos/**
        String resourceLocation = "file:" + recursosDir ; // ex.: file:recursos/

        registry.addResourceHandler(resourceHandler)
                .addResourceLocations(resourceLocation);
    }
}