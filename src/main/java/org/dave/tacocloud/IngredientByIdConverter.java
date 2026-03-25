package org.dave.tacocloud;

import org.dave.tacocloud.model.Ingredient;
import org.dave.tacocloud.repository.IngredientRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private IngredientRepository ingredientRepo;

    public IngredientByIdConverter(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public Ingredient convert (String id) {
        return ingredientRepo.findById(id).orElse(null);
    }

}
