package ru.nanaslav.guitarshop.repository;

import org.springframework.data.repository.CrudRepository;
import ru.nanaslav.guitarshop.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
