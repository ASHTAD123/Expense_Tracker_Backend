package com.example.expense.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name="expense")
public class ExpenseEntity {
	
		
		@Id
		@GeneratedValue(strategy=GenerationType.AUTO)
		@Column(name="expenseId")
		private int expenseId;
		
		@Valid
		@NotNull
		@NotEmpty(message="Expense Name should not be empty")
		@Column(name="expenseName")	
		@Pattern(regexp = "^(?!\\d+$)[A-Za-z0-9]{2,20}$",message="Pls provide a valid expense name")
		private String expenseName;
		
		@Valid
		@DecimalMin(value = "1", inclusive = true)
        @DecimalMax(value = "2147483647", inclusive = true)
		private float amount;
		
		@Valid
		@NotNull
		@NotEmpty
		@Column(name="date")
		private String date;
		
		@Valid
		@NotNull
		@NotEmpty(message="Expense Description should not be empty")
		@Column(name="description")
		@Pattern(regexp = "^(?!\\d+$)[A-Za-z0-9 ]{5,50}$", 
		message="Pls provide a valid expense description")
		private String description;
			
		@ManyToOne
		@JoinColumn(name="id",nullable=false)
		@JsonIgnore
		private RegisterEntity registerEntity;
		
		public ExpenseEntity() {
			super();
		}
		
		public ExpenseEntity(int expenseId) {
		    this.expenseId = expenseId;
		}

		public ExpenseEntity(int expenseId, String expenseName, float amount, String date, String description,
				RegisterEntity registerEntity) {
			super();
			this.expenseId = expenseId;
			this.expenseName = expenseName;
			this.amount = amount;
			this.date = date;
			this.description = description;
			this.registerEntity = registerEntity;
		}


		public int getid() {
			return expenseId;
		}

		public void setid(int expenseId) {
			this.expenseId = expenseId;
		}

		public int getExpenseId() {
			return expenseId;
		}

		public void setExpenseId(int expenseId) {
			this.expenseId = expenseId;
		}

		public String getExpenseName() {
			return expenseName;
		}

		public void setExpenseName(String expenseName) {
			
			this.expenseName = expenseName;
		}

		public float getAmount() {
			return amount;
		}

		public void setAmount(float amount) {
			this.amount = amount;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description.trim();
		}

		public RegisterEntity getRegisterEntity() {
			return registerEntity;
		}

		public void setRegisterEntity(RegisterEntity registerEntity) {
			this.registerEntity = registerEntity;
		}

		@Override
		public String toString() {
			return "ExpenseEntity [expenseId=" + expenseId + ", expenseName=" + expenseName + ", amount=" + amount
					+ ", date=" + date + ", description=" + description ;
		}


		
		
}
