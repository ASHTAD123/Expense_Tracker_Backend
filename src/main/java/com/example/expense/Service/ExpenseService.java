package com.example.expense.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.expense.Dao.ExpenseRepo;
import com.example.expense.Dao.RegistrationRepo;
import com.example.expense.Entities.ExpenseEntity;
import com.example.expense.Entities.RegisterEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
@Transactional
public class ExpenseService {

	@Autowired
	private ExpenseRepo expenseRepository;
	
	@Autowired
	private RegistrationRepo registerRepository;

	@Autowired
	private CookieService cookieService;
	
	public List<ExpenseEntity> searchExpenseByName(String expenseName,HttpServletRequest request){
		
		int regId = 0;
		String cookie = cookieService.getCookie(request);
		
		 try{
			 if (!cookie.isEmpty()) 
				  regId = Integer.parseInt(cookie);
			 else
				 System.out.println("Invalid cookie value: " + cookie);
			 
		 }catch(NumberFormatException  e) {
			 e.printStackTrace();
		 }
		 
		 List<ExpenseEntity> expenseList = expenseRepository.findByExpenseNameAndRegisterEntityIdContainingIgnoreCase(expenseName.trim(),regId);
			 
		return expenseList;
	}


	public RegisterEntity getRegisteredUserById(int registrationId) {

		Optional<RegisterEntity> entity = registerRepository.findById(registrationId);
		RegisterEntity registerEntity = null;

		if (!entity.isPresent()) {
			return registerEntity;
		}

		return registerEntity = entity.get();
	}

	public ExpenseEntity getExpenseByExpenseIdAndRegisterId(int expenseId, int userId) {

		Optional<ExpenseEntity> currentExpOptional = expenseRepository.findByExpenseIdAndRegisterEntityId(expenseId,userId);
	    ExpenseEntity expenseEntity = currentExpOptional.get();

		if (!currentExpOptional.isPresent()) {
			return expenseEntity;
		}

		return expenseEntity;
	}

	
	public List<Optional<ExpenseEntity>> getExpense(HttpServletRequest request, HttpServletResponse response) { 
	    
		List<Optional<ExpenseEntity>> entity = null;
	    	  	
	    String cookie = cookieService.getCookie(request);
	    
	    if (!cookie.isEmpty()) {	    	
	    	 int regId = Integer.parseInt(cookie);
	    	 entity= expenseRepository.findAllByRegisterEntity_Id(regId);
		}	
	    return entity;
	}


	public ResponseEntity<String> addExpense(ExpenseEntity expenseEntity, HttpServletRequest request,HttpServletResponse response) {

		int regId =0;
		
		ResponseEntity<String> responeEntityString = null;

		String cookie = cookieService.getCookie(request);
	
		 if (!cookie.isEmpty()) 
			  regId = Integer.parseInt(cookie);
		 	
		 RegisterEntity registeredEntity = getRegisteredUserById(regId);
		 
		 ExpenseEntity newlyCreatedEntity = new ExpenseEntity();
		 newlyCreatedEntity.setExpenseName(expenseEntity.getExpenseName().trim());
		 newlyCreatedEntity.setAmount(expenseEntity.getAmount());
		 newlyCreatedEntity.setDescription(expenseEntity.getDescription().trim());
		 newlyCreatedEntity.setDate(expenseEntity.getDate().trim());
		 newlyCreatedEntity.setRegisterEntity(registeredEntity);
		
		 if (registeredEntity == null)
				responeEntityString = ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Unable to add expense, User not found");

			ExpenseEntity savedExpenseEntity = expenseRepository.save(newlyCreatedEntity);

			if (savedExpenseEntity != null)
				return responeEntityString = ResponseEntity.status(HttpStatus.OK).body("Expense Added Successfully");
		
		return responeEntityString;
	}

	
	public ResponseEntity<String> updateUserExpense(ExpenseEntity expenseEntity, Integer expenseId, HttpServletRequest request,
			HttpServletResponse response) {

		int regId =0;
		
		ResponseEntity<String> responeEntityString = null;

		String cookie = cookieService.getCookie(request);
		
		 if (!cookie.isEmpty()) 
			  regId = Integer.parseInt(cookie);
		 
		RegisterEntity registeredEntity = getRegisteredUserById(regId);

		if (registeredEntity == null) {
			return responeEntityString = ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Unable to update expense,User not found");
		}

		int userId = registeredEntity.getuserid();
		ExpenseEntity recievedExpenseEntity = getExpenseByExpenseIdAndRegisterId(expenseId, userId);

		if (recievedExpenseEntity == null) {
			return responeEntityString = ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Unable to update expense,Expense not found with the given expense details");
		}
		
		recievedExpenseEntity.setExpenseId(expenseId);
		recievedExpenseEntity.setAmount(expenseEntity.getAmount());
		recievedExpenseEntity.setDate(expenseEntity.getDate());
		recievedExpenseEntity.setDescription(expenseEntity.getDescription().trim());
		recievedExpenseEntity.setExpenseName(expenseEntity.getExpenseName().trim());
		recievedExpenseEntity.setRegisterEntity(registeredEntity);

		expenseRepository.save(recievedExpenseEntity);

		responeEntityString = ResponseEntity.status(HttpStatus.OK).body("Expense Updated Successfully");

		return responeEntityString;
	}

	public ResponseEntity<String> deleteUserExpense(Integer expenseId, HttpServletRequest request,
			HttpServletResponse response) {

		ResponseEntity<String> responeEntityString = null;
		
		int registrationId = 0;
	   
		String cookie = cookieService.getCookie(request);
		
		 if (!cookie.isEmpty()) 
			 registrationId = Integer.parseInt(cookie);
		 
		RegisterEntity registeredEntity = getRegisteredUserById(registrationId);

		if (registeredEntity == null)
			responeEntityString = ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Unable to delete expense,User not found");

		ExpenseEntity recievedExpenseEntity = getExpenseByExpenseIdAndRegisterId(expenseId, registrationId);

		if (recievedExpenseEntity == null)
			responeEntityString = ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Unable to delete expense,Expense not found with the given expense details");

		expenseRepository.delete(recievedExpenseEntity);

		Optional<ExpenseEntity> checkDeleted = expenseRepository.findByExpenseIdAndRegisterEntityId(expenseId, registrationId);

		if (!checkDeleted.isPresent())
			responeEntityString = ResponseEntity.status(HttpStatus.OK).body("Expense Deleted Successfully");

		return responeEntityString;
	}
}
