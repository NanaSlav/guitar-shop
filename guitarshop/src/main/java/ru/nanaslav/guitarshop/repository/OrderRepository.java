package ru.nanaslav.guitarshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nanaslav.guitarshop.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {}
