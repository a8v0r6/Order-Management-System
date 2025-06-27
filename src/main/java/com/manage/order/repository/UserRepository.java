package com.manage.order.repository;

import com.manage.order.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET is_Deleted = true where customer_ID = :id", nativeQuery = true)
    public void softDelete(@Param("id") Integer id);
}
