package ru.nanaslav.guitarshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nanaslav.guitarshop.model.Delivery;
import ru.nanaslav.guitarshop.model.DeliveryType;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findAllByDeliveryType(DeliveryType deliveryType);
}
