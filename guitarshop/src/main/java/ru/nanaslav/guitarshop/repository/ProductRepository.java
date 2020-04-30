package ru.nanaslav.guitarshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.nanaslav.guitarshop.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {}
