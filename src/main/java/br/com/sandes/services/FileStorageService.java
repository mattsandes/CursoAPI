package br.com.sandes.services;

import br.com.sandes.config.FileStorageConfig;
import br.com.sandes.exceptions.FileStorageException;
import br.com.sandes.exceptions.MyFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    //caminho completo ate a pasta onde os arquivos serão salvas;
    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {

        //trasnformando o caminho para uma path um tipo de path reconhecido pelo java;
        //e criando o diretorio onde os arquivos serão armazenados;
        Path path = Paths.get(fileStorageConfig.getUploadDir())
                .toAbsolutePath().normalize();

        this.fileStorageLocation = path;

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e){
            throw new FileStorageException("Could not created the directory where the uploaded file will be stored!", e);
        }
    }

    //metodo para o armazenamento de arquivos em disco;
    public String storeFile(MultipartFile file){
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(filename.contains("..")){
                throw new FileStorageException(
                        "Sorry, filename contains invalid path sequence " + filename);
            }

            Path targetLocation = this.fileStorageLocation.resolve(filename);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return filename;

        } catch (Exception e){
            throw new FileStorageException("Could not store file " + filename + ". Please try again!");
        }
    }

    public Resource loadFileAsResource(String filename){
        try{
            //pegando o file do arquivo
            Path filePath = this.fileStorageLocation.resolve(filename).normalize();

            //otendo o recurso atraves do filepath;
            Resource resource = new UrlResource(filePath.toUri());

            //verificando se o resourse existe;
            if(resource.exists()){
                return resource;
            }
            else{
                throw new MyFileNotFoundException("File not found");
            }

        } catch (Exception e){
            throw new MyFileNotFoundException("File not found" + filename, e);
        }
    }
}
