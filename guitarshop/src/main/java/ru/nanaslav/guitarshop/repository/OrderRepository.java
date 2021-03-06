package ru.nanaslav.guitarshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.nanaslav.guitarshop.model.Order;
import ru.nanaslav.guitarshop.model.User;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByCustomer(User user);
    Page<Order> findAllByExecutedFalse(Pageable pageable);
}
