package com.manage.order.repository;

import com.manage.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @EntityGraph(attributePaths = {"itemList", "user"})
    public Page<Order> findByUserCustomerId(Integer customerId, Pageable pageable);


    @Query(value = "UPDATE orders SET status = :status where order_id =:orderId", nativeQuery = true)
    public void updateOrderStatus(@Param("status") String status, @Param("orderId") Integer orderId);
}
