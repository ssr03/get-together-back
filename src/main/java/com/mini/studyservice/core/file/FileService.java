package com.mini.studyservice.core.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mini.studyservice.core.config.DefaultConfig;
import com.mini.studyservice.core.config.FileStorageConfig;
import com.mini.studyservice.core.util.image.ImageUtils;

@Service
public class FileService {
	private final Path fileStorageLocation;
	private final Path thumbnailLocation;
	
	@Autowired
	public FileService(FileStorageConfig fileStorageConfig) {
		this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir())
				.toAbsolutePath().normalize();
		this.thumbnailLocation = Paths.get(fileStorageConfig.getThumbnailDir())
				.toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
			Files.createDirectories(this.thumbnailLocation);
		} catch (Exception e) {
			throw new FileStorageException("파일이 저장 될 디렉토리를 생성하지 못했습니다.", e);
		}
	}
	private String getDownloadFileUri(String fileName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
        		.path("/study/download/")
                .path(fileName)
                .toUriString();
    }
	 public UploadFileResponse storeFile(MultipartFile file) throws MultipartException{
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        UUID uuid = UUID.randomUUID();
        String storedName = uuid.toString() + fileName;

        try {
            if(fileName.contains("..")) {
                throw new FileStorageException("파일명에 허용되지 않는 문자가 포함되어 있습니다." + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(storedName); 
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            
            UploadFileResponse uf = new UploadFileResponse();
            uf.setStoredName(storedName);
            uf.setFileDownloadUri(getDownloadFileUri(storedName));
            
            Path ThumnailTargetLocation = this.thumbnailLocation.resolve(storedName);
            
            String type = storedName.substring(storedName.lastIndexOf(".")+1);
            ImageUtils.createThumbnail(targetLocation.toString(), ThumnailTargetLocation.toString(), type, DefaultConfig.THUMBNAIL_WIDTH, DefaultConfig.THUMBNAIL_HEIGHT);
            return uf;

        } catch(IOException ex) {
            throw new FileStorageException("파일 " + fileName + "을 저장할 수 없습니다. 다시 시도해 보세요.", ex);
        }
    }
	 public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
      
            Resource resource = new UrlResource(filePath.toUri());
                if(resource.exists()) {
                    return resource;
                } else {
                    throw new CustomFileNotFoundException("파일을 찾을 수 없습니다." + fileName);
                }
	    } catch(MalformedURLException ex) {
	        throw new CustomFileNotFoundException("파일을 찾을 수 없습니다." + ex);
	    }
	}
	 public Resource loadThumbnailImageAsResource(String fileName) {
		 Path imagePath = this.thumbnailLocation.resolve(fileName).normalize();
		 
		 Resource resource;
		try {
			resource = new UrlResource(imagePath.toUri());

			 if(resource.exists()) {
				 return resource;
			 }else {
				 throw new CustomFileNotFoundException("이미지를 찾을 수 없습니다."+fileName);
			 }
		 } catch (MalformedURLException e) {
			 throw new CustomFileNotFoundException("파일을 찾을 수 없습니다." + e);	
		 }
	 }
	 public void getImage(HttpServletResponse response,String fileName) throws IOException {
		Resource resource = loadFileAsResource(fileName);
		String filePath = resource.getFile().getAbsolutePath();
		File file = new File(filePath);
		if(file.exists()) {
			String contentType = "application/octet-stream";
			response.setContentType(contentType);
			OutputStream out = response.getOutputStream();
			FileInputStream in = new FileInputStream(file);
			
			IOUtils.copy(in, out);
			out.close();
			in.close();
		}else {
			throw new FileNotFoundException();
		}
	 }
	 public void getThumbnailImage(HttpServletResponse response, String fileName) {
		 Resource resource = loadThumbnailImageAsResource(fileName);
		 try {
			String imgPath = resource.getFile().getAbsolutePath();
			File file = new File(imgPath);
			if(file.exists()) {
				String contentType = "application/octet-stream";
				response.setContentType(contentType);
				OutputStream out = response.getOutputStream();
				FileInputStream in = new FileInputStream(file);
				
				IOUtils.copy(in, out);
				out.close();
				in.close();
			}else {
				throw new FileNotFoundException();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	 }
}
