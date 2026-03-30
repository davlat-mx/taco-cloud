//package org.dave.tacocloud.controller;
//
//import java.util.Optional;
//
//import org.dave.tacocloud.model.Taco;
//import org.dave.tacocloud.repository.OrderRepository;
//import org.dave.tacocloud.repository.TacoRepository;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping(path="/api/tacos",
//    produces="application/json")
//@CrossOrigin(origins="http://tacocloud:8080")
//public class TacoController {
//
//    private TacoRepository tacoRepo;
//    public TacoController(TacoRepository tacoRepo) {
//        this.tacoRepo = tacoRepo;
//    }
//
//    @GetMapping(params="recent")
//    public Iterable<Taco> recentTacos() {
//        PageRequest page = PageRequest.of(
//            0, 12, Sort.by("createdAt").descending());
//        return tacoRepo.findAll(page).getContent();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Taco> tacoById(@PathVariable("id") Integer id) {
//        Optional<Taco> optTaco = tacoRepo.findById(id);
//        if (optTaco.isPresent()) {
//            return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
//        }
//        return ResponseEntity.notFound().build();
//    }
//
//    @PostMapping(consumes="application/json")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Taco postTaco(@RequestBody Taco taco) {
//        return tacoRepo.save(taco);
//    }
//}