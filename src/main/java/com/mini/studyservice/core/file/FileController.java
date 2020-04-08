package com.mini.studyservice.core.file;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import com.mini.studyservice.core.config.DefaultConfig;

@RestController
@RequestMapping(DefaultConfig.APP_NAME)
public class FileController {
	private final FileService filseService;
	
	@Autowired
	public FileController(FileService fileService) {
		this.filseService = fileService;
	}
	
	@PostMapping("/upload/file")
	public ResponseEntity<UploadFileResponse> uploadFile(@RequestParam("file") MultipartFile file) throws MultipartException {
		UploadFileResponse uf = filseService.storeFile(file);
		return new ResponseEntity<UploadFileResponse>(uf,HttpStatus.CREATED);
	}
	@GetMapping("/download/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request){
		Resource resource = filseService.loadFileAsResource(fileName);
		 
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
 
        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
 
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
	}
	@GetMapping("/img/{fileName}")
	public void getImage(HttpServletResponse response, @PathVariable String fileName) throws IOException{
		filseService.getImage(response, fileName);
	}
	@GetMapping("/thumbnail/{fileName}")
	public void getThumbnail(HttpServletResponse response, @PathVariable String fileName) {
		filseService.getThumbnailImage(response, fileName);
	}
}
