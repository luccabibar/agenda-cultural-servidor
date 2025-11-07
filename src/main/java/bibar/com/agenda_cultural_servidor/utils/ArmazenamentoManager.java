package bibar.com.agenda_cultural_servidor.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import bibar.com.agenda_cultural_servidor.records.ArquivoTipo;

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


    public Optional<String> armazenaImagem(MultipartFile imagem) throws IllegalArgumentException
    {
        Path destino = recursosImg;
        Path localImagem;

        try {
            // define nome
            String nomeImagem = UUID.randomUUID().toString();
            String contentType = imagem.getContentType();

            // verifica se o mimetype nao ta zoado
            MediaType tipo = MediaType.parseMediaType(contentType);
            
            if(tipo.equals(MediaType.IMAGE_JPEG))
                nomeImagem += ".jpg";

            else if(tipo.equals(MediaType.IMAGE_PNG))
                nomeImagem += ".png";

            else
                throw new InvalidMediaTypeException(contentType,"ArmazenamentoManager: content type ilegal");


            // define subdiretorio
            String subdiretorio = String.valueOf(nomeImagem.charAt(0));
            
            // define destino
			destino = destino.resolve(subdiretorio);

            if(!Files.exists(destino))
                Files.createDirectories(destino);

            destino = destino.resolve(nomeImagem);

            // realiza copia ao destino
			InputStream imagemIS = imagem.getInputStream();
			Files.copy(imagemIS, destino, StandardCopyOption.REPLACE_EXISTING);

            // anota caminho da imagem
            localImagem = Paths.get(subdiretorio).resolve(nomeImagem);
        }
        catch (InvalidPathException ex) {
            System.err.println("ArmazenamentoManager: Impossivel salvar arquivo, caminho invalido: " + ex.toString());
            return Optional.empty();
        }
        catch (InvalidMediaTypeException ex) {
            System.err.println("ArmazenamentoManager: Impossivel salvar arquivo, content type invalido: " + ex.toString());
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

        // se tudo correu bem, retorna caminho da imagem (sem a base do caminho)
        return Optional.of(localImagem.toString());
    }


    public Optional<ArquivoTipo> getImagem(String caminhoImagem)
    {
        Path caminho = recursosImg;

        try{
            // procura a imagem
            caminho = recursosImg.resolve(caminhoImagem);

            // se nao existe ou esta tentando acessar um lugar nao autorizado
            if(!Files.exists(caminho) || !caminho.startsWith(recursosImg))
                return Optional.empty();


            // encontrou, pega dados
            byte[] imagem = Files.readAllBytes(caminho);
            String tipoStr = Files.probeContentType(caminho);

            // verifica se o mimetype nao ta zoado
            MediaType tipo = MediaType.parseMediaType(tipoStr);            
            
            if(!tipo.equals(MediaType.IMAGE_JPEG) && !tipo.equals(MediaType.IMAGE_PNG))
                throw new InvalidMediaTypeException(tipoStr, "MediaType deve ser PNG ou JPEG");
            
            // monta resposta
            ArquivoTipo response = new ArquivoTipo(imagem, tipo);
            return Optional.of(response);
        }
        catch(IOException ex){
			System.err.println("ArmazenamentoManager: Impossivel buscar arquivo, erro de IO: " + ex.toString());
			return Optional.empty();
        }
        catch(InvalidMediaTypeException ex){
			System.err.println("ArmazenamentoManager: Impossivel buscar arquivo, MediaType invalido: " + ex.toString());
			return Optional.empty();
        }
        catch(InvalidPathException ex){
			System.err.println("ArmazenamentoManager: Impossivel buscar arquivo, caminho invalido: " + ex.toString());
			return Optional.empty();
        }
    }
}
