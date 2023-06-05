package com.hops.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hops.helper.FileUploadHelper;
import com.hops.model.User;
import com.hops.repository.UserRepository;
import com.hops.service.UserService;
import com.hops.util.CsvFileGenerator;
import com.hops.util.ExcelGenerator;
import com.hops.util.PdfGenerator;
import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin("*")
public class FileUploadController {

	@Autowired
	private FileUploadHelper fileUploadHelper;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private UserRepository userRepository;

	
	@Autowired
	private UserService userService;
	

	@Autowired
	private CsvFileGenerator csvGenerator;
	
	
	
	
	@PostMapping("/upload-file")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,@RequestParam ("user") String userdata) throws JsonMappingException, JsonProcessingException {

		User readvalue = mapper.readValue(userdata, User.class);
		
//		System.out.println(file.getName());
//		System.out.println(file.getOriginalFilename());
//		System.out.println(file.getContentType());
//		System.out.println(file.getSize());

		try {

			if (file.isEmpty()) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Request must contain file");

			}

			boolean f = fileUploadHelper.uploadFile(file);
			if (f) {
				// return ResponseEntity.ok("File is successfully uploaded");

				
				//User user=null;
				String url= ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/")
						.path(file.getOriginalFilename()).toUriString();
				
				System.out.println("Image url is :"+url);
				

				
				readvalue.setImage(url);
				
				userRepository.save(readvalue);
				System.out.println(url);
				
				
				
				return ResponseEntity.status(HttpStatus.CREATED).body("save image");

			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("some went wrong ! try again");

	}
	
	@GetMapping("/getAllUsers")
	public List<User> getUsers(){
		return userRepository.findAll();
	}
	
	@GetMapping("/getAllUsers/{id}")
	public ResponseEntity<User> getUserById(@PathVariable int id)
	{
		User user = userRepository.findById(id).orElseThrow();
		return ResponseEntity.ok(user);
		
	}
	
	@PutMapping("/update-file")
	public ResponseEntity<String> updateFile(@RequestParam("file") MultipartFile file,@RequestParam ("user") String userdata) throws JsonMappingException, JsonProcessingException {

		User readvalue = mapper.readValue(userdata, User.class);
		
//		System.out.println(file.getName());
//		System.out.println(file.getOriginalFilename());
//		System.out.println(file.getContentType());
//		System.out.println(file.getSize());

		try {

			if (file.isEmpty()) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Request must contain file");

			}

			boolean f = fileUploadHelper.uploadFile(file);
			if (f) {
				// return ResponseEntity.ok("File is successfully uploaded");

				
				//User user=null;
				String url= ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/")
						.path(file.getOriginalFilename()).toUriString();
				
				System.out.println("Image url is :"+url);
				

				
				readvalue.setImage(url);
				
				userRepository.save(readvalue);
				System.out.println(url);
				
				
				
				return ResponseEntity.status(HttpStatus.CREATED).body("save image");

			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("some went wrong ! try again");

	}
	

	
	
	
	@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable int id)
	{
		userRepository.deleteById(id);
		return "user deleted with id : "+id;
		
	}
	
	
	
	
	  @GetMapping("/export-to-excel")
	    public void exportIntoExcelFile(HttpServletResponse response) throws IOException {
	        response.setContentType("application/octet-stream");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());

	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=student" + currentDateTime + ".xlsx";
	        response.setHeader(headerKey, headerValue);

	        List <User> listofUsers = userService.getTheListUser();
	        ExcelGenerator generator = new ExcelGenerator(listofUsers);
	        generator.generateExcelFile(response);
	    }
	
	  @GetMapping("/export-to-pdf")
	  public void generatePdfFile(HttpServletResponse response) throws DocumentException, IOException 
	  {
	    response.setContentType("application/pdf");
	    DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
	    String currentDateTime = dateFormat.format(new Date());
	    String headerkey = "Content-Disposition";
	    String headervalue = "attachment; filename=student" + currentDateTime + ".pdf";
	    response.setHeader(headerkey, headervalue);
	    List < User > listofUsers = userService.getTheListUser();
	    PdfGenerator generator = new PdfGenerator();
	    generator.generate(listofUsers, response);
	  }
	  
	  @GetMapping("/export-to-csv")
	  public void exportIntoCSV(HttpServletResponse response) throws IOException {
	    response.setContentType("text/csv");
	    response.addHeader("Content-Disposition", "attachment; filename=\"student.csv\"");
	    csvGenerator.writeUsersToCsv(userService.getTheListUser(), response.getWriter());
	  }
	  

}
