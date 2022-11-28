package br.com.marcoshssilva.ecommerce.domain.services.etc;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import br.com.marcoshssilva.ecommerce.domain.exceptions.throwables.FileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Service
public class S3Service {

    private static final Logger LOG = LoggerFactory.getLogger(S3Service.class);

    @Autowired
    private AmazonS3 s3client;

    @Value("${s3.bucket}")
    private String bucketName;

    public URI uploadFile(MultipartFile file) {
        try {
            String filename = file.getOriginalFilename();
            InputStream is = file.getInputStream();
            String contentType = file.getContentType();
            
            return uploadFile(is, filename, contentType);
            
        } catch (IOException ex) {
            throw new FileException("Erro de IO: " + ex.getMessage());
            
        }

    }

    public URI uploadFile(InputStream is, String fileName, String contentType) {

        ObjectMetadata metadata = new ObjectMetadata();

        LOG.info("Iniciando o UPLOAD");
        s3client.putObject(bucketName, fileName, is, metadata);
        LOG.info("UPLOAD completo");

        try {
            return s3client.getUrl(bucketName, fileName).toURI();

        } catch (URISyntaxException ex) {
            throw new RuntimeException("ERRO ao converter URL para URI");

        }

    }
}
