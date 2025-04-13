package com.example.expense.Entities;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name="register",uniqueConstraints={@UniqueConstraint(columnNames={"email"})})
public class RegisterEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	@Valid
	@Column(name="username")
	@Pattern(
	regexp = "^[a-zA-Z][a-zA-Z0-9_]{5,29}$", message = "Example : JohnDoe_123")
	private String username;
	
	@Valid
	@Column(name="password")
	@Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",message="Example valid password : Abc@1234")
	private String password;
	
	@Valid
	@Column(name="email")
	@Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",message = "Invalid email format")
	private String email;
	
	@Valid
	@Column(name="fullName")
	@Pattern(regexp="^[A-Z][a-zA-Z-' ]{5,29}$",	message = "Example fullname : Mary Jane Smith")
	private String fullName;

	@OneToMany(mappedBy="registerEntity",cascade=CascadeType.ALL)
	@JsonIgnore
	private List<ExpenseEntity> expense;

	public RegisterEntity() {
		super();
	}

	public RegisterEntity(int id, String username, String password, String email, String fullName,
			List<ExpenseEntity> expense) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.fullName = fullName;
		this.expense = expense;
	}

	public int getuserid() {
		return id;
	}

	public void setuserid(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public List<ExpenseEntity> getExpense() {
		return expense;
	}

	public void setExpense(List<ExpenseEntity> expense) {
		this.expense = expense;
	}

	@Override
	public String toString() {
		return "RegisterEntity [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", fullName=" + fullName + "]";
	}
	
}
