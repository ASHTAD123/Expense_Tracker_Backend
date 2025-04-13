package com.example.expense.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.expense.Entities.RegisterEntity;

@Repository
public interface RegistrationRepo extends JpaRepository<RegisterEntity,Integer> {

	RegisterEntity findByEmailAndPassword(String email,String password);

	RegisterEntity findByEmail(String email);
	

}
