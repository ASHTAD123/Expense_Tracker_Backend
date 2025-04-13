package com.example.expense.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.expense.Entities.ExpenseEntity;
import com.example.expense.Service.CookieService;
import com.example.expense.Service.ExpenseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/expenseTracker")
@CrossOrigin(origins = "https://symphonious-otter-07cb0e.netlify.app")
public class ExpenseController {

	@Autowired
	private ExpenseService expenseService;
	
	ResponseEntity<String> responseString = null;
	
	@Autowired
	private CookieService cookieService;
	
	@GetMapping("/home")
	public String  home(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		 return "Hello from Railway!";
	}
	
	@GetMapping("/search/{expenseName}")
	public List<ExpenseEntity> searchExpenseByName(@PathVariable String expenseName,HttpServletRequest request){
		return expenseService.searchExpenseByName(expenseName, request);
		
	}

	@GetMapping("/{expenseId}")
	public ExpenseEntity getExpenseById(@PathVariable Integer expenseId,HttpServletRequest request){
		
		int regId = 0;
		String cookie = cookieService.getCookie(request);
		
		 if (!cookie.isEmpty()) 
			  regId = Integer.parseInt(cookie);
		
		 return expenseService.getExpenseByExpenseIdAndRegisterId(expenseId, regId);
	}
	
	
	@GetMapping("/expenses")
	public List<Optional<ExpenseEntity>> getExpense(HttpServletRequest request, HttpServletResponse response) {
		
		return expenseService.getExpense(request, response);
	}

	@PostMapping("/addExpense")
	public ResponseEntity<String> addUserExpense(@Valid @RequestBody ExpenseEntity expense, HttpServletRequest request,HttpServletResponse response) {
		return responseString = expenseService.addExpense(expense, request, response);
	}

	@PostMapping("/updateExpense/{expenseId}")
	public ResponseEntity<String> updateUserExpense(@Valid @RequestBody ExpenseEntity expense,@PathVariable Integer expenseId,HttpServletRequest request, HttpServletResponse response) {
		return responseString = expenseService.updateUserExpense(expense, expenseId,request, response);
	}

	@DeleteMapping("/removeExpense/{expenseId}")
	public ResponseEntity<String> deleteUserExpense(@PathVariable Integer expenseId, HttpServletRequest request,HttpServletResponse response) {	
		return responseString = expenseService.deleteUserExpense(expenseId, request, response);
	}
}
