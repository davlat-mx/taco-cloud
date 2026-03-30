package org.dave.tacocloud.repository;

import org.dave.tacocloud.model.Taco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TacoRepository extends JpaRepository<Taco, Integer> {
}
