package bibar.com.agenda_cultural_servidor.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ArmazenamentoManager
{
	Path recursosImg; 

    
	public ArmazenamentoManager (
		@Value("${recursos.diretorio.img}") String recursosImgInj
	) {
        try{
            recursosImg = Paths.get(recursosImgInj);
            
            if(!Files.exists(recursosImg))
                Files.createDirectories(recursosImg);
        }
        catch(InvalidPathException ex){
            System.err.println("ArmazenamentoManager: Impossivel definir caminho para recursosImg: " + ex.getMessage());
        }
        catch(IOException ex){
            System.err.println("ArmazenamentoManager: Impossivel acessar ou criar diretorio de recursosImg: " + ex.getMessage());
        }
	}


    public Optional<String> ArmazenaImagemEvento(MultipartFile imagem)
    {
        Path destino;

        try {
            // define nome
            String nome = "placeholder";

            // define destino
			destino = recursosImg;
			destino = destino.resolve(nome);

            // realiza copia ao destino
			InputStream imagemIS = imagem.getInputStream();
			Files.copy(imagemIS, destino, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (InvalidPathException ex) {
            System.err.println("ArmazenamentoManager: Impossivel salvar arquivo, caminho invalido: " + ex.toString());
            return Optional.empty();
        }
        catch (IOException ex) {
			System.err.println("ArmazenamentoManager: Impossivel salvar arquivo, erro de I/O: " + ex.toString());
			return Optional.empty();
        }
		// catch (IllegalStateException ex) {
		// 	System.err.println("ArmazenamentoManager: Impossivel salvar arquivo: " + ex.toString());
		// 	return Optional.empty();
        // }
		catch (Exception ex) {
			System.err.println("ArmazenamentoManager: Impossivel salvar arquivo: " + ex.toString());
			return Optional.empty();
        }

        return Optional.of(destino.toString());
    }
}
