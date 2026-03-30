package org.dave.tacocloud.controller;

import org.dave.tacocloud.config.OrderProps;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dave.tacocloud.model.Ingredient;
import org.dave.tacocloud.model.Taco;
import org.dave.tacocloud.model.TacoOrder;
import org.dave.tacocloud.model.User;
import org.dave.tacocloud.repository.IngredientRepository;
import org.dave.tacocloud.repository.OrderRepository;
import org.dave.tacocloud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
@RequiredArgsConstructor
public class OrderController {

    private final OrderProps orderProps;
    private final UserRepository userRepository;
    private final OrderRepository orderRepo;


    @GetMapping("/current")
    public String orderForm(){
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus sessionStatus, @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        log.info("Order submitted: {}", order);
        order.setUser(user);

        orderRepo.save(order);
        sessionStatus.setComplete();

        return "redirect:/";
    }

    @GetMapping
    public String ordersForUser(@AuthenticationPrincipal User user, Model model) {

        Pageable pageable = PageRequest.of(0, orderProps.getPageSize());
        model.addAttribute("orders",
            orderRepo.findByUserOrderByPlacedAtDesc(user, pageable));
        return "orderList";
    }

    @PatchMapping(path="/{orderId}", consumes="application/json")
    public TacoOrder patchOrder(@PathVariable("orderId") Long orderId,
                                @RequestBody TacoOrder patch) {
        TacoOrder order = orderRepo.findById(orderId).get();
        if (patch.getDeliveryName() != null) {
            order.setDeliveryName(patch.getDeliveryName());
        }
        if (patch.getDeliveryStreet() != null) {
            order.setDeliveryStreet(patch.getDeliveryStreet());
        }
        if (patch.getDeliveryCity() != null) {
            order.setDeliveryCity(patch.getDeliveryCity());
        }
        if (patch.getDeliveryState() != null) {
            order.setDeliveryState(patch.getDeliveryState());
        }
        if (patch.getDeliveryZip() != null) {order.setDeliveryZip(patch.getDeliveryZip());
        }
        if (patch.getCcNumber() != null) {
            order.setCcNumber(patch.getCcNumber());
        }
        if (patch.getCcExpiration() != null) {
            order.setCcExpiration(patch.getCcExpiration());
        }
        if (patch.getCcCVV() != null) {
            order.setCcCVV(patch.getCcCVV());
        }
        return orderRepo.save(order);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderId") Long orderId) {
        try {
            orderRepo.deleteById(orderId);
        } catch (EmptyResultDataAccessException e) {}
    }

    @Slf4j
    @Controller
    @RequestMapping("/design")
    @SessionAttributes("tacoOrder")
    public static class DesignTacoController {

        private final IngredientRepository ingredientRepo;

        @Autowired
        public DesignTacoController(IngredientRepository ingredientRepo) {
            this.ingredientRepo = ingredientRepo;
        }

        @ModelAttribute
        public void addIngredientsToModel(Model model) {
            Iterable<Ingredient> ingredients = ingredientRepo.findAll();
            Ingredient.Type[] types = Ingredient.Type.values();
            for(Ingredient.Type type : types) {
                model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
            }
        }

        @ModelAttribute(name = "tacoOrder")
        public TacoOrder order() {
            return new TacoOrder();
        }

        @ModelAttribute(name = "taco")
        public Taco taco() {
            return new Taco();
        }

        @GetMapping
        public String showDesignForm(Model model) {
            return "design";
        }

        @PostMapping
        public String processTaco(@Valid  Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder) {
            if (errors.hasErrors()) {
                return "design";
            }
            tacoOrder.addTaco(taco);
            log.info("Processing taco: {}", taco);

            return "redirect:/orders/current";
        }

        private Iterable<Ingredient> filterByType(Iterable<Ingredient> ingredients, Ingredient.Type type) {
            List<Ingredient> filtered = new ArrayList<>();
            for (Ingredient ingredient : ingredients) {
                if (ingredient.getType().equals(type)) {
                    filtered.add(ingredient);
                }
            }
            return  filtered;
        }
    }
}
