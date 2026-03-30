package org.dave.tacocloud.config;

import java.util.Arrays;

import org.dave.tacocloud.model.Ingredient;
import org.dave.tacocloud.model.Taco;
import org.dave.tacocloud.repository.IngredientRepository;
import org.dave.tacocloud.repository.TacoRepository;
import org.dave.tacocloud.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.dave.tacocloud.model.Ingredient.Type;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/login").setViewName("login");
    }

    @Bean
    public CommandLineRunner dataLoader(
        IngredientRepository repo,
        UserRepository userRepo,
        PasswordEncoder encoder,
        TacoRepository tacoRepo) {
        return args -> {
            Ingredient flourTortilla = new Ingredient(
                "FLTO", "Flour Tortilla", Type.WRAP);
            Ingredient cornTortilla = new Ingredient(
                "COTO", "Corn Tortilla", Type.WRAP);
            Ingredient groundBeef = new Ingredient(
                "GRBF", "Ground Beef", Type.PROTEIN);
            Ingredient carnitas = new Ingredient(
                "CARN", "Carnitas", Type.PROTEIN);
            Ingredient tomatoes = new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES);
            Ingredient lettuce = new Ingredient(
                "LETC", "Lettuce", Type.VEGGIES);
            Ingredient cheddar = new Ingredient(
                "CHED", "Cheddar", Type.CHEESE);
            Ingredient jack = new Ingredient(
                "JACK", "Monterrey Jack", Type.CHEESE);
            Ingredient salsa = new Ingredient(
                "SLSA", "Salsa", Type.SAUCE);
            Ingredient sourCream = new Ingredient(
                "SRCR", "Sour Cream", Type.SAUCE);
            repo.save(flourTortilla);
            repo.save(cornTortilla);
            repo.save(groundBeef);
            repo.save(carnitas);
            repo.save(tomatoes);
            repo.save(lettuce);
            repo.save(cheddar);
            repo.save(jack);
            repo.save(salsa);
            repo.save(sourCream);
            Taco taco1 = new Taco();
            taco1.setName("Carnivore");
            taco1.setIngredients(Arrays.asList(
                flourTortilla, groundBeef, carnitas,
                sourCream, salsa, cheddar));
            tacoRepo.save(taco1);
            Taco taco2 = new Taco();
            taco2.setName("Bovine Bounty");
            taco2.setIngredients(Arrays.asList(
                cornTortilla, groundBeef, cheddar,
                jack, sourCream));
            tacoRepo.save(taco2);
            Taco taco3 = new Taco();
            taco3.setName("Veg-Out");
            taco3.setIngredients(Arrays.asList(
                flourTortilla, cornTortilla, tomatoes,
                lettuce, salsa));
            tacoRepo.save(taco3);
        };
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
