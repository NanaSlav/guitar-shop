package ru.nanaslav.guitarshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nanaslav.guitarshop.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {}
