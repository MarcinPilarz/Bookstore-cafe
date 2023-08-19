package springboot.bookstorecafe.controllers;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import springboot.bookstorecafe.services.ProductImageService;

@RestController
public class ProductImageController {

	@Autowired
	private ProductImageService imageService;

	@PostMapping(value="/addImage")
	public String addImageProduct(HttpServletRequest request,@RequestParam("image") MultipartFile file) throws IOException, SerialException, SQLException{
		
		byte[] bytes=file.getBytes();
		
		//Blob blob= new javax.sql.rowset.SerialBlob(bytes);
		
		return null;
		
	}
}
