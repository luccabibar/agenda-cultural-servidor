package bibar.com.agenda_cultural_servidor.endpoints.usuarios;

import org.springframework.stereotype.Service;

@Service
public class UsuariosService
{
    private UsuariosRepository usuariosRepository;

    public UsuariosService (
        UsuariosRepository usuariosRepositoryInj
    ) {
        usuariosRepository = usuariosRepositoryInj;
    }
}
