package ru.nanaslav.guitarshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nanaslav.guitarshop.model.Cart;
import ru.nanaslav.guitarshop.model.User;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
}
