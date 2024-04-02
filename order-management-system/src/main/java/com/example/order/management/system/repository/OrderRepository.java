package com.example.order.management.system.repository;

import com.example.order.management.system.modal.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Integer> {

}
