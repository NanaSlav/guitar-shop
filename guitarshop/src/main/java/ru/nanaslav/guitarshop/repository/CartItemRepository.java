package ru.nanaslav.guitarshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nanaslav.guitarshop.model.Cart;
import ru.nanaslav.guitarshop.model.CartItem;

import java.util.Set;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Set<CartItem> findAllByCart(Cart cart);
}
