package ru.nanaslav.guitarshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nanaslav.guitarshop.model.OrderedProduct;

public interface OrderedProductRepository extends JpaRepository<OrderedProduct, Long> {}
