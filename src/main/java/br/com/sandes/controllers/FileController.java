package br.com.sandes.controllers;

import br.com.sandes.data.vo.v1.UploadFileResponseVO;
import br.com.sandes.services.FileStorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Tag(name = "File Endpoint")
@RestController
@RequestMapping("/api/file/v1")
public class FileController {

    private Logger logger = Logger.getLogger(FileController.class.getName());

    @Autowired
    private FileStorageService service;

    @PostMapping("/uploadFile")
    public UploadFileResponseVO uploadFile(@RequestParam("file") MultipartFile file){

        logger.info("Storing file to disk");

        //armazenando o caminho do arquivo em uma variavel;
        var fileName = service.storeFile(file);

        //difinindo o caminho de download do arquivo;

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                    .path("/api/file/v1/downloadFile/")
                    .path(fileName)
                .toUriString();

        return new UploadFileResponseVO(
                fileName,
                fileDownloadUri,
                file.getContentType(),
                file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponseVO> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files){

        logger.info("Storing files to disk");

        //iterando em cima do array de multipartfiles;

        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    //setando um parametro para que o endpoint possa reconhecer o tipo do arquivo;
    @GetMapping("/downloadFile/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable String filename,
            HttpServletRequest request){

        logger.info("Reading a file from disk");

        //obtendo o recurso;
        Resource resource = service.loadFileAsResource(filename);

        //devolvendo o conteudo que esta sendo baixado;
        var contentType = "";

        try{
            contentType = request
                    .getServletContext()
                    .getMimeType(resource.getFile().getAbsolutePath());

        } catch (Exception e){
            logger.info("Could not determine file type!");
        }

        if(contentType.isBlank()){
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
