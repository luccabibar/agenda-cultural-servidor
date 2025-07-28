package bibar.com.agenda_cultural_servidor.endpoints.usuarios;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bibar.com.agenda_cultural_servidor.records.ResponseWrapper;

@RestController
@CrossOrigin
@RequestMapping("/usuarios")
public class UsuariosController
{
    private UsuariosService usuariosService;

    public UsuariosController(
        UsuariosService usuariosControllerInj
    ) {
        usuariosService = usuariosControllerInj;
    }
}
