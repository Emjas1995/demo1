package com.example1.demo1;



import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepo extends CrudRepository<User, Integer>{

    @Query(value = "SELECT amount FROM \"user\" WHERE id = :id", nativeQuery = true)
    int findAmountById(@Param("id") int id);

    @Modifying
    @Query(value = "UPDATE \"user\" a SET a.amount = a.amount + :amount WHERE a.id = :id", nativeQuery = true)
    void UpdateAmount(@Param("id") int id, @Param("amount") int amount);

    @Modifying
    @Query(value = "UPDATE \"user\" a SET a.amount = a.amount - :amount WHERE a.id = :id", nativeQuery = true)
    void UpdateAmountWithdraw(@Param("id") int id, @Param("amount") int amount);

    Optional<User> findById(int userId);
}
