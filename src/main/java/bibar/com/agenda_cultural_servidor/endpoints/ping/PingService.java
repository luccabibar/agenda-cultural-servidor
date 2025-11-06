package bibar.com.agenda_cultural_servidor.endpoints.ping;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PingService
{
	Path recursosImg; 


	public PingService (
		@Value("${recursos.diretorio.img}") String recursosImgInj
	) {
		recursosImg = Paths.get(recursosImgInj);

		System.out.println("CONSTRUTOR PING SERVICE???????????????? " + recursosImg.toString());


		if(!Files.exists(recursosImg)){
			try{
				Files.createDirectories(recursosImg);
				System.out.println("criado com sucesso");
			}
			catch(IOException ex){
				System.err.println("Impossivel acessar ou criar diretorio de recursos: " + ex.getMessage());
			}
		}
		else{
			System.out.println("ja existe");
		}
	}


    public String ping()
	{
        return "pong";
    }


    public boolean storeImage(String texto, MultipartFile imagem)
    {
        System.out.println("Sobre a imagem: ");
        System.out.println("Nome: " + imagem.getName());
        System.out.println("Texto: " + texto);
        System.out.println("Nome original: " + imagem.getOriginalFilename());
        System.out.println("Content Type: " + imagem.getContentType());
        System.out.println("Tamanho: " + imagem.getSize() + "b");

        try {
			
			Path destino = recursosImg;
			System.out.println(destino.toString());

			destino = destino.resolve(imagem.getOriginalFilename());
			System.out.println(destino.toString());

            // imagem.transferTo(destino.toFile());
			
			InputStream imagemIS = imagem.getInputStream();
			Files.copy(imagemIS, destino, StandardCopyOption.REPLACE_EXISTING);
        } 
		catch (IOException ex) {
			System.err.println("Impossivel salvar arquivo: " + ex.toString());
			return false;
        }
		catch (IllegalStateException ex) {
			System.err.println("Impossivel salvar arquivo: " + ex.toString());
			return false;
        }
		catch (Exception ex) {
			System.err.println("Impossivel salvar arquivo: " + ex.toString());
			return false;
        }

        return true;
    }
}
