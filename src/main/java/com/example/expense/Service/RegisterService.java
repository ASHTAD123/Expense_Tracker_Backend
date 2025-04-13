package com.example.expense.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.expense.Dao.RegistrationRepo;
import com.example.expense.Entities.RegisterEntity;

import ch.qos.logback.core.util.Duration;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class RegisterService {

	@Autowired
	private RegistrationRepo registerRepo;
	
	
	public Boolean checkIfUserAlreadyExistsByEmail(RegisterEntity registeredUser) {

		Boolean userAlreadyExistsByEmail = false;

		try {
			RegisterEntity registeredUserOrNot = null;
			registeredUserOrNot = registerRepo.findByEmail(registeredUser.getEmail());

			if (registeredUserOrNot != null)
				userAlreadyExistsByEmail = true;
			else if (registeredUserOrNot == null)
				userAlreadyExistsByEmail = false;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return userAlreadyExistsByEmail;
	}

	public ResponseEntity<String> register(RegisterEntity registeredUser) {

		ResponseEntity<String> responeEntityString = null;

		try {
			Boolean userAlreadyExistsByEmail = checkIfUserAlreadyExistsByEmail(registeredUser);

			if (userAlreadyExistsByEmail) {
				responeEntityString = ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("User Already exists with this email ,pls try different email");
			} else 
				registerRepo.save(registeredUser);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responeEntityString;
	}

	public ResponseEntity<String> loginUser(HttpServletRequest request, HttpServletResponse response,RegisterEntity loginEntity) {

		ResponseEntity<String> responeEntityString = null;

		try {

			RegisterEntity fetchedLogin = registerRepo.findByEmailAndPassword(loginEntity.getEmail(),loginEntity.getPassword());

			if (fetchedLogin != null) {

			    String cookieValue = String.valueOf(fetchedLogin.getuserid());
			    
				 
            ResponseCookie cookie = ResponseCookie.from("loggedUser", cookieValue)
                    .httpOnly(true)
                    .secure(true) // Set to false if testing on localhost without HTTPS
                    .sameSite("None") //  Required for cross-origin cookies (React <-> Spring)
                    .path("/")
                    .maxAge(7 * 24 * 60 * 60) // 7 days in seconds
                    .build();
				 
	 			response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
				responeEntityString = ResponseEntity.status(HttpStatus.OK).body("You Logged in Successfully");
				   
				ResponseEntity<String> responseEntity = ResponseEntity
	 			            .status(HttpStatus.OK)
	 			            .header(HttpHeaders.SET_COOKIE, cookie.toString())  //  Attach cookie in response
	 			            .body("You Logged in Successfully");
	 
	 			    System.out.println("You Logged in Successfully");
	 
	 			    return responseEntity;  // Return response with cookie
	 			    
			} else {
 			    return ResponseEntity
 			            .status(HttpStatus.BAD_REQUEST)
 			            .body("Invalid user credentials, Please check your credentials.");
 			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return responeEntityString;

	}
}
