package br.com.marcoshssilva.ecommerce.services;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.marcoshssilva.ecommerce.services.exceptions.FileException;

@Service
public class ImageService {

    public BufferedImage getImageFromFile(MultipartFile uploadedFile) {
        String ext = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());
        if (!"png".equals(ext) && !"jpg".equals(ext)) {
            throw new FileException("Imagem não é JPG ou PNG");
        }

        try {
            BufferedImage img = ImageIO.read(uploadedFile.getInputStream());
            if ("png".equals(ext)) {
                img = toJpg(img);
            }

            return img;
        } catch (IOException ex) {
            throw new FileException("Erro ao ler o arquivo");
        }
    }

    public static BufferedImage toJpg(BufferedImage img) {
        BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        jpgImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
        return jpgImage;
    }

    public InputStream getInputStream(BufferedImage img, String extension) {
        ByteArrayOutputStream oss = new ByteArrayOutputStream();
        try {
            ImageIO.write(img, extension, oss);
        } catch (IOException ex) {
            throw new FileException("Erro ao ler arquivo");
        }
        return new ByteArrayInputStream(oss.toByteArray());
    }

    public BufferedImage cropSquare(BufferedImage sourceImg) {
        int min = sourceImg.getHeight() <= sourceImg.getWidth() ? sourceImg.getHeight() : sourceImg.getWidth();
        return Scalr.crop(
                sourceImg,
                (sourceImg.getWidth() / 2) - min / 2,
                (sourceImg.getHeight() / 2) - min / 2,
                min,
                min
        );
    }

    public BufferedImage resize(BufferedImage sourceImg, int size) {
        return Scalr.resize(
                sourceImg,
                Scalr.Method.ULTRA_QUALITY,
                size);
    }
}
