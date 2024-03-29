package com.smart.smarycontactmanager.smartcontroller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
// import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.smarycontactmanager.helper.Message;
import com.smart.smarycontactmanager.smartdao.UserRepository;
import com.smart.smarycontactmanager.smartentities.Contact;
import com.smart.smarycontactmanager.smartentities.User;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		//principal will give current logged in user name
		String userName = principal.getName();
		System.out.println(userName);
		
		//get user from db
		User user = this.userRepository.getUserByUserName(userName);
		System.out.println(user);
		model.addAttribute("user", user);
	}
    
    @PostMapping("/index")
    public String dashboard(Model model,Principal principal){
       
        return "normal/user_dashboard"; 
    }

    @GetMapping("/add-contact")
    public String openAddContactForm(Model model){

        model.addAttribute("title", "Add Contact");
        model.addAttribute("contact", new Contact());

        return "normal/add_contact_form";
    }

    @PostMapping("/process-contact")
	public String processAddContact(@Validated @ModelAttribute Contact contact, BindingResult result ,@RequestParam("profileImage") MultipartFile mpFile,Principal principal, Model model, HttpSession session) {

		Path destPath = null;
		String originalFilename = null;
		
		 /** making image name unique*/
		// String currDateTime= (LocalDateTime.now()+"").replace(":", "-");
		
		/**
		 * Here we added contact to respective user list to get list of contact using method
		 * First we retrieve current user
		 * next add current user into his contact fields
		 * next add this contact info retrieved from form data into users contact List
		 * send this updated contact form data to user contact-list
		 * */
		
		try {
		/**
		 * Setting explicitly retrieving image data using @RequestParam, first save image into /resource/static/image folder then  
		 * save this image unique name into database as a Url string 
		 * */
			
         String name = principal.getName();
		User user = this.userRepository.getUserByUserName(name);
			if(mpFile.isEmpty()) {
				System.out.println("file is empty");
			//	throw new Exception("Image file must not selected..!!");
				originalFilename = "contact_profile.png";
			}else {
				// originalFilename = currDateTime+"@"+mpFile.getOriginalFilename();
                File savedFile = new ClassPathResource("/static/image").getFile();
                destPath = Paths.get(savedFile.getAbsolutePath()+File.separator+mpFile.getOriginalFilename());
                Files.copy(mpFile.getInputStream(), destPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image path :"+destPath);
			}	
				/** retrieve current class-path resource folder relative path */
				 
			 
				
				 
				contact.setImage(originalFilename);
			
			/** first complete contact form setting all attributes details */
			contact.setUser(user);
			
			/** Retrieving current users all contact list and again add current retrieved contact info into the list */
			user.getContacts().add(contact);	
			this.userRepository.save(user);
			System.out.println("Data :"+contact);
		/** save this updated or added contacts  user into database  */
		// User addedContactResult = userService.addContactInUser(currentLogInUserDetails);
		
		// if(addedContactResult !=null) {
			
		// 	/** Now actual storing image into given path, when first Registered this file path location into DB then now*/
		// 	Files.copy(mpFile.getInputStream(), destPath, StandardCopyOption.REPLACE_EXISTING);
		// 	System.out.println("After successful contact added : "+addedContactResult);
		// }
		
		/** success message alert */
		session.setAttribute("message", new Message("Contact saved successfully.....!!", "success"));
		model.addAttribute("contact", new Contact());
		return "normmal/add_contact_form";
		
		}catch(Exception e) {
			
			System.out.println("Error : "+e);
			e.printStackTrace();
			model.addAttribute("contact", contact);

			/** failure message alert */
			session.setAttribute("message", new Message("Something goes wrong, please try again.....!!", "danger"));
			return "normal/add_contact_form";
		}

	}
}
