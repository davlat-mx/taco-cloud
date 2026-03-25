package org.dave.tacocloud.repository;

import java.util.List;

import java.util.Date;
import org.dave.tacocloud.model.TacoOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
//    TacoOrder save(TacoOrder order);
    List<TacoOrder> findByDeliveryZip(String deliveryZip);

    List<TacoOrder> readOrdersByDeliveryZipAndPlacedAtBetween(
        String deliveryZip, Date startDate, Date endDate);

    List<TacoOrder> findByDeliveryToAndDeliveryCityAllIgnoresCase(
        String deliveryTo, String deliveryCity);

    List<TacoOrder> findByDeliveryCityOrderByDeliveryTo(String city);

//    @Query("Order o where o.deliveryCity='Seattle'")
//    List<TacoOrder> readOrdersDeliveredInSeattle();
}
