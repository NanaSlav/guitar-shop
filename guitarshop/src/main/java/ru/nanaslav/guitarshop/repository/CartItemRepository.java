package ru.nanaslav.guitarshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nanaslav.guitarshop.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {}
