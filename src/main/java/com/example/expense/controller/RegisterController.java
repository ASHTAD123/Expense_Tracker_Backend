package com.example.expense.controller;


import com.example.expense.Entities.RegisterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.expense.Service.RegisterService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/expenseTracker")
@CrossOrigin(origins="https://symphonious-otter-07cb0e.netlify.app")
public class RegisterController {

	@Autowired
	private RegisterService registrationService;
	
	ResponseEntity<String> responseString = null;
	
	@PostMapping(value = "/register")
	public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterEntity register) {
		return responseString = registrationService.register(register);
	}
	@PostMapping("/login")
	public ResponseEntity<String> LoginUser(@Valid @RequestBody RegisterEntity login,HttpServletRequest request,HttpServletResponse response) {
		return responseString = registrationService.loginUser(request,response,login);
	}

	@PostMapping("/logout")
	public ResponseEntity<String> LogoutUser(@Valid @RequestBody RegisterEntity login, HttpServletRequest request,HttpServletResponse response) {
		 Cookie cookieToDelete = new Cookie("loggedUser", "");
		 cookieToDelete.setDomain("localhost");
         cookieToDelete.setMaxAge(0);
         cookieToDelete.setPath("/expenseTracker"); // Ensure the path is the same as the original cookie
         response.addCookie(cookieToDelete);
		return responseString = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("You have logged out");
	}
}
