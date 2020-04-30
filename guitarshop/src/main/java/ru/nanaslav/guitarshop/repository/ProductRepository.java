package ru.nanaslav.guitarshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.nanaslav.guitarshop.model.Category;
import ru.nanaslav.guitarshop.model.Product;

import java.util.List;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByCategoriesContains(Category category, Pageable pageable);
}
