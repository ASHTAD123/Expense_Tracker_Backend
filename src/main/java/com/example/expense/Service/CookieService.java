package com.example.expense.Service;

import org.springframework.stereotype.Service;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class CookieService {

	public String getCookie(HttpServletRequest request) {
	    Cookie[] cookies = request.getCookies();
	    
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("loggedUser".equals(cookie.getName())) {
	                return cookie.getValue();
	            }
	        }
	    }
	    return null; // or empty string if you prefer
	}
}
