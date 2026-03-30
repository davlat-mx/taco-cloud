package org.dave.tacocloud.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.rest.core.annotation.RestResource;

@Data
@Entity
@RestResource(rel = "tacos", path="tacos")
public class Taco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min=5, message = "Name must be at least 5 characters long")
    private String name;

    private Date createdAt;

    @NotNull
    @Size(min = 1, message="You must choose at least 1 ingredient")
    @ManyToMany()
    private List<Ingredient> ingredients;

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }
}
