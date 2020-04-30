package ru.nanaslav.guitarshop.repository;

import org.springframework.data.repository.CrudRepository;
import ru.nanaslav.guitarshop.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}
