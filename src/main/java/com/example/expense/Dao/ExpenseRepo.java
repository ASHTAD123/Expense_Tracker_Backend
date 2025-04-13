package com.example.expense.Dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.expense.Entities.ExpenseEntity;

@Repository
public interface ExpenseRepo extends JpaRepository<ExpenseEntity,Integer> {

	List<Optional<ExpenseEntity>> findAllByRegisterEntity_Id(int registerEntityId);

	Optional<ExpenseEntity> findByExpenseIdAndRegisterEntityId(int expenseId,int id);
	
	@Query(nativeQuery=true,value="select * from public.expense WHERE TRIM(public.expense.expenseName)=:expenseName AND public.expense.id=:regId")	
	List<ExpenseEntity> findByExpenseNameAndRegisterEntityIdContainingIgnoreCase(@Param("expenseName") String expenseName,@Param("regId")int regId);

}
