package in.tech_camp.furima.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class SaveImageFileUtil {

  private static final String UPLOAD_DIR = "uploads";

  public static String saveImageFile(MultipartFile image) throws IOException {
    String originalFilename = image.getOriginalFilename();

    String fileName = UUID.randomUUID().toString() + "_" + originalFilename;

    Path uploadPath = Paths.get(UPLOAD_DIR);

    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }

    Path filePath = uploadPath.resolve(fileName);
    Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

    return fileName;
  }
}