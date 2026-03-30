package org.dave.tacocloud.repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

import org.dave.tacocloud.model.TacoOrder;
import org.dave.tacocloud.model.User;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

    List<TacoOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
//    TacoOrder save(TacoOrder order);
//    List<TacoOrder> findByDeliveryZip(String deliveryZip);
//
//    List<TacoOrder> readOrdersByDeliveryZipAndPlacedAtBetween(
//        String deliveryZip, Date startDate, Date endDate);
//
//    List<TacoOrder> findByDeliveryToAndDeliveryCityAllIgnoresCase(
//        String deliveryTo, String deliveryCity);

//    List<TacoOrder> findByDeliveryCityOrderByDeliveryTo(String city);

//    @Query("Order o where o.deliveryCity='Seattle'")
//    List<TacoOrder> readOrdersDeliveredInSeattle();
}
