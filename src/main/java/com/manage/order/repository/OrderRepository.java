package com.manage.order.repository;

import com.manage.order.entity.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @EntityGraph(attributePaths = {"itemList", "user"})
    public Page<Order> findByUserCustomerIdAndIsDeletedFalse(Integer customerId, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "UPDATE orders SET status = :status, version = version + 1 where order_id =:orderId and version = :version", nativeQuery = true)
    public int updateOrderStatus(@Param("status") String status, @Param("orderId") Integer orderId, @Param("version") Integer version);

}
