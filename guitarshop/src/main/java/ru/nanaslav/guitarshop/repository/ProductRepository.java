package ru.nanaslav.guitarshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.nanaslav.guitarshop.model.Category;
import ru.nanaslav.guitarshop.model.Product;

import java.util.List;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategoriesContains(Category category);
}
