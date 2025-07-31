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


    public boolean criaPessoa(
        String nome,
        String email,
        String senha
    ) {
        Boolean existe = usuariosRepository.usuarioExiste(nome, email, "");

        return existe;

        // if(existe)
        //     return false;
    }
}
