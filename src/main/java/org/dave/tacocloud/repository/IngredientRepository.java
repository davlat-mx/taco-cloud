package org.dave.tacocloud.repository;

import java.util.Optional;

import org.dave.tacocloud.model.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

//    Iterable<Ingredient> findAll();
//
//    Optional<Ingredient> findById(String id);
//
//    Ingredient save(Ingredient ingredient);
}
