package org.dave.tacocloud.repository;

import java.util.UUID;

import org.dave.tacocloud.model.TacoOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<TacoOrder, UUID> {
}
